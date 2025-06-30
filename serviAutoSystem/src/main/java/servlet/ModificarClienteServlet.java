// serviAutoSystem/src/main/java/servlet/ModificarClienteServlet.java
package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import domain.Request;
import domain.Response;
import domain.Cliente;
import com.serviautoweb.api.serviauto.system.util.ClienteSocketUtil;
import java.util.List;

public class ModificarClienteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idCliente = req.getParameter("idCliente");
        if (idCliente != null) {
            // Cargar datos del cliente para modificar
            Request request = new Request("buscarClientePorId", idCliente);
            Response response = ClienteSocketUtil.enviarRequestAlServidor(request);
            if ("200".equals(response.getStatus())) {
                req.setAttribute("cliente", response.getData());
                req.getRequestDispatcher("actualizarCliente.jsp").forward(req, resp);
                return;
            } else {
                // Si no se encuentra, vuelve a la lista
                resp.sendRedirect("ModificarClienteServlet");
                return;
            }
        }
        // Mostrar la lista de clientes
        Request request = new Request("obtenerTodosClientes", null);
        Response response = ClienteSocketUtil.enviarRequestAlServidor(request);
        if ("200".equals(response.getStatus())) {
            req.setAttribute("listaClientes", response.getData());
        }
        req.getRequestDispatcher("modificarClientes.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accion = req.getParameter("accion");
        String idCliente = req.getParameter("idCliente");

        if ("eliminar".equals(accion)) {
            Request request = new Request("eliminarCliente", idCliente);
            ClienteSocketUtil.enviarRequestAlServidor(request);
            doGet(req, resp);
        } else if ("actualizar".equals(accion)) {
            // Redirige al formulario de modificación con los datos cargados
            resp.sendRedirect("ModificarClienteServlet?idCliente=" + idCliente);
        } else if ("guardarModificacion".equals(accion)) {
            // Guardar los cambios del cliente
            Cliente cliente = new Cliente(
                    idCliente,
                    req.getParameter("nombre"),
                    req.getParameter("primerApellido"),
                    req.getParameter("segundoApellido"),
                    req.getParameter("telefono"),
                    req.getParameter("celular"),
                    req.getParameter("direccion"),
                    req.getParameter("email")
            );
            Request request = new Request("actualizarCliente", cliente);
            ClienteSocketUtil.enviarRequestAlServidor(request);
            resp.sendRedirect("ModificarClienteServlet");
        } else {
            doGet(req, resp);
        }
    }
}
