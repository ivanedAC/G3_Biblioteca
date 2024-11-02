/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaLogica;
import capaDatos.clsJDBC;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;

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
            throw new Exception("Error al generar codigo de Cliente---->"+e.getMessage());
        }
        return 0;
    }
    
    public int buscarCodigoCliente(String ndoc)throws Exception{
        strSQL="select C.codigo as codigo from cliente C inner join persona P on C.cod_persona=P.codigo where P.numero_documento = '"+ndoc+"'";
        try {
            rs=objConectar.consultar(strSQL);
            while (rs.next()) {                
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar codigo de Persona---->"+e.getMessage());
        }
        return 0;
    }
    
    public int buscarCodigoPersona(String ndoc)throws Exception{
        strSQL="select codigo from persona where numero_documento = '"+ndoc+"'";
        try {
            rs=objConectar.consultar(strSQL);
            while (rs.next()) {                
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar codigo de Persona---->"+e.getMessage());
        }
        return 0;
    }
    
    public boolean buscarSexoPorNDoc(String ndoc)throws Exception{
        strSQL="select P.sexo as sexo from cliente C inner join persona P on C.cod_persona=P.codigo where P.numero_documento ='"+ndoc+"'";
        try {
            rs=objConectar.consultar(strSQL);
            while(rs.next()){
                return rs.getBoolean("sexo");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar el sexo--->"+e.getMessage());
        }
        return true;
    }
    
    public ResultSet obtenerClientePorDoc(String doc) throws Exception{
        strSQL = "select P.*, C.estado as estado from cliente C inner join persona P on C.cod_persona=P.codigo where P.numero_documento='"+doc+"'";
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
            
        } catch (Exception e) {
            throw new Exception("Error al buscar cliente-->"+e.getMessage());
        }
    }
    public ResultSet obtenerPersonaPorDoc(String doc) throws Exception{
        strSQL = "select * from persona where numero_documento='"+doc+"'";
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
            
        } catch (Exception e) {
            throw new Exception("Error al buscar persona-->"+e.getMessage());
        }
    }
    
    public ResultSet listarClientesN( ) throws Exception{
        strSQL = "select Cl.codigo, Pa.nombre as pais, TD.nombre as tipo_documento, Pe.numero_documento as numero_documento, Pe.nombres, (Pe.ape_paterno || ' ' || Pe.ape_materno) as apellidos,\n" +
"	Pe.sexo, Pe.f_nacimiento, Pe.direccion, Pe.telefono, Pe.correo, Cl.estado\n" +
"	from persona Pe    \n" +
"	inner join cliente Cl on Pe.codigo=Cl.cod_persona \n" +
"	inner join pais Pa on Pa.codigo=Pe.cod_pais\n" +
"	inner join tipo_documento TD on Pe.cod_tipo_doc=TD.codigo\n" +
"	where Pe.cod_tipo_doc !=2 \n"
                + "order by 1";
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar cliente Natural-->"+e.getMessage());
        }
    }
    
    public ResultSet listarClientesJ( ) throws Exception{
        strSQL = "select Cl.codigo, Pa.nombre as pais, TD.nombre as tipo_documento, Pe.numero_documento as numero_documento, Pe.nombres, Pe.razon_social,\n" +
"	Pe.direccion, Pe.telefono, Pe.correo, Cl.estado\n" +
"	from persona Pe    \n" +
"	inner join cliente Cl on Pe.codigo=Cl.cod_persona \n" +
"	inner join pais Pa on Pa.codigo=Pe.cod_pais\n" +
"	inner join tipo_documento TD on Pe.cod_tipo_doc=TD.codigo\n" +
"	where Pe.cod_tipo_doc =2 \n"
                + "order by 1";
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar cliente Juridico-->"+e.getMessage());
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
    
    public void insertarClienteExistente(String ndoc,String estado) throws Exception{
        int cod_per=buscarCodigoPersona(ndoc);
        strSQL="insert into cliente (cod_persona,estado)values ("+cod_per+",'"+estado+"')";
        try {
            objConectar.ejecutar(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al insertar cliente existente-->"+e.getMessage());
        }
        
    }
    
    public void insertarClienteNatural(int cpais, int ctipodoc,String ndoc,String nom,String ape_pat, String ape_mat,boolean sex,String f_nac,String dir,String cel,String f_reg,String correo,String estado) throws Exception{
        int cod=generarCodigoCliente();
        strSQL ="SELECT pa_insert_clientenatural("+cod+","+cpais+","+ctipodoc+", '"+ndoc+"' , '"+nom+"' , '"+ape_pat+"' , '"+ape_mat+"' ,"+sex+", '"+f_nac+"' , '"+dir+"', '"+cel+"', '"+f_reg+"' , '"+correo+"' ,'"+estado+"')";
        try {
            objConectar.consultar(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al insertar Cliente Natural-->"+e.getMessage());
        }
    }
    
    public void modificarClienteJuridico(int cpais, int ctipodoc, String ndoc, String nom,String rs, String dir, String cel, String f_reg,String correo,String estado)throws Exception{
       int cod=buscarCodigoCliente(ndoc);
       strSQL="select pa_update_clientejuridica("+cod+","+cpais+","+ctipodoc+", '"+ndoc+"' , '"+nom+"', '"+rs+"' , '"+dir+"' , '"+cel+"' , '"+f_reg+"' , '"+correo+"','"+estado+"')";
        try {
            objConectar.consultar(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al modificar cliente juridico-->"+e.getMessage());
        }
    }
    
    public void modificarClienteNatural(int cpais, int ctipodoc, String ndoc, String nom,String ape_pa, String ape_ma,String f_nac,String direccion, String telefono,String f_reg,String correo,String estado, boolean sex)throws Exception{
       int cod=buscarCodigoCliente(ndoc);
       strSQL="select pa_update_clientenatural("+cod+","+cpais+","+ctipodoc+",'"+ndoc+"', '"+nom+"', '"+ape_pa+"', '"+ape_ma+"', "+sex+", '"+f_nac+"', '"+direccion+"', '"+telefono+"', '"+f_reg+"','"+correo+"', '"+estado+"')";
        try {
            objConectar.consultar(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al modificar cliente natural-->"+e.getMessage());
        }
    }
    
    
    public void darBajaCliente(String ndoc) throws Exception{
        int cod=buscarCodigoCliente(ndoc);
        strSQL="select pa_disabled_cliente("+cod+")";
        try {
            objConectar.consultar(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al dar de baja cliente-->"+e.getMessage());
        }
    }
    
    public void eliminarCliente(String ndoc)throws Exception{
        int cod=buscarCodigoCliente(ndoc);
        strSQL="delete from cliente where codigo="+cod;
        try {
            objConectar.ejecutar(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al dar de baja cliente-->"+e.getMessage());
        }
    }
}
