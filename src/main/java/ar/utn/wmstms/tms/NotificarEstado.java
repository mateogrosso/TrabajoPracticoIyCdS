package ar.utn.wmstms.tms;

public class NotificarEstado {
    public String notificar(String codigoEnvio, EstadoEnvio estado) {
        return "El envío " + codigoEnvio + " cambió a estado: " + estado;
    }
}