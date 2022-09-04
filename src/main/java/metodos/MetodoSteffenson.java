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
    double x, fxi;
    String ps;

    public MetodoSteffenson() {
        init();
        pedirCosas();
        fi1 = new Function("fi1(xi)= xi - ( f(xi)^2 ) / ( f(xi + f(xi)) - f(xi) )", f);

        setMetodoFuncion((xi, fi1, f, debug) -> {
            double xi1;
            xi1 = fi1.calculate(xi);

            xi.setArgumentValue(xi1);
            System.out.print(String.format(debug.run(), xi1, f.calculate(xi)));
            return f.calculate(xi);
        });
    }

    @Override
    void pedirFuncion() {
        //Introducir la funcion
        System.out.println("Introduce la funcion f(x):");
        f = new Function("f", scanner.nextLine(), "x");
    }

    @Override
    void pedirPunto() {
        //Introducir el punto
        System.out.println("Introduce el punto inicial:");
        x = scanner.nextDouble();

        scanner.nextLine();

        xi = new Argument("xi", x);
    }

    @Override
    public double calcular(int iteraciones, boolean debug) {
        setDebugState(debug);

        //Condiciones iniciales
        System.out.println(String.format("f(x)=%s\nx0="+floatStringPrint()+"\nfx0="+floatStringPrint(),f.getFunctionExpressionString(),xi.getArgumentValue(),f.calculate(xi)));
        System.out.println("     "+fi1.calculate(xi));

        // Si las iteraciones son igual o menor que cero entonces se busca la raiz segun la precision pedida
        // si no se hacen las iteraciones requeridas
        if (iteraciones == 0) {

            do {
                fxi = metodoIteracion();
            } while (redondear(fxi) != 0);
        } else {

            for (int i = 0; i < iteraciones; i++) {
                metodoIteracion();
            }
        }

        return xi.getArgumentValue();
    }

    @Override
    public String getNombre() {
        return "Metodo de Steffenson";
    }

    @Override
    String getDebug() {
        return ("xi = " + floatStringPrint() + " | fxi = " + floatStringPrint() + "\n");
    }
}