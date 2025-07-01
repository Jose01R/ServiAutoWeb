<%--
  Created by IntelliJ IDEA.
  User: XT
  Date: 30/06/2025
  Time: 02:14 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Operaciones Técnicas</title>
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
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
      --border-color: #b7cbe3; /* Borde más suave */
      --shadow-light: rgba(55, 82, 108, 0.10);
      --button-shadow-color: rgba(33,147,176,0.10);
      --title-shadow-color: rgba(211, 224, 232, 0.8);
    }

    body {
      margin: 0;
      padding: 0;
      min-height: 100vh;
      font-family: 'Poppins', sans-serif; /* Fuente moderna */
      background: linear-gradient(135deg, var(--accent-light) 0%, var(--accent-dark) 100%);
      display: flex;
      align-items: center;
      justify-content: center;
      overflow: hidden; /* Evita scroll si no es necesario */
    }
    .center-container {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      min-height: 100vh; /* Ajusta a la altura de la vista */
      width: 100vw; /* Ajusta al ancho de la vista */
      padding: 20px; /* Padding para evitar que el contenido toque los bordes */
      box-sizing: border-box; /* Incluye padding en el ancho/alto total */
    }
    .main-title {
      font-size: 2.8em; /* Título más grande */
      font-weight: 800; /* Más peso para el título */
      color: var(--text-dark);
      margin-bottom: 50px; /* Más espacio debajo del título */
      letter-spacing: 1.5px; /* Más espaciado entre letras */
      text-shadow: 0 4px 12px var(--title-shadow-color); /* Sombra más pronunciada */
      text-align: center;
      animation: fadeInDown 0.8s ease-out; /* Animación de entrada para el título */
    }

    @keyframes fadeInDown {
      from { opacity: 0; transform: translateY(-30px); }
      to { opacity: 1; transform: translateY(0); }
    }

    .menu-grid {
      display: grid;
      grid-template-columns: repeat(2, 1fr); /* 2 columnas iguales */
      gap: 30px; /* Espacio más uniforme entre elementos */
      max-width: 500px; /* Ancho máximo para la cuadrícula */
      width: 90%; /* Ancho responsivo */
      background: var(--bg-white);
      border: 1px solid var(--border-color); /* Borde más sutil */
      border-radius: 20px; /* Bordes más redondeados */
      padding: 35px 40px; /* Más padding */
      box-shadow: 0 12px 40px var(--shadow-light); /* Sombra más grande y suave */
      animation: fadeInUp 0.8s ease-out 0.2s forwards; /* Animación de entrada con retraso */
      opacity: 0; /* Oculto inicialmente para la animación */
    }

    @keyframes fadeInUp {
      from { opacity: 0; transform: translateY(30px); }
      to { opacity: 1; transform: translateY(0); }
    }

    .menu-item {
      border: none;
      border-radius: 15px; /* Bordes más redondeados para los ítems */
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 1.2em; /* Texto más grande */
      font-weight: 600;
      background: linear-gradient(120deg, var(--primary-light) 0%, var(--primary-dark) 100%);
      color: var(--text-light);
      min-height: 80px; /* Altura mínima de los ítems */
      text-align: center;
      text-decoration: none;
      box-shadow: 0 4px 12px var(--button-shadow-color); /* Sombra para los ítems */
      transition: transform 0.2s ease-out, box-shadow 0.2s ease-out, background 0.3s ease; /* Transiciones más suaves */
      cursor: pointer;
    }
    .menu-item:hover {
      background: linear-gradient(120deg, var(--primary-dark) 0%, var(--primary-light) 100%);
      transform: translateY(-6px) scale(1.05); /* Efecto hover más pronunciado */
      box-shadow: 0 10px 28px rgba(33,147,176,0.25); /* Sombra más grande en hover */
    }

    /* Media Queries para responsividad */
    @media (max-width: 600px) {
      .main-title {
        font-size: 2.2em;
        margin-bottom: 40px;
      }
      .menu-grid {
        grid-template-columns: 1fr; /* Una sola columna en pantallas pequeñas */
        padding: 25px;
        gap: 20px;
        width: 85%;
        max-width: 350px; /* Limita el ancho en móviles */
      }
      .menu-item {
        font-size: 1.1em;
        min-height: 70px;
      }
    }
  </style>
</head>
<body>
<div class="center-container">
  <div class="main-title">Operaciones</div>
  <div class="menu-grid">
    <%-- Usar request.getContextPath() para URLs robustas --%>
    <a href="<%= request.getContextPath() %>/detalleOrden" class="menu-item">Detalle Orden</a>
    <a href="<%= request.getContextPath() %>/repuesto" class="menu-item">Repuestos</a>
    <a href="<%= request.getContextPath() %>/servicio" class="menu-item">Servicios</a>
    <%-- Si necesitas un botón para volver al menú principal, lo puedes añadir aquí --%>
    <a href="<%= request.getContextPath() %>/MenuServlet" class="menu-item">Volver al Menú</a>
  </div>
</div>
</body>
</html>