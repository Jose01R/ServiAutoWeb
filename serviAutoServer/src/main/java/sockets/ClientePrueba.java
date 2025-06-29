package sockets;

import domain.Cliente;
import domain.Request;
import domain.Response;

import java.io.*;
import java.net.Socket;

public class ClientePrueba {
    public static void main(String[] args) {
        String serverIp = "192.168.18.61";
        int serverPort = 5000;

        //Usamos ObjectOutputStream y ObjectInputStream para enviar y recibir objetos Java
        try (
                Socket socket = new Socket(serverIp, serverPort);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {
            System.out.println("Conectado al servidor en " + serverIp + ":" + serverPort);

            // --- Ejemplo 1: Enviar una solicitud de prueba simple ---
            // El campo 'data' puede ser null si la acción no requiere datos
            Request testRequest = new Request("test", "Datos de prueba desde el cliente");

            System.out.println("Enviando Request (test): " + testRequest);
            out.writeObject(testRequest); // Envía el objeto Request
            out.flush();
            out.reset();

            //Recibir la respuesta del servidor
            Response testResponse = (Response) in.readObject(); // Lee el objeto Response
            System.out.println("Respuesta del servidor (test):");
            System.out.println("  Status: " + testResponse.getStatus());
            System.out.println("  Message: " + testResponse.getMessage());
            System.out.println("  Data: " + testResponse.getData());
            System.out.println("------------------------------------------");



            // --- Ejemplo 3: Enviar una solicitud para obtener un cliente por ID ---
            String clienteIdToGet = "CLI001";
            Request getClienteRequest = new Request("GET_CLIENTE_BY_ID", clienteIdToGet);//action no existe

            System.out.println("Enviando Request (GET_CLIENTE_BY_ID): " + getClienteRequest); //action no existe
            out.writeObject(getClienteRequest);
            out.flush();
            out.reset();

            Response getClienteResponse = (Response) in.readObject();
            System.out.println("Respuesta del servidor (GET_CLIENTE_BY_ID):");
            System.out.println("  Status: " + getClienteResponse.getStatus());
            System.out.println("  Message: " + getClienteResponse.getMessage());
            System.out.println("  Data: " + getClienteResponse.getData());
            if (getClienteResponse.getData() instanceof Cliente) {
                Cliente clienteObtenido = (Cliente) getClienteResponse.getData();
                System.out.println("  Cliente obtenido: " + clienteObtenido.getNombre() + " (ID: " + clienteObtenido.getIdCliente() + ")");
            } else {
                System.out.println("  No se obtuvo un objeto Cliente."); //esperado
            }
            System.out.println("------------------------------------------");


        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se pudo encontrar la clase al deserializar el objeto. Asegúrate de que las clases de dominio estén en el classpath del cliente.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error de E/S al conectar o comunicar con el servidor. ¿Está el servidor corriendo en " + serverIp + ":" + serverPort + "?");
            e.printStackTrace();
        }
    }
}

