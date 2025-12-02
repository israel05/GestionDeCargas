package es.israeldelamo.transportes.dao;

import es.israeldelamo.transportes.bbdd.ConexionBD;
import es.israeldelamo.transportes.modelos.ModeloDocumentoCapacitacion;
import es.israeldelamo.transportes.utilidades.Alertas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Dao para Documento de Capacitacion dentro de la bbdd
 *
 * @author israel
 * @version $Id: $Id
 */
public class DaoDocumentoCapacitacion {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(DaoDocumentoCapacitacion.class);


    /**
     * Metodo que carga los datos de la tabla DOCUMENTOS DE CAPACITACION y los devuelve para usarlos en un listado de documentos de capacitación
     *
     * @return listado de documentos de capacitación para cargar en un tableview
     */
    public static ObservableList<ModeloDocumentoCapacitacion> cargarListadoDocumentosCapacitacion() {
        ObservableList<ModeloDocumentoCapacitacion> listadoDePaises = FXCollections.observableArrayList();
        ConexionBD conexion;
        try {
            conexion = new ConexionBD();

            String consulta = "SELECT *  FROM DOCUMENTO_CAPACITACION";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String cod_capacitacion = rs.getString("cod_capacitacion");
                Float peligrosidad = rs.getFloat("NATURALEZAS_PELIGROSAS_cod_naturaleza_peligrosa");
                Blob foto = rs.getBlob("foto_en_vigor");
                ModeloDocumentoCapacitacion mp = new ModeloDocumentoCapacitacion(cod_capacitacion, peligrosidad, foto);
                listadoDePaises.add(mp);

            }
            rs.close();
            conexion.CloseConexion();
        } catch (
                SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de Documentos de capacitación");
            alertaError.mostrarError(e.getMessage());
        }
        return listadoDePaises;
    }


    /**
     * Modifica una entrada en la base de datos partiendo del código documento de capacitacion
     *
     * @param documentoOriginal   contiene el código del objeto a modificar en la bbdd
     * @param documentoModificado los datos nuevos a actualizar.
     * @return a boolean
     */
    public static boolean modificarDocumentoCapacitacion(ModeloDocumentoCapacitacion documentoOriginal, ModeloDocumentoCapacitacion documentoModificado) {
        ConexionBD conexion;
        PreparedStatement pstmt;
        try {
            conexion = new ConexionBD();

            String consulta = "UPDATE DOCUMENTO_CAPACITACION SET NATURALEZAS_PELIGROSAS_cod_naturaleza_peligrosa = ?, foto_en_vigor = ? WHERE (cod_capacitacion= ?)";
            pstmt = conexion.getConexion().prepareStatement(consulta);

            pstmt.setFloat(1, documentoModificado.getPeligrosidad());
            pstmt.setBlob(2, documentoModificado.getFoto());


            pstmt.setString(3, documentoOriginal.getCod_capacitacion());
            int filasAfectadas = pstmt.executeUpdate();

            //if (pstmt != null)
            pstmt.close();
            //if (conexion != null)
            conexion.CloseConexion();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de Capacitación");
            logger.error("No he podido cargar el listado de Capacitación");
            alertaError.mostrarError(e.getMessage());
            return false;

        }


    }

    /**
     * Nueva entrada para los documentos de capacitación
     *
     * @param modeloDocumentoCapacitacion el nuevo documento a añadir
     * @return un boolean con éxito o fracaso de la operación
     */
    public static boolean nuevoDocumentoCapacitacion(ModeloDocumentoCapacitacion modeloDocumentoCapacitacion) {
        // hay que verifica que no exista esa entrada. si no, dara error desde base de datos
        ConexionBD conexion;
        PreparedStatement pstmt;
        try {
            conexion = new ConexionBD();
            //INSERT INTO `transportes`.`DOCUMENTO_CAPACITACION` (`cod_capacitacion`, `peligrosidad`) VALUES ('1', '2');
            String consulta = "INSERT INTO DOCUMENTO_CAPACITACION (cod_capacitacion, NATURALEZAS_PELIGROSAS_cod_naturaleza_peligrosa, foto_en_vigor) VALUES (?, ?,?);";
            pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, modeloDocumentoCapacitacion.getCod_capacitacion());
            pstmt.setFloat(2, modeloDocumentoCapacitacion.getPeligrosidad());
            pstmt.setBlob(3, modeloDocumentoCapacitacion.getFoto());

            // FALTA EL BLOB
            int filasAfectadas = pstmt.executeUpdate();// aqui veremos cuantas filas estan afectadas en caso de no poder añadir cosas

            pstmt.close();

            conexion.CloseConexion();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de Documentos de capacitacion");
            logger.error("No he podido cargar el listado de Documentos de capacitacion", e);
            alertaError.mostrarError(e.getMessage());
            return false;
        }
    }

    /**
     * Eliminar un Tipo de Vehiculo de la tabla
     *
     * @param modeloTipoDeVehiculoAEliminar codigo del documento de capacitación a eliminar.
     * @return a boolean
     */
    public static boolean eliminarDocumentoCapacitacion(String modeloTipoDeVehiculoAEliminar) {
        try {
            ConexionBD conexion;
            PreparedStatement pstmt;
            conexion = new ConexionBD();
            //DELETE FROM `transportes`.`DOCUMENTO_CAPACITACION` WHERE (`cod_capacitacion` = '2');
            String consulta = "DELETE FROM DOCUMENTO_CAPACITACION WHERE (cod_capacitacion = ?)";
            pstmt = conexion.getConexion()
                    .prepareStatement(consulta);
            pstmt.setString(1, modeloTipoDeVehiculoAEliminar);
            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();
            conexion.CloseConexion();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido borrar ese registro de documentos de capacitación");
            logger.error("No he podido borrar ese registro de documentos de capacitación");
            alertaError.mostrarError(e.getMessage());
            return false;
        }
    }


    /**
     * EL generador de informes JasperReports no puede leer directamente SQL sin exponer la contraseña
     * Mediante un juego de ArrayList y String[] podemos pasar los datos que queramos
     */
    public void cargarListadoDocumentoCapacitaciónAARrays() {
        //todo
    }


}
