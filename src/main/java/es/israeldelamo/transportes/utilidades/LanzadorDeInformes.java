package es.israeldelamo.transportes.utilidades;

import es.israeldelamo.transportes.Iniciografico;
import es.israeldelamo.transportes.bbdd.ConexionBD;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LanzadorDeInformes {

    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(LanzadorDeInformes.class);


    /**
     * Lanza un Jasper Reports a partir de su nombre sin extensión y teniendo alojado en resources>informes
     * arroja errores de tipo Alert según el try de IO, SQL o Jasper
     *
     * @param elInformeALanzar el nombre del informe a lanzar pero sin extensión y ubicado en la carpeta informes de resources
     */

    public static void lanzadorDeInformes(String elInformeALanzar) {
        try {
            //abrimos la base de datos
            ConexionBD conexion = new ConexionBD();
            InputStream reportStream;
            reportStream = Objects.requireNonNull(Iniciografico.class.getResource("/es/israeldelamo/transportes/informes/" + elInformeALanzar + ".jasper")).openStream();
            if (reportStream == null) {
                logger.error("El archivo no esta ahí, a lo mejor se ha borrado");
            } else {
                logger.info("El archivo se ha encontrado, lo lanzo en consecuencia");
            }
            //Jasper la suele liar con la ruta de las imágenes, se la pasaremos a mano
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("IMAGE_PATH", Objects.requireNonNull(Iniciografico.class.getResource("/es/israeldelamo/transportes/imagenes/")).toString());


            JasperReport report = (JasperReport) JRLoader.loadObject(reportStream);
            JasperPrint jprint = JasperFillManager.fillReport(report, parametros, conexion.getConexion());
            //Preparamos un visor, no intentaremos usar el salvar a PDF; para eso el SO ya nos da las impresoras A PDF
            JasperViewer viewer = new JasperViewer(jprint, false);
            viewer.setZoomRatio(0.75f);
            viewer.setFitPageZoomRatio();
            //lanzamos la visión
            viewer.setVisible(true);
            //cerramos conexión base de datos
            conexion.CloseConexion();

        } catch (JRException e) {
            Alertas alerta = new Alertas();
            alerta.mostrarError("Ha ocurrido un error al cargar el informe  por parte de Jasper");
            logger.error("Ha ocurrido un error al cargar el informe por parte de Jasper", e);
        } catch (IOException e) {
            Alertas alerta = new Alertas();
            alerta.mostrarError("Ha ocurrido un error al cargar el informe al abrir el archivo");
            logger.error("Ha ocurrido un error al cargar el informe al abrir el archivo", e);
        } catch (SQLException e) {
            Alertas alerta = new Alertas();
            alerta.mostrarError("Ha ocurrido un error al cargar el informe al abrir la base de datos");
            logger.error("Ha ocurrido un error al cargar el informe al abrir la base de datos", e);
        }
    }


    /**
     * Lanza un Jasper Reports a partir de su nombre sin extensión y teniendo alojado en resources>informes
     * arroja errores de tipo Alert según el try de IO, SQL o Jasper
     * Este procedimiento permite que se le pase un parámetro como criterio de filtro
     *
     * @param elInformeALanzar  el nombre del informe a lanzar pero sin extensión y ubicado en la carpeta informes de resources
     * @param parametroDeFiltro el codigo extra si es que el informe lo usa para hacer el filtrado
     */

    public static void lanzadorDeInformes(String elInformeALanzar, String parametroDeFiltro) {
        try {
            //abrimos la base de datos
            ConexionBD conexion = new ConexionBD();
            InputStream reportStream;
            reportStream = Objects.requireNonNull(Iniciografico.class.getResource("/es/israeldelamo/transportes/informes/" + elInformeALanzar + ".jasper")).openStream();
            if (reportStream == null) {
                logger.error("El archivo no esta ahí, tal vez se haya borrado");
            } else {
                logger.info("El archivo se ha encontrado, lo lanzo");
            }
            //Jasper la suele liar con la ruta de las imágenes, se la pasaremos a mano
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("IMAGE_PATH", Objects.requireNonNull(Iniciografico.class.getResource("/es/israeldelamo/transportes/imagenes/")).toString());
            parametros.put("CRITERIOFILTRO", parametroDeFiltro);


            JasperReport report = (JasperReport) JRLoader.loadObject(reportStream);
            JasperPrint jprint = JasperFillManager.fillReport(report, parametros, conexion.getConexion());
            //Preparamos un visor, no intentaremos usar el salvar a PDF; para eso el SO ya nos da las impresoras A PDF
            JasperViewer viewer = new JasperViewer(jprint, false);
            viewer.setZoomRatio(0.50f);
            viewer.setFitPageZoomRatio();
            //lanzamos la visión
            viewer.setVisible(true);
            //cerramos conexión base de datos
            conexion.CloseConexion();

        } catch (JRException e) {
            Alertas alerta = new Alertas();
            alerta.mostrarError("Ha ocurrido un error al cargar el informe por parte de Jasper");
            logger.error("Ha ocurrido un error al cargar el informe por parte de Jasper", e);
        } catch (IOException e) {
            Alertas alerta = new Alertas();
            alerta.mostrarError("Ha ocurrido un error al cargar el informe al abrir el archivo");
            logger.error("Ha ocurrido un error al cargar el informe al abrir el archivo", e);
        } catch (SQLException e) {
            Alertas alerta = new Alertas();
            alerta.mostrarError("Ha ocurrido un error al cargar el informe al abrir la base de datos");
            logger.error("Ha ocurrido un error al cargar el informe al abrir la base de datos", e);
        }
    }

/*
    public static void main(String[] args) {


        LocalDate inicio = LocalDate.of(2000, 1, 1); // 1 de enero de 2000
        System.out.println(inicio);
        LocalDate diafinal = LocalDate.of(2024, 3, 3); // 1 de enero de 2000
        System.out.println(diafinal);
        lanzadorDeInformes("EnvioPorFechas",inicio.toString(),diafinal.toString() );
    }
*/

    /**
     * Lanza un Jasper Reports a partir de su nombre sin extensión y teniendo alojado en resources>informes
     * arroja errores de tipo Alert según el try de IO, SQL o Jasper
     * Este procedimiento permite que se le pase un parámetro como criterio de filtro
     *
     * @param elInformeALanzar el nombre del informe a lanzar pero sin extensión y ubicado en la carpeta informes de resources
     * @param fechaInicio      primera fecha como parámetro para determinados informes
     * @param fechaFinal       final fecha como parámetro para determinados informes
     */

    public static void lanzadorDeInformes(String elInformeALanzar, String fechaInicio, String fechaFinal) {
        try {
            //abrimos la base de datos
            ConexionBD conexion = new ConexionBD();
            InputStream reportStream;
            reportStream = Objects.requireNonNull(Iniciografico.class.getResource("/es/israeldelamo/transportes/informes/" + elInformeALanzar + ".jasper")).openStream();
            if (reportStream == null) {
                logger.error("El archivo no esta ahí");
            } else {
                logger.info("El archivo se ha encontrado");
            }
            //Jasper la suele liar con la ruta de las imágenes, se la pasaremos a mano
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("IMAGE_PATH", Objects.requireNonNull(Iniciografico.class.getResource("/es/israeldelamo/transportes/imagenes/")).toString());
            parametros.put("FECHAINICIO", fechaInicio);
            parametros.put("FECHAFINAL", fechaFinal);


            JasperReport report = (JasperReport) JRLoader.loadObject(reportStream);
            JasperPrint jprint = JasperFillManager.fillReport(report, parametros, conexion.getConexion());
            //Preparamos un visor, no intentaremos usar el salvar a PDF; para eso el SO ya nos da las impresoras A PDF
            JasperViewer viewer = new JasperViewer(jprint, false);
            viewer.setZoomRatio(0.50f);
            viewer.setFitPageZoomRatio();
            //lanzamos la visión
            viewer.setVisible(true);
            //cerramos conexión base de datos
            conexion.CloseConexion();

        } catch (JRException e) {
            Alertas alerta = new Alertas();
            alerta.mostrarError("Ha ocurrido un error al cargar el informe por parte de Jasper");
            logger.error("Ha ocurrido un error al cargar el informe  por parte de Jasper", e);
        } catch (IOException e) {
            Alertas alerta = new Alertas();
            alerta.mostrarError("Ha ocurrido un error al cargar el informe al abrir el archivo");
            logger.error("Ha ocurrido un error al cargar el informe al abrir el archivo", e);
        } catch (SQLException e) {
            Alertas alerta = new Alertas();
            alerta.mostrarError("Ha ocurrido un error al cargar el informe al abrir la base de datos");
            logger.error("Ha ocurrido un error al cargar el informe al abrir la base de datos", e);
        }
    }


}
