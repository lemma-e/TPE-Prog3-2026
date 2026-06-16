package algorithm;

import java.util.ArrayList;
import java.util.List;

import model.Camion;
import model.Paquete;

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
    * lista de paquetes asignados por cada camion
    * lista con el peso actual cargado en cada camion
    * lista de paquetes no asignados
    * llama al metodo recursivo back desde el primer paquete
    * devuelve la mejor solución encontrada
    */
    public Solucion resolver() {
        List<List<Paquete>> asignacionActual = new ArrayList<>();
        List<Integer> pesoActualPorCamion = new ArrayList<>();
        int index = 0;
        while (index < this.camiones.size()) {
            asignacionActual.add(new ArrayList<>());
            pesoActualPorCamion.add(0);
            index++;
        }
        List<Paquete> paquetesNoAsignadosActual = new ArrayList<>();
        back(0, asignacionActual, pesoActualPorCamion, paquetesNoAsignadosActual, 0);
        if (this.mejorSolucion != null) {
            this.mejorSolucion.setEstadosGenerados(this.estadosGenerados);
        }
        return this.mejorSolucion;
    }



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

    /*
    1. Genero un estado
    2. Pregunto si hay poda
    3. Pregunto si llegué a una solución
    4. Si no hay solucion, genero hijos
    5. Agrego candidato
    6. Llamo recursivamente
    7. Quito candidato
     */
    private void back(int indicePaquete, List<List<Paquete>> asignacionActual, List<Integer> pesoActualPorCamion, 
                        List<Paquete> paquetesNoAsignadosActual, int pesoNoAsignadoActual) {

        //cada llamada al back representa un estado generado
        this.estadosGenerados++;

        //Poda: si el peso no asignado actual ya no mejora la mejor solucion
        //no sigo explorando esta rama
        if (hayPoda(pesoNoAsignadoActual)) {
            return;
        }
        //si ya decidi sobre los paquetes tengo una solucion completa
        if (esSolucion(indicePaquete)) {
            operarSolucion(asignacionActual, paquetesNoAsignadosActual, pesoNoAsignadoActual);
        } else {
            //Agarro el paquete del nivel actual del arbol
            Paquete paqueteActual = this.paquetes.get(indicePaquete);

            int nroHijo = 0;
            while (nroHijo < this.camiones.size()) {
                Camion camionActual = this.camiones.get(nroHijo);
                if (puedeAsignar(paqueteActual, camionActual, pesoActualPorCamion.get(nroHijo))) {
                    //agrego el paquete a la solucion parcial
                    agregarASolucion(paqueteActual, nroHijo, asignacionActual, pesoActualPorCamion);
                    // Recursividad: avanzar al siguiente nivel del arbol
                    back(indicePaquete + 1, asignacionActual, pesoActualPorCamion, paquetesNoAsignadosActual, pesoNoAsignadoActual);
                    //quitar el cambio para probar otro hijo
                    quitarDeSolucion(paqueteActual, nroHijo, asignacionActual, pesoActualPorCamion);
                }

                nroHijo++;
            }
            //existe la posibilidad de NO asignar el paquete actual
            agregarANoAsignados(paqueteActual, paquetesNoAsignadosActual);
            back(indicePaquete + 1, asignacionActual, pesoActualPorCamion, paquetesNoAsignadosActual, pesoNoAsignadoActual + paqueteActual.getPeso());
            quitarDeNoAsignados(paqueteActual, paquetesNoAsignadosActual);
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
    * Verificar si un paquete puede ser asignado a un camion
    * Se puede asignar si:
    * - El peso actual del camion + peso del paquete no debe superar la capacidad
    * - Si el paquete contiene alimentos, el camion debe estar refrigerado
    */
    private boolean puedeAsignar(Paquete paquete, Camion camion, int pesoActualCamion){
        if (pesoActualCamion + paquete.getPeso() > camion.getCapacidad()) {
            return false;
        }
        if (paquete.isContieneAlimentos() && !camion.isEstaRefrigerado()) {
            return false;
        }
        return true;
    }

    /*
    * Agregar un paquete a la solución parcial
    * nroCamion -> posición de la lista de camiones en la que se está asignando el paquete
    * y se actualiza el peso actual cargado en ese camion
    */
    private void agregarASolucion(Paquete paquete, int nroCamion, List<List<Paquete>> asignacionActual, List<Integer> pesoActualPorCamion){
        asignacionActual.get(nroCamion).add(paquete);
        int nuevoPeso = pesoActualPorCamion.get(nroCamion) + paquete.getPeso();
        pesoActualPorCamion.set(nroCamion, nuevoPeso);
    }

    /*
    * Quitar un paquete ed la solución parcial
    * Quitar el paquete de la lista del camion 
    * y se actualiza el peso actual cargado en ese camión
    */
    private void quitarDeSolucion(Paquete paquete, int nroCamion, List<List<Paquete>> asignacionActual, List<Integer> pesoActualPorCamion){
        asignacionActual.get(nroCamion).remove(asignacionActual.get(nroCamion).size() - 1);
        int nuevoPeso = pesoActualPorCamion.get(nroCamion) - paquete.getPeso();
        pesoActualPorCamion.set(nroCamion, nuevoPeso);
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
    private void quitarDeNoAsignados(Paquete paquete, List<Paquete> paquetesNoAsignadosActual){
        paquetesNoAsignadosActual.remove(paquetesNoAsignadosActual.size() - 1);
    }

    /*
    * Si el peso no asignado de la solución actual es menor que el mejor peso
    * encontrado hasta el momento entonces se guarda esta solución como la mejor
    */
    private void operarSolucion(List<List<Paquete>> asignacionActual, List<Paquete> paquetesNoAsignadosActual, int pesoNoAsignadoActual){
        if (pesoNoAsignadoActual < this.mejorPesoNoAsignado){
            this.mejorPesoNoAsignado = pesoNoAsignadoActual;
            this.mejorSolucion = new Solucion(this.camiones, asignacionActual, paquetesNoAsignadosActual, pesoNoAsignadoActual, this.estadosGenerados);
        }
    }
}
