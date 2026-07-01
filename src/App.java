import service.Servicios;

public class App {
    public static void main(String[] args) throws Exception {
        Servicios servicios = new Servicios("examples/Camiones.csv", "examples/Paquetes.csv");

        System.out.println("--------------------------------------------------------");
        System.out.println("SERVICIO 1:");
        System.out.println("Complejidad promedio: O(1)");
        System.out.println(" ");
        System.out.println("Codigo P001:");
        System.out.println(servicios.servicio1("P001"));
        System.out.println(" ");
        System.out.println("Codigo P999:");
        System.out.println(servicios.servicio1("P999"));

        System.out.println("--------------------------------------------------------");
        System.out.println("SERVICIO 2:");
        System.out.println("Complejidad: O(1)");
        System.out.println(" ");
        System.out.println("Paquetes con alimentos:");
        System.out.println(servicios.servicio2(true));
        System.out.println(" ");
        System.out.println("Paquetes sin alimentos:");
        System.out.println(servicios.servicio2(false));

        System.out.println("--------------------------------------------------------");
        System.out.println("SERVICIO 3:");
        System.out.println("Complejidad: O(log N + M + K)");
        System.out.println(" ");
        System.out.println("Rango 1 a 10:");
        System.out.println(servicios.servicio3(1, 10));
        System.out.println(" ");
        System.out.println("Rango 50 a 100:");
        System.out.println(servicios.servicio3(50, 100));
        System.out.println(" ");
        System.out.println("Rango 20 a 60:");
        System.out.println(servicios.servicio3(20, 60));

        System.out.println("--------------------------------------------------------");
        System.out.println("BACKTRACKING");
        System.out.println("Complejidad: O((C + 1)^P)");
        System.out.println("Metrica: estados generados");
        System.out.println(" ");
        System.out.println(servicios.backtracking());

        System.out.println("--------------------------------------------------------");
        System.out.println("GREEDY");
        System.out.println("Complejidad: O(P log P + P*C)");
        System.out.println("Metrica: candidatos considerados");
        System.out.println(" ");
        System.out.println(servicios.greedy());
    }
}
