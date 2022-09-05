package metodos;

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

        fi1 = new Function("fi1(xi)= xi - ( f(xi)^2 ) / ( f(xi + f(xi)) - f(xi) )", f);

        setMetodoFuncion((xi, f, fi1, debug) -> {
            double xi1;
            xi1 = fi1.calculate(xi);

            xi.setArgumentValue(xi1);
            System.out.printf(debug.run(), xi1, f.calculate(xi));
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
    double pedirPunto() {
        //Introducir el punto
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
        System.out.printf("f(x)=%s\nx0=" + floatStringPrint() + "\nfx0=" + floatStringPrint() + "%n", f.getFunctionExpressionString(), xi.getArgumentValue(), f.calculate(xi));
        System.out.println("     " + fi1.calculate(xi));

        // Si las iteraciones son igual o menor que cero entonces se busca la raiz segun la precision pedida
        // si no se hacen las iteraciones requeridas
        if (iteraciones == 0) {

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

        return xi.getArgumentValue();
    }

    @Override
    public String getNombre() {
        return "Metodo de Steffenson";
    }

    @Override
    String getDebug() {
        return ("x = " + floatStringPrint() + " | f(x) = " + floatStringPrint() + "\n");
    }
}