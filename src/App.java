import service.Servicios;

public class App {
    public static void main(String[] args) throws Exception {
        Servicios servicios = new Servicios("examples/Camiones.csv", "examples/Paquetes.csv");
        System.out.println(servicios.servicio1("P001"));
        System.out.println(servicios.servicio1("P999"));
    }
}
