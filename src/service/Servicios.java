package service;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import model.Camion;
import model.Paquete;

public class Servicios {
    //Completar con las estructuras y métodos privados que se requieran.
    //estructuras clases
    private List<Camion> camiones;
    private List<Paquete> paquetes;
    //estructura servicio 1
    private HashMap<String, Paquete> paquetesPorCodigo;
    //estructuras servicio 2
    private List<Paquete> paquetesConAlimentos;
    private List<Paquete> paquetesSinAlimentos;


    /*
    * Expresar la complejidad temporal del constructor.
    *  O(P), donde P = cantidad de paquetes
    */
    public Servicios(String pathCamiones, String pathPaquetes){
        this.camiones = new ArrayList<>();
        this.paquetes = new ArrayList<>();
        this.paquetesConAlimentos = new ArrayList<>();
        this.paquetesSinAlimentos = new ArrayList<>();
        this.paquetesPorCodigo = new HashMap<>();
        this.cargarPaquetes(pathPaquetes);
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
    private void cargarPaquetes(String pathPaquetes)  {
    try {
            Scanner scan = new Scanner(new File(pathPaquetes));
            if (scan.hasNextLine()) {
                scan.nextLine(); // saltea la primera, que es la cantidad
            }
            while (scan.hasNextLine()) {
                String linea = scan.nextLine();
                Paquete paquete = crearPaquete(linea);
                this.agregarPaquete(paquete);
            }
            scan.close();
        }catch (FileNotFoundException e) {
            System.out.println("archivo paquetes no encontrado");
        }
    } 
    private Paquete crearPaquete(String linea) {
        String[] datos = linea.split(";");
        int idPaquete = Integer.parseInt(datos[0]);
        String codigoPaquete = datos[1];
        int peso = Integer.parseInt(datos[2]);
        boolean contieneAlimentos = datos[3].equals("1");
        int nivelUrgencia = Integer.parseInt(datos[4]);

        return new Paquete(idPaquete, codigoPaquete, peso, contieneAlimentos, nivelUrgencia);
    /*
    ejemplo:
        datos[0] = "1"
        datos[1] = "P001"
        datos[2] = "30"
        datos[3] = "1" (1 true, 0 false)
        datos[4] = "80"
    */
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
