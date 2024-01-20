package com.common.nayong.model;

import com.common.core.base.helper.JsonHelper;
import com.common.core.web.security.base.ClaimsInterface;
import com.common.nayong.Entity.UserEntity;
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
    private final long mId;

    public static class Identity implements ClaimsInterface {
        @JsonProperty public String username;
        @JsonProperty public String email;

        @Override public String key() { return "identity"; }
        @Override public String pack() { return JsonHelper.asString(JsonHelper.toTree(this)); }
        @Override public void unpack(String content) {
            var data = JsonHelper.parse(JsonHelper.fromString(content), Identity.class);
            if (data == null) return;
            this.username = data.username;
            this.email = data.email;
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

    private final Authorities mAuthorities = new Authorities();
    private final Identity mIdentity = new Identity();

    public UserModel(Long id, Consumer<ClaimsInterface> claimsApply) {
        mId = id;
        Arrays.stream(getClaims()).forEach(claimsApply);
    }

    public UserModel(UserEntity entity) {
        mId = entity.getUserNum();
        mIdentity.username = entity.getUserID();
        mIdentity.email = entity.getUserEmail();
    }

    public long getId() { return mId; }
    public ClaimsInterface[] getClaims() { return new ClaimsInterface[] { mAuthorities, mIdentity }; }
    public Collection<GrantedAuthority> getAuthorities() { return mAuthorities.getAuthorities(); }
    public Identity getIdentity() { return mIdentity; }


    public static UserModel getCurrent() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var principal = auth.getPrincipal();
        if (!(principal instanceof UserModel)) return null;

        return (UserModel) principal;
    }
}
