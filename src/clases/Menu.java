package clases;

import java.io.IOException;
import java.util.Scanner;

public class Menu {
	public static void ListarMenu() {
        System.out.println("************************************************************");
        System.out.println("""
                1- Convertir peso argentino (ARS) a dólar (USD).
                2- Convertir dólar (USD) a peso argentino (ARG).
                3- Convertir real brasileño (BRL) a dólar (USD).
                4- Convertir dólar (USD) a real brasileño (BRL).
                5- Convertir peso colombiano (COP) a dólar (USD).
                6- Convertir dólar (USD) a peso colombiano (COP).
                7- Otras monedas.
                8- Obtener historial de conversiones.
                9- Salir
                Elija una opción:""");
    }

    public static void convertir(int opcion, Conversion conversion, Scanner lectura) {
        switch (opcion) {
            case 1:
            	convertirMoneda("ARS", "USD", conversion, lectura);
                break;
            case 2:
            	convertirMoneda("USD", "ARS", conversion, lectura);
                break;
            case 3:
                convertirMoneda("BRL", "USD", conversion, lectura);
                break;
            case 4:
                convertirMoneda("USD", "BRL", conversion, lectura);
                break;
            case 5:
                convertirMoneda("COP", "USD", conversion, lectura);
                break;
            case 6:
                convertirMoneda("USD", "COP", conversion, lectura);
                break;
            case 7:
                elegirOtrasMonedas(conversion, lectura);
                break;
            case 8:
            	mostrarHistorialConversiones();
                break;
            case 9:
                System.out.println("¡Gracias por usar nuestro conversor! ¡Nos vemos!");
                System.exit(0);
            default:
                System.out.println("Esta opción no es valida. Por favor, seleccione una opción válida del menú.");
                break;
        }
    }

    private static void convertirMoneda(String monedaBase, String monedaDestino, Conversion c, Scanner lectura) {
        System.out.println("Ingrese la cantidad que quiere convertir:");
        double monto = lectura.nextDouble();
        int montoEntero = (int) monto;
        RegistroConversion registro = c.convertir(monedaBase, monedaDestino, montoEntero);
        mostrarResultado(registro, monedaBase, monedaDestino);
    }
    
    private static void elegirOtrasMonedas(Conversion conversion, Scanner lectura) {
        try {
            System.out.println("""
            A continuación se muestra la cantidad de 19 monedas para poder convertir:
        
            1.  AED: United Arab Emirates      2.  AFN: Afghanistan            3.  ALL: Albania
            4.  AMD: Armenia                   5.  ANG: Netherlands Antilles   6.  AOA: Angola
            7.  AUD: Australia                 8.  AWG: Aruba                  9.  AZN: Azerbaijan                
            10. BBD: Barbados                  11. BGN: Bulgaria		   12. BHD: Bahrain
            13. BSD: Bahamas                   14. BTN: Bhutan                 15. BWP: Botswana             
            16. BZD: Belize                    17. CAD: Canada                 18. CHF: Switzerland            
            19. CLP: Chile
            Intoroduzca solo los codigos, por ejemplo: CAD, BZD
            
            Ingrese el código de moneda base:""");

            String monedaBase = lectura.next().toUpperCase();
            System.out.println("Ingrese el código de la moneda a covertir:");
            String monedaDestino = lectura.next().toUpperCase();
            convertirMoneda(monedaBase, monedaDestino, conversion, lectura);
        } catch (Exception e) {
            System.out.println("Error: Ingrese un código de moneda válido.");
        }
    }
    
    private static void mostrarHistorialConversiones() {
    	RegistroConversion[] obtener;
		Conversion con = new Conversion();
		try {
			obtener = con.obtenerRegistros();
			if(obtener != null) {
				for(int i = 0; i < obtener.length; i++) {
					System.out.println(String.format("Se convertió de %s a %s la cantidad de: %s "
							+ "con un resultado de: %s y una tasa de conversión del: %s "
							+ "En la fecha: %s", obtener[i].getConversion().getMonedaOrigen(),
							obtener[i].getConversion().getMonedaDestino(), obtener[i].getConversion().getMonto(),
							obtener[i].getConversion().getResultado(), obtener[i].getConversion().getConversionRate(),
							obtener[i].getTimestamp()));
				}
			}else {
				System.out.println("No hay registros para mostrar.");
			}
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		}
    }

    private static void mostrarResultado(RegistroConversion registro, String monedaBase, String monedaDestino) {
        if (registro != null) {
            Conversion conversion = registro.getConversion();
            if (conversion != null) {
                double monto = conversion.getMonto();
                double resultado = conversion.getResultado();
                double conversionRate = conversion.getConversionRate();

                monto = Math.round(monto * 10000.0) / 10000.0;
                resultado = Math.round(resultado * 10000.0) / 10000.0;
                conversionRate = Math.round(conversionRate * 10000.0) / 10000.0;

                System.out.println("************************************************************");
                System.out.println("Resultado de la conversión:");
                System.out.println("---------------------------------");
                System.out.println("Moneda base: " + conversion.getMonedaOrigen());
                System.out.println("Moneda a convertir: " + conversion.getMonedaDestino());
                System.out.println("Monto: " + String.format("%.1f", monto));
                System.out.println("Tasa de conversión: " + String.format("%.5f", conversionRate));
                System.out.println("Resultado: " + String.format("%.2f", resultado));
                System.out.println("************************************************************");

            } else {
                System.out.println("Ha ocurrido un error al obtener la conversión.");
            }
        } else {
            System.out.println("Ha ocurrido un error al convertir la moneda.");
        }
    }
    
}
