/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControlViajes;
import com.toedter.calendar.IDateEvaluator;
import java.awt.Color;
import java.util.Vector;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author USUARIO
 */
public class MarcadorDeFechas implements IDateEvaluator{
    
    private Vector<Date> fechasCarga;
    private Vector<Date> fechasDescarga;

    public MarcadorDeFechas(Vector<Date> fechasCarga, Vector<Date> fechasDescarga) {
        this.fechasCarga = fechasCarga;
        this.fechasDescarga = fechasDescarga;
    }

    @Override
    public boolean isSpecial(Date date) {
        // Retorna true si la fecha es de carga o descarga
        return fechasCarga.contains(date) || fechasDescarga.contains(date);
    }

    @Override
    public Color getSpecialForegroundColor() {
        // No cambiamos el color del texto, retornamos null
        return null;
    }

    @Override
    public Color getSpecialBackroundColor() {
        // Marca con verde para fechas de carga, rojo para fechas de descarga
        if (fechasCarga.contains(fechasCarga)) {
            return Color.GREEN; // Para fechas de carga
        } else if (fechasDescarga.contains(fechasDescarga)) {
            return Color.RED; // Para fechas de descarga
        }
        return null; // Si no es especial, retornamos null
    }

    @Override
    public String getSpecialTooltip() {
        
        return null; // Sin tooltip si no es especial
    }

    @Override
    public boolean isInvalid(Date date) {
        // No queremos invalidar ninguna fecha, siempre retornamos false
        return false;
    }

    @Override
    public Color getInvalidForegroundColor() {
        return null; // No usaremos este método
    }

    @Override
    public Color getInvalidBackroundColor() {
        return null; // No invalidaremos fechas
    }

    @Override
    public String getInvalidTooltip() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
