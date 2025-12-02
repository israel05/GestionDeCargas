package es.israeldelamo.transportes.dao;

import es.israeldelamo.transportes.bbdd.ConexionBD;
import es.israeldelamo.transportes.modelos.ModeloConductor;
import es.israeldelamo.transportes.utilidades.Alertas;
import es.israeldelamo.transportes.utilidades.ImageViewABlob;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Es el gestor de datos para la base de datos del los objetos CONDUCTOR
 *
 * @author israel
 * @version $Id: $Id
 */
public class DaoConductor {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(DaoConductor.class);


    /**
     * Contiene en forma de String Array la lista de los nombres leído por la base de datos. Es necesario para JasperReportsDataSource
     */
    public String[] dniArray;

    /**
     * Contiene en forma de String Array la lista de los pass leídos por la base de datos. Es necesario para JasperReportsDataSource
     */
    public String[] nombreArray;
    /**
     * Contiene en forma de String Array la lista de los roles leídos por la base de datos. Es necesario para JasperReportsDataSource
     */
    public String[] apellidosArray;
    /**
     * Contiene en forma de String Array la lista de los roles leídos por la base de datos. Es necesario para JasperReportsDataSource
     */
    public String[] contactoRecogidaArray;

    /**
     * Contiene en forma de String Array la lista de los correos leídos por la base de datos. Es necesario para JasperReportsDataSource
     */
    public String[] correoArray;
    /**
     * Contiene en forma de String Array la lista de los telefonos leídos por la base de datos. Es necesario para JasperReportsDataSource
     */
    public String[] telefonoArray;

    public java.awt.Image[] imagenesArray;


