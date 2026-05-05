package modelos;

public abstract class Vehiculo {
    protected String patente;
    protected String marca;
    protected String modelo;
    protected int horasEstimadas;

    public Vehiculo(String patente, String marca, String modelo, int horasEstimadas) {
        this.patente = patente;
        this.marca = marca;
        this.modelo = modelo;
        this.horasEstimadas = horasEstimadas;
    }

}
