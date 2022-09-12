package metodos;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Function;

public class MetodoBiseccion extends Metodo {
    private static final int XD = -1;
    private static final int XU = 1;

    public MetodoBiseccion() {
        super();
    }

    /**
     * Implementacion del proceso donde se debe de pedir la o las funciones necesarias para realizar el metodo
     */
    @Override
    void pedirFuncion() {
        System.out.println("Introduce la funcion f(x):");
        f = new Function("f", scanner.nextLine(), "x");
    }

    /**
     * Implementacion del proceso donde se piden los puntos necesarios para el metodo
     */
    @Override
    void pedirPuntos() {
        boolean flagR = false;
        Argument xd = getArgumentN(XD), xu = getArgumentN(XU);
        double fxd, fxu;

        do {
            if (flagR) System.out.println("Los valores para x no son correctos");
            flagR = true;

            System.out.println("Ingrese xd: ");
            xd.setArgumentValue(scanner.nextDouble());
            scanner.nextLine();
            fxd = f.calculate(xd);

            System.out.println("Ingrese xu: ");
            xu.setArgumentValue(scanner.nextDouble());
            scanner.nextLine();
            fxu = f.calculate(xu);

        } while ((xd.getArgumentValue() == xu.getArgumentValue()) || (fxd > 0 && fxu > 0) || (fxd < 0 && fxu < 0));

        if (fxu < fxd) {
            //Swap variables
            double aux = xu.getArgumentValue();
            xu.setArgumentValue(xd.getArgumentValue());
            xd.setArgumentValue(aux);
            System.out.println("Xd y Xu se intercambiaron");
        }
    }

    /**
     * Implementacion donde se debe de imprimir las condiciones iniciales del metodo
     */
    @Override
    void condicionesIniciales() {
        System.out.printf("f(x) = %s\n" +
                        "xd = " + floatStringPrint() + " | f(xd) = " + floatStringPrint() + "\n" +
                        "xu = " + floatStringPrint() + " | f(xu) = " + floatStringPrint() + "\n",
                f.getFunctionExpressionString(),
                getArgumentN(XD).getArgumentValue(), f.calculate(getArgumentN(XD)),
                getArgumentN(XU).getArgumentValue(), f.calculate(getArgumentN(XU))
        );
    }

    /**
     * @return El nombre del metodo
     */
    @Override
    String getNombre() {
        return "Metodo de Biseccion";
    }

    /**
     * Crea el formato para debuggear el metodo, debe llevar un salto de linea al final
     */
    @Override
    String getDebug() {
        return "Ciclo %d \n" +
                "xd = " + floatStringPrint() + " | f(xd) = " + floatStringPrint() + "\n" +
                "xu = " + floatStringPrint() + " | f(xu) = " + floatStringPrint() + "\n";
    }

    /**
     * Asigna a lambdaFuncion la funcion a usar durante cada iteracion
     * La función deberá retornar f(x) evaluada en el nuevo punto
     */
    @Override
    void setLambdaIteracion() {
        lambdaIteracion = () -> {
            Argument xd = getArgumentN(XD);
            Argument xu = getArgumentN(XU);

            double xr = (xd.getArgumentValue() + xu.getArgumentValue()) / 2;
            double fxr = f.calculate(xr);

            setXi(xr, (fxr > 0) ? XU : XD);

            System.out.printf(lambdaDebug.run(),
                    iteracion,
                    xd.getArgumentValue(), f.calculate(xd),
                    xu.getArgumentValue(), f.calculate(xu)
            );

            setXi(xr);
            return fxr;
        };
    }
}
