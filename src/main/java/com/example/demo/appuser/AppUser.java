package com.example.demo.appuser;

import com.example.demo.role.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table
public class AppUser {
    @Id
    @SequenceGenerator(
            name = "appuser_sequence",
            sequenceName = "appuser_sequence",
            allocationSize = 1
    )
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appuser_sequence")
    private long id;

    private String name;

    private String email;

    private LocalDate dob;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "app_user_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName="id")
    )
    @JsonIgnoreProperties("users")
    Set<Role> roles;

    public AppUser() {}

    public AppUser(long id, String name, String email, LocalDate dob) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dob = dob;
    }

    public AppUser(String name, String email, LocalDate dob) {
        this.name = name;
        this.email = email;
        this.dob = dob;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role) {
        roles.remove(role);
        role.getUsers().remove(this);
    }

    public void removeRoles() {
        for (Role role : roles) {
            removeRole(role);
        }
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                '}';
    }
}
