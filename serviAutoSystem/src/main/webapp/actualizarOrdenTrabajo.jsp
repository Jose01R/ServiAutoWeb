<%--
  Created by IntelliJ IDEA.
  User: XT
  Date: 30/06/2025
  Time: 03:30 p. m.
  Para modificar una Orden de Trabajo
--%>
<%@ page import="domain.OrdenTrabajo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  OrdenTrabajo orden = (OrdenTrabajo) request.getAttribute("ordenTrabajo");
  String fechaIngreso = orden.getFechaIngreso() != null ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(orden.getFechaIngreso()) : "";
  String fechaDevolucion = orden.getFechaDevolucion() != null ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(orden.getFechaDevolucion()) : "";
%>
<html>
<head>
  <title>Modificar Orden de Trabajo</title>
  <style>
    body { background: #f4f8fb; font-family: Arial, sans-serif; }
    .form-container {
      max-width: 550px; margin: 40px auto; background: #fff; border-radius: 10px;
      box-shadow: 0 4px 16px rgba(55,82,108,0.08); padding: 32px 24px;
    }
    h2 { text-align: center; color: #37526c; }
    label { display: block; margin-top: 12px; font-weight: bold; }
    input[type="text"], input[type="date"] {
      width: 100%; padding: 8px; margin-top: 4px; border: 1px solid #d3e0e8; border-radius: 4px;
    }
    .btn-guardar {
      margin-top: 20px; width: 100%; background: #2596ff; color: #fff; border: none;
      padding: 10px; border-radius: 4px; font-size: 1em; font-weight: bold; cursor: pointer;
    }
    .btn-cancelar {
      margin-top: 10px; width: 100%; background: #e74c3c; color: #fff; border: none;
      padding: 10px; border-radius: 4px; font-size: 1em; font-weight: bold; cursor: pointer;
    }
  </style>
</head>
<body>
<div class="form-container">
  <h2>Modificar Orden de Trabajo</h2>
  <form method="post" action="ModificarOrdenTrabajoServlet">
    <input type="hidden" name="accion" value="guardarModificacion"/>
    <input type="hidden" name="idOrdenTrabajo" value="<%= orden.getIdOrdenTrabajo() %>"/>

    <label>Descripción de la Solicitud:</label>
    <input type="text" name="descripcionSolicitud" value="<%= orden.getDescripcionSolicitud() %>" required/>

    <label>Fecha de Ingreso:</label>
    <input type="date" name="fechaIngreso" value="<%= fechaIngreso %>" required/>

    <label>Estado:</label>
    <input type="text" name="estado" value="<%= orden.getEstado() %>" required/>

    <label>Fecha de Devolución:</label>
    <input type="date" name="fechaDevolucion" value="<%= fechaDevolucion %>" />

    <button class="btn-guardar" type="submit">Guardar Cambios</button>
    <button type="button" class="btn-cancelar" onclick="window.location.href='ModificarOrdenTrabajoServlet'">Cancelar</button>
  </form>
</div>
</body>
</html>
