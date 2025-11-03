package com.soda.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "listado_productos")
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_listadoProductos")
    private Integer id_listadoProductos;

    @Column(name = "descripcion", length = 100)
    private String descripcion;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "disponible")
    private Boolean disponible;

    @Column(name = "categoria", length = 50)
    private String categoria;

    @Column(name = "imagen", length = 1024)
    private String imagen;
}
