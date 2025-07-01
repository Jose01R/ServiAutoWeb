<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registrar Cliente</title>
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
            flex-direction: column; /* Permite apilar el mensaje y el formulario */
            justify-content: center;
            align-items: center;
        }

        .container {
            max-width: 800px; /* Increased max-width to allow for two columns */
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

        @media (min-width: 600px) { /* Apply two columns on screens 600px and wider */
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
        input[type="email"],
        input[type="tel"] {
            width: calc(100% - 24px); /* Adjust for padding */
            padding: 12px;
            border: 1px solid var(--border-color);
            border-radius: 8px;
            font-size: 1em;
            color: var(--text-dark);
            transition: border-color 0.3s ease, box-shadow 0.3s ease;
            box-sizing: border-box;
        }

        input[type="text"]:focus,
        input[type="email"]:focus,
        input[type="tel"]:focus {
            border-color: var(--primary-dark);
            box-shadow: 0 0 0 3px rgba(33, 147, 176, 0.2);
            outline: none;
        }

        .btn-group {
            display: flex;
            justify-content: flex-end; /* Align buttons to the right */
            gap: 15px;
            margin-top: 40px; /* More space above buttons */
            padding-top: 20px;
            border-top: 1px solid var(--border-color); /* Separator line */
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
            /* flex-grow: 1; Removed flex-grow to allow buttons to size based on content */
            justify-content: center;
            min-width: 150px; /* Give buttons a min-width */
        }
        .btn:hover {
            background: linear-gradient(120deg, var(--primary-dark) 0%, var(--primary-light) 100%);
            transform: translateY(-2px);
            box-shadow: 0 6px 15px rgba(0,0,0,0.15);
        }

        /* Estilo para el botón secundario (Volver) */
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
            max-width: 800px; /* Match new container max-width */
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

        /* Media Queries for Responsiveness */
        @media (max-width: 768px) {
            body {
                padding: 15px;
            }
            .container {
                padding: 25px;
                border-radius: 15px;
                max-width: 100%; /* Allow container to be full width on smaller screens */
            }
            .form-title {
                font-size: 1.8em;
                margin-bottom: 25px;
            }
            /* .form-fields-grid will automatically revert to 1 column due to default setting */
            .form-group {
                margin-bottom: 15px; /* Reintroduce margin-bottom for single column layout */
            }
            input[type="text"],
            input[type="email"],
            input[type="tel"] {
                padding: 10px;
                font-size: 0.95em;
            }
            .btn-group {
                flex-direction: column; /* Stack buttons in a column */
                gap: 10px;
                justify-content: center; /* Center buttons when stacked */
                align-items: stretch; /* Make buttons full width */
                border-top: none; /* Remove top border if desired on small screens */
                padding-top: 0;
            }
            .btn {
                width: 100%; /* Buttons take full width */
                min-width: unset; /* Remove min-width constraint */
            }
            .message-box {
                padding: 12px;
                margin-bottom: 15px;
                font-size: 0.9em;
                max-width: 100%; /* Match container width */
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
            input[type="email"],
            input[type="tel"] {
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
    boolean isErrorMessage = (mensaje != null && mensaje.toLowerCase().contains("error"));
%>

<% if (mensaje != null && !mensaje.isEmpty()) { %>
<div class="message-box <%= isErrorMessage ? "error-message" : "success-message" %>">
    <i class="fas <%= isErrorMessage ? "fa-times-circle" : "fa-check-circle" %>"></i>
    <%= mensaje %>
</div>
<% } %>

<div class="container">
    <form action="<%= request.getContextPath() %>/ClienteServlet" method="post">
        <h2 class="form-title">Registro de Cliente</h2>

        <div class="form-fields-grid">
            <div class="form-group">
                <label for="idCliente">ID:</label>
                <input type="text" id="idCliente" name="idCliente" required />
            </div>

            <div class="form-group">
                <label for="nombre">Nombre:</label>
                <input type="text" id="nombre" name="nombre" required />
            </div>

            <div class="form-group">
                <label for="apellido1">Primer Apellido:</label>
                <input type="text" id="apellido1" name="apellido1" required />
            </div>

            <div class="form-group">
                <label for="apellido2">Segundo Apellido:</label>
                <input type="text" id="apellido2" name="apellido2" required />
            </div>

            <div class="form-group">
                <label for="telefono">Teléfono:</label>
                <input type="tel" id="telefono" name="telefono" required />
            </div>

            <div class="form-group">
                <label for="celular">Celular:</label>
                <input type="tel" id="celular" name="celular" required />
            </div>

            <div class="form-group">
                <label for="direccion">Dirección:</label>
                <input type="text" id="direccion" name="direccion" required />
            </div>

            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required />
            </div>
        </div>

        <div class="btn-group">
            <button type="submit" class="btn">
                <i class="fas fa-save"></i> Registrar Cliente
            </button>
            <a href="<%= request.getContextPath() %>/SeleccionAccionClienteServlet" class="btn btn-secondary">
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