//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import excepciones.*;
import modelos.*;

import javax.swing.*;
import java.awt.*;

public class Main {

    private static Garage garage;

    public static void main(String[] args) {
        String capStr = JOptionPane.showInputDialog(null,
                "Ingrese la capacidad máxima del garage (en espacios):",
                "Inicializar Garage", JOptionPane.QUESTION_MESSAGE);

        if (capStr == null) return;

        try {
            int cap = Integer.parseInt(capStr.trim());
            if (cap <= 0) throw new NumberFormatException();
            garage = new Garage(cap);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Capacidad inválida. El sistema se cerrará.");
            return;
        }

        String[] opciones = {
                "1. Registrar ingreso",
                "2. Registrar salida",
                "3. Listar vehículos",
                "4. Estado del garage",
                "5. Reportes",
                "6. Salir"
        };

        while (true) {
            int eleccion = JOptionPane.showOptionDialog(null,
                    "SISTEMA DE GARAGE \nCapacidad: " + garage.getCapacidadMaxima() +
                            "  |  Disponible: " + garage.getEspacioDisponible(),
                    "Menú Principal",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, opciones, opciones[0]);

            if (eleccion == JOptionPane.CLOSED_OPTION || eleccion == 5) {
                JOptionPane.showMessageDialog(null, "Hasta luego!");
                break;
            }

            switch (eleccion) {
                case 0 -> registrarIngreso();
                case 1 -> registrarSalida();
                case 2 -> listarVehiculos();
                case 3 -> mostrarEstado();
                case 4 -> mostrarReporte();
            }
        }
    }

    private static void registrarIngreso() {
        try {
            String[] tipos = {"Moto", "Auto", "Camión"};
            int tipoIdx = JOptionPane.showOptionDialog(null,
                    "Seleccione el tipo de vehículo:",
                    "Tipo de vehículo",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, tipos, tipos[0]);

            if (tipoIdx == JOptionPane.CLOSED_OPTION) return;

            String patente = pedirTexto("Ingrese la patente:", "Patente");
            if (patente == null) return;
            String marca   = pedirTexto("Ingrese la marca:", "Marca");
            if (marca == null) return;
            String modelo  = pedirTexto("Ingrese el modelo:", "Modelo");
            if (modelo == null) return;

            String horasStr = pedirTexto("Ingrese las horas estimadas de permanencia:", "Horas");
            if (horasStr == null) return;

            int horas;
            try {
                horas = Integer.parseInt(horasStr.trim());
                if (horas <= 0) throw new HorasInvalidasException("Las horas deben ser mayores a 0.");
            } catch (NumberFormatException e) {
                throw new HorasInvalidasException("Valor de horas inválido.");
            }

            Vehiculo v = switch (tipoIdx) {
                case 0 -> new Moto(patente, marca, modelo, horas);
                case 1 -> new Auto(patente, marca, modelo, horas);
                case 2 -> new Camion(patente, marca, modelo, horas);
                default -> null;
            };

            if (v == null) return;

            garage.registrarIngreso(v);

            StringBuilder sb = new StringBuilder("Vehículo registrado con éxito.\n\n");
            sb.append("Tipo: ").append(v.getClass().getSimpleName()).append("\n");
            sb.append("Patente: ").append(v.getPatente()).append("\n");
            sb.append("Marca: ").append(v.getMarca()).append("\n");
            sb.append("Modelo: ").append(v.getModelo()).append("\n");
            sb.append("Horas: ").append(v.getHorasEstimadas()).append("\n");
            sb.append("Espacios ocupados: ").append(v.getEspaciosOcupados()).append("\n");
            sb.append(String.format("Costo estimado: $%.2f", v.calcularCosto()));

            JOptionPane.showMessageDialog(null, sb.toString(), "Ingreso registrado", JOptionPane.INFORMATION_MESSAGE);

        } catch (GarageLlenoException | PatenteDuplicadaException | HorasInvalidasException e) {
            JOptionPane.showMessageDialog(null, "⚠️ " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void registrarSalida() {
        try {
            String patente = pedirTexto("Ingrese la patente del vehículo a retirar:", "Salida");
            if (patente == null) return;

            Vehiculo v = garage.registrarSalida(patente);

            String resumen = "Salida registrada.\n\n" +
                    "Tipo: " + v.getClass().getSimpleName() + "\n" +
                    "Patente: " + v.getPatente() + "\n" +
                    "Marca: " + v.getMarca() + "\n" +
                    "Modelo: " + v.getModelo() + "\n" +
                    "Horas: " + v.getHorasEstimadas() + "\n" +
                    String.format("Costo total: $%.2f", v.calcularCosto());

            JOptionPane.showMessageDialog(null, resumen, "Salida registrada", JOptionPane.INFORMATION_MESSAGE);

        } catch (VehiculoNoEncontradoException e) {
            JOptionPane.showMessageDialog(null, "⚠️ " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void listarVehiculos() {
        if (garage.getVehiculos().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay vehículos estacionados.", "Lista", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        StringBuilder sb = new StringBuilder("VEHÍCULOS ESTACIONADOS");
        for (Vehiculo v : garage.getVehiculos()) {
            sb.append("🚗 ").append(v.getClass().getSimpleName())
                    .append(" | ").append(v.getPatente())
                    .append(" | ").append(v.getMarca()).append(" ").append(v.getModelo())
                    .append(" | ").append(v.getHorasEstimadas()).append("h")
                    .append(String.format(" | $%.2f", v.calcularCosto()))
                    .append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Vehículos estacionados", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void mostrarEstado() {
        JOptionPane.showMessageDialog(null, garage.getEstado(), "Estado del Garage", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void mostrarReporte() {
        JOptionPane.showMessageDialog(null, garage.getReporte(), "Reportes", JOptionPane.INFORMATION_MESSAGE);
    }

    private static String pedirTexto(String mensaje, String titulo) {
        String valor;
        do {
            valor = JOptionPane.showInputDialog(null, mensaje, titulo, JOptionPane.QUESTION_MESSAGE);
            if (valor == null) return null;
            if (valor.trim().isEmpty())
                JOptionPane.showMessageDialog(null, "El campo no puede estar vacío.", "Validación", JOptionPane.WARNING_MESSAGE);
        } while (valor.trim().isEmpty());
        return valor.trim();
    }
}