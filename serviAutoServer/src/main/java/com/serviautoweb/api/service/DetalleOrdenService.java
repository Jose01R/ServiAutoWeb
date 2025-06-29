package com.serviautoweb.api.service;


import dataManager.DetalleOrdenXmlData;
import domain.DetalleOrden;
import org.jdom2.JDOMException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class DetalleOrdenService {
    private final DetalleOrdenXmlData detalleOrdenXmlData;

    // Constructor que recibe la ruta del archivo XML
    public DetalleOrdenService() throws IOException, JDOMException {
        this.detalleOrdenXmlData = DetalleOrdenXmlData.abrirDocumento("WEB-INF/detallesOrden.xml");
    }

    /**
     * Agrega un nuevo detalle de orden.
     * Aplica validaciones de negocio antes de delegar a la capa data.
     */
    public boolean agregarDetalleOrden(DetalleOrden detalleOrden, String idOrdenTrabajo,
                                       String nombreServicio, String nombreRepuesto) throws IOException {
        // Validación de negocio: no se permite agregar ambos servicio y repuesto
        if (nombreServicio != null && nombreRepuesto != null) {
            throw new IllegalArgumentException("Un DetalleOrden no puede tener asociado tanto un Servicio como un Repuesto.");
        }
        // Retorna true si se insertó, false si ya existe
        if (detalleOrdenXmlData.getDetalleOrdenPorId(detalleOrden.getIdDetalleOrden()).isPresent()) {
            return false;
        }
        detalleOrdenXmlData.insertarDetalleOrden(detalleOrden, idOrdenTrabajo, nombreServicio, nombreRepuesto);
        return true;
    }

    /**
     * Obtiene todos los detalles de orden.
     */
    public List<DetalleOrden> obtenerTodosDetallesOrden() {
        return detalleOrdenXmlData.getTodosDetallesOrden();
    }

    /**
     * Busca un detalle de orden por su ID.
     */
    public Optional<DetalleOrden> buscarDetalleOrdenPorId(String idDetalleOrden) {
        return detalleOrdenXmlData.getDetalleOrdenPorId(idDetalleOrden);
    }

    /**
     * Actualiza un detalle de orden existente.
     */
    public boolean actualizarDetalleOrden(DetalleOrden detalleActualizado) throws IOException {
        return detalleOrdenXmlData.actualizarDetalleOrden(detalleActualizado);
    }

    /**
     * Elimina un detalle de orden por su ID.
     */
    public boolean eliminarDetalleOrden(String idDetalleOrden) throws IOException {
        return detalleOrdenXmlData.eliminarDetalleOrden(idDetalleOrden);
    }
}
