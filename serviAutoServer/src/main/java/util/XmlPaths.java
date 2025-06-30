package util;

public class XmlPaths {
    // Ruta base donde se almacenarán todos los archivos XML

    //RUTA JARED
    private static final String BASE_PATH = "C:\\Users\\XT\\Documents\\Intellij\\ServiAutoWeb\\";

    //RUTA JOSE
    //private static final String BASE_PATH = "IC:\\Users\\PC\\Documents\\UCR\\Progra_I\\PROYECTO_II\\";

    //RUTA ALEX
    //private static final String BASE_PATH = "C:\\Users\\PC\\Documents\\UCR\\Progra_II\\PROYECTO_II\\";

    // Nombres de los archivos XML
    private static final String CLIENTES_FILE = "clientes.xml";
    private static final String DETALLE_ORDEN_FILE = "detallesOrden.xml";
    private static final String ORDEN_TRABAJO_FILE = "ordenesTrabajo.xml";
    private static final String REPUESTO_FILE = "repuestos.xml";
    private static final String SERVICIO_FILE = "servicios.xml";
    private static final String VEHICULO_FILE = "vehiculos.xml";

    // Métodos para obtener las rutas completas
    public static String getClientesPath() {
        return BASE_PATH + CLIENTES_FILE;
    }

    public static String getDetalleOrdenPath() {
        return BASE_PATH + DETALLE_ORDEN_FILE;
    }

    public static String getOrdenTrabajoPath() {
        return BASE_PATH + ORDEN_TRABAJO_FILE;
    }

    public static String getRepuestoPath() {
        return BASE_PATH + REPUESTO_FILE;
    }

    public static String getServicioPath() {
        return BASE_PATH + SERVICIO_FILE;
    }

    public static String getVehiculoPath() {
        return BASE_PATH + VEHICULO_FILE;
    }

}
