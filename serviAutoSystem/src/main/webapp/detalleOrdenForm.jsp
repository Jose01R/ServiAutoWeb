<%@ page import="domain.DetalleOrden" %>
<%@ page import="java.util.List" %>
<%@ page import="domain.Servicio" %>
<%@ page import="domain.Repuesto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  DetalleOrden detalle = (DetalleOrden) request.getAttribute("detalleOrden");
  List<Servicio> servicios = (List<Servicio>) request.getAttribute("servicios");
  List<Repuesto> repuestos = (List<Repuesto>) request.getAttribute("repuestos");
  String action = (detalle != null) ? "actualizar" : "crear";
%>
<html>
<head>
  <title><%= (detalle != null) ? "Editar Detalle" : "Nuevo Detalle" %></title>
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
  <style>
    /* Variables CSS */
    :root {
      --primary-light: #6dd5ed;
      --primary-dark: #2193b0;
      --accent-light: #e0eafc;
      --accent-dark: #cfdef3;
      --text-dark: #37526c;
      --text-light: #ffffff;
      --bg-white: rgba(255, 255, 255, 0.97);
      --border-color: #ddd;
      --shadow-light: rgba(55, 82, 108, 0.10);
      --success-color: #28a745;
      --success-bg: #e6ffe6;
      --error-color: #dc3545;
      --error-bg: #ffe6e6;
    }

    body {
      margin: 0;
      padding: 20px;
      min-height: 100vh;
      font-family: 'Poppins', sans-serif;
      background: linear-gradient(135deg, var(--accent-light) 0%, var(--accent-dark) 100%);
      display: flex;
      justify-content: center;
      align-items: flex-start; /* Alinea al inicio verticalmente */
    }
    .container {
      max-width: 600px;
      width: 100%;
      margin: 20px auto;
      padding: 40px; /* Más padding para el formulario */
      background: var(--bg-white);
      border-radius: 18px;
      box-shadow: 0 10px 30px var(--shadow-light);
      animation: fadeIn 0.5s ease-out;
    }

    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(20px); }
      to { opacity: 1; transform: translateY(0); }
    }

    h1 {
      font-size: 2.2em;
      color: var(--text-dark);
      font-weight: 700;
      margin-bottom: 30px;
      text-align: center;
    }
    .form-group {
      margin-bottom: 25px; /* Más espacio entre grupos */
    }
    label {
      display: block;
      margin-bottom: 8px; /* Más espacio con el input */
      color: var(--text-dark);
      font-weight: 600;
      font-size: 1.05em;
    }
    input[type="text"],
    input[type="number"] {
      width: calc(100% - 24px); /* Ajusta por padding */
      padding: 12px; /* Más padding en inputs */
      border: 1px solid var(--border-color);
      border-radius: 10px; /* Bordes más redondeados */
      font-size: 1em;
      transition: border-color 0.3s ease, box-shadow 0.3s ease;
      box-shadow: inset 0 1px 3px rgba(0,0,0,0.05); /* Sombra interna sutil */
    }
    input[type="text"]:focus,
    input[type="number"]:focus {
      border-color: var(--primary-light);
      box-shadow: 0 0 0 3px rgba(33, 147, 176, 0.2); /* Sombra de enfoque */
      outline: none;
    }
    .btn-container {
      display: flex;
      gap: 15px; /* Más espacio entre botones */
      margin-top: 30px;
      justify-content: center; /* Centra los botones */
    }
    .btn {
      padding: 12px 30px; /* Más padding */
      border: none;
      border-radius: 10px;
      font-weight: 600;
      cursor: pointer;
      text-decoration: none;
      text-align: center;
      transition: all 0.3s ease;
      box-shadow: 0 4px 10px rgba(0,0,0,0.1);
    }
    .btn-primary {
      background: linear-gradient(120deg, var(--primary-light) 0%, var(--primary-dark) 100%);
      color: var(--text-light);
    }
    .btn-primary:hover {
      background: linear-gradient(120deg, var(--primary-dark) 0%, var(--primary-light) 100%);
      transform: translateY(-2px);
      box-shadow: 0 6px 15px rgba(0,0,0,0.15);
    }
    .btn-secondary {
      background: #f0f4f8; /* Fondo más suave */
      color: var(--text-dark);
      border: 1px solid var(--border-color);
    }
    .btn-secondary:hover {
      background: #e6edf3;
      transform: translateY(-2px);
      box-shadow: 0 6px 15px rgba(0,0,0,0.08);
    }

    /* Mensajes de feedback */
    .message-box {
      padding: 15px;
      border-radius: 10px;
      margin-bottom: 25px;
      font-weight: 500;
      display: flex;
      align-items: center;
      gap: 10px;
      box-shadow: 0 2px 8px rgba(0,0,0,0.05);
    }
    .success-message {
      color: var(--success-color);
      background-color: var(--success-bg);
      border: 1px solid #c3e6cb;
    }
    .error-message {
      color: var(--error-color);
      background-color: var(--error-bg);
      border: 1px solid #f5c6cb;
    }
    .input-error {
      border-color: var(--error-color) !important;
      box-shadow: 0 0 0 3px rgba(220, 53, 69, 0.2) !important;
    }
  </style>
</head>
<body>
<h2><%= (detalle != null) ? "Editar Detalle de Orden" : "Nuevo Detalle de Orden" %></h2>

<form action="DetalleOrdenServlet" method="post">
  <% if (detalle != null) { %>
  <input type="hidden" name="idDetalleOrden" value="<%= detalle.getIdDetalleOrden() %>"/>
  <% } %>

  <label>ID Orden de Trabajo:</label>
  <input type="text" name="idOrdenTrabajo" required value="<%= (detalle != null && detalle.getOrdenTrabajo() != null) ? detalle.getOrdenTrabajo().getIdOrdenTrabajo() : "" %>"/><br>

  <label>Cantidad:</label>
  <input type="number" name="cantidad" required value="<%= (detalle != null) ? detalle.getCantidad() : 1 %>"/><br>

  <label>Observaciones:</label>
  <textarea name="observaciones"><%= (detalle != null) ? detalle.getObservaciones() : "" %></textarea><br>

  <label>Tipo Detalle:</label>
  <input type="text" name="tipoDetalle" required value="<%= (detalle != null) ? detalle.getTipoDetalle() : "" %>"/><br>

  <label>ID Estado:</label>
  <input type="text" name="idEstado" required value="<%= (detalle != null) ? detalle.getIdEstado() : "" %>"/><br>

  <label>Servicio o Repuesto:</label>
  <select name="tipoDetalle">
    <option value="servicio">Servicio</option>
    <option value="repuesto">Repuesto</option>
  </select>

  <select name="nombreItem">
    <optgroup label="Servicios">
      <% for (Servicio s : servicios) { %>
      <option value="<%= s.getNombre() %>"><%= s.getNombre() %></option>
      <% } %>
    </optgroup>
    <optgroup label="Repuestos">
      <% for (Repuesto r : repuestos) { %>
      <option value="<%= r.getNombre() %>"><%= r.getNombre() %></option>
      <% } %>
    </optgroup>
  </select><br><br>

  <input type="hidden" name="action" value="<%= action %>"/>
  <button type="submit"><%= (detalle != null) ? "Actualizar" : "Registrar" %></button>
  <a href="DetalleOrdenServlet">Cancelar</a>
</form>
</body>
</html>