/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaLogica;

/**
 *
 * @author ACER
 */
public class clsUsuarioSTATIC {

    public static Integer codigo = -1;
    public static String nomUsuario = "";
    public static String nombre_completo = "";
    public static String cargo = "";
    public static String sede = "";

    public static void cerrarSesion() {
        codigo = -1;
        nomUsuario = "";
        nombre_completo = "";
        cargo = "";
        sede = "";
    }
}
