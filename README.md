# TPE Programación 3

![Java](https://img.shields.io/badge/Java-21-red?logo=openjdk)
![Materia](https://img.shields.io/badge/Materia-Programaci%C3%B3n%203-blue)

---

# Integrantes

- Cuartas, Sofia Micaela
- Lemma, Ignacio

---

## Descripción

El problema consiste en procesar información de **paquetes** y **camiones de reparto** a partir de archivos `.csv`, para luego resolver distintos servicios de consulta y un problema de asignación de paquetes a camiones

El trabajo se divide en dos partes:

1. **Servicios de búsqueda y consulta sobre paquetes**
2. **Asignación de paquetes a camiones utilizando Backtracking y Greedy**

---

## Contexto del problema

Dados dos archivos de entrada:

- `Camiones.csv`
- `Paquetes.csv`

Cada camión posee:

- ID único
- Patente
- Si está refrigerado
- Capacidad máxima

Cada paquete posee:

- ID único
- Código identificador
- Peso
- Si contiene alimentos
- Nivel de urgencia

---

## Estructura de carpetas proyecto

```text
TPE_PROG3_2026/
│
├── examples/
│   ├── Camiones.csv
│   └── Paquetes.csv
│
├── src/
│   ├── algorithm/
│   │   ├── Backtracking.java
│   │   └── Greedy.java
│   │
│   ├── model/
│   │   ├── Camion.java
│   │   ├── Paquete.java
│   │   └── Solucion.java
│   │
│   ├── service/
│   │   └── Servicios.java
│   │
│   └── App.java
│
├── .gitignore
└── README.md
```

---

##  Formato de los archivos de entrada

### `Camiones.csv`

```text
<camiones_totales>
<id_camion>;<patente>;<esta_refrigerado>;<capacidad_kg>
```

Ejemplo:

```text
3
100;AAA000A;1;100
101;AAA001B;0;500
102;AAA002C;1;115
```

---

### `Paquetes.csv`

```text
<paquetes_totales>
<id_paquete>;<codigo_paquete>;<peso_kg>;<contiene_alimentos>;<nivel_urgencia>
```

Ejemplo:

```text
4
1;P001;30;1;80
2;P002;100;0;2
3;P003;80;0;10
4;P004;25;1;100
```

---

##  Compilar y ejecutar

Desde la raíz:

```bash
javac -d bin src/model/*.java src/service/*.java src/algorithm/*.java src/App.java
```

Luego:

```bash
java -cp bin App
```

> [!IMPORTANT]
> El proyecto fue probado compilando y ejecutando desde consola, sin depender exclusivamente de VSCode e IntelliJ IDEA

---

## Modelo de clases

| Clase | Responsabilidad |
|---|---|
| `Camion` | Representa un camión leído desde el archivo `Camiones.csv` |
| `Paquete` | Representa un paquete leído desde el archivo `Paquetes.csv` |
| `Solucion` | Representa una solución de paquetes a camiones |
| `Servicios` | Carga los archivos y resuelve los servicios de consulta |
| `Backtracking` | Resuelve la asignación mediante backtracking con poda |
| `Greedy` | Resuelve la asignación mediante la tecnica greedy |
| `App` | Ejecuta pruebas y muestra los resultados por consola |

---

# Primera Parte — Servicios

Implementar los tres servicios eficientes sobre los paquetes cargados

---

## Servicio 1 — Buscar paquete por código

### Descripción

Dado un código de paquete, retorna toda la información asociada al paquete

Si no existe, retorna `null`

```java
public Paquete servicio1(String codigoPaquete)
```

### Estructura utilizada

Se utiliza un `HashMap<String, Paquete>`:

```java
private HashMap<String, Paquete> paquetesPorCodigo;
```

La clave es el código del paquete y el valor es el objeto `Paquete`

### Complejidad

| Caso | Complejidad |
|---|---|
| Promedio | `O(1)` |
| Peor caso | `O(P)` |

Donde `P` es la cantidad de paquetes

> [!NOTE]
> Se utiliza `HashMap` porque permite acceder rápidamente a un paquete a partir de su código

---

## Servicio 2 — Buscar paquetes por alimentos

### Descripción

Dado un booleano, retorna los paquetes que contienen alimentos o los que no contienen alimentos

```java
public List<Paquete> servicio2(boolean contieneAlimentos)
```

### Estructuras utilizadas

Se utilizan dos listas preconstruidas:

```java
private List<Paquete> paquetesConAlimentos;
private List<Paquete> paquetesSinAlimentos;
```

Al cargar cada paquete, se agrega automáticamente a la lista correspondiente

### Complejidad

| Operación | Complejidad |
|---|---|
| Retornar paquetes con alimentos | `O(1)` |
| Retornar paquetes sin alimentos | `O(1)` |

> [!TIP]
> La eficiencia se logra porque las listas se arman durante la carga de datos, evitando recorrer todos los paquetes cada vez que se consulta

---

## Servicio 3 — Buscar paquetes por rango de urgencia

### Descripción

Dados dos valores enteros, retorna todos los paquetes cuyo nivel de urgencia se encuentre dentro del rango indicado, incluyendo los extremos

```java
public List<Paquete> servicio3(int urgenciaMinima, int urgenciaMaxima)
```

### Estructura utilizada

Se utiliza un `HashMap<Integer, List<Paquete>>`:

```java
private HashMap<Integer, List<Paquete>> paquetesPorUrgencia;
```

La clave es el nivel de urgencia y el valor es la lista de paquetes con ese nivel

### Complejidad

```text
O(N + M)
```

Donde:

- `N` es la cantidad de niveles de urgencia recorridos
- `M` es la cantidad de paquetes encontrados y agregados al resultado

En el peor caso, si se devuelven todos los paquetes, puede considerarse:

```text
O(P)
```

Donde `P` es la cantidad total de paquetes

---

## Resumen de servicios

| Servicio | Entrada | Salida | Estructura principal | Complejidad |
|---|---|---|---|---|
| Servicio 1 | Código de paquete | `Paquete` o `null` | `HashMap<String, Paquete>` | `O(1)` promedio |
| Servicio 2 | Booleano alimentos | Lista de paquetes | Listas preconstruidas | `O(1)` |
| Servicio 3 | Rango de urgencia | Lista de paquetes | `HashMap<Integer, List<Paquete>>` | `O(N + M)` |

---

# Segunda Parte — Asignación de paquetes

El objetivo es asignar paquetes a camiones minimizando el peso total de paquetes no asignados

---

## Restricciones

Para asignar un paquete a un camión se deben respetar dos restricciones:

1. El camión no puede superar su capacidad máxima de carga
2. Si el paquete contiene alimentos, solo puede asignarse a un camión refrigerado

```java
private boolean puedeAsignar(Paquete paquete, Camion camion, int pesoActualCamion)
```

---

## Formato de solución

La clase `Solucion` almacena:

- Camiones
- Paquetes asignados a cada camión
- Paquetes no asignados
- Peso total no asignado
- Métrica del algoritmo

Ejemplo de salida:

```text
Peso no asignado: 0
Metrica: 13

Camion 100 [AAA000A, refrigerado=true, capacidad=100]
Carga usada: 55/100
Paquetes asignados: [P001 [peso=30, alimentos=true, urgencia=80], P004 [peso=25, alimentos=true, urgencia=100]]

Paquetes no asignados: []
```

---

# 🔎 Backtracking

## Estrategia

Backtracking explora todas las posibles asignaciones de paquetes a camiones

Para cada paquete se prueban las siguientes opciones:

- Asignarlo a cada camión posible
- Dejarlo sin asignar

La solución se construye progresivamente y se guarda la mejor solución encontrada, es decir, aquella con menor peso no asignado

---

## Molde de la solución

Cada nivel del árbol representa una decisión sobre un paquete

```text
Nivel 0 → decisión sobre paquete 0
Nivel 1 → decisión sobre paquete 1
Nivel 2 → decisión sobre paquete 2
...
```

Para cada paquete existen:

```text
C opciones de camión + 1 opción de no asignar
```

Donde `C` es la cantidad de camiones

---

## Podas utilizadas

Se aplican podas cuando:

- El paquete supera la capacidad disponible del camión
- El paquete contiene alimentos y el camión no es refrigerado
- El peso no asignado actual ya no puede mejorar la mejor solución encontrada

---

## Métrica

La métrica utilizada es:

```text
Cantidad de estados generados
```

Cada llamada recursiva al método `back` representa un estado generado

---

## Complejidad

Se define:

```text
A = C + 1
```

Donde:

- `C` es la cantidad de camiones
- `1` representa la opción de no asignar el paquete

Entonces, para `P` paquetes:

```text
O(A^P)
```

Equivalente a:

```text
O((C + 1)^P)
```

> [!WARNING]
> Backtracking puede encontrar la solución óptima, pero su costo crece exponencialmente con la cantidad de paquetes

---

# Greedy

## Estrategia

En cada paso:

1. Selecciona el paquete más pesado que aún no fue procesado
2. Busca el camión válido que quede con menor espacio libre luego de cargarlo
3. Si existe un camión válido, asigna el paquete
4. Si no existe, deja el paquete sin asignar

---

## Función de selección

La función de selección es:

```text
Elegir el paquete más pesado pendiente.
```

Justificación:

```text
Si un paquete pesado queda sin asignar, aumenta más el peso total no asignado.
```

---

## Función de factibilidad

Un paquete puede asignarse a un camión si:

- No supera la capacidad máxima del camión
- Si contiene alimentos, el camión es refrigerado

---

## Métrica

La métrica utilizada es:

```text
Cantidad de candidatos considerados
```

Cada paquete seleccionado desde el conjunto de candidatos se cuenta como candidato considerado

---

## Complejidad

La complejidad es:

```text
O(P^2 + P*C)
```

Donde:

- `P` es la cantidad de paquetes
- `C` es la cantidad de camiones

Esto se debe a que:

- `seleccionarPaquete` recorre los candidatos pendientes
- `seleccionarCamion` recorre los camiones para cada paquete

> [!WARNING]
> Greedy no garantiza encontrar la solución óptima. Toma decisiones locales y no vuelve atrás

---

#  Comparación entre Backtracking y Greedy

| Aspecto | Backtracking | Greedy |
|---|---|---|
| Explora alternativas | Sí | No |
| Vuelve atrás | Sí | No |
| Garantiza óptimo | Sí, si explora correctamente el espacio | No necesariamente |
| Costo temporal | Exponencial | Polinomial |
| Métrica | Estados generados | Candidatos considerados |
| Resultado esperado | Mejor solución posible | Aproximación rápida |

---

## Resultado con los archivos de ejemplo

| Técnica | Peso no asignado | Métrica |
|---|---:|---:|
| Backtracking | `0` | `13 estados generados` |
| Greedy | `25` | `4 candidatos considerados` |

> [!IMPORTANT]
> En la prueba realizada, Backtracking encontró una solución óptima con peso no asignado `0`, mientras que Greedy obtuvo una solución válida pero no óptima, dejando sin asignar un paquete de peso `25`

---

# Pruebas desde `App.java`

La clase `App.java` ejecuta una demostración completa del proyecto

Se prueban:

- Servicio 1 con un código existente
- Servicio 1 con un código inexistente
- Servicio 2 con paquetes con alimentos
- Servicio 2 con paquetes sin alimentos
- Servicio 3 con varios rangos de urgencia
- Backtracking
- Greedy

---

## Ejemplo de salida

```text
SERVICIO 1:
Complejidad promedio: O(1)

Codigo P001:
P001 [peso=30, alimentos=true, urgencia=80]

Codigo P999:
null

SERVICIO 2:
Complejidad: O(1)

Paquetes con alimentos:
[P001 [peso=30, alimentos=true, urgencia=80], P004 [peso=25, alimentos=true, urgencia=100]]

Paquetes sin alimentos:
[P002 [peso=100, alimentos=false, urgencia=2], P003 [peso=80, alimentos=false, urgencia=10]]

BACKTRACKING
Complejidad: O((C + 1)^P)
Metrica: estados generados

Peso no asignado: 0
Metrica: 13

GREEDY
Complejidad: O(P^2 + P*C)
Metrica: candidatos considerados

Peso no asignado: 25
Metrica: 4
```

---

# Decisiones de diseño

## Uso de `HashMap`

Se utiliza `HashMap` para acelerar búsquedas por clave

Ejemplo:

```java
private HashMap<String, Paquete> paquetesPorCodigo;
```

Esto permite obtener paquetes por código con complejidad promedio `O(1)`

---

## Uso de listas preconstruidas

Para el Servicio 2 se mantienen dos listas:

```java
private List<Paquete> paquetesConAlimentos;
private List<Paquete> paquetesSinAlimentos;
```

Esto permite responder en tiempo constante sin recorrer todos los paquetes

---

## Copia de soluciones

La clase `Solucion` copia las estructuras recibidas

Esto es necesario porque Backtracking sigue modificando las listas mientras explora nuevas ramas

```java
this.paquetesPorCamion.add(new ArrayList<>(listaPaquetes));
```

> [!IMPORTANT]
> Sin estas copias, la mejor solución guardada podría modificarse al continuar la exploración del árbol de búsqueda

---

## Procesamiento de CSV

Cada línea del archivo se separa usando `split(";")`

Ejemplo:

```java
String[] datos = linea.split(";");
```

Luego se convierten los valores necesarios:

```java
int peso = Integer.parseInt(datos[2]);
boolean contieneAlimentos = datos[3].equals("1");
```

> [!NOTE]
> Esta estrategia es adecuada porque el formato de entrada del trabajo es simple y controlado

---

# Fuentes consultadas

- Material de la cátedra 
- https://es.stackoverflow.com/questions/38085/leer-fichero-formato-csv-en-java
- https://www.youtube.com/watch?v=uwn-Zkttux4
- https://www.youtube.com/watch?v=0bH5p9gdTtY
- https://stackoverflow.com/questions/3946529/what-is-the-best-standard-style-for-a-tostring-implementation
- https://stackoverflow.com/questions/43370772/how-to-pretty-print-a-complex-java-object-e-g-with-fields-that-are-collections
- https://stackoverflow.com/questions/3615721/how-to-use-the-tostring-method-in-java?utm_source=chatgpt.com
- https://stackoverflow.com/questions/11659515/overriding-tostring-method?utm_source=chatgpt.com
- https://mkyong.com/java/how-to-read-and-parse-csv-file-in-java/?utm_source=chatgpt.com
- https://www.youtube.com/watch?v=VX9CwPn-BBE
- https://stackoverflow.com/questions/52108017/csv-split-by-a-comma?utm_source=chatgpt.com
