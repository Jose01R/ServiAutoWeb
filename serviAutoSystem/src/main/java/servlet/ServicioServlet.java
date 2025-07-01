package servlet;

import com.serviautoweb.api.serviauto.system.util.ClienteSocketUtil;
import domain.Request; // Asegúrate de que esta ruta sea correcta
import domain.Response; // Asegúrate de que esta ruta sea correcta
import domain.Servicio; // Asegúrate de que esta ruta sea correcta
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet; // Esta anotación reemplaza la configuración en web.xml si la usas
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List; // Importar List para el cast

// Si usas esta anotación, no necesitas la configuración de este servlet en web.xml
// @WebServlet("/servicio")
public class ServicioServlet extends HttpServlet {
    private static final double PRECIO_MAXIMO = 1000000.0;
    private static final double COSTO_MANO_OBRA_MAXIMO = 500000.0;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");

            if (action == null || action.isEmpty()) {
                mostrarListaServicios(request, response);
                return;
            }

            switch (action) {
                case "nuevo":
                    // Para un nuevo servicio, el objeto 'servicio' no existe, se pasa null
                    request.setAttribute("servicio", null); // Asegura que el JSP sepa que es nuevo
                    request.getRequestDispatcher("servicioForm.jsp").forward(request, response);
                    break;
                case "editar":
                    editarServicio(request, response);
                    break;
                case "eliminar":
                    eliminarServicio(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/servicio"); // Redirige a la lista
            }
        } catch (Exception e) {
            // Un error general inesperado en GET
            manejarError(request, response, "Error en la operación: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Validar y obtener datos del formulario
            Servicio servicio = validarYCrearServicio(request);
            String action = request.getParameter("action");

            // Procesar la acción
            Response resp = procesarAccion(action, servicio);

            // Manejar la respuesta del servidor socket
            if ("500".equals(resp.getStatus())) {
                // Si el servidor socket devuelve un error 500
                throw new ServletException(resp.getMessage());
            }

            // Redirigir con mensaje de éxito a la lista de servicios
            request.getSession().setAttribute("success",
                    "Servicio " + (action.equals("crear") ? "creado" : "actualizado") + " correctamente");
            response.sendRedirect(request.getContextPath() + "/servicio");

        } catch (IllegalArgumentException e) {
            // Error de validación del formulario (datos incorrectos)
            request.setAttribute("error", e.getMessage());
            // Se debe volver a pasar el objeto servicio para que los campos se rellenen
            // con los valores que el usuario ingresó (incluso si son inválidos)
            request.setAttribute("servicio", new Servicio(
                    request.getParameter("nombre"),
                    // Intenta parsear los valores aunque sean inválidos para volver a mostrarlos
                    // O maneja NumberFormatException aquí si prefieres no mostrar el valor inválido
                    parseOrDefault(request.getParameter("precio"), 0.0),
                    parseOrDefault(request.getParameter("costoManoObra"), 0.0)
            ));
            request.getRequestDispatcher("servicioForm.jsp").forward(request, response);
        } catch (Exception e) {
            // Otro error inesperado en POST
            manejarError(request, response, "Error en el servidor: " + e.getMessage());
        }
    }

    private void editarServicio(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        if (nombre == null || nombre.trim().isEmpty()) {
            // Si no se proporciona un nombre válido para editar, redirige con error.
            request.getSession().setAttribute("error", "Nombre de servicio no válido para editar.");
            response.sendRedirect(request.getContextPath() + "/servicio");
            return;
        }

        Response resp = ClienteSocketUtil.enviarRequestAlServidor(
                new Request("buscarServicioPorNombre", nombre)
        );

        if ("500".equals(resp.getStatus())) {
            // Si el servidor socket devuelve un error o no encuentra el servicio
            request.getSession().setAttribute("error", "Error al obtener el servicio: " + resp.getMessage());
            response.sendRedirect(request.getContextPath() + "/servicio");
        } else if (resp.getData() == null) {
            // Si el servicio no se encuentra (data es null)
            request.getSession().setAttribute("error", "Servicio '" + nombre + "' no encontrado.");
            response.sendRedirect(request.getContextPath() + "/servicio");
        }
        else {
            request.setAttribute("servicio", (Servicio) resp.getData()); // Asegura el cast a Servicio
            request.getRequestDispatcher("servicioForm.jsp").forward(request, response);
        }
    }

