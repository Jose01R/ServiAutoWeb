<%@ page import="domain.Servicio" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        Servicio servicio = (Servicio) request.getAttribute("servicio");
        String pageTitle = (servicio == null) ? "Nuevo Servicio" : "Editar Servicio";
        String errorMessage = (String) request.getAttribute("error");
        String successMessage = (String) request.getAttribute("success");
    %>
    <title><%= pageTitle %></title>
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
<div class="container">
    <h1><%= pageTitle %></h1>

    <%-- Mostrar mensajes de error o éxito que vienen del servlet --%>
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

    <form id="servicioForm" action="<%= request.getContextPath() %>/servicio" method="post" onsubmit="return validateForm()">
        <input type="hidden" name="action" value="<%= (servicio == null) ? "crear" : "actualizar" %>">

        <div class="form-group">
            <label for="nombre">Nombre del Servicio:</label>
            <input type="text" id="nombre" name="nombre" value="<%= (servicio != null) ? servicio.getNombre() : "" %>"
                   required maxlength="100" pattern="[A-Za-zÁáÉéÍíÓóÚúÑñ0-9 ]{3,100}"
                   title="El nombre debe tener entre 3 y 100 caracteres, solo letras, números y espacios">
        </div>

        <div class="form-group">
            <label for="precio">Precio (₡):</label>
            <input type="number" id="precio" name="precio" value="<%= (servicio != null) ? String.format("%.2f", servicio.getPrecio()) : "" %>"
                   required min="0" max="1000000" step="0.01">
        </div>

        <div class="form-group">
            <label for="costoManoObra">Costo Mano de Obra (₡):</label>
            <input type="number" id="costoManoObra" name="costoManoObra"
                   value="<%= (servicio != null) ? String.format("%.2f", servicio.getCostoManoObra()) : "" %>" required min="0" max="500000" step="0.01">
        </div>

        <div class="btn-container">
            <button type="submit" class="btn btn-primary">
                <%= (servicio == null) ? "Crear Servicio" : "Actualizar Servicio" %>
            </button>
            <a href="<%= request.getContextPath() %>/servicio" class="btn btn-secondary">Cancelar</a>
        </div>
    </form>
</div>

<script>
    function validateForm() {
        const errorDiv = document.getElementById('errorMessages');
        const nombre = document.getElementById('nombre');
        const precio = document.getElementById('precio');
        const costoManoObra = document.getElementById('costoManoObra');
        let errors = [];

        // Limpiar errores previos
        errorDiv.innerHTML = '';
        errorDiv.style.display = 'none';
        [nombre, precio, costoManoObra].forEach(input => input.classList.remove('input-error'));

        // Validar nombre
        if (nombre.value.trim().length < 3 || nombre.value.trim().length > 100) {
            errors.push('El nombre debe tener entre 3 y 100 caracteres.');
            nombre.classList.add('input-error');
        }
        // Validación de patrón se confía en el atributo pattern del input.
        // Si necesitas más control en JS:
        // if (!nombre.value.trim().match(/^[A-Za-zÁáÉéÍíÓóÚúÑñ0-9 ]+$/)) {
        //     errors.push('El nombre solo puede contener letras, números y espacios.');
        //     nombre.classList.add('input-error');
        // }

        // Validar precio
        const precioValue = parseFloat(precio.value);
        if (isNaN(precioValue) || precioValue < 0 || precioValue > 1000000) {
            errors.push('El precio debe estar entre 0 y 1,000,000.');
            precio.classList.add('input-error');
        }

        // Validar costo mano de obra
        const costoValue = parseFloat(costoManoObra.value);
        if (isNaN(costoValue) || costoValue < 0 || costoValue > 500000) {
            errors.push('El costo de mano de obra debe estar entre 0 y 500,000.');
            costoManoObra.classList.add('input-error');
        }

        if (errors.length > 0) {
            errorDiv.innerHTML = errors.join('<br>');
            errorDiv.style.display = 'block';
            return false;
        }

        return true;
    }

    // Validación en tiempo real (elimina las clases de error al escribir)
    document.querySelectorAll('input').forEach(input => {
        input.addEventListener('input', function() {
            this.classList.remove('input-error');
            if (document.querySelectorAll('.input-error').length === 0) {
                document.getElementById('errorMessages').style.display = 'none';
            }
        });
    });

    // Ocultar los mensajes de éxito/error del servlet después de unos segundos
    window.onload = function() {
        const successDiv = document.querySelector('.success-message');
        const errorDivServlet = document.querySelector('.error-message'); // El que viene del servlet

        // Asegura que no sea el div de mensajes de JS (que tiene ID)
        const actualErrorDivServlet = (errorDivServlet && errorDivServlet.id !== 'errorMessages') ? errorDivServlet : null;

        if (successDiv && successDiv.textContent.trim() !== '') {
            setTimeout(() => {
                successDiv.style.display = 'none';
            }, 5000); // Ocultar después de 5 segundos
        }
        if (actualErrorDivServlet && actualErrorDivServlet.textContent.trim() !== '') {
            setTimeout(() => {
                actualErrorDivServlet.style.display = 'none';
            }, 7000); // Ocultar después de 7 segundos
        }
    };
</script>
</body>
</html>