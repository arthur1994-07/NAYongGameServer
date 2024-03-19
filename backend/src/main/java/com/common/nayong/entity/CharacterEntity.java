package com.common.nayong.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ChaInfo", schema = "dbo")
public class CharacterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "ChaInfo", sequenceName = "z_gs_chainfo", initialValue = 1, allocationSize = 1)
    @Column(name = "ChaNum", unique = true)
    private int chaNum;

    @Column(name = "ChaName", columnDefinition = "TEXT", unique = true)
    @NotBlank
    private String chaName;

    @Column(name = "ChaSchool", columnDefinition = "integer default 0", unique = true)
    @NotBlank
    private int chaSchool;

    @Column(name = "ChaLevel", columnDefinition = "integer default 0", unique = true)
    @NotBlank
    private int chaLevel;

    @Column(name = "ChaOnline", columnDefinition = "integer default 0")
    @NotBlank
    private int chaOnline;

    @Column(name = "ContributionPoint", columnDefinition = "integer default 0")
    @NotBlank
    private int contributionPoint;

    @Column(name = "ChaClass", columnDefinition = "TEXT", unique = true)
    @NotBlank
    private String chaClass;

    @Column(name = "UserNum", columnDefinition = "integer default 0")
    @NotBlank
    private int userNum;
}
