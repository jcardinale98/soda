package com.soda.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "listado_usuarios")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_listadoUsuarios")
    private Integer id_listadoUsuarios;

    @Column(length = 50)
    private String usuario;

    @Column(length = 50)
    private String nombre;

    @Column(length = 50)
    private String apellido;

    @Column(length = 100)
    private String correo;

    @Column(length = 1024)
    private String fotografia;
}
