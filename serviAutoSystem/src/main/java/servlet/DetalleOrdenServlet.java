package servlet;

import com.serviautoweb.api.serviauto.system.util.ClienteSocketUtil;
import domain.Request;
import domain.Response;
import domain.DetalleOrden;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DetalleOrdenServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null || action.isEmpty()) {
            // Obtener todos los detalles de orden
            Response resp = ClienteSocketUtil.enviarRequestAlServidor(
                    new Request("obtenerTodosDetallesOrden", null)
            );
            request.setAttribute("detallesOrden", resp.getData());

            // Obtener servicios y repuestos para los selectores
            Response respServicios = ClienteSocketUtil.enviarRequestAlServidor(
                    new Request("obtenerTodosServicios", null)
            );
            Response respRepuestos = ClienteSocketUtil.enviarRequestAlServidor(
                    new Request("obtenerTodosRepuestos", null)
            );

            request.setAttribute("servicios", respServicios.getData());
            request.setAttribute("repuestos", respRepuestos.getData());

            request.getRequestDispatcher("detalleOrden.jsp").forward(request, response);
            return;
        }

        switch (action) {
            case "nuevo":
                request.getRequestDispatcher("detalleOrdenForm.jsp").forward(request, response);
                break;
            case "editar":
                String id = request.getParameter("id");
                Response resp = ClienteSocketUtil.enviarRequestAlServidor(
                        new Request("buscarDetalleOrdenPorId", id)
                );
                request.setAttribute("detalleOrden", resp.getData());
                request.getRequestDispatcher("detalleOrdenForm.jsp").forward(request, response);
                break;
            case "eliminar":
                id = request.getParameter("id");
                ClienteSocketUtil.enviarRequestAlServidor(
                        new Request("eliminarDetalleOrden", id)
                );
                response.sendRedirect("DetalleOrdenServlet");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idOrdenTrabajo = request.getParameter("idOrdenTrabajo");
        String tipoDetalle = request.getParameter("tipoDetalle"); // "servicio" o "repuesto"
        String nombreItem = request.getParameter("nombreItem"); // nombre del servicio o repuesto

        Map<String, Object> datos = new HashMap<>();
        DetalleOrden detalle = new DetalleOrden();
        // Configurar el detalle según sea servicio o repuesto

        datos.put("detalleOrden", detalle);
        datos.put("idOrdenTrabajo", idOrdenTrabajo);
        if ("servicio".equals(tipoDetalle)) {
            datos.put("nombreServicio", nombreItem);
            datos.put("nombreRepuesto", null);
        } else {
            datos.put("nombreServicio", null);
            datos.put("nombreRepuesto", nombreItem);
        }

        String action = request.getParameter("action");
        if ("crear".equals(action)) {
            ClienteSocketUtil.enviarRequestAlServidor(
                    new Request("agregarDetalleOrden", datos)
            );
        } else if ("actualizar".equals(action)) {
            ClienteSocketUtil.enviarRequestAlServidor(
                    new Request("actualizarDetalleOrden", detalle)
            );
        }

        response.sendRedirect("DetalleOrdenServlet");
    }
}