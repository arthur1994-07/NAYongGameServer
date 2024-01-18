package com.common.nayong.Entity;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "GSUserInfo", schema = "dbo")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "UserNum", unique = true)
    private int userNum;

    @Column (name = "UserID", columnDefinition = "TEXT", unique = true)
    @NotBlank
    private String userID;

    @Column (name = "UserPass", columnDefinition = "TEXT", unique = true)
    @NotBlank
    private String userPass;

    @Column (name = "UserType", columnDefinition = "bigint default 0")
    private int userType;

    @Column (name = "UserEmail", columnDefinition = "TEXT", unique = true)
    @NotBlank
    private String userEmail;

    public int getUserNum() { return this.userNum; }

    public String getUserID() { return this.userID; }
    public void setUserID(String userId) { this.userID = userId; }

    public String getUserEmail() { return this.userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public int getUserType() { return this.userType; }
    public void setUserType(int userType) { this.userType = userType; }
}
