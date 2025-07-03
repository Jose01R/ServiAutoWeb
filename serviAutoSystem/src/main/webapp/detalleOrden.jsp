<%@ page import="java.util.List" %>
<%@ page import="domain.DetalleOrden" %>
<%@ page import="domain.Servicio" %>
<%@ page import="domain.Repuesto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestión de Detalles de Orden</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        :root {
            --primary-light: #6dd5ed;
            --primary-dark: #2193b0;
            --accent-light: #e0eafc;
            --accent-dark: #cfdef3;
            --text-dark: #37526c;
            --text-light: #ffffff;
            --bg-white: rgba(255,255,255,0.97);
            --border-color: #ddd;
            --shadow-light: rgba(55,82,108,0.10);
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
            gap: 8px;
        }
        .btn:hover {
            background: linear-gradient(120deg, var(--primary-dark) 0%, var(--primary-light) 100%);
            transform: translateY(-4px);
            box-shadow: 0 8px 20px rgba(0,0,0,0.2);
        }
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

        .filter-section {
            margin-bottom: 25px;
            padding: 14px 22px;
            background: #f8f9fa;
            border-radius: 12px;
            display: flex;
            justify-content: flex-start;
            align-items: center;
        }
        .filter-section select {
            padding: 9px 18px;
            border-radius: 6px;
            border: 1px solid var(--border-color);
            font-family: 'Poppins', sans-serif;
            font-size: 1em;
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
            display: inline-flex;
            align-items: center;
        }
        .action-links a:hover {
            color: var(--primary-light);
            text-decoration: underline;
            transform: translateY(-1px);
        }
        .action-links .edit-icon {
            color: var(--primary-dark);
        }
        .action-links .delete-icon {
            color: var(--error-color);
        }

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

        @keyframes slideInFromTop {
            from { opacity: 0; transform: translateY(-20px); }
            to { opacity: 1; transform: translateY(0); }
        }

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
        <h1 class="title">Gestión de Detalles de Orden</h1>
        <div class="btn-group">
            <a href="<%= request.getContextPath() %>/MenuServlet" class="btn btn-secondary">
                <i class="fas fa-home"></i> Volver al Menú
            </a>
            <a href="<%= request.getContextPath() %>/DetalleOrden?action=nuevo" class="btn">
                <i class="fas fa-plus"></i> Nuevo Detalle
            </a>
        </div>
    </div>

    <%-- Mensajes --%>
    <%
        String successMessage = (String) request.getAttribute("success");
        String errorMessage = (String) request.getAttribute("error");
        if (successMessage != null && !successMessage.isEmpty()) {
    %>
    <div class="message-box success-message"><%= successMessage %></div>
    <% } if (errorMessage != null && !errorMessage.isEmpty()) { %>
    <div class="message-box error-message"><%= errorMessage %></div>
    <% } %>

    <div class="filter-section">
        <form action="<%= request.getContextPath() %>/DetalleOrden" method="get" style="margin: 0;">
            <select name="tipoFiltro" onchange="this.form.submit()">
                <option value="">Todos los detalles</option>
                <option value="servicio" <%= "servicio".equals(request.getParameter("tipoFiltro")) ? "selected" : "" %>>Solo servicios</option>
                <option value="repuesto" <%= "repuesto".equals(request.getParameter("tipoFiltro")) ? "selected" : "" %>>Solo repuestos</option>
            </select>
        </form>
    </div>

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Orden Trabajo</th>
            <th>Tipo</th>
            <th>Detalle</th>
            <th>Precio</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<DetalleOrden> detallesOrden = (List<DetalleOrden>) request.getAttribute("detallesOrden");
            if (detallesOrden != null && !detallesOrden.isEmpty()) {
                for (DetalleOrden detalle : detallesOrden) {
                    String tipo = "No definido";
                    String nombreDetalle = "N/A";
                    double precioDetalle = 0.0;

                    if (detalle.getServicio() != null) {
                        tipo = "Servicio";
                        nombreDetalle = detalle.getServicio().getNombre();
                        precioDetalle = detalle.getServicio().getPrecio();
                    } else if (detalle.getRepuesto() != null) {
                        tipo = "Repuesto";
                        nombreDetalle = detalle.getRepuesto().getNombre();
                        precioDetalle = detalle.getRepuesto().getPrecio();
                    }
        %>
        <tr>
            <td><%= detalle.getIdDetalleOrden() %></td>
            <td><%= (detalle.getOrdenTrabajo() != null) ? detalle.getOrdenTrabajo().getIdOrdenTrabajo() : "N/A" %></td>
            <td><%= tipo %></td>
            <td><%= nombreDetalle %></td>
            <td>₡<%= String.format("%,.2f", precioDetalle) %></td>
            <td class="action-links">
                <a href="<%= request.getContextPath() %>/DetalleOrden?action=editar&id=<%= detalle.getIdDetalleOrden() %>">
                    <i class="fas fa-edit edit-icon"></i> Editar
                </a>
                <a href="#" onclick="confirmarEliminarDetalle('<%= detalle.getIdDetalleOrden() %>')">
                    <i class="fas fa-trash-alt delete-icon"></i> Eliminar
                </a>
            </td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="6" style="text-align: center; color: var(--text-dark); padding: 25px;">No hay detalles de orden disponibles.</td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>

<script>
    function confirmarEliminarDetalle(id) {
        if (confirm('¿Está seguro que desea eliminar este detalle?')) {
            window.location.href = '<%= request.getContextPath() %>/DetalleOrden?action=eliminar&id=' + encodeURIComponent(id);
        }
    }

    window.onload = function() {
        const messages = document.querySelectorAll('.message-box');
        messages.forEach(msg => {
            if (msg.textContent.trim() !== '') {
                setTimeout(() => msg.style.display = 'none', 5000);
            }
        });
    };
</script>
</body>
</html>
