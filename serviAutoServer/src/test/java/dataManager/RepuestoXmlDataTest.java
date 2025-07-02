package dataManager;

import static dataManager.RepuestoXmlData.abrirDocumento;
import static org.junit.jupiter.api.Assertions.*;

import domain.Repuesto;
import org.jdom2.JDOMException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class RepuestoXmlDataTest {

    private static final String TEST_XML_FILE = "test_repuestos.xml";
    private RepuestoXmlData repuestoXmlData;
    private String PATH = "C:\\Users\\Lexis\\Desktop\\Proyecto\\test_repuestos.xml";

//    @BeforeEach
//    void setUp() throws IOException {
//        File file = new File(TEST_XML_FILE);
//        if (file.exists()) {
//            file.delete();
//        }
//        repuestoXmlData = new RepuestoXmlData(TEST_XML_FILE, "repuestos");
//    }

    @BeforeEach
    void init(){
        try{
            File testFile = new File(PATH);
            // Asegúrate de que el archivo no exista antes de cada prueba
            if (testFile.exists()) {
                testFile.delete();
            }
            // Abre un nuevo documento para cada prueba
            repuestoXmlData = abrirDocumento(PATH);
        }catch (IOException | JDOMException e) {
            // Manejo de excepciones más específico si es necesario
            fail("Error al inicializar la prueba: " + e.getMessage());
        }
    }

//    @AfterEach
//    void tearDown() {
//        File file = new File(PATH);
//        if (file.exists()) {
//            file.delete();
//        }
//    }

    @Test
    void testInsertarRepuesto() throws IOException {
        Repuesto repuesto = new Repuesto("Filtro de Aire", 25.50, 10, false);
        repuestoXmlData.insertarRepuesto(repuesto);

        Optional<Repuesto> foundRepuesto = repuestoXmlData.getRepuestoPorNombre("Filtro de Aire");
        assertTrue(foundRepuesto.isPresent(), "El repuesto insertado debería ser encontrado.");
        assertEquals(25.50, foundRepuesto.get().getPrecio(), 0.001, "El precio debe coincidir.");
    }

    @Test
    void testInsertarRepuestoExistente() throws IOException {
        Repuesto repuesto1 = new Repuesto("Filtro de Aire", 25.50, 10, false);
        repuestoXmlData.insertarRepuesto(repuesto1);

        Repuesto repuesto2 = new Repuesto("Filtro de Aire", 30.00, 5, true);
        repuestoXmlData.insertarRepuesto(repuesto2); // Intentar insertar el mismo nombre

        List<Repuesto> repuestos = repuestoXmlData.getTodosRepuestos();
        assertEquals(1, repuestos.size(), "Solo debería haber un repuesto con el mismo nombre.");
        assertEquals(25.50, repuestos.get(0).getPrecio(), 0.001, "El precio del primer repuesto insertado debería persistir.");
    }

    @Test
    void testGetTodosRepuestos() throws IOException {
        repuestoXmlData.insertarRepuesto(new Repuesto("Filtro de Aire", 25.50, 10, false));
        repuestoXmlData.insertarRepuesto(new Repuesto("Bujía", 5.00, 50, true));

        List<Repuesto> repuestos = repuestoXmlData.getTodosRepuestos();
        assertNotNull(repuestos, "La lista de repuestos no debe ser nula.");
        assertEquals(2, repuestos.size(), "Debería haber 2 repuestos.");
    }

    @Test
    void testGetRepuestoPorNombreNoExistente() throws IOException {
        Optional<Repuesto> foundRepuesto = repuestoXmlData.getRepuestoPorNombre("Repuesto Inexistente");
        assertFalse(foundRepuesto.isPresent(), "No se debería encontrar un repuesto con un nombre inexistente.");
    }

    @Test
    void testActualizarRepuesto() throws IOException {
        Repuesto repuesto = new Repuesto("Filtro de Aire", 25.50, 10, false);
        repuestoXmlData.insertarRepuesto(repuesto);

        Repuesto repuestoActualizado = new Repuesto("Filtro de Aire", 28.00, 8, true);
        boolean updated = repuestoXmlData.actualizarRepuesto(repuestoActualizado);

        assertTrue(updated, "El repuesto debería ser actualizado.");
        Optional<Repuesto> foundRepuesto = repuestoXmlData.getRepuestoPorNombre("Filtro de Aire");
        assertTrue(foundRepuesto.isPresent(), "El repuesto actualizado debería ser encontrado.");
        assertEquals(28.00, foundRepuesto.get().getPrecio(), 0.001, "El precio del repuesto debe haber sido actualizado.");
        assertEquals(8, foundRepuesto.get().getCantidad(), "La cantidad del repuesto debe haber sido actualizada.");
        assertTrue(foundRepuesto.get().isPedido(), "El estado 'pedido' del repuesto debe haber sido actualizado.");
    }

    @Test
    void testActualizarRepuestoNoExistente() throws IOException {
        Repuesto repuestoActualizado = new Repuesto("Repuesto Inexistente", 10.0, 5, false);
        boolean updated = repuestoXmlData.actualizarRepuesto(repuestoActualizado);
        assertFalse(updated, "No se debería poder actualizar un repuesto que no existe.");
    }

    @Test
    void testEliminarRepuesto() throws IOException {
        Repuesto repuesto = new Repuesto("Filtro de Aire", 25.50, 10, false);
        repuestoXmlData.insertarRepuesto(repuesto);

        boolean deleted = repuestoXmlData.eliminarRepuesto("Filtro de Aire");
        assertTrue(deleted, "El repuesto debería ser eliminado.");

        Optional<Repuesto> foundRepuesto = repuestoXmlData.getRepuestoPorNombre("Filtro de Aire");
        assertFalse(foundRepuesto.isPresent(), "El repuesto eliminado no debería ser encontrado.");
    }

    @Test
    void testEliminarRepuestoNoExistente() throws IOException {
        boolean deleted = repuestoXmlData.eliminarRepuesto("Repuesto Inexistente");
        assertFalse(deleted, "No se debería poder eliminar un repuesto que no existe.");
    }
}
