package dataManager;

import static org.junit.jupiter.api.Assertions.*;

import domain.Vehiculo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class VehiculoXmlDataTest {

    private static final String TEST_XML_FILE = "test_vehiculos.xml";
    private VehiculoXmlData vehiculoXmlData;

    @BeforeEach
    void setUp() throws IOException {
        File file = new File(TEST_XML_FILE);
        if (file.exists()) {
            file.delete();
        }
        vehiculoXmlData = new VehiculoXmlData(TEST_XML_FILE, "vehiculos");
    }

    @AfterEach
    void tearDown() {
        File file = new File(TEST_XML_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testInsertarVehiculo() throws IOException {
        Vehiculo vehiculo = new Vehiculo("ABC-123", "Rojo", "Toyota", "Sedan", 2020, "VIN123", 2.0);
        String idClienteDueno = "C001"; // ID de un cliente ficticio
        vehiculoXmlData.insertarVehiculo(vehiculo, idClienteDueno);

        Optional<Vehiculo> foundVehiculo = vehiculoXmlData.getVehiculoPorPlaca("ABC-123");
        assertTrue(foundVehiculo.isPresent(), "El vehículo insertado debería ser encontrado.");
        assertEquals("Toyota", foundVehiculo.get().getMarca(), "La marca del vehículo debe coincidir.");
    }

    @Test
    void testInsertarVehiculoExistente() throws IOException {
        Vehiculo vehiculo1 = new Vehiculo("ABC-123", "Rojo", "Toyota", "Sedan", 2020, "VIN123", 2.0);
        vehiculoXmlData.insertarVehiculo(vehiculo1, "C001");

        Vehiculo vehiculo2 = new Vehiculo("ABC-123", "Azul", "Honda", "SUV", 2022, "VIN456", 2.5);
        vehiculoXmlData.insertarVehiculo(vehiculo2, "C002"); // Intentar insertar la misma placa

        List<Vehiculo> vehiculos = vehiculoXmlData.getTodosVehiculos();
        assertEquals(1, vehiculos.size(), "Solo debería haber un vehículo con la misma placa.");
        assertEquals("Rojo", vehiculos.get(0).getColor(), "El primer vehículo insertado debería persistir.");
    }

    @Test
    void testGetTodosVehiculos() throws IOException {
        vehiculoXmlData.insertarVehiculo(new Vehiculo("ABC-123", "Rojo", "Toyota", "Sedan", 2020, "VIN1", 2.0), "C001");
        vehiculoXmlData.insertarVehiculo(new Vehiculo("XYZ-456", "Azul", "Honda", "SUV", 2022, "VIN2", 2.5), "C001");

        List<Vehiculo> vehiculos = vehiculoXmlData.getTodosVehiculos();
        assertNotNull(vehiculos, "La lista de vehículos no debe ser nula.");
        assertEquals(2, vehiculos.size(), "Debería haber 2 vehículos.");
    }

    @Test
    void testGetVehiculoPorPlacaNoExistente() throws IOException {
        Optional<Vehiculo> foundVehiculo = vehiculoXmlData.getVehiculoPorPlaca("XXX-999");
        assertFalse(foundVehiculo.isPresent(), "No se debería encontrar un vehículo con una placa inexistente.");
    }

    @Test
    void testActualizarVehiculo() throws IOException {
        Vehiculo vehiculo = new Vehiculo("ABC-123", "Rojo", "Toyota", "Sedan", 2020, "VIN123", 2.0);
        vehiculoXmlData.insertarVehiculo(vehiculo, "C001");

        Vehiculo vehiculoActualizado = new Vehiculo("ABC-123", "Verde", "Toyota", "Hatchback", 2021, "VIN123Updated", 1.8);
        boolean updated = vehiculoXmlData.actualizarVehiculo(vehiculoActualizado);

        assertTrue(updated, "El vehículo debería ser actualizado.");
        Optional<Vehiculo> foundVehiculo = vehiculoXmlData.getVehiculoPorPlaca("ABC-123");
        assertTrue(foundVehiculo.isPresent(), "El vehículo actualizado debería ser encontrado.");
        assertEquals("Verde", foundVehiculo.get().getColor(), "El color del vehículo debe haber sido actualizado.");
        assertEquals("Hatchback", foundVehiculo.get().getEstilo(), "El estilo del vehículo debe haber sido actualizado.");
        assertEquals(2021, foundVehiculo.get().getAnio(), "El año del vehículo debe haber sido actualizado.");
    }

    @Test
    void testActualizarVehiculoNoExistente() throws IOException {
        Vehiculo vehiculoActualizado = new Vehiculo("XXX-999", "Negro", "Ford", "Camioneta", 2018, "VIN000", 3.0);
        boolean updated = vehiculoXmlData.actualizarVehiculo(vehiculoActualizado);
        assertFalse(updated, "No se debería poder actualizar un vehículo que no existe.");
    }

    @Test
    void testEliminarVehiculo() throws IOException {
        Vehiculo vehiculo = new Vehiculo("ABC-123", "Rojo", "Toyota", "Sedan", 2020, "VIN123", 2.0);
        vehiculoXmlData.insertarVehiculo(vehiculo, "C001");

        boolean deleted = vehiculoXmlData.eliminarVehiculo("ABC-123");
        assertTrue(deleted, "El vehículo debería ser eliminado.");

        Optional<Vehiculo> foundVehiculo = vehiculoXmlData.getVehiculoPorPlaca("ABC-123");
        assertFalse(foundVehiculo.isPresent(), "El vehículo eliminado no debería ser encontrado.");
    }

    @Test
    void testEliminarVehiculoNoExistente() throws IOException {
        boolean deleted = vehiculoXmlData.eliminarVehiculo("XXX-999");
        assertFalse(deleted, "No se debería poder eliminar un vehículo que no existe.");
    }
}
