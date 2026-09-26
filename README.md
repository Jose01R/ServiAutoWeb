# ServiAutoWeb 🚗

> **Nota:** Este es un proyecto académico desarrollado como parte de formación en desarrollo de software.

## Descripción

ServiAutoWeb es una aplicación de gestión para un taller mecánico con arquitectura cliente-servidor basada en sockets. El sistema permite administrar clientes, vehículos, servicios, órdenes de trabajo, inventario y usuarios de un taller mecánico.

## Tecnologías y temas aplicados

### Lenguajes de programación
- **Java** (99.9%) - lógica del backend y del servidor
- **HTML** (0.1%) - interfaz web

### Conceptos y temas implementados
- ✅ Programación orientada a objetos (POO)
- ✅ Patrones de diseño
- ✅ Arquitectura cliente-servidor
- ✅ Comunicación por sockets TCP/IP
- ✅ Programación concurrente con hilos / thread pool
- ✅ Gestión de bases de datos
- ✅ Interfaz web
- ✅ Autenticación y autorización
- ✅ Manejo de sesiones
- ✅ Validación de datos
- ✅ Operaciones CRUD

## Funcionalidades principales

- 📋 Gestión de clientes
- 🔧 Registro de servicios y reparaciones
- 🚙 Control de vehículos
- 📝 Órdenes de trabajo
- 📦 Inventario de repuestos
- 💰 Facturación y reportes
- 👤 Control de usuarios y permisos

## Colaboradores

| Nombre | Usuario / rol |
|--------|---------------|
| Jose01R | Desarrollador |
| Arbey Alexander León Machado | TsLexis (Desarrollador) |
| Jared Morales Morales | Desarrollador (cuenta desactivada) |

Agradecemos especialmente a **Jared Morales Morales** por su aportación al proyecto, aunque la cuenta ya no esté disponible.

## Arquitectura del proyecto

Se confirma que el proyecto implementa una estructura de tipo cliente-servidor usando sockets en Java.

- El servidor crea un `ServerSocket` y escucha conexiones entrantes.
- Cada cliente se conecta mediante `Socket`.
- Se gestionan múltiples conexiones con un pool de hilos.
- El servidor atiende peticiones del cliente y coordina la lógica del sistema.

Esto permite una comunicación en red entre varios clientes y un servidor central para gestionar la información del taller.

## Instrucciones de uso

### Requisitos previos
- Java JDK 8 o superior
- Base de datos (MySQL, PostgreSQL o similar)
- IDE recomendado: IntelliJ IDEA, Eclipse o Visual Studio Code

### Instalación

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/Jose01R/ServiAutoWeb.git
   cd ServiAutoWeb
   ```

2. Configurar la base de datos:
   - Crear una base de datos nueva
   - Ejecutar los scripts SQL ubicados en la carpeta `database/`
   - Configurar las credenciales en el archivo de configuración

3. Compilar el proyecto:
   ```bash
   mvn clean install
   ```

### Ejecutar la aplicación

#### Iniciar el servidor
```bash
java -cp target/classes sockets.ServiAutoServer
```

El servidor quedará escuchando en el puerto **5000**.

#### Conectar clientes
```bash
java -cp target/classes cliente.ClienteApp
```

### Acceso a la aplicación

1. Iniciar el servidor
2. Ejecutar el cliente
3. Ingresar las credenciales del sistema
4. Navegar por el menú principal para gestionar el taller

### Estructura del proyecto

```text
ServiAutoWeb/
├── serviAutoServer/
│   ├── src/main/java/
│   │   ├── sockets/      # servidor y manejo de conexiones
│   │   ├── handlers/     # controladores de peticiones
│   │   └── models/       # modelos de datos
│   └── resources/        # configuraciones
├── serviAutoCliente/
│   ├── src/main/java/
│   │   ├── cliente/      # código cliente
│   │   ├── ui/           # interfaz de usuario
│   │   └── models/       # modelos de datos
│   └── resources/        # configuraciones
├── database/             # scripts SQL
├── pom.xml               # configuración Maven
├── README.md             # documentación del proyecto
└── .gitignore
```

## Guía de usuario

### Para administradores
- Gestionar usuarios
- Configurar parámetros del taller
- Revisar reportes y estadísticas

### Para mecánicos
- Crear y actualizar órdenes de servicio
- Registrar reparaciones realizadas
- Actualizar estado del vehículo

### Para recepcionistas
- Registrar nuevos clientes
- Generar citas y servicios
- Gestionar facturas

## Problemas comunes

### Error de conexión al servidor
- Verificar que el servidor está ejecutándose en puerto 5000.
- Confirmar que el firewall o red permiten la conexión.
- Revisar los logs del servidor.

### Error de conexión a base de datos
- Verificar que el servicio de BD está activo.
- Confirmar las credenciales y nombre de la base de datos.
- Revisar la configuración del proyecto.

### Error al iniciar sesión
- Verificar credenciales.
- Comprobar permisos del usuario.
- Revisar registros y logs del sistema.

## Contribuciones

Este es un proyecto académico. Si deseas sugerir mejoras o reportar fallos, puedes crear un issue o contactar a los colaboradores.

## Licencia

Especificar la licencia del proyecto (MIT, GPL, etc.).

## Contacto

Para dudas o más información, contactar con los desarrolladores del proyecto.

---

**Última actualización:** Septiembre 2026
