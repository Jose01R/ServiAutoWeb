package dataManager;

import domain.OrdenTrabajo;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class OrdenTrabajoXmlData {
    private Document document;
    private Element raiz;
    private String rutaDocumento;
    private static final String ROOT_ELEMENT_NAME = "ordenesTrabajo";
    private static final String ORDEN_TRABAJO_ELEMENT_NAME = "ordenTrabajo";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public OrdenTrabajoXmlData(String rutaDocumento, String nombreRaiz) throws IOException {
        this.rutaDocumento = rutaDocumento;
        this.raiz = new Element(nombreRaiz);
        this.document = new Document(raiz);
        guardar();
    }

    private OrdenTrabajoXmlData(String rutaDocumento) throws JDOMException, IOException {
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setIgnoringBoundaryWhitespace(true);

        this.document = saxBuilder.build(rutaDocumento);
        this.raiz = document.getRootElement();
        this.rutaDocumento = rutaDocumento;
    }

    public static OrdenTrabajoXmlData abrirDocumento(String rutaDocumento) throws JDOMException, IOException {
        if (new File(rutaDocumento).exists()) {
            return new OrdenTrabajoXmlData(rutaDocumento);
        } else {
            return new OrdenTrabajoXmlData(rutaDocumento, ROOT_ELEMENT_NAME);
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
     * Inserta una nueva orden de trabajo en el documento XML.
     * @param ordenTrabajo El objeto OrdenTrabajo a insertar.
     * @param placaVehiculo La placa del vehículo asociado a esta orden.
     * @throws IOException Si ocurre un error de E/S al guardar.
     */
    public void insertarOrdenTrabajo(OrdenTrabajo ordenTrabajo, String placaVehiculo) throws IOException {
        if (getOrdenTrabajoPorId(ordenTrabajo.getIdOrdenTrabajo()).isPresent()) {
            System.out.println("Error: Ya existe una orden de trabajo con el ID '" + ordenTrabajo.getIdOrdenTrabajo() + "'. No insertada.");
            return;
        }

        Element eOrdenTrabajo = new Element(ORDEN_TRABAJO_ELEMENT_NAME);
        eOrdenTrabajo.setAttribute("idOrdenTrabajo", ordenTrabajo.getIdOrdenTrabajo());
        eOrdenTrabajo.setAttribute("placaVehiculo", placaVehiculo); // Relación con Vehiculo

        eOrdenTrabajo.addContent(new Element("descripcionSolicitud").setText(ordenTrabajo.getDescripcionSolicitud()));
        eOrdenTrabajo.addContent(new Element("fechaIngreso").setText(DATE_FORMAT.format(ordenTrabajo.getFechaIngreso())));
        eOrdenTrabajo.addContent(new Element("estado").setText(ordenTrabajo.getEstado()));
        if (ordenTrabajo.getFechaDevolucion() != null) {
            eOrdenTrabajo.addContent(new Element("fechaDevolucion").setText(DATE_FORMAT.format(ordenTrabajo.getFechaDevolucion())));
        }

        this.raiz.addContent(eOrdenTrabajo);
        guardar();
    }

    /**
     * Obtiene una lista de todas las órdenes de trabajo en el documento XML.
     * @return Una lista de objetos OrdenTrabajo.
     */
    public List<OrdenTrabajo> getTodasOrdenesTrabajo() {
        List<Element> eListaOrdenes = raiz.getChildren(ORDEN_TRABAJO_ELEMENT_NAME);
        List<OrdenTrabajo> ordenes = new ArrayList<>();
        for (Element eOrden : eListaOrdenes) {
            Date fechaIngreso = null;
            Date fechaDevolucion = null;
            try {
                fechaIngreso = DATE_FORMAT.parse(eOrden.getChildText("fechaIngreso"));
                if (eOrden.getChildText("fechaDevolucion") != null) {
                    fechaDevolucion = DATE_FORMAT.parse(eOrden.getChildText("fechaDevolucion"));
                }
            } catch (ParseException e) {
                System.err.println("Error al parsear fecha en OrdenTrabajo: " + e.getMessage());
            }

            OrdenTrabajo ordenActual = new OrdenTrabajo(
                    eOrden.getAttributeValue("idOrdenTrabajo"),
                    eOrden.getChildText("descripcionSolicitud"),
                    fechaIngreso,
                    eOrden.getChildText("estado")
            );
            ordenActual.setFechaDevolucion(fechaDevolucion);

            // Asignar el vehículo - esto implicaría cargar el vehículo por su placa
            String placaVehiculo = eOrden.getAttributeValue("placaVehiculo");
            try {
                VehiculoXmlData vehiculoData = VehiculoXmlData.abrirDocumento("ruta/vehiculos.xml"); // ajusta ruta real si es necesario
                vehiculoData.getVehiculoPorPlaca(placaVehiculo).ifPresent(ordenActual::setVehiculo);
            } catch (Exception e) {
                System.err.println("Error cargando vehículo con placa: " + placaVehiculo + " - " + e.getMessage());
            }

                ordenes.add(ordenActual);
        }
        return ordenes;
    }

    /**
     * Busca una orden de trabajo por su ID.
     * @param idOrdenTrabajo El ID de la orden de trabajo a buscar.
     * @return Un Optional que contiene la OrdenTrabajo si se encuentra, o un Optional vacío.
     */
    public Optional<OrdenTrabajo> getOrdenTrabajoPorId(String idOrdenTrabajo) {
        return getTodasOrdenesTrabajo().stream()
                .filter(o -> o.getIdOrdenTrabajo().equals(idOrdenTrabajo))
                .findFirst();
    }

    /**
     * Actualiza una orden de trabajo existente en el documento XML.
     * @param ordenActualizada El objeto OrdenTrabajo con los datos actualizados.
     * @return true si la orden fue actualizada, false si no se encontró.
     * @throws IOException Si ocurre un error de E/S al guardar.
     */
    public boolean actualizarOrdenTrabajo(OrdenTrabajo ordenActualizada) throws IOException {
        List<Element> eListaOrdenes = raiz.getChildren(ORDEN_TRABAJO_ELEMENT_NAME);
        for (Element eOrden : eListaOrdenes) {
            if (eOrden.getAttributeValue("idOrdenTrabajo").equals(ordenActualizada.getIdOrdenTrabajo())) {
                eOrden.getChild("descripcionSolicitud").setText(ordenActualizada.getDescripcionSolicitud());
                eOrden.getChild("fechaIngreso").setText(DATE_FORMAT.format(ordenActualizada.getFechaIngreso()));
                eOrden.getChild("estado").setText(ordenActualizada.getEstado());

                // Actualizar o añadir fechaDevolucion
                if (ordenActualizada.getFechaDevolucion() != null) {
                    Element fechaDevolucionElement = eOrden.getChild("fechaDevolucion");
                    if (fechaDevolucionElement == null) {
                        eOrden.addContent(new Element("fechaDevolucion").setText(DATE_FORMAT.format(ordenActualizada.getFechaDevolucion())));
                    } else {
                        fechaDevolucionElement.setText(DATE_FORMAT.format(ordenActualizada.getFechaDevolucion()));
                    }
                } else {
                    // Si se elimina la fecha de devolución, quitar el elemento
                    eOrden.removeChild("fechaDevolucion");
                }
                guardar();
                return true;
            }
        }
        System.out.println("Error: Orden de trabajo con ID '" + ordenActualizada.getIdOrdenTrabajo() + "' no encontrada para actualizar.");
        return false;
    }

    /**
     * Elimina una orden de trabajo del documento XML por su ID.
     * @param idOrdenTrabajo El ID de la orden de trabajo a eliminar.
     * @return true si la orden fue eliminada, false si no se encontró.
     * @throws IOException Si ocurre un error de E/S al guardar.
     */
    public boolean eliminarOrdenTrabajo(String idOrdenTrabajo) throws IOException {
        List<Element> eListaOrdenes = raiz.getChildren(ORDEN_TRABAJO_ELEMENT_NAME);
        for (Element eOrden : eListaOrdenes) {
            if (eOrden.getAttributeValue("idOrdenTrabajo").equals(idOrdenTrabajo)) {
                raiz.removeContent(eOrden);
                guardar();
                return true;
            }
        }
        System.out.println("Error: Orden de trabajo con ID '" + idOrdenTrabajo + "' no encontrada para eliminar.");
        return false;
    }

    public String generarNuevoIdOrdenTrabajo() {
        List<Element> eListaOrdenes = raiz.getChildren(ORDEN_TRABAJO_ELEMENT_NAME);
        int maxNumero = 0;

        for (Element orden : eListaOrdenes) {
            String id = orden.getAttributeValue("idOrdenTrabajo");
            if (id != null && id.startsWith("ORD-")) {
                try {
                    int num = Integer.parseInt(id.substring(4));
                    if (num > maxNumero) {
                        maxNumero = num;
                    }
                } catch (NumberFormatException ignored) {}
            }
        }

        int nuevoNumero = maxNumero + 1;
        return String.format("ORD-%03d", nuevoNumero);
    }
}


