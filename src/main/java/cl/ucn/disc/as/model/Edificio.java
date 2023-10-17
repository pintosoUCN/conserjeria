/*
 * Copyright (c) 2023. Arquitectura de Sistemas, DISC, UCN.
 */

package cl.ucn.disc.as.model;

import io.ebean.annotation.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * The Edificio class.
 *
 * @author Diego Urrutia-Astorga.
 */
@ToString(callSuper = true)
@AllArgsConstructor
@Builder
@Entity
public class Edificio extends BaseModel {

    /**
     * The Nombre.
     */
    @NotNull
    @Getter
    private String nombre;

    /**
     * The Direccion.
     */
    @NotNull
    @Getter
    private String direccion;

    /**
     * The departamentos asociados
     */
    @Getter
    private List<Departamento> departamentos;

    /**
     * Agrega un departamento a la lista del edificio
     * @param departamento
     */
    public void add(Departamento departamento) {
        departamentos.add(departamento);
    }


}

