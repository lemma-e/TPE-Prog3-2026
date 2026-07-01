package algorithm;

import java.util.ArrayList;
import java.util.List;
import model.Camion;
import model.Paquete;
import model.Solucion;
public class Backtracking {
    private List<Camion> camiones;
    private List<Paquete> paquetes;

    private Solucion mejorSolucion;

    private int mejorPesoNoAsignado;
    private int estadosGenerados;

    public Backtracking(List<Camion> camiones, List<Paquete> paquetes) {
        this.camiones = camiones;
        this.paquetes = paquetes;
        this.mejorSolucion = null;
        this.mejorPesoNoAsignado = Integer.MAX_VALUE;
        this.estadosGenerados = 0;
    }

    /*
    * estructuras necesarias para comenzar el backtracking.
    * Se "limpia" la carga de los camiones para iniciar desde cero
    * Se crea la lista de paquetes no asignados
    * Luego se llama al método recursivo desde el primer paquete
    * Devuelve la mejor solución encontrada
    */
    /*
     * Raiz: no hay paquetes asignados
     * Estados: cada estado es una asignacion parcial de paquetes a camiones
     * Niveles: cada nivel representa la decision sobre un paquete
     * Hijos: Asignar el paquete actual a cada camion posible
     * Hoja: una hoja es solucion si ya se decidio sobre todos los paquetes
     * Solucion: asignacion completa de paquetes a camiones
     * Objetivo: minimizar el peso total de paquetes no asignados
     * Podas:
     * - !NO asignar si se supera la capacidad del camion
     * - !NO asignar alimentos a un camion no refrigerado
     * - Podar si el peso no asignado ya no mejora la mejor solucion
    */
    public Solucion resolver() {
        limpiarCargaCamiones();
        List<Paquete> paquetesNoAsignadosActual = new ArrayList<>();
        back(0, paquetesNoAsignadosActual, 0);
        if (this.mejorSolucion != null) {
            this.mejorSolucion.setEstadosGenerados(this.estadosGenerados);
        }
        limpiarCargaCamiones();
        return this.mejorSolucion;
    }

    private void back(int indicePaquete, List<Paquete> paquetesNoAsignadosActual, int pesoNoAsignadoActual) {

        //cada llamada al back representa un estado generado
        this.estadosGenerados++;

        //si ya decidi sobre los paquetes tengo una solucion completa
        if (esSolucion(indicePaquete)) {
            operarSolucion(paquetesNoAsignadosActual, pesoNoAsignadoActual);
        } else {
            //Agarro el paquete del nivel actual del arbol
            Paquete paqueteActual = this.paquetes.get(indicePaquete);
            int nroHijo = 0;
            while (nroHijo < this.camiones.size()) {
                Camion camionActual = this.camiones.get(nroHijo);
                /*
                *Antes de generar el nuevo estado verifico si el paquete puede
                *asignarse al camión
                *Esto poda ramas que incumplen capacidad o refrigeración
                */
                if (camionActual.puedeCargar(paqueteActual) && !hayPoda(pesoNoAsignadoActual)) {
                camionActual.cargarPaquete(paqueteActual);
                back(indicePaquete + 1, paquetesNoAsignadosActual, pesoNoAsignadoActual);
                camionActual.quitarUltimoPaquete();
                }

                nroHijo++;
            }
                /*
            * Opcion de no asignar el paquete actual
            * Antes de generar el nuevo estado:
            * calcular el peso no asignado y verifico si esa rama mejora
            */
            int nuevoPesoNoAsignado = pesoNoAsignadoActual + paqueteActual.getPeso();
            if (!hayPoda(nuevoPesoNoAsignado)) {
                agregarANoAsignados(paqueteActual, paquetesNoAsignadosActual);
                back(indicePaquete + 1, paquetesNoAsignadosActual, nuevoPesoNoAsignado);
                quitarDeNoAsignados(paquetesNoAsignadosActual);
            }
        }
    }


    private List<List<Paquete>> obtenerAsignacionActual() {
        List<List<Paquete>> asignacionActual = new ArrayList<>();
        int i = 0;
        while (i < this.camiones.size()) {
            Camion camion = this.camiones.get(i);
            asignacionActual.add(new ArrayList<>(camion.getPaquetesAsignados()));
            i++;
        }
        return asignacionActual;
    }

    private void limpiarCargaCamiones() {
        int i = 0;
        while (i < this.camiones.size()) {
            this.camiones.get(i).limpiarCarga();
            i++;
        }
    }

    /*
    * Indica si se llegó a una solución completa
    * Como cada nivel del árbol representa la decisión sobre un paquete
    * es una solución cuando el índice alcanza la cantidad total de paquetes
    */
    private boolean esSolucion(int indicePaquete){
        return indicePaquete == this.paquetes.size();
    }

    /*
    * el objetivo es minimizar el peso no asignado, si el peso no asignado
    * actual ya es mayor o igual que el mejor peso encontrado hasta el momento
    * entonces esta rama no puede mejorar la mejor solución
    */
    private boolean hayPoda(int pesoNoAsignadoActual){
        return pesoNoAsignadoActual >= this.mejorPesoNoAsignado;
    }
    
    /*
    * Agrega un paquete a la lista de paquetes no asignados
    * cuando en una rama del árbol se decide no cargar el paquete en ningún camión
    * el paquete forma parte de la solución como no asignado
    */
    private void agregarANoAsignados(Paquete paquete, List<Paquete> paquetesNoAsignadosActual){
        paquetesNoAsignadosActual.add(paquete);
    }

    /*
    * Quita un paquete de la lista de paquetes no asignados
    * Para volver al estado anterior y seguir probando otras ramas
    */
    private void quitarDeNoAsignados(List<Paquete> paquetesNoAsignadosActual){
        paquetesNoAsignadosActual.remove(paquetesNoAsignadosActual.size() - 1);
    }

    /*
    * Si el peso no asignado de la solución actual es menor que el mejor peso
    * encontrado hasta el momento entonces se guarda esta solución como la mejor
    */
    private void operarSolucion(List<Paquete> paquetesNoAsignadosActual, int pesoNoAsignadoActual) {
        if (pesoNoAsignadoActual < this.mejorPesoNoAsignado) {
            this.mejorPesoNoAsignado = pesoNoAsignadoActual;
            this.mejorSolucion = new Solucion(
                    this.camiones,
                    obtenerAsignacionActual(),
                    paquetesNoAsignadosActual,
                    pesoNoAsignadoActual,
                    this.estadosGenerados
            );
        }
    }
}
