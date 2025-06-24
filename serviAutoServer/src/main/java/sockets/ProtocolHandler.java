package sockets;

import domain.Request;
import domain.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.UUID;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;


public class ProtocolHandler {
    private static final Logger logger = LogManager.getLogger(ProtocolHandler.class);

//    private static final String SERVER_FILE_STORAGE_ROOT = "C:\\Users\\PC\\Documents\\UCR\\Progra_II\\PROYECTO\\BinaryFilesLocal\\img";
//    private static final String ROOM_IMAGES_RELATIVE_PATH_PREFIX = "data/images/rooms/";
//    private static final String TEMP_IMAGES_RELATIVE_PATH_PREFIX = "data/images/temp_rooms/";

    public Response handle(Request request) {
        logger.debug("Handling request: {}", request.getAction());
        try {
            switch (request.getAction()) {


                case "test": {
                    //se muestra en la consola del server
                    System.out.println("HELLO IM WORKING");
                    return new Response("200", "Test OK. ProtocolHandler funcionando.", "Hello from server!");
                }

                default:
                    logger.warn("Acción no reconocida: {}", request.getAction());
                    return new Response("400", "Acción no reconocida: " + request.getAction(), null);

            }

        } catch (Exception e) {
            logger.error("Error handling request {}: {}", request.getAction(), e.getMessage());
            return new Response("500", "Internal Server Error: " + e.getMessage(), null);
        }

    }

    private int parseIntFromRequest(Object data) {
        if (data instanceof Double) {
            return ((Double) data).intValue();
        } else if (data instanceof Integer) {
            return (Integer) data;
        } else {
            try {
                return Integer.parseInt(data.toString());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Tipo de dato inválido para ID numérico: " + data, e);
            }
        }
    }


}