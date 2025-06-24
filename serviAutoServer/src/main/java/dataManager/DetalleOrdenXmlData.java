package dataManager;

import domain.DetalleOrden;
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

public class DetalleOrdenXmlData {
    private Document document;
    private Element raiz;
    private String rutaDocumento;
    private static final String ROOT_ELEMENT_NAME = "detallesOrden";
    private static final String DETALLE_ORDEN_ELEMENT_NAME = "detalleOrden";

    public DetalleOrdenXmlData(String rutaDocumento, String nombreRaiz) throws IOException {
        this.rutaDocumento = rutaDocumento;
        this.raiz = new Element(nombreRaiz);
        this.document = new Document(raiz);
        guardar();
    }

    private DetalleOrdenXmlData(String rutaDocumento) throws JDOMException, IOException {
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setIgnoringBoundaryWhitespace(true);

        this.document = saxBuilder.build(rutaDocumento);
        this.raiz = document.getRootElement();
        this.rutaDocumento = rutaDocumento;
    }

    public static DetalleOrdenXmlData abrirDocumento(String rutaDocumento) throws JDOMException, IOException {
        if (new File(rutaDocumento).exists()) {
            return new DetalleOrdenXmlData(rutaDocumento);
        } else {
            return new DetalleOrdenXmlData(rutaDocumento, ROOT_ELEMENT_NAME);
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
     * Inserta un nuevo detalle de orden en el documento XML.
     * @param detalleOrden El objeto DetalleOrden a insertar.
     * @param idOrdenTrabajo La ID de la orden de trabajo asociada.
     * @param nombreServicio El nombre del servicio asociado (puede ser null).
     * @param nombreRepuesto El nombre del repuesto asociado (puede ser null).
     * @throws IOException Si ocurre un error de E/S al guardar.
     * @throws IllegalArgumentException Si se intenta asociar tanto un servicio como un repuesto.
     */
    public void insertarDetalleOrden(DetalleOrden detalleOrden, String idOrdenTrabajo,
                                     String nombreServicio, String nombreRepuesto) throws IOException {
        if (getDetalleOrdenPorId(detalleOrden.getIdDetalleOrden()).isPresent()) {
            System.out.println("Error: Ya existe un detalle de orden con el ID '" + detalleOrden.getIdDetalleOrden() + "'. No insertado.");
            return;
        }
        if (nombreServicio != null && nombreRepuesto != null) {
            throw new IllegalArgumentException("Un DetalleOrden no puede tener asociado tanto un Servicio como un Repuesto.");
        }

        Element eDetalleOrden = new Element(DETALLE_ORDEN_ELEMENT_NAME);
        eDetalleOrden.setAttribute("idDetalleOrden", detalleOrden.getIdDetalleOrden());
        eDetalleOrden.setAttribute("idOrdenTrabajo", idOrdenTrabajo); // Relación con OrdenTrabajo

        eDetalleOrden.addContent(new Element("cantidad").setText(String.valueOf(detalleOrden.getCantidad())));
        eDetalleOrden.addContent(new Element("observaciones").setText(detalleOrden.getObservaciones()));
        eDetalleOrden.addContent(new Element("tipoDetalle").setText(detalleOrden.getTipoDetalle()));
        eDetalleOrden.addContent(new Element("idEstado").setText(detalleOrden.getIdEstado()));

        if (nombreServicio != null) {
            eDetalleOrden.addContent(new Element("servicio").setAttribute("nombre", nombreServicio));
        } else if (nombreRepuesto != null) {
            eDetalleOrden.addContent(new Element("repuesto").setAttribute("nombre", nombreRepuesto));
        }

        this.raiz.addContent(eDetalleOrden);
        guardar();
    }

    /**
     * Obtiene una lista de todos los detalles de orden en el documento XML.
     * @return Una lista de objetos DetalleOrden.
     */
    public List<DetalleOrden> getTodosDetallesOrden() {
        List<Element> eListaDetalles = raiz.getChildren(DETALLE_ORDEN_ELEMENT_NAME);
        List<DetalleOrden> detalles = new ArrayList<>();
        // En un escenario real, necesitarías ServicioXmlData y RepuestoXmlData para cargar los objetos
        // ServicioXmlData servicioData = ServicioXmlData.abrirDocumento("ruta/servicios.xml");
        // RepuestoXmlData repuestoData = RepuestoXmlData.abrirDocumento("ruta/repuestos.xml");

        for (Element eDetalle : eListaDetalles) {
            DetalleOrden detalleActual = new DetalleOrden(
                    eDetalle.getAttributeValue("idDetalleOrden"),
                    Integer.parseInt(eDetalle.getChildText("cantidad")),
                    eDetalle.getChildText("observaciones"),
                    eDetalle.getChildText("tipoDetalle"),
                    eDetalle.getChildText("idEstado")
            );

            // Asignar OrdenTrabajo (si es necesario cargarla completa)
            // String idOrdenTrabajo = eDetalle.getAttributeValue("idOrdenTrabajo");
            // OrdenTrabajoXmlData ordenData = OrdenTrabajoXmlData.abrirDocumento("ruta/ordenes.xml");
            // ordenData.getOrdenTrabajoPorId(idOrdenTrabajo).ifPresent(detalleActual::setOrdenTrabajo);

            // Cargar Servicio o Repuesto
            Element eServicio = eDetalle.getChild("servicio");
            if (eServicio != null) {
                // String nombreServicio = eServicio.getAttributeValue("nombre");
                // servicioData.getServicioPorNombre(nombreServicio).ifPresent(detalleActual::setServicio);
            } else {
                Element eRepuesto = eDetalle.getChild("repuesto");
                if (eRepuesto != null) {
                    // String nombreRepuesto = eRepuesto.getAttributeValue("nombre");
                    // repuestoData.getRepuestoPorNombre(nombreRepuesto).ifPresent(detalleActual::setRepuesto);
                }
            }
            detalles.add(detalleActual);
        }
        return detalles;
    }

    /**
     * Busca un detalle de orden por su ID.
     * @param idDetalleOrden El ID del detalle de orden a buscar.
     * @return Un Optional que contiene el DetalleOrden si se encuentra, o un Optional vacío.
     */
    public Optional<DetalleOrden> getDetalleOrdenPorId(String idDetalleOrden) {
        return getTodosDetallesOrden().stream()
                .filter(d -> d.getIdDetalleOrden().equals(idDetalleOrden))
                .findFirst();
    }

    /**
     * Actualiza un detalle de orden existente en el documento XML.
     * @param detalleActualizado El objeto DetalleOrden con los datos actualizados.
     * @return true si el detalle de orden fue actualizado, false si no se encontró.
     * @throws IOException Si ocurre un error de E/S al guardar.
     */
    public boolean actualizarDetalleOrden(DetalleOrden detalleActualizado) throws IOException {
        List<Element> eListaDetalles = raiz.getChildren(DETALLE_ORDEN_ELEMENT_NAME);
        for (Element eDetalle : eListaDetalles) {
            if (eDetalle.getAttributeValue("idDetalleOrden").equals(detalleActualizado.getIdDetalleOrden())) {
                eDetalle.getChild("cantidad").setText(String.valueOf(detalleActualizado.getCantidad()));
                eDetalle.getChild("observaciones").setText(detalleActualizado.getObservaciones());
                eDetalle.getChild("tipoDetalle").setText(detalleActualizado.getTipoDetalle());
                eDetalle.getChild("idEstado").setText(detalleActualizado.getIdEstado());

                // Asumo que la actualización de Servicio/Repuesto se maneja por separado si cambian las relaciones
                guardar();
                return true;
            }
        }
        System.out.println("Error: Detalle de orden con ID '" + detalleActualizado.getIdDetalleOrden() + "' no encontrado para actualizar.");
        return false;
    }

    /**
     * Elimina un detalle de orden del documento XML por su ID.
     * @param idDetalleOrden El ID del detalle de orden a eliminar.
     * @return true si el detalle de orden fue eliminado, false si no se encontró.
     * @throws IOException Si ocurre un error de E/S al guardar.
     */
    public boolean eliminarDetalleOrden(String idDetalleOrden) throws IOException {
        List<Element> eListaDetalles = raiz.getChildren(DETALLE_ORDEN_ELEMENT_NAME);
        for (Element eDetalle : eListaDetalles) {
            if (eDetalle.getAttributeValue("idDetalleOrden").equals(idDetalleOrden)) {
                raiz.removeContent(eDetalle);
                guardar();
                return true;
            }
        }
        System.out.println("Error: Detalle de orden con ID '" + idDetalleOrden + "' no encontrado para eliminar.");
        return false;
    }
}
