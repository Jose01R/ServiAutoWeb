package dataManager;

import domain.DetalleOrden;
import domain.OrdenTrabajo;
import domain.Repuesto;
import domain.Servicio;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import util.XmlPaths;

import java.io.*;
import java.util.*;

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
        this.document = saxBuilder.build(rutaDocumento);
        this.raiz = document.getRootElement();
        this.rutaDocumento = rutaDocumento;
    }

    public static DetalleOrdenXmlData abrirDocumento(String rutaDocumento) throws JDOMException, IOException {
        return new File(rutaDocumento).exists()
                ? new DetalleOrdenXmlData(rutaDocumento)
                : new DetalleOrdenXmlData(rutaDocumento, ROOT_ELEMENT_NAME);
    }

    private void guardar() throws IOException {
        Format format = Format.getPrettyFormat();
        format.setEncoding("UTF-8");
        try (PrintWriter pw = new PrintWriter(this.rutaDocumento)) {
            new XMLOutputter(format).output(this.document, pw);
        }
    }

    public void insertarDetalleOrden(DetalleOrden detalleOrden, String idOrdenTrabajo, String nombreServicio, String nombreRepuesto) throws IOException {
        Element eDetalle = new Element(DETALLE_ORDEN_ELEMENT_NAME);
        eDetalle.setAttribute("idDetalleOrden", detalleOrden.getIdDetalleOrden());
        eDetalle.setAttribute("idOrdenTrabajo", idOrdenTrabajo);

        eDetalle.addContent(new Element("cantidad").setText(String.valueOf(detalleOrden.getCantidad())));
        eDetalle.addContent(new Element("observaciones").setText(Optional.ofNullable(detalleOrden.getObservaciones()).orElse("")));
        eDetalle.addContent(new Element("tipoDetalle").setText(Optional.ofNullable(detalleOrden.getTipoDetalle()).orElse("")));
        eDetalle.addContent(new Element("idEstado").setText(Optional.ofNullable(detalleOrden.getIdEstado()).orElse("")));

        if (nombreServicio != null && !nombreServicio.isBlank()) {
            eDetalle.addContent(new Element("servicio").setAttribute("nombre", nombreServicio));
        } else if (nombreRepuesto != null && !nombreRepuesto.isBlank()) {
            eDetalle.addContent(new Element("repuesto").setAttribute("nombre", nombreRepuesto));
        }

        this.raiz.addContent(eDetalle);
        guardar();
    }

    public List<DetalleOrden> getTodosDetallesOrden(List<Servicio> servicios, List<Repuesto> repuestos) {
        List<DetalleOrden> detalles = new ArrayList<>();
        OrdenTrabajoXmlData ordenTrabajoData = null;
        try {
            ordenTrabajoData = OrdenTrabajoXmlData.abrirDocumento(XmlPaths.getOrdenTrabajoPath());
        } catch (Exception e) {
            System.err.println("Error cargando ordenes de trabajo: " + e.getMessage());
        }

        for (Element eDetalle : raiz.getChildren(DETALLE_ORDEN_ELEMENT_NAME)) {
            DetalleOrden d = new DetalleOrden(
                    eDetalle.getAttributeValue("idDetalleOrden"),
                    Integer.parseInt(eDetalle.getChildText("cantidad")),
                    eDetalle.getChildText("observaciones"),
                    eDetalle.getChildText("tipoDetalle"),
                    eDetalle.getChildText("idEstado")
            );

            String idOrden = eDetalle.getAttributeValue("idOrdenTrabajo");
            if (ordenTrabajoData != null) {
                ordenTrabajoData.getOrdenTrabajoPorId(idOrden).ifPresent(d::setOrdenTrabajo);
            }

            Element eServicio = eDetalle.getChild("servicio");
            if (eServicio != null) {
                String nombre = eServicio.getAttributeValue("nombre");
                Servicio encontrado = servicios.stream()
                        .filter(s -> s.getNombre() != null && s.getNombre().equalsIgnoreCase(nombre))
                        .findFirst().orElse(null);
                if (encontrado != null) d.setServicio(encontrado);
            }

            Element eRepuesto = eDetalle.getChild("repuesto");
            if (eRepuesto != null) {
                String nombre = eRepuesto.getAttributeValue("nombre");
                Repuesto encontrado = repuestos.stream()
                        .filter(r -> r.getNombre() != null && r.getNombre().equalsIgnoreCase(nombre))
                        .findFirst().orElse(null);
                if (encontrado != null) d.setRepuesto(encontrado);
            }

            detalles.add(d);
        }
        return detalles;
    }

    public Optional<DetalleOrden> getDetalleOrdenPorId(String id, List<Servicio> servicios, List<Repuesto> repuestos) {
        return getTodosDetallesOrden(servicios, repuestos).stream()
                .filter(d -> d.getIdDetalleOrden().equals(id)).findFirst();
    }

    public List<DetalleOrden> getTodosDetallesOrden() {
        return getTodosDetallesOrden(new ArrayList<>(), new ArrayList<>());
    }

    public Optional<DetalleOrden> getDetalleOrdenPorId(String idDetalleOrden) {
        return getDetalleOrdenPorId(idDetalleOrden, new ArrayList<>(), new ArrayList<>());
    }

    public boolean actualizarDetalleOrden(DetalleOrden d) throws IOException {
        for (Element eDetalle : raiz.getChildren(DETALLE_ORDEN_ELEMENT_NAME)) {
            if (eDetalle.getAttributeValue("idDetalleOrden").equals(d.getIdDetalleOrden())) {
                eDetalle.getChild("cantidad").setText(String.valueOf(d.getCantidad()));
                eDetalle.getChild("observaciones").setText(Optional.ofNullable(d.getObservaciones()).orElse(""));
                eDetalle.getChild("tipoDetalle").setText(Optional.ofNullable(d.getTipoDetalle()).orElse(""));
                eDetalle.getChild("idEstado").setText(Optional.ofNullable(d.getIdEstado()).orElse(""));
                guardar();
                return true;
            }
        }
        return false;
    }

    public boolean eliminarDetalleOrden(String idDetalleOrden) throws IOException {
        for (Element eDetalle : raiz.getChildren(DETALLE_ORDEN_ELEMENT_NAME)) {
            if (eDetalle.getAttributeValue("idDetalleOrden").equals(idDetalleOrden)) {
                raiz.removeContent(eDetalle);
                guardar();
                return true;
            }
        }
        return false;
    }

    public String generarNuevoId() {
        int max = getTodosDetallesOrden().stream()
                .map(DetalleOrden::getIdDetalleOrden)
                .filter(id -> id != null && id.startsWith("DO"))
                .mapToInt(id -> {
                    try { return Integer.parseInt(id.substring(2)); }
                    catch (NumberFormatException e) { return 0; }
                })
                .max().orElse(0);
        return String.format("DO%03d", max + 1);
    }
}
