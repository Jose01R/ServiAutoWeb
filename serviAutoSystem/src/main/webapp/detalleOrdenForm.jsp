<%@ page import="domain.DetalleOrden" %>
<%@ page import="java.util.List" %>
<%@ page import="domain.Servicio" %>
<%@ page import="domain.Repuesto" %>
<%@ page import="domain.OrdenTrabajo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    DetalleOrden detalle = (DetalleOrden) request.getAttribute("detalleOrden");
    List<Servicio> servicios = (List<Servicio>) request.getAttribute("servicios");
    List<Repuesto> repuestos = (List<Repuesto>) request.getAttribute("repuestos");
    List<OrdenTrabajo> ordenesTrabajo = (List<OrdenTrabajo>) request.getAttribute("ordenesTrabajo");
    String pageTitle = (detalle == null) ? "Nuevo Detalle de Orden" : "Editar Detalle de Orden";
    String errorMessage = (String) request.getAttribute("error");
    String successMessage = (String) request.getAttribute("success");
    boolean esServicio = (detalle != null && detalle.getServicio() != null) || (detalle == null); // predeterminado a servicio si nuevo

    String idOrdenSelected = (detalle != null && detalle.getOrdenTrabajo() != null)
            ? detalle.getOrdenTrabajo().getIdOrdenTrabajo()
            : (detalle != null ? detalle.getIdOrdenTrabajo() : "");
%>
<html>
<head>
    <title><%= pageTitle %></title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
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
            max-width: 600px;
            width: 100%;
            margin: 20px auto;
            padding: 40px;
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
            margin-bottom: 25px;
        }
        label {
            display: block;
            margin-bottom: 8px;
            color: var(--text-dark);
            font-weight: 600;
            font-size: 1.05em;
        }
        input[type="text"],
        input[type="number"],
        textarea,
        select {
            width: 100%;
            padding: 12px;
            border: 1px solid var(--border-color);
            border-radius: 10px;
            font-size: 1em;
            transition: border-color 0.3s ease, box-shadow 0.3s ease;
            box-shadow: inset 0 1px 3px rgba(0,0,0,0.05);
        }
        input[type="text"]:focus,
        input[type="number"]:focus,
        textarea:focus,
        select:focus {
            border-color: var(--primary-light);
            box-shadow: 0 0 0 3px rgba(33, 147, 176, 0.2);
            outline: none;
        }
        textarea {
            resize: vertical;
            min-height: 70px;
        }
        .btn-container {
            display: flex;
            gap: 15px;
            margin-top: 30px;
            justify-content: center;
        }
        .btn {
            padding: 12px 30px;
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
            background: #f0f4f8;
            color: var(--text-dark);
            border: 1px solid var(--border-color);
        }
        .btn-secondary:hover {
            background: #e6edf3;
            transform: translateY(-2px);
            box-shadow: 0 6px 15px rgba(0,0,0,0.08);
        }
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
<div class="container">
    <h1><%= pageTitle %></h1>

    <%-- Mensajes de error o éxito --%>
    <%
        if (errorMessage != null && !errorMessage.isEmpty()) {
    %>
    <div class="message-box error-message" style="display: block;">
        <%= errorMessage %>
    </div>
    <%
        }
        if (successMessage != null && !successMessage.isEmpty()) {
    %>
    <div class="message-box success-message">
        <%= successMessage %>
    </div>
    <%
        }
    %>
    <div id="errorMessages" class="message-box error-message" style="display: none;"></div>

    <form id="detalleForm" action="<%= request.getContextPath() %>/DetalleOrden" method="post" onsubmit="return validateForm()">
        <% if (detalle != null) { %>
        <input type="hidden" name="idDetalleOrden" value="<%= detalle.getIdDetalleOrden() %>"/>
        <% } %>
        <input type="hidden" name="action" value="<%= (detalle == null) ? "crear" : "actualizar" %>">

        <div class="form-group">
            <label for="idOrdenTrabajo">ID Orden de Trabajo:</label>
            <select id="idOrdenTrabajo" name="idOrdenTrabajo" required>
                <option value="">Selecciona una orden de trabajo</option>
                <%
                    if (ordenesTrabajo != null) {
                        for (OrdenTrabajo ot : ordenesTrabajo) {
                %>
                <option value="<%= ot.getIdOrdenTrabajo() %>"
                        <%= (idOrdenSelected != null && idOrdenSelected.equals(ot.getIdOrdenTrabajo())) ? "selected" : "" %>>
                    <%= ot.getIdOrdenTrabajo() %>
                </option>
                <%
                        }
                    }
                %>
            </select>
        </div>

        <div class="form-group">
            <label for="cantidad">Cantidad:</label>
            <input type="number" id="cantidad" name="cantidad" required min="1" max="999"
                   value="<%= (detalle != null) ? detalle.getCantidad() : 1 %>"/>
        </div>

        <div class="form-group">
            <label for="observaciones">Observaciones:</label>
            <textarea id="observaciones" name="observaciones"><%= (detalle != null) ? detalle.getObservaciones() : "" %></textarea>
        </div>

        <div class="form-group">
            <label for="tipoDetalle">Tipo Detalle:</label>
            <select id="tipoDetalle" name="tipoDetalle" onchange="toggleItemSelect()">
                <option value="servicio" <%= esServicio ? "selected" : "" %>>Servicio</option>
                <option value="repuesto" <%= (!esServicio) ? "selected" : "" %>>Repuesto</option>
            </select>
        </div>

        <div class="form-group" id="servicioSelectGroup" style="<%= (!esServicio) ? "display:none;" : "" %>">
            <label for="servicioSelect">Servicio:</label>
            <select id="servicioSelect" name="servicioNombre">
                <option value="">Selecciona un servicio</option>
                <% for (Servicio s : servicios) { %>
                <option value="<%= s.getNombre() %>"
                        <%= (detalle != null && detalle.getServicio() != null && detalle.getServicio().getNombre().equals(s.getNombre())) ? "selected" : "" %>>
                    <%= s.getNombre() %>
                </option>
                <% } %>
            </select>
        </div>

        <div class="form-group" id="repuestoSelectGroup" style="<%= esServicio ? "display:none;" : "" %>">
            <label for="repuestoSelect">Repuesto:</label>
            <select id="repuestoSelect" name="repuestoNombre">
                <option value="">Selecciona un repuesto</option>
                <% for (Repuesto r : repuestos) { %>
                <option value="<%= r.getNombre() %>"
                        <%= (detalle != null && detalle.getRepuesto() != null && detalle.getRepuesto().getNombre().equals(r.getNombre())) ? "selected" : "" %>>
                    <%= r.getNombre() %>
                </option>
                <% } %>
            </select>
        </div>

        <div class="form-group">
            <label for="idEstado">ID Estado:</label>
            <input type="text" id="idEstado" name="idEstado" required
                   value="<%= (detalle != null) ? detalle.getIdEstado() : "" %>"/>
        </div>

        <div class="btn-container">
            <button type="submit" class="btn btn-primary">
                <%= (detalle == null) ? "Registrar Detalle" : "Actualizar Detalle" %>
            </button>
            <a href="<%= request.getContextPath() %>/DetalleOrden" class="btn btn-secondary">Cancelar</a>
        </div>
    </form>
