<%--
  Created by IntelliJ IDEA.
  User: XT
  Date: 30/06/2025
  Time: 03:19 p. m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="domain.Vehiculo" %>
<%@ page import="domain.Cliente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Vehiculo vehiculo = (Vehiculo) request.getAttribute("vehiculo");
  Cliente dueno = vehiculo != null ? vehiculo.getDueno() : null;
%>
<html>
<head>
  <title>Modificar Vehículo</title>
  <style>
    body { background: #f4f8fb; font-family: Arial, sans-serif; }
    .form-container {
      max-width: 500px; margin: 40px auto; background: #fff; border-radius: 10px;
      box-shadow: 0 4px 16px rgba(55,82,108,0.08); padding: 32px 24px;
    }
    h2 { text-align: center; color: #37526c; }
    label { display: block; margin-top: 12px; font-weight: bold; }
    input[type="text"], input[type="number"] {
      width: 100%; padding: 8px; margin-top: 4px; border: 1px solid #d3e0e8; border-radius: 4px;
    }
    .btn-guardar {
      margin-top: 20px; width: 100%; background: #2596ff; color: #fff; border: none;
      padding: 10px; border-radius: 4px; font-size: 1em; font-weight: bold; cursor: pointer;
    }
    .readonly {
      background: #f6fafd;
    }

    .btn-cancelar {
      margin-top: 10px;
      width: 100%;
      background: #e74c3c;
      color: #fff;
      border: none;
      padding: 10px;
      border-radius: 4px;
      font-size: 1em;
      font-weight: bold;
      cursor: pointer;
    }
  </style>
</head>
<body>
<div class="form-container">
  <h2>Modificar Vehículo</h2>
  <form method="post" action="ModificarEliminarVehiculoServlet">
    <input type="hidden" name="accion" value="guardarModificacion"/>
    <input type="hidden" name="placa" value="<%= vehiculo.getPlaca() %>"/>
    <label>Placa:</label>
    <input type="text" name="placaNueva" value="<%= vehiculo.getPlaca() %>" required/>
    <label>Color:</label>
    <input type="text" name="color" value="<%= vehiculo.getColor() %>" required/>
    <label>Marca:</label>
    <input type="text" name="marca" value="<%= vehiculo.getMarca() %>" required/>
    <label>Estilo:</label>
    <input type="text" name="estilo" value="<%= vehiculo.getEstilo() %>" required/>
    <label>Año:</label>
    <input type="number" name="anio" value="<%= vehiculo.getAnio() %>" required min="1900" max="2100"/>
    <label>VIN:</label>
    <input type="text" name="vin" value="<%= vehiculo.getVin() %>" required/>
    <label>Cilindraje:</label>
    <input type="number" step="0.01" name="cilindraje" value="<%= vehiculo.getCilindraje() %>" required/>
    <label>Dueño:</label>
    <input type="text" value="<%= dueno != null ? dueno.getNombre() + " " + dueno.getPrimerApellido() : "Sin dueño" %>" class="readonly" readonly/>
    <button class="btn-guardar" type="submit">Guardar Cambios</button>
    <button type="button" class="btn-cancelar" onclick="window.location.href='ModificarEliminarVehiculoServlet'">Cancelar</button>
  </form>
</div>
</body>
</html>