package com.webapp.ftm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name="team")
public class TeamEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name="homecourt")
    private String homeCourt;

    @OneToMany(mappedBy = "playerStatisticPk.teamEntity")
    private List<PlayerStatisticEntity> playerStatisticEntities;
}
