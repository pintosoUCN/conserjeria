/*
 * Copyright (c) 2023. Arquitectura de Sistemas, DISC, UCN.
 */

package cl.ucn.disc.as.model;

import io.ebean.annotation.NotNull;
import lombok.*;

import javax.persistence.Entity;
import java.time.Instant;
import java.util.List;

/**
 * The Contrato class.
 *
 * @author Diego Urrutia-Astorga.
 */
@ToString(callSuper = true)
@AllArgsConstructor
@Builder
@Entity
public class Contrato extends BaseModel {


    @NotNull
    @Getter
    @Setter
    private Instant fechaPago;

    /**
     * Dueño del departamento
     */
    @NotNull
    @Getter
    @Setter
    private Persona persona;

    /**
     * Departamento asociado
     */
    @NotNull
    @Getter
    @Setter
    private Departamento departamento;

    /**
     *Pagos asociados al contrato
     */
    @Getter
    @Setter
    private List<Pago> pagos;


}

