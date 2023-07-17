package com.example.place.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "keyword")
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "key_column")
    private String keyColumn;

    @Column(name = "value_column")
    private int valueColumn;

}
