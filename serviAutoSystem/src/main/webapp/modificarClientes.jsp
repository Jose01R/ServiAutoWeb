<%@ page import="domain.Cliente" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  List<Cliente> clientes = (List<Cliente>) request.getAttribute("listaClientes");
  String mensaje = (String) request.getAttribute("mensaje");
  // Busca mensajes pasados como parámetro de URL (después de un redirect)
  if (mensaje == null || mensaje.isEmpty()) {
    mensaje = request.getParameter("mensaje");
  }
  boolean isErrorMessage = (mensaje != null && mensaje.toLowerCase().contains("error"));
%>
<html>
<head>
  <title>Gestión de Clientes</title>
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
      --border-color: #d3e0e8;
      --shadow-light: rgba(55, 82, 108, 0.10);
      --success-color: #28a745;
      --success-bg: #e6ffe6;
      --error-color: #dc3545;
      --error-bg: #ffe6e6;
      --modificar-btn: #2596ff;
      --eliminar-btn: #e74c3c;
      --secondary-button-bg: #f0f4f8;
      --secondary-button-text: var(--text-dark);
      --secondary-button-border: #b7cbe3;
      --secondary-button-shadow: rgba(0,0,0,0.08);
      --secondary-button-hover-bg: #e6edf3;
      --secondary-button-hover-shadow: rgba(0,0,0,0.12);
      --menu-btn-bg: #4CAF50;
      --menu-btn-hover-bg: #45a049;
    }

    body {
      margin: 0;
      padding: 20px;
      min-height: 100vh;
      font-family: 'Poppins', sans-serif;
      background: linear-gradient(135deg, var(--accent-light) 0%, var(--accent-dark) 100%);
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: flex-start;
      box-sizing: border-box;
    }

    .main-title {
      font-size: 3em;
      font-weight: 800;
      color: var(--text-dark);
      margin-bottom: 40px;
      letter-spacing: 2px;
      text-shadow: 0 5px 15px rgba(211, 224, 232, 0.8);
      text-align: center;
      animation: fadeInDown 0.8s ease-out;
    }

    @keyframes fadeInDown {
      from { opacity: 0; transform: translateY(-40px); }
      to { opacity: 1; transform: translateY(0); }
    }

    .container {
      width: 95%;
      max-width: 1400px;
      background: var(--bg-white);
      border-radius: 18px;
      box-shadow: 0 10px 30px var(--shadow-light);
      padding: 40px;
      box-sizing: border-box;
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
      max-width: 1400px;
      box-sizing: border-box;
      animation: fadeIn 0.5s ease-out;
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

    .message-box i {
      font-size: 1.2em;
    }

    /* Filtros y búsqueda */
    .filter-section {
      display: flex;
      flex-wrap: wrap;
      gap: 20px;
      margin-bottom: 30px;
      padding-bottom: 20px;
      border-bottom: 1px solid var(--border-color);
      width: 100%;
      justify-content: flex-end;
      align-items: center;
    }

    .filter-group {
      display: flex;
      align-items: center;
      gap: 10px;
      flex-grow: 1;
      justify-content: flex-end;
      flex-wrap: wrap;
    }

    .filter-group label {
      color: var(--text-dark);
      font-weight: 500;
      white-space: nowrap;
    }

    .filter-section input[type="text"] {
      padding: 10px 15px;
      border: 1px solid var(--border-color);
      border-radius: 8px;
      font-size: 0.95em;
      color: var(--text-dark);
      transition: border-color 0.3s ease, box-shadow 0.3s ease;
      width: 180px;
    }
    .filter-section input[type="text"]:focus {
      border-color: var(--primary-dark);
      box-shadow: 0 0 0 3px rgba(33, 147, 176, 0.2);
      outline: none;
    }

    .btn-filter {
      background: var(--primary-dark);
      color: var(--text-light);
      padding: 10px 20px;
      border: none;
      border-radius: 8px;
      cursor: pointer;
      font-weight: 600;
      transition: background 0.3s ease, transform 0.2s ease;
      display: flex;
      align-items: center;
      gap: 8px;
      white-space: nowrap;
    }
    .btn-filter:hover {
      background: var(--primary-light);
      transform: translateY(-2px);
    }

    /* Estilos de la tabla */
    .table-wrapper {
      width: 100%;
      overflow-x: auto;
      margin-bottom: 30px;
      border: 1px solid var(--border-color);
      border-radius: 12px;
      box-shadow: 0 5px 15px rgba(0,0,0,0.05);
    }

    table {
      width: 100%;
      border-collapse: separate;
      border-spacing: 0;
      background: #fff;
      min-width: 1000px;
    }

    th, td {
      padding: 12px 18px;
      text-align: left;
      font-size: 0.95em;
      border-bottom: 1px solid var(--border-color);
      border-right: 1px solid var(--border-color);
    }

    th {
      background: var(--accent-light);
      font-weight: 700;
      color: var(--text-dark);
      text-align: center;
      position: sticky;
      top: 0;
      z-index: 1;
    }

    th:last-child, td:last-child {
      border-right: none;
    }

    tr:last-child td {
      border-bottom: none;
    }

    tbody tr:hover {
      background-color: #f9fbfd;
    }

    /* Acciones (botones dentro de la tabla) */
    .acciones {
      display: flex;
      gap: 8px;
      justify-content: center;
      align-items: center;
    }

    .btn-action {
      border: none;
      color: white;
      padding: 8px 12px;
      border-radius: 8px;
      cursor: pointer;
      font-size: 0.9em;
      font-weight: 600;
      transition: all 0.2s ease;
      display: inline-flex;
      align-items: center;
      gap: 5px;
      box-shadow: 0 2px 5px rgba(0,0,0,0.1);
    }
    .btn-action:hover {
      transform: translateY(-1px);
      box-shadow: 0 4px 8px rgba(0,0,0,0.15);
    }

    .btn-modificar {
      background: var(--modificar-btn);
    }
    .btn-eliminar {
      background: var(--eliminar-btn);
    }

    /* Contenedor de Botón de volver al menú principal - POSICIONADO FIJO */
    .button-main-menu-container {
      position: fixed;
      bottom: 30px;
      right: 30px;
      z-index: 1000;
      box-shadow: 0 8px 16px rgba(0,0,0,0.2);
      border-radius: 12px;
      display: flex;
      gap: 10px;
    }

    .btn-menu {
      background: var(--menu-btn-bg);
      color: var(--text-light);
      padding: 15px 30px;
      border: none;
      border-radius: 12px;
      font-weight: 600;
      cursor: pointer;
      text-decoration: none;
      transition: all 0.3s ease;
      display: inline-flex;
      align-items: center;
      gap: 10px;
      box-shadow: 0 4px 8px rgba(0,0,0,0.1);
      white-space: nowrap;
    }
    .btn-menu:hover {
      background: var(--menu-btn-hover-bg);
      transform: translateY(-2px);
      box-shadow: 0 6px 12px rgba(0,0,0,0.2);
    }
    .btn-menu i {
      font-size: 1.2em;
    }

    /* Contenedor del botón "Volver al Menú Clientes" - POSICIONADO AL FINAL DEL CONTENIDO */
    .button-back-container {
      margin-top: 50px;
      width: 100%;
      max-width: 1400px;
      display: flex;
      justify-content: center;
      animation: fadeIn 0.8s ease-out 0.4s forwards;
    }

    .btn-back {
      padding: 14px 28px;
      border: none;
      border-radius: 12px;
      font-weight: 600;
      cursor: pointer;
      text-decoration: none;
      transition: all 0.3s ease;
      box-shadow: 0 2px 8px var(--secondary-button-shadow);
      display: inline-flex;
      align-items: center;
      gap: 8px;
      background: var(--secondary-button-bg);
      color: var(--secondary-button-text);
      border: 1px solid var(--secondary-button-border);
    }
    .btn-back:hover {
      background: var(--secondary-button-hover-bg);
      transform: translateY(-2px);
      box-shadow: 0 4px 12px var(--secondary-button-hover-shadow);
    }


    /* Media Queries para Responsividad */
    @media (max-width: 1200px) {
      .container {
        padding: 30px;
      }
      .main-title {
        font-size: 2.5em;
        margin-bottom: 30px;
      }
      th, td {
        padding: 10px 15px;
        font-size: 0.9em;
      }
      .btn-action {
        padding: 7px 10px;
        font-size: 0.85em;
      }
      .btn-action i {
        font-size: 0.9em;
      }
      .message-box {
        max-width: 95%;
      }
      .button-main-menu-container {
        bottom: 20px;
        right: 20px;
      }
      .btn-menu {
        padding: 12px 25px;
        font-size: 0.95em;
      }
    }

    @media (max-width: 768px) {
      body {
        padding: 15px;
      }
      .main-title {
        font-size: 2em;
        margin-bottom: 25px;
      }
      .container {
        padding: 20px;
        border-radius: 15px;
        width: 100%;
      }
      .filter-section {
        flex-direction: column;
        align-items: stretch;
        gap: 15px;
        margin-bottom: 25px;
      }
      .filter-group {
        flex-direction: column;
        align-items: flex-start;
        gap: 5px;
      }
      .filter-section input[type="text"] {
        width: calc(100% - 24px);
      }
      .btn-filter {
        width: 100%;
        justify-content: center;
      }
      .table-wrapper {
        margin-bottom: 20px;
        border-radius: 10px;
      }
      th, td {
        padding: 8px 12px;
        font-size: 0.85em;
      }
      .acciones {
        flex-direction: column;
        gap: 5px;
      }
      .btn-action {
        width: 100%;
        justify-content: center;
        font-size: 0.8em;
        padding: 6px 10px;
      }
      .message-box {
        padding: 12px;
        margin-bottom: 20px;
        font-size: 0.9em;
      }
      .button-main-menu-container {
        bottom: 15px;
        right: 15px;
      }
      .btn-menu {
        padding: 10px 20px;
        font-size: 0.9em;
        gap: 8px;
      }
      .btn-menu i {
        font-size: 1em;
      }

      .button-back-container {
        margin-top: 30px;
        align-items: center;
      }
      .btn-back {
        width: 100%;
        justify-content: center;
        padding: 12px 20px;
      }
    }

    @media (max-width: 480px) {
      body {
        padding: 10px;
      }
      .main-title {
        font-size: 1.8em;
        margin-bottom: 20px;
      }
      .container {
        padding: 15px;
        border-radius: 12px;
      }
      .filter-section input[type="text"] {
        width: calc(100% - 20px);
      }
      th, td {
        padding: 6px 8px;
        font-size: 0.8em;
      }
      .btn-action {
        padding: 5px 8px;
        font-size: 0.75em;
      }
      .message-box {
        padding: 10px;
        font-size: 0.8em;
      }
      .btn-menu {
        padding: 8px 15px;
        font-size: 0.8em;
        gap: 5px;
      }
      .button-main-menu-container {
        bottom: 10px;
        right: 10px;
      }
      .btn-back {
        padding: 10px 15px;
      }
    }
  </style>
</head>
<body>

<div class="main-title">Gestión de Clientes</div>

<% if (mensaje != null && !mensaje.isEmpty()) { %>
<div class="message-box <%= isErrorMessage ? "error-message" : "success-message" %>">
  <i class="fas <%= isErrorMessage ? "fa-times-circle" : "fa-check-circle" %>"></i>
  <%= mensaje %>
</div>
<% } %>

<div class="container">

  <div class="filter-section">
    <form action="<%= request.getContextPath() %>/ModificarClienteServlet" method="get" class="filter-group">
      <label for="buscarCliente">Buscar por ID o Nombre:</label>
      <div style="display: flex; gap: 10px; width: 100%; flex-wrap: wrap; justify-content: flex-end;">
        <input type="text"
               id="buscarCliente"
               name="searchQuery"
               placeholder="Escriba aquí..."
               value="<%= request.getAttribute("searchQuery") != null ? request.getAttribute("searchQuery") : "" %>">
        <button type="submit" class="btn-filter">
          <i class="fas fa-search"></i> Buscar
        </button>
        <% if (request.getAttribute("searchQuery") != null && !((String)request.getAttribute("searchQuery")).isEmpty()) { %>
        <a href="<%= request.getContextPath() %>/ModificarClienteServlet"
           class="btn-filter" style="background: var(--error-color);">
          <i class="fas fa-times"></i> Limpiar
        </a>
        <% } %>
      </div>
    </form>
  </div>


  <div class="table-wrapper">
    <table>
      <thead>
      <tr>
        <th>ID</th>
        <th>Nombre</th>
        <th>Primer Apellido</th>
        <th>Segundo Apellido</th>
        <th>Teléfono</th>
        <th>Celular</th>
        <th>Dirección</th>
        <th>Email</th>
        <th>Acciones</th>
      </tr>
      </thead>
      <tbody>
      <% if (clientes != null && !clientes.isEmpty()) {
        for (Cliente cliente : clientes) { %>
      <tr>
        <td><%= cliente.getIdCliente() %></td>
        <td><%= cliente.getNombre() %></td>
        <td><%= cliente.getPrimerApellido() %></td>
        <td><%= cliente.getSegundoApellido() %></td>
        <td><%= cliente.getTelefono() %></td>
        <td><%= cliente.getCelular() %></td>
        <td><%= cliente.getDireccion() %></td>
        <td><%= cliente.getEmail() %></td>
        <td class="acciones">
          <form action="<%= request.getContextPath() %>/ModificarClienteServlet" method="post" style="display:inline;">
            <input type="hidden" name="idCliente" value="<%= cliente.getIdCliente() %>"/>
            <input type="hidden" name="accion" value="editar"/>
            <button class="btn-action btn-modificar" type="submit">
              <i class="fas fa-edit"></i> Modificar
            </button>
          </form>
          <form action="<%= request.getContextPath() %>/ModificarClienteServlet" method="post" style="display:inline;" onsubmit="return confirm('¿Seguro que desea eliminar este cliente?');">
            <input type="hidden" name="idCliente" value="<%= cliente.getIdCliente() %>"/>
            <input type="hidden" name="accion" value="eliminar"/>
            <button class="btn-action btn-eliminar" type="submit">
              <i class="fas fa-trash-alt"></i> Eliminar
            </button>
          </form>
        </td>
      </tr>
      <%  }
      } else { %>
      <tr>
        <td colspan="9" style="text-align: center; padding: 20px; color: var(--text-dark);">No hay clientes registrados o no se encontraron resultados.</td>
      </tr>
      <% } %>
      </tbody>
    </table>
  </div>
</div>

<div class="button-back-container">
  <a href="<%= request.getContextPath() %>/SeleccionAccionClienteServlet" class="btn-back">
    <i class="fas fa-arrow-alt-circle-left"></i> Volver al Menú Clientes
  </a>
</div>


<script>
  // Auto-ocultar mensajes de feedback después de unos segundos
  window.onload = function() {
    const messages = document.querySelectorAll('.message-box');
    messages.forEach(message => {
      if (message.textContent.trim() !== '') {
        setTimeout(() => {
          message.style.display = 'none';
        }, 5000);
      }
    });
  };
</script>
</body>
</html>