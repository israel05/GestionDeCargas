package es.israeldelamo.transportes.dao;

import es.israeldelamo.transportes.bbdd.ConexionBD;
import es.israeldelamo.transportes.modelos.ModeloTipoDeBulto;
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
 * Clase par gesitonar las llamdas a la tabla PAISES y poder rellenar tablas, modificar elementos y f
 *
 * @author israel
 * @version $Id: $Id
 */
public class DaoTipoDeBulto {

    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(DaoTipoDeBulto.class);


    /**
     * Metodo que carga los datos de la tabla TIPO DE BULTO y los devuelve para usarlos en un listado de tipos de bultos
     *
     * @return listado de tipos de bultos para cargar en un tableview
     */
    public static ObservableList<ModeloTipoDeBulto> cargarListadTiposDeBultos() {
        ObservableList<ModeloTipoDeBulto> listadoDePaises = FXCollections.observableArrayList();
        ConexionBD conexion;
        try {
            conexion = new ConexionBD();
            //SELECT cod_tipo_bulto, nombre_bulto, imagen FROM TIPO_DE_BULTO;
            String consulta = "SELECT cod_tipo_bulto, nombre_bulto, imagen_bulto FROM TIPO_DE_BULTO";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int cod_tipo_bulto = rs.getInt("cod_tipo_bulto");
                String nombre_bulto = rs.getString("nombre_bulto");
                Blob imagen_bulto = rs.getBlob("imagen_bulto");


                ModeloTipoDeBulto mp = new ModeloTipoDeBulto(cod_tipo_bulto, nombre_bulto, imagen_bulto);
                listadoDePaises.add(mp);

            }
            rs.close();
            conexion.CloseConexion();
        } catch (
                SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de paises");
            alertaError.mostrarError(e.getMessage());
        }
        return listadoDePaises;
    }

    /**
     * Elimina un Tipo De BUlt en función de un objeto de Tipo de bulto
     *
     * @param modeloTipoDeBultoAEliminar el tipo de bulto a eliminar
     * @return a boolean
     */
    public static boolean eliminarTipoDeBulto(ModeloTipoDeBulto modeloTipoDeBultoAEliminar) {
        ConexionBD conexion;
        PreparedStatement pstmt;
        try {
            conexion = new ConexionBD();
            //DELETE FROM LOGINS WHERE (user = ?) and (pass = ?) and (rol = ?);
            String consulta = "DELETE FROM TIPO_DE_BULTO WHERE (cod_tipo_bulto = ?)";
            pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setInt(1, modeloTipoDeBultoAEliminar.getCod_tipo_bulto());
            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();
            conexion.CloseConexion();

            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido borrar ese registro");
            alertaError.mostrarError(e.getMessage());
            return false;
        }
    }

    /**
     * Crea una nueva entrada en la tabla de tipos de bultos
     *
     * @param nuevomodeloTipoDeBulto el nuevo bulto a crear
     * @return a boolean
     */
    public static boolean nuevoTipoDeBulto(ModeloTipoDeBulto nuevomodeloTipoDeBulto) {

        try {
            ConexionBD conexion;
            PreparedStatement pstmt;
            conexion = new ConexionBD();

            String consulta = "INSERT INTO  TIPO_DE_BULTO (cod_tipo_bulto, nombre_bulto, imagen_bulto) " +
                    "VALUES (?,?,?)";
            pstmt = conexion.getConexion()
                    .prepareStatement(consulta);

            pstmt.setInt(1, nuevomodeloTipoDeBulto.getCod_tipo_bulto());
            pstmt.setString(2, nuevomodeloTipoDeBulto.getNombre_bulto());
            pstmt.setBlob(3, nuevomodeloTipoDeBulto.getImagen_bulto());


            int filasAfectadas = pstmt.executeUpdate();
            // if (pstmt != null)
            pstmt.close();
            // if (conexion != null)
            conexion.CloseConexion();

            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de Tipos de Bulto");
            logger.error("No he podido cargar el listado de Tipos de Bulto");
            alertaError.mostrarError(e.getMessage());
            return false;

        }
    }

    /**
     * Permite cambiar una entrada en la lista de tipos de bultos
     *
     * @param modeloAmodificar                 a {@link es.israeldelamo.transportes.modelos.ModeloTipoDeBulto} object
     * @param datosNuevosTipoDeBultoAModificar a {@link es.israeldelamo.transportes.modelos.ModeloTipoDeBulto} object
     * @return a boolean
     */
    public static boolean modificarTipoDeBulto(ModeloTipoDeBulto modeloAmodificar, ModeloTipoDeBulto datosNuevosTipoDeBultoAModificar) {
        ConexionBD conexion;
        PreparedStatement pstmt;

        try {
            conexion = new ConexionBD();

            String consulta = "UPDATE TIPO_DE_BULTO SET nombre_bulto=?, imagen_bulto=? WHERE (cod_tipo_bulto=?)";
            pstmt = conexion.getConexion().prepareStatement(consulta);

            pstmt.setString(1, datosNuevosTipoDeBultoAModificar.getNombre_bulto());
            pstmt.setBlob(2, datosNuevosTipoDeBultoAModificar.getImagen_bulto());
            pstmt.setInt(3, modeloAmodificar.getCod_tipo_bulto());


            int filasAfectadas = pstmt.executeUpdate();


            pstmt.close();
            conexion.CloseConexion();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de tipos de bultos");
            logger.error("No he podido cargar el listado de tipos de bultos");
            alertaError.mostrarError(e.getMessage());
            return false;

        }

    }

    /**
     * Devuelve el tipo de bulto concreto a partir de un código de busqueda
     *
     * @param codCargaABuscar del bulto del tipo de bulto a buscar
     * @return el objeto ModeloTipoBulto completo
     */
    public static ModeloTipoDeBulto devuelvemeElTipoDeBultoCompletoPorCodigo(int codCargaABuscar) {

        ConexionBD conexion;

        try {
            conexion = new ConexionBD();
            //DELETE FROM LOGINS WHERE (user = ?) and (pass = ?) and (rol = ?);
            String consulta = "SELECT * FROM TIPO_DE_BULTO WHERE (cod_tipo_bulto = ?)";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);

            pstmt.setInt(1, codCargaABuscar);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int codTipoBulto = rs.getInt("cod_tipo_bulto");
                String nombreTipoBulto = rs.getString("nombre_bulto");
                pstmt.close();
                conexion.CloseConexion();
                ModeloTipoDeBulto modeloTipoDeBulto = new ModeloTipoDeBulto(codTipoBulto, nombreTipoBulto, null);
                return modeloTipoDeBulto;
            }
            rs.close();
            conexion.CloseConexion();
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido borrar ese registro");
            logger.error("No he podido borrar ese registro");
            alertaError.mostrarError(e.getMessage());

        }
        return null;
    }

}
