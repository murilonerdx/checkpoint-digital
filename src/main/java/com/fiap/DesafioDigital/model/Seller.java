package com.fiap.DesafioDigital.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tb_seller")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="nm_username", unique = true)
    private String username;
    @Column(name="ds_email")
    private String email;

    @OneToMany(mappedBy = "seller",cascade = CascadeType.PERSIST)
    private List<Setup> setups = new ArrayList<>();

    @Column(name="ds_senha", nullable = false)
    private String senha;

    public String getEmail() {
        return email;
    }

    public Seller(Long id, String username, String email, String senha) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.senha = senha;
    }

    public Seller(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Setup> getSetups() {
        return setups;
    }

    public void setSetups(List<Setup> setups) {
        this.setups = setups;
    }
}
