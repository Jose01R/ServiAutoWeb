package service;


import dataManager.OrdenTrabajoXmlData;
import domain.OrdenTrabajo;
import org.jdom2.JDOMException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class OrdenTrabajoService {
    private final OrdenTrabajoXmlData ordenTrabajoXmlData;

    // Constructor que recibe la ruta del archivo XML
    public OrdenTrabajoService(String rutaArchivo) throws IOException, JDOMException {
        this.ordenTrabajoXmlData = OrdenTrabajoXmlData.abrirDocumento(rutaArchivo);
    }

    /**
     * Agrega una nueva orden de trabajo.
     * @param ordenTrabajo Objeto OrdenTrabajo a insertar.
     * @param placaVehiculo Placa del vehículo asociado.
     * @return true si se insertó, false si ya existe una orden con ese ID.
     */
    public boolean agregarOrdenTrabajo(OrdenTrabajo ordenTrabajo, String placaVehiculo) throws IOException {
        if (ordenTrabajoXmlData.getOrdenTrabajoPorId(ordenTrabajo.getIdOrdenTrabajo()).isPresent()) {
            return false;
        }
        ordenTrabajoXmlData.insertarOrdenTrabajo(ordenTrabajo, placaVehiculo);
        return true;
    }

    /**
     * Obtiene todas las órdenes de trabajo.
     */
    public List<OrdenTrabajo> obtenerTodasOrdenesTrabajo() {
        return ordenTrabajoXmlData.getTodasOrdenesTrabajo();
    }

    /**
     * Busca una orden de trabajo por su ID.
     */
    public Optional<OrdenTrabajo> buscarOrdenTrabajoPorId(String idOrdenTrabajo) {
        return ordenTrabajoXmlData.getOrdenTrabajoPorId(idOrdenTrabajo);
    }

    /**
     * Actualiza una orden de trabajo existente.
     */
    public boolean actualizarOrdenTrabajo(OrdenTrabajo ordenActualizada) throws IOException {
        return ordenTrabajoXmlData.actualizarOrdenTrabajo(ordenActualizada);
    }

    /**
     * Elimina una orden de trabajo por su ID.
     */
    public boolean eliminarOrdenTrabajo(String idOrdenTrabajo) throws IOException {
        return ordenTrabajoXmlData.eliminarOrdenTrabajo(idOrdenTrabajo);
    }
}
