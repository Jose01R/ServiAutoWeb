package dataManager;

import static org.junit.jupiter.api.Assertions.*;

import domain.DetalleOrden;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class DetalleOrdenXmlDataTest {

    private static final String TEST_XML_FILE = "test_detallesOrden.xml";
    private DetalleOrdenXmlData detalleOrdenXmlData;

    @BeforeEach
    void setUp() throws IOException {
        File file = new File(TEST_XML_FILE);
        if (file.exists()) {
            file.delete();
        }
        detalleOrdenXmlData = new DetalleOrdenXmlData(TEST_XML_FILE, "detallesOrden");
    }

    @AfterEach
    void tearDown() {
        File file = new File(TEST_XML_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testInsertarDetalleOrdenConServicio() throws IOException {
        DetalleOrden detalle = new DetalleOrden("D001", 1, "Servicio de pulido", "Servicio", "Completo");
        String idOrdenTrabajo = "OT001";
        String nombreServicio = "Pulido Exterior";

        detalleOrdenXmlData.insertarDetalleOrden(detalle, idOrdenTrabajo, nombreServicio, null);

        Optional<DetalleOrden> foundDetalle = detalleOrdenXmlData.getDetalleOrdenPorId("D001");
        assertTrue(foundDetalle.isPresent(), "El detalle de orden insertado debería ser encontrado.");
        assertEquals(1, foundDetalle.get().getCantidad(), "La cantidad debe coincidir.");
        assertEquals("Servicio", foundDetalle.get().getTipoDetalle(), "El tipo de detalle debe ser Servicio.");
        // Nota: Para verificar el servicio/repuesto asociado, tendrías que cargar el XML y
        // verificar el nodo XML directamente, o tener un método en DetalleOrden que devuelva el nombre del servicio/repuesto.
        // Aquí solo confirmamos que el detalle base se guardó.
    }

    @Test
    void testInsertarDetalleOrdenConRepuesto() throws IOException {
        DetalleOrden detalle = new DetalleOrden("D002", 2, "Reemplazo de llantas", "Repuesto", "Pendiente");
        String idOrdenTrabajo = "OT001";
        String nombreRepuesto = "Llanta Michelin";

        detalleOrdenXmlData.insertarDetalleOrden(detalle, idOrdenTrabajo, null, nombreRepuesto);

        Optional<DetalleOrden> foundDetalle = detalleOrdenXmlData.getDetalleOrdenPorId("D002");
        assertTrue(foundDetalle.isPresent(), "El detalle de orden insertado debería ser encontrado.");
        assertEquals(2, foundDetalle.get().getCantidad(), "La cantidad debe coincidir.");
        assertEquals("Repuesto", foundDetalle.get().getTipoDetalle(), "El tipo de detalle debe ser Repuesto.");
    }

    @Test
    void testInsertarDetalleOrdenConAmbosServicioYRepuestoDebeLanzarExcepcion() throws IOException {
        DetalleOrden detalle = new DetalleOrden("D003", 1, "Error", "Mixto", "Error");
        String idOrdenTrabajo = "OT001";
        String nombreServicio = "Servicio";
        String nombreRepuesto = "Repuesto";

        assertThrows(IllegalArgumentException.class, () -> {
            detalleOrdenXmlData.insertarDetalleOrden(detalle, idOrdenTrabajo, nombreServicio, nombreRepuesto);
        }, "Debe lanzar una excepción si se intenta insertar con servicio y repuesto.");
    }

    @Test
    void testInsertarDetalleOrdenExistente() throws IOException {
        DetalleOrden detalle1 = new DetalleOrden("D001", 1, "Observacion 1", "Servicio", "Estado1");
        detalleOrdenXmlData.insertarDetalleOrden(detalle1, "OT001", "Servicio A", null);

        DetalleOrden detalle2 = new DetalleOrden("D001", 2, "Observacion 2", "Repuesto", "Estado2");
        detalleOrdenXmlData.insertarDetalleOrden(detalle2, "OT002", null, "Repuesto B"); // Intentar insertar el mismo ID

        List<DetalleOrden> detalles = detalleOrdenXmlData.getTodosDetallesOrden();
        assertEquals(1, detalles.size(), "Solo debería haber un detalle de orden con el mismo ID.");
        assertEquals(1, detalles.get(0).getCantidad(), "El primer detalle insertado debería persistir.");
    }

    @Test
    void testGetTodosDetallesOrden() throws IOException {
        detalleOrdenXmlData.insertarDetalleOrden(new DetalleOrden("D001", 1, "Obs1", "Servicio", "Est1"), "OT001", "Servicio X", null);
        detalleOrdenXmlData.insertarDetalleOrden(new DetalleOrden("D002", 2, "Obs2", "Repuesto", "Est2"), "OT001", null, "Repuesto Y");

        List<DetalleOrden> detalles = detalleOrdenXmlData.getTodosDetallesOrden();
        assertNotNull(detalles, "La lista de detalles no debe ser nula.");
        assertEquals(2, detalles.size(), "Debería haber 2 detalles de orden.");
    }

    @Test
    void testGetDetalleOrdenPorIdNoExistente() throws IOException {
        Optional<DetalleOrden> foundDetalle = detalleOrdenXmlData.getDetalleOrdenPorId("D999");
        assertFalse(foundDetalle.isPresent(), "No se debería encontrar un detalle de orden con un ID inexistente.");
    }

    @Test
    void testActualizarDetalleOrden() throws IOException {
        DetalleOrden detalle = new DetalleOrden("D001", 1, "Observacion inicial", "Servicio", "Pendiente");
        detalleOrdenXmlData.insertarDetalleOrden(detalle, "OT001", "Servicio A", null);

        DetalleOrden detalleActualizado = new DetalleOrden("D001", 3, "Observacion actualizada", "Servicio", "Completado");
        boolean updated = detalleOrdenXmlData.actualizarDetalleOrden(detalleActualizado);

        assertTrue(updated, "El detalle de orden debería ser actualizado.");
        Optional<DetalleOrden> foundDetalle = detalleOrdenXmlData.getDetalleOrdenPorId("D001");
        assertTrue(foundDetalle.isPresent(), "El detalle actualizado debería ser encontrado.");
        assertEquals(3, foundDetalle.get().getCantidad(), "La cantidad debe haber sido actualizada.");
        assertEquals("Observacion actualizada", foundDetalle.get().getObservaciones(), "Las observaciones deben haber sido actualizadas.");
        assertEquals("Completado", foundDetalle.get().getIdEstado(), "El estado debe haber sido actualizado.");
    }

    @Test
    void testActualizarDetalleOrdenNoExistente() throws IOException {
        DetalleOrden detalleActualizado = new DetalleOrden("D999", 10, "No existe", "N/A", "N/A");
        boolean updated = detalleOrdenXmlData.actualizarDetalleOrden(detalleActualizado);
        assertFalse(updated, "No se debería poder actualizar un detalle de orden que no existe.");
    }

    @Test
    void testEliminarDetalleOrden() throws IOException {
        DetalleOrden detalle = new DetalleOrden("D001", 1, "Observacion", "Servicio", "Estado");
        detalleOrdenXmlData.insertarDetalleOrden(detalle, "OT001", "Servicio A", null);

        boolean deleted = detalleOrdenXmlData.eliminarDetalleOrden("D001");
        assertTrue(deleted, "El detalle de orden debería ser eliminado.");

        Optional<DetalleOrden> foundDetalle = detalleOrdenXmlData.getDetalleOrdenPorId("D001");
        assertFalse(foundDetalle.isPresent(), "El detalle de orden eliminado no debería ser encontrado.");
    }

    @Test
    void testEliminarDetalleOrdenNoExistente() throws IOException {
        boolean deleted = detalleOrdenXmlData.eliminarDetalleOrden("D999");
        assertFalse(deleted, "No se debería poder eliminar un detalle de orden que no existe.");
    }
}
