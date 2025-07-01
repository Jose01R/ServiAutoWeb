<%@ page import="java.util.List" %>
<%@ page import="domain.Repuesto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Gestión de Repuestos</title>
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
    }

    body {
      margin: 0;
      padding: 20px;
      min-height: 100vh;
      font-family: 'Poppins', sans-serif; /* Fuente moderna */
      background: linear-gradient(135deg, var(--accent-light) 0%, var(--accent-dark) 100%);
      display: flex;
      justify-content: center;
      align-items: flex-start; /* Alinea al inicio verticalmente */
    }
    .container {
      max-width: 1000px;
      width: 100%; /* Asegura que ocupe el ancho disponible */
      margin: 20px auto; /* Margen superior/inferior para centrar */
      padding: 30px; /* Más padding para una sensación espaciosa */
      background: var(--bg-white);
      border-radius: 18px; /* Bordes más suaves */
      box-shadow: 0 10px 30px var(--shadow-light); /* Sombra más pronunciada */
      animation: fadeIn 0.5s ease-out; /* Animación de entrada */
    }

    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(20px); }
      to { opacity: 1; transform: translateY(0); }
    }

    .header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 30px; /* Más espacio */
      padding-bottom: 15px;
      border-bottom: 1px solid var(--border-color); /* Separador sutil */
    }
    .title {
      font-size: 2.2em; /* Título más grande */
      color: var(--text-dark);
      font-weight: 700; /* Más peso para el título */
    }
    .btn-group {
      display: flex;
      gap: 10px; /* Espacio entre los botones */
    }
    .btn {
      padding: 12px 25px; /* Más padding */
      border: none;
      border-radius: 10px; /* Bordes más redondeados */
      background: linear-gradient(120deg, var(--primary-light) 0%, var(--primary-dark) 100%);
      color: var(--text-light);
      font-weight: 600;
      cursor: pointer;
      text-decoration: none;
      transition: all 0.3s ease; /* Transición suave */
      box-shadow: 0 4px 10px rgba(0,0,0,0.1); /* Sombra para botones */
      display: inline-flex;
      align-items: center;
      gap: 8px; /* Espacio entre texto e ícono */
    }
    .btn:hover {
      background: linear-gradient(120deg, var(--primary-dark) 0%, var(--primary-light) 100%);
      transform: translateY(-2px); /* Efecto hover */
      box-shadow: 0 6px 15px rgba(0,0,0,0.15);
    }
    /* Estilo para el botón secundario (Volver al Menú) */
    .btn-secondary {
      background: #f0f4f8;
      color: var(--text-dark);
      border: 1px solid var(--border-color);
      box-shadow: 0 2px 5px rgba(0,0,0,0.05);
    }
    .btn-secondary:hover {
      background: #e6edf3;
      transform: translateY(-1px);
      box-shadow: 0 4px 8px rgba(0,0,0,0.1);
    }

    table {
      width: 100%;
      border-collapse: separate; /* Para bordes redondeados */
      border-spacing: 0;
      margin-top: 25px;
      border-radius: 12px; /* Bordes de la tabla */
      overflow: hidden; /* Para que los bordes redondeados se apliquen al contenido */
      box-shadow: 0 5px 15px var(--shadow-light); /* Sombra para la tabla */
    }
    th, td {
      padding: 15px 20px; /* Más padding en celdas */
      text-align: left;
      border-bottom: 1px solid #eee; /* Borde más claro */
    }
    th {
      background-color: #f0f4f8; /* Fondo más suave para encabezados */
      color: var(--text-dark);
      font-weight: 600;
      text-transform: uppercase; /* Mayúsculas para encabezados */
      font-size: 0.95em;
    }
    tr:last-child td {
      border-bottom: none; /* No borde en la última fila */
    }
    tbody tr:hover {
      background-color: #fbfdff; /* Efecto hover en filas */
    }
    .action-links a {
      margin-right: 15px; /* Más espacio */
      color: var(--primary-dark);
      text-decoration: none;
      font-weight: 500;
      transition: color 0.3s ease;
    }
    .action-links a:hover {
      color: var(--primary-light);
      text-decoration: underline;
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

    /* Estilos para el estado de pedido */
    .estado-pedido {
      padding: 6px 12px; /* Más padding */
      border-radius: 6px; /* Más redondeado */
      font-size: 0.85em; /* Ligeramente más pequeño */
      font-weight: 600;
      display: inline-block; /* Para que el padding y border-radius funcionen bien */
      text-transform: uppercase;
      letter-spacing: 0.5px;
    }
    .pedido-si {
      background-color: var(--success-bg);
      color: var(--success-color);
      border: 1px solid #a3d9b8; /* Borde sutil */
    }
    .pedido-no {
      background-color: var(--error-bg);
      color: var(--error-color);
      border: 1px solid #f0b5bd; /* Borde sutil */
    }

    /* Estilos para los iconos */
    .btn i {
      margin-right: 5px; /* Espacio entre el ícono y el texto del botón */
    }
    .action-links a i {
      margin-right: 5px; /* Espacio entre el ícono y el texto del enlace de acción */
      font-size: 0.9em; /* Íconos un poco más pequeños para las acciones */
    }
    /* Clases específicas para íconos en botones */
    .btn-secondary i.fa-home { /* Icono para "Volver al Menú" */
      /* No necesitas un estilo especial, solo que sea un ícono de Font Awesome */
    }
    .btn i.fa-plus { /* Icono para "Nuevo Repuesto" */
      /* No necesitas un estilo especial */
    }
    /* Clases específicas para íconos en enlaces de acción */
    .action-links .edit-icon {
      color: var(--primary-dark); /* Color del ícono de editar */
    }
    .action-links .delete-icon {
      color: var(--error-color); /* Color del ícono de eliminar */
    }
  </style>
</head>
<body>
<div class="container">
  <div class="header">
    <h1 class="title">Gestión de Repuestos</h1>
    <div class="btn-group">
      <a href="<%= request.getContextPath() %>/MenuServlet" class="btn btn-secondary">
        <i class="fas fa-home"></i> Volver al Menú
      </a>
      <a href="<%= request.getContextPath() %>/repuesto?action=nuevo" class="btn">
        <i class="fas fa-plus"></i> Nuevo Repuesto
      </a>
    </div>
  </div>

  <%-- Mostrar mensajes de éxito o error --%>
  <%
    String errorMessage = (String) request.getAttribute("error"); // Del request si es un error de procesamiento
    String successMessage = (String) session.getAttribute("success"); // De la sesión para flash messages

    if (errorMessage != null && !errorMessage.isEmpty()) {
  %>
  <div class="message-box error-message">
    <%= errorMessage %>
  </div>
  <%
    }
    if (successMessage != null && !successMessage.isEmpty()) {
      session.removeAttribute("success"); // Limpiar el mensaje después de mostrarlo
  %>
  <div class="message-box success-message">
    <%= successMessage %>
  </div>
  <%
    }
  %>

  <table>
    <thead>
    <tr>
      <th>Nombre</th>
      <th>Precio</th>
      <th>Cantidad</th>
      <th>Estado de Pedido</th>
      <th>Acciones</th>
    </tr>
    </thead>
    <tbody>
    <%
      List<Repuesto> repuestos = (List<Repuesto>) request.getAttribute("repuestos");
      if (repuestos != null && !repuestos.isEmpty()) {
        for (Repuesto repuesto : repuestos) {
    %>
    <tr>
      <td><%= repuesto.getNombre() %></td>
      <td>₡<%= String.format("%,.2f", repuesto.getPrecio()) %></td>
      <td style="text-align: center;"><%= repuesto.getCantidad() %></td>
      <td>
        <span class="estado-pedido <%= repuesto.isPedido() ? "pedido-si" : "pedido-no" %>">
          <%= repuesto.isPedido() ? "Pedido realizado" : "Sin pedido" %>
        </span>
      </td>
      <td class="action-links">
        <a href="<%= request.getContextPath() %>/repuesto?action=editar&nombre=<%= repuesto.getNombre() %>">
          <i class="fas fa-edit edit-icon"></i> Editar
        </a>
        <a href="#" onclick="confirmarEliminar('<%= repuesto.getNombre() %>')">
          <i class="fas fa-trash-alt delete-icon"></i> Eliminar
        </a>
      </td>
    </tr>
    <%
      }
    } else {
    %>
    <tr>
      <td colspan="5" style="text-align: center; color: var(--text-dark); padding: 25px;">No hay repuestos disponibles.</td>
    </tr>
    <%
      }
    %>
    </tbody>
  </table>
</div>

<script>
  function confirmarEliminar(nombre) {
    if (confirm('¿Está seguro que desea eliminar el repuesto "' + nombre + '"?')) {
      // Usar request.getContextPath() para una URL robusta
      window.location.href = '<%= request.getContextPath() %>/repuesto?action=eliminar&nombre=' + encodeURIComponent(nombre);
    }
  }

  // Auto-ocultar mensajes de feedback después de unos segundos
  window.onload = function() {
    const messages = document.querySelectorAll('.message-box'); // Selecciona todos los divs con la clase 'message-box'
    messages.forEach(message => {
      if (message.textContent.trim() !== '') { // Solo si tienen contenido
        setTimeout(() => {
          message.style.display = 'none';
        }, 5000); // Ocultar después de 5 segundos
      }
    });
  };
</script>
</body>
</html>