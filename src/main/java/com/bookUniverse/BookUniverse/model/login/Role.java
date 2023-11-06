package com.bookUniverse.BookUniverse.model.login;

import com.bookUniverse.BookUniverse.enums.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import javax.persistence.*;
@Entity
@Table(name = "role",
        uniqueConstraints = {@UniqueConstraint(name = "uk_role_name",columnNames = "user_role")})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(generator = "user_role_seq_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_role_seq_gen",sequenceName = "user_role_seq",allocationSize = 1)
    private Integer id;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private UserRoles role;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active",columnDefinition = "boolean default true")
    private boolean isActive;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
