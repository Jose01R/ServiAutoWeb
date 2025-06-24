package dataManager;

import domain.Servicio;
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

public class ServicioXmlData {
    private Document document;
    private Element raiz;
    private String rutaDocumento;
    private static final String ROOT_ELEMENT_NAME = "servicios";
    private static final String SERVICIO_ELEMENT_NAME = "servicio";

    public ServicioXmlData(String rutaDocumento, String nombreRaiz) throws IOException {
        this.rutaDocumento = rutaDocumento;
        this.raiz = new Element(nombreRaiz);
        this.document = new Document(raiz);
        guardar();
    }

    private ServicioXmlData(String rutaDocumento) throws JDOMException, IOException {
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setIgnoringBoundaryWhitespace(true);

        this.document = saxBuilder.build(rutaDocumento);
        this.raiz = document.getRootElement();
        this.rutaDocumento = rutaDocumento;
    }

    public static ServicioXmlData abrirDocumento(String rutaDocumento) throws JDOMException, IOException {
        if (new File(rutaDocumento).exists()) {
            return new ServicioXmlData(rutaDocumento);
        } else {
            return new ServicioXmlData(rutaDocumento, ROOT_ELEMENT_NAME);
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
     * Inserta un nuevo servicio en el documento XML.
     * Se asume que el nombre del servicio es único y actúa como su ID.
     * @param servicio El objeto Servicio a insertar.
     * @throws IOException Si ocurre un error de E/S al guardar.
     */
    public void insertarServicio(Servicio servicio) throws IOException {
        if (getServicioPorNombre(servicio.getNombre()).isPresent()) {
            System.out.println("Error: Ya existe un servicio con el nombre '" + servicio.getNombre() + "'. No insertado.");
            return;
        }

        Element eServicio = new Element(SERVICIO_ELEMENT_NAME);
        eServicio.setAttribute("nombre", servicio.getNombre()); // Nombre como ID

        eServicio.addContent(new Element("precio").setText(String.valueOf(servicio.getPrecio())));
        eServicio.addContent(new Element("costoManoObra").setText(String.valueOf(servicio.getCostoManoObra())));

        this.raiz.addContent(eServicio);
        guardar();
    }

    /**
     * Obtiene una lista de todos los servicios en el documento XML.
     * @return Una lista de objetos Servicio.
     */
    public List<Servicio> getTodosServicios() {
        List<Element> eListaServicios = raiz.getChildren(SERVICIO_ELEMENT_NAME);
        List<Servicio> servicios = new ArrayList<>();
        for (Element eServicio : eListaServicios) {
            Servicio servicioActual = new Servicio(
                    eServicio.getAttributeValue("nombre"),
                    Double.parseDouble(eServicio.getChildText("precio")),
                    Double.parseDouble(eServicio.getChildText("costoManoObra"))
            );
            servicios.add(servicioActual);
        }
        return servicios;
    }

    /**
     * Busca un servicio por su nombre.
     * @param nombre El nombre del servicio a buscar.
     * @return Un Optional que contiene el Servicio si se encuentra, o un Optional vacío.
     */
    public Optional<Servicio> getServicioPorNombre(String nombre) {
        return getTodosServicios().stream()
                .filter(s -> s.getNombre().equals(nombre))
                .findFirst();
    }

    /**
     * Actualiza un servicio existente en el documento XML.
     * @param servicioActualizado El objeto Servicio con los datos actualizados.
     * @return true si el servicio fue actualizado, false si no se encontró.
     * @throws IOException Si ocurre un error de E/S al guardar.
     */
    public boolean actualizarServicio(Servicio servicioActualizado) throws IOException {
        List<Element> eListaServicios = raiz.getChildren(SERVICIO_ELEMENT_NAME);
        for (Element eServicio : eListaServicios) {
            if (eServicio.getAttributeValue("nombre").equals(servicioActualizado.getNombre())) {
                eServicio.getChild("precio").setText(String.valueOf(servicioActualizado.getPrecio()));
                eServicio.getChild("costoManoObra").setText(String.valueOf(servicioActualizado.getCostoManoObra()));
                guardar();
                return true;
            }
        }
        System.out.println("Error: Servicio con nombre '" + servicioActualizado.getNombre() + "' no encontrado para actualizar.");
        return false;
    }

    /**
     * Elimina un servicio del documento XML por su nombre.
     * @param nombre El nombre del servicio a eliminar.
     * @return true si el servicio fue eliminado, false si no se encontró.
     * @throws IOException Si ocurre un error de E/S al guardar.
     */
    public boolean eliminarServicio(String nombre) throws IOException {
        List<Element> eListaServicios = raiz.getChildren(SERVICIO_ELEMENT_NAME);
        for (Element eServicio : eListaServicios) {
            if (eServicio.getAttributeValue("nombre").equals(nombre)) {
                raiz.removeContent(eServicio);
                guardar();
                return true;
            }
        }
        System.out.println("Error: Servicio con nombre '" + nombre + "' no encontrado para eliminar.");
        return false;
    }
}

