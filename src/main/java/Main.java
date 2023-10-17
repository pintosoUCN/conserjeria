import cl.ucn.disc.as.dao.PersonaFinder;
import cl.ucn.disc.as.model.Edificio;
import cl.ucn.disc.as.model.Persona;
import cl.ucn.disc.as.services.Sistema;
import cl.ucn.disc.as.services.SistemaImpl;
import io.ebean.DB;
import io.ebean.Database;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public final class Main {

    public static void main(String[] args){
        log.debug("Starting...");
        //base de datos
        Database db = DB.getDefault();

        //the sistema
        Sistema sistema = new SistemaImpl(db);
        Edificio edificio = Edificio.builder()
                .nombre("Y1")
                .direccion("Angamos 0628")
                .build();
        log.debug("Edificio");
        edificio = sistema.add(edificio);


        //crear persona
        Persona persona = Persona.builder()
                .rut("11111111-1")
                .apellidos("Pinto Mendoza")
                .nombre("Joaquin")
                .email("joako@gmail.com")
                .telefono("+56984257632")
                .build();

        //guargar persona
        db.save(persona);

        //rescatar de la base de datos
        //PersonaFinder pf = new PersonaFinder();
        //Optional<Persona> oPersona=pf.byRut("12345678-9");
        //oPersona.ifPresent(p -> log.debug(p.toString()));

        log.debug("La persona despues de guardar es:" , persona.toString());
        log.debug("Done");
    }
}
