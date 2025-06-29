package com.serviautoweb.api.service;


import dataManager.RepuestoXmlData;
import domain.Repuesto;
import org.jdom2.JDOMException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class RepuestoService {
    private final RepuestoXmlData repuestoXmlData;

    // Constructor que recibe la ruta del archivo XML
    public RepuestoService() throws IOException, JDOMException {
        this.repuestoXmlData = RepuestoXmlData.abrirDocumento("WEB-INF/repuestos.xml");
    }

    /**
     * Agrega un nuevo repuesto.
     * @param repuesto El objeto Repuesto a insertar.
     * @return true si se insertó, false si ya existe un repuesto con ese nombre.
     */
    public boolean agregarRepuesto(Repuesto repuesto) throws IOException {
        if (repuestoXmlData.getRepuestoPorNombre(repuesto.getNombre()).isPresent()) {
            return false;
        }
        repuestoXmlData.insertarRepuesto(repuesto);
        return true;
    }

    /**
     * Obtiene todos los repuestos.
     */
    public List<Repuesto> obtenerTodosRepuestos() {
        return repuestoXmlData.getTodosRepuestos();
    }

    /**
     * Busca un repuesto por su nombre.
     */
    public Optional<Repuesto> buscarRepuestoPorNombre(String nombre) {
        return repuestoXmlData.getRepuestoPorNombre(nombre);
    }

    /**
     * Actualiza un repuesto existente.
     */
    public boolean actualizarRepuesto(Repuesto repuestoActualizado) throws IOException {
        return repuestoXmlData.actualizarRepuesto(repuestoActualizado);
    }

    /**
     * Elimina un repuesto por su nombre.
     */
    public boolean eliminarRepuesto(String nombre) throws IOException {
        return repuestoXmlData.eliminarRepuesto(nombre);
    }
}
