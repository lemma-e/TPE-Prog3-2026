package algorithm;

import java.util.ArrayList;
import java.util.List;
import model.Camion;
import model.Paquete;
import model.Solucion;

public class Greedy {

    private List<Camion> camiones;
    private List<Paquete> paquetes;

    private int candidatosConsiderados;

    public Greedy(List<Camion> camiones, List<Paquete> paquetes) {
        this.camiones = camiones;
        this.paquetes = paquetes;
        this.candidatosConsiderados = 0;
    }

    /*
     * Estrategia Greedy:
     * El candidato es el paquete mas pesado que todavía no fue procesado
     * idea: es intentar asignar primero los paquetes de mayor peso porque si
     * quedan sin asignar aumentan más el peso total no asignado
     * La asignacion se busca entre los camiones válidos aquel que quede con
     * menor espacio libre luego de cargar el paquete e intentar aprovechar
     * mejor la capacidad disponible.
     * El paquete que no puede asignarse a ningún camion se agrega a la lista de
     * paquetes no asignados
     * 1. Crea solución vacía
     * 2. Copia los paquetes como candidatos
     * 3. Mientras haya candidatos:
            - elige el paquete más pesado
            - busca el camion válido que quede con menor espacio libre
            - si existe lo asigna
            - si no existe lo deja no asignado
     * 4. Devuelve Solucion
     */

    /*
    * llamar el algoritmo greedy
    * mientras existan paquetes selecciona el paquete más pesado
    * y busca asignarlo al camión valido que quede con menor espacio libre
    * Si no existe uno válido, el paquete queda no asignado
    * Retorna una solución con los paquetes asignados, los paquetes no asignados,
    * el peso total no asignado y la cantidad de candidatos considerados
    */
    public Solucion resolver() {
        List<List<Paquete>> asignacionActual = new ArrayList<>();
        List<Integer> pesoActualPorCamion = new ArrayList<>();

        int i = 0;
        while (i < this.camiones.size()) {
            asignacionActual.add(new ArrayList<>());
            pesoActualPorCamion.add(0);
            i++;
        }

        List<Paquete> paquetesNoAsignados = new ArrayList<>();
        List<Paquete> candidatos = new ArrayList<>(this.paquetes);

        int pesoNoAsignado = 0;
        while (!candidatos.isEmpty()) {
            Paquete paqueteActual = seleccionarPaquete(candidatos);
            candidatos.remove(paqueteActual);
            this.candidatosConsiderados++;

            int nroCamion = seleccionarCamion(paqueteActual, pesoActualPorCamion);
            if (nroCamion != -1) {
                agregarASolucion(paqueteActual, nroCamion, asignacionActual, pesoActualPorCamion);
            } else {
                paquetesNoAsignados.add(paqueteActual);
                pesoNoAsignado += paqueteActual.getPeso();
            }
        }
        return new Solucion(this.camiones,asignacionActual,paquetesNoAsignados,pesoNoAsignado,this.candidatosConsiderados);
    }

    /*
     * Selecciona el paquete más pesado entre los candidatos
     */
    private Paquete seleccionarPaquete(List<Paquete> candidatos) {
        Paquete mejorPaquete = candidatos.get(0);
        int i = 1;

        while (i < candidatos.size()) {
            Paquete paqueteActual = candidatos.get(i);
            if (paqueteActual.getPeso() > mejorPaquete.getPeso()) {
                mejorPaquete = paqueteActual;
            }
            i++;
        }
        return mejorPaquete;
    }

    /*
     * Selecciona el camion válido que queda con menor espacio libre
     * luego de cargar el paquete
     * Si no hay ningún camion válido, devuelve -1
     */
    private int seleccionarCamion(Paquete paquete, List<Integer> pesoActualPorCamion) {
        int mejorCamion = -1;
        int menorEspacioLibre = Integer.MAX_VALUE;
        int i = 0;

        while (i < this.camiones.size()) {
            Camion camion = this.camiones.get(i);
            int pesoActual = pesoActualPorCamion.get(i);
            if (puedeAsignar(paquete, camion, pesoActual)) {
                int espacioLibre = camion.getCapacidad() - (pesoActual + paquete.getPeso());
                if (espacioLibre < menorEspacioLibre) {
                    menorEspacioLibre = espacioLibre;
                    mejorCamion = i;
                }
            }
            i++;
        }
        return mejorCamion;
    }

    /*
     * Verifica si un paquete puede asignarse a un camion:
     * - no debe superar la capacidad del camion
     * - si contiene alimentos, el camión debe estar refrigerado
     */
    private boolean puedeAsignar(Paquete paquete, Camion camion, int pesoActualCamion) {
        if (pesoActualCamion + paquete.getPeso() > camion.getCapacidad()) {
            return false;
        }
        if (paquete.isContieneAlimentos() && !camion.isEstaRefrigerado()) {
            return false;
        }
        return true;
    }

    /*
     * Agrega un paquete a la solución parcial y actualiza el peso actual
     * cargado en el camion correspondiente
     */
    private void agregarASolucion(Paquete paquete,int nroCamion,List<List<Paquete>> asignacionActual,List<Integer> pesoActualPorCamion){
        asignacionActual.get(nroCamion).add(paquete);
        int nuevoPeso = pesoActualPorCamion.get(nroCamion) + paquete.getPeso();
        pesoActualPorCamion.set(nroCamion, nuevoPeso);
    }
}