    private void eliminarServicio(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        if (nombre == null || nombre.trim().isEmpty()) {
            request.getSession().setAttribute("error", "Nombre de servicio no válido para eliminar.");
            response.sendRedirect(request.getContextPath() + "/servicio");
            return;
        }

        Response respEliminar = ClienteSocketUtil.enviarRequestAlServidor(
                new Request("eliminarServicio", nombre)
        );

        if ("500".equals(respEliminar.getStatus())) {
            request.getSession().setAttribute("error",
                    "Error al eliminar el servicio: " + respEliminar.getMessage());
        } else {
            request.getSession().setAttribute("success", "Servicio eliminado correctamente");
        }
        response.sendRedirect(request.getContextPath() + "/servicio"); // Redirige a la lista
    }

    private void mostrarListaServicios(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Response resp = ClienteSocketUtil.enviarRequestAlServidor(
                new Request("obtenerTodosServicios", null)
        );

        if ("500".equals(resp.getStatus())) {
            request.setAttribute("error", "Error al obtener servicios: " + resp.getMessage());
            request.setAttribute("servicios", new java.util.ArrayList<Servicio>()); // Asegura una lista vacía para JSP
        } else {
            // Es crucial que resp.getData() sea un List<Servicio> o compatible
            request.setAttribute("servicios", (List<Servicio>) resp.getData());
        }

        // Obtener y limpiar mensajes de la sesión (flash messages)
        String success = (String) request.getSession().getAttribute("success");
        if (success != null) {
            request.setAttribute("success", success);
            request.getSession().removeAttribute("success");
        }
        String error = (String) request.getSession().getAttribute("error");
        if (error != null) {
            request.setAttribute("error", error);
            request.getSession().removeAttribute("error");
        }


        request.getRequestDispatcher("servicio.jsp").forward(request, response);
    }

    private Servicio validarYCrearServicio(HttpServletRequest request) {
        String nombre = request.getParameter("nombre");
        String precioStr = request.getParameter("precio");
        String costoManoObraStr = request.getParameter("costoManoObra");

        // Validaciones
        if (nombre == null || nombre.trim().isEmpty() || nombre.trim().length() < 3 || nombre.trim().length() > 100) {
            throw new IllegalArgumentException("El nombre debe tener entre 3 y 100 caracteres.");
        }
        // validación del patrón regex
        // if (!nombre.trim().matches("[A-Za-zÁáÉéÍíÓóÚúÑñ0-9 ]+")) {
        //     throw new IllegalArgumentException("El nombre solo puede contener letras, números y espacios.");
        // }


        double precio = validarValorNumerico(precioStr, "precio", PRECIO_MAXIMO);
        double costoManoObra = validarValorNumerico(costoManoObraStr, "costo de mano de obra", COSTO_MANO_OBRA_MAXIMO);

        return new Servicio(nombre.trim(), precio, costoManoObra);
    }

    private double validarValorNumerico(String valor, String campo, double maximo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("El " + campo + " no puede estar vacío.");
        }
        try {
            double numero = Double.parseDouble(valor);
            if (numero < 0 || numero > maximo) {
                throw new IllegalArgumentException(
                        String.format("El %s debe estar entre 0 y %,.2f", campo, maximo));
            }
            return numero;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El " + campo + " debe ser un número válido.");
        }
    }

    // Método auxiliar para parsear a double o devolver un valor por defecto
    private double parseOrDefault(String value, double defaultValue) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException | NullPointerException e) {
            return defaultValue;
        }
    }

    private Response procesarAccion(String action, Servicio servicio) {
        if ("crear".equals(action)) {
            return ClienteSocketUtil.enviarRequestAlServidor(
                    new Request("agregarServicio", servicio)
            );
        } else if ("actualizar".equals(action)) {
            return ClienteSocketUtil.enviarRequestAlServidor(
                    new Request("actualizarServicio", servicio)
            );
        } else {
            throw new IllegalArgumentException("Acción no válida: " + action);
        }
    }

    private void manejarError(HttpServletRequest request, HttpServletResponse response, String mensaje)
            throws ServletException, IOException {
        request.setAttribute("error", mensaje);
        // Si el error ocurre en un GET que va a la lista, no deberíamos ir a form.jsp
        // Si el error ocurre en un POST al form, deberíamos volver al form

        request.getRequestDispatcher("servicioForm.jsp").forward(request, response);
    }
}