package clases;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegistroConversion {

    private Conversion conversion;
    private LocalDateTime timestamp;

    public RegistroConversion(Conversion conversion) {
        this.conversion = conversion;
        this.timestamp = LocalDateTime.now();
    }

    public Conversion getConversion() {
        return conversion;
    }

    public void setConversion(Conversion conversion) {
        this.conversion = conversion;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "Registro de Conversión: {" +
                "\n  Moneda de Origen: " + conversion.getMonedaOrigen() +
                "\n  Moneda de Destino: " + conversion.getMonedaDestino() +
                "\n  Monto: " + conversion.getMonto() +
                "\n  Conversión: " + conversion.getConversionRate() +
                "\n  Resultado: " + conversion.getResultado() +
                "\n  Fecha consulta: " + timestamp.format(formatter) +
                "\n}";
    }
}
