<%--
  Created by IntelliJ IDEA.
  User: XT
  Date: 30/06/2025
  Time: 02:14 p. m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Menú Clientes</title>
  <style>
    body {
      margin: 0;
      padding: 0;
      min-height: 100vh;
      font-family: 'Segoe UI', Arial, sans-serif;
      background: linear-gradient(135deg, #e0eafc 0%, #cfdef3 100%);
      display: flex;
      align-items: center;
      justify-content: center;
    }
    .center-container {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      min-height: 100vh;
      width: 100vw;
    }
    .main-title {
      font-size: 2.3em;
      font-weight: bold;
      color: #37526c;
      margin-bottom: 38px;
      letter-spacing: 1px;
      text-shadow: 0 2px 8px #d3e0e8;
    }
    .menu-grid {
      display: grid;
      grid-template-columns: 1fr 1fr;
      grid-template-rows: 1fr;
      gap: 35px 40px;
      width: 420px;
      height: 120px;
      background: rgba(255,255,255,0.97);
      border: 2px solid #b7cbe3;
      border-radius: 16px;
      padding: 25px 26px;
      box-shadow: 0 8px 32px rgba(55,82,108,0.10);
    }
    .menu-item {
      border: none;
      border-radius: 12px;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 1.15em;
      font-weight: 600;
      background: linear-gradient(120deg, #6dd5ed 0%, #2193b0 100%);
      color: #fff;
      min-width: 145px;
      min-height: 65px;
      text-align: center;
      text-decoration: none;
      box-shadow: 0 2px 8px rgba(33,147,176,0.10);
      transition: transform 0.15s, box-shadow 0.15s, background 0.2s;
      cursor: pointer;
    }
    .menu-item:hover {
      background: linear-gradient(120deg, #2193b0 0%, #6dd5ed 100%);
      transform: translateY(-4px) scale(1.04);
      box-shadow: 0 8px 24px rgba(33,147,176,0.18);
    }
  </style>
</head>
<body>
<div class="center-container">
  <div class="main-title">Menú Clientes</div>
  <div class="menu-grid">
    <a href="ClienteServlet" class="menu-item">Registrar Clientes</a>
    <a href="ModificarClienteServlet" class="menu-item">Modificar Clientes</a>
  </div>
</div>
</body>
</html>
