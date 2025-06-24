package dataManager;

import domain.Repuesto;
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

public class RepuestoXmlData {
    private Document document;
    private Element raiz;
    private String rutaDocumento;
    private static final String ROOT_ELEMENT_NAME = "repuestos";
    private static final String REPUESTO_ELEMENT_NAME = "repuesto";

    public RepuestoXmlData(String rutaDocumento, String nombreRaiz) throws IOException {
        this.rutaDocumento = rutaDocumento;
        this.raiz = new Element(nombreRaiz);
        this.document = new Document(raiz);
        guardar();
    }

    private RepuestoXmlData(String rutaDocumento) throws JDOMException, IOException {
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setIgnoringBoundaryWhitespace(true);

        this.document = saxBuilder.build(rutaDocumento);
        this.raiz = document.getRootElement();
        this.rutaDocumento = rutaDocumento;
    }

    public static RepuestoXmlData abrirDocumento(String rutaDocumento) throws JDOMException, IOException {
        if (new File(rutaDocumento).exists()) {
            return new RepuestoXmlData(rutaDocumento);
        } else {
            return new RepuestoXmlData(rutaDocumento, ROOT_ELEMENT_NAME);
        }
    }

    private void guardar() throws IOException {
        Format format = Format.getPrettyFormat();
        format.setEncoding("UTF-8");
        XMLOutputter xmlOutputter = new XMLOutputter(format);

        try (PrintWriter printWriter = new PrintWriter(this.rutaDocumento)) {
            xmlOutputter.output(this.document, printWriter);
        }
    }

    /**
     * Inserta un nuevo repuesto en el documento XML.
     * Se asume que el nombre del repuesto es único y actúa como su ID.
     * @param repuesto El objeto Repuesto a insertar.
     * @throws IOException Si ocurre un error de E/S al guardar.
     */
    public void insertarRepuesto(Repuesto repuesto) throws IOException {
        if (getRepuestoPorNombre(repuesto.getNombre()).isPresent()) {
            System.out.println("Error: Ya existe un repuesto con el nombre '" + repuesto.getNombre() + "'. No insertado.");
            return;
        }

        Element eRepuesto = new Element(REPUESTO_ELEMENT_NAME);
        eRepuesto.setAttribute("nombre", repuesto.getNombre()); // Nombre como ID

        eRepuesto.addContent(new Element("precio").setText(String.valueOf(repuesto.getPrecio())));
        eRepuesto.addContent(new Element("cantidad").setText(String.valueOf(repuesto.getCantidad())));
        eRepuesto.addContent(new Element("pedido").setText(String.valueOf(repuesto.isPedido())));

        this.raiz.addContent(eRepuesto);
        guardar();
    }

    /**
     * Obtiene una lista de todos los repuestos en el documento XML.
     * @return Una lista de objetos Repuesto.
     */
    public List<Repuesto> getTodosRepuestos() {
        List<Element> eListaRepuestos = raiz.getChildren(REPUESTO_ELEMENT_NAME);
        List<Repuesto> repuestos = new ArrayList<>();
        for (Element eRepuesto : eListaRepuestos) {
            Repuesto repuestoActual = new Repuesto(
                    eRepuesto.getAttributeValue("nombre"),
                    Double.parseDouble(eRepuesto.getChildText("precio")),
                    Integer.parseInt(eRepuesto.getChildText("cantidad")),
                    Boolean.parseBoolean(eRepuesto.getChildText("pedido"))
            );
            repuestos.add(repuestoActual);
        }
        return repuestos;
    }

    /**
     * Busca un repuesto por su nombre.
     * @param nombre El nombre del repuesto a buscar.
     * @return Un Optional que contiene el Repuesto si se encuentra, o un Optional vacío.
     */
    public Optional<Repuesto> getRepuestoPorNombre(String nombre) {
        return getTodosRepuestos().stream()
                .filter(r -> r.getNombre().equals(nombre))
                .findFirst();
    }

    /**
     * Actualiza un repuesto existente en el documento XML.
     * @param repuestoActualizado El objeto Repuesto con los datos actualizados.
     * @return true si el repuesto fue actualizado, false si no se encontró.
     * @throws IOException Si ocurre un error de E/S al guardar.
     */
    public boolean actualizarRepuesto(Repuesto repuestoActualizado) throws IOException {
        List<Element> eListaRepuestos = raiz.getChildren(REPUESTO_ELEMENT_NAME);
        for (Element eRepuesto : eListaRepuestos) {
            if (eRepuesto.getAttributeValue("nombre").equals(repuestoActualizado.getNombre())) {
                eRepuesto.getChild("precio").setText(String.valueOf(repuestoActualizado.getPrecio()));
                eRepuesto.getChild("cantidad").setText(String.valueOf(repuestoActualizado.getCantidad()));
                eRepuesto.getChild("pedido").setText(String.valueOf(repuestoActualizado.isPedido()));
                guardar();
                return true;
            }
        }
        System.out.println("Error: Repuesto con nombre '" + repuestoActualizado.getNombre() + "' no encontrado para actualizar.");
        return false;
    }

    /**
     * Elimina un repuesto del documento XML por su nombre.
     * @param nombre El nombre del repuesto a eliminar.
     * @return true si el repuesto fue eliminado, false si no se encontró.
     * @throws IOException Si ocurre un error de E/S al guardar.
     */
    public boolean eliminarRepuesto(String nombre) throws IOException {
        List<Element> eListaRepuestos = raiz.getChildren(REPUESTO_ELEMENT_NAME);
        for (Element eRepuesto : eListaRepuestos) {
            if (eRepuesto.getAttributeValue("nombre").equals(nombre)) {
                raiz.removeContent(eRepuesto);
                guardar();
                return true;
            }
        }
        System.out.println("Error: Repuesto con nombre '" + nombre + "' no encontrado para eliminar.");
        return false;
    }
}

