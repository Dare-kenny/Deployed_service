package com.example.Invoisecure.models;

import com.example.Invoisecure.enum_package.ROLETYPE;
import jakarta.persistence.*;

@Entity
@Table(name="personnel_credentials")
public class Personnel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String username;
    @Column
    private String password;
    @Enumerated(EnumType.STRING)
    @Column
    private ROLETYPE role;

    public Personnel() {}

    public Personnel(String username, String password, ROLETYPE role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return String.valueOf(role);
    }

    public void setRole(ROLETYPE role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
