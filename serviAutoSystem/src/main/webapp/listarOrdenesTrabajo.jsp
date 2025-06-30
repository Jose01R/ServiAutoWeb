<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="domain.OrdenTrabajo" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%
  List<OrdenTrabajo> listaOrdenes = (List<OrdenTrabajo>) request.getAttribute("listaOrdenes");
  String filtroId = request.getParameter("filtroId");
  String filtroEstado = request.getParameter("filtroEstado");
  SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
%>

<!DOCTYPE html>
<html>
<head>
  <title>Listado de Órdenes de Trabajo</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f2f8fc;
      padding: 20px;
    }

    .filtros {
      background-color: #ffffff;
      padding: 15px;
      margin-bottom: 20px;
      border-radius: 8px;
      box-shadow: 0 0 10px rgba(0,0,0,0.05);
    }

    .tabla {
      width: 100%;
      border-collapse: collapse;
    }

    .tabla th, .tabla td {
      padding: 10px;
      border: 1px solid #ccc;
    }

    .tabla th {
      background-color: #e0f2ff;
    }

    .acciones button {
      margin: 0 2px;
    }

    .boton-filtrar {
      background-color: #2980b9;
      color: white;
      border: none;
      padding: 8px 12px;
      cursor: pointer;
    }

    .boton-filtrar:hover {
      background-color: #1c6693;
    }
  </style>
</head>
<body>

<h2>Órdenes de Trabajo</h2>

<div class="filtros">
  <form method="get" action="OrdenTrabajoServlet">
    <input type="hidden" name="action" value="listar" />
    <label for="filtroId">ID Orden:</label>
    <input type="text" name="filtroId" value="<%= filtroId != null ? filtroId : "" %>" />
    &nbsp;&nbsp;
    <label for="filtroEstado">Estado:</label>
    <input type="text" name="filtroEstado" value="<%= filtroEstado != null ? filtroEstado : "" %>" />
    &nbsp;&nbsp;
    <input type="submit" class="boton-filtrar" value="Filtrar" />
  </form>
</div>

<table class="tabla">
  <thead>
  <tr>
    <th>ID</th>
    <th>Descripción</th>
    <th>Fecha Ingreso</th>
    <th>Fecha Devolución</th>
    <th>Estado</th>
    <th>Acciones</th>
  </tr>
  </thead>
  <tbody>
  <%
    if (listaOrdenes != null && !listaOrdenes.isEmpty()) {
      for (OrdenTrabajo orden : listaOrdenes) {
        if ((filtroId == null || orden.getIdOrdenTrabajo().contains(filtroId)) &&
                (filtroEstado == null || orden.getEstado().equalsIgnoreCase(filtroEstado))) {
  %>
  <tr>
    <td><%= orden.getIdOrdenTrabajo() %></td>
    <td><%= orden.getDescripcionSolicitud() %></td>
    <td><%= formato.format(orden.getFechaIngreso()) %></td>
    <td><%= orden.getFechaDevolucion() != null ? formato.format(orden.getFechaDevolucion()) : "Pendiente" %></td>
    <td><%= orden.getEstado() %></td>
    <td class="acciones">
      <!-- Aquí se colocarán los botones de acción más adelante -->
    </td>
  </tr>
  <%
      }
    }
  } else {
  %>
  <tr>
    <td colspan="6" style="text-align: center;">No hay órdenes de trabajo registradas.</td>
  </tr>
  <%
    }
  %>
  </tbody>
</table>

</body>
</html>
