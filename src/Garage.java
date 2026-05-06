import excepciones.*;
import modelos.Vehiculo;

import java.util.ArrayList;

public class Garage {
    private int capacidadMaxima;
    private ArrayList<Vehiculo> vehiculos;

    public Garage(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
        this.vehiculos = new ArrayList<>();
    }

    public int getEspacioOcupado() {
        int total = 0;
        for (Vehiculo v : vehiculos) total += v.getEspaciosOcupados();
        return total;
    }

    public int getEspacioDisponible() {
        return capacidadMaxima - getEspacioOcupado();
    }

    public void registrarIngreso(Vehiculo v)
            throws GarageLlenoException, PatenteDuplicadaException {

        for (Vehiculo x : vehiculos) {
            if (x.getPatente().equalsIgnoreCase(v.getPatente()))
                throw new PatenteDuplicadaException("La patente " + v.getPatente() + " ya está registrada.");
        }
        if (v.getEspaciosOcupados() > getEspacioDisponible())
            throw new GarageLlenoException("No hay espacio suficiente. Disponible: " + getEspacioDisponible());

        vehiculos.add(v);
    }

    public Vehiculo registrarSalida(String patente) throws VehiculoNoEncontradoException {
        for (Vehiculo v : vehiculos) {
            if (v.getPatente().equalsIgnoreCase(patente)) {
                vehiculos.remove(v);
                return v;
            }
        }
        throw new VehiculoNoEncontradoException("No se encontró el vehículo con patente: " + patente);
    }

    public ArrayList<Vehiculo> getVehiculos() { return vehiculos; }
    public int getCapacidadMaxima()           { return capacidadMaxima; }

    public String getEstado() {
        return "Capacidad total: " + capacidadMaxima +
                "\nEspacio ocupado: " + getEspacioOcupado() +
                "\nEspacio disponible: " + getEspacioDisponible();
    }

    public String getReporte() {
        int motos = 0, autos = 0, camiones = 0;
        double recaudacion = 0;
        for (Vehiculo v : vehiculos) {
            String tipo = v.getClass().getSimpleName();
            switch (tipo) {
                case "Moto":   motos++;    break;
                case "Auto":   autos++;    break;
                case "Camion": camiones++; break;
            }
            recaudacion += v.calcularCosto();
        }
        return "=== REPORTE ===" +
                "\nTotal vehículos: " + vehiculos.size() +
                "\n  Motos: " + motos +
                "\n  Autos: " + autos +
                "\n  Camiones: " + camiones +
                "\nEspacio ocupado: " + getEspacioOcupado() +
                "\nEspacio libre: " + getEspacioDisponible() +
                String.format("%nRecaudación estimada: $%.2f", recaudacion);
    }
}
