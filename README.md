# ServiAutoWeb 🚗

> **Nota:** Este es un proyecto académico desarrollado como parte de formación en desarrollo de software.

## Descripción

ServiAutoWeb es una aplicación web de gestión para un taller mecánico. Sistema completo que permite administrar de manera eficiente los servicios, clientes, vehículos, órdenes de trabajo y el control de inventario de un taller mecánico.

## Tecnologías y Temas Aplicados

### Lenguajes de Programación
- **Java** (99.9%) - Backend y lógica principal
- **HTML** (0.1%) - Interfaz web

### Conceptos y Temas Implementados
- ✅ Programación Orientada a Objetos (POO)
- ✅ Patrones de Diseño
- ✅ Arquitectura MVC (Model-View-Controller)
- ✅ Gestión de Bases de Datos
- ✅ Interfaz de Usuario Web
- ✅ Autenticación y Autorización
- ✅ Manejo de Sesiones
- ✅ Validación de Datos
- ✅ Operaciones CRUD

## Funcionalidades Principales

- 📋 Gestión de clientes
- 🔧 Registro de servicios y reparaciones
- 🚙 Control de vehículos
- 📝 Órdenes de trabajo
- 📦 Inventario de repuestos
- 💰 Facturación y reportes
- 👤 Control de usuarios y permisos

## Colaboradores

| Nombre | Rol |
|--------|-----|
| Jose01R | Desarrollador |
| Jared Morales Morales | Desarrollador (Cuenta desactivada) |

Agradecemos especialmente a **Jared Morales Morales** por sus valiosas contribuciones al proyecto, a pesar de haber perdido acceso a su cuenta de GitHub.

## Instrucciones de Uso

### Requisitos Previos
- Java JDK 8 o superior
- Base de Datos (MySQL, PostgreSQL o similar)
- Servidor web compatible con Java (Tomcat, etc.)
- IDE recomendado: IntelliJ IDEA, Eclipse o Visual Studio Code

### Instalación

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/Jose01R/ServiAutoWeb.git
   cd ServiAutoWeb
   ```

2. **Configurar la Base de Datos**
   - Crear una base de datos nueva
   - Ejecutar los scripts SQL ubicados en la carpeta `database/`
   - Configurar las credenciales en el archivo de configuración

3. **Compilar el Proyecto**
   ```bash
   mvn clean install
   ```
   O si usas Gradle:
   ```bash
   gradle build
   ```

4. **Ejecutar la Aplicación**
   - Desplegar en el servidor de aplicaciones (Tomcat, etc.)
   - O ejecutar directamente si es una aplicación standalone
   ```bash
   java -jar ServiAutoWeb.jar
   ```

### Acceso a la Aplicación

1. Abrir un navegador web
2. Navegar a: `http://localhost:8080/ServiAutoWeb` (o el puerto configurado)
3. Usar las credenciales de acceso proporcionadas
4. Navegar por el menú principal para acceder a las diferentes funcionalidades

### Estructura del Proyecto

```
ServiAutoWeb/
├── src/
│   ├── main/
│   │   ├── java/          # Código fuente Java
│   │   ├── resources/     # Configuraciones
│   │   └── webapp/        # Archivos web (HTML, CSS, JS)
│   └── test/              # Pruebas unitarias
├── database/              # Scripts SQL
├── pom.xml               # Configuración Maven
└── README.md             # Este archivo
```

## Guía de Usuario

### Para Administradores
- Acceder con credenciales de administrador
- Gestionar usuarios del sistema
- Configurar parámetros del taller
- Generar reportes completos

### Para Mecánicos
- Crear y actualizar órdenes de trabajo
- Registrar servicios realizados
- Controlar inventario de repuestos
- Actualizar estado de vehículos

### Para Recepcionistas
- Registrar nuevos clientes
- Crear órdenes de servicio
- Gestionar citas
- Generar facturas

## Problemas Comunes

### Error de conexión a BD
- Verificar que el servidor de base de datos está activo
- Confirmar las credenciales en el archivo de configuración
- Revisar que el puerto está abierto

### Error al iniciar sesión
- Verificar credenciales
- Asegurar que el usuario tiene permisos asignados
- Revisar logs del servidor

## Contribuciones

Este es un proyecto académico. Para sugerencias o mejoras, por favor abra un issue o contacte a los colaboradores.

## Licencia

Especificar la licencia del proyecto (MIT, GPL, etc.)

## Contacto

Para preguntas o más información sobre el proyecto, contactar con los desarrolladores.

---

**Última actualización:** Septiembre 2026
