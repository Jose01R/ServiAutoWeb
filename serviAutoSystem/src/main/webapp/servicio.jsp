<%@ page import="java.util.List" %>
<%@ page import="domain.Servicio" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestión de Servicios</title>
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
            font-family: 'Poppins', sans-serif;
            background: linear-gradient(135deg, var(--accent-light) 0%, var(--accent-dark) 100%);
            display: flex;
            justify-content: center;
            align-items: flex-start;
        }
        .container {
            max-width: 1000px;
            width: 100%;
            margin: 20px auto;
            padding: 35px;
            background: var(--bg-white);
            border-radius: 20px;
            box-shadow: 0 12px 40px var(--shadow-light);
            animation: fadeIn 0.6s ease-out;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(30px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 35px;
            padding-bottom: 20px;
            border-bottom: 1px solid var(--border-color);
        }
        .title {
            font-size: 2.5em;
            color: var(--text-dark);
            font-weight: 700;
            letter-spacing: 0.5px;
        }
        .btn-group {
            display: flex;
            gap: 15px;
        }
        .btn {
            padding: 14px 28px;
            border: none;
            border-radius: 12px;
            background: linear-gradient(120deg, var(--primary-light) 0%, var(--primary-dark) 100%);
            color: var(--text-light);
            font-weight: 600;
            cursor: pointer;
            text-decoration: none;
            transition: all 0.3s ease;
            box-shadow: 0 6px 15px rgba(0,0,0,0.15);
            display: inline-flex;
            align-items: center;
            gap: 8px; /* Espacio entre texto e ícono */
        }
        .btn:hover {
            background: linear-gradient(120deg, var(--primary-dark) 0%, var(--primary-light) 100%);
            transform: translateY(-4px);
            box-shadow: 0 8px 20px rgba(0,0,0,0.2);
        }
        /* Estilo para el botón secundario (Volver al Menú) */
        .btn-secondary {
            background: #f0f4f8;
            color: var(--text-dark);
            border: 1px solid var(--border-color);
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
        }
        .btn-secondary:hover {
            background: #e6edf3;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0,0,0,0.12);
        }

        table {
            width: 100%;
            border-collapse: separate;
            border-spacing: 0;
            margin-top: 30px;
            border-radius: 15px;
            overflow: hidden;
            box-shadow: 0 8px 25px var(--shadow-light);
        }
        th, td {
            padding: 18px 25px;
            text-align: left;
            border-bottom: 1px solid #e0e0e0;
        }
        th {
            background-color: #eef4f9;
            color: var(--text-dark);
            font-weight: 600;
            text-transform: uppercase;
            font-size: 0.9em;
            letter-spacing: 0.5px;
        }
        tr:last-child td {
            border-bottom: none;
        }
        tbody tr:hover {
            background-color: #f8fbff;
            transition: background-color 0.2s ease;
        }
        .action-links a {
            margin-right: 18px;
            color: var(--primary-dark);
            text-decoration: none;
            font-weight: 500;
            transition: color 0.3s ease, transform 0.2s ease;
        }
        .action-links a:hover {
            color: var(--primary-light);
            text-decoration: underline;
            transform: translateY(-1px);
        }

        /* Mensajes de feedback */
        .message-box {
            padding: 18px;
            border-radius: 12px;
            margin-bottom: 30px;
            font-weight: 500;
            display: flex;
            align-items: center;
            gap: 12px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.08);
            animation: slideInFromTop 0.5s ease-out;
        }
        .success-message {
            color: var(--success-color);
            background-color: var(--success-bg);
            border: 1px solid #b3e3bb;
        }
        .error-message {
            color: var(--error-color);
            background-color: var(--error-bg);
            border: 1px solid #f0b5bd;
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
        .btn i.fa-plus { /* Icono para "Nuevo Servicio" */
            /* No necesitas un estilo especial */
        }
        /* Clases específicas para íconos en enlaces de acción */
        .action-links .edit-icon {
            color: var(--primary-dark); /* Color del ícono de editar */
        }
        .action-links .delete-icon {
            color: var(--error-color); /* Color del ícono de eliminar */
        }

        /* Animación para mensajes */
        @keyframes slideInFromTop {
            from { opacity: 0; transform: translateY(-20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        /* Media Queries para responsividad */
        @media (max-width: 768px) {
            .container {
                padding: 25px;
                border-radius: 15px;
            }
            .header {
                flex-direction: column;
                align-items: flex-start;
                margin-bottom: 25px;
            }
            .title {
                font-size: 2em;
                margin-bottom: 15px;
            }
            .btn-group {
                flex-direction: column;
                width: 100%;
                gap: 10px;
            }
            .btn {
                width: calc(100% - 28px);
                padding: 12px;
            }
            table {
                border-radius: 10px;
            }
            th, td {
                padding: 12px 15px;
                font-size: 0.9em;
            }
            .action-links a {
                margin-right: 10px;
            }
        }

        @media (max-width: 480px) {
            body {
                padding: 10px;
            }
            .container {
                padding: 15px;
                margin: 10px auto;
            }
            .title {
                font-size: 1.8em;
            }
            th, td {
                font-size: 0.8em;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h1 class="title">Gestión de Servicios</h1>
        <div class="btn-group">
            <a href="<%= request.getContextPath() %>/MenuServlet" class="btn btn-secondary">
                <i class="fas fa-home"></i> Volver al Menú
            </a>
            <a href="<%= request.getContextPath() %>/servicio?action=nuevo" class="btn">
                <i class="fas fa-plus"></i> Nuevo Servicio
            </a>
        </div>
    </div>

    <%-- Mostrar mensajes de éxito o error --%>
    <%
        // Asegúrate de que los mensajes se manejen de forma consistente,
        // ya sea solo con request.getAttribute o con session.getAttribute para flash messages.
        // Aquí se mantiene la lógica de tu ejemplo anterior.
        String successMessage = (String) request.getAttribute("success");
        String errorMessage = (String) request.getAttribute("error");

        if (successMessage != null && !successMessage.isEmpty()) {
    %>
    <div class="message-box success-message">
        <%= successMessage %>
    </div>
    <%
        }
        if (errorMessage != null && !errorMessage.isEmpty()) {
    %>
    <div class="message-box error-message">
        <%= errorMessage %>
    </div>
    <%
        }
    %>

    <table>
        <thead>
        <tr>
            <th>Nombre</th>
            <th>Precio</th>
            <th>Costo Mano de Obra</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Servicio> servicios = (List<Servicio>) request.getAttribute("servicios");

            if (servicios != null && !servicios.isEmpty()) {
                for (Servicio servicio : servicios) {
        %>
        <tr>
            <td><%= servicio.getNombre() %></td>
            <td>₡<%= String.format("%,.2f", servicio.getPrecio()) %></td>
            <td>₡<%= String.format("%,.2f", servicio.getCostoManoObra()) %></td>
            <td class="action-links">
                <a href="<%= request.getContextPath() %>/servicio?action=editar&nombre=<%= servicio.getNombre() %>">
                    <i class="fas fa-edit edit-icon"></i> Editar
                </a>
                <a href="#" onclick="confirmarEliminarServicio('<%= servicio.getNombre() %>')">
                    <i class="fas fa-trash-alt delete-icon"></i> Eliminar
                </a>
            </td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="4" style="text-align: center; color: var(--text-dark); padding: 25px;">No hay servicios disponibles.</td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
</div>

<script>
    function confirmarEliminarServicio(nombre) {
        if (confirm('¿Está seguro que desea eliminar el servicio "' + nombre + '"?')) {
            // Usar request.getContextPath() para una URL robusta
            window.location.href = '<%= request.getContextPath() %>/servicio?action=eliminar&nombre=' + encodeURIComponent(nombre);
        }
    }

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