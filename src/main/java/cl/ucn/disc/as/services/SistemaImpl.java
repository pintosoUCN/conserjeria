package cl.ucn.disc.as.services;

import cl.ucn.disc.as.model.*;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import io.ebean.Database;
import io.ebean.PersistenceIOException;
import io.ebean.Query;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Slf4j
public class SistemaImpl implements Sistema {

    private final Database database;

    public SistemaImpl(Database database) {
        this.database = database;
    }

    @Override
    public Edificio add(Edificio edificio) {
        try {
            this.database.save(edificio);
        } catch (PersistenceIOException ex) {
            log.error("Error", ex);
            //throw new SistemaException("Error al crear un edificio", ex);
        }
        return edificio;
    }

    @Override
    public Persona add(Persona persona) {
        try {
            this.database.save(persona);
        } catch (PersistenceIOException ex) {
            log.error("Error", ex);
            //throw new SistemaException("Error al crear una persona", ex);
        }
        return persona;
    }

    @Override
    public Departamento addDepartamento(Departamento departamento, Edificio edificio) {
        edificio.add(departamento);
        try {
            this.database.save(departamento);
        } catch (PersistenceIOException ex) {
            log.error("Error", ex);
            //throw new SistemaException("Error al crear un departamento", ex);
        }
        return departamento;
    }

    @Override
    public Departamento addDepartamento(Departamento departamento, Long idEdificio) {
        Edificio edificio = this.database.find(Edificio.class, idEdificio);
        if (edificio != null) {
            edificio.add(departamento);
            try {
                this.database.save(departamento);
            } catch (PersistenceIOException ex) {
                log.error("Error", ex);
                //throw new SistemaException("Error al crear un departamento", ex);
            }
        } else {
            log.error("El edificio con ID {} no se encontró.", idEdificio);
            //throw new SistemaException("El edificio con ID " + idEdificio + " no se encontró.");
        }
        return departamento;
    }

    @Override
    public Contrato realizarContrato(Persona duenio, Departamento departamento, Instant fechaPago) {
        Contrato contrato = new Contrato(fechaPago, duenio, departamento, new ArrayList<>());
        try {
            this.database.save(contrato);
        } catch (PersistenceIOException ex) {
            log.error("Error", ex);
            //throw new SistemaException("Error al crear un contrato", ex);
        }
        return contrato;
    }


    @Override
    public Contrato realizarContrato(Long idDuenio, Long idDepartamento, Instant fechaPago) {
        Persona duenio = this.database.find(Persona.class, idDuenio);
        Departamento departamento = this.database.find(Departamento.class, idDepartamento);
        if (duenio != null && departamento != null) {
            Contrato contrato = new Contrato(fechaPago, duenio, departamento, new ArrayList<>());
            try {
                this.database.save(contrato);
            } catch (PersistenceIOException ex) {
                log.error("Error", ex);
                //throw new SistemaException("Error al crear un contrato", ex);
            }
            return contrato;
        } else {
            log.error("Persona o departamento no encontrados.");
            //throw new SistemaException("Persona o departamento no encontrados.");
            return null;
        }
    }

    @Override
    public List<Contrato> getContratos() {
        Query<Contrato> query = this.database.find(Contrato.class);
        return query.findList();
    }

    @Override
    public List<Persona> getPersonas() {
        Query<Persona> query = this.database.find(Persona.class);
        return query.findList();
    }

    @Override
    public List<Pago> getPagos(String rut) {
        Query<Pago> query = this.database.find(Pago.class);
        query.where().eq("persona.rut", rut);
        return query.findList();
    }

    @Override
    public Optional<Persona> getPersona(String rut) {
        return Optional.empty();
    }

    @Override
    public void populate() {
        //buid the persona
        {
            Persona persona = Persona.builder()
                    .rut("20881033-2")
                    .nombre("Diego")
                    .apellidos("Urrutia Astorga")
                    .email("durrutia@ucn.cl")
                    .telefono("+56926374859")
                    .build();
        }
        //the faker
        Locale locale = new Locale("es-CL");
        FakeValuesService fvs = new FakeValuesService(locale, new RandomService());
        Faker faker = new Faker(locale);

        //faker
        for (int i = 0; i < 30; i++) {
            Persona persona = Persona.builder()
                    .rut(fvs.bothify("########-#"))
                    .nombre(faker.name().firstName())
                    .apellidos(faker.name().lastName())
                    .email(fvs.bothify("????@gmail.com"))
                    .telefono(fvs.bothify("+569########"))
                    .build();
            this.database.save(persona);

        }
    }
}
