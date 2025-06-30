<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registrar Cliente</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f1f9fc;
            padding: 30px;
        }
        form {
            background-color: white;
            padding: 30px;
            max-width: 600px;
            margin: auto;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        input, label {
            display: block;
            width: 100%;
            margin-bottom: 15px;
        }
        input {
            padding: 10px;
            font-size: 16px;
        }
        .button {
            background-color: #3498db;
            color: white;
            padding: 10px;
            font-weight: bold;
            border: none;
            cursor: pointer;
        }
        .button:hover {
            background-color: #2980b9;
        }
        .mensaje {
            color: green;
            text-align: center;
            font-weight: bold;
        }
    </style>
</head>
<body>

<% String mensaje = (String) request.getAttribute("mensaje"); %>
<% if (mensaje != null) { %>
<div class="mensaje"><%= mensaje %></div>
<% } %>

<form action="ClienteServlet" method="post">
    <h2>Registro de Cliente</h2>
    <label for="idCliente">ID:</label>
    <input type="text" name="idCliente" required />

    <label for="nombre">Nombre:</label>
    <input type="text" name="nombre" required />

    <label for="apellido1">Primer Apellido:</label>
    <input type="text" name="apellido1" required />

    <label for="apellido2">Segundo Apellido:</label>
    <input type="text" name="apellido2" required />

    <label for="telefono">Teléfono:</label>
    <input type="text" name="telefono" required />

    <label for="celular">Celular:</label>
    <input type="text" name="celular" required />

    <label for="direccion">Dirección:</label>
    <input type="text" name="direccion" required />

    <label for="email">Email:</label>
    <input type="email" name="email" required />

    <input type="submit" class="button" value="Registrar Cliente" />
</form>

</body>
</html>
