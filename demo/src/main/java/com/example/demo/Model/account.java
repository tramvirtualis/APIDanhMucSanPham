package com.example.demo.Model;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import java.io.Serializable;

@Entity
@Table(name="accounts")
public class account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    public account(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public account() {
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
