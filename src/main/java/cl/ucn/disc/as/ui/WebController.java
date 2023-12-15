package cl.ucn.disc.as.ui;


import cl.ucn.disc.as.model.Persona;
import cl.ucn.disc.as.services.Sistema;
import cl.ucn.disc.as.services.SistemaImpl;
import io.ebean.DB;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.javalin.Javalin;

import java.util.Optional;

public final class WebController implements RoutesConfigurator {

    /**
     * The Sistema
     */
    private final Sistema sistema;

    public WebController() {
        this.sistema = new SistemaImpl(DB.getDefault());
    }

    @Override
    public void configure(final Javalin app) {
        app.get("/", ctx -> {
            ctx.result("Welcome to Conserjeria API REST");
        });

        app.get("/personas", ctx -> {
            ctx.json(this.sistema.getPersonas());
        });

        app.get("/grpc/personas/rut/{rut}", ctx -> {
            String rut = ctx.pathParam("rut");

            ManagedChannel channel = ManagedChannelBuilder
                    .forAddress("localhost", 50123)
                    .usePlaintext()
                    .build();

            PersonaGrpcServiceGrpc.PersonaGrpcServiceBlockingStub stub =
                    PersonaGrpcServiceGrpc.newBlockingStub(channel);

            PersonaGrpcResponse response = stub.retrieve(PersonaGrpcRequest
                    .newBuilder()
                    .setRut(rut)
                    .build());

            PersonaGrpc personaGrpc = response.getPersona();

            Optional<Persona> oPersona = Optional.of(Persona.builder()
                    .rut(personaGrpc.getRut())
                    .nombre(personaGrpc.getNombre())
                    .apellidos(personaGrpc.getApellidos())
                    .email(personaGrpc.getEmail())
                    .telefono(personaGrpc.getTelefono())
                    .build());
            ctx.json(oPersona.orElseThrow(() -> new NotFoundResponse("Can't find Persona with rut: " + rut)));
        });


    }
}