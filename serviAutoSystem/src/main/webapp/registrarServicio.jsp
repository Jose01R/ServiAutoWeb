<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Registrar Servicio</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f9f5e7;
      padding: 30px;
    }
    form {
      background-color: white;
      padding: 30px;
      max-width: 500px;
      margin: auto;
      border-radius: 10px;
      box-shadow: 0 0 10px rgba(0,0,0,0.1);
    }
    label, input {
      display: block;
      width: 100%;
      margin-bottom: 15px;
    }
    input {
      padding: 10px;
      font-size: 16px;
    }
    .button {
      background-color: #3498db;
      color: white;
      padding: 10px;
      font-weight: bold;
      border: none;
      cursor: pointer;
    }
    .button:hover {
      background-color: #2980b9;
    }
    .mensaje {
      text-align: center;
      color: green;
      font-weight: bold;
    }
  </style>
</head>
<body>

<% String mensaje = (String) request.getAttribute("mensaje"); %>
<% if (mensaje != null) { %>
<div class="mensaje"><%= mensaje %></div>
<% } %>

<form action="ServicioServlet" method="post">
  <h2>Registrar Servicio</h2>

  <label for="nombre">Nombre del Servicio:</label>
  <input type="text" name="nombre" required>

  <label for="precio">Precio del Servicio:</label>
  <input type="number" name="precio" step="0.01" min="0" required>

  <label for="costoManoObra">Costo Mano de Obra:</label>
  <input type="number" name="costoManoObra" step="0.01" min="0" required>

  <input type="submit" class="button" value="Registrar Servicio">
</form>

</body>
</html>
