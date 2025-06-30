<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registrar Orden de Trabajo</title>
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
        input, label, textarea, select {
            display: block;
            width: 100%;
            margin-bottom: 15px;
        }
        input, textarea, select {
            padding: 10px;
            font-size: 16px;
        }
        .button {
            background-color: #2c3e50;
            color: white;
            padding: 10px;
            font-weight: bold;
            border: none;
            cursor: pointer;
        }
        .button:hover {
            background-color: #1a252f;
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

<form action="OrdenTrabajoServlet" method="post">
    <h2>Registro de Orden de Trabajo</h2>

    <label for="idOrdenTrabajo">ID de Orden:</label>
    <input type="text" name="idOrdenTrabajo" required />

    <label for="placaVehiculo">Placa del Vehículo:</label>
    <input type="text" name="placaVehiculo" required />

    <label for="descripcionSolicitud">Descripción de la Solicitud:</label>
    <textarea name="descripcionSolicitud" rows="4" required></textarea>

    <label for="fechaIngreso">Fecha de Ingreso:</label>
    <input type="date" name="fechaIngreso" required />

    <label for="estado">Estado:</label>
    <select name="estado" required>
        <option value="En proceso">En proceso</option>
        <option value="Finalizado">Finalizado</option>
        <option value="Pendiente">Pendiente</option>
    </select>

    <label for="fechaDevolucion">Fecha de Devolución (opcional):</label>
    <input type="date" name="fechaDevolucion" />

    <input type="submit" class="button" value="Registrar Orden" />
</form>

</body>
</html>
