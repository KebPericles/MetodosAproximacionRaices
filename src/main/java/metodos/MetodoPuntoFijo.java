package metodos;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Function;

public class MetodoPuntoFijo extends Metodo {

    public MetodoPuntoFijo() {
        super();

        // Formula para el siguiente punto
        fi1 = new Function("fi1(xi)=xi+f(xi)", f);
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

    protected void setLambdaIteracion() {
        lambdaIteracion = () -> {
            Argument xi = getArgumentN();
            xi.setArgumentValue(fi1.calculate(xi));

            System.out.printf((lambdaDebug.run()) + "%n",
                    xi.getArgumentValue(),
                    f.calculate(xi)
            );

            return redondear(f.calculate(xi));
        };
    }

    protected void imprimirCondicionesIniciales() {
        System.out.printf("f(x)=%s\nx0=" + floatStringPrint() + "\nf(x0)=" + floatStringPrint() + "%n", f.getFunctionExpressionString(), getArgumentN().getArgumentValue(), f.calculate(getArgumentN()));
        System.out.println("     " + fi1.calculate(getArgumentN()));
    }

    @Override
    public String getNombre() {
        return "Método de punto fijo";
    }

    @Override
    String getDebug() {
        return "Ciclo " + iteracion + "\nx = " + floatStringPrint() + " | f(x) = " + floatStringPrint() + "\n";
    }
}
