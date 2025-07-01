<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Menú Vehículo</title>
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
      --border-color: #b7cbe3;
      --shadow-light: rgba(55, 82, 108, 0.10);
      --button-shadow-color: rgba(33,147,176,0.10);
      --title-shadow-color: rgba(211, 224, 232, 0.8);
      --secondary-button-bg: #f0f4f8;
      --secondary-button-text: var(--text-dark);
      --secondary-button-border: var(--border-color);
      --secondary-button-shadow: rgba(0,0,0,0.08);
      --secondary-button-hover-bg: #e6edf3;
      --secondary-button-hover-shadow: rgba(0,0,0,0.12);
    }

    body {
      margin: 0;
      padding: 20px;
      min-height: 100vh;
      font-family: 'Poppins', sans-serif; /* Consistente con otras vistas */
      background: linear-gradient(135deg, var(--accent-light) 0%, var(--accent-dark) 100%);
      display: flex;
      align-items: center;
      justify-content: center;
      overflow: hidden; /* Evita scroll no deseado */
      box-sizing: border-box; /* Incluye padding en el tamaño total */
    }
    .center-container {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      min-height: 100vh; /* Ocupa al menos toda la altura de la vista */
      width: 100%; /* Ocupa todo el ancho */
      padding: 20px;
      box-sizing: border-box;
    }
    .main-title {
      font-size: 3em; /* Más grande y prominente */
      font-weight: 800; /* Más peso */
      color: var(--text-dark);
      margin-bottom: 60px; /* Más espacio debajo */
      letter-spacing: 2px; /* Más espaciado entre letras */
      text-shadow: 0 5px 15px var(--title-shadow-color); /* Sombra más suave */
      text-align: center;
      animation: fadeInDown 0.8s ease-out; /* Animación de entrada */
    }

    @keyframes fadeInDown {
      from { opacity: 0; transform: translateY(-40px); }
      to { opacity: 1; transform: translateY(0); }
    }

    .menu-grid {
      display: grid;
      grid-template-columns: repeat(2, 1fr); /* 2 columnas iguales */
      gap: 25px; /* Espacio uniforme entre elementos */
      max-width: 600px; /* Ancho máximo consistente con otros menús */
      width: 90%; /* Ancho responsivo */
      background: var(--bg-white);
      border: 1px solid var(--border-color);
      border-radius: 25px; /* Bordes más suaves */
      padding: 45px; /* Más padding */
      box-shadow: 0 15px 50px var(--shadow-light); /* Sombra más pronunciada */
      animation: fadeInUp 0.8s ease-out 0.2s forwards; /* Animación con retraso */
      opacity: 0; /* Oculto inicialmente para la animación */
      /* No necesitamos justify-content: center aquí porque son 2 elementos en 2 columnas */
    }

    @keyframes fadeInUp {
      from { opacity: 0; transform: translateY(40px); }
      to { opacity: 1; transform: translateY(0); }
    }

    .menu-item {
      border: none;
      border-radius: 18px; /* Bordes más redondeados */
      display: flex;
      flex-direction: column; /* Icono y texto apilados */
      align-items: center;
      justify-content: center;
      font-size: 1.3em;
      font-weight: 600;
      background: linear-gradient(120deg, var(--primary-light) 0%, var(--primary-dark) 100%);
      color: var(--text-light);
      min-height: 110px; /* Altura mínima consistente */
      text-align: center;
      text-decoration: none;
      box-shadow: 0 5px 15px var(--button-shadow-color);
      transition: transform 0.2s ease-out, box-shadow 0.2s ease-out, background 0.3s ease;
      cursor: pointer;
    }
    .menu-item:hover {
      background: linear-gradient(120deg, var(--primary-dark) 0%, var(--primary-light) 100%);
      transform: translateY(-8px) scale(1.06); /* Efecto hover pronunciado */
      box-shadow: 0 12px 30px rgba(33,147,176,0.3);
    }

    /* Estilos para los iconos dentro de los elementos del menú */
    .menu-item i {
      font-size: 2em; /* Tamaño del icono */
      margin-bottom: 10px; /* Espacio entre el icono y el texto */
      color: var(--text-light);
      transition: transform 0.2s ease-out;
    }
    .menu-item:hover i {
      transform: scale(1.1);
    }

    /* Botón de "Volver al Menú Principal" */
    .back-button-container {
      margin-top: 50px; /* Espacio superior para separarlo del grid */
      animation: fadeIn 0.8s ease-out 0.4s forwards;
      opacity: 0;
    }

    .btn-back {
      padding: 14px 28px;
      border: none;
      border-radius: 12px;
      background: var(--secondary-button-bg);
      color: var(--secondary-button-text);
      border: 1px solid var(--secondary-button-border);
      font-weight: 600;
      cursor: pointer;
      text-decoration: none;
      transition: all 0.3s ease;
      box-shadow: 0 2px 8px var(--secondary-button-shadow);
      display: inline-flex;
      align-items: center;
      gap: 8px;
    }
    .btn-back:hover {
      background: var(--secondary-button-hover-bg);
      transform: translateY(-2px);
      box-shadow: 0 4px 12px var(--secondary-button-hover-shadow);
    }
    .btn-back i {
      margin-right: 5px;
      color: var(--secondary-button-text);
    }

    /* Animación para el botón de volver */
    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(20px); }
      to { opacity: 1; transform: translateY(0); }
    }

    /* Media Queries para responsividad */
    @media (max-width: 768px) {
      .main-title {
        font-size: 2.5em;
        margin-bottom: 40px;
      }
      .menu-grid {
        grid-template-columns: 1fr; /* Una sola columna en tablets/móviles */
        padding: 35px;
        gap: 20px;
        width: 90%;
        max-width: 450px;
      }
      .menu-item {
        font-size: 1.2em;
        min-height: 90px;
      }
      .menu-item i {
        font-size: 1.8em;
        margin-bottom: 8px;
      }
      .back-button-container {
        margin-top: 40px;
      }
      .btn-back {
        width: 100%; /* El botón ocupa todo el ancho disponible */
        padding: 12px;
        justify-content: center; /* Centra el contenido si ocupa todo el ancho */
      }
    }

    @media (max-width: 480px) {
      body {
        padding: 10px;
      }
      .main-title {
        font-size: 2em;
        margin-bottom: 30px;
      }
      .menu-grid {
        padding: 25px;
        gap: 15px;
        width: 95%;
      }
      .menu-item {
        font-size: 1.1em;
        min-height: 80px;
      }
      .menu-item i {
        font-size: 1.6em;
        margin-bottom: 5px;
      }
      .back-button-container {
        margin-top: 30px;
      }
    }
  </style>
</head>
<body>
<div class="center-container">
  <div class="main-title">Menú Vehículo</div>
  <div class="menu-grid">
    <a href="<%= request.getContextPath() %>/VehiculoServlet" class="menu-item">
      <i class="fas fa-car-alt"></i> Registrar Vehículo
    </a>
    <a href="<%= request.getContextPath() %>/ModificarEliminarVehiculoServlet" class="menu-item">
      <i class="fas fa-tools"></i> Modificar/Eliminar Vehículo
    </a>
  </div>
  <div class="back-button-container">
    <a href="<%= request.getContextPath() %>/MenuServlet" class="btn-back">
      <i class="fas fa-arrow-alt-circle-left"></i> Volver al Menú Principal
    </a>
  </div>
</div>
</body>
</html>