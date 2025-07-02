package servlet;

import com.serviautoweb.api.serviauto.system.util.ClienteSocketUtil;
import domain.DetalleOrden;
import domain.Request;
import domain.Response;
import domain.Servicio;
import domain.Repuesto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

public class DetalleOrdenServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("nuevo".equals(action)) {
            cargarServiciosYRepuestos(request);
            request.getRequestDispatcher("detalleOrdenForm.jsp").forward(request, response);
            return;
        }

        if ("editar".equals(action)) {
            String id = request.getParameter("id");
            Response resp = ClienteSocketUtil.enviarRequestAlServidor(new Request("buscarDetalleOrdenPorId", id));
            request.setAttribute("detalleOrden", resp.getData());
            cargarServiciosYRepuestos(request);
            request.getRequestDispatcher("detalleOrdenForm.jsp").forward(request, response);
            return;
        }

        if ("eliminar".equals(action)) {
            String id = request.getParameter("id");
            ClienteSocketUtil.enviarRequestAlServidor(new Request("eliminarDetalleOrden", id));
            response.sendRedirect("DetalleOrdenServlet");
            return;
        }

        // Por defecto: mostrar todos
        Response resp = ClienteSocketUtil.enviarRequestAlServidor(new Request("obtenerTodosDetallesOrden", null));
        request.setAttribute("detallesOrden", resp.getData());

        cargarServiciosYRepuestos(request);
        request.getRequestDispatcher("detalleOrden.jsp").forward(request, response);
    }

    private void cargarServiciosYRepuestos(HttpServletRequest request) {
        Response respServicios = ClienteSocketUtil.enviarRequestAlServidor(new Request("obtenerTodosServicios", null));
        Response respRepuestos = ClienteSocketUtil.enviarRequestAlServidor(new Request("obtenerTodosRepuestos", null));
        request.setAttribute("servicios", respServicios.getData());
        request.setAttribute("repuestos", respRepuestos.getData());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        String idDetalle = request.getParameter("idDetalleOrden");
        String idOrdenTrabajo = request.getParameter("idOrdenTrabajo");
        String nombreItem = request.getParameter("nombreItem");
        String tipoDetalle = request.getParameter("tipoDetalle");
        String idEstado = request.getParameter("idEstado");
        String observaciones = request.getParameter("observaciones");
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));

        DetalleOrden detalle = (idDetalle == null || idDetalle.isEmpty())
                ? new DetalleOrden(UUID.randomUUID().toString())
                : new DetalleOrden(idDetalle);
        detalle.setCantidad(cantidad);
        detalle.setObservaciones(observaciones);
        detalle.setTipoDetalle(tipoDetalle);
        detalle.setIdEstado(idEstado);

        Map<String, Object> datos = new HashMap<>();
        datos.put("detalleOrden", detalle);
        datos.put("idOrdenTrabajo", idOrdenTrabajo);

        // Determinar si es servicio o repuesto
        Response servicioResp = ClienteSocketUtil.enviarRequestAlServidor(new Request("buscarServicioPorNombre", nombreItem));
        if ("200".equals(servicioResp.getStatus())) {
            datos.put("nombreServicio", nombreItem);
            datos.put("nombreRepuesto", null);
        } else {
            datos.put("nombreServicio", null);
            datos.put("nombreRepuesto", nombreItem);
        }

        if ("crear".equals(action)) {
            ClienteSocketUtil.enviarRequestAlServidor(new Request("agregarDetalleOrden", datos));
        } else if ("actualizar".equals(action)) {
            ClienteSocketUtil.enviarRequestAlServidor(new Request("actualizarDetalleOrden", detalle));
        }

        response.sendRedirect("DetalleOrdenServlet");
    }
}
