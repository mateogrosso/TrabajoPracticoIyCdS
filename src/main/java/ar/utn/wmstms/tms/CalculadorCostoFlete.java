package ar.utn.wmstms.tms;

public class CalculadorCostoFlete {
    public double calcular(double pesoKg, double pesoVolumetricoKg) {
        return Math.max(pesoKg, pesoVolumetricoKg) * 100;
    }
}