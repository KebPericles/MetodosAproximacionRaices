import metodos.*;

import java.lang.reflect.InvocationTargetException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MetodosAproximacionRaices {

    static final String[] OPCIONES = {"Biseccion", "Punto fijo", "Newton-Raphson", "Secante", "Steffenson"};

    public static void main(String[] args) {
        Scanner sn = new Scanner(System.in);
        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario

        Metodo metodo = null;

        System.out.println("Programa para calcular la raiz de una funcion con diferentes metodos de aproximacion");

        while (!salir) {

            for (int i = 0; i < OPCIONES.length; i++) {
                System.out.println((i + 1) + ".- Metodo de " + OPCIONES[i]);
            }
            System.out.println((OPCIONES.length + 1) + ".- Salir");

            try {

                System.out.println("Escribe una de las opciones");
                opcion = sn.nextInt() - 1;

                switch (opcion) {
                    case 0:
                        metodo = new MetodoBiseccion();
                        break;
                    case 1:
                        metodo = new MetodoPuntoFijo();
                        break;
                    case 2:
                        metodo = new MetodoNewtonRaphson();
                        break;
                    case 3:
                        metodo = new MetodoSecante();
                        break;
                    case 4:
                        metodo = new MetodoSteffenson();
                        break;
                    default:
                        return;
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes insertar un número");
                sn.next();
            }

            System.out.println(metodo.getNombre());

            metodo.mostrarResultado(0, true);

            sn.nextLine();
            System.out.println("¿Quiere repetir? (s/n)");
            String a = sn.nextLine();
            salir = (a.toLowerCase().charAt(0) != 's');
        }
    }
}
