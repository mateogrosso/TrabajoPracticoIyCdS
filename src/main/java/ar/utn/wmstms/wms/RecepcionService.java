package ar.utn.wmstms.wms;

public class RecepcionService {
    public int registrarRecepcion(String producto, int cantidad) {
        if (cantidad <= 0) {
           throw new IllegalArgumentException("La cantidad recibida debe ser mayor a 0 (se recibió: " + cantidad + ")");
        }
        return cantidad;
    }
}