package servlet;

import com.serviautoweb.api.serviauto.system.util.ClienteSocketUtil;
import domain.Request;
import domain.Response;
import domain.Repuesto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class RepuestoServlet extends HttpServlet {
    private static final double PRECIO_MAXIMO = 1000000.0;
    private static final int CANTIDAD_MAXIMA = 1000;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null || action.isEmpty()) {
                listarRepuestos(request, response);
                return;
            }

            switch (action) {
                case "nuevo":
                    mostrarFormulario(request, response, null);
                    break;
                case "editar":
                    editarRepuesto(request, response);
                    break;
                case "eliminar":
                    eliminarRepuesto(request, response);
                    break;
                default:
                    response.sendRedirect("repuesto");
                    break;
            }
        } catch (Exception e) {
            manejarError(request, response, "Error procesando la solicitud: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String nombre = validarNombre(request.getParameter("nombre"));
            double precio = validarPrecio(request.getParameter("precio"));
            int cantidad = validarCantidad(request.getParameter("cantidad"));
            boolean pedido = "true".equals(request.getParameter("pedido"));

            Repuesto repuesto = new Repuesto(nombre, precio, cantidad, pedido);
            String action = request.getParameter("action");

            Response resp;
            if ("crear".equals(action)) {
                resp = ClienteSocketUtil.enviarRequestAlServidor(
                        new Request("agregarRepuesto", repuesto)
                );
            } else if ("actualizar".equals(action)) {
                resp = ClienteSocketUtil.enviarRequestAlServidor(
                        new Request("actualizarRepuesto", repuesto)
                );
            } else {
                throw new IllegalArgumentException("Acción no válida");
            }

            if ("200".equals(resp.getStatus())) {
                request.getSession().setAttribute("success",
                        "crear".equals(action) ? "Repuesto creado exitosamente" : "Repuesto actualizado exitosamente");
                response.sendRedirect("repuesto");
            } else {
                request.setAttribute("error", resp.getMessage());
                request.setAttribute("repuesto", repuesto);
                request.getRequestDispatcher("repuestoForm.jsp").forward(request, response);
            }

        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("repuestoForm.jsp").forward(request, response);
        }
    }



    private void listarRepuestos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Response resp = ClienteSocketUtil.enviarRequestAlServidor(
                new Request("obtenerTodosRepuestos", null)
        );

        if ("500".equals(resp.getStatus())) {
            manejarError(request, response, "Error al obtener la lista de repuestos: " + resp.getMessage());
            return;
        }

        request.setAttribute("repuestos", resp.getData());
        request.getRequestDispatcher("repuesto.jsp").forward(request, response);
    }

    private void editarRepuesto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        if (nombre == null || nombre.trim().isEmpty()) {
            manejarError(request, response, "Nombre de repuesto no válido");
            return;
        }

        Response resp = ClienteSocketUtil.enviarRequestAlServidor(
                new Request("buscarRepuestoPorNombre", nombre)
        );

        //verificar específicamente el estado 200
        if ("200".equals(resp.getStatus())) {
            //Si encontró el repuesto, mostrarlo en el formulario
            mostrarFormulario(request, response, (Repuesto) resp.getData());
        } else {
            //Si hay error o no se encuentra, mostrar mensaje de error
            manejarError(request, response, "Error al obtener el repuesto: " + resp.getMessage());
        }
    }

    private void eliminarRepuesto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        if (nombre == null || nombre.trim().isEmpty()) {
            manejarError(request, response, "Nombre de repuesto no válido");
            return;
        }

        Response resp = ClienteSocketUtil.enviarRequestAlServidor(
                new Request("eliminarRepuesto", nombre)
        );

        if ("500".equals(resp.getStatus())) {
            request.getSession().setAttribute("error", "Error al eliminar el repuesto: " + resp.getMessage());
        } else {
            request.getSession().setAttribute("success", "Repuesto eliminado exitosamente");
        }

        response.sendRedirect("repuesto");
    }

    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response, Repuesto repuesto)
            throws ServletException, IOException {
        request.setAttribute("repuesto", repuesto);
        request.getRequestDispatcher("repuestoForm.jsp").forward(request, response);
    }

    private void manejarError(HttpServletRequest request, HttpServletResponse response, String mensaje)
            throws ServletException, IOException {
        request.setAttribute("error", mensaje);
        request.getRequestDispatcher("repuesto.jsp").forward(request, response);
    }

    private void manejarRespuesta(HttpServletRequest request, Response resp, String mensajeExito, String mensajeError) {
        HttpSession session = request.getSession();
        if ("500".equals(resp.getStatus())) {
            session.setAttribute("error", mensajeError + ": " + resp.getMessage());
        } else {
            session.setAttribute("success", mensajeExito);
        }
    }

    private String validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del repuesto es requerido");
        }
        if (nombre.length() < 3 || nombre.length() > 100) {
            throw new IllegalArgumentException("El nombre debe tener entre 3 y 100 caracteres");
        }
        return nombre.trim();
    }

    private double validarPrecio(String precioStr) {
        try {
            double precio = Double.parseDouble(precioStr);
            if (precio < 0 || precio > PRECIO_MAXIMO) {
                throw new IllegalArgumentException("El precio debe estar entre 0 y " + PRECIO_MAXIMO);
            }
            return precio;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Precio inválido");
        }
    }

    private int validarCantidad(String cantidadStr) {
        try {
            int cantidad = Integer.parseInt(cantidadStr);
            if (cantidad < 0 || cantidad > CANTIDAD_MAXIMA) {
                throw new IllegalArgumentException("La cantidad debe estar entre 0 y " + CANTIDAD_MAXIMA);
            }
            return cantidad;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Cantidad inválida");
        }
    }
}