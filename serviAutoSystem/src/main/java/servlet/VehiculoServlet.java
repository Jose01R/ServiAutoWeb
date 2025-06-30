package servlet;

import com.serviautoweb.api.serviauto.system.util.ClienteSocketUtil;
import domain.Cliente;
import domain.Request;
import domain.Response;
import domain.Vehiculo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class VehiculoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Obtener parámetros del formulario
        String placa = req.getParameter("placa");
        String color = req.getParameter("color");
        String marca = req.getParameter("marca");
        String estilo = req.getParameter("estilo");
        int anio = Integer.parseInt(req.getParameter("anio"));
        String vin = req.getParameter("vin");
        double cilindraje = Double.parseDouble(req.getParameter("cilindraje"));
        String idClienteDueno = req.getParameter("idClienteDueno");

        // Buscar el cliente por ID
        Request reqCliente = new Request("buscarClientePorId", idClienteDueno);
        Response respCliente = ClienteSocketUtil.enviarRequestAlServidor(reqCliente);

        if ("200".equals(respCliente.getStatus()) && respCliente.getData() instanceof Cliente) {
            Cliente dueno = (Cliente) respCliente.getData();

            Vehiculo vehiculo = new Vehiculo(placa, color, marca, estilo, anio, vin, cilindraje);
            vehiculo.setDueno(dueno);

            Map<String, Object> datosVehiculo = new HashMap<>();
            datosVehiculo.put("vehiculo", vehiculo);
            datosVehiculo.put("idClienteDueno", idClienteDueno);

            Request reqVehiculo = new Request("agregarVehiculo", datosVehiculo);
            Response respVehiculo = ClienteSocketUtil.enviarRequestAlServidor(reqVehiculo);

            if ("200".equals(respVehiculo.getStatus())) {
                req.setAttribute("mensaje", "Vehículo registrado correctamente.");
            } else {
                req.setAttribute("mensaje", "Error al registrar vehículo: " + respVehiculo.getMessage());
            }
        } else {
            req.setAttribute("mensaje", "No se encontró el cliente con ID: " + idClienteDueno);
        }

        // Cargar la lista de clientes actualizada
        Request reqClientes = new Request("obtenerTodosClientes", null);
        Response respClientes = ClienteSocketUtil.enviarRequestAlServidor(reqClientes);
        if ("200".equals(respClientes.getStatus())) {
            req.setAttribute("listaClientes", respClientes.getData());
        }


        req.getRequestDispatcher("registrarVehiculo.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Request reqClientes = new Request("obtenerTodosClientes", null);
        Response respClientes = ClienteSocketUtil.enviarRequestAlServidor(reqClientes);

        if ("200".equals(respClientes.getStatus())) {
            req.setAttribute("listaClientes", respClientes.getData());
        }

        req.getRequestDispatcher("registrarVehiculo.jsp").forward(req, resp);
    }
}
