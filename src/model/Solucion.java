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
    private int calcularPeso(List<Paquete> paquetes) {
        int total = 0;
        for (Paquete paquete : paquetes) {
            total += paquete.getPeso();
        }
        return total;
    }

    @Override
    public String toString() {
        String resultado = "";
        resultado += "Peso no asignado: " + this.pesoNoAsignado + "\n";
        resultado += "Metrica: " + this.estadosGenerados + "\n\n";
        int i = 0;
        while (i < this.camiones.size()) {
            Camion camion = this.camiones.get(i);
            List<Paquete> paquetesAsignados = this.paquetesPorCamion.get(i);
            resultado += camion + "\n";
            resultado += "Carga usada: " + calcularPeso(paquetesAsignados) + "/" + camion.getCapacidad() + "\n";
            resultado += "Paquetes asignados: " + paquetesAsignados + "\n\n";
            i++;
        }
        resultado += "Paquetes no asignados: " + this.paquetesNoAsignados + "\n";
        return resultado;
    }
}