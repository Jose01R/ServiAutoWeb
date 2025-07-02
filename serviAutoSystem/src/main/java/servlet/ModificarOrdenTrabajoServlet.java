
package servlet;

import com.serviautoweb.api.serviauto.system.util.ClienteSocketUtil;
import domain.OrdenTrabajo;
import domain.Request;
import domain.Response;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ModificarOrdenTrabajoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idOrdenTrabajo = req.getParameter("idOrdenTrabajo");
        String searchQuery = req.getParameter("searchQuery");

        if (idOrdenTrabajo != null) {
            Request request = new Request("buscarOrdenTrabajoPorId", idOrdenTrabajo);
            Response response = ClienteSocketUtil.enviarRequestAlServidor(request);
            if ("200".equals(response.getStatus())) {
                req.setAttribute("ordenTrabajo", response.getData());
                req.getRequestDispatcher("actualizarOrdenTrabajo.jsp").forward(req, resp);
                return;
            } else {
                resp.sendRedirect("ModificarOrdenTrabajoServlet");
                return;
            }
        }

        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            Request request = new Request("buscarOrdenesTrabajo", searchQuery.trim());
            Response response = ClienteSocketUtil.enviarRequestAlServidor(request);

            if ("200".equals(response.getStatus())) {
                req.setAttribute("listaOrdenes", response.getData());
                req.setAttribute("searchQuery", searchQuery);
            } else {
                req.setAttribute("mensaje", "No se encontraron resultados para la búsqueda");
            }

        } else {
            Request request = new Request("obtenerTodasOrdenesTrabajo", null);
            Response response = ClienteSocketUtil.enviarRequestAlServidor(request);
            if ("200".equals(response.getStatus())) {
                req.setAttribute("listaOrdenes", response.getData());
            }
        }

        req.getRequestDispatcher("modificarOrdenTrabajo.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accion = req.getParameter("accion");
        String idOrdenTrabajo = req.getParameter("idOrdenTrabajo");

        if ("eliminar".equals(accion)) {
            Request request = new Request("eliminarOrdenTrabajo", idOrdenTrabajo);
            ClienteSocketUtil.enviarRequestAlServidor(request);
            doGet(req, resp);
        } else if ("actualizar".equals(accion)) {
            resp.sendRedirect("ModificarOrdenTrabajoServlet?idOrdenTrabajo=" + idOrdenTrabajo);
        } else if ("guardarModificacion".equals(accion)) {
            try {
                String descripcion = req.getParameter("descripcionSolicitud");
                String fechaIngresoStr = req.getParameter("fechaIngreso");
                String estado = req.getParameter("estado");
                String fechaDevolucionStr = req.getParameter("fechaDevolucion");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date fechaIngreso = sdf.parse(fechaIngresoStr);
                Date fechaDevolucion = null;

                if (fechaDevolucionStr != null && !fechaDevolucionStr.trim().isEmpty()) {
                    fechaDevolucion = sdf.parse(fechaDevolucionStr);
                }

                OrdenTrabajo ordenActualizada = new OrdenTrabajo(idOrdenTrabajo, descripcion, fechaIngreso, estado);
                ordenActualizada.setFechaDevolucion(fechaDevolucion);

                Request request = new Request("actualizarOrdenTrabajo", ordenActualizada);
                ClienteSocketUtil.enviarRequestAlServidor(request);
            } catch (Exception e) {
                req.setAttribute("mensaje", "Error al actualizar la orden: " + e.getMessage());
                doGet(req, resp);
                return;
            }

            resp.sendRedirect("ModificarOrdenTrabajoServlet");
        } else {
            doGet(req, resp);
        }
    }
}
