<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Gestión de Órdenes de Trabajo</title>
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
  <style>
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
      font-family: 'Poppins', sans-serif;
      background: linear-gradient(135deg, var(--accent-light) 0%, var(--accent-dark) 100%);
      display: flex;
      align-items: center;
      justify-content: center;
      overflow: hidden;
    }
    .center-container {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      min-height: 100vh;
      width: 100%;
      padding: 20px;
      box-sizing: border-box;
    }
    .main-title {
      font-size: 3em;
      font-weight: 800;
      color: var(--text-dark);
      margin-bottom: 60px;
      letter-spacing: 2px;
      text-shadow: 0 5px 15px var(--title-shadow-color);
      text-align: center;
      animation: fadeInDown 0.8s ease-out;
    }
    @keyframes fadeInDown {
      from { opacity: 0; transform: translateY(-40px); }
      to { opacity: 1; transform: translateY(0); }
    }
    .menu-grid {
      display: grid;
      grid-template-columns: repeat(2, 1fr);
      gap: 25px;
      max-width: 600px;
      width: 90%;
      background: var(--bg-white);
      border: 1px solid var(--border-color);
      border-radius: 25px;
      padding: 45px;
      box-shadow: 0 15px 50px var(--shadow-light);
      animation: fadeInUp 0.8s ease-out 0.2s forwards;
      opacity: 0;
    }
    @keyframes fadeInUp {
      from { opacity: 0; transform: translateY(40px); }
      to { opacity: 1; transform: translateY(0); }
    }
    .menu-item {
      border: none;
      border-radius: 18px;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      font-size: 1.3em;
      font-weight: 600;
      background: linear-gradient(120deg, var(--primary-light) 0%, var(--primary-dark) 100%);
      color: var(--text-light);
      min-height: 110px;
      text-align: center;
      text-decoration: none;
      box-shadow: 0 5px 15px var(--button-shadow-color);
      transition: transform 0.2s ease-out, box-shadow 0.2s ease-out, background 0.3s ease;
      cursor: pointer;
    }
    .menu-item:hover {
      background: linear-gradient(120deg, var(--primary-dark) 0%, var(--primary-light) 100%);
      transform: translateY(-8px) scale(1.06);
      box-shadow: 0 12px 30px rgba(33,147,176,0.3);
    }
    .back-button-container {
      margin-top: 50px;
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
    .menu-item i {
      font-size: 2em;
      margin-bottom: 10px;
      color: var(--text-light);
      transition: transform 0.2s ease-out;
    }
    .menu-item:hover i {
      transform: scale(1.1);
    }
    .btn-back i {
      margin-right: 5px;
      color: var(--secondary-button-text);
    }
    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(20px); }
      to { opacity: 1; transform: translateY(0); }
    }
    @media (max-width: 768px) {
      .main-title {
        font-size: 2.5em;
        margin-bottom: 40px;
      }
      .menu-grid {
        grid-template-columns: 1fr;
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
        width: 100%;
        padding: 12px;
        justify-content: center;
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
  <div class="main-title">Gestión de Órdenes de Trabajo</div>
  <div class="menu-grid">
    <a href="<%= request.getContextPath() %>/OrdenTrabajoServlet" class="menu-item">
      <i class="fas fa-file-alt"></i> Registrar Orden de Trabajo
    </a>
    <a href="<%= request.getContextPath() %>/ModificarOrdenTrabajoServlet" class="menu-item">
      <i class="fas fa-edit"></i> Modificar Orden de Trabajo
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