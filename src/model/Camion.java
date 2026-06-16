package model;

public class Camion {

    private int idCamion;
    private String patente;
    private boolean estaRefrigerado;
    private int capacidad;

    public Camion(int idCamion, String patente, boolean estaRefrigerado, int capacidad) {
        this.idCamion = idCamion;
        this.patente = patente;
        this.estaRefrigerado = estaRefrigerado;
        this.capacidad = capacidad;
    }

    public int getIdCamion() {
        return idCamion;
    }
    public String getPatente() {
        return patente;
    }
    public boolean isEstaRefrigerado() {
        return estaRefrigerado;
    }
    public int getCapacidad() {
        return capacidad;
    }

    @Override
    public String toString() {
        return "Camion " + idCamion 
                + " [" + patente 
                + ", refrigerado=" + estaRefrigerado 
                + ", capacidad=" + capacidad + "]";
    }
}
