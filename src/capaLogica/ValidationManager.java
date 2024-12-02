/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaLogica;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import capaDatos.clsJDBC;
import java.sql.ResultSet;

/**
 *
 * @author Edgar
 */
public class ValidationManager {

    public static boolean esMayorDeEdad(Date fechaNacimiento) {
        Calendar fechaActual = Calendar.getInstance();

        Calendar fechaNacimientoCal = Calendar.getInstance();
        fechaNacimientoCal.setTime(fechaNacimiento);

        int añosDiferencia = fechaActual.get(Calendar.YEAR) - fechaNacimientoCal.get(Calendar.YEAR);

        if (fechaActual.get(Calendar.DAY_OF_YEAR) < fechaNacimientoCal.get(Calendar.DAY_OF_YEAR)) {
            añosDiferencia--;
        }

        // Retornar verdadero si la diferencia es mayor o igual a 18
        return añosDiferencia >= 18;
    }

    public static boolean validarEmail(String email) {
        // Expresión regular para validar el formato de correo electrónico
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

        // Compilar la expresión regular
        Pattern pattern = Pattern.compile(emailRegex);

        // Comparar el correo con la expresión regular
        Matcher matcher = pattern.matcher(email);

        // Retornar true si coincide, false si no
        return matcher.matches();
    }

    public static boolean validarTelefonoPeru(String telefono) {
        // Expresión regular para validar números peruanos
        String regex = "^9\\d{8}$";
        return Pattern.matches(regex, telefono);
    }

    public static boolean validarExistencia(String nombreTabla,String nombrePK, int codigo) throws Exception{
        clsJDBC objConexion = new clsJDBC();
        String strSQL = "SELECT COUNT(*) FROM "+nombreTabla+" WHERE "+nombrePK+" = "+codigo;
        int cantidad = 0;
        try {
            ResultSet rs = objConexion.consultar(strSQL);
            if(rs.next()){
                cantidad = rs.getInt(1);
            }
            return cantidad > 0;
        } catch (Exception e) {
            throw new Exception("Error al validar la existencia: " + e.getMessage());
        }
    }

    public static boolean validarExistencia(String nombreTabla,String nombrePK, String codigo) throws Exception{
        clsJDBC objConexion = new clsJDBC();
        String strSQL = "SELECT COUNT(*) FROM "+nombreTabla+" WHERE "+nombrePK+" = '"+codigo+"'";
        int cantidad = 0;
        try {
            ResultSet rs = objConexion.consultar(strSQL);
            if(rs.next()){
                cantidad = rs.getInt(1);
            }
            return cantidad > 0;
        } catch (Exception e) {
            throw new Exception("Error al validar la existencia: " + e.getMessage());
        }
    }
    
}
