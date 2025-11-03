package com.soda.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "listado_reposteria_cafe")
public class Reposteria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_listadoReposteriaCafe")
    private Integer id_listadoReposteriaCafe;

    @Column(name = "descripcion", length = 100)
    private String descripcion;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "disponible")
    private Boolean disponible;

    @Column(name = "imagen", length = 1024)
    private String imagen;
}
