package com.example.demo.role;

import com.example.demo.appuser.AppUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Role {
    @Id
    @SequenceGenerator(
            name = "role_sequence",
            sequenceName = "role_sequence",
            allocationSize = 1
    )
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_sequence")
    private long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnoreProperties("roles")
    Set<AppUser> users;

    public Role() {}

    public Role(String name) {
        this.name = name;
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<AppUser> getUsers() {
        return users;
    }

    public void setUsers(Set<AppUser> users) {
        this.users = users;
    }

    public void addUser(AppUser user) {
        users.add(user);
        user.getRoles().add(this);
    }

    public void removeUser(AppUser user) {
        users.remove(user);
        user.getRoles().remove(this);
    }

    public void removeUsers() {
        users.forEach(this::removeUser);
    }
}
