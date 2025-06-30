<%@ page import="java.util.List" %>
<%@ page import="domain.Cliente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Registrar Vehículo</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f1f9fc;
      padding: 30px;
    }
    form {
      background-color: white;
      padding: 30px;
      max-width: 600px;
      margin: auto;
      border-radius: 10px;
      box-shadow: 0 0 10px rgba(0,0,0,0.1);
    }
    input, label {
      display: block;
      width: 100%;
      margin-bottom: 15px;
    }
    input {
      padding: 10px;
      font-size: 16px;
    }
    .button {
      background-color: #27ae60;
      color: white;
      padding: 10px;
      font-weight: bold;
      border: none;
      cursor: pointer;
    }
    .button:hover {
      background-color: #219150;
    }
    .mensaje {
      color: green;
      text-align: center;
      font-weight: bold;
    }
    .combo-clientes {
      width: 100%;
      padding: 14px 12px;
      font-size: 18px;
      border-radius: 8px;
      border: 1.5px solid #27ae60;
      background-color: #f8fdfb;
      box-shadow: 0 2px 6px rgba(39,174,96,0.08);
      margin-bottom: 18px;
      transition: border-color 0.2s;
    }
    .combo-clientes:focus {
      border-color: #219150;
      outline: none;
      background-color: #eafaf1;
    }
  </style>
</head>
<body>

<% String mensaje = (String) request.getAttribute("mensaje"); %>
<% if (mensaje != null) { %>
<div class="mensaje"><%= mensaje %></div>
<% } %>

<form action="VehiculoServlet" method="post">
  <h2>Registro de Vehículo</h2>

  <label for="placa">Placa:</label>
  <input type="text" name="placa" required />

  <label for="color">Color:</label>
  <input type="text" name="color" required />

  <label for="marca">Marca:</label>
  <input type="text" name="marca" required />

  <label for="estilo">Estilo:</label>
  <input type="text" name="estilo" required />

  <label for="anio">Año:</label>
  <input type="number" name="anio" required min="1900" max="2100" />

  <label for="vin">VIN:</label>
  <input type="text" name="vin" required />

  <label for="cilindraje">Cilindraje:</label>
  <input type="number" step="0.1" name="cilindraje" required min="0" />

  <label for="idClienteDueno">Cliente Dueño:</label>
  <select name="idClienteDueno" required class="combo-clientes">
    <option value="">Seleccione un cliente</option>
    <%
      List clientes = (List) request.getAttribute("listaClientes");
      if (clientes != null) {
        for (Object obj : clientes) {
          Cliente cliente = (Cliente) obj;
    %>
    <option value="<%= cliente.getIdCliente() %>"><%= cliente.getNombre() %> (<%= cliente.getIdCliente() %>)</option>
    <%
        }
      }
    %>
  </select>

  <input type="submit" class="button" value="Registrar Vehículo" />
</form>

</body>
</html>
