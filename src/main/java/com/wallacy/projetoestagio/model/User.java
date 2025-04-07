package com.wallacy.projetoestagio.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tb_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
}
