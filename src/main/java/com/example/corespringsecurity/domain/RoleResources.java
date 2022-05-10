package com.example.corespringsecurity.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Data
@Table(name = "ROLE_RESOURCES")
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@IdClass(RoleResourcesID.class)
public class RoleResources implements Serializable {
    @Id
    @JoinColumn(name="resource_id")
    @JsonBackReference
    @ManyToOne
    private Resources resources;

    @Id
    @JoinColumn(name="role_id")
    @JsonBackReference
    @ManyToOne
    private Role role;
}
