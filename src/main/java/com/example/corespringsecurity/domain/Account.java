package com.example.corespringsecurity.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@ToString(exclude = {"accountRoles"})
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String username;

    @Column
    private String email;

    @Column
    private int age;

    @Column
    private String password;

    @OneToMany(mappedBy = "account", cascade={CascadeType.ALL}, orphanRemoval = true)
    private Set<AccountRoles> accountRoles = new HashSet<>();

    public void addAccountRoles(AccountRoles accountRoles) {
        this.accountRoles.add(accountRoles);
    }

    public void clearAccountRoles() {
        this.accountRoles.removeAll(this.accountRoles);
    }

//    @ManyToMany(fetch = FetchType.LAZY, cascade={CascadeType.ALL})
//    @JoinTable(name = "account_roles", joinColumns = { @JoinColumn(name = "account_id") }, inverseJoinColumns = {
//            @JoinColumn(name = "role_id") })
//    private Set<Role> userRoles = new HashSet<>();
}
