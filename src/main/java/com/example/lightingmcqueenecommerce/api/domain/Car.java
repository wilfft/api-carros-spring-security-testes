package com.example.lightingmcqueenecommerce.api.domain;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity(name = "carro")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome")
    private String name;
    @Column(name = "tipo")
    private String type;
    @Column(name = "descricao")
    private String description;
    @Column(name = "url_foto")
    private String urlPhoto;

    private String urlVideo;
    private String latitude;
    private String longitude;

}
