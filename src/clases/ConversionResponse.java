package clases;

import com.google.gson.annotations.SerializedName;

public class ConversionResponse {
	@SerializedName("conversion_rate")
    private double conversionRate;

    @SerializedName("conversion_result")
    private double resultado;
	
    @SerializedName("base_code")
    private String monedaOrigen;

    @SerializedName("target_code")
    private String monedaDestino;

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

    public double getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(double conversionRate) {
        this.conversionRate = conversionRate;
    }

    public double getResultado() {
        return resultado;
    }

    public void setResultado(double resultado) {
        this.resultado = resultado;
    }
}
