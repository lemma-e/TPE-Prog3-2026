package service;
import algorithm.Backtracking;
import algorithm.Greedy;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import model.Camion;
import model.Paquete;
import model.Solucion;

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
    //estructura servicio 3
    private HashMap<Integer, List<Paquete>> paquetesPorUrgencia;


    /*
    * Expresar la complejidad temporal del constructor.
    * O(P+C), P=CANTIDAD DE PAQUETES, C=CANTIDAD DE CAMIONES
    * Se recorren ambos archivos una sola vez.
    */
    public Servicios(String pathCamiones, String pathPaquetes){
        this.camiones = new ArrayList<>();
        this.paquetes = new ArrayList<>();
        this.paquetesConAlimentos = new ArrayList<>();
        this.paquetesSinAlimentos = new ArrayList<>();
        this.paquetesPorCodigo = new HashMap<>();
        this.paquetesPorUrgencia = new HashMap<>();
        this.cargarPaquetes(pathPaquetes);
        this.cargarCamiones(pathCamiones);
    }
    /*
    * Expresar la complejidad temporal del servicio 1.
    * busqueda por hashmap con clave
    * Big O: O(n), donde n es la cantidad de paquetes
    * Promedio: O(1), porque se busca por clave en un hashMap
    */
    public Paquete servicio1(String codigoPaquete) { 
            if (this.paquetesPorCodigo.containsKey(codigoPaquete)) {
            return this.paquetesPorCodigo.get(codigoPaquete);
        }
        return null;
    }
    
    /*
    * Expresar la complejidad temporal del servicio 2.
    * Al retornar una lista pre construida a medida que se cargan los paquetes,
    * se evita recorrer toda la lista
    * Big O: O(1)
    */
    public List<Paquete> servicio2(boolean contieneAlimentos) {
        if (contieneAlimentos) {
            return this.paquetesConAlimentos;
        }
        return this.paquetesSinAlimentos;
    }
    /*
    * Expresar la complejidad temporal del servicio 3.
    * recorrer todos los niveles de urgencia dentro del rango
    * en cada nivel se busca un hashmap
    * Big O: O(N + M) (cantidad de niveles recorridos + cantidad de paquetes encontrados)
    * En peor caso, si se devuelven todos los paquetes: O(P)
    */
    public List<Paquete> servicio3(int urgenciaMinima, int urgenciaMaxima) {
        List<Paquete> resultado = new ArrayList<>();
        
        int nivel = urgenciaMinima;

        while (nivel <= urgenciaMaxima) {
            if (this.paquetesPorUrgencia.containsKey(nivel)) {
                List<Paquete> paquetesNivel = this.paquetesPorUrgencia.get(nivel);
                for (Paquete paquete : paquetesNivel) {
                    resultado.add(paquete);
                }
            }
            nivel++;
        }
        return resultado;
    }

    //estructuras auxiliares
    private void agregarPaquete(Paquete paquete) {
        //servivio 1
        this.paquetes.add(paquete);
        this.paquetesPorCodigo.put(paquete.getCodigoPaquete(), paquete);
        //servicio 2
        if (paquete.isContieneAlimentos()) {
                this.paquetesConAlimentos.add(paquete);
            }else {
                    this.paquetesSinAlimentos.add(paquete);
                }
        // servicio 3
        int nivelUrgencia = paquete.getNivelUrgencia();
        if (!this.paquetesPorUrgencia.containsKey(nivelUrgencia)) {
            this.paquetesPorUrgencia.put(nivelUrgencia, new ArrayList<>());
        }
        this.paquetesPorUrgencia.get(nivelUrgencia).add(paquete);
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

    private void agregarCamion(Camion camion) {
        this.camiones.add(camion);
    }

    private void cargarCamiones(String pathCamiones) {
        try {
            Scanner scan = new Scanner(new File(pathCamiones));
            if (scan.hasNextLine()) {
                scan.nextLine(); // saltea la primera, que es la cantidad
            }
            while (scan.hasNextLine()) {
                String linea = scan.nextLine();
                Camion camion = crearCamion(linea);
                this.agregarCamion(camion);
            }
            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println("archivo camiones no encontrado");
        }
    }

    private Camion crearCamion(String linea) {
        String[] datos = linea.split(";");
        int idCamion = Integer.parseInt(datos[0]);
        String patente = datos[1];
        boolean estaRefrigerado = datos[2].equals("1");
        int capacidad = Integer.parseInt(datos[3]);

        return new Camion(idCamion, patente, estaRefrigerado, capacidad);
    }
    

    //PARTE 2 
//
    //BACKTRACKING
    //
    /*
    * El algoritmo backtracking explora las posibles asignaciones de paquetes, respetando
    * las restricciones de capacidad y refrigeración.
    * C = cantidad de camiones + 1 (cantidad de alternativas por paquete)
    * P = cantidad de paquetes
    * En el peor caso, cada paquete puede asignarse a cualquiera de los camiones
    * o quedar sin asignarse
    * Big O: O(C^P)
    */
    public Solucion backtracking() {
        Backtracking algoritmo = new Backtracking(this.camiones, this.paquetes);
        return algoritmo.resolver();
    }

    //GREEDY
    /*
    * El algoritmo Greedy para asignar paquetes a camiones
    * Selecciona el paquete más pesado pendiente y lo asigna
    * al camión válido que quede con menor espacio libre
    * C = cantidad de camiones
    * P = cantidad de paquetes
    * seleccionarPaquete recorre los candidatos pendientes -> O(P^2)
    * seleccionarCamion recorre los camiones por cada paquete -> O(P*C)
    * Big O: O(P^2 + P*C)
    */
    public Solucion greedy() {
        Greedy algoritmo = new Greedy(this.camiones, this.paquetes);
        return algoritmo.resolver();
    }
}
