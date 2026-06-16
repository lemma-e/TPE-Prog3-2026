package algorithm;

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
     * - Podar si el peso no asignado ya no mejora la mejor solucion.
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
}
