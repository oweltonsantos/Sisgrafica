package br.com.Controller;

import br.com.Connection.ConnectionFactory;
import br.com.Model.ToModelCliente;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Classe de acesso a dados do cliente
 *
 * @author oweltonsantos
 */
public class ControllerCliente implements IDAO<ToModelCliente> {
    
    @Override
    public void inserir(ToModelCliente cliente) throws Exception {
        //Conexao c = new Conexao();
        ConnectionFactory conf = new ConnectionFactory();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        //ResultSet rs = null;
        
        String sql = "INSERT INTO TBCLIENTE (NOME, CPF, DATANASCIMENTO) VALUES (?, ?, ?)";
        stmt = con.prepareStatement(sql);
        stmt.setString(1, cliente.getNome());
        stmt.setString(2, cliente.getCpf());
        stmt.setDate(3, new Date(cliente.getDataNascimento().getTime()));
        stmt.execute();
       conf.confirmar();
       
       
       
       ConnectionFactory.closeConnection(con, stmt);
       
       JOptionPane.showMessageDialog(null, "Cliente Salvo com Sucesso!");
    }

    @Override
    public void alterar(ToModelCliente cliente) throws Exception {
        //Conexao c = new Conexao();
        ConnectionFactory conf = new ConnectionFactory();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
       // ResultSet rs = null;
        
        String sql = "UPDATE TBCLIENTE SET NOME=?, CPF=?, DATANASCIMENTO=? WHERE CODIGO=?";
        stmt = con.prepareStatement(sql);
        stmt.setString(1, cliente.getNome());
        stmt.setString(2, cliente.getCpf());
        stmt.setDate(3, new Date(cliente.getDataNascimento().getTime()));
        stmt.setInt(4, cliente.getCodigo());
        stmt.execute();
        conf.confirmar();
    }

    @Override
    public void excluir(ToModelCliente cliente) throws Exception {
        //Conexao c = new Conexao();
        ConnectionFactory conf = new ConnectionFactory();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
       // ResultSet rs = null;
        String sql = "DELETE FROM TBCLIENTE WHERE CODIGO=?";
        stmt = con.prepareStatement(sql);
        stmt.setInt(1, cliente.getCodigo());
        stmt.execute();
        conf.confirmar();
    }

    @Override
    public ArrayList<ToModelCliente> listarTodos() throws Exception {
        //Conexao c = new Conexao();
        //ConnectionFactory conf = new ConnectionFactory();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        String sql = "SELECT * FROM TBCLIENTE ORDER BY NOME";
        stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        ArrayList listaClientes = new ArrayList();
        while (rs.next()) {
            ToModelCliente cliente = new ToModelCliente();
            cliente.setCodigo(rs.getInt("CODIGO"));
            cliente.setNome(rs.getString("NOME"));
            cliente.setCpf(rs.getString("CPF"));
            cliente.setDataNascimento(rs.getDate("DATANASCIMENTO"));
            listaClientes.add(cliente);
        }

        return listaClientes;
    }

    @Override
    public ToModelCliente recuperar(int codigo) throws Exception {
        //Conexao c = new Conexao();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        String sql = "SELECT * FROM TBCLIENTE WHERE CODIGO=?";
        stmt = con.prepareStatement(sql);
        stmt.setInt(1, codigo);
        ResultSet rs = stmt.executeQuery();

        ToModelCliente cliente = new ToModelCliente();
        if (rs.next()) {
            cliente.setCodigo(rs.getInt("CODIGO"));
            cliente.setNome(rs.getString("NOME"));
            cliente.setCpf(rs.getString("CPF"));
            cliente.setDataNascimento(rs.getDate("DATANASCIMENTO"));
        }

        return cliente;
    }
}
