package dataManager;

import static org.junit.jupiter.api.Assertions.*;

import domain.Servicio;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ServicioXmlDataTest {

    private static final String TEST_XML_FILE = "test_servicios.xml";
    private ServicioXmlData servicioXmlData;

    @BeforeEach
    void setUp() throws IOException {
        File file = new File(TEST_XML_FILE);
        if (file.exists()) {
            file.delete();
        }
        servicioXmlData = new ServicioXmlData(TEST_XML_FILE, "servicios");
    }

    @AfterEach
    void tearDown() {
        File file = new File(TEST_XML_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testInsertarServicio() throws IOException {
        Servicio servicio = new Servicio("Cambio de Frenos", 150.0, 50.0);
        servicioXmlData.insertarServicio(servicio);

        Optional<Servicio> foundServicio = servicioXmlData.getServicioPorNombre("Cambio de Frenos");
        assertTrue(foundServicio.isPresent(), "El servicio insertado debería ser encontrado.");
        assertEquals(150.0, foundServicio.get().getPrecio(), 0.001, "El precio debe coincidir.");
    }

    @Test
    void testInsertarServicioExistente() throws IOException {
        Servicio servicio1 = new Servicio("Cambio de Frenos", 150.0, 50.0);
        servicioXmlData.insertarServicio(servicio1);

        Servicio servicio2 = new Servicio("Cambio de Frenos", 180.0, 60.0);
        servicioXmlData.insertarServicio(servicio2); // Intentar insertar el mismo nombre

        List<Servicio> servicios = servicioXmlData.getTodosServicios();
        assertEquals(1, servicios.size(), "Solo debería haber un servicio con el mismo nombre.");
        assertEquals(150.0, servicios.get(0).getPrecio(), 0.001, "El precio del primer servicio insertado debería persistir.");
    }

    @Test
    void testGetTodosServicios() throws IOException {
        servicioXmlData.insertarServicio(new Servicio("Revision", 50.0, 20.0));
        servicioXmlData.insertarServicio(new Servicio("Diagnostico", 80.0, 30.0));

        List<Servicio> servicios = servicioXmlData.getTodosServicios();
        assertNotNull(servicios, "La lista de servicios no debe ser nula.");
        assertEquals(2, servicios.size(), "Debería haber 2 servicios.");
    }

    @Test
    void testGetServicioPorNombreNoExistente() throws IOException {
        Optional<Servicio> foundServicio = servicioXmlData.getServicioPorNombre("Servicio Inexistente");
        assertFalse(foundServicio.isPresent(), "No se debería encontrar un servicio con un nombre inexistente.");
    }

    @Test
    void testActualizarServicio() throws IOException {
        Servicio servicio = new Servicio("Cambio de Frenos", 150.0, 50.0);
        servicioXmlData.insertarServicio(servicio);

        Servicio servicioActualizado = new Servicio("Cambio de Frenos", 175.0, 55.0);
        boolean updated = servicioXmlData.actualizarServicio(servicioActualizado);

        assertTrue(updated, "El servicio debería ser actualizado.");
        Optional<Servicio> foundServicio = servicioXmlData.getServicioPorNombre("Cambio de Frenos");
        assertTrue(foundServicio.isPresent(), "El servicio actualizado debería ser encontrado.");
        assertEquals(175.0, foundServicio.get().getPrecio(), 0.001, "El precio del servicio debe haber sido actualizado.");
        assertEquals(55.0, foundServicio.get().getCostoManoObra(), 0.001, "El costo de mano de obra debe haber sido actualizado.");
    }

    @Test
    void testActualizarServicioNoExistente() throws IOException {
        Servicio servicioActualizado = new Servicio("Servicio Inexistente", 100.0, 30.0);
        boolean updated = servicioXmlData.actualizarServicio(servicioActualizado);
        assertFalse(updated, "No se debería poder actualizar un servicio que no existe.");
    }

    @Test
    void testEliminarServicio() throws IOException {
        Servicio servicio = new Servicio("Cambio de Frenos", 150.0, 50.0);
        servicioXmlData.insertarServicio(servicio);

        boolean deleted = servicioXmlData.eliminarServicio("Cambio de Frenos");
        assertTrue(deleted, "El servicio debería ser eliminado.");

        Optional<Servicio> foundServicio = servicioXmlData.getServicioPorNombre("Cambio de Frenos");
        assertFalse(foundServicio.isPresent(), "El servicio eliminado no debería ser encontrado.");
    }

    @Test
    void testEliminarServicioNoExistente() throws IOException {
        boolean deleted = servicioXmlData.eliminarServicio("Servicio Inexistente");
        assertFalse(deleted, "No se debería poder eliminar un servicio que no existe.");
    }
}