    /**
     * Carga el listado de conductores en un observable arrayList
     *
     * @return listadoDeConductores
     */
    public static ObservableList<ModeloConductor> cargarListadoConductores() {
        ConexionBD conexion;
        ObservableList<ModeloConductor> listadoDeConductores = FXCollections.observableArrayList();

        try {
            conexion = new ConexionBD();

            String consulta = "SELECT * FROM CONDUCTOR;";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String contacto_recogida = rs.getString("contacto_recogida");
                String correo_electronico = rs.getString("correo_electronico");
                Blob foto_humano = rs.getBlob("foto_humano");
                Boolean es_internacional = rs.getBoolean("es_internacional");
                Integer cp = rs.getInt("codigo_postal");
                String localidad = rs.getString("localidad");
                String provincia = rs.getString("provincia");
                String pais = rs.getString("pais");
                String tipo_de_carnet = rs.getString("tipo_de_carnet");
                String telefono = rs.getString("telefono");
                String motivo_lista_negra = rs.getString("motivo_lista_negra");
                String nacionalidad = rs.getString("nacionalidad");
                ModeloConductor mp = new ModeloConductor(
                        dni,
                        nombre,
                        apellido,
                        contacto_recogida,
                        correo_electronico,
                        foto_humano,
                        es_internacional,
                        cp,
                        provincia,
                        localidad,
                        pais, tipo_de_carnet, telefono, motivo_lista_negra, nacionalidad
                );
                listadoDeConductores.add(mp);

            }
            pstmt.close();
            rs.close();
            conexion.CloseConexion();
        } catch (
                SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de conductores");
            alertaError.mostrarError(e.getMessage());
        }
        return listadoDeConductores;
    }

    /**
     * Modifica un conductor conservando su DNI, el resto de los parámetros se actualizan sin más
     *
     * @param modeloCoductorActualizado los nuevos datos del conductor
     * @return Verdadero si se ha actualizado bien, falso si dio error
     */
    public static boolean actualizarConductor(ModeloConductor modeloCoductorActualizado) {
        ConexionBD conexion;
        PreparedStatement pstmt;
        try {
            conexion = new ConexionBD();
            //UPDATE CONDUCTOR SET `nombre` = 'noma',
            // `apellido` = 'apedia',
            // `contacto_recogida` = 'colegaa',
            // `correo_electronico` = 'correina',
            // `es_internacional` = '0',
            // `tipo_de_carnet` = 'A2',
            // `telefono` = '2',
            // `localidad` = 'Roma',
            // `provincia` = 'Roma',
            // `pais` = 'Italia',
            // `codigo_postal` = '12',
            // `motivo_lista_negra` = 'Huele',
            // `nacionalidad` = 'Italia'
            // WHERE (`dni` = '123333');
            String consulta = "UPDATE CONDUCTOR SET `nombre`= ?, `apellido`= ?, `contacto_recogida`= ?, " +
                    "`correo_electronico`= ?, `foto_humano`=?, `es_internacional`=?, `codigo_postal`=?, " +
                    "`localidad` = ?, `provincia` = ?, `pais` = ?, tipo_de_carnet = ?, " +
                    "`telefono` = ? , " +
                    "`motivo_lista_negra` = ?, " +
                    "`nacionalidad` = ? " +
                    "WHERE dni=?;";


            pstmt = conexion.getConexion().prepareStatement(consulta);

            pstmt.setString(1, modeloCoductorActualizado.getNombre());
            pstmt.setString(2, modeloCoductorActualizado.getApellido());
            pstmt.setString(3, modeloCoductorActualizado.getContacto_recogida());

            pstmt.setString(4, modeloCoductorActualizado.getCorreo_electronico());
            pstmt.setBlob(5, modeloCoductorActualizado.getFoto_humano());
            pstmt.setBoolean(6, modeloCoductorActualizado.getEs_internacional());
            pstmt.setInt(7, modeloCoductorActualizado.getCodigo_postal());

            pstmt.setString(8, modeloCoductorActualizado.getLocalidad());
            pstmt.setString(9, modeloCoductorActualizado.getProvincia());
            pstmt.setString(10, modeloCoductorActualizado.getPais());

            pstmt.setString(11, modeloCoductorActualizado.getTipo_de_carnet());
            pstmt.setString(12, modeloCoductorActualizado.getTelefono());
            pstmt.setString(13, modeloCoductorActualizado.getMotivo_lista_negra());
            pstmt.setString(14, modeloCoductorActualizado.getNacionalidad());

            pstmt.setString(15, modeloCoductorActualizado.getDni());

            int n = pstmt.executeUpdate();
            pstmt.close();
            conexion.CloseConexion();
            return n > 0;

        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido actualizar el listado de conductores");
            logger.error("No he podido actualizar el listado de conductores");

            alertaError.mostrarError(e.getMessage());
            return false;
        }

    }

    /**
     * Crea un nuevo conductor
     *
     * @param conductorNuevo el nuevo conductor a añadir
     * @return filasAfectadas boolean si se ha modificado con exito o no
     */
    public static boolean nuevoConductor(ModeloConductor conductorNuevo) {
        ConexionBD conexion;
        PreparedStatement pstmt;

        try {
            conexion = new ConexionBD();
            //INSERT INTO `transportes`.`CONDUCTOR`
            // (`dni`, `nombre`, `apellido`, `contacto_recogida`, `correo_electronico`, `es_internacional`,
            // `tipo_de_carnet`, `telefono`, `localidad`, `provincia`, `pais`, `codigo_postal`,
            // `motivo_lista_negra`, `nacionalidad`)
            //
            // VALUES ('123333', 'nom', 'apedi', 'colega', 'correin', '1', 'C1',
            // '234234234', 'Vitoria', 'Álava', 'España', '2', 'Huele bien', 'España');

            String consulta = "INSERT INTO CONDUCTOR (" +
                    "`dni`, " +
                    "`nombre`, " +
                    "`apellido`, " +
                    "`contacto_recogida`, " +
                    "`correo_electronico`, " +
                    "`foto_humano`, " +
                    "`es_internacional`, " +
                    "`tipo_de_carnet`, " +
                    "`telefono`, " +
                    "`localidad`, " +
                    "`provincia`, " +
                    "`pais`, " +
                    "`codigo_postal`, " +
                    "`motivo_lista_negra`, " +
                    "`nacionalidad` )" +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
            pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, conductorNuevo.getDni());
            pstmt.setString(2, conductorNuevo.getNombre());
            pstmt.setString(3, conductorNuevo.getApellido());
            pstmt.setString(4, conductorNuevo.getContacto_recogida());
            pstmt.setString(5, conductorNuevo.getCorreo_electronico());
            pstmt.setBlob(6, conductorNuevo.getFoto_humano());
            pstmt.setBoolean(7, conductorNuevo.getEs_internacional());
            pstmt.setString(8, conductorNuevo.getTipo_de_carnet());
            pstmt.setString(9, conductorNuevo.getTelefono());
            pstmt.setString(10, conductorNuevo.getLocalidad());
            pstmt.setString(11, conductorNuevo.getProvincia());
            pstmt.setString(12, conductorNuevo.getPais());
            pstmt.setInt(13, conductorNuevo.getCodigo_postal());
            pstmt.setString(14, conductorNuevo.getMotivo_lista_negra());
            pstmt.setString(15, conductorNuevo.getNacionalidad());

            int filasAfectadas = pstmt.executeUpdate();
            //if (pstmt != null)
            pstmt.close();
            //if (conexion != null)
            conexion.CloseConexion();

            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de paises");
            logger.error("No he podido cargar el listado de paises");
            alertaError.mostrarError(e.getMessage());
            e.printStackTrace();
            return false;

        }

    }

    /**
     * Carga un conductor en función de un dni
     *
     * @param dniDelConductorACargar el dni del conductor
     * @return un objeto entero de tipo conductor
     */
    public static ObservableList<ModeloConductor> cargarUnConductorConcreto(String dniDelConductorACargar) {
        ConexionBD conexion;
        ObservableList<ModeloConductor> listadoDeConductores = FXCollections.observableArrayList();

        try {
            conexion = new ConexionBD();

            String consulta = "SELECT * FROM CONDUCTOR WHERE dni=?;";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, dniDelConductorACargar);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String contacto_recogida = rs.getString("contacto_recogida");
                String correo_electronico = rs.getString("correo_electronico");
                Blob foto_humano = rs.getBlob("foto_humano");
                Boolean es_internacional = rs.getBoolean("es_internacional");
                Integer cp = rs.getInt("codigo_postal");
                String localidad = rs.getString("localidad");
                String provincia = rs.getString("provincia");
                String pais = rs.getString("pais");
                String tipo_de_carnet = rs.getString("tipo_de_carnet");
                String telefono = rs.getString("telefono");
                String motivo_lista_negra = rs.getString("motivo_lista_negra");
                String nacionalidad = rs.getString("nacionalidad");
                ModeloConductor mp = new ModeloConductor(
                        dni,
                        nombre,
                        apellido,
                        contacto_recogida,
                        correo_electronico,
                        foto_humano,
                        es_internacional,
                        cp,
                        localidad,
                        provincia,
                        pais, tipo_de_carnet, telefono, motivo_lista_negra, nacionalidad
                );
                listadoDeConductores.add(mp);

            }
            pstmt.close();
            rs.close();
            conexion.CloseConexion();
        } catch (
                SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de conductores");
            alertaError.mostrarError(e.getMessage());
        }
        return listadoDeConductores;
    }


    /**
     * Elimina un conductor una vez indicado su dni
     *
     * @param elConductorAEliminar el conductor a eliminar
     * @return boolean de su resultado
     */
    public static boolean eliminarConductor(String elConductorAEliminar) {

        ConexionBD conexion;
        PreparedStatement pstmt;
        try {
            conexion = new ConexionBD();
            String consulta = "DELETE FROM CONDUCTOR WHERE (dni = ?)";
            pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, elConductorAEliminar);

            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();
            conexion.CloseConexion();

            return filasAfectadas > 0;

        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido borrar ese registro de conductores");
            alertaError.mostrarError(e.getMessage());
            logger.error(e.getMessage());
            return false;
        }
    }


    /**
     * EL generador de informes JasperReports no puede leer directamente SQL sin exponer la contraseña
     * Mediante un juego de ArrayList y String[] podemos pasar los datos que queramos
     */
    public void cargarListadoConductoresAArrays() {

        ConexionBD conexion;

        List<String> listaDeStringsDni = new ArrayList<>();
        List<String> listaDeStringsNombres = new ArrayList<>();
        List<String> listaDeStringsApellidos = new ArrayList<>();
        List<String> listaDeStringsContactoRecogida = new ArrayList<>();
        List<String> listaDeStringsCorreo = new ArrayList<>();
        List<String> listaDeStringsTelefono = new ArrayList<>();
        List<Image> listadoDeImagenes = new ArrayList<>();

        try {
            conexion = new ConexionBD();
            String consulta = "SELECT * FROM CONDUCTOR;";

            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String contacto_recogida = rs.getString("contacto_recogida");
                String correo = rs.getString("correo_electronico");
                String telefono = rs.getString("telefono");
                Blob foto = rs.getBlob("foto_humano");

                java.awt.Image image = ImageViewABlob.convertBlobToImage(foto);

                listaDeStringsDni.add(dni);
                listaDeStringsNombres.add(nombre);
                listaDeStringsApellidos.add(apellido);
                listaDeStringsContactoRecogida.add(contacto_recogida);
                listaDeStringsCorreo.add(correo);
                listaDeStringsTelefono.add(telefono);
                listadoDeImagenes.add(image);

            }

            dniArray = listaDeStringsDni.toArray(String[]::new);
            nombreArray = listaDeStringsNombres.toArray(String[]::new);
            apellidosArray = listaDeStringsApellidos.toArray(String[]::new);
            contactoRecogidaArray = listaDeStringsContactoRecogida.toArray(String[]::new);
            correoArray = listaDeStringsCorreo.toArray(String[]::new);
            telefonoArray = listaDeStringsTelefono.toArray(String[]::new);
            imagenesArray = listadoDeImagenes.toArray(java.awt.Image[]::new);
            rs.close();
            conexion.CloseConexion();
        } catch (
                SQLException e) {
            Alertas alertas = new Alertas();
            alertas.mostrarError("Imposible pasar los datos De Conductor Para Informes");
            alertas.mostrarError(e.getMessage());
            //  e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * <p>main auxiliar.</p>
     *
     * @param args an array of {@link java.lang.String} objects
     */
    /*
    public static void main(String[] args) {
        ModeloConductor modelin = new ModeloConductor("1",
                "aasd",
                "perezoa",
                "antonio",
                "antoni@orecogida.pis",
                null,
                true,
                6969,
                "Gaza",
                "Gaza",
                "Israel",
                "C1",
                "67579988",
                "huele a huevos",
                "Israel");

       actualizarConductor(modelin);
    }*/

}
