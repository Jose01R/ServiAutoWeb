<%@ page import="domain.Cliente" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  List<Cliente> clientes = (List<Cliente>) request.getAttribute("listaClientes");
%>
<html>
<head>
  <title>Clientes con filtros y resultados</title>
  <style>
    body {
      margin: 0;
      padding: 0;
      font-family: Arial, sans-serif;
      box-sizing: border-box;
      background: #f4f8fb;
    }
    .container {
      display: flex;
      justify-content: center;
      align-items: center;
      min-height: 100vh;
      background: #f4f8fb;
    }
    .main-content {
      width: 90%;
      max-width: 1200px;
      background: #fff;
      border-radius: 10px;
      box-shadow: 0 4px 16px rgba(55,82,108,0.08);
      padding: 32px 24px;
      display: flex;
      flex-direction: column;
      align-items: center;
    }
    .header {
      font-size: 1.4em;
      font-weight: bold;
      margin-bottom: 24px;
      color: #37526c;
      text-align: center;
    }
    .table-container {
      width: 100%;
      display: flex;
      justify-content: center;
    }
    table {
      border-collapse: collapse;
      width: 100%;
      background: #fff;
      border: 2px solid #d3e0e8;
      border-radius: 5px;
      overflow: hidden;
      margin: 0 auto;
    }
    th, td {
      border: 1px solid #d3e0e8;
      padding: 8px 10px;
      text-align: left;
      font-size: 1em;
    }
    th {
      background: #f6fafd;
      font-weight: bold;
      text-align: center;
    }
    tr {
      height: 38px;
    }
    .acciones {
      display: flex;
      gap: 6px;
      justify-content: center;
    }
    .btn {
      border: none;
      color: white;
      padding: 4px 16px;
      border-radius: 4px;
      cursor: pointer;
      font-size: 0.95em;
      font-weight: bold;
    }
    .btn-modificar {
      background: #2596ff;
    }
    .btn-eliminar {
      background: #e74c3c;
    }
  </style>
</head>
<body>
<div class="container">
  <div class="main-content">
    <div class="header">Lista de Clientes</div>
    <div class="table-container">
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
        <% if (clientes != null) {
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
            <form method="post" style="display:inline;">
              <input type="hidden" name="idCliente" value="<%= cliente.getIdCliente() %>"/>
              <input type="hidden" name="accion" value="actualizar"/>
              <button class="btn btn-modificar" type="submit">Modificar</button>
            </form>
            <form method="post" style="display:inline;" onsubmit="return confirm('¿Seguro que desea eliminar este cliente?');">
              <input type="hidden" name="idCliente" value="<%= cliente.getIdCliente() %>"/>
              <input type="hidden" name="accion" value="eliminar"/>
              <button class="btn btn-eliminar" type="submit">Eliminar</button>
            </form>
          </td>
        </tr>
        <%  }
        } %>
        </tbody>
      </table>
    </div>
  </div>
</div>
</body>
</html>