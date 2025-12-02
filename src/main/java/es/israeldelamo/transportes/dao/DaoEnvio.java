package es.israeldelamo.transportes.dao;

import es.israeldelamo.transportes.bbdd.ConexionBD;
import es.israeldelamo.transportes.modelos.ModeloEnvio;
import es.israeldelamo.transportes.modelos.ModeloEnvioUnico;
import es.israeldelamo.transportes.utilidades.Alertas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase par gestionar las llamadas a la tabla ENVIOS y poder rellenar tablas, modificar elementos y f
 *
 * @author israel
 * @version $Id: $Id
 */
public class DaoEnvio {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(DaoEnvio.class);

    /**
     * Contiene en forma de String Array la lista de los trackers leídos por la base de datos. Es necesario para JasperReportsDataSource
     */
    public String[] cod_trackingArray;
    /**
     * Contiene en forma de String Array la lista de los conductores de datos. Es necesario para JasperReportsDataSource
     */
    public String[] cod_conductorArray;
    /**
     * Contiene en forma de String Array la lista de los vehiculos leídos por la base de datos. Es necesario para JasperReportsDataSource
     */
    public String[] cod_vehiculoArray;
    /**
     * Contiene en forma de String Array la lista de las entregas leídos por la base de datos. Es necesario para JasperReportsDataSource
     */
    public String[] cod_carga_entregaArray;

