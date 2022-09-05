package metodos;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Function;

public class MetodoSteffenson extends Metodo {

    //Programa para calcular la raiz de una funcion por el metodo de Steffenson
    //F1: Introducir la funcion
    //F2: Introducir los puntos
    //F3: Introducir la precision
    //F4: Definir xi+1
    //F5: Encontrar raiz

    //Variables
    double x;
    String ps;

    public MetodoSteffenson() {
        init();

        fi1 = new Function("fi1(xi)= xi - ( f(xi)^2 ) / ( f(xi + f(xi)) - f(xi) )", f);
    }

    @Override
    void pedirFuncion() {
        //Introducir la funcion
        System.out.println("Introduce la funcion f(x):");
        f = new Function("f", scanner.nextLine(), "x");
    }

    @Override
    void pedirPuntos() {
        //Introducir el punto
        System.out.println("Introduce el punto inicial:");
        setXi(scanner.nextDouble());
        scanner.nextLine();
    }

    protected void condicionesIniciales(){
        System.out.printf("f(x)=%s\nx0=" + floatStringPrint() + "\nf(x0)=" + floatStringPrint() + "%n",
                f.getFunctionExpressionString(),
                getArgumentN().getArgumentValue(),
                f.calculate(getArgumentN()));
        System.out.println("     " + fi1.calculate(getArgumentN()));
    }

    @Override
    public String getNombre() {
        return "Metodo de Steffenson";
    }

    @Override
    String getDebug() {
        return ("x = " + floatStringPrint() + " | f(x) = " + floatStringPrint() + "\n");
    }

    @Override
    void setMetodoFuncion() {
        metodoFuncion = () -> {
            Argument xi = getArgumentN();
            xi.setArgumentValue(fi1.calculate(xi));
            System.out.printf(debugFuncion.run(), xi.getArgumentValue(), f.calculate(xi));
            return f.calculate(xi);
        };
    }
}