package es.israeldelamo.transportes.dao;

import es.israeldelamo.transportes.bbdd.ConexionBD;
import es.israeldelamo.transportes.modelos.ModeloCargaYEntrega;
import es.israeldelamo.transportes.utilidades.Alertas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * El Dao para Carga y Entrega
 *
 * @author israel
 * @version $Id: $Id
 */
public class DaoCargaYEntrega {

    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(DaoCargaYEntrega.class);

    /**
     * En función del codigo de carga marcamos como ya_reservado
     *
     * @param codigoDeCargaAMarcar      la entrada de Carga y Entrega a marcar
     * @param codigoPostalComoSeguridad int
     */
    public static void marcarComoPedido(String codigoDeCargaAMarcar, int codigoPostalComoSeguridad) {
        ConexionBD conexion;
        PreparedStatement pstmt;
        int filasAfectadas = 0;
        try {
            conexion = new ConexionBD();
            String consulta = "UPDATE `transportes`.`CARGA_Y_ENTREGA` SET `ya_reservado` = '1', cp_destino = ? WHERE (`cod_carga` = ?)";


            pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setInt(1, codigoPostalComoSeguridad);
            pstmt.setString(2, codigoDeCargaAMarcar);
            filasAfectadas = pstmt.executeUpdate();
            logger.info("Actualizada cargas y envios");
            pstmt.close();
            conexion.CloseConexion();
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            logger.error(e.getMessage());
            alertaError.mostrarError("No he podido marcar esa  Carga y Entrega. Entradas cambiadas :" + filasAfectadas);
            alertaError.mostrarError(e.getMessage());
        }

    }

    /**
     * Devuelve el código de carga con el número más alto que indicará que esl el último creado
     *
     * @return el número de carga
     */

