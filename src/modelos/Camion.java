package modelos;

public class Camion extends Vehiculo {
    private static final double TARIFA = 1500.0;

    public Camion(String patente, String marca, String modelo, int horasEstimadas) {
        super(patente, marca, modelo, horasEstimadas);
    }
    @Override
    public int getEspaciosOcupados() { return 4; }

    @Override
    public double calcularCosto() { return horasEstimadas * TARIFA; }
}
