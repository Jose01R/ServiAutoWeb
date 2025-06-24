package sockets;

import domain.Cliente;
import domain.Request;
import domain.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTestServer {

    private static final int TEST_PORT = 5000; // Usar un puerto diferente para evitar conflictos
    private ServiAutoServer server;
    private Thread serverThread;
    private CountDownLatch serverStartedLatch;

    @BeforeEach
    void setUp() {
        serverStartedLatch = new CountDownLatch(1);
        server = new ServiAutoServer(); // Pasa el CountDownLatch
        serverThread = new Thread(() -> server.start()); // Pasa el puerto de prueba
        serverThread.start(); // Inicia el servidor en un hilo separado
    }

    @AfterEach
    void tearDown() throws InterruptedException {
        if (server != null) {
            server.stop(); // Detener el servidor
        }
        if (serverThread != null && serverThread.isAlive()) {
            serverThread.join(5000); // Esperar hasta 5 segundos para que el hilo del servidor termine
            if (serverThread.isAlive()) {
                System.err.println("Advertencia: El hilo del servidor no terminó a tiempo.");
                serverThread.interrupt(); // Intentar interrumpir si aún está vivo
            }
        }
    }

    @Test
    void testTestRequest() throws IOException, ClassNotFoundException, InterruptedException {
        // Esperar a que el servidor indique que ha iniciado
        //assertTrue(serverStartedLatch.await(10, TimeUnit.SECONDS), "El servidor no se inició a tiempo.");

        try (
                Socket socket = new Socket("localhost", TEST_PORT);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {
            // --- Ejemplo 1: Enviar una solicitud de prueba simple ("test") ---
            Request testRequest = new Request("test", "Datos de prueba desde el cliente");

            System.out.println("Enviando Request (test): " + testRequest);
            out.writeObject(testRequest);
            out.flush();
            out.reset();

            Response testResponse = (Response) in.readObject();
            System.out.println("Respuesta del servidor (test):");
            System.out.println("  Status: " + testResponse.getStatus());
            System.out.println("  Message: " + testResponse.getMessage());
            System.out.println("  Data: " + testResponse.getData());
            System.out.println("------------------------------------------");

            // Validaciones con assertions
            assertNotNull(testResponse, "La respuesta no debe ser nula.");
            assertEquals("200", testResponse.getStatus(), "El status debe ser 200 (OK).");
            assertTrue(testResponse.getMessage().contains("Test OK"), "El mensaje debe indicar éxito.");
            assertNotNull(testResponse.getData(), "Los datos de respuesta no deben ser nulos.");
            assertEquals("Hello from server!", testResponse.getData(), "Los datos deben coincidir con la respuesta del stub.");

        }
    }




}