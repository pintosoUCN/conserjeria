package cl.ucn.disc.as.services;

import cl.ucn.disc.as.model.*;

import java.time.Instant;
import java.util.List;

public interface Sistema {

    /**
     * Agrega un edificio al sistema
     *
     * @param edificio
     */
    Edificio add(Edificio edificio);

    Persona add(Persona persona);
    Departamento addDepartamento(Departamento departamento, Edificio edificio);
    Departamento addDepartamento(Departamento departamento, Long idEdificio);
    Contrato realizarContrato(Persona duenio, Departamento departamento, Instant fechaPago);
    Contrato realizarContrato(Long idDuenio, Long idDepartamento, Instant fechaPago);
    List<Contrato> getContratos();
    List<Persona> getPersonas();
    List<Pago> getPagos(String rut);
}
