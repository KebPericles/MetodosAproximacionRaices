package metodos;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Function;
import org.mariuszgromada.math.mxparser.mXparser;

import java.util.Scanner;

public abstract class Metodo {
    protected int p, iteracion;
    private MetodoFuncion metodoFuncion;
    protected DebugFuncion debugFuncion;
    protected Argument xi;
    protected Function f;
    protected Function fi1;
    protected Scanner scanner;

    protected void init() {
        //Configurar libreria
        mXparser.disableAlmostIntRounding();
        mXparser.disableUlpRounding();
        mXparser.enableCanonicalRounding();

        scanner = new Scanner(System.in);

        initMetodo();
    }

    /**
     * Pide los datos necesarios para ejecutar el método
     */
    public void initMetodo() {
        pedirFuncion();
        setXi(pedirPunto());
        pedirPrecision();
    }

    void pedirPrecision() {
        String ps;
        //Precision
        do {
            System.out.println("Ingresa la precisión deseada (solo 0 y debe terminar con 1)(ej. 0.001 para 2 cifras decimales de presición)");
            ps = scanner.nextLine();
        } while (!ps.matches("^0?\\.0*1$"));

        String[] pa = ps.split("\\.");

        p = ((pa.length != 1) ? pa[1] : pa[0]).length();
    }

    abstract void pedirFuncion();

    abstract double pedirPunto();

    /**
     * Ejecuta el metodo el numero de iteraciones indicado y mostrando o no los pasos
     *
     * @param iteraciones El numero de iteraciones a ejecutar, si es menor o igual a 0 entonces se ejecuta hasta aproximar la raiz con la precision deseada
     * @param debug       Si es true entonces se mostraran los pasos de cada iteracion
     */
    abstract public double calcular(int iteraciones, boolean debug);

    /**
     * Ejecuta el metodo el numero de iterciones indicado sin mostrar los pasos
     *
     * @param iteraciones El numero de iteraciones a ejecutar, si es menor o igual a 0 entonces se ejecuta hasta aproximar la raiz con la precision deseada
     */
    public double calcular(int iteraciones) {
        return calcular(iteraciones, false);
    }

    /**
     * Calcula la raiz de la funcion mostrando los pasos
     *
     * @param debug Si es true entonces se mostraran los pasos de cada iteracion
     */
    public double calcular(boolean debug) {
        return calcular(0, debug);
    }

    /**
     * Calcula la raiz de la funcion sin mostrar los pasos
     */
    public double calcular() {
        return calcular(0, false);
    }

    public void mostrarResultado(int iteraciones, boolean debug) {
        System.out.printf((floatStringPrint()) + "%n", calcular(iteraciones, debug));
    }

    public void mostrarResultado(int iteraciones) {
        mostrarResultado(iteraciones, false);
    }

    public void mostrarResultado(boolean debug) {
        mostrarResultado(0, debug);
    }

    public void mostrarResultado() {
        mostrarResultado(0, false);
    }

    //---------------------

    //--------------------
    //Setters y Getters
    //--------------------
    protected void setXi(double x) {
        xi = new Argument("xi", x);
    }

    //---------------------

    //--------------------
    //String Functions
    //--------------------

    /**
     * @return El nombre del metodo
     */
    abstract String getNombre();

    /**
     * Crea el formato para debuggear el metodo, debe llevar un salto de linea al final
     */
    abstract String getDebug();

    public void imprimirIteracion() {
        System.out.printf("Iteracion %d%n", iteracion);
    }

    //---------------------

    //---------------------
    //Utilities
    //---------------------
    String floatStringPrint() {
        return "%" + (p + 2) + "." + p + "f";
    }

    /**
     * Redondea un numero a la precision deseada
     *
     * @param x El numero a redondear
     * @param p El numero de cifras despues del punto
     */
    public double redondear(double x, int p) {
        return Math.round(x * Math.pow(10, p)) / Math.pow(10, p);
    }

    public double redondear(double x) {
        return redondear(x, p);
    }

    //---------------------

    //---------------------
    //Lambdas
    //---------------------

    /**
     * Interfaz para la creacion de lambdas de los diferentes metodos numericos
     */
    protected interface MetodoFuncion {
        double run(Argument xi, Function f, Function fi1, DebugFuncion debugFuncion);
    }

    /**
     * Calcula el siguiente punto y retorna el valor de la funcion evaluada en el nuevo punto
     *
     * @return El valor de la funcion evaluada en xi+1
     */
    protected double iterar() {
        return metodoFuncion.run(xi, f, fi1, debugFuncion);
    }

    protected void setMetodoFuncion(MetodoFuncion metodoFuncion) {
        this.metodoFuncion = metodoFuncion;
    }


    protected interface DebugFuncion {
        String run();
    }

    private void setDebugFuncion(DebugFuncion debugFuncion) {
        this.debugFuncion = debugFuncion;
    }

    /**
     * Establece si se deberá debuggear o no en cada iteracion
     *
     * @param debug True para debuggear en cada iteracion, false para no imprimir nada
     */
    protected void setDebugState(boolean debug) {
        if (debug) {
            setDebugFuncion(this::getDebug);
            return;
        }

        setDebugFuncion(() -> "");

    }
}
