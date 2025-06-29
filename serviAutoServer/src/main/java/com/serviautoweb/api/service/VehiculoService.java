package com.serviautoweb.api.service;


import dataManager.VehiculoXmlData;
import domain.Vehiculo;
import org.jdom2.JDOMException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class VehiculoService {
    private final VehiculoXmlData vehiculoXmlData;

    // Constructor que recibe la ruta del archivo XML
    public VehiculoService() throws IOException, JDOMException {
        this.vehiculoXmlData = VehiculoXmlData.abrirDocumento("WEB-INF/vehiculos.xml");
    }

    /**
     * Agrega un nuevo vehículo.
     * @param vehiculo El objeto Vehiculo a insertar.
     * @param idClienteDueno El ID del cliente dueño del vehículo.
     * @return true si se insertó correctamente, false si ya existe un vehículo con esa placa.
     */
    public boolean agregarVehiculo(Vehiculo vehiculo, String idClienteDueno) throws IOException {
        if (vehiculoXmlData.getVehiculoPorPlaca(vehiculo.getPlaca()).isPresent()) {
            return false;
        }
        vehiculoXmlData.insertarVehiculo(vehiculo, idClienteDueno);
        return true;
    }

    /**
     * Obtiene todos los vehículos.
     * @return lista de vehículos.
     */
    public List<Vehiculo> obtenerTodosVehiculos() {
        return vehiculoXmlData.getTodosVehiculos();
    }

    /**
     * Busca un vehículo por su placa.
     * @param placa placa del vehículo.
     * @return Optional con el vehículo si existe.
     */
    public Optional<Vehiculo> buscarVehiculoPorPlaca(String placa) {
        return vehiculoXmlData.getVehiculoPorPlaca(placa);
    }

    /**
     * Actualiza un vehículo existente.
     * @param vehiculoActualizado objeto Vehiculo con datos nuevos.
     * @return true si la actualización fue exitosa.
     */
    public boolean actualizarVehiculo(Vehiculo vehiculoActualizado) throws IOException {
        return vehiculoXmlData.actualizarVehiculo(vehiculoActualizado);
    }

    /**
     * Elimina un vehículo por su placa.
     * @param placa placa del vehículo.
     * @return true si se eliminó correctamente.
     */
    public boolean eliminarVehiculo(String placa) throws IOException {
        return vehiculoXmlData.eliminarVehiculo(placa);
    }
}
