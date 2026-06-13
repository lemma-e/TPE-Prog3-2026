package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Camion;
import model.Paquete;

public class Servicios {
    //Completar con las estructuras y métodos privados que se requieran.
    //estructuras clases
    private List<Camion> camiones;
    private List<Paquete> paquetes;
    //estructura servicio 1
    private HashMap<String, Paquete> paquetesPorCodigo;


    /*
    * Expresar la complejidad temporal del constructor.
    *  ?????????
    */
    public Servicios(String pathCamiones, String pathPaquetes){
        this.camiones = new ArrayList<>();
        this.paquetes = new ArrayList<>();
        this.paquetesPorCodigo = new HashMap<>();
    }
    /*
    * Expresar la complejidad temporal del servicio 1.
    */
    public Paquete servicio1(String codigoPaquete) { }
    /*
    * Expresar la complejidad temporal del servicio 2.
    */
    public List<Paquete> servicio2(boolean contieneAlimentos) { }
    /*
    * Expresar la complejidad temporal del servicio 3.
    */
    public List<Paquete> servicio3(int urgenciaMinima, int urgenciaMaxima) { }
}
