/*
 * Copyright (c) 2023. Arquitectura de Sistemas, DISC, UCN.
 */

package cl.ucn.disc.as.model;

import io.ebean.annotation.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * The Persona class.
 *
 * @author Diego Urrutia-Astorga.
 */
@ToString(callSuper = true)
@AllArgsConstructor
@Builder
@Entity
public class Departamento extends BaseModel {

    /**
     * The Numero.
     */
    @NotNull
    private Integer numero;

    /**
     * The piso.
     */
    @NotNull
    private Integer piso;


}
