package modelos;

public class Auto extends Vehiculo {
    private static final double TARIFA = 1000.0;

    public Auto(String patente, String marca, String modelo, int horasEstimadas) {
        super(patente, marca, modelo, horasEstimadas);
    }
    @Override
    public int getEspaciosOcupados() { return 2; }

    @Override
    public double calcularCosto() { return horasEstimadas * TARIFA; }
}
