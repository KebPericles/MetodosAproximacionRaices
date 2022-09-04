import metodos.Metodo;
import metodos.MetodoSteffenson;

public class MetodosAproximacionRaices {

    public static void main(String[] args) {

        Metodo metodo=new MetodoSteffenson();

        metodo.mostrarResultado(true);
        System.out.println("Programa para calcular la raiz de una funcion con diferentes metodos de aproximacion");

    }
}
