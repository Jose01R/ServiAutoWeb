<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Menú Principal</title>
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
            --special-button-light: #f7971e; /* Naranja */
            --special-button-dark: #ffd200; /* Amarillo */
        }

        body {
            margin: 0;
            padding: 0;
            min-height: 100vh;
            font-family: 'Poppins', sans-serif;
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
            min-height: 100vh;
            width: 100vw;
            padding: 20px;
            box-sizing: border-box;
        }
        .main-title {
            font-size: 3em; /* Título un poco más grande */
            font-weight: 800;
            color: var(--text-dark);
            margin-bottom: 60px; /* Más espacio debajo del título */
            letter-spacing: 2px; /* Más espaciado entre letras */
            text-shadow: 0 5px 15px var(--title-shadow-color); /* Sombra más pronunciada */
            text-align: center;
            animation: fadeInDown 0.8s ease-out;
        }

        @keyframes fadeInDown {
            from { opacity: 0; transform: translateY(-40px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .menu-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr); /* 2 columnas iguales */
            grid-template-rows: repeat(2, 1fr); /* 2 filas iguales */
            gap: 25px; /* Espacio uniforme y ligeramente reducido entre elementos */
            max-width: 600px; /* Ancho máximo para la cuadrícula */
            width: 90%; /* Ancho responsivo */
            background: var(--bg-white);
            border: 1px solid var(--border-color);
            border-radius: 25px; /* Bordes más redondeados */
            padding: 45px; /* Más padding */
            box-shadow: 0 15px 50px var(--shadow-light); /* Sombra más grande y suave */
            animation: fadeInUp 0.8s ease-out 0.2s forwards;
            opacity: 0;
        }

        @keyframes fadeInUp {
            from { opacity: 0; transform: translateY(40px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .menu-item {
            border: none;
            border-radius: 18px; /* Bordes más redondeados para los ítems */
            display: flex;
            flex-direction: column; /* Apila el icono y el texto */
            align-items: center;
            justify-content: center;
            font-size: 1.3em; /* Texto más grande */
            font-weight: 600;
            background: linear-gradient(120deg, var(--primary-light) 0%, var(--primary-dark) 100%);
            color: var(--text-light);
            min-height: 110px; /* Altura mínima de los ítems */
            text-align: center;
            text-decoration: none;
            box-shadow: 0 5px 15px var(--button-shadow-color); /* Sombra para los ítems */
            transition: transform 0.2s ease-out, box-shadow 0.2s ease-out, background 0.3s ease;
            cursor: pointer;
            position: relative; /* Para posibles iconos o detalles */
        }
        .menu-item:hover {
            background: linear-gradient(120deg, var(--primary-dark) 0%, var(--primary-light) 100%);
            transform: translateY(-8px) scale(1.06); /* Efecto hover más pronunciado */
            box-shadow: 0 12px 30px rgba(33,147,176,0.3); /* Sombra más grande en hover */
        }

        /* Estilo para el botón de "Detalles, Servicios y Repuestos" */
        .menu-item.right-bottom {
            font-size: 1.15em; /* Ligeramente más pequeño para el texto largo */
            font-weight: 600; /* Mantenemos el peso */
            background: linear-gradient(120deg, #f7971e 0%, #ffd200 100%); /* Usar colores directos o variables aquí */
            color: var(--text-dark); /* Color de texto oscuro para contraste */
            box-shadow: 0 5px 15px rgba(255, 165, 0, 0.2); /* Sombra con color naranja */
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            padding: 15px;
        }
        .menu-item.right-bottom:hover {
            background: linear-gradient(120deg, #ffd200 0%, #f7971e 100%);
            color: #1a1a1a;
            transform: translateY(-8px) scale(1.06);
            box-shadow: 0 12px 30px rgba(255, 165, 0, 0.4);
        }

        /* Estilos para los iconos dentro de los elementos del menú */
        .menu-item i {
            font-size: 2em; /* Tamaño del icono */
            margin-bottom: 10px; /* Espacio entre el icono y el texto */
            color: var(--text-light); /* Color predeterminado para iconos de elementos normales */
            transition: transform 0.2s ease-out; /* Transición suave para el icono */
        }
        .menu-item:hover i {
            transform: scale(1.1); /* Ligeramente más grande en hover */
        }

        /* Color de icono específico para el botón de "Detalles, Servicios y Repuestos" */
        .menu-item.right-bottom i {
            color: var(--text-dark); /* Color oscuro para este icono */
        }
        .menu-item.right-bottom:hover i {
            color: #1a1a1a; /* Color más oscuro en hover */
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
            .menu-item.right-bottom {
                font-size: 1.1em;
            }
            .menu-item i {
                font-size: 1.8em;
                margin-bottom: 8px;
            }
        }

        @media (max-width: 480px) {
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
            .menu-item.right-bottom {
                font-size: 1em;
            }
            .menu-item i {
                font-size: 1.6em;
                margin-bottom: 5px;
            }
        }
    </style>
</head>
<body>
<div class="center-container">
    <div class="main-title">Menú Principal</div>
    <div class="menu-grid">
        <a href="<%= request.getContextPath() %>/SeleccionAccionClienteServlet" class="menu-item">
            <i class="fas fa-users"></i> Cliente
        </a>
        <a href="<%= request.getContextPath() %>/SeleccionAccionVehiculoServlet" class="menu-item">
            <i class="fas fa-car-side"></i> Vehículo
        </a>
        <a href="<%= request.getContextPath() %>/SeleccionAccionOrdenTrabajoServlet" class="menu-item">
            <i class="fas fa-clipboard-list"></i> Orden de Trabajo
        </a>
        <a href="<%= request.getContextPath() %>/operacionesTecnicas" class="menu-item right-bottom">
            <i class="fas fa-cogs"></i> Detalles, Servicios y Repuestos
        </a>
    </div>
</div>
</body>
</html>