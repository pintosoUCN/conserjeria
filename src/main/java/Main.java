import cl.ucn.disc.as.ui.ApiRestServer;
import cl.ucn.disc.as.ui.WebController;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public final class Main {

    public static void main(String[] args){
        log.debug("Starting...");
        ApiRestServer.start(7070, new WebController());
        log.debug("Done");
    }
}
