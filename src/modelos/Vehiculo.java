package modelos;

import interfaces.Calculable;
import interfaces.Mostrable;

public abstract class Vehiculo implements Calculable, Mostrable {
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

    public abstract int getEspaciosOcupados();

    public String getPatente() { return patente; }
    public String getMarca()   { return marca; }
    public String getModelo()  { return modelo; }
    public int getHorasEstimadas() { return horasEstimadas; }

    @Override
    public void mostrarDatos() {
        System.out.println("Tipo: " + getClass().getSimpleName());
        System.out.println("Patente: " + patente);
        System.out.println("Marca: " + marca);
        System.out.println("Modelo: " + modelo);
        System.out.println("Horas estimadas: " + horasEstimadas);
        System.out.println("Espacios ocupados: " + getEspaciosOcupados());
        System.out.printf("Costo estimado: $%.2f%n", calcularCosto());
    }
}