    public static int buscar_cod_carga_mas_alto() {

        int valorMasAlto = 0;
        ConexionBD conexion;
        try {
            conexion = new ConexionBD();
            String consulta = "SELECT max(cod_carga) FROM transportes.CARGA_Y_ENTREGA;";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                valorMasAlto = rs.getInt("max(cod_carga)");
            }
            return valorMasAlto;
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }

    }


    /**
     * Pasamos como si fuera un SQL injection toda la parte de SQL que queramos a partir del WHERE
     *
     * @param cadenaSQLWhere         cadena sql que hace la consulta
     * @param cargarEstandoReservada si es verdadero, cargará en la lista las que estén marcadas como reservadas.
     * @return listado de carga y entrega para cargar en un tableview
     */
    public static ObservableList<ModeloCargaYEntrega> cargarListadoEntregaYCargaSQL(String cadenaSQLWhere, boolean cargarEstandoReservada) {
        ConexionBD conexion;
        ObservableList<ModeloCargaYEntrega> listadoCargasYEntregas = FXCollections.observableArrayList();

        try {
            conexion = new ConexionBD();

            String consulta = "select cod_carga,franja_horario_recogida,franja_horario_entrega,dimension_l,dimension_h,dimension_a,peso_unitario,manipulacion_carga_bool,remontable,es_naturaleza_peligrosa,ambito_de_carga,doble_conductor,carga_completa_o_grupaje,necesita_rampa,cod_naturaleza_peligrosa,localidad_origen,provincia_origen,pais_origen,cp_origen,localidad_destino,pais_destino,provincia_destino,cp_destino,instrucciones,reembolso_cantidad,con_vuelta,precio,seguro,es_adr,ya_reservado,es_atp,cantidad_bultos,fecha_creaccion,empresa,tipo_bulto from CARGA_Y_ENTREGA where" + cadenaSQLWhere;

            Statement stmt = conexion.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(consulta);


            // ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int cod_carga = rs.getInt("cod_carga");
                Timestamp franja_horario_recogida = rs.getTimestamp("franja_horario_recogida");
                Timestamp franja_horario_entrega = rs.getTimestamp("franja_horario_entrega");
                float dimension_l = rs.getFloat("dimension_l");
                float dimension_a = rs.getFloat("dimension_a");
                float dimension_h = rs.getFloat("dimension_h");
                float peso_unitario = rs.getFloat("peso_unitario");
                boolean manipulacion_carga_bool = rs.getBoolean("manipulacion_carga_bool");
                boolean remontable = rs.getBoolean("remontable");
                Integer tipo_bulto = rs.getInt("tipo_bulto");
                boolean doble_conductor = rs.getBoolean("doble_conductor");
                boolean carga_completa_o_grupaje = rs.getBoolean("carga_completa_o_grupaje");
                boolean necesita_rampa = rs.getBoolean("necesita_rampa");
                float cod_naturaleza_peligrosa = rs.getFloat("cod_naturaleza_peligrosa");
                String localidad_origen = rs.getString("localidad_origen");
                String provincia_origen = rs.getString("provincia_origen");
                String pais_origen = rs.getString("pais_origen");
                int cp_origen = rs.getInt("cp_origen");
                String localidad_destino = rs.getString("localidad_destino");
                String provincia_destino = rs.getString("provincia_destino");
                String pais_destino = rs.getString("pais_destino");
                int cp_destino = rs.getInt("cp_destino");
                String instrucciones = rs.getString("instrucciones");
                String reembolso_cantidad = rs.getString("reembolso_cantidad");
                boolean cod_vuelta = rs.getBoolean("con_vuelta");
                String precio = rs.getString("precio");
                String seguro = rs.getString("seguro");
                boolean es_adr = rs.getBoolean("es_adr");
                boolean es_atp = rs.getBoolean("es_atp");
                boolean ya_reservado = rs.getBoolean("ya_reservado");
                int cantidad_bultos = rs.getInt("cantidad_bultos");
                Timestamp fecha_creaccion = rs.getTimestamp("fecha_creaccion");
                String empresa = rs.getString("empresa");


                if (ya_reservado == cargarEstandoReservada) { //si está marcado como verdadero es que se cargue aún reservada
                    ModeloCargaYEntrega mp = new ModeloCargaYEntrega(
                            cod_carga,
                            franja_horario_recogida,
                            franja_horario_entrega,
                            dimension_l,
                            dimension_a,
                            dimension_h,
                            peso_unitario,
                            manipulacion_carga_bool,
                            remontable,
                            tipo_bulto,
                            doble_conductor,
                            carga_completa_o_grupaje,
                            necesita_rampa,
                            cod_naturaleza_peligrosa,
                            localidad_origen,
                            provincia_origen,
                            pais_origen,
                            cp_origen,
                            localidad_destino,
                            provincia_destino,
                            pais_destino,
                            cp_destino,
                            instrucciones,
                            reembolso_cantidad,
                            cod_vuelta,
                            precio,
                            seguro,
                            es_adr,
                            es_atp,
                            ya_reservado,
                            cantidad_bultos,
                            fecha_creaccion,
                            empresa);
                    listadoCargasYEntregas.add(mp);
                }
            }

            rs.close();
            conexion.CloseConexion();
        } catch (
                SQLException e) {
            Alertas alertaError = new Alertas();

            alertaError.mostrarError("No he podido cargar Carga y Entrega");
            logger.error("No he podido cargar Carga y Entrega en la base de datos");
            alertaError.mostrarError(e.getMessage());

        }
        return listadoCargasYEntregas;
    }


    /**
     * Procedimiento que carga los datos de la tabla Modelo Carga Y Entrega y los devuelve para usarlos en un listado de sistemas de carga
     *
     * @return listado de carga y entrega para cargar en un tableview
     */
    public static ObservableList<ModeloCargaYEntrega> cargarListadoEntregaYCarga() {
        ConexionBD conexion;
        ObservableList<ModeloCargaYEntrega> listadoCargasYEntregas = FXCollections.observableArrayList();

        try {
            conexion = new ConexionBD();

            String consulta = "SELECT * FROM CARGA_Y_ENTREGA";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                int cod_carga = rs.getInt("cod_carga");
                Timestamp franja_horario_recogida = rs.getTimestamp("franja_horario_recogida");
                Timestamp franja_horario_entrega = rs.getTimestamp("franja_horario_entrega");
                Float dimension_l = rs.getFloat("dimension_l");
                Float dimension_a = rs.getFloat("dimension_a");
                Float dimension_h = rs.getFloat("dimension_h");
                Float peso_unitario = rs.getFloat("peso_unitario");
                Boolean manipulacion_carga_bool = rs.getBoolean("manipulacion_carga_bool");
                Boolean remontable = rs.getBoolean("remontable");
                Integer tipo_bulto = rs.getInt("tipo_bulto");
                Boolean doble_conductor = rs.getBoolean("doble_conductor");
                Boolean carga_completa_o_grupaje = rs.getBoolean("carga_completa_o_grupaje");
                Boolean necesita_rampa = rs.getBoolean("necesita_rampa");
                Float cod_naturaleza_peligrosa = rs.getFloat("cod_naturaleza_peligrosa");
                String localidad_origen = rs.getString("localidad_origen");
                String provincia_origen = rs.getString("provincia_origen");
                String pais_origen = rs.getString("pais_origen");
                Integer cp_origen = rs.getInt("cp_origen");
                String localidad_destino = rs.getString("localidad_destino");
                String provincia_destino = rs.getString("provincia_destino");
                String pais_destino = rs.getString("pais_destino");
                Integer cp_destino = rs.getInt("cp_destino");
                String instrucciones = rs.getString("instrucciones");
                String reembolso_cantidad = rs.getString("reembolso_cantidad");
                Boolean con_vuelta = rs.getBoolean("con_vuelta");
                String precio = rs.getString("precio");
                String seguro = rs.getString("seguro");
                Boolean es_adr = rs.getBoolean("es_adr");
                Boolean es_atp = rs.getBoolean("es_atp");
                Boolean ya_reservado = rs.getBoolean("ya_reservado");
                Integer cantidad_bultos = rs.getInt("cantidad_bultos");
                Timestamp fecha_creaccion = rs.getTimestamp("fecha_creaccion");
                String empresa = rs.getString("empresa");

                ModeloCargaYEntrega mp = new ModeloCargaYEntrega(
                        cod_carga,
                        franja_horario_recogida,
                        franja_horario_entrega,
                        dimension_l,
                        dimension_a,
                        dimension_h,
                        peso_unitario,
                        manipulacion_carga_bool,
                        remontable,
                        tipo_bulto,
                        doble_conductor,
                        carga_completa_o_grupaje,
                        necesita_rampa,
                        cod_naturaleza_peligrosa,
                        localidad_origen,
                        provincia_origen,
                        pais_origen,
                        cp_origen,
                        localidad_destino,
                        provincia_destino,
                        pais_destino,
                        cp_destino,
                        instrucciones,
                        reembolso_cantidad,
                        con_vuelta,
                        precio,
                        seguro,
                        es_adr,
                        es_atp,
                        ya_reservado,
                        cantidad_bultos,
                        fecha_creaccion,
                        empresa
                );
                listadoCargasYEntregas.add(mp);

            }
            rs.close();
            conexion.CloseConexion();
        } catch (
                SQLException e) {
            Alertas alertaError = new Alertas();

            alertaError.mostrarError("No he podido cargar Carga y Entrega");
            logger.error("No he podido cargar Carga y Entrega como lista");
            alertaError.mostrarError(e.getMessage());

        }
        return listadoCargasYEntregas;
    }


    /**
     * Procedimiento que carga los datos de la tabla Modelo Carga Y Entrega y los devuelve para usarlos en un
     * listado de sistemas de carga filtrado por empresa
     *
     * @param codigoEmpresaAFiltrar devuelve solo las cargas relativas a esa empresa
     * @return listado de carga y entrega para cargar en un tableview
     */
    public static ObservableList<ModeloCargaYEntrega> cargarListadoEntregaYCargaDeUnaSolaEmpresa(String codigoEmpresaAFiltrar) {
        ConexionBD conexion;
        ObservableList<ModeloCargaYEntrega> listadoCargasYEntregas = FXCollections.observableArrayList();

        try {
            conexion = new ConexionBD();

            String consulta = "SELECT * FROM CARGA_Y_ENTREGA WHERE empresa=?";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            //filtra por esa empresa
            pstmt.setString(1, codigoEmpresaAFiltrar);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                int cod_carga = rs.getInt("cod_carga");
                Timestamp franja_horario_recogida = rs.getTimestamp("franja_horario_recogida");
                Timestamp franja_horario_entrega = rs.getTimestamp("franja_horario_entrega");
                Float dimension_l = rs.getFloat("dimension_l");
                Float dimension_a = rs.getFloat("dimension_a");
                Float dimension_h = rs.getFloat("dimension_h");
                Float peso_unitario = rs.getFloat("peso_unitario");
                Boolean manipulacion_carga_bool = rs.getBoolean("manipulacion_carga_bool");
                Boolean remontable = rs.getBoolean("remontable");
                Integer tipo_bulto = rs.getInt("tipo_bulto");
                Boolean doble_conductor = rs.getBoolean("doble_conductor");
                Boolean carga_completa_o_grupaje = rs.getBoolean("carga_completa_o_grupaje");
                Boolean necesita_rampa = rs.getBoolean("necesita_rampa");
                Float cod_naturaleza_peligrosa = rs.getFloat("cod_naturaleza_peligrosa");
                String localidad_origen = rs.getString("localidad_origen");
                String provincia_origen = rs.getString("provincia_origen");
                String pais_origen = rs.getString("pais_origen");
                Integer cp_origen = rs.getInt("cp_origen");
                String localidad_destino = rs.getString("localidad_destino");
                String provincia_destino = rs.getString("provincia_destino");
                String pais_destino = rs.getString("pais_destino");
                Integer cp_destino = rs.getInt("cp_destino");
                String instrucciones = rs.getString("instrucciones");
                String reembolso_cantidad = rs.getString("reembolso_cantidad");
                Boolean con_vuelta = rs.getBoolean("con_vuelta");
                String precio = rs.getString("precio");
                String seguro = rs.getString("seguro");
                Boolean es_adr = rs.getBoolean("es_adr");
                Boolean es_atp = rs.getBoolean("es_atp");
                Boolean ya_reservado = rs.getBoolean("ya_reservado");
                Integer cantidad_bultos = rs.getInt("cantidad_bultos");
                Timestamp fecha_creaccion = rs.getTimestamp("fecha_creaccion");
                String empresa = rs.getString("empresa");

                ModeloCargaYEntrega mp = new ModeloCargaYEntrega(
                        cod_carga,
                        franja_horario_recogida,
                        franja_horario_entrega,
                        dimension_l,
                        dimension_a,
                        dimension_h,
                        peso_unitario,
                        manipulacion_carga_bool,
                        remontable,
                        tipo_bulto,
                        doble_conductor,
                        carga_completa_o_grupaje,
                        necesita_rampa,
                        cod_naturaleza_peligrosa,
                        localidad_origen,
                        provincia_origen,
                        pais_origen,
                        cp_origen,
                        localidad_destino,
                        provincia_destino,
                        pais_destino,
                        cp_destino,
                        instrucciones,
                        reembolso_cantidad,
                        con_vuelta,
                        precio,
                        seguro,
                        es_adr,
                        es_atp,
                        ya_reservado,
                        cantidad_bultos,
                        fecha_creaccion,
                        empresa
                );
                listadoCargasYEntregas.add(mp);

            }
            logger.info("He cargado  cargas de la empresas{}", listadoCargasYEntregas.size());
            rs.close();
            conexion.CloseConexion();
        } catch (
                SQLException e) {
            Alertas alertaError = new Alertas();

            alertaError.mostrarError("No he podido cargar Carga y Entrega");
            logger.error("No he podido cargar Carga y Entrega");
            alertaError.mostrarError(e.getMessage());

        }
        return listadoCargasYEntregas;
    }


    /**
     * Procedimiento que modifica los datos de un ModeloCargaYEntrega en la BD
     *
     * @param antiguoCodigoModeloCargaYEntrega codigo de carga
     * @param nuevoModeloCargaYEntrega         Nuevos datos del ModeloCargaYEntrega a modificar
     * @return true/false
     */
    public static boolean modificarModeloCargaYEntrega(Integer antiguoCodigoModeloCargaYEntrega, ModeloCargaYEntrega nuevoModeloCargaYEntrega) {
        ConexionBD conexion;
        PreparedStatement pstmt;

        try {
            conexion = new ConexionBD();
            String consulta = "UPDATE CARGA_Y_ENTREGA SET " +
                    "franja_horario_recogida = ?, " +
                    "franja_horario_entrega = ?, " +
                    "dimension_l = ?, " +
                    "dimension_a = ?, " +
                    "dimension_h = ?, " +
                    "peso_unitario = ?, " +
                    "manipulacion_carga_bool = ?, " +
                    "remontable = ?, " +
                    "tipo_bulto = ?, " +
                    "doble_conductor = ?, " +
                    "carga_completa_o_grupaje = ?, " +
                    "necesita_rampa = ?, " +
                    "cod_naturaleza_peligrosa = ?, " +
                    "localidad_origen = ?," +
                    "provincia_origen = ?," +
                    "pais_origen = ?, " +
                    "cp_origen = ?, " +
                    "localidad_destino = ?," +
                    "provincia_destino = ?," +
                    "pais_destino = ?, " +
                    "cp_destino = ?," +
                    "instrucciones = ?, " +
                    "reembolso_cantidad = ?," +
                    "con_vuelta = ?," +
                    "precio = ?," +
                    "seguro = ?, " +
                    "es_adr = ?, " +
                    "es_atp = ?, " +
                    "cantidad_bultos = ?, " +
                    "fecha_creaccion = ?, " +
                    "empresa = ? " +
                    "WHERE (cod_carga = ?)";
            pstmt = conexion.getConexion().prepareStatement(consulta);

            pstmt.setTimestamp(1, nuevoModeloCargaYEntrega.getFranja_horario_recogida());
            pstmt.setTimestamp(2, nuevoModeloCargaYEntrega.getFranja_horario_entrega());
            pstmt.setFloat(3, nuevoModeloCargaYEntrega.getDimension_l());
            pstmt.setFloat(4, nuevoModeloCargaYEntrega.getDimension_a());
            pstmt.setFloat(5, nuevoModeloCargaYEntrega.getDimension_h());
            pstmt.setFloat(6, nuevoModeloCargaYEntrega.getPeso_unitario());
            pstmt.setBoolean(7, nuevoModeloCargaYEntrega.getManipulacion_carga_bool());
            pstmt.setBoolean(8, nuevoModeloCargaYEntrega.getRemontable());
            pstmt.setInt(9, nuevoModeloCargaYEntrega.getTipo_bulto());
            pstmt.setBoolean(10, nuevoModeloCargaYEntrega.getDoble_conductor());
            pstmt.setBoolean(11, nuevoModeloCargaYEntrega.getCarga_completa_o_grupaje());
            pstmt.setBoolean(12, nuevoModeloCargaYEntrega.getNecesita_rampa());
            pstmt.setFloat(13, nuevoModeloCargaYEntrega.getCod_naturaleza_peligrosa());
            pstmt.setString(14, nuevoModeloCargaYEntrega.getLocalidad_origen());
            pstmt.setString(15, nuevoModeloCargaYEntrega.getProvincia_origen());
            pstmt.setString(16, nuevoModeloCargaYEntrega.getPais_origen());
            pstmt.setInt(17, nuevoModeloCargaYEntrega.getCp_origen());
            pstmt.setString(18, nuevoModeloCargaYEntrega.getLocalidad_destino());
            pstmt.setString(19, nuevoModeloCargaYEntrega.getProvincia_destino());
            pstmt.setString(20, nuevoModeloCargaYEntrega.getPais_destino());
            pstmt.setInt(21, nuevoModeloCargaYEntrega.getCp_destino());
            pstmt.setString(22, nuevoModeloCargaYEntrega.getInstrucciones());
            pstmt.setString(23, nuevoModeloCargaYEntrega.getReembolso_cantidad());
            pstmt.setBoolean(24, nuevoModeloCargaYEntrega.getCon_vuelta());
            pstmt.setString(25, nuevoModeloCargaYEntrega.getPrecio());
            pstmt.setString(26, nuevoModeloCargaYEntrega.getSeguro());
            pstmt.setBoolean(27, nuevoModeloCargaYEntrega.getEs_adr());
            pstmt.setBoolean(28, nuevoModeloCargaYEntrega.getEs_atp());
            pstmt.setInt(29, nuevoModeloCargaYEntrega.getCantidad_bultos());
            pstmt.setTimestamp(30, nuevoModeloCargaYEntrega.getFecha_creaccion());
            pstmt.setString(31, nuevoModeloCargaYEntrega.getEmpresa());


            pstmt.setInt(32, antiguoCodigoModeloCargaYEntrega);


            int filasAfectadas = pstmt.executeUpdate();

            logger.info("Actualizada ModeloCargaYEntrega");

            pstmt.close();

            conexion.CloseConexion();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de ModeloCargaYEntrega");
            logger.error("No he podido cargar el listado de ModeloCargaYEntrega en la base de datos");
            alertaError.mostrarError(e.getMessage());

            return false;

        }

    }


    /**
     * Procedimiento que CREA un nuevo una entrada en Carga Y entrega
     *
     * @param nuevoModeloCargaYEntrega Instancia del nuevo sistema de carga de entrega y
     * @return true/false
     */
    public static boolean nuevoModeloCargaYEntrega(ModeloCargaYEntrega nuevoModeloCargaYEntrega) {
        ConexionBD conexion;
        PreparedStatement pstmt;

        try {
            conexion = new ConexionBD();


            String consulta = "INSERT INTO CARGA_Y_ENTREGA (" +
                    "cod_carga," +
                    "franja_horario_recogida, " +
                    "franja_horario_entrega, " +
                    "dimension_l, " +
                    "dimension_a, " +
                    "dimension_h, " +
                    "peso_unitario, " +
                    "manipulacion_carga_bool, " +
                    "remontable , " +
                    "tipo_bulto, " +
                    "doble_conductor, " +
                    "carga_completa_o_grupaje, " +
                    "necesita_rampa , " +
                    "cod_naturaleza_peligrosa , " +
                    "localidad_origen ," +
                    "provincia_origen ," +
                    "pais_origen , " +
                    "cp_origen , " +
                    "localidad_destino ," +
                    "provincia_destino ," +
                    "pais_destino , " +
                    "cp_destino ," +
                    "instrucciones , " +
                    "reembolso_cantidad ," +
                    "con_vuelta ," +
                    "precio, " +
                    "seguro , " +
                    "es_adr , " +
                    "es_atp,  " +
                    "cantidad_bultos, " +
                    "empresa)" +
                    " VALUES (" +
                    "?,?,?,?,?,?," +
                    "?,?,?,?,?,?," +
                    "?,?,?,?,?,?," +
                    "?,?,?,?,?,?," +
                    "?,?,?,?,?,?," +
                    "?)";
            // no metemos la fecha de creacción, lo hará la base de datos sola
            pstmt = conexion.getConexion().prepareStatement(consulta);


            pstmt.setInt(1, nuevoModeloCargaYEntrega.getCod_carga());
            pstmt.setTimestamp(2, nuevoModeloCargaYEntrega.getFranja_horario_recogida());
            pstmt.setTimestamp(3, nuevoModeloCargaYEntrega.getFranja_horario_entrega());
            pstmt.setFloat(4, nuevoModeloCargaYEntrega.getDimension_l());
            pstmt.setFloat(5, nuevoModeloCargaYEntrega.getDimension_a());
            pstmt.setFloat(6, nuevoModeloCargaYEntrega.getDimension_h());
            pstmt.setFloat(7, nuevoModeloCargaYEntrega.getPeso_unitario());
            pstmt.setBoolean(8, nuevoModeloCargaYEntrega.getManipulacion_carga_bool());
            pstmt.setBoolean(9, nuevoModeloCargaYEntrega.getRemontable());
            pstmt.setInt(10, nuevoModeloCargaYEntrega.getTipo_bulto());
            pstmt.setBoolean(11, nuevoModeloCargaYEntrega.getDoble_conductor());
            pstmt.setBoolean(12, nuevoModeloCargaYEntrega.getCarga_completa_o_grupaje());
            pstmt.setBoolean(13, nuevoModeloCargaYEntrega.getNecesita_rampa());
            pstmt.setFloat(14, nuevoModeloCargaYEntrega.getCod_naturaleza_peligrosa());
            pstmt.setString(15, nuevoModeloCargaYEntrega.getLocalidad_origen());
            pstmt.setString(16, nuevoModeloCargaYEntrega.getProvincia_origen());
            pstmt.setString(17, nuevoModeloCargaYEntrega.getPais_origen());
            pstmt.setInt(18, nuevoModeloCargaYEntrega.getCp_origen());
            pstmt.setString(19, nuevoModeloCargaYEntrega.getLocalidad_destino());
            pstmt.setString(20, nuevoModeloCargaYEntrega.getProvincia_destino());
            pstmt.setString(21, nuevoModeloCargaYEntrega.getPais_destino());
            pstmt.setInt(22, nuevoModeloCargaYEntrega.getCp_destino());
            pstmt.setString(23, nuevoModeloCargaYEntrega.getInstrucciones());
            pstmt.setString(24, nuevoModeloCargaYEntrega.getReembolso_cantidad());
            pstmt.setBoolean(25, nuevoModeloCargaYEntrega.getCon_vuelta());
            pstmt.setString(26, nuevoModeloCargaYEntrega.getPrecio());
            pstmt.setString(27, nuevoModeloCargaYEntrega.getSeguro());
            pstmt.setBoolean(28, nuevoModeloCargaYEntrega.getEs_adr());
            pstmt.setBoolean(29, nuevoModeloCargaYEntrega.getEs_atp());
            pstmt.setInt(30, nuevoModeloCargaYEntrega.getCantidad_bultos());

            pstmt.setString(31, nuevoModeloCargaYEntrega.getEmpresa());


            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();

            conexion.CloseConexion();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            //Alertas alertaError = new Alertas();
            // alertaError.mostrarError("No he podido cargar el listado de ModeloCargaYEntrega");
            System.out.println(e.getMessage());
            logger.error("No he podido cargar el listado de ModeloCargaYEntrega");
            // alertaError.mostrarError(e.getMessage());

            return false;

        }


    }

    /**
     * Eliminar modelo de carga y entrega
     *
     * @param modeloSistemaCargaVehiculoAEliminar un modelo de sistema de carga de vehículo que eliminara
     * @return true or falso si ha logrado eliminarlo
     */
    public static boolean eliminarModeloCargaYEntrega(String modeloSistemaCargaVehiculoAEliminar) {
        ConexionBD conexion;
        PreparedStatement pstmt;
        try {
            conexion = new ConexionBD();

            // DELETE FROM `transportes`.`CARGA_Y_ENTREGA` WHERE (`cod_carga` = '8');

            String consulta = "DELETE FROM CARGA_Y_ENTREGA WHERE (cod_carga = ?)";
            pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, modeloSistemaCargaVehiculoAEliminar);
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

/*
    public static void main(String[] args) {

        Timestamp franjaHorarioRecogida = new Timestamp(System.currentTimeMillis());
        Timestamp franjaHorarioEntrega = new Timestamp(System.currentTimeMillis() + 3600000); // 1 hora después





        ModeloCargaYEntrega mp2 = new ModeloCargaYEntrega(
                7777,
                franjaHorarioRecogida,
                franjaHorarioEntrega,
                4f,
                3f,
                3f,
                3f,
                false,
                false,
                1 ,
                false,
                true,
                false ,
                4.01f,
                "Vitoria",
                "Álava",
                "España",
                1005,
                "Vitoria",
                "Álava",
                "España",
                1004,
                "",
                "43",
                false,
                "23",
                "",
                false,
                true,
                false,
                3,
                franjaHorarioEntrega,
                 "23");

        ModeloCargaYEntrega cargaYEntrega = new ModeloCargaYEntrega(
                6,
                franjaHorarioRecogida,
                franjaHorarioEntrega,
                2.5f,
                1.5f,
                1.0f,
                20.0f,
                true,
                false,
                3,
                true,
                false,
                true,
                1F,
                "Madrid",
                "Madrid",
                "España",
                28001,
                "Barcelona",
                "Barcelona",
                "España",
                8001,
                "Instrucciones especiales",
                "100.0",
                true,
                "1000",
                "300",
                true,
                true,
                false,
                5,
                franjaHorarioEntrega,
                "12"
        );
        modificarModeloCargaYEntrega(7777,mp2);

    }
*/
}
