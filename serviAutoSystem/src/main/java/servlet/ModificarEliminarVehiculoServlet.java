package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import domain.Request;
import domain.Response;
import domain.Vehiculo;
import com.serviautoweb.api.serviauto.system.util.ClienteSocketUtil;

import java.io.IOException;

public class ModificarEliminarVehiculoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String placa = req.getParameter("placa");
        if (placa != null) {
            // Buscar vehículo por placa y mostrar formulario de modificación
            Request request = new Request("obtenerVehiculoPorPlaca", placa);
            Response response = ClienteSocketUtil.enviarRequestAlServidor(request);
            if ("200".equals(response.getStatus())) {
                req.setAttribute("vehiculo", response.getData());
                req.getRequestDispatcher("actualizarVehiculo.jsp").forward(req, resp);
                return;
            } else {
                resp.sendRedirect("ModificarEliminarVehiculoServlet");
                return;
            }
        }
        // Mostrar lista de vehículos
        Request request = new Request("obtenerTodosVehiculos", null);
        Response response = ClienteSocketUtil.enviarRequestAlServidor(request);
        if ("200".equals(response.getStatus())) {
            req.setAttribute("listaVehiculos", response.getData());
        }
        req.getRequestDispatcher("modificarEliminarVehiculos.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accion = req.getParameter("accion");
        String placa = req.getParameter("placa");

        if ("eliminar".equals(accion)) {
            Request request = new Request("eliminarVehiculo", placa);
            ClienteSocketUtil.enviarRequestAlServidor(request);
            doGet(req, resp);
        } else if ("actualizar".equals(accion)) {
            resp.sendRedirect("ModificarEliminarVehiculoServlet?placa=" + placa);
        } else if ("guardarModificacion".equals(accion)) {
            Request buscarRequest = new Request("obtenerVehiculoPorPlaca", placa);
            Response buscarResponse = ClienteSocketUtil.enviarRequestAlServidor(buscarRequest);
            if (!"200".equals(buscarResponse.getStatus())) {
                resp.sendRedirect("ModificarEliminarVehiculoServlet");
                return;
            }
            Vehiculo original = (Vehiculo) buscarResponse.getData();

            // Actualizar los campos del vehículo
            String placaNueva = req.getParameter("placaNueva");
            original.setColor(req.getParameter("color"));
            original.setMarca(req.getParameter("marca"));
            original.setEstilo(req.getParameter("estilo"));
            original.setAnio(Integer.parseInt(req.getParameter("anio")));
            original.setVin(req.getParameter("vin"));
            original.setCilindraje(Double.parseDouble(req.getParameter("cilindraje")));

            // Cambiar la placa si es diferente
            if (placaNueva != null && !placaNueva.equals(original.getPlaca())) {
                // Asume que tienes un setter para la placa
                java.lang.reflect.Field field = null;
                try {
                    field = original.getClass().getDeclaredField("placa");
                    field.setAccessible(true);
                    field.set(original, placaNueva);
                } catch (Exception e) {
                    e.printStackTrace();
                    resp.sendRedirect("ModificarEliminarVehiculoServlet");
                    return;
                }
            }

            Request updateRequest = new Request("actualizarVehiculo", original);
            ClienteSocketUtil.enviarRequestAlServidor(updateRequest);
            resp.sendRedirect("ModificarEliminarVehiculoServlet");
        } else {
            doGet(req, resp);
        }
    }
}
