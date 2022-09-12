import metodos.Metodo;
import metodos.MetodoNewtonRaphson;
import metodos.MetodoPuntoFijo;
import metodos.MetodoSteffenson;

public class MetodosAproximacionRaices {

    public static void main(String[] args) {

        Metodo metodo = new MetodoNewtonRaphson();

        metodo.mostrarResultado(0,true);
        System.out.println("Programa para calcular la raiz de una funcion con diferentes metodos de aproximacion");

    }
}