    /**
     * Método que carga los datos de la tabla ENVIO y los devuelve para usarlos en un listado de paises
     *
     * @return listado de paises para cargar en un tableview
     */
    public static ObservableList<ModeloEnvio> cargarListadoEnvios() {
        ConexionBD conexion;
        ObservableList<ModeloEnvio> listadoDeEnvios = FXCollections.observableArrayList();
        try {
            conexion = new ConexionBD();
            String consulta = "SELECT * FROM ENVIO";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                String cod_tracking = rs.getString("cod_tracking");
                int cod_carga_entrega = rs.getInt("cod_carga_entrega");
                String cod_conductor = rs.getString("cod_conductor");
                String cod_vehiculo = rs.getString("cod_vehiculo");

                ModeloEnvio mp = new ModeloEnvio(cod_tracking,
                        cod_carga_entrega,
                        cod_conductor,
                        cod_vehiculo
                );
                listadoDeEnvios.add(mp);
            }
            rs.close();
            conexion.CloseConexion();
        } catch (
                SQLException e) {
            logger.error(e.getMessage());
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de Envios");
            alertaError.mostrarError(e.getMessage());
        }
        return listadoDeEnvios;
    }


    /**
     * Modifica en envio a partir de su codigo de tracker y un objeto Modelo de envio con los datos nuevos
     *
     * @param envioOriginal codigo como cadena del tracker
     * @param nuevoEnvio    el nuevo objeto, pero perdiendo su codigo de tracker
     * @return boolean del resultado
     */
    public static boolean modificarEnvio(String envioOriginal, ModeloEnvio nuevoEnvio) {
        ConexionBD conexion;
        PreparedStatement pstmt;

        try {
            conexion = new ConexionBD();
//UPDATE ENVIO SET cod_carga_entrega = '2', cod_conductor = '1', `cod_vehiculo` = '45688gg' WHERE (`cod_tracking` = '2');
            String consulta = "UPDATE ENVIO SET cod_carga_entrega = ?, cod_conductor=? , cod_vehiculo = ? WHERE (cod_tracking = ?)";
            pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setInt(1, nuevoEnvio.getCod_carga_entrega());
            pstmt.setString(2, nuevoEnvio.getCod_conductor());
            pstmt.setString(3, nuevoEnvio.getCod_vehiculo());
            pstmt.setString(4, envioOriginal);


            int filasAfectadas = pstmt.executeUpdate();
            //if (pstmt != null)
            pstmt.close();
            //if (conexion != null)
            conexion.CloseConexion();


            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de envios");
            alertaError.mostrarError(e.getMessage());
            logger.error(e.getMessage());
            return false;

        }

    }

    /**
     * Elimina un envio una vez indicado su tracker
     *
     * @param elTrackerAEliminar el envio a eliminar
     * @return boolean de su resultado
     */
    public static boolean eliminarEnvio(String elTrackerAEliminar) {

        ConexionBD conexion;
        PreparedStatement pstmt;
        try {
            conexion = new ConexionBD();
            String consulta = "DELETE FROM ENVIO WHERE (cod_tracking = ?)";
            pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, elTrackerAEliminar);

            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();
            conexion.CloseConexion();

            return filasAfectadas > 0;

        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido borrar ese registro");
            alertaError.mostrarError(e.getMessage());
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * Nuevo envio de modelo de envios
     *
     * @param envioNuevo el nuenvo envio
     * @return a boolean
     */
    public static boolean nuevoEnvio(ModeloEnvio envioNuevo) {
        // hay que verifica que no exista esa entrada. si no, dara error desde base de datos
        ConexionBD conexion;
        PreparedStatement pstmt;
        try {
            conexion = new ConexionBD();
            String consulta = "INSERT INTO ENVIO (`cod_tracking`, `cod_carga_entrega`, `cod_conductor`,cod_vehiculo ) VALUES (?, ?, ?, ?);";
            pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, envioNuevo.getCod_tracking());
            pstmt.setInt(2, envioNuevo.getCod_carga_entrega());
            pstmt.setString(3, envioNuevo.getCod_conductor());
            pstmt.setString(4, envioNuevo.getCod_vehiculo());
            int filasAfectadas = pstmt.executeUpdate();// aquí veremos cuantas filas están afectadas en caso de no poder añadir cosas

            pstmt.close();

            conexion.CloseConexion();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido hacer una reserva en la carga de envios");
            alertaError.mostrarError(e.getMessage());
            logger.error("No he podido hacer una reserva en la carga de envios");
            return false;
        }

    }


    /**
     * Método que carga los datos de la tabla ENVIO según los últimos 10 envios y los devuelve en un array de modelos de envio
     *
     * @return listado de los ultimos 10 envios
     */
    public static ObservableList<ModeloEnvio> cargarListadoUltimosDiezEnvios() {
        ConexionBD conexion;
        ObservableList<ModeloEnvio> listadoDeEnvios = FXCollections.observableArrayList();
        try {
            conexion = new ConexionBD();
            String consulta = "SELECT * FROM ENVIO ORDER BY cod_tracking DESC LIMIT 10";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                String cod_tracking = rs.getString("cod_tracking");
                int cod_carga_entrega = rs.getInt("cod_carga_entrega");
                String cod_conductor = rs.getString("cod_conductor");
                String cod_vehiculo = rs.getString("cod_vehiculo");

                ModeloEnvio mp = new ModeloEnvio(cod_tracking,
                        cod_carga_entrega,
                        cod_conductor,
                        cod_vehiculo
                );
                listadoDeEnvios.add(mp);
            }
            rs.close();
            conexion.CloseConexion();
        } catch (
                SQLException e) {
            logger.error(e.getMessage());
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de Envios");
            alertaError.mostrarError(e.getMessage());
        }
        return listadoDeEnvios;
    }


    /**
     * Busca el envio en función del código de carga, que es único en su creacción y nos devuelve los datos básicos de ese envio Unico
     * para poderlos usar en un informe de JasperReports en formato ModeloEnvioUnico
     */

    public ModeloEnvioUnico cargarEnvioUnicoAArray(int codigo_carga) {
        List<String> listaDeStringsTrackings = new ArrayList<>();
        List<String> listaDeStringsCodigoCargaEntrega = new ArrayList<>();
        List<String> listaDeStringsCodigoConductor = new ArrayList<>();
        List<String> listaDeStringsCodigoVehiculos = new ArrayList<>();
        ConexionBD conexion;
        //	ObservableList<ModeloEnvio> listadoDeEnvios = FXCollections.observableArrayList();
        ModeloEnvioUnico meu = new ModeloEnvioUnico("", 0, "", ""); //lo creamos limpio
        try {
            conexion = new ConexionBD();
            String consulta = "SELECT cod_tracking, cod_conductor, cod_vehiculo, cod_carga_entrega FROM ENVIO WHERE cod_carga_entrega=?;";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setInt(1, codigo_carga);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                meu.setCod_conductor(rs.getString("cod_conductor"));
                meu.setCod_carga_entrega(rs.getInt("cod_carga_entrega"));
                meu.setCod_vehiculo(rs.getString("cod_vehiculo"));
                meu.setCod_tracking(rs.getString("cod_tracking"));
                //rellenamos los Array String
                listaDeStringsTrackings.add(meu.getCod_tracking());
                listaDeStringsCodigoConductor.add(meu.getCod_conductor());
                listaDeStringsCodigoVehiculos.add(meu.getCod_vehiculo());
                listaDeStringsCodigoCargaEntrega.add(String.valueOf(meu.getCod_carga_entrega()));
            }
            pstmt.close();
            rs.close();
            conexion.CloseConexion();


            cod_trackingArray = listaDeStringsTrackings.toArray(String[]::new);
            cod_conductorArray = listaDeStringsCodigoConductor.toArray(String[]::new);
            cod_vehiculoArray = listaDeStringsCodigoVehiculos.toArray(String[]::new);
            cod_carga_entregaArray = listaDeStringsCodigoCargaEntrega.toArray(String[]::new);


        } catch (
                SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar ese modelo de envio único");
            alertaError.mostrarError(e.getMessage());
        }
        return meu;
    }


}
