package principal;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import clases.Conversion;
import clases.Menu;

public class Principal {

	public static void main(String[] args) throws IOException {
		Scanner lectura = new Scanner(System.in);
        System.out.println("\n************************************************************\n"
        		+ "Bienvenidos al Convertidor de Monedas- Challenge Backend ONE JAVA.");
        try {
            Conversion conversion = new Conversion();
           do {
                Menu.ListarMenu();
                int opcion = lectura.nextInt();
                Menu.convertir(opcion, conversion, lectura);
            } while (true);
        } catch (InputMismatchException e) {
            System.out.println("Por favor, ingrese un número de la lista.");
        } finally {
            lectura.close();
        }
	}

}
