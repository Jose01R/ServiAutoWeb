package dataManager;

import static org.junit.jupiter.api.Assertions.*;

import domain.Cliente;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteXmlDataTest {

    private static final String TEST_XML_FILE = "test_clientes.xml";
    private ClienteXmlData clienteXmlData;

    @BeforeEach
    void setUp() throws IOException {
        // Asegurarse de que el archivo no exista al inicio de cada test
        File file = new File(TEST_XML_FILE);
        if (file.exists()) {
            file.delete();
        }
        clienteXmlData = new ClienteXmlData(TEST_XML_FILE, "clientes");
    }

    @AfterEach
    void tearDown() {
        // Eliminar el archivo XML después de cada test
        File file = new File(TEST_XML_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testInsertarCliente() throws IOException {
        Cliente cliente = new Cliente("C001", "Juan", "Perez", "Gomez", "1234567", "7890123", "Calle Falsa 123", "juan@example.com");
        clienteXmlData.insertarCliente(cliente);

        Optional<Cliente> foundCliente = clienteXmlData.getClientePorId("C001");
        assertTrue(foundCliente.isPresent(), "El cliente insertado debería ser encontrado.");
        assertEquals("Juan", foundCliente.get().getNombre(), "El nombre del cliente debe coincidir.");
    }

    @Test
    void testInsertarClienteExistente() throws IOException {
        Cliente cliente1 = new Cliente("C001", "Juan", "Perez", "Gomez", "1234567", "7890123", "Calle Falsa 123", "juan@example.com");
        clienteXmlData.insertarCliente(cliente1);

        Cliente cliente2 = new Cliente("C001", "Pedro", "Gomez", "Silva", "7654321", "9876543", "Av. Siempre Viva 456", "pedro@example.com");
        clienteXmlData.insertarCliente(cliente2); // Intentar insertar el mismo ID

        List<Cliente> clientes = clienteXmlData.getTodosClientes();
        assertEquals(1, clientes.size(), "Solo debería haber un cliente con el mismo ID.");
        assertEquals("Juan", clientes.get(0).getNombre(), "El primer cliente insertado debería persistir.");
    }

    @Test
    void testGetTodosClientes() throws IOException {
        clienteXmlData.insertarCliente(new Cliente("C001", "Juan", "Perez", "Gomez", "123", "456", "Dir1", "j@e.com"));
        clienteXmlData.insertarCliente(new Cliente("C002", "Maria", "Lopez", "Diaz", "789", "012", "Dir2", "m@e.com"));

        List<Cliente> clientes = clienteXmlData.getTodosClientes();
        assertNotNull(clientes, "La lista de clientes no debe ser nula.");
        assertEquals(2, clientes.size(), "Debería haber 2 clientes.");
    }

    @Test
    void testGetClientePorIdNoExistente() throws IOException {
        Optional<Cliente> foundCliente = clienteXmlData.getClientePorId("C999");
        assertFalse(foundCliente.isPresent(), "No se debería encontrar un cliente con un ID inexistente.");
    }

    @Test
    void testActualizarCliente() throws IOException {
        Cliente cliente = new Cliente("C001", "Juan", "Perez", "Gomez", "123", "456", "Dir1", "j@e.com");
        clienteXmlData.insertarCliente(cliente);

        Cliente clienteActualizado = new Cliente("C001", "Juan Actualizado", "Perez", "Gomez", "123", "456", "Nueva Direccion", "juan.updated@example.com");
        boolean updated = clienteXmlData.actualizarCliente(clienteActualizado);

        assertTrue(updated, "El cliente debería ser actualizado.");
        Optional<Cliente> foundCliente = clienteXmlData.getClientePorId("C001");
        assertTrue(foundCliente.isPresent(), "El cliente actualizado debería ser encontrado.");
        assertEquals("Juan Actualizado", foundCliente.get().getNombre(), "El nombre del cliente debe haber sido actualizado.");
        assertEquals("Nueva Direccion", foundCliente.get().getDireccion(), "La dirección del cliente debe haber sido actualizada.");
    }

    @Test
    void testActualizarClienteNoExistente() throws IOException {
        Cliente clienteActualizado = new Cliente("C999", "No Existe", "Apellido", "Otro", "000", "000", "Dir", "n@e.com");
        boolean updated = clienteXmlData.actualizarCliente(clienteActualizado);
        assertFalse(updated, "No se debería poder actualizar un cliente que no existe.");
    }

    @Test
    void testEliminarCliente() throws IOException {
        Cliente cliente = new Cliente("C001", "Juan", "Perez", "Gomez", "123", "456", "Dir1", "j@e.com");
        clienteXmlData.insertarCliente(cliente);

        boolean deleted = clienteXmlData.eliminarCliente("C001");
        assertTrue(deleted, "El cliente debería ser eliminado.");

        Optional<Cliente> foundCliente = clienteXmlData.getClientePorId("C001");
        assertFalse(foundCliente.isPresent(), "El cliente eliminado no debería ser encontrado.");
    }

    @Test
    void testEliminarClienteNoExistente() throws IOException {
        boolean deleted = clienteXmlData.eliminarCliente("C999");
        assertFalse(deleted, "No se debería poder eliminar un cliente que no existe.");
    }
}