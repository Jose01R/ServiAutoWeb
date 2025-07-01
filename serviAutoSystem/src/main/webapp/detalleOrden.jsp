<%@ page import="java.util.List" %>
<%@ page import="domain.DetalleOrden" %><%-- Reemplaza 'com.yourpackage.model' con el paquete de tu clase DetalleOrden --%>
<%@ page import="domain.Servicio" %><%-- Reemplaza con tu paquete --%>
<%@ page import="domain.Repuesto" %><%-- Reemplaza con tu paquete --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- Se ha eliminado la directiva taglib para JSTL --%>
<html>
<head>
  <title>Gestión de Detalles de Orden</title>
  <style>
    body {
      margin: 0;
      padding: 20px;
      min-height: 100vh;
      font-family: 'Segoe UI', Arial, sans-serif;
      background: linear-gradient(135deg, #e0eafc 0%, #cfdef3 100%);
    }
    .container {
      max-width: 1000px;
      margin: 0 auto;
      padding: 20px;
      background: rgba(255,255,255,0.97);
      border-radius: 16px;
      box-shadow: 0 8px 32px rgba(55,82,108,0.10);
    }
    .header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 20px;
    }
    .title {
      font-size: 2em;
      color: #37526c;
    }
    .btn {
      padding: 10px 20px;
      border: none;
      border-radius: 8px;
      background: linear-gradient(120deg, #6dd5ed 0%, #2193b0 100%);
      color: white;
      font-weight: 600;
      cursor: pointer;
      text-decoration: none;
    }
    .btn:hover {
      background: linear-gradient(120deg, #2193b0 0%, #6dd5ed 100%);
    }
    table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 20px;
    }
    th, td {
      padding: 12px;
      text-align: left;
      border-bottom: 1px solid #ddd;
    }
    th {
      background-color: #f8f9fa;
      color: #37526c;
    }
    .action-links a {
      margin-right: 10px;
      color: #2193b0;
      text-decoration: none;
    }
    .action-links a:hover {
      text-decoration: underline;
    }
    .filter-section {
      margin-bottom: 20px;
      padding: 15px;
      background: #f8f9fa;
      border-radius: 8px;
    }
    .filter-section select {
      padding: 8px;
      margin-right: 10px;
      border-radius: 4px;
      border: 1px solid #ddd;
    }
  </style>
</head>
<body>
<div class="container">
  <div class="header">
    <h1 class="title">Detalles de Orden</h1>
    <a href="DetalleOrdenServlet?action=nuevo" class="btn">Nuevo Detalle</a>
  </div>

  <div class="filter-section">
    <form action="DetalleOrdenServlet" method="get">
      <select name="tipoFiltro" onchange="this.form.submit()">
        <option value="">Todos los detalles</option>
        <option value="servicio" <%= "servicio".equals(request.getParameter("tipoFiltro")) ? "selected" : "" %>>Solo servicios</option>
        <option value="repuesto" <%= "repuesto".equals(request.getParameter("tipoFiltro")) ? "selected" : "" %>>Solo repuestos</option>
      </select>
    </form>
  </div>

  <table>
    <thead>
    <tr>
      <th>ID</th>
      <th>Orden de Trabajo</th>
      <th>Tipo</th>
      <th>Detalle</th>
      <th>Precio</th>
      <th>Acciones</th>
    </tr>
    </thead>
    <tbody>
    <%
      // Se obtiene la lista de detalles de orden desde el request scope
      // Asegúrate de que tu Servlet esté pasando esta lista con el nombre "detallesOrden"
      List<DetalleOrden> detallesOrden = (List<DetalleOrden>) request.getAttribute("detallesOrden");

      if (detallesOrden != null && !detallesOrden.isEmpty()) {
        for (DetalleOrden detalle : detallesOrden) {
          String tipo = (detalle.getServicio() != null) ? "Servicio" : "Repuesto";
          String nombreDetalle = (detalle.getServicio() != null) ? detalle.getServicio().getNombre() : detalle.getRepuesto().getNombre();
          double precioDetalle = (detalle.getServicio() != null) ? detalle.getServicio().getPrecio() : detalle.getRepuesto().getPrecio();
    %>
    <tr>
      <td><%= detalle.getIdDetalleOrden() %></td>
      <td><%= detalle.getOrdenTrabajo() != null ? detalle.getOrdenTrabajo().getIdOrdenTrabajo() : "N/A" %></td>
      <td><%= tipo %></td>
      <td><%= nombreDetalle %></td>
      <td>$<%= String.format("%.2f", precioDetalle) %></td>
      <td class="action-links">
        <a href="DetalleOrdenServlet?action=editar&id=<%= detalle.getIdDetalleOrden() %>">Editar</a>
        <a href="DetalleOrdenServlet?action=eliminar&id=<%= detalle.getIdDetalleOrden() %>"
           onclick="return confirm('¿Está seguro de eliminar este detalle?')">Eliminar</a>
      </td>
    </tr>
    <%
      }
    } else {
    %>
    <tr>
      <td colspan="6">No hay detalles de orden disponibles.</td>
    </tr>
    <%
      }
    %>
    </tbody>
  </table>
</div>
</body>
</html>