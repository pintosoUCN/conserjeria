/*
 * Copyright (c) 2023. Arquitectura de Sistemas, DISC, UCN.
 */

package cl.ucn.disc.as.model;

import cl.ucn.disc.as.exceptions.IllegalDomainException;
import cl.ucn.disc.as.utils.ValidationUtils;
import io.ebean.annotation.NotNull;
import lombok.*;

import javax.persistence.Entity;

/**
 * The Persona class.
 *
 * @author Diego Urrutia-Astorga.
 */
@ToString(callSuper = true)
@AllArgsConstructor
@Builder
@Entity
public class Persona extends BaseModel {

    /**
     * The RUT.
     */
    @NotNull
    @Setter
    @Getter
    private String rut;

    /**
     * The Nombre.
     */
    @NotNull
    @Setter
    @Getter
    private String nombre;

    /**
     * The Apellidos.
     */
    @NotNull
    @Setter
    @Getter
    private String apellidos;

    /**
     * The Email.
     */
    @NotNull
    @Setter
    @Getter
    private String email;

    /**
     * The Telefono.
     */
    @NotNull
    @Setter
    @Getter
    private String telefono;

    public static class PersonaBuilder {
        /**
         * @return Persona
         */
        public  Persona build(){
            //validate rut
            /*if(!ValidationUtils.isRutValid(this.rut)){
                throw new IllegalDomainException("Rut not valid: " + this.rut);
            }*/

            //validate email
            if (!ValidationUtils.isEmailValid(this.email)){
                throw new IllegalDomainException("Email not valid: " + this.email);
            }

            //add the validations
            return new Persona(this.rut,this.nombre,this.apellidos,this.email,this.telefono);
        }
    }

}
