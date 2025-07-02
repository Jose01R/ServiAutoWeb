package service;

import dataManager.ClienteXmlData;
import domain.Cliente;
import org.jdom2.JDOMException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ClienteService {
    private final ClienteXmlData clienteXmlData;

    // Constructor que recibe la ruta del archivo XML
    public ClienteService(String rutaArchivo) throws IOException, JDOMException {
        this.clienteXmlData = ClienteXmlData.abrirDocumento(rutaArchivo);
    }

    public void agregarCliente(Cliente cliente) throws IOException {

        if (clienteXmlData != null) {
            clienteXmlData.insertarCliente(cliente);
        }
    }

    public List<Cliente> obtenerTodosClientes() {
        return clienteXmlData.getTodosClientes();
    }

    public Optional<Cliente> buscarClientePorId(String idCliente) {
        return clienteXmlData.getClientePorId(idCliente);
    }

    public boolean actualizarCliente(Cliente cliente) throws IOException {

        return clienteXmlData.actualizarCliente(cliente);
    }

    public boolean eliminarCliente(String idCliente) throws IOException {

        return clienteXmlData.eliminarCliente(idCliente);
    }

    public List<Cliente> buscarClientesPorIdONombre(String query) {
        return clienteXmlData.buscarClientesPorIdONombre(query);
    }
}
