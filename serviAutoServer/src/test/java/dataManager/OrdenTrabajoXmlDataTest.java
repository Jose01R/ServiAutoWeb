package dataManager;

import static org.junit.jupiter.api.Assertions.*;

import domain.OrdenTrabajo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class OrdenTrabajoXmlDataTest {

    private static final String TEST_XML_FILE = "test_ordenesTrabajo.xml";
    private OrdenTrabajoXmlData ordenTrabajoXmlData;

    @BeforeEach
    void setUp() throws IOException {
        File file = new File(TEST_XML_FILE);
        if (file.exists()) {
            file.delete();
        }
        ordenTrabajoXmlData = new OrdenTrabajoXmlData(TEST_XML_FILE, "ordenesTrabajo");
    }

    @AfterEach
    void tearDown() {
        File file = new File(TEST_XML_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testInsertarOrdenTrabajo() throws IOException {
        Date fechaIngreso = new Date(); // Fecha actual
        OrdenTrabajo orden = new OrdenTrabajo("OT001", "Cambio de aceite", fechaIngreso, "Pendiente");
        String placaVehiculo = "ABC-123"; // Placa de un vehículo ficticio
        ordenTrabajoXmlData.insertarOrdenTrabajo(orden, placaVehiculo);

        Optional<OrdenTrabajo> foundOrden = ordenTrabajoXmlData.getOrdenTrabajoPorId("OT001");
        assertTrue(foundOrden.isPresent(), "La orden de trabajo insertada debería ser encontrada.");
        assertEquals("Cambio de aceite", foundOrden.get().getDescripcionSolicitud(), "La descripción debe coincidir.");
        assertEquals("Pendiente", foundOrden.get().getEstado(), "El estado debe coincidir.");
    }

    @Test
    void testInsertarOrdenTrabajoExistente() throws IOException {
        Date fechaIngreso = new Date();
        OrdenTrabajo orden1 = new OrdenTrabajo("OT001", "Cambio de aceite", fechaIngreso, "Pendiente");
        ordenTrabajoXmlData.insertarOrdenTrabajo(orden1, "ABC-123");

        OrdenTrabajo orden2 = new OrdenTrabajo("OT001", "Revisión frenos", fechaIngreso, "Completada");
        ordenTrabajoXmlData.insertarOrdenTrabajo(orden2, "XYZ-456"); // Intentar insertar el mismo ID

        List<OrdenTrabajo> ordenes = ordenTrabajoXmlData.getTodasOrdenesTrabajo();
        assertEquals(1, ordenes.size(), "Solo debería haber una orden con el mismo ID.");
        assertEquals("Cambio de aceite", ordenes.get(0).getDescripcionSolicitud(), "La primera orden insertada debería persistir.");
    }

    @Test
    void testGetTodasOrdenesTrabajo() throws IOException {
        Date fechaIngreso1 = new Date();
        OrdenTrabajo orden1 = new OrdenTrabajo("OT001", "Cambio de aceite", fechaIngreso1, "Pendiente");
        ordenTrabajoXmlData.insertarOrdenTrabajo(orden1, "ABC-123");

        Date fechaIngreso2 = new Date();
        OrdenTrabajo orden2 = new OrdenTrabajo("OT002", "Revisión frenos", fechaIngreso2, "En Proceso");
        ordenTrabajoXmlData.insertarOrdenTrabajo(orden2, "XYZ-456");

        List<OrdenTrabajo> ordenes = ordenTrabajoXmlData.getTodasOrdenesTrabajo();
        assertNotNull(ordenes, "La lista de órdenes no debe ser nula.");
        assertEquals(2, ordenes.size(), "Debería haber 2 órdenes de trabajo.");
    }

    @Test
    void testGetOrdenTrabajoPorIdNoExistente() throws IOException {
        Optional<OrdenTrabajo> foundOrden = ordenTrabajoXmlData.getOrdenTrabajoPorId("OT999");
        assertFalse(foundOrden.isPresent(), "No se debería encontrar una orden con un ID inexistente.");
    }

    @Test
    void testActualizarOrdenTrabajo() throws IOException {
        Date fechaIngreso = new Date();
        OrdenTrabajo orden = new OrdenTrabajo("OT001", "Cambio de aceite", fechaIngreso, "Pendiente");
        ordenTrabajoXmlData.insertarOrdenTrabajo(orden, "ABC-123");

        OrdenTrabajo ordenActualizada = new OrdenTrabajo("OT001", "Cambio de aceite y filtros", fechaIngreso, "Completada");
        ordenActualizada.setFechaDevolucion(new Date()); // Añadir fecha de devolución
        boolean updated = ordenTrabajoXmlData.actualizarOrdenTrabajo(ordenActualizada);

        assertTrue(updated, "La orden de trabajo debería ser actualizada.");
        Optional<OrdenTrabajo> foundOrden = ordenTrabajoXmlData.getOrdenTrabajoPorId("OT001");
        assertTrue(foundOrden.isPresent(), "La orden actualizada debería ser encontrada.");
        assertEquals("Cambio de aceite y filtros", foundOrden.get().getDescripcionSolicitud(), "La descripción debe haber sido actualizada.");
        assertEquals("Completada", foundOrden.get().getEstado(), "El estado debe haber sido actualizado.");
        assertNotNull(foundOrden.get().getFechaDevolucion(), "La fecha de devolución debería estar presente.");
    }

    @Test
    void testActualizarOrdenTrabajoEliminarFechaDevolucion() throws IOException {
        Date fechaIngreso = new Date();
        OrdenTrabajo orden = new OrdenTrabajo("OT002", "Revisión general", fechaIngreso, "Pendiente");
        orden.setFechaDevolucion(new Date()); // Establecer una fecha de devolución inicial
        ordenTrabajoXmlData.insertarOrdenTrabajo(orden, "XYZ-456");

        OrdenTrabajo ordenActualizada = new OrdenTrabajo("OT002", "Revisión general", fechaIngreso, "Finalizada");
        ordenActualizada.setFechaDevolucion(null); // Eliminar fecha de devolución
        boolean updated = ordenTrabajoXmlData.actualizarOrdenTrabajo(ordenActualizada);

        assertTrue(updated, "La orden de trabajo debería ser actualizada.");
        Optional<OrdenTrabajo> foundOrden = ordenTrabajoXmlData.getOrdenTrabajoPorId("OT002");
        assertTrue(foundOrden.isPresent(), "La orden actualizada debería ser encontrada.");
        assertNull(foundOrden.get().getFechaDevolucion(), "La fecha de devolución debería haber sido eliminada.");
    }


    @Test
    void testActualizarOrdenTrabajoNoExistente() throws IOException {
        Date fechaIngreso = new Date();
        OrdenTrabajo ordenActualizada = new OrdenTrabajo("OT999", "No Existe", fechaIngreso, "Error");
        boolean updated = ordenTrabajoXmlData.actualizarOrdenTrabajo(ordenActualizada);
        assertFalse(updated, "No se debería poder actualizar una orden que no existe.");
    }

    @Test
    void testEliminarOrdenTrabajo() throws IOException {
        Date fechaIngreso = new Date();
        OrdenTrabajo orden = new OrdenTrabajo("OT001", "Cambio de aceite", fechaIngreso, "Pendiente");
        ordenTrabajoXmlData.insertarOrdenTrabajo(orden, "ABC-123");

        boolean deleted = ordenTrabajoXmlData.eliminarOrdenTrabajo("OT001");
        assertTrue(deleted, "La orden de trabajo debería ser eliminada.");

        Optional<OrdenTrabajo> foundOrden = ordenTrabajoXmlData.getOrdenTrabajoPorId("OT001");
        assertFalse(foundOrden.isPresent(), "La orden de trabajo eliminada no debería ser encontrada.");
    }

    @Test
    void testEliminarOrdenTrabajoNoExistente() throws IOException {
        boolean deleted = ordenTrabajoXmlData.eliminarOrdenTrabajo("OT999");
        assertFalse(deleted, "No se debería poder eliminar una orden que no existe.");
    }
}