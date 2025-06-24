package sockets;

import domain.Request;
import domain.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private static final Logger logger = LogManager.getLogger(ClientHandler.class);
    private final Socket socket;

    private final ProtocolHandler handler = new ProtocolHandler();

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        // Usamos ObjectInputStream y ObjectOutputStream para serializar/deserializar objetos
        try (
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())
        ) {
            // Se debe escribir la primera "firma" o un objeto inicial para que el ObjectInputStream
            // del cliente pueda inicializarse correctamente
            // Una opción es flush y resetear el stream al inicio, o enviar un objeto nulo si no se espera nada de inicio
            // Para flujos de requests/responses, el flush y reset después de cada objeto enviado suele ser importante

            while (true) { // Bucle continuo para manejar múltiples solicitudes
                Request req = null;
                try {
                    // Lee un objeto Request del stream
                    req = (Request) in.readObject();
                    logger.debug("Recibido del cliente {}: {}", socket.getInetAddress(), req);

                    // Procesa la solicitud con el handler
                    Response res = handler.handle(req);

                    // Envía la respuesta al cliente
                    out.writeObject(res);
                    out.flush(); // Asegura que los datos se envíen de inmediato
                    out.reset(); // Importante para limpiar el cache del stream y enviar objetos actualizados
                    // (evita enviar la misma instancia de objeto si sus contenidos cambian)
                    logger.debug("Enviado al cliente {}: {}", socket.getInetAddress(), res);

                } catch (EOFException e) {
                    // El cliente cerró su stream de salida o la conexión.
                    logger.info("Cliente {} cerró la conexión.", socket.getInetAddress());
                    break; // Sale del bucle while
                } catch (ClassNotFoundException e) {
                    // Error si la clase de un objeto serializado no se encuentra en el classpath del servidor
                    logger.error("Clase no encontrada al deserializar objeto del cliente {}: {}", socket.getInetAddress(), e.getMessage());
                    Response errorResponse = new Response("ERROR", "Clase de objeto desconocida: " + e.getMessage(), null);
                    out.writeObject(errorResponse);
                    out.flush();
                    out.reset();
                } catch (InvalidClassException e) {
                    // Error de versión de serialización
                    logger.error("Versión de clase inválida al deserializar objeto del cliente {}: {}", socket.getInetAddress(), e.getMessage());
                    Response errorResponse = new Response("ERROR", "Versión de objeto incompatible: " + e.getMessage(), null);
                    out.writeObject(errorResponse);
                    out.flush();
                    out.reset();
                } catch (IOException e) {
                    // Otros errores de E/S (ej. conexión reseteada por el cliente)
                    logger.error("Error de E/S con cliente {}: {}", socket.getInetAddress(), e.getMessage());
                    break; // Sale del bucle por error en la conexión
                } catch (Exception e) {
                    // Captura cualquier otra excepción inesperada del ProtocolHandler o de la lógica de negocio
                    logger.error("Error inesperado al procesar la solicitud del cliente {}: {}", socket.getInetAddress(), e.getMessage(), e);
                    Response errorResponse = new Response("ERROR", "Error interno del servidor: " + e.getMessage(), null);
                    out.writeObject(errorResponse);
                    out.flush();
                    out.reset();
                }
            }
        } catch (IOException e) {
            // Esto ocurre si hay un problema al establecer los streams inicialmente (ej. socket ya cerrado)
            logger.error("Error al establecer streams para cliente {}: {}", socket.getInetAddress(), e.getMessage());
        } finally {
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                    logger.info("Socket del cliente {} cerrado.", socket.getInetAddress());
                }
            } catch (IOException e) {
                logger.error("Error al cerrar el socket del cliente {}: {}", socket.getInetAddress(), e.getMessage());
            }
        }
    }
}


