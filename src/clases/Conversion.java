package clases;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;

public class Conversion {
    @SerializedName("Moneda_Origen")
    private String monedaOrigen;

    @SerializedName("Moneda_Destino")
    private String monedaDestino;

    @SerializedName("Monto_a_Convertir")
    private double monto;

    @SerializedName("Resultado")
    private double resultado;

    @SerializedName("Tasa_Conversion")
    private double conversionRate;

    public Conversion() {}

    public Conversion(String monedaOrigen, String monedaDestino, double monto) {
        this.monedaOrigen = monedaOrigen;
        this.monedaDestino = monedaDestino;
        this.monto = monto;
    }
    
    public String getMonedaOrigen() {
        return monedaOrigen;
    }

    public void setMonedaOrigen(String monedaOrigen) {
        this.monedaOrigen = monedaOrigen;
    }

    public String getMonedaDestino() {
        return monedaDestino;
    }

    public void setMonedaDestino(String monedaDestino) {
        this.monedaDestino = monedaDestino;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public double getResultado() {
        return resultado;
    }

    public void setResultado(double resultado) {
        this.resultado = resultado;
    }

    public double getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(double conversionRate) {
        this.conversionRate = conversionRate;
    }
    
    public RegistroConversion[] obtenerRegistros() throws IOException{
    	RegistroConversion[] historial;
    	Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
    	try (Reader fileReader = new FileReader("registros.json")) {
            historial = gson.fromJson(fileReader, RegistroConversion[].class);
        } catch (FileNotFoundException e) {
            historial = new RegistroConversion[0];
        }
    	return historial;
    }
    
    public RegistroConversion convertir(String codMonOrigen, String codMonDestino, int monto) {

        URI direccion_API = URI.create("https://v6.exchangerate-api.com/v6/776c5141cf7821f6ee9fe2ce/pair/"
                + codMonOrigen + "/" + codMonDestino + "/" + monto);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(direccion_API)
                .build();
        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        try (JsonReader reader = new JsonReader(new StringReader(response.body()))) {
            reader.setLenient(true);

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .create();

            ConversionResponse conversionResponse = gson.fromJson(reader, ConversionResponse.class);

            this.conversionRate = conversionResponse.getConversionRate();
            this.resultado = monto * conversionRate;

            Conversion conversion = new Conversion(conversionResponse.getMonedaOrigen(), conversionResponse.getMonedaDestino(), monto);
            conversion.setResultado(resultado);
            conversion.setConversionRate(conversionResponse.getConversionRate());

            RegistroConversion registroConversion = new RegistroConversion(conversion);

            RegistroConversion[] historial;
            try (Reader fileReader = new FileReader("registros.json")) {
                historial = gson.fromJson(fileReader, RegistroConversion[].class);
            } catch (FileNotFoundException e) {
                historial = new RegistroConversion[0];
            }

            RegistroConversion[] nuevoHistorial = new RegistroConversion[historial.length + 1];
            System.arraycopy(historial, 0, nuevoHistorial, 0, historial.length);
            nuevoHistorial[historial.length] = registroConversion;

            try (Writer fileWriter = new FileWriter("registros.json")) {
                gson.toJson(nuevoHistorial, fileWriter);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return registroConversion;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
