/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaLogica;

import capaDatos.clsJDBC;
import java.sql.ResultSet;
import java.util.Date;

/**
 *
 * @author IVAN
 */
public class clsUsuario {

    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs = null;

    public ResultSet listarUsuarios() throws Exception {
        strSQL = "SELECT * from listado_usuarios";
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar usuarios" + e.getMessage());
        }
    }

    public ResultSet login(String us, String pass) throws Exception {
        strSQL = "SELECT * FROM fn_login('" + us + "','" + pass + "')";
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al intentar logearse" + e.getMessage());
        }
    }

    public int validar_vigencia(int id) throws Exception {
        strSQL = "SELECT fn_validar_vigencia(" + id + ") as resultado";
        try {
            rs = objConectar.consultar(strSQL);
            if (rs.next()) {
                return rs.getInt("resultado");
            } else {
                return -1;
            }
        } catch (Exception e) {
            throw new Exception("Error al validadr vigencia" + e.getMessage());
        }
    }

    public int generarCodigoUsuario() throws Exception {
        strSQL = "select COALESCE(Max(codigo),0)+1 as codigo from usuario";
        try {
            rs = objConectar.consultar(strSQL);
            while (rs.next()) {
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar codigo de usuario: " + e.getMessage());
        }
        return 0;
    }

    public int registrarUsuario(Integer cod, Integer cpais, Integer ctipodoc, String ndoc,
            String nom, String apPat, String apMat, Boolean sex, String FechaNac, String dir,
            String cel, String f_reg, String cor, Integer ctuser, String usu, String pass, String estado,
            Integer sede) throws Exception {
        f_reg = "current_date";
        strSQL = "SELECT pa_insert_Usuario(" + cod + "," + cpais + "," + ctipodoc + ",'" + ndoc + "','"
                + nom + "','" + apPat + "','" + apMat + "'," + sex + ",'" + FechaNac + "','" + dir + "','" + cel
                + "'," + f_reg + ",'" + cor + "'," + ctuser + ",'" + usu + "','" + pass + "','" + estado + "',"
                + sede + ") " + "as resultado";
        try {
            rs = objConectar.consultar(strSQL);
            if (rs.next()) {
                return rs.getInt("resultado");
            } else {
                return -1;
            }
        } catch (Exception e) {
            throw new Exception("Error al registrar usuario: " + e.getMessage());
        }
    }

    public int modificarUsuario(Integer cod, Integer cpais, Integer ctipodoc, String ndoc,
            String nom, String apPat, String apMat, Boolean sex, String FechaNac, String dir,
            String cel, String f_reg, String cor, Integer ctuser, String usu, String estado,
            Integer sede) throws Exception {
        f_reg = "current_date";
        strSQL = "SELECT pa_update_Usuario(" + cod + "," + cpais + "," + ctipodoc + ",'" + ndoc + "','"
                + nom + "','" + apPat + "','" + apMat + "'," + sex + ",'" + FechaNac + "','" + dir + "','" + cel
                + "'," + f_reg + ",'" + cor + "'," + ctuser + ",'" + usu + "','" + estado + "',"
                + sede + ") " + "as resultado";
        try {
            rs = objConectar.consultar(strSQL);
            if (rs.next()) {
                return rs.getInt("resultado");
            } else {
                return -1;
            }
        } catch (Exception e) {
            throw new Exception("Error al modificar usuario: " + e.getMessage());
        }
    }

    public int modificarPass(Integer cod, String pass, String cpass) throws Exception {
        strSQL = "SELECT pa_update_usuario_pass(" + cod + ",'" + pass + "','" + cpass + "') as resultado";
        try {
            rs = objConectar.consultar(strSQL);
            if (rs.next()) {
                return rs.getInt("resultado");
            }
        } catch (Exception e) {
            throw new Exception("Error al modificar contraseña: " + e.getMessage());
        }
        return 0;
    }

    public ResultSet buscarUsuario(Integer cod) throws Exception {
        strSQL = "SELECT * from listado_usuarios where codigo = " + cod;
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar usuario: " + e.getMessage());
        }
    }

    public Integer buscarCodigoPersona(String nroDoc) throws Exception {
        strSQL = "SELECT * from listado_personas left join\n"
                + "usuario usu on usu.cod_persona = listado_personas.codigo\n"
                + "where usu.codigo is null and numero_documento = '" + nroDoc + "'";
        try {
            rs = objConectar.consultar(strSQL);
            if (rs.next()) {
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar persona: " + e.getMessage());
        }
        return 0;
    }

    public int registrarUsuarioExistente(Integer cod, Integer cpais, Integer ctipodoc, String ndoc,
            String nom, String apPat, String apMat, Boolean sex, String FechaNac, String dir,
            String cel, String f_reg, String cor, Integer ctuser, String usu, String pass, String estado,
            Integer sede, Boolean opc) throws Exception {
        f_reg = "current_date";
        strSQL = "SELECT pa_insert_UsuarioExistente(" + buscarCodigoPersona(ndoc) + "," + cpais + "," + ctipodoc + ",'" + ndoc + "','"
                + nom + "','" + apPat + "','" + apMat + "'," + sex + ",'" + FechaNac + "','" + dir + "','" + cel
                + "'," + f_reg + ",'" + cor + "'," + cod + "," + ctuser + ",'" + usu + "','" + pass + "','" + estado + "',"
                + sede + "," + opc + ") " + "as resultado";
        try {
            rs = objConectar.consultar(strSQL);
            if (rs.next()) {
                return rs.getInt("resultado");
            }
        } catch (Exception e) {
            throw new Exception("Error al registrar usuario existente: " + e.getMessage());
        }
        return 0;
    }

    public int eliminarUsuario(Integer cod) throws Exception {
        strSQL = "SELECT pa_delete_usuario(" + cod + ") as resultado";
        try {
            rs = objConectar.consultar(strSQL);
            if (rs.next()) {
                return rs.getInt("resultado");
            }
        } catch (Exception e) {
            throw new Exception("Error al eliminar usuario: " + e.getMessage());
        }
        return 0;
    }

    public int darBajaUsuario(Integer cod) throws Exception {
        strSQL = "SELECT pa_disabled_usuario(" + cod + ") as resultado";
        try {
            rs = objConectar.consultar(strSQL);
            if (rs.next()) {
                return rs.getInt("resultado");
            }
        } catch (Exception e) {
            throw new Exception("Error al dar baja usuario: " + e.getMessage());
        }
        return 0;
    }

    public ResultSet obtenerData(String usu) throws Exception {
        strSQL = "SELECT * from listado_usuarios where usuario ='" + usu + "'";
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al obtenr usuario" + e.getMessage());
        }
    }

}
