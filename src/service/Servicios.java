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

        //hardcode borrar
        Paquete paquete1 = new Paquete(1, "A000", 10, true, 10);
        this.agregarPaquete(paquete1);
    }
    /*
    * Expresar la complejidad temporal del servicio 1.
    * busqueda por hashmap con clave
    * Big O: O(n)
    * Promedio: O(1)
    */
    public Paquete servicio1(String codigoPaquete) { 
            if (this.paquetesPorCodigo.containsKey(codigoPaquete)) {
            return this.paquetesPorCodigo.get(codigoPaquete);
        }
        return null;
    }
    private void agregarPaquete(Paquete paquete) {
        this.paquetes.add(paquete);
        this.paquetesPorCodigo.put(paquete.getCodigoPaquete(), paquete);
    }  
    /*
    * Expresar la complejidad temporal del servicio 2.
    */
    public List<Paquete> servicio2(boolean contieneAlimentos) {return null;}
    /*
    * Expresar la complejidad temporal del servicio 3.
    */
    public List<Paquete> servicio3(int urgenciaMinima, int urgenciaMaxima) {return null;}
}
