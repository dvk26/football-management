package com.webapp.ftm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="permissions")
public class PermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String apiPath;
    private String method;
    private String module;
    public PermissionEntity(String name, String apiPath, String method ,String module){
        this.name=name;
        this.apiPath=apiPath;
        this.method=method;
        this.module=module;
    }
}
