package metodos;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Function;

public class MetodoNewtonRaphson extends Metodo {

    private final String debug = "Ciclo %d \nx = " + floatStringPrint() + " | f(x) = " + floatStringPrint() + "\n";
    Function df;

    public MetodoNewtonRaphson() {
        super();

        df = new Function("df(s)=der(f(x),x,s)", f);
        fi1 = new Function("fi1(x)=-f(x)/df(x) + x", f, df);
    }

    @Override
    void pedirFuncion() {
        System.out.println("Introduce la funcion f(x):");
        f = new Function("f", scanner.nextLine(), "x");
    }

    @Override
    void pedirPuntos() {
        System.out.println("Introduce el punto inicial:");
        setXi(scanner.nextDouble());
        scanner.nextLine();
    }

    @Override
    void condicionesIniciales() {
        System.out.printf("f(x)=%s\n" +
                        "x0=" + floatStringPrint() + "\n" +
                        "f(x0)=" + floatStringPrint() + "%n",
                f.getFunctionExpressionString(),
                getArgumentN().getArgumentValue(),
                f.calculate(getArgumentN())
        );
    }

    /**
     * @return El nombre del metodo
     */
    @Override
    String getNombre() {
        return "Metodo de Newton-Raphson";
    }

    /**
     * Crea el formato para debuggear el metodo, debe llevar un salto de linea al final
     */
    @Override
    String getDebug() {
        return debug;
    }

    /**
     * Asigna el proceso a usar durante cada iteracion
     */
    @Override
    void setLambdaIteracion() {
        lambdaIteracion = () -> {
            Argument xi = getArgumentN();
            xi.setArgumentValue(fi1.calculate(xi));

            System.out.printf((lambdaDebug.run()) + "%n",
                    iteracion,
                    xi.getArgumentValue(),
                    f.calculate(xi)
            );

            return redondear(f.calculate(xi));
        };
    }
}
