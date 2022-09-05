package metodos;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Function;

public class MetodoSecante extends Metodo {

    Function df;

    public MetodoSecante() {
        init();

        //F4
        df = new Function("df(x0,x1)=(f(x0)-f(x1))/(x0-x1)", f);
        fi1 = new Function("fi1(xi,x1i)=xi - f(xi)/df(x1i,xi)", f, df);
    }

    /**
     *
     */
    @Override
    void pedirFuncion() {
        System.out.println("Introduce la funcion f(x):");
        f = new Function("f", scanner.nextLine(), "x");
    }

    /**
     *
     */
    @Override
    void pedirPuntos() {
        do {
            System.out.println("Introduce el punto 0:");
            setXi(scanner.nextDouble(), -1);
            scanner.nextLine();

            System.out.println("Introduce el punto 1:");
            setXi(scanner.nextDouble());
            scanner.nextLine();
        } while (getArgumentN().getArgumentValue() == getArgumentN(-1).getArgumentValue());
    }

    @Override
    void condicionesIniciales() {
        System.out.printf("f(x) = %s%nx0 = "+floatStringPrint()+"           x1 = "+floatStringPrint()+"%n",
                f.getFunctionExpressionString(),
                getArgumentN(-1).getArgumentValue(),
                getArgumentN(0).getArgumentValue()
        );
    }

    /**
     * @return El nombre del metodo
     */
    @Override
    String getNombre() {
        return "Metodo de la Secante";
    }

    /**
     * Crea el formato para debuggear el metodo, debe llevar un salto de linea al final
     */
    @Override
    String getDebug() {
        return "x = " + floatStringPrint() + " | f(x)" + floatStringPrint() + "\n";
    }

    /**
     *
     */
    @Override
    void setLambdaIteracion() {
        lambdaIteracion = () -> {
            Argument x1i = getArgumentN(-1), xi = getArgumentN();
            double xi1 = fi1.calculate(xi, x1i);

            x1i.setArgumentValue(xi.getArgumentValue());
            xi.setArgumentValue(xi1);

            System.out.printf(lambdaDebug.run(), xi.getArgumentValue(), f.calculate(xi));

            return f.calculate(xi);
        };
    }
}
