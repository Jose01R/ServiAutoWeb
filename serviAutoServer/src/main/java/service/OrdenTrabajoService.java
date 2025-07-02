package service;


import dataManager.OrdenTrabajoXmlData;
import domain.OrdenTrabajo;
import org.jdom2.JDOMException;

import java.io.IOException;
import java.util.ArrayList;
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
        // Asigna un nuevo ID automáticamente si está vacío
        if (ordenTrabajo.getIdOrdenTrabajo() == null || ordenTrabajo.getIdOrdenTrabajo().isBlank()) {
            String nuevoId = ordenTrabajoXmlData.generarNuevoIdOrdenTrabajo();  // <--- Aquí se llama al método nuevo
            ordenTrabajo = new OrdenTrabajo(nuevoId, ordenTrabajo.getDescripcionSolicitud(),
                    ordenTrabajo.getFechaIngreso(), ordenTrabajo.getEstado());
        }

        // Verifica que no exista
        if (ordenTrabajoXmlData.getOrdenTrabajoPorId(ordenTrabajo.getIdOrdenTrabajo()).isPresent()) {
            return false;
        }

        ordenTrabajoXmlData.insertarOrdenTrabajo(ordenTrabajo, placaVehiculo);
        return true;
    }

    public List<OrdenTrabajo> buscarOrdenesTrabajoPorIdOPlaca(String query) {
        List<OrdenTrabajo> todas = ordenTrabajoXmlData.getTodasOrdenesTrabajo();
        List<OrdenTrabajo> filtradas = new ArrayList<>();

        for (OrdenTrabajo o : todas) {
            if (o.getIdOrdenTrabajo().equalsIgnoreCase(query) ||
                    (o.getVehiculo() != null && o.getVehiculo().getPlaca().equalsIgnoreCase(query))) {
                filtradas.add(o);
            }
        }

        return filtradas;
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

    public String obtenerNuevoIdOrdenTrabajo() {
        return ordenTrabajoXmlData.generarNuevoIdOrdenTrabajo();
    }

}
