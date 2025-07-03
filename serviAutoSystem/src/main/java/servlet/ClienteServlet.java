/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import com.serviautoweb.api.serviauto.system.util.ClienteSocketUtil;
import domain.Cliente;
import domain.Request;
import domain.Response;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;


/**
 *
 * @author XT
 */
public class ClienteServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("registrarCliente.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // ClienteServlet.java
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idCliente = request.getParameter("idCliente");
        String nombre = request.getParameter("nombre");
        String apellido1 = request.getParameter("apellido1");
        String apellido2 = request.getParameter("apellido2");
        String telefono = request.getParameter("telefono");
        String celular = request.getParameter("celular");
        String direccion = request.getParameter("direccion");
        String email = request.getParameter("email");
        if (!idCliente.matches("\\d+")) {
            request.setAttribute("mensaje", "Error: El ID solo debe contener números.");
            request.getRequestDispatcher("registrarCliente.jsp").forward(request, response);
            return;
        }
        if (!telefono.matches("\\d+")) {
            request.setAttribute("mensaje", "Error: El teléfono solo debe contener números.");
            request.getRequestDispatcher("registrarCliente.jsp").forward(request, response);
            return;
        }
        if (!celular.matches("\\d+")) {
            request.setAttribute("mensaje", "Error: El celular solo debe contener números.");
            request.getRequestDispatcher("registrarCliente.jsp").forward(request, response);
            return;
        }
        //  Crear objeto Cliente
        Cliente cliente = new Cliente(idCliente, nombre, apellido1, apellido2, telefono, celular, direccion, email);

        // Crear Request y enviar al servidor
        Request req = new Request("agregarCliente", cliente);
        Response serverResp = ClienteSocketUtil.enviarRequestAlServidor(req);

        //Procesar respuesta y reenviar a JSP
        request.setAttribute("mensaje", serverResp.getMessage());
        request.getRequestDispatcher("registrarCliente.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
