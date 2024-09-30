/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaLogica;
import capaDatos.clsJDBC;
import java.sql.*;

public class clsCliente {
    
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs= null;
    
    
    public int generarCodigoCliente()throws Exception{
        strSQL="select coalesce(max(codigo),0)+1 as codigo from cliente";
        try {
            rs=objConectar.consultar(strSQL);
            while (rs.next()) {                
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar codigo de marca---->"+e.getMessage());
        }
        return 0;
    }
    
    public ResultSet obtenerClientePorDoc(String doc) throws Exception{
        strSQL = "select P.* from cliente C inner join persona P on C.cod_persona=P.codigo where P.numero_documento='"+doc+"'";
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar cliente-->"+e.getMessage());
        }
    }
    
    public void insertarClienteJuridico(int cpais, int ctipodoc, String ndoc, String nom,String rs, String dir, String cel, String f_reg,String correo,String estado) throws Exception{
        int cod=generarCodigoCliente();
        strSQL = "SELECT pa_insert_clientejuridica("+cod+","+cpais+","+ctipodoc+",'"+ndoc+"' , '"+nom+"', '"+rs+"', '"+dir+"', '"+cel+"' , '"+f_reg+"', '"+correo+"' , '"+estado+"')";
        try {
            objConectar.consultar(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al insertar Cliente Juridico-->"+e.getMessage());
        }
    }
    
    public void insertarClienteNatural(int cpais, int ctipodoc,String ndoc,String nom,String ape_pat, String ape_mat,boolean sex,String f_nac,String dir,String cel,String f_reg,String correo,String estado) throws Exception{
        int cod=generarCodigoCliente();
        strSQL ="SELECT pa_insert_clientenatural("+cod+","+cpais+","+ctipodoc+", '"+ndoc+"' , '"+nom+"' , '"+ape_pat+"' , '"+ape_mat+"' ,"+sex+", '"+f_nac+"' ,"+dir+","+cel+","+f_reg+","+correo+","+estado+")";
        try {
            objConectar.ejecutar(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al insertar Cliente Natural-->"+e.getMessage());
        }
    }
}
