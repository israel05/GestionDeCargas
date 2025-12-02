package es.israeldelamo.transportes.dao;

import es.israeldelamo.transportes.bbdd.ConexionBD;
import es.israeldelamo.transportes.modelos.ModeloConductorTieneVehiculo;
import es.israeldelamo.transportes.modelos.ModeloVehiculo;
import es.israeldelamo.transportes.utilidades.Alertas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Dao para la tabla que relaciona la tabla de Vehiculos con la tabla de conductores
 *
 * @author israel
 * @version $Id: $Id
 */
public class DaoConductorTieneVehiculo {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(DaoConductorTieneVehiculo.class);

    /**
     * String array para poder usar JasperReports sobre  los codigos de vehiculos
     */
    public String[] cod_vehiculoArray;
    /**
     * String array para poder usar JasperReports sobre los codigos de conductores
     */
    public String[] cod_conductorArray;

    /**
     * Lee la base de datos llamda CONDUCTOR_has_VEHICULOS y construye un observablelist de modeloConductorTieneVehiculo
     *
     * @return aquello que contiene la base de datos
     */
    public static ObservableList<ModeloConductorTieneVehiculo> cargarListadoConductorTieneVehiculo() {
        ObservableList<ModeloConductorTieneVehiculo> listadoDeConductorTieneVehiculo = FXCollections.observableArrayList();
        ConexionBD conexion;
        try {
            conexion = new ConexionBD();
            String consulta = "SELECT * FROM CONDUCTOR_has_VEHICULOS";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String conductor_dni = rs.getString("conductor_dni");
                String vehiculos_cod_vehiculo = rs.getString("vehiculos_cod_vehiculo");
                ModeloConductorTieneVehiculo l = new ModeloConductorTieneVehiculo(conductor_dni, vehiculos_cod_vehiculo);
                listadoDeConductorTieneVehiculo.add(l);
            }
            rs.close();
            conexion.CloseConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listadoDeConductorTieneVehiculo;
    }

    /**
     * Cambia el vehículo que tiene un conductor concreto
     *
     * @param vehiculo  el coche que se le va a asignar
     * @param conductor El conductor al que le modificamos el coche
     * @return a boolean
     */
    public static boolean modificarConductorTieneVehiculo(ModeloConductorTieneVehiculo conductor, String vehiculo) {
        ConexionBD conexion;
        PreparedStatement pstmt;
        try {
            conexion = new ConexionBD();
            //UPDATE `transportes`.`CONDUCTOR_has_VEHICULOS` SET `vehiculos_cod_vehiculo` = '2505LGM' WHERE (`conductor_dni` = '1') and (`vehiculos_cod_vehiculo` = '45688gg');
            String consulta = " UPDATE CONDUCTOR_has_VEHICULOS SET vehiculos_cod_vehiculo = ? WHERE ('conductor_dni' = ?) and ('vehiculos_cod_vehiculo' = ?)";
            pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, vehiculo); //nuevo vehiculo
            pstmt.setString(2, conductor.getConductor_dni());
            pstmt.setString(3, conductor.getVehiculos_cod_vehiculo());

            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();
            conexion.CloseConexion();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de conductores con coches");
            logger.error("No he podido cargar el listado de conductores con coches");
            alertaError.mostrarError(e.getMessage());
            return false;
        }
    }


    /**
     * Asocia un nuevo conductor con un vehiculo
     *
     * @param nuevoConductor el nueva asociacion de conductor con vehiculo
     * @return a boolean
     */
    public static boolean nuevoConductorTieneVehiculo(ModeloConductorTieneVehiculo nuevoConductor) {
        // hay que verifica que no exista esa entrada. si no, dara error desde base de datos
        ConexionBD conexion;
        PreparedStatement pstmt;
        try {
            conexion = new ConexionBD();

            String consulta = "INSERT INTO CONDUCTOR_has_VEHICULOS (conductor_dni, vehiculos_cod_vehiculo) VALUES (?, ?);";
            pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, nuevoConductor.getConductor_dni());
            pstmt.setString(2, nuevoConductor.getVehiculos_cod_vehiculo());

            int filasAfectadas = pstmt.executeUpdate();// aqui veremos cuantas filas estan afectadas en caso de no poder añadir cosas

            pstmt.close();

            conexion.CloseConexion();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de Vehiculos tiene login");
            logger.error("No he podido cargar el listado de Vehiculos tiene login");
            alertaError.mostrarError(e.getMessage());
            return false;
        }

    }

    /**
     * <p>cargarListadoDeVehiculosQueTieneUnConductor.</p>
     *
     * @param dniDelConductor a {@link java.lang.String} object
     * @return a {@link javafx.collections.ObservableList} object
     */
    public static ObservableList<ModeloVehiculo> cargarListadoDeVehiculosQueTieneUnConductor(String dniDelConductor) {


        ObservableList<ModeloVehiculo> listaDeVehiculosDelConductor = FXCollections.observableArrayList();
        try {
            ConexionBD conexion;
            conexion = new ConexionBD();
            String consulta = "SELECT v.* FROM VEHICULOS v   JOIN CONDUCTOR_has_VEHICULOS chv ON v.cod_vehiculo = chv.vehiculos_cod_vehiculo JOIN CONDUCTOR c ON chv.conductor_dni = c.dni WHERE c.dni = ?";

            PreparedStatement pstmt;
            pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, dniDelConductor);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String cod_vehiculo = rs.getString("cod_vehiculo");
                float mma = rs.getFloat("MMA");
                float din_a = rs.getFloat("dimension_A");
                float din_l = rs.getFloat("dimension_L");
                float din_h = rs.getFloat("dimension_H");
                float carga_util = rs.getFloat("carga_util");
                String cod_tipo_vehiculo = rs.getString("cod_tipo_vehiculo");
                ModeloVehiculo mp = new ModeloVehiculo(cod_vehiculo, mma, din_a, din_l, din_h, carga_util, cod_tipo_vehiculo);
                listaDeVehiculosDelConductor.add(mp);
            }
            rs.close();
            conexion.CloseConexion();


        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he encontrado ningún vehiculo para ese conductor");
            logger.error("No he encontrado ningún vehiculo para ese conductor");
            alertaError.mostrarError(e.getMessage());


        }
        return listaDeVehiculosDelConductor;
    }

    /**
     * Borra una entrada de la relacción entre Conductores y Vehiculos
     *
     * @param aBorrar la asociación a borrar
     * @return a boolean
     */
    public static boolean borrarConductoresTienenVehiculo(ModeloConductorTieneVehiculo aBorrar) {
        ConexionBD conexion;
        PreparedStatement pstmt;
        try {
            conexion = new ConexionBD();
            //DELETE FROM CONDUCTOR_has_VEHICULOS WHERE (conductor_dni = ?) and (vehiculos_cod_vehiculo = ?);
            String consulta = "DELETE FROM CONDUCTOR_has_VEHICULOS WHERE (conductor_dni = ?) and (vehiculos_cod_vehiculo = ?)";
            pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, aBorrar.getConductor_dni());
            pstmt.setString(2, aBorrar.getVehiculos_cod_vehiculo());
            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();
            conexion.CloseConexion();

            return filasAfectadas > 0;

        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido borrar ese registro");
            logger.error("No he podido borrar ese registro");
            alertaError.mostrarError(e.getMessage());
            return false;
        }
    }

    /**
     * EL generador de informes JasperReports no puede leer directamente SQL sin exponer la contraseña
     * Mediante un juego de ArrayList y String[] podemos pasar los datos que queramos
     */
    public void cargarListadoConductoresTienenVehiculoAArrays() {
        //todo
    }

}
