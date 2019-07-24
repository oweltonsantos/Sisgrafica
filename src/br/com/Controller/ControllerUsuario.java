/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Controller;

import br.com.Connection.ConnectionFactory;
import br.com.Model.ModelUsuario;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author oweltonsantos
 */
public class ControllerUsuario {

    public void create(ModelUsuario u) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO usuario (usuario,foneuser,tipo_usuario,login,senha)VALUES(?,?,?,?,?)");
            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getFoneuser());
            stmt.setString(3, u.getTipo_usuario());
            stmt.setString(4, u.getLogin());
            stmt.setString(5, u.getSenha());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

    }
    public void update(ModelUsuario u) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE usuario SET usuario = ?,foneuser = ?,tipo_usuario = ?,login = ?,senha = ? WHERE iduser = ?");
            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getFoneuser());
            stmt.setString(3, u.getTipo_usuario());
            stmt.setString(4, u.getLogin());
            stmt.setString(5, u.getSenha());
            stmt.setInt(6, u.getId());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

    }
    public void delete(ModelUsuario u) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM usuario WHERE iduser = ?");
            stmt.setInt(1, u.getId());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Excluído com sucesso!");
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

    }

    public List<ModelUsuario> read() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<ModelUsuario> users = new ArrayList<>();
         
        try {
            stmt = con.prepareStatement("SELECT * FROM usuario");
            rs = stmt.executeQuery();
          
            while (rs.next()) {
                
                ModelUsuario user = new ModelUsuario();
                user.setId(rs.getInt("iduser"));
                user.setNome(rs.getString("usuario"));
                user.setFoneuser(rs.getString("foneuser"));
                user.setTipo_usuario(rs.getString("tipo_usuario"));
                user.setLogin(rs.getString("login"));

                users.add(user);
            }
            

        } catch (SQLException ex) {
            Logger.getLogger(ControllerUsuario.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return users;

    }
    public List<ModelUsuario> readQuery(String usuario) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<ModelUsuario> users = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM usuario WHERE login = ?");
            stmt.setString(1, usuario);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ModelUsuario user = new ModelUsuario();
                user.setId(rs.getInt("iduser"));
                user.setNome(rs.getString("usuario"));
                user.setFoneuser(rs.getString("foneuser"));
                user.setTipo_usuario(rs.getString("tipo_usuario"));
                user.setLogin(rs.getString("login"));

                users.add(user);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ControllerUsuario.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return users;

    }

    public String criptSenha(String senha) {
        String senhaHash = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte messageDigest[] = md.digest(senha.getBytes("UTF-8"));

            StringBuilder sb = new StringBuilder();

            for (byte b : messageDigest) {

                sb.append(String.format("%02X", 0xFF & b));

            }

            senhaHash = sb.toString();

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(ControllerUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return senhaHash;
    }
}
