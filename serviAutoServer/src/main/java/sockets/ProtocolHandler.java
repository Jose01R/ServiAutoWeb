package sockets;

import domain.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ClienteService;
import org.jdom2.JDOMException;
import service.DetalleOrdenService;
import service.OrdenTrabajoService;

import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.io.IOException;

public class ProtocolHandler {
    private static final Logger logger = LogManager.getLogger(ProtocolHandler.class);

    // Ruta al archivo XML de clientes
    private static final String CLIENTES_XML_PATH = "C:\\Users\\XT\\Documents\\Intellij\\ServiAutoWeb\\clientes.xml";
    private static final String DETALLE_ORDEN_XML_PATH = "C:\\Users\\XT\\Documents\\Intellij\\ServiAutoWeb\\detalleOrden.xml";
    private static final String Orden_Trabajo_XML_PATH = "C:\\Users\\XT\\Documents\\Intellij\\ServiAutoWeb\\ordenTrabajo.xml";
    private ClienteService clienteService;
    private DetalleOrdenService detalleOrdenService;
    private OrdenTrabajoService ordenTrabajoService;
    public ProtocolHandler() {
        try {
            this.clienteService = new ClienteService(CLIENTES_XML_PATH);
            this.detalleOrdenService = new DetalleOrdenService(DETALLE_ORDEN_XML_PATH);
            this.ordenTrabajoService = new OrdenTrabajoService(Orden_Trabajo_XML_PATH);
        } catch (IOException | JDOMException e) {
            logger.error("Error inicializando ClienteService: {}", e.getMessage());
            this.clienteService = null;
        }
    }

    public Response handle(Request request) {
        logger.debug("Handling request: {}", request.getAction());
        try {
            switch (request.getAction()) {
                case "test":
                    System.out.println("HELLO IM WORKING");
                    return new Response("200", "Test OK. ProtocolHandler funcionando.", "Hello from server!");

                //--------------Cliente Actions----------------
                case "insertarCliente": {
                    if (clienteService == null)
                        return new Response("500", "ClienteService no disponible", null);
                    if (!(request.getData() instanceof Cliente)) {
                        return new Response("400", "Datos de cliente inválidos", null);
                    }
                    Cliente cliente = (Cliente) request.getData();
                    // Comprobar si ya existe un cliente con ese ID
                    Optional<Cliente> existente = clienteService.buscarClientePorId(cliente.getIdCliente());
                    if (existente.isPresent()) {
                        return new Response("409", "Ya existe un cliente con ese ID", null);
                    }
                    clienteService.agregarCliente(cliente);
                    return new Response("200", "Cliente insertado correctamente", null);
                }

                case "obtenerTodosClientes": {
                    if (clienteService == null)
                        return new Response("500", "ClienteService no disponible", null);

                    List<Cliente> clientes = clienteService.obtenerTodosClientes();
                    return new Response("200", "Lista de clientes", clientes);
                }

                case "buscarClientePorId": {
                    if (clienteService == null)
                        return new Response("500", "ClienteService no disponible", null);

                    String id = (String) request.getData();
                    Optional<Cliente> clienteOpt = clienteService.buscarClientePorId(id);
                    if (clienteOpt.isPresent()) {
                        return new Response("200", "Cliente encontrado", clienteOpt.get());
                    } else {
                        return new Response("404", "Cliente no encontrado", null);
                    }
                }

                case "actualizarCliente": {
                    if (clienteService == null)
                        return new Response("500", "ClienteService no disponible", null);

                    if (!(request.getData() instanceof Cliente)) {
                        return new Response("400", "Datos de cliente inválidos", null);
                    }
                    Cliente cliente = (Cliente) request.getData();
                    boolean actualizado = clienteService.actualizarCliente(cliente);
                    if (actualizado) {
                        return new Response("200", "Cliente actualizado correctamente", null);
                    } else {
                        return new Response("404", "Cliente no encontrado para actualizar", null);
                    }
                }

                case "eliminarCliente": {
                    if (clienteService == null)
                        return new Response("500", "ClienteService no disponible", null);

                    String id = (String) request.getData();
                    boolean eliminado = clienteService.eliminarCliente(id);
                    if (eliminado) {
                        return new Response("200", "Cliente eliminado correctamente", null);
                    } else {
                        return new Response("404", "Cliente no encontrado para eliminar", null);
                    }
                }

                //--------------DetalleOrden Actions----------------
                case "agregarDetalleOrden": {
                    if (detalleOrdenService == null)
                        return new Response("500", "DetalleOrdenService no disponible", null);

                    // Se espera un Map o clase auxiliar con los datos necesarios
                    if (!(request.getData() instanceof Map)) {
                        return new Response("400", "Datos para detalle de orden inválidos", null);
                    }
                    Map<String, Object> datos = (Map<String, Object>) request.getData();
                    DetalleOrden detalle = (DetalleOrden) datos.get("detalleOrden");
                    String idOrdenTrabajo = (String) datos.get("idOrdenTrabajo");
                    String nombreServicio = (String) datos.get("nombreServicio");
                    String nombreRepuesto = (String) datos.get("nombreRepuesto");

                    boolean insertado = detalleOrdenService.agregarDetalleOrden(detalle, idOrdenTrabajo, nombreServicio, nombreRepuesto);
                    if (insertado) {
                        return new Response("200", "Detalle de orden insertado correctamente", null);
                    } else {
                        return new Response("409", "Ya existe un detalle de orden con ese ID", null);
                    }
                }

                case "obtenerTodosDetallesOrden": {
                    if (detalleOrdenService == null)
                        return new Response("500", "DetalleOrdenService no disponible", null);

                    List<DetalleOrden> detalles = detalleOrdenService.obtenerTodosDetallesOrden();
                    return new Response("200", "Lista de detalles de orden", detalles);
                }

                case "buscarDetalleOrdenPorId": {
                    if (detalleOrdenService == null)
                        return new Response("500", "DetalleOrdenService no disponible", null);

                    String id = (String) request.getData();
                    Optional<DetalleOrden> detalleOpt = detalleOrdenService.buscarDetalleOrdenPorId(id);
                    if (detalleOpt.isPresent()) {
                        return new Response("200", "Detalle de orden encontrado", detalleOpt.get());
                    } else {
                        return new Response("404", "Detalle de orden no encontrado", null);
                    }
                }

                case "actualizarDetalleOrden": {
                    if (detalleOrdenService == null)
                        return new Response("500", "DetalleOrdenService no disponible", null);

                    if (!(request.getData() instanceof DetalleOrden)) {
                        return new Response("400", "Datos de detalle de orden inválidos", null);
                    }
                    DetalleOrden detalle = (DetalleOrden) request.getData();
                    boolean actualizado = detalleOrdenService.actualizarDetalleOrden(detalle);
                    if (actualizado) {
                        return new Response("200", "Detalle de orden actualizado correctamente", null);
                    } else {
                        return new Response("404", "Detalle de orden no encontrado para actualizar", null);
                    }
                }

                case "eliminarDetalleOrden": {
                    if (detalleOrdenService == null)
                        return new Response("500", "DetalleOrdenService no disponible", null);

                    String id = (String) request.getData();
                    boolean eliminado = detalleOrdenService.eliminarDetalleOrden(id);
                    if (eliminado) {
                        return new Response("200", "Detalle de orden eliminado correctamente", null);
                    } else {
                        return new Response("404", "Detalle de orden no encontrado para eliminar", null);
                    }
                }
                //--------------Orden Trabajo Actions----------------
                case "agregarOrdenTrabajo": {
                    if (ordenTrabajoService == null)
                        return new Response("500", "OrdenTrabajoService no disponible", null);

                    if (!(request.getData() instanceof Map)) {
                        return new Response("400", "Datos para orden de trabajo inválidos", null);
                    }
                    Map<String, Object> datos = (Map<String, Object>) request.getData();
                    OrdenTrabajo ordenTrabajo = (OrdenTrabajo) datos.get("ordenTrabajo");
                    String placaVehiculo = (String) datos.get("placaVehiculo");

                    boolean insertado = ordenTrabajoService.agregarOrdenTrabajo(ordenTrabajo, placaVehiculo);
                    if (insertado) {
                        return new Response("200", "Orden de trabajo insertada correctamente", null);
                    } else {
                        return new Response("409", "Ya existe una orden de trabajo con ese ID", null);
                    }
                }

                case "obtenerTodasOrdenesTrabajo": {
                    if (ordenTrabajoService == null)
                        return new Response("500", "OrdenTrabajoService no disponible", null);

                    List<OrdenTrabajo> ordenes = ordenTrabajoService.obtenerTodasOrdenesTrabajo();
                    return new Response("200", "Lista de órdenes de trabajo", ordenes);
                }

                case "buscarOrdenTrabajoPorId": {
                    if (ordenTrabajoService == null)
                        return new Response("500", "OrdenTrabajoService no disponible", null);

                    String id = (String) request.getData();
                    Optional<OrdenTrabajo> ordenOpt = ordenTrabajoService.buscarOrdenTrabajoPorId(id);
                    if (ordenOpt.isPresent()) {
                        return new Response("200", "Orden de trabajo encontrada", ordenOpt.get());
                    } else {
                        return new Response("404", "Orden de trabajo no encontrada", null);
                    }
                }

                case "actualizarOrdenTrabajo": {
                    if (ordenTrabajoService == null)
                        return new Response("500", "OrdenTrabajoService no disponible", null);

                    if (!(request.getData() instanceof OrdenTrabajo)) {
                        return new Response("400", "Datos de orden de trabajo inválidos", null);
                    }
                    OrdenTrabajo orden = (OrdenTrabajo) request.getData();
                    boolean actualizado = ordenTrabajoService.actualizarOrdenTrabajo(orden);
                    if (actualizado) {
                        return new Response("200", "Orden de trabajo actualizada correctamente", null);
                    } else {
                        return new Response("404", "Orden de trabajo no encontrada para actualizar", null);
                    }
                }

                case "eliminarOrdenTrabajo": {
                    if (ordenTrabajoService == null)
                        return new Response("500", "OrdenTrabajoService no disponible", null);

                    String id = (String) request.getData();
                    boolean eliminado = ordenTrabajoService.eliminarOrdenTrabajo(id);
                    if (eliminado) {
                        return new Response("200", "Orden de trabajo eliminada correctamente", null);
                    } else {
                        return new Response("404", "Orden de trabajo no encontrada para eliminar", null);
                    }
                }
                default:
                    logger.warn("Acción no reconocida: {}", request.getAction());
                    return new Response("400", "Acción no reconocida: " + request.getAction(), null);
            }
        } catch (Exception e) {
            logger.error("Error handling request {}: {}", request.getAction(), e.getMessage());
            return new Response("500", "Internal Server Error: " + e.getMessage(), null);
        }
    }
}