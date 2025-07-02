// OrdenTrabajoServlet.java
package servlet;

import com.serviautoweb.api.serviauto.system.util.ClienteSocketUtil;
import domain.OrdenTrabajo;
import domain.Request;
import domain.Response;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrdenTrabajoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("registrarOrdenTrabajo.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String descripcion = request.getParameter("descripcionSolicitud");
            String fechaIngresoStr = request.getParameter("fechaIngreso");
            String estado = request.getParameter("estado");
            String placa = request.getParameter("placaVehiculo");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaIngreso = sdf.parse(fechaIngresoStr);

            OrdenTrabajo orden = new OrdenTrabajo(null, descripcion, fechaIngreso, estado);

            Map<String, Object> datos = new HashMap<>();
            datos.put("ordenTrabajo", orden);
            datos.put("placaVehiculo", placa);

            Request req = new Request("agregarOrdenTrabajo", datos);
            Response resp = ClienteSocketUtil.enviarRequestAlServidor(req);

            request.setAttribute("mensaje", resp.getMessage());
        } catch (Exception e) {
            request.setAttribute("mensaje", "Error al registrar orden de trabajo: " + e.getMessage());
        }
        request.getRequestDispatcher("registrarOrdenTrabajo.jsp").forward(request, response);
    }
}
