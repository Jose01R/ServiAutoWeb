<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Registrar Repuesto</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #eef6f9;
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
    label, input, select {
      display: block;
      width: 100%;
      margin-bottom: 15px;
    }
    input, select {
      padding: 10px;
      font-size: 16px;
    }
    .button {
      background-color: #2ecc71;
      color: white;
      padding: 10px;
      font-weight: bold;
      border: none;
      cursor: pointer;
    }
    .button:hover {
      background-color: #27ae60;
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

<form action="RepuestoServlet" method="post">
  <h2>Registrar Repuesto</h2>

  <label for="nombre">Nombre del Repuesto:</label>
  <input type="text" name="nombre" required>

  <label for="precio">Precio:</label>
  <input type="number" name="precio" step="0.01" min="0" required>

  <label for="cantidad">Cantidad en Stock:</label>
  <input type="number" name="cantidad" min="0" required>

  <label for="pedido">¿Está pedido?</label>
  <select name="pedido" required>
    <option value="false">No</option>
    <option value="true">Sí</option>
  </select>

  <input type="submit" class="button" value="Registrar Repuesto">
</form>

</body>
</html>
