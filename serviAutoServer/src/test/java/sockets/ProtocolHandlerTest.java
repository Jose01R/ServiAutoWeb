package sockets;

import domain.*;
import org.junit.jupiter.api.Test;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ProtocolHandlerTest {

    ProtocolHandler protocolHandler = new ProtocolHandler();
    @Test
    void test_insertar_cliente_funciona() {
        try (Socket socket = new Socket("192.168.18.61", 5000)) { // Cambia el puerto si es necesario
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Crear cliente de prueba
            Cliente cliente = new Cliente("123", "Juan", "Perez", "juan@mail.com", "8888-8888","Calle Falsa 123", "5555-5555","jared@gmail.com");
            Request req = new Request("insertarCliente", cliente);

            // Enviar request
            out.writeObject(req);

            // Recibir response
            Response resp = (Response) in.readObject();
            System.out.println("Código: " + resp.getStatus());
            System.out.println("Mensaje: " + resp.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    void insertar_detalleOrden_funciona() {

       // crear el objeto DetalleOrden
        DetalleOrden detalle = new DetalleOrden("DET001", 22,"bonita", "limpieza", "completado");

        Map<String, Object> datos = new HashMap<>();
        datos.put("detalleOrden", detalle);
        datos.put("idOrdenTrabajo", "ORD123");
        datos.put("nombreServicio", "Lavado");// Si es servicio, repuesto debe ir null
        datos.put("nombreRepuesto", null);

        Request req = new Request("agregarDetalleOrden", datos);
        // Ejecutar el handler
        Response resp = protocolHandler.handle(req);

        // Verificar la respuesta
        assertEquals("200", resp.getStatus(), "Debe insertar correctamente el detalle de orden");
        assertEquals("Detalle de orden insertado correctamente", resp.getMessage());
    }

    @Test
    void insertar_ordenTrabajo_funciona() {

        ProtocolHandler handler = new ProtocolHandler();
        Date fecha = new Date();

        // Crear el objeto OrdenTrabajo
        OrdenTrabajo orden = new OrdenTrabajo("ORD001","arreglo", fecha, "pendiente");

        // Armar el Map con datos requeridos por el protocolo
        Map<String, Object> datos = new HashMap<>();
        datos.put("ordenTrabajo", orden);
        datos.put("placaVehiculo", "ABC123");

        // Armar el request del protocolo
        Request req = new Request("agregarOrdenTrabajo", datos);

        // Ejecutar el handler
        Response resp = handler.handle(req);

      //assert
        assertEquals("200", resp.getStatus(), "La orden de trabajo debería insertarse correctamente.");
        assertEquals("Orden de trabajo insertada correctamente", resp.getMessage());
    }
}