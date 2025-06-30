<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Menu Principal</title>
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
            font-size: 2.5em;
            font-weight: bold;
            color: #37526c;
            margin-bottom: 40px;
            letter-spacing: 1px;
            text-shadow: 0 2px 8px #d3e0e8;
        }
        .menu-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            grid-template-rows: 1fr 1fr;
            gap: 30px 40px;
            width: 520px;
            height: 320px;
            background: rgba(255,255,255,0.95);
            border: 2px solid #b7cbe3;
            border-radius: 18px;
            padding: 40px 30px;
            box-shadow: 0 8px 32px rgba(55,82,108,0.10);
        }
        .menu-item {
            border: none;
            border-radius: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 1.25em;
            font-weight: 600;
            background: linear-gradient(120deg, #6dd5ed 0%, #2193b0 100%);
            color: #fff;
            min-width: 180px;
            min-height: 90px;
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
        .menu-item.right-bottom {
            font-size: 1.05em;
            font-weight: 500;
            justify-content: flex-end;
            align-items: flex-end;
            padding: 18px;
            background: linear-gradient(120deg, #f7971e 0%, #ffd200 100%);
            color: #37526c;
        }
        .menu-item.right-bottom:hover {
            background: linear-gradient(120deg, #ffd200 0%, #f7971e 100%);
            color: #222;
        }
    </style>
</head>
<body>
<div class="center-container">
    <div class="main-title">Menú Principal</div>
    <div class="menu-grid">
        <a href="ClienteServlet" class="menu-item">Cliente</a>
        <a href="VehiculoServlet" class="menu-item">Vehículo</a>
        <a href="OrdenTrabajoServlet" class="menu-item">Orden de Trabajo</a>
        <a href="DetalleOrdenServlet" class="menu-item right-bottom">Detalles, Servicios y Repuestos</a>
    </div>
</div>
</body>
</html>