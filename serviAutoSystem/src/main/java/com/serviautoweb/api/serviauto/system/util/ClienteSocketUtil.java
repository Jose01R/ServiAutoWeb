package com.serviautoweb.api.serviauto.system.util;

import domain.Request;
import domain.Response;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClienteSocketUtil {
    public static Response enviarRequestAlServidor(Request req) {
        try (
                Socket socket = new Socket("192.168.50.130", 5000); // IP y puerto del servidor
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {
            // Enviar el objeto Request al servidor
            out.writeObject(req);
            out.flush();
            out.reset(); // Limpia el caché de objetos serializados

            // Esperar y leer la respuesta del servidor
            Response resp = (Response) in.readObject();
            return resp;
        } catch (Exception e) {
            // Si ocurre un error, devolver un Response con el mensaje de error
            return new Response("500", "Error de comunicación: " + e.getMessage(), null);
        }
    }
}
