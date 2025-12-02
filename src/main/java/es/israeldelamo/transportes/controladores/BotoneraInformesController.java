package es.israeldelamo.transportes.controladores;

import es.israeldelamo.transportes.utilidades.LanzadorDeInformes;
import javafx.fxml.FXML;

/**
 * Una pantallita de 3 por 3 con los diferentes informes
 */
public class BotoneraInformesController {

    /**
     * Lanza un Jasper Reports con un listado de todos los registros de la tabla Login
     */
    @FXML
    void crearInformeDeUltimos20CargasCreadas() {
        LanzadorDeInformes.lanzadorDeInformes("20UltimasCargasCreadas");

    }


    /**
     * Lanza un Jasper Reports con un listado de todos los registros de la tabla Login
     */
    @FXML
    void crearInformeDeLogins() {
        LanzadorDeInformes.lanzadorDeInformes("Logins");
    }

    /**
     * Lanza un Jasper Reports con un listado de todas las cargas por país de origen
     */
    @FXML
    void crearInformeDeCargasPorPaisOrigen() {
        LanzadorDeInformes.lanzadorDeInformes("CargasPorOrigen");
    }


    /**
     * Lanza un Jasper Reports con un listado de todos las cargas por país de destino
     */
    @FXML
    void crearInformeDeCargasPorPaisDestino() {
        LanzadorDeInformes.lanzadorDeInformes("CargasPorDestino");
    }

    /**
     * Lanza un Jasper Reports con el listado de todos los tipos de carnet
     */

    @FXML
    void crearInformeEnviosDetalle() {
        LanzadorDeInformes.lanzadorDeInformes("InformeEnviosDetalle");
    }


    /**
     * Lanza un JasperReports con un listado de todos las empresas de la base de datos
     */
    @FXML
    void crearInformeDeEmpresas() {
        LanzadorDeInformes.lanzadorDeInformes("Empresas");
    }


    /**
     * Lanza un JasperReports con un listado de todos los conductores y sus datos básicos
     */
    @FXML
    void crearInformeDeConductor() {
        LanzadorDeInformes.lanzadorDeInformes("Conductores");
    }

}