</div>
<script>
    function toggleItemSelect() {
        const tipo = document.getElementById('tipoDetalle').value;
        document.getElementById('servicioSelectGroup').style.display = (tipo === 'servicio') ? 'block' : 'none';
        document.getElementById('repuestoSelectGroup').style.display = (tipo === 'repuesto') ? 'block' : 'none';
    }

    function validateForm() {
        const errorDiv = document.getElementById('errorMessages');
        const idOrdenTrabajo = document.getElementById('idOrdenTrabajo');
        const cantidad = document.getElementById('cantidad');
        const tipoDetalle = document.getElementById('tipoDetalle');
        const servicioSelect = document.getElementById('servicioSelect');
        const repuestoSelect = document.getElementById('repuestoSelect');
        const idEstado = document.getElementById('idEstado');
        let errors = [];

        errorDiv.innerHTML = '';
        errorDiv.style.display = 'none';
        [idOrdenTrabajo, cantidad, idEstado, servicioSelect, repuestoSelect].forEach(input => input.classList.remove('input-error'));

        if (idOrdenTrabajo.value.trim().length === 0) {
            errors.push('Debe seleccionar el ID de la orden de trabajo.');
            idOrdenTrabajo.classList.add('input-error');
        }
        if (isNaN(parseInt(cantidad.value)) || parseInt(cantidad.value) <= 0) {
            errors.push('La cantidad debe ser un número mayor a 0.');
            cantidad.classList.add('input-error');
        }
        if (idEstado.value.trim().length === 0) {
            errors.push('Debe ingresar el ID del estado.');
            idEstado.classList.add('input-error');
        }
        if (tipoDetalle.value === 'servicio') {
            if (!servicioSelect.value) {
                errors.push('Debe seleccionar un servicio.');
                servicioSelect.classList.add('input-error');
            }
        }
        if (tipoDetalle.value === 'repuesto') {
            if (!repuestoSelect.value) {
                errors.push('Debe seleccionar un repuesto.');
                repuestoSelect.classList.add('input-error');
            }
        }
        if (errors.length > 0) {
            errorDiv.innerHTML = errors.join('<br>');
            errorDiv.style.display = 'block';
            return false;
        }
        return true;
    }

    document.querySelectorAll('input, select').forEach(input => {
        input.addEventListener('input', function() {
            this.classList.remove('input-error');
            if (document.querySelectorAll('.input-error').length === 0) {
                document.getElementById('errorMessages').style.display = 'none';
            }
        });
    });

    window.onload = function() {
        const successDiv = document.querySelector('.success-message');
        const errorDivServlet = document.querySelector('.error-message');
        const actualErrorDivServlet = (errorDivServlet && errorDivServlet.id !== 'errorMessages') ? errorDivServlet : null;
        if (successDiv && successDiv.textContent.trim() !== '') {
            setTimeout(() => {
                successDiv.style.display = 'none';
            }, 5000);
        }
        if (actualErrorDivServlet && actualErrorDivServlet.textContent.trim() !== '') {
            setTimeout(() => {
                actualErrorDivServlet.style.display = 'none';
            }, 7000);
        }
    };
</script>
</body>
</html>