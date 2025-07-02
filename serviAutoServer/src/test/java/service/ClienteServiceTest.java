package service;

import domain.Cliente;
import org.jdom2.JDOMException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ClienteServiceTest {
    @Test
    void testInsertarCliente() {

        Cliente cliente = new Cliente("C001", "Juan", "Perez", "Gomez",
                "1234567", "7890123", "Calle Falsa 123", "jared@gmail.com");
        try {
            ClienteService clienteService= new ClienteService("C:\\Users\\Lexis\\Desktop\\Proyecto\\clientes.xml");
            clienteService.agregarCliente(cliente);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JDOMException e) {
            throw new RuntimeException(e);
        }

    }
}