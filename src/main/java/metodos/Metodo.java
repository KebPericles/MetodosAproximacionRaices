package metodos;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Function;
import org.mariuszgromada.math.mxparser.mXparser;

import java.util.Hashtable;
import java.util.Scanner;

public abstract class Metodo {
    protected int p, iteracion;
    protected double fxi;
    protected MetodoFuncion lambdaIteracion;
    protected DebugFuncion lambdaDebug;
    protected Hashtable<String, Argument> xi;
    protected Function f;
    protected Function fi1;
    protected Scanner scanner;

    public Metodo() {
        iniciar();
    }

    protected void iniciar() {
        //Configurar libreria
        mXparser.disableAlmostIntRounding();
        mXparser.disableUlpRounding();
        mXparser.enableCanonicalRounding();

        // Inicializar el guardado de los puntos
        xi = new Hashtable<>();

        scanner = new Scanner(System.in);

        iniciarMetodo();
    }

    /**
     * Pide los datos necesarios para ejecutar el método
     */
    public void iniciarMetodo() {
        pedirFuncion();
        pedirPuntos();
        pedirPrecision();
        setLambdaIteracion();
    }

    /**
     * Metodo donde se pide ingresar la precision decimal deseada para la raiz
     */
    void pedirPrecision() {
        String ps;
        //Precision
        do {
            System.out.println("Ingresa la precisión deseada (solo 0 y debe terminar con 1)(ej. 0.001 para 2 cifras decimales de precisión)");
            ps = scanner.nextLine();
        } while (!ps.matches("^0?\\.0*1$"));

        String[] pa = ps.split("\\.");

        p = ((pa.length != 1) ? pa[1] : pa[0]).length();
    }

    /**
     * Implementacion del proceso donde se debe de pedir la o las funciones necesarias para realizar el metodo
     */
    abstract void pedirFuncion();

    /**
     * Implementacion del proceso donde se piden los puntos necesarios para el metodo
     */
    abstract void pedirPuntos();

    /**
     * Implementacion donde se debe de imprimir las condiciones iniciales del metodo
     */
    abstract void imprimirCondicionesIniciales();

    /**
     * Ejecuta el metodo el numero de iteraciones indicado y mostrando o no los pasos
     *
     * @param iteraciones El numero de iteraciones a ejecutar, si es menor o igual a 0 entonces se ejecuta hasta aproximar la raiz con la precision deseada
     * @param debug       Si es true entonces se mostraran los pasos de cada iteracion
     */
    public double calcular(int iteraciones, boolean debug) {
        setDebugState(debug);
        iteracion = 0;

        imprimirCondicionesIniciales();

        if (iteraciones <= 0) {
            while (redondear(iterar()) != 0) {
                iteracion++;
            }
        } else {
            for (; iteracion < iteraciones; iteracion++) {
                iterar();
            }
        }
        return getArgumentN().getArgumentValue();
    }

    public void mostrarResultado(int iteraciones, boolean debug) {
        System.out.printf("El resultado es: "+(floatStringPrint()) + "%n", calcular(iteraciones, debug));
    }

    //---------------------

    //--------------------
    //Setters y Getters
    //--------------------

    /**
     * Devuelve el argumento xi+n
     *
     * @param n numero entero que indica el indice, si es 0 entonces devuelve xi
     * @return retorna el argumento xi+n
     */
    public Argument getArgumentN(int n) {
        if (xi.get(getKey(n)) == null ) setXi(0,n);
        return xi.get(getKey(n));
    }

    public Argument getArgumentN() {
        return getArgumentN(0);
    }

    public void setArgumentN(int n, Argument argument) {
        xi.put(getKey(n), argument);
    }

    protected void setXi(double x) {
        setXi(x, 0);
    }

    public void setXi(double x, int n) {
        setArgumentN(n, new Argument(getKey(n), x));
    }

    protected String getKey(int n) {
        return (n == 0 ? "xi" : (n < 0) ? "x" + Math.abs(n) + "i" : "xi" + n);
    }

    //---------------------

    //--------------------
    //String Functions
    //--------------------

    /**
     * @return El nombre del metodo
     */
    public abstract String getNombre();

    /**
     * Crea el formato para debuggear el metodo, debe llevar un salto de linea al final
     */
    abstract String getDebug();

    //---------------------

    //---------------------
    // Utilities
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
        double run();
    }

    /**
     * Calcula el siguiente punto y retorna el valor de la funcion evaluada en el nuevo punto
     *
     * @return El valor de la funcion evaluada en xi+1
     */
    protected double iterar() {
        return lambdaIteracion.run();
    }

    /**
     * Asigna a lambdaFuncion la funcion a usar durante cada iteracion
     * La función deberá retornar f(x) evaluada en el nuevo punto
     */
    abstract void setLambdaIteracion();


    protected interface DebugFuncion {
        String run();
    }

    private void setLambdaDebug(DebugFuncion lambdaDebug) {
        this.lambdaDebug = lambdaDebug;
    }

    /**
     * Establece si se deberá debuggear o no en cada iteracion
     *
     * @param debug True para debuggear en cada iteracion, false para no imprimir nada
     */
    protected void setDebugState(boolean debug) {
        if (debug) {
            setLambdaDebug(this::getDebug);
            return;
        }

        setLambdaDebug(() -> "");
    }
}
