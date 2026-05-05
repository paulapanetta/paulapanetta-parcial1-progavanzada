package modelos;

public class Moto extends Vehiculo {
    private static final double TARIFA = 700.0;

    public Moto(String patente, String marca, String modelo, int horasEstimadas) {
        super(patente, marca, modelo, horasEstimadas);
    }
}
