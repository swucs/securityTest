package com.example.corespringsecurity.domain;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "RESOURCES")
@Data
@ToString(exclude = {"roleResources"})
@EntityListeners(value = { AuditingEntityListener.class })
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Resources implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "resource_id")
    private Long id;

    @Column(name = "resource_name")
    private String resourceName;

    @Column(name = "http_method")
    private String httpMethod;

    @Column(name = "order_num")
    private int orderNum;

    @Column(name = "resource_type")
    private String resourceType;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "role_resources", joinColumns = {
//            @JoinColumn(name = "resource_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
//    private Set<Role> roleSet = new HashSet<>();

    @OneToMany(mappedBy = "resources", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<RoleResources> roleResources = new ArrayList<>();

    public void clearRoleResources() {
        this.roleResources.removeAll(this.roleResources);
    }

    public void addRoleResources(RoleResources rr) {
        this.roleResources.add(rr);
    }

}
