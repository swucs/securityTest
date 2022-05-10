package com.example.corespringsecurity.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "ACCOUNT_ROLES")
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@IdClass(AccountRolesID.class)
public class AccountRoles implements Serializable {

    @Id
    @JoinColumn(name="account_id")
    @JsonBackReference
    @ManyToOne
    private Account account;

    @Id
    @JoinColumn(name="role_id")
    @JsonBackReference
    @ManyToOne
    private Role role;
}
