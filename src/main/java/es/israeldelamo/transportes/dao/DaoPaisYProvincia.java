package es.israeldelamo.transportes.dao;

import es.israeldelamo.transportes.bbdd.ConexionBD;
import es.israeldelamo.transportes.modelos.ModeloPaisYProvincia;
import es.israeldelamo.transportes.utilidades.Alertas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Este DAO debe verificar la integridad referencial de la tabla PROVINCIAS, impidiendo ningun añadido si el pais no
 * existia, su misión es informar al usuario de los errores posibles a la hora añadir esa información
 *
 * @author israel
 * @version $Id: $Id
 */
public class DaoPaisYProvincia {

    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(DaoPaisYProvincia.class);


    /**
     * Este metodo lee la base de datos y devuelve un observarble arraylist de todos los paises y sus provincias
     *
     * @return obersableList de paises y provincias
     */
    public static ObservableList<ModeloPaisYProvincia> cargarListadoProvincias() {
        ConexionBD conexion;
        ObservableList<ModeloPaisYProvincia> listadoDePaises = FXCollections.observableArrayList();

        try {
            conexion = new ConexionBD();

            String consulta = "SELECT Paises_pais,provincia FROM PROVINCIAS";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String pais = rs.getString("Paises_pais");
                String provincia = rs.getString("provincia");
                ModeloPaisYProvincia mp = new ModeloPaisYProvincia(pais, provincia);
                listadoDePaises.add(mp);

            }
            rs.close();
            conexion.CloseConexion();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return listadoDePaises;
    }

    /**
     * Este metodo lee la base de datos y devuelve un observarble arraylist de un solo país y  tosas sus provincias
     *
     * @param elPaisAFiltrar hay que pasarle un pais para que nos devuelva sus provincias
     * @return obersableList de paises y provincias pero de un solo pais
     */
    public static ObservableList<ModeloPaisYProvincia> cargarListadoProvincias(String elPaisAFiltrar) {
        ObservableList<ModeloPaisYProvincia> listadoDePaises = FXCollections.observableArrayList();
        ConexionBD conexion;
        try {
            conexion = new ConexionBD();
            String consulta = "SELECT Paises_pais,provincia FROM PROVINCIAS where Paises_pais=? ";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, elPaisAFiltrar);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String pais = rs.getString("Paises_pais");
                String provincia = rs.getString("provincia");
                ModeloPaisYProvincia mp = new ModeloPaisYProvincia(pais, provincia);
                listadoDePaises.add(mp);
            }
            rs.close();
            conexion.CloseConexion();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return listadoDePaises;
    }


    /**
     * Metodo que modifica los datos de un  provincia en la BD. AÑADIR RESTRICCIÓN PARA QUE NO ENTREN PAISES
     *
     * @param paisyprovinciaOriginal Instancia del pais con datos nuevos
     * @param nuevaProvincia         Nueva provincia del pais a modificar
     * @return true/false
     */
    public static boolean modificarProvincia(ModeloPaisYProvincia paisyprovinciaOriginal, ModeloPaisYProvincia nuevaProvincia) {
        ConexionBD conexion;
        PreparedStatement pstmt;

        try {
            conexion = new ConexionBD();
            // UPDATE `transportes`.`PROVINCIAS` SET `provincia` = 'Álava/Araba' WHERE (`provincia` = 'Álava') and (`Paises_pais` = 'España');

            String consulta = "UPDATE PROVINCIAS SET provincia = ? WHERE provincia = ? and Paises_pais = ?";
            pstmt = conexion.getConexion().prepareStatement(consulta);

            pstmt.setString(1, nuevaProvincia.getProvincia());
            pstmt.setString(2, paisyprovinciaOriginal.getProvincia());
            pstmt.setString(3, paisyprovinciaOriginal.getPais());

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
            return false;

        }

    }

    /**
     * Crea una nueva entrada en Pais y Provincia
     *
     * @param modeloPaisYProvinciaNuevo el nuevo Pais y Provincia en la tabla, pero el pais debe existir antes
     * @return a boolean
     */
    public static boolean nuevoPaisYProvincia(ModeloPaisYProvincia modeloPaisYProvinciaNuevo) {

        try {
            ConexionBD conexion;
            PreparedStatement pstmt;

            conexion = new ConexionBD();
            String consulta = "INSERT INTO PROVINCIAS (provincia , Paises_pais ) VALUES ( ?, ?)";

            pstmt = conexion.getConexion().prepareStatement(consulta);

            pstmt.setString(1, modeloPaisYProvinciaNuevo.getProvincia());
            pstmt.setString(2, modeloPaisYProvinciaNuevo.getPais());

            int filasAfectadas = pstmt.executeUpdate();
            //if (pstmt != null)
            pstmt.close();
            //if (conexion != null)
            conexion.CloseConexion();

            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de paises");
            alertaError.mostrarError(e.getMessage());
            return false;

        }
    }

    /**
     * Elimina un vehículo seleccionado en función de su cod_vehiculo
     *
     * @param modeloABorrar el vehiculo con código a borrar
     * @return a boolean
     */
    public static boolean eliminarProvincia(ModeloPaisYProvincia modeloABorrar) {

        try {
            ConexionBD conexion;
            PreparedStatement pstmt;
            conexion = new ConexionBD();

            String consulta = "DELETE FROM PROVINCIAS WHERE (provincia = ?) AND (Paises_pais = ?)";
            pstmt = conexion.getConexion()
                    .prepareStatement(consulta);
            pstmt.setString(2, modeloABorrar.getPais());
            pstmt.setString(1, modeloABorrar.getProvincia());
            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();
            conexion.CloseConexion();

            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido borrar ese registro de provincias, puede que existan localidades asociadas");
            alertaError.mostrarError(e.getMessage());
            logger.error("No he podido borrar ese registro de provincias, puede que existan localidades asociadas");
            return false;
        }
    }

}
