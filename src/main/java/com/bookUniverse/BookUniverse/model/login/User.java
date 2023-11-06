package com.bookUniverse.BookUniverse.model.login;

import com.bookUniverse.BookUniverse.enums.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(generator = "users_seq_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "users_seq_gen",sequenceName = "users_seq",allocationSize = 1)
    private long id;
    private String firstName;
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns =@JoinColumn(name = "role_id"))
    private List<Role> roles;
}
