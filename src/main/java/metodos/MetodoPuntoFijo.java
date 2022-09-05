package metodos;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Function;

public class MetodoPuntoFijo extends Metodo {

    double x, fxi, xi1;

    public MetodoPuntoFijo() {
        init();

        // Se basa en
        fi1 = new Function("fi1(xi)=xi+f(xi)", f);

        setMetodoFuncion((xi, f, fi1, debug) -> {
            xi.setArgumentValue(fi1.calculate(xi));

            System.out.printf((getDebug()) + "%n", xi.getArgumentValue(), f.calculate(xi));

            return redondear(f.calculate(xi));
        });
    }

    @Override
    void pedirFuncion() {
        System.out.println("Introduce la funcion f(x):");
        f = new Function("f", scanner.nextLine(), "x");
    }

    @Override
    double pedirPunto() {
        System.out.println("Introduce el punto inicial:");
        x = scanner.nextDouble();
        scanner.nextLine();

        return x;
    }

    @Override
    public double calcular(int iteraciones, boolean debug) {
        setDebugState(debug);
        iteracion = 0;

        //Condiciones iniciales
        System.out.printf("f(x)=%s\nx0=" + floatStringPrint() + "\nf(x0)=" + floatStringPrint() + "%n", f.getFunctionExpressionString(), xi.getArgumentValue(), f.calculate(xi));
        System.out.println("     " + fi1.calculate(xi));

        if (iteraciones <= 0) {
            do {
                imprimirIteracion();
                fxi = iterar();
                iteracion++;
            } while (redondear(fxi) != 0);
        } else {
            for (; iteracion < iteraciones; iteracion++) {
                imprimirIteracion();
                iterar();
            }
        }
        return xi1;
    }

    @Override
    String getNombre() {
        return "Método de punto fijo";
    }

    @Override
    String getDebug() {
        return "x = " + floatStringPrint() + " | f(x) = " + floatStringPrint() + "\n";
    }
}
