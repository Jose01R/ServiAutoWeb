<%@ page import="java.util.List" %>
<%@ page import="domain.Cliente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Registrar Vehículo</title>
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
  <style>
    /* Variables CSS para reutilizar colores */
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
      --secondary-button-bg: #f0f4f8;
      --secondary-button-text: var(--text-dark);
      --secondary-button-border: #b7cbe3;
      --secondary-button-shadow: rgba(0,0,0,0.08);
      --secondary-button-hover-bg: #e6edf3;
      --secondary-button-hover-shadow: rgba(0,0,0,0.12);
    }

    body {
      margin: 0;
      padding: 20px;
      min-height: 100vh;
      font-family: 'Poppins', sans-serif;
      background: linear-gradient(135deg, var(--accent-light) 0%, var(--accent-dark) 100%);
      display: flex;
      flex-direction: column; /* Permite apilar el mensaje y el formulario */
      justify-content: center;
      align-items: center;
      box-sizing: border-box; /* Incluye padding en el tamaño total */
    }

    .container {
      max-width: 750px; /* Ancho ligeramente mayor para el formulario de vehículo */
      width: 100%;
      margin: 20px auto;
      padding: 40px;
      background: var(--bg-white);
      border-radius: 18px;
      box-shadow: 0 10px 30px var(--shadow-light);
      animation: fadeIn 0.5s ease-out;
      box-sizing: border-box;
    }

    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(20px); }
      to { opacity: 1; transform: translateY(0); }
    }

    .form-title {
      font-size: 2.2em;
      color: var(--text-dark);
      font-weight: 700;
      margin-bottom: 30px;
      text-align: center;
      padding-bottom: 15px;
      border-bottom: 1px solid var(--border-color);
    }

    /* --- NEW: Form fields grid container --- */
    .form-fields-grid {
      display: grid;
      grid-template-columns: 1fr; /* Default to one column for mobile first */
      gap: 20px; /* Space between form groups */
    }

    @media (min-width: 650px) { /* Apply two columns on screens 650px and wider */
      .form-fields-grid {
        grid-template-columns: repeat(2, 1fr); /* Two columns */
        gap: 25px 40px; /* Vertical and horizontal gap */
      }
    }
    /* --- END NEW --- */

    .form-group {
      margin-bottom: 0; /* Remove margin-bottom from form-group when inside grid */
    }

    label {
      display: block;
      margin-bottom: 8px;
      color: var(--text-dark);
      font-weight: 500;
      font-size: 0.95em;
    }

    input[type="text"],
    input[type="number"],
    select.combo-clientes { /* Aplicar estilos a select también */
      width: calc(100% - 24px); /* Ajusta por el padding */
      padding: 12px;
      border: 1px solid var(--border-color);
      border-radius: 8px;
      font-size: 1em;
      color: var(--text-dark);
      transition: border-color 0.3s ease, box-shadow 0.3s ease;
      box-sizing: border-box;
      -webkit-appearance: none; /* Remove default styling for select on Webkit */
      -moz-appearance: none;    /* Remove default styling for select on Firefox */
      appearance: none;         /* Remove default styling for select */
      background-image: url('data:image/svg+xml;charset=UTF-8,<svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="%2337526c" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-chevron-down"><polyline points="6 9 12 15 18 9"></polyline></svg>'); /* Custom arrow */
      background-repeat: no-repeat;
      background-position: right 12px center;
      background-size: 14px;
    }

    input[type="text"]:focus,
    input[type="number"]:focus,
    select.combo-clientes:focus {
      border-color: var(--primary-dark);
      box-shadow: 0 0 0 3px rgba(33, 147, 176, 0.2);
      outline: none;
    }

    /* Ajustes específicos para el campo de Cliente Dueño en el grid */
    .form-group.full-width {
      grid-column: 1 / -1; /* Ocupa ambas columnas */
    }

    .btn-group {
      display: flex;
      justify-content: flex-end; /* Alinea los botones a la derecha */
      gap: 15px;
      margin-top: 40px;
      padding-top: 20px;
      border-top: 1px solid var(--border-color);
    }

    .btn {
      padding: 12px 25px;
      border: none;
      border-radius: 10px;
      background: linear-gradient(120deg, var(--primary-light) 0%, var(--primary-dark) 100%);
      color: var(--text-light);
      font-weight: 600;
      cursor: pointer;
      text-decoration: none;
      transition: all 0.3s ease;
      box-shadow: 0 4px 10px rgba(0,0,0,0.1);
      display: inline-flex;
      align-items: center;
      gap: 8px;
      min-width: 160px; /* Ancho mínimo para los botones */
      justify-content: center;
    }
    .btn:hover {
      background: linear-gradient(120deg, var(--primary-dark) 0%, var(--primary-light) 100%);
      transform: translateY(-2px);
      box-shadow: 0 6px 15px rgba(0,0,0,0.15);
    }

    /* Estilo para el botón secundario (Volver) */
    .btn-secondary {
      background: var(--secondary-button-bg);
      color: var(--secondary-button-text);
      border: 1px solid var(--secondary-button-border);
      box-shadow: 0 2px 5px var(--secondary-button-shadow);
    }
    .btn-secondary:hover {
      background: var(--secondary-button-hover-bg);
      transform: translateY(-1px);
      box-shadow: 0 4px 8px var(--secondary-button-hover-shadow);
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
      width: 100%;
      max-width: 750px; /* Coincide con el ancho del formulario */
      box-sizing: border-box;
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

    /* Estilos para los iconos en botones */
    .btn i {
      margin-right: 5px;
    }

    /* Media Queries para responsividad */
    @media (max-width: 768px) {
      body {
        padding: 15px;
      }
      .container {
        padding: 25px;
        border-radius: 15px;
      }
      .form-title {
        font-size: 1.8em;
        margin-bottom: 25px;
      }
      .form-fields-grid {
        /* Defaults to 1fr already for mobile first */
        gap: 15px;
      }
      input[type="text"],
      input[type="number"],
      select.combo-clientes {
        padding: 10px;
        font-size: 0.95em;
      }
      .btn-group {
        flex-direction: column; /* Apila los botones en pantallas pequeñas */
        gap: 10px;
        justify-content: center;
        align-items: stretch;
        border-top: none; /* Remove top border if desired on small screens */
        padding-top: 0;
      }
      .btn {
        width: 100%; /* Ocupa el ancho completo */
        min-width: unset;
      }
      .message-box {
        padding: 12px;
        margin-bottom: 15px;
        font-size: 0.9em;
      }
    }

    @media (max-width: 480px) {
      body {
        padding: 10px;
      }
      .container {
        padding: 20px;
        border-radius: 12px;
      }
      .form-title {
        font-size: 1.6em;
        margin-bottom: 20px;
      }
      label {
        font-size: 0.9em;
      }
      input[type="text"],
      input[type="number"],
      select.combo-clientes {
        padding: 8px;
        font-size: 0.85em;
      }
      .btn {
        padding: 10px;
        font-size: 0.9em;
      }
      .message-box {
        padding: 10px;
        font-size: 0.85em;
      }
    }
  </style>
</head>
<body>

<%
  String mensaje = (String) request.getAttribute("mensaje");
  // Asumimos que si hay un "error" en el mensaje, queremos un estilo de error
  boolean isErrorMessage = (mensaje != null && mensaje.toLowerCase().contains("error"));
%>

<% if (mensaje != null && !mensaje.isEmpty()) { %>
<div class="message-box <%= isErrorMessage ? "error-message" : "success-message" %>">
  <i class="fas <%= isErrorMessage ? "fa-times-circle" : "fa-check-circle" %>"></i>
  <%= mensaje %>
</div>
<% } %>

<div class="container">
  <form action="<%= request.getContextPath() %>/VehiculoServlet" method="post">
    <h2 class="form-title">Registro de Vehículo</h2>

    <div class="form-fields-grid">
      <div class="form-group">
        <label for="placa">Placa:</label>
        <input type="text" id="placa" name="placa" required />
      </div>

      <div class="form-group">
        <label for="color">Color:</label>
        <input type="text" id="color" name="color" required />
      </div>

      <div class="form-group">
        <label for="marca">Marca:</label>
        <input type="text" id="marca" name="marca" required />
      </div>

      <div class="form-group">
        <label for="estilo">Estilo:</label>
        <input type="text" id="estilo" name="estilo" required />
      </div>

      <div class="form-group">
        <label for="anio">Año:</label>
        <input type="number" id="anio" name="anio" required min="1900" max="2100" />
      </div>

      <div class="form-group">
        <label for="vin">VIN:</label>
        <input type="text" id="vin" name="vin" required />
      </div>

      <div class="form-group">
        <label for="cilindraje">Cilindraje:</label>
        <input type="number" id="cilindraje" step="0.1" name="cilindraje" required min="0" />
      </div>

      <div class="form-group full-width"> <%-- Este campo ocupará todo el ancho en el grid --%>
        <label for="idClienteDueno">Cliente Dueño:</label>
        <select id="idClienteDueno" name="idClienteDueno" required class="combo-clientes">
          <option value="">Seleccione un cliente</option>
          <%
            List<Cliente> clientes = (List<Cliente>) request.getAttribute("listaClientes");
            if (clientes != null) {
              for (Cliente cliente : clientes) {
          %>
          <option value="<%= cliente.getIdCliente() %>"><%= cliente.getNombre() %> (<%= cliente.getIdCliente() %>)</option>
          <%
              }
            }
          %>
        </select>
      </div>
    </div>

    <div class="btn-group">
      <button type="submit" class="btn">
        <i class="fas fa-save"></i> Registrar Vehículo
      </button>
      <a href="<%= request.getContextPath() %>/SeleccionAccionVehiculoServlet" class="btn btn-secondary">
        <i class="fas fa-arrow-alt-circle-left"></i> Volver
      </a>
    </div>
  </form>
</div>

<script>
  // Auto-ocultar mensajes de feedback después de unos segundos
  window.onload = function() {
    const messages = document.querySelectorAll('.message-box');
    messages.forEach(message => {
      if (message.textContent.trim() !== '') {
        setTimeout(() => {
          message.style.display = 'none';
        }, 5000); // Ocultar después de 5 segundos
      }
    });
  };
</script>
</body>
</html>