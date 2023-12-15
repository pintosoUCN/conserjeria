import cl.ucn.disc.as.model.Departamento;
import cl.ucn.disc.as.model.Edificio;
import cl.ucn.disc.as.services.PersonaGrpcServiceImpl;
import cl.ucn.disc.as.services.Sistema;
import cl.ucn.disc.as.services.SistemaImpl;
import cl.ucn.disc.as.ui.ApiRestServer;
import cl.ucn.disc.as.ui.WebController;
import io.ebean.DB;
import io.ebean.Database;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;

import java.io.IOException;


@Slf4j
public final class Main {

    public static Database getDatabase() {
        DatabaseConfig config = new DatabaseConfig();
        config.setName("default");

        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDriver("org.mariadb.jdbc.Driver");
        dataSourceConfig.setUsername("my-username");
        dataSourceConfig.setPassword("secret-password-conserjeria-as");
        dataSourceConfig.setUrl("jdbc:mariadb://localhost:3306/sample-db");

        config.setDataSourceConfig(dataSourceConfig);

        config.setDefaultServer(true);
        config.setRegister(false);

        return DatabaseFactory.create(config);
    }

    public static void main(String[] args) throws IOException {


        log.debug("Starting Main...");

        ApiRestServer.start(7070, new WebController());

        Server server = ServerBuilder
                .forPort(50123)
                .addService(new PersonaGrpcServiceImpl())
                .build();
        server.start();

        log.debug("Done...");

        Database db = getDatabase();
        Sistema sistema = new SistemaImpl(db);


        Edificio edificio = Edificio.builder()
                .nombre("EDIFICIO")
                .direccion("DIR1")
                .build();

        Edificio createdEdificio = sistema.add(edificio);

    }


}