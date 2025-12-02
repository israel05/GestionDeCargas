package es.israeldelamo.transportes.dao;

import es.israeldelamo.transportes.bbdd.ConexionBD;
import es.israeldelamo.transportes.modelos.ModeloPaisYProvinciaYLocalidad;
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
public class DaoPaisYProvinciaYLocalidad {

    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(DaoPaisYProvinciaYLocalidad.class);


    /**
     * Este metodo lee la base de datos y devuelve un observarble arraylist de solo las localidades en función de pais y provincia
     *
     * @param elPaisQueFiltra      el pais que usamos como base
     * @param laProvinciaQueFiltra la provincia del pais que usamos como base
     * @return obersableList de paises y provincias pero de una sola provincia concreta
     */
    public static ObservableList<ModeloPaisYProvinciaYLocalidad> cargarListadoLocalidades(String elPaisQueFiltra, String laProvinciaQueFiltra) {
        ObservableList<ModeloPaisYProvinciaYLocalidad> listadoDeLocalidades = FXCollections.observableArrayList();
        ConexionBD conexion;
        try {
            conexion = new ConexionBD();
            String consulta = "SELECT localidad, provincia, pais FROM LOCALIDADES where (provincia=?) AND (pais=?)";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, laProvinciaQueFiltra);
            pstmt.setString(2, elPaisQueFiltra);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String pais = rs.getString("pais");
                String provincia = rs.getString("provincia");
                String localidad = rs.getString("localidad");
                ModeloPaisYProvinciaYLocalidad mp = new ModeloPaisYProvinciaYLocalidad(pais, provincia, localidad);
                listadoDeLocalidades.add(mp);
            }
            rs.close();
            conexion.CloseConexion();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return listadoDeLocalidades;
    }

    /**
     * Este metodo lee la base de datos y devuelve un observarble arraylist de la tabla entera
     *
     * @return obersableList de paises y provincias todas
     */
    public static ObservableList<ModeloPaisYProvinciaYLocalidad> cargarListadoLocalidades() {
        ObservableList<ModeloPaisYProvinciaYLocalidad> listadoDeLocalidades = FXCollections.observableArrayList();
        ConexionBD conexion;
        try {
            conexion = new ConexionBD();
            String consulta = "SELECT localidad, provincia, pais FROM LOCALIDADES";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String pais = rs.getString("pais");
                String provincia = rs.getString("provincia");
                String localidad = rs.getString("localidad");
                ModeloPaisYProvinciaYLocalidad mp = new ModeloPaisYProvinciaYLocalidad(pais, provincia, localidad);
                listadoDeLocalidades.add(mp);
            }
            rs.close();
            conexion.CloseConexion();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return listadoDeLocalidades;
    }

    /**
     * Modifica una localidad a partir de un modelo de localidad y su nuevo nombre
     *
     * @param paisyprovinciaylocalidad la localidad que va a ser modificada
     * @param nuevaLocalidad           nuevo nombre para la localidad
     * @return a boolean
     */
    public static boolean modificarLocalidad(ModeloPaisYProvinciaYLocalidad paisyprovinciaylocalidad, String nuevaLocalidad) {
        ConexionBD conexion;
        PreparedStatement pstmt;

        try {
            conexion = new ConexionBD();

            String consulta = "UPDATE LOCALIDADES SET localidad = ? WHERE localidad = ? and pais = ? and provincia = ?";
            pstmt = conexion.getConexion().prepareStatement(consulta);

            pstmt.setString(1, nuevaLocalidad);
            pstmt.setString(2, paisyprovinciaylocalidad.getLocalidad());
            pstmt.setString(3, paisyprovinciaylocalidad.getPais());
            pstmt.setString(4, paisyprovinciaylocalidad.getProvincia());


            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();
            conexion.CloseConexion();

            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de paises y provincias y localidades");
            alertaError.mostrarError(e.getMessage());
            logger.error("No he podido cargar el listado de paises y provincias y localidades");
            return false;

        }

    }

    /**
     * Nueva localidad a partir de un modelo localidad que se le pase
     *
     * @param modeloPaisYProvinciaYLocalidadNuevo el modelo localidad a pasar.
     * @return a boolean
     */
    public static boolean nuevoPaisYProvinciaYLocalidades(ModeloPaisYProvinciaYLocalidad modeloPaisYProvinciaYLocalidadNuevo) {
        try {
            ConexionBD conexion;
            PreparedStatement pstmt;
            conexion = new ConexionBD();
            String consulta = "INSERT INTO LOCALIDADES (pais , provincia, localidad ) VALUES ( ?, ?,?)";
            pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(2, modeloPaisYProvinciaYLocalidadNuevo.getProvincia());
            pstmt.setString(1, modeloPaisYProvinciaYLocalidadNuevo.getPais());
            pstmt.setString(3, modeloPaisYProvinciaYLocalidadNuevo.getLocalidad());
            int filasAfectadas = pstmt.executeUpdate();
            //if (pstmt != null)
            pstmt.close();
            //if (conexion != null)
            conexion.CloseConexion();

            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de localidades");
            alertaError.mostrarError(e.getMessage());
            logger.error("No he podido cargar el listado de localidades");
            return false;

        }

    }

    /**
     * Elimina una localidad en función de cuál haya sido seleccionada mediamnte doble click
     *
     * @param mp el Modelo Localidad a eliminar
     * @return a boolean
     */
    public static boolean eliminarLocalidad(ModeloPaisYProvinciaYLocalidad mp) {

        try {
            ConexionBD conexion;
            PreparedStatement pstmt;
            conexion = new ConexionBD();

            String consulta = "DELETE FROM LOCALIDADES WHERE (pais = ?) AND (provincia= ?) AND (localidad = ?)";
            pstmt = conexion.getConexion()
                    .prepareStatement(consulta);

            pstmt.setString(1, mp.getPais());
            pstmt.setString(2, mp.getProvincia());
            pstmt.setString(3, mp.getLocalidad());
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
