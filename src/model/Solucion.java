package model;

import java.util.ArrayList;
import java.util.List;

public class Solucion {

    private List<Camion> camiones;
    private List<List<Paquete>> paquetesPorCamion;
    private List<Paquete> paquetesNoAsignados;
    private int pesoNoAsignado;
    private int estadosGenerados;

    /*
     * Crea una solución copiando las estructuras actuales
     * !importante: hacer copias porque backtracking sigue modificando
     * la listas después de encontrar una solución
     */
    public Solucion(List<Camion> camiones,List<List<Paquete>> paquetesPorCamion, List<Paquete> paquetesNoAsignados, 
    int pesoNoAsignado, int estadosGenerados) {

        this.camiones = new ArrayList<>(camiones);
        this.paquetesPorCamion = new ArrayList<>();
        for (List<Paquete> listaPaquetes : paquetesPorCamion) {
            this.paquetesPorCamion.add(new ArrayList<>(listaPaquetes));
        }
        this.paquetesNoAsignados = new ArrayList<>(paquetesNoAsignados);
        this.pesoNoAsignado = pesoNoAsignado;
        this.estadosGenerados = estadosGenerados;
    }

    public List<Camion> getCamiones() {
        return camiones;
    }
    public List<List<Paquete>> getPaquetesPorCamion() {
        return paquetesPorCamion;
    }
    public List<Paquete> getPaquetesNoAsignados() {
        return paquetesNoAsignados;
    }
    public int getPesoNoAsignado() {
        return pesoNoAsignado;
    }
    public int getEstadosGenerados() {
        return estadosGenerados;
    }
    public void setEstadosGenerados(int estadosGenerados) {
        this.estadosGenerados = estadosGenerados;
    }

    @Override
    public String toString() {
        return "Solucion [camiones=" + camiones + ", paquetesPorCamion=" + paquetesPorCamion + ", paquetesNoAsignados="
                + paquetesNoAsignados + ", pesoNoAsignado=" + pesoNoAsignado + ", estadosGenerados=" + estadosGenerados
                + ", getCamiones()=" + getCamiones() + ", getPaquetesPorCamion()=" + getPaquetesPorCamion()
                + ", getPaquetesNoAsignados()=" + getPaquetesNoAsignados() + ", getPesoNoAsignado()="
                + getPesoNoAsignado() + ", getEstadosGenerados()=" + getEstadosGenerados() + "]";
    }
}