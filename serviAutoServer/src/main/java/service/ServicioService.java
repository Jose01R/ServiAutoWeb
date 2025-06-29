package service;

import dataManager.ServicioXmlData;
import domain.Servicio;
import org.jdom2.JDOMException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ServicioService {
    private final ServicioXmlData servicioXmlData;

    // Constructor que recibe la ruta del archivo XML
    public ServicioService(String rutaArchivo) throws IOException, JDOMException {
        this.servicioXmlData = ServicioXmlData.abrirDocumento(rutaArchivo);
    }

    /**
     * Agrega un nuevo servicio.
     * @param servicio El objeto Servicio a insertar.
     * @return true si se insertó, false si ya existe un servicio con ese nombre.
     */
    public boolean agregarServicio(Servicio servicio) throws IOException {
        if (servicioXmlData.getServicioPorNombre(servicio.getNombre()).isPresent()) {
            return false;
        }
        servicioXmlData.insertarServicio(servicio);
        return true;
    }

    /**
     * Obtiene todos los servicios.
     */
    public List<Servicio> obtenerTodosServicios() {
        return servicioXmlData.getTodosServicios();
    }

    /**
     * Busca un servicio por su nombre.
     */
    public Optional<Servicio> buscarServicioPorNombre(String nombre) {
        return servicioXmlData.getServicioPorNombre(nombre);
    }

    /**
     * Actualiza un servicio existente.
     */
    public boolean actualizarServicio(Servicio servicioActualizado) throws IOException {
        return servicioXmlData.actualizarServicio(servicioActualizado);
    }

    /**
     * Elimina un servicio por su nombre.
     */
    public boolean eliminarServicio(String nombre) throws IOException {
        return servicioXmlData.eliminarServicio(nombre);
    }
}
