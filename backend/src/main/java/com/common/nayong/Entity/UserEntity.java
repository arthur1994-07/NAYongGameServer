package com.common.nayong.Entity;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "GSUserInfo", schema = "dbo")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "GSUserInfo", sequenceName = "z_gs_userinfo", initialValue = 1, allocationSize = 1)
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
}
