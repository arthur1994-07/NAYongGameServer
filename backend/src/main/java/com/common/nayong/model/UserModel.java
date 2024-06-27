package com.common.nayong.model;

import com.common.core.base.helper.JsonHelper;
import com.common.core.web.permission.PermissionSystem;
import com.common.core.web.security.base.ClaimsInterface;
import com.common.nayong.entity.UserEntity;
import com.common.nayong.enumerate.UserType;
import com.common.nayong.permission.LicenseRightAnnotation;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class UserModel {
    private static final long K_ANONYMOUS_USER_ID = 0;
    private static final String K_ANONYMOUS_USERNAME = "Anonymous User";

    public static class Identity implements ClaimsInterface {
        @JsonProperty private String username;
        @JsonProperty private String email;
        @JsonProperty private String idToken;
        @JsonProperty private int userType;
        @JsonProperty private int userLoginState;

        public String getUsername() { return username; }
        public String getEmail() { return email; }
        public String idToken() { return idToken; }
        public int getUserType() { return userType; }
        public int getUserLoginState() { return userLoginState; }

        @Override public String key() { return "identity"; }
        @Override public String pack() { return JsonHelper.asString(JsonHelper.toTree(this)); }
        @Override public void unpack(String content) {
            var data = JsonHelper.parse(JsonHelper.fromString(content), Identity.class);
            if (data == null) return;
            this.username = data.username;
            this.email = data.email;
            this.idToken = data.idToken;
            this.userType = data.userType;
            this.userLoginState = data.getUserLoginState();
        }
    }

    public static class Authorities implements ClaimsInterface {
        private final List<GrantedAuthority> mAuthorities = new ArrayList<>();

        @Override public String key() { return "authorities"; }
        @Override public String pack() {
            var list = mAuthorities.stream().map(GrantedAuthority::getAuthority).toList();
            return JsonHelper.asString(JsonHelper.toTree(list));
        }
        @Override public void unpack(String content) {
            mAuthorities.clear();
            var data = JsonHelper.parse(JsonHelper.fromString(content), String[].class);
            if (data == null) return;
            Arrays.stream(data).forEach(s -> mAuthorities.add(new SimpleGrantedAuthority(s)));
        }

        public List<GrantedAuthority> getAuthorities() { return mAuthorities; }
        public void set(List<GrantedAuthority> newGranted) {
            mAuthorities.clear();
            mAuthorities.addAll(newGranted);
        }
    }
    private final long mId;
    private final Authorities mAuthorities = new Authorities();
    private final Identity mIdentity = new Identity();

    public UserModel(Long id, Consumer<ClaimsInterface> claimsApply) {
        mId = id;
        Arrays.stream(getClaims()).forEach(claimsApply);
    }

    public UserModel(int id, String username, String idToken) {
        mId = id;
        mIdentity.username = username;
        mIdentity.idToken = idToken;
    }
    public UserModel(UserEntity entity, String idToken) {
        this(entity.getUserNum(), entity.getUserID(), idToken);
    }

    public UserModel(UserEntity entity) {
        mId = entity.getUserNum();
        mIdentity.username = entity.getUserID();
        mIdentity.email = entity.getUserEmail();
        mIdentity.userType = entity.getUserType();
        mIdentity.userLoginState = entity.getUserLoginState();
    }

    public long getId() { return mId; }
    public ClaimsInterface[] getClaims() { return new ClaimsInterface[] { mAuthorities, mIdentity }; }
    public Collection<GrantedAuthority> getAuthorities() { return mAuthorities.getAuthorities(); }
    public boolean isAnonymous() { return mId == K_ANONYMOUS_USER_ID; }
    public Identity getIdentity() { return mIdentity; }

    public static UserModel anonymousUser(PermissionSystem<LicenseRightAnnotation> system, boolean hasPermission) {
        var model = new UserModel(K_ANONYMOUS_USER_ID, (c) -> {});
        model.mIdentity.username = K_ANONYMOUS_USERNAME;
        List<GrantedAuthority> authorities = hasPermission ? system.getStream().map(s ->
                (GrantedAuthority) new SimpleGrantedAuthority(s.databaseKey)).toList() : List.of();
        model.mAuthorities.set(authorities);
        return model;
    }

    public static UserModel getCurrent() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var principal = auth.getPrincipal();
        if (!(principal instanceof UserModel)) return null;

        return (UserModel) principal;
    }
}
