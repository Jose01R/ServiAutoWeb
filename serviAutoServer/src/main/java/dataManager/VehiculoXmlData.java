package dataManager;

import domain.Vehiculo;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VehiculoXmlData {
    private Document document;
    private Element raiz;
    private String rutaDocumento;
    private static final String ROOT_ELEMENT_NAME = "vehiculos";
    private static final String VEHICULO_ELEMENT_NAME = "vehiculo";

    public VehiculoXmlData(String rutaDocumento, String nombreRaiz) throws IOException {
        this.rutaDocumento = rutaDocumento;
        this.raiz = new Element(nombreRaiz);
        this.document = new Document(raiz);
        guardar();
    }

    private VehiculoXmlData(String rutaDocumento) throws JDOMException, IOException {
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setIgnoringBoundaryWhitespace(true);

        this.document = saxBuilder.build(rutaDocumento);
        this.raiz = document.getRootElement();
        this.rutaDocumento = rutaDocumento;
    }

    public static VehiculoXmlData abrirDocumento(String rutaDocumento) throws JDOMException, IOException {
        if (new File(rutaDocumento).exists()) {
            return new VehiculoXmlData(rutaDocumento);
        } else {
            return new VehiculoXmlData(rutaDocumento, ROOT_ELEMENT_NAME);
        }
    }

    private void guardar() throws IOException {
        Format format = Format.getPrettyFormat();
        format.setEncoding("UTF-8");
        XMLOutputter xmlOutputter = new XMLOutputter(format);

        try (PrintWriter printWriter = new PrintWriter(this.rutaDocumento)) {
            xmlOutputter.output(this.document, printWriter);
        }
    }

    /**
     * Inserta un nuevo vehículo en el documento XML.
     * @param vehiculo El objeto Vehiculo a insertar.
     * @param idClienteDueno El ID del cliente al que pertenece este vehículo.
     * @throws IOException Si ocurre un error de E/S al guardar.
     */
    public void insertarVehiculo(Vehiculo vehiculo, String idClienteDueno) throws IOException {
        if (getVehiculoPorPlaca(vehiculo.getPlaca()).isPresent()) {
            System.out.println("Error: Ya existe un vehículo con la placa '" + vehiculo.getPlaca() + "'. No insertado.");
            return;
        }

        Element eVehiculo = new Element(VEHICULO_ELEMENT_NAME);
        eVehiculo.setAttribute("placa", vehiculo.getPlaca());
        eVehiculo.setAttribute("idClienteDueno", idClienteDueno); // Relación con Cliente

        eVehiculo.addContent(new Element("color").setText(vehiculo.getColor()));
        eVehiculo.addContent(new Element("marca").setText(vehiculo.getMarca()));
        eVehiculo.addContent(new Element("estilo").setText(vehiculo.getEstilo()));
        eVehiculo.addContent(new Element("anio").setText(String.valueOf(vehiculo.getAnio())));
        eVehiculo.addContent(new Element("vin").setText(vehiculo.getVin()));
        eVehiculo.addContent(new Element("cilindraje").setText(String.valueOf(vehiculo.getCilindraje())));

        this.raiz.addContent(eVehiculo);
        guardar();
    }

    /**
     * Obtiene una lista de todos los vehículos en el documento XML.
     * @return Una lista de objetos Vehiculo.
     */
    public List<Vehiculo> getTodosVehiculos() {
        List<Element> eListaVehiculos = raiz.getChildren(VEHICULO_ELEMENT_NAME);
        List<Vehiculo> vehiculos = new ArrayList<>();
        // En un escenario real, necesitarías un ClienteXmlData para cargar el objeto Cliente
        // ClienteXmlData clienteData = ClienteXmlData.abrirDocumento("ruta/clientes.xml");
        for (Element eVehiculo : eListaVehiculos) {
            Vehiculo vehiculoActual = new Vehiculo(
                    eVehiculo.getAttributeValue("placa"),
                    eVehiculo.getChildText("color"),
                    eVehiculo.getChildText("marca"),
                    eVehiculo.getChildText("estilo"),
                    Integer.parseInt(eVehiculo.getChildText("anio")),
                    eVehiculo.getChildText("vin"),
                    Double.parseDouble(eVehiculo.getChildText("cilindraje"))
            );
            // Asignar el dueño (Cliente) - esto implicaría cargar el cliente por su ID
            // String idClienteDueno = eVehiculo.getAttributeValue("idClienteDueno");
            // clienteData.getClientePorId(idClienteDueno).ifPresent(vehiculoActual::setDueno);
            vehiculos.add(vehiculoActual);
        }
        return vehiculos;
    }

    /**
     * Busca un vehículo por su placa.
     * @param placa La placa del vehículo a buscar.
     * @return Un Optional que contiene el Vehiculo si se encuentra, o un Optional vacío.
     */
    public Optional<Vehiculo> getVehiculoPorPlaca(String placa) {
        return getTodosVehiculos().stream()
                .filter(v -> v.getPlaca().equals(placa))
                .findFirst();
    }

    /**
     * Actualiza un vehículo existente en el documento XML.
     * @param vehiculoActualizado El objeto Vehiculo con los datos actualizados.
     * @return true si el vehículo fue actualizado, false si no se encontró.
     * @throws IOException Si ocurre un error de E/S al guardar.
     */
    public boolean actualizarVehiculo(Vehiculo vehiculoActualizado) throws IOException {
        List<Element> eListaVehiculos = raiz.getChildren(VEHICULO_ELEMENT_NAME);
        for (Element eVehiculo : eListaVehiculos) {
            if (eVehiculo.getAttributeValue("placa").equals(vehiculoActualizado.getPlaca())) {
                eVehiculo.getChild("color").setText(vehiculoActualizado.getColor());
                eVehiculo.getChild("marca").setText(vehiculoActualizado.getMarca());
                eVehiculo.getChild("estilo").setText(vehiculoActualizado.getEstilo());
                eVehiculo.getChild("anio").setText(String.valueOf(vehiculoActualizado.getAnio()));
                eVehiculo.getChild("vin").setText(vehiculoActualizado.getVin());
                eVehiculo.getChild("cilindraje").setText(String.valueOf(vehiculoActualizado.getCilindraje()));
                guardar();
                return true;
            }
        }
        System.out.println("Error: Vehículo con placa '" + vehiculoActualizado.getPlaca() + "' no encontrado para actualizar.");
        return false;
    }

    /**
     * Elimina un vehículo del documento XML por su placa.
     * @param placa La placa del vehículo a eliminar.
     * @return true si el vehículo fue eliminado, false si no se encontró.
     * @throws IOException Si ocurre un error de E/S al guardar.
     */
    public boolean eliminarVehiculo(String placa) throws IOException {
        List<Element> eListaVehiculos = raiz.getChildren(VEHICULO_ELEMENT_NAME);
        for (Element eVehiculo : eListaVehiculos) {
            if (eVehiculo.getAttributeValue("placa").equals(placa)) {
                raiz.removeContent(eVehiculo);
                guardar();
                return true;
            }
        }
        System.out.println("Error: Vehículo con placa '" + placa + "' no encontrado para eliminar.");
        return false;
    }
}

