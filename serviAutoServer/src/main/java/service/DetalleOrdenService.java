package service;

import dataManager.DetalleOrdenXmlData;
import domain.DetalleOrden;
import domain.Repuesto;
import domain.Servicio;
import org.jdom2.JDOMException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class DetalleOrdenService {
    private final DetalleOrdenXmlData detalleOrdenXmlData;

    public DetalleOrdenService(String rutaArchivo) throws IOException, JDOMException {
        this.detalleOrdenXmlData = DetalleOrdenXmlData.abrirDocumento(rutaArchivo);
    }

    public boolean agregarDetalleOrden(DetalleOrden detalleOrden, String idOrdenTrabajo,
                                       String nombreServicio, String nombreRepuesto) throws IOException {

        if (nombreServicio != null && nombreRepuesto != null &&
                !nombreServicio.isBlank() && !nombreRepuesto.isBlank()) {
            throw new IllegalArgumentException("Un DetalleOrden no puede tener tanto servicio como repuesto.");
        }

        if (detalleOrdenXmlData.getDetalleOrdenPorId(detalleOrden.getIdDetalleOrden()).isPresent()) {
            return false;
        }

        detalleOrdenXmlData.insertarDetalleOrden(detalleOrden, idOrdenTrabajo, nombreServicio, nombreRepuesto);
        return true;
    }

    public List<DetalleOrden> obtenerTodosDetallesOrden() {
        return detalleOrdenXmlData.getTodosDetallesOrden();
    }

    public Optional<DetalleOrden> buscarDetalleOrdenPorId(String idDetalleOrden) {
        return detalleOrdenXmlData.getDetalleOrdenPorId(idDetalleOrden);
    }

    public boolean actualizarDetalleOrden(DetalleOrden detalleActualizado) throws IOException {
        return detalleOrdenXmlData.actualizarDetalleOrden(detalleActualizado);
    }

    public boolean eliminarDetalleOrden(String idDetalleOrden) throws IOException {
        return detalleOrdenXmlData.eliminarDetalleOrden(idDetalleOrden);
    }

    public List<DetalleOrden> obtenerTodosDetallesOrden(List<Servicio> servicios, List<Repuesto> repuestos) {
        return detalleOrdenXmlData.getTodosDetallesOrden(servicios, repuestos);
    }

    public String generarNuevoIdDetalleOrden() {
        return detalleOrdenXmlData.generarNuevoId();
    }
}
