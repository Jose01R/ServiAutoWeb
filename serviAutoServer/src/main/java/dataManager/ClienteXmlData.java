package dataManager;

import domain.Cliente;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ClienteXmlData {
    private Document document;
    private Element raiz;
    private String rutaDocumento;
    private static final String ROOT_ELEMENT_NAME = "clientes";
    private static final String CLIENTE_ELEMENT_NAME = "cliente";

    /**
     * Constructor para crear un nuevo archivo XML si no existe.
     * @param rutaDocumento La ruta al archivo XML.
     * @param nombreRaiz El nombre del elemento raíz del XML.
     * @throws IOException Si ocurre un error de E/S.
     */
    public ClienteXmlData(String rutaDocumento, String nombreRaiz) throws IOException {
        this.rutaDocumento = rutaDocumento;
        this.raiz = new Element(nombreRaiz);
        this.document = new Document(raiz);
        guardar(); // Crea el archivo XML inicial
    }

    /**
     * Constructor para abrir un archivo XML existente.
     * @param rutaDocumento La ruta al archivo XML.
     * @throws JDOMException Si ocurre un error al parsear el XML.
     * @throws IOException Si ocurre un error de E/S.
     */
    private ClienteXmlData(String rutaDocumento) throws JDOMException, IOException {
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setIgnoringBoundaryWhitespace(true); // Ignorar espacios en blanco para un parsing más limpio

        this.document = saxBuilder.build(rutaDocumento);
        this.raiz = document.getRootElement();
        this.rutaDocumento = rutaDocumento;
    }

    /**
     * Método estático para abrir un documento ClienteXmlData.
     * Crea uno nuevo si no existe, o abre el existente.
     * @param rutaDocumento La ruta al archivo XML.
     * @return Una instancia de ClienteXmlData.
     * @throws JDOMException Si ocurre un error al parsear el XML.
     * @throws IOException Si ocurre un error de E/S.
     */
    public static ClienteXmlData abrirDocumento(String rutaDocumento) throws JDOMException, IOException {
        if (new File(rutaDocumento).exists()) {
            return new ClienteXmlData(rutaDocumento);
        } else {
            return new ClienteXmlData(rutaDocumento, ROOT_ELEMENT_NAME);
        }
    }

    /**
     * Guarda el documento XML en el archivo especificado.
     * @throws IOException Si ocurre un error de E/S.
     */
    private void guardar() throws IOException {
        Format format = Format.getPrettyFormat(); // Formato legible con indentación
        format.setEncoding("UTF-8"); // Codificación recomendada
        XMLOutputter xmlOutputter = new XMLOutputter(format);

        try (PrintWriter printWriter = new PrintWriter(this.rutaDocumento)) {
            xmlOutputter.output(this.document, printWriter);
        }

        // Opcional: imprimir en consola para depuración
        // xmlOutputter.output(this.document, System.out);
    }

    /**
     * Inserta un nuevo cliente en el documento XML.
     * @param cliente El objeto Cliente a insertar.
     * @throws IOException Si ocurre un error de E/S al guardar.
     */
    public void insertarCliente(Cliente cliente) throws IOException {
        if (getClientePorId(cliente.getIdCliente()).isPresent()) {
            System.out.println("Error: Ya existe un cliente con el ID '" + cliente.getIdCliente() + "'. No insertado.");
            return;
        }

        Element eCliente = new Element(CLIENTE_ELEMENT_NAME);
        eCliente.setAttribute("idCliente", cliente.getIdCliente());

        eCliente.addContent(new Element("nombre").setText(cliente.getNombre()));
        eCliente.addContent(new Element("primerApellido").setText(cliente.getPrimerApellido()));
        eCliente.addContent(new Element("segundoApellido").setText(cliente.getSegundoApellido()));
        eCliente.addContent(new Element("telefono").setText(cliente.getTelefono()));
        eCliente.addContent(new Element("celular").setText(cliente.getCelular()));
        eCliente.addContent(new Element("direccion").setText(cliente.getDireccion()));
        eCliente.addContent(new Element("email").setText(cliente.getEmail()));

        // Opcional: Si Cliente tuviera una lista de Vehiculos asociada directamente en el XML del cliente,
        // se añadiría aquí. Por ahora, asumimos que la relación se maneja desde Vehiculo.

        this.raiz.addContent(eCliente);
        guardar();
    }

    /**
     * Obtiene una lista de todos los clientes en el documento XML.
     * @return Una lista de objetos Cliente.
     */
    public List<Cliente> getTodosClientes() {
        List<Element> eListaClientes = raiz.getChildren(CLIENTE_ELEMENT_NAME);
        List<Cliente> clientes = new ArrayList<>();
        for (Element eCliente : eListaClientes) {
            Cliente clienteActual = new Cliente(
                    eCliente.getAttributeValue("idCliente"),
                    eCliente.getChildText("nombre"),
                    eCliente.getChildText("primerApellido"),
                    eCliente.getChildText("segundoApellido"),
                    eCliente.getChildText("telefono"),
                    eCliente.getChildText("celular"),
                    eCliente.getChildText("direccion"),
                    eCliente.getChildText("email")
            );
            // Si Cliente tuviera Vehiculos como elementos hijos, se cargarían aquí.
            clientes.add(clienteActual);
        }
        return clientes;
    }

    /**
     * Busca un cliente por su ID.
     * @param idCliente El ID del cliente a buscar.
     * @return Un Optional que contiene el Cliente si se encuentra, o un Optional vacío.
     */
    public Optional<Cliente> getClientePorId(String idCliente) {
        return getTodosClientes().stream()
                .filter(c -> c.getIdCliente().equals(idCliente))
                .findFirst();
    }

    /**
     * Actualiza un cliente existente en el documento XML.
     * @param clienteActualizado El objeto Cliente con los datos actualizados.
     * @return true si el cliente fue actualizado, false si no se encontró.
     * @throws IOException Si ocurre un error de E/S al guardar.
     */
    public boolean actualizarCliente(Cliente clienteActualizado) throws IOException {
        List<Element> eListaClientes = raiz.getChildren(CLIENTE_ELEMENT_NAME);
        for (Element eCliente : eListaClientes) {
            if (eCliente.getAttributeValue("idCliente").equals(clienteActualizado.getIdCliente())) {
                eCliente.getChild("nombre").setText(clienteActualizado.getNombre());
                eCliente.getChild("primerApellido").setText(clienteActualizado.getPrimerApellido());
                eCliente.getChild("segundoApellido").setText(clienteActualizado.getSegundoApellido());
                eCliente.getChild("telefono").setText(clienteActualizado.getTelefono());
                eCliente.getChild("celular").setText(clienteActualizado.getCelular());
                eCliente.getChild("direccion").setText(clienteActualizado.getDireccion());
                eCliente.getChild("email").setText(clienteActualizado.getEmail());
                guardar();
                return true;
            }
        }
        System.out.println("Error: Cliente con ID '" + clienteActualizado.getIdCliente() + "' no encontrado para actualizar.");
        return false;
    }

    /**
     * Elimina un cliente del documento XML por su ID.
     * @param idCliente El ID del cliente a eliminar.
     * @return true si el cliente fue eliminado, false si no se encontró.
     * @throws IOException Si ocurre un error de E/S al guardar.
     */
    public boolean eliminarCliente(String idCliente) throws IOException {
        List<Element> eListaClientes = raiz.getChildren(CLIENTE_ELEMENT_NAME);
        for (Element eCliente : eListaClientes) {
            if (eCliente.getAttributeValue("idCliente").equals(idCliente)) {
                raiz.removeContent(eCliente);
                guardar();
                return true;
            }
        }
        System.out.println("Error: Cliente con ID '" + idCliente + "' no encontrado para eliminar.");
        return false;
    }
}