package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
     * Primero se ordenan los paquetes de mayor a menor peso
     * La idea es intentar asignar primero los paquetes más pesados porque
     * si quedan sin asignar aumentan mas el peso total no asignado
     * Luego se recorre la lista ordenada de paquetes
     * Para cada paquete se busca entre los camiones válidos que quede
     * con menor espacio libre luego de cargar
     * Si el paquete no puede asignarse a ningún camion, se agrega a la lista
     * de paquetes no asignados
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
        limpiarCargaCamiones();
        List<Paquete> paquetesNoAsignados = new ArrayList<>();
        List<Paquete> candidatos = new ArrayList<>(this.paquetes);
        ordenarPorPesoDescendente(candidatos);
        int pesoNoAsignado = 0;
        int i = 0;
        while (i < candidatos.size()) {
            Paquete paqueteActual = candidatos.get(i);
            this.candidatosConsiderados++;
            Camion camionElegido = seleccionarCamion(paqueteActual);
            if (camionElegido != null) {
                camionElegido.cargarPaquete(paqueteActual);
            } else {
                paquetesNoAsignados.add(paqueteActual);
                pesoNoAsignado += paqueteActual.getPeso();
            }
            i++;
        }
        Solucion solucion = new Solucion(
                this.camiones,
                obtenerAsignacionActual(),
                paquetesNoAsignados,
                pesoNoAsignado,
                this.candidatosConsiderados
        );
        limpiarCargaCamiones();
        return solucion;
    }

    /*
    * Ordena los paquetes candidatos de mayor a menor peso
    * Esto permite aplicar la función de selección greedy recorriendo
    * la lista ya ordenada, sin buscar el paquete más pesado en cada iteracion
    */
    private void ordenarPorPesoDescendente(List<Paquete> candidatos) {
        Collections.sort(candidatos, new Comparator<Paquete>() {
            @Override
            public int compare(Paquete p1, Paquete p2) {
                return Integer.compare(p2.getPeso(), p1.getPeso());
            }
        });
    }

    /*
    * Selecciona el camión válido que queda con menor espacio libre
    * luego de cargar el paquete
    * Si no hay ningún camion válido devuelve null
    */
    private Camion seleccionarCamion(Paquete paquete) {
        Camion mejorCamion = null;
        int menorEspacioLibre = Integer.MAX_VALUE;
        int i = 0;
        while (i < this.camiones.size()) {
            Camion camion = this.camiones.get(i);
            if (camion.puedeCargar(paquete)) {
                int espacioLibre = camion.getCapacidad() - (camion.getPesoActual() + paquete.getPeso());
                if (espacioLibre < menorEspacioLibre) {
                    menorEspacioLibre = espacioLibre;
                    mejorCamion = camion;
                }
            }
            i++;
        }
        return mejorCamion;
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
}