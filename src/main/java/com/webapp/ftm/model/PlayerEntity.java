package com.webapp.ftm.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Table(name="player")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class PlayerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name="dateofbirth")
    private Date dateOfBirth;

    @Column(name="type")
    private String type;

    @Column(name="note")
    private String note;

    @OneToMany(mappedBy = "playerStatisticPk.playerEntity", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<PlayerStatisticEntity> playerStatistics;
}
