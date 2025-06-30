<%--
  Created by IntelliJ IDEA.
  User: XT
  Date: 30/06/2025
  Time: 02:49 p. m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="domain.Cliente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Cliente cliente = (Cliente) request.getAttribute("cliente");
%>
<html>
<head>
  <title>Modificar Cliente</title>
  <style>
    body { background: #f4f8fb; font-family: Arial, sans-serif; }
    .form-container {
      max-width: 500px; margin: 40px auto; background: #fff; border-radius: 10px;
      box-shadow: 0 4px 16px rgba(55,82,108,0.08); padding: 32px 24px;
    }
    h2 { text-align: center; color: #37526c; }
    label { display: block; margin-top: 12px; font-weight: bold; }
    input[type="text"], input[type="email"] {
      width: 100%; padding: 8px; margin-top: 4px; border: 1px solid #d3e0e8; border-radius: 4px;
    }
    .btn-guardar {
      margin-top: 20px; width: 100%; background: #2596ff; color: #fff; border: none;
      padding: 10px; border-radius: 4px; font-size: 1em; font-weight: bold; cursor: pointer;
    }
  </style>
</head>
<body>
<div class="form-container">
  <h2>Modificar Cliente</h2>
  <form method="post" action="ModificarClienteServlet">
    <input type="hidden" name="accion" value="guardarModificacion"/>
    <input type="hidden" name="idCliente" value="<%= cliente.getIdCliente() %>"/>
    <label>Nombre:</label>
    <input type="text" name="nombre" value="<%= cliente.getNombre() %>" required/>
    <label>Primer Apellido:</label>
    <input type="text" name="primerApellido" value="<%= cliente.getPrimerApellido() %>" required/>
    <label>Segundo Apellido:</label>
    <input type="text" name="segundoApellido" value="<%= cliente.getSegundoApellido() %>" required/>
    <label>Teléfono:</label>
    <input type="text" name="telefono" value="<%= cliente.getTelefono() %>" required/>
    <label>Celular:</label>
    <input type="text" name="celular" value="<%= cliente.getCelular() %>" required/>
    <label>Dirección:</label>
    <input type="text" name="direccion" value="<%= cliente.getDireccion() %>" required/>
    <label>Email:</label>
    <input type="email" name="email" value="<%= cliente.getEmail() %>" required/>
    <button class="btn-guardar" type="submit">Guardar Cambios</button>
  </form>
</div>
</body>
</html>
