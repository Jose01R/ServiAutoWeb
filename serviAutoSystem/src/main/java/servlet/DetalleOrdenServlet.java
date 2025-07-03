// ==============================
// 1. SERVLET: DetalleOrdenServlet.java
// ==============================
package servlet;

import com.serviautoweb.api.serviauto.system.util.ClienteSocketUtil;
import domain.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

public class DetalleOrdenServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        Map<String, List<?>> baseData = cargarDatosBase(request);
        List<Servicio> servicios = (List<Servicio>) baseData.get("servicios");
        List<Repuesto> repuestos = (List<Repuesto>) baseData.get("repuestos");
        List<OrdenTrabajo> ordenes = (List<OrdenTrabajo>) baseData.get("ordenesTrabajo");

        if ("nuevo".equals(action)) {
            request.getRequestDispatcher("detalleOrdenForm.jsp").forward(request, response);
            return;
        }

        if ("editar".equals(action)) {
            String id = request.getParameter("id");
            Response resp = ClienteSocketUtil.enviarRequestAlServidor(new Request("buscarDetalleOrdenPorId", id));
            DetalleOrden detalle = (DetalleOrden) resp.getData();
            asociarServicioORepuesto(detalle, servicios, repuestos);
            asociarOrdenTrabajo(detalle, ordenes);
            request.setAttribute("detalleOrden", detalle);
            request.getRequestDispatcher("detalleOrdenForm.jsp").forward(request, response);
            return;
        }

        if ("eliminar".equals(action)) {
            String id = request.getParameter("id");
            ClienteSocketUtil.enviarRequestAlServidor(new Request("eliminarDetalleOrden", id));
            response.sendRedirect("DetalleOrden");
            return;
        }

        Response resp = ClienteSocketUtil.enviarRequestAlServidor(new Request("obtenerTodosDetallesOrden", null));
        List<DetalleOrden> detalles = (List<DetalleOrden>) resp.getData();

        if (detalles != null) {
            for (DetalleOrden det : detalles) {
                asociarServicioORepuesto(det, servicios, repuestos);
                asociarOrdenTrabajo(det, ordenes);
            }
        }

        request.setAttribute("detallesOrden", detalles);
        request.getRequestDispatcher("detalleOrden.jsp").forward(request, response);
    }

    private Map<String, List<?>> cargarDatosBase(HttpServletRequest request) {
        Response respServicios = ClienteSocketUtil.enviarRequestAlServidor(new Request("obtenerTodosServicios", null));
        Response respRepuestos = ClienteSocketUtil.enviarRequestAlServidor(new Request("obtenerTodosRepuestos", null));
        Response respOrdenes = ClienteSocketUtil.enviarRequestAlServidor(new Request("obtenerTodasOrdenesTrabajo", null));

        List<Servicio> servicios = (List<Servicio>) respServicios.getData();
        List<Repuesto> repuestos = (List<Repuesto>) respRepuestos.getData();
        List<OrdenTrabajo> ordenes = (List<OrdenTrabajo>) respOrdenes.getData();

        request.setAttribute("servicios", servicios);
        request.setAttribute("repuestos", repuestos);
        request.setAttribute("ordenesTrabajo", ordenes);

        Map<String, List<?>> map = new HashMap<>();
        map.put("servicios", servicios);
        map.put("repuestos", repuestos);
        map.put("ordenesTrabajo", ordenes);
        return map;
    }

    private void asociarServicioORepuesto(DetalleOrden detalle, List<Servicio> servicios, List<Repuesto> repuestos) {
        if (detalle == null) return;
        if ("servicio".equalsIgnoreCase(detalle.getTipoDetalle())) {
            String nombre = detalle.getServicio() != null ? detalle.getServicio().getNombre() : null;
            if (nombre != null) {
                for (Servicio s : servicios) {
                    if (s.getNombre().equalsIgnoreCase(nombre)) {
                        detalle.setServicio(s);
                        break;
                    }
                }
            }
        } else {
            String nombre = detalle.getRepuesto() != null ? detalle.getRepuesto().getNombre() : null;
            if (nombre != null) {
                for (Repuesto r : repuestos) {
                    if (r.getNombre().equalsIgnoreCase(nombre)) {
                        detalle.setRepuesto(r);
                        break;
                    }
                }
            }
        }
    }

    private void asociarOrdenTrabajo(DetalleOrden detalle, List<OrdenTrabajo> ordenes) {
        if (detalle == null) return;
        for (OrdenTrabajo ot : ordenes) {
            if (ot.getIdOrdenTrabajo().equals(detalle.getIdOrdenTrabajo())) {
                detalle.setOrdenTrabajo(ot);
                break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String idDetalle = request.getParameter("idDetalleOrden");
        String idOrdenTrabajo = request.getParameter("idOrdenTrabajo");
        String tipoDetalle = request.getParameter("tipoDetalle");
        String idEstado = request.getParameter("idEstado");
        String observaciones = request.getParameter("observaciones");
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));

        String nombreItem = "servicio".equals(tipoDetalle) ? request.getParameter("servicioNombre") : request.getParameter("repuestoNombre");

        DetalleOrden detalle = (idDetalle == null || idDetalle.isEmpty()) ?
                new DetalleOrden((String) ClienteSocketUtil.enviarRequestAlServidor(new Request("generarIdDetalleOrden", null)).getData()) :
                new DetalleOrden(idDetalle);

        detalle.setCantidad(cantidad);
        detalle.setObservaciones(observaciones);
        detalle.setTipoDetalle(tipoDetalle);
        detalle.setIdEstado(idEstado);

        Map<String, Object> datos = new HashMap<>();
        datos.put("detalleOrden", detalle);
        datos.put("idOrdenTrabajo", idOrdenTrabajo);
        datos.put("nombreServicio", "servicio".equals(tipoDetalle) ? nombreItem : null);
        datos.put("nombreRepuesto", "repuesto".equals(tipoDetalle) ? nombreItem : null);

        Response resp;
        if ("crear".equals(action)) {
            resp = ClienteSocketUtil.enviarRequestAlServidor(new Request("agregarDetalleOrden", datos));
        } else {
            resp = ClienteSocketUtil.enviarRequestAlServidor(new Request("actualizarDetalleOrden", detalle));
        }

        if (!"200".equals(resp.getStatus())) {
            request.setAttribute("error", resp.getMessage());
            request.getRequestDispatcher("detalleOrdenForm.jsp").forward(request, response);
            return;
        }
        response.sendRedirect("DetalleOrden");
    }
}
