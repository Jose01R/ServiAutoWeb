package sockets;

import domain.*;
import org.junit.jupiter.api.Test;
import service.ServicioService;

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
        try (Socket socket = new Socket("192.168.1.174", 5000)) { // Cambia el puerto si es necesario
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Crear cliente de prueba
            Cliente cliente = new Cliente("123", "Juan", "Perez", "juan@mail.com", "8888-8888", "Calle Falsa 123", "5555-5555", "jared@gmail.com");
            Request req = new Request("agregarCliente", cliente);

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
        try (Socket socket = new Socket("192.168.18.61", 5000)) { // Cambia el puerto si es necesario
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // crear el objeto DetalleOrden
            DetalleOrden detalle = new DetalleOrden("DET001", 22, "bonita", "limpieza", "completado");

            Map<String, Object> datos = new HashMap<>();
            datos.put("detalleOrden", detalle);
            datos.put("idOrdenTrabajo", "ORD123");
            datos.put("nombreServicio", "Lavado");// Si es servicio, repuesto debe ir null
            datos.put("nombreRepuesto", null);

            Request req = new Request("agregarDetalleOrden", datos);

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
    void insertar_ordenTrabajo_funciona() {

        try (Socket socket = new Socket("192.168.18.61", 5000)) { // Cambia el puerto si es necesario
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            Date fecha = new Date();

            // Crear el objeto OrdenTrabajo
            OrdenTrabajo orden = new OrdenTrabajo("ORD001", "arreglo", fecha, "pendiente");

            // Armar el Map con datos requeridos por el protocolo
            Map<String, Object> datos = new HashMap<>();
            datos.put("ordenTrabajo", orden);
            datos.put("placaVehiculo", "ABC123");

            // Armar el request del protocolo
            Request req = new Request("agregarOrdenTrabajo", datos);

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
    void test_insertar_repuesto_funciona() {
        try (Socket socket = new Socket("192.168.18.61", 5000)) { // Cambia el puerto si es necesario
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Crear cliente de prueba
            Repuesto repuesto = new Repuesto("Llanta", 233, 2, true);
            Request req = new Request("agregarRepuesto", repuesto);

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
    void test_insertar_servicio_funciona() {
        try (Socket socket = new Socket("192.168.18.61", 5000)) { // Cambia el puerto si es necesario
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Crear cliente de prueba
            Servicio servicio = new Servicio("lavado", 100, 3242);
            Request req = new Request("agregarServicio", servicio);

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
    void insertar_vehiculo_funciona() {

        // Crear el objeto Vehiculo

        try (Socket socket = new Socket("192.168.18.61", 5000)) { // Cambia el puerto si es necesario
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Crear cliente de prueba
            Vehiculo vehiculo = new Vehiculo("ABC123", "Rojo", "Toyota", "Corolla", 2020, "2ads", 200);
            Map<String, Object> datos = new HashMap<>();
            datos.put("vehiculo", vehiculo);
            datos.put("idClienteDueno", "123"); // ID del cliente al que pertenece el vehículo
            Request req = new Request("agregarVehiculo", datos);

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
}