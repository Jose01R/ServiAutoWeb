<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="domain.Servicio" %>
<%@ page import="domain.Repuesto" %>
<%
  List<Servicio> servicios = (List<Servicio>) request.getAttribute("listaServicios");
  List<Repuesto> repuestos = (List<Repuesto>) request.getAttribute("listaRepuestos");
%>
<!DOCTYPE html>
<html>
<head>
  <title>Registrar Detalles, Servicios y Repuestos</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f4f7f9;
      padding: 30px;
    }
    form {
      background-color: white;
      padding: 30px;
      max-width: 900px;
      margin: auto;
      border-radius: 10px;
      box-shadow: 0 0 10px rgba(0,0,0,0.1);
    }
    h2 {
      color: #2c3e50;
    }
    label, select, input, textarea {
      display: block;
      width: 100%;
      margin-bottom: 15px;
      font-size: 14px;
    }
    input, textarea, select {
      padding: 8px;
    }
    .button {
      background-color: #3498db;
      color: white;
      padding: 12px;
      border: none;
      font-weight: bold;
      cursor: pointer;
    }
    .button:hover {
      background-color: #2980b9;
    }
    .detalle-section {
      border: 1px solid #ccc;
      padding: 15px;
      margin-top: 20px;
      border-radius: 5px;
      background-color: #f9fbfd;
    }
  </style>
  <script>
    function agregarDetalle() {
      const contenedor = document.getElementById("detallesContainer");
      const nuevo = document.querySelector(".detalle-section").cloneNode(true);
      nuevo.querySelectorAll("input, select, textarea").forEach(e => e.value = "");
      contenedor.appendChild(nuevo);
    }
  </script>
</head>
<body>

<form action="DetalleOrdenServlet" method="post">
  <h2>Registrar Detalles para Orden de Trabajo</h2>

  <label for="idOrdenTrabajo">ID de Orden de Trabajo:</label>
  <input type="text" name="idOrdenTrabajo" required />

  <div id="detallesContainer">
    <div class="detalle-section">
      <h3>Detalle</h3>

      <label for="idDetalleOrden">ID Detalle:</label>
      <input type="text" name="idDetalleOrden" required />

      <label for="tipoDetalle">Tipo de Detalle:</label>
      <select name="tipoDetalle" required>
        <option value="Servicio">Servicio</option>
        <option value="Repuesto">Repuesto</option>
      </select>

      <label for="nombreServicio">Servicio:</label>
      <select name="nombreServicio">
        <option value="">-- Seleccionar Servicio --</option>
        <% for (Servicio s : servicios) { %>
        <option value="<%= s.getNombre() %>"><%= s.getNombre() %></option>
        <% } %>
      </select>

      <label for="nombreRepuesto">Repuesto:</label>
      <select name="nombreRepuesto">
        <option value="">-- Seleccionar Repuesto --</option>
        <% for (Repuesto r : repuestos) { %>
        <option value="<%= r.getNombre() %>"><%= r.getNombre() %></option>
        <% } %>
      </select>

      <label for="cantidad">Cantidad:</label>
      <input type="number" name="cantidad" min="1" required />

      <label for="idEstado">ID Estado:</label>
      <input type="text" name="idEstado" required />

      <label for="observaciones">Observaciones:</label>
      <textarea name="observaciones" rows="3"></textarea>
    </div>
  </div>

  <button type="button" class="button" onclick="agregarDetalle()">Agregar otro detalle</button>
  <br><br>
  <input type="submit" class="button" value="Guardar Detalles">
</form>

</body>
</html>
