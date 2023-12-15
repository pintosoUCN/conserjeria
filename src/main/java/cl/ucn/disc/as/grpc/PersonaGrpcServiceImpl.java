package cl.ucn.disc.as.services;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class PersonaGrpcServiceImpl extends PersonaGrpcServiceGrpc.PersonaGrpcServiceImplBase {

    @Override
    public void retrieve(PersonaGrpcRequest request, StreamObserver<PersonaGrpcResponse> responseObserver) {
        log.debug("Retrieving PersonaGrpcRequest ..");
        PersonaGrpc personaGrpc = PersonaGrpc.newBuilder()
                .setRut("208810332")
                .setNombre("Joquin")
                .setApellidos("Pinto")
                .setEmail("joaquin@email.com")
                .setTelefono("123456789")
                .build();
        responseObserver.onNext(PersonaGrpcResponse.newBuilder()
                .setPersonaGrpc(personaGrpc)
                .build());
        responseObserver.onCompleted();
    }
}

