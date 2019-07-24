package br.com.Controller;

import br.com.Connection.ConnectionFactory;
import br.com.Model.ModelFornecedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Classe de acesso a dados do fornecedor
 *
 * @author oweltonsantos
 */
public class ControllerFornecedor implements IDAO<ModelFornecedor> {

    @Override
    public void inserir(ModelFornecedor fornecedor) throws Exception {
        //Conexao c = new Conexao();
        ConnectionFactory conf = new ConnectionFactory();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        String sql = "INSERT INTO TBFORNECEDOR (NOME, CNPJ) VALUES (?, ?)";
        stmt = con.prepareStatement(sql);
        stmt.setString(1, fornecedor.getNome());
        stmt.setString(2, fornecedor.getCnpj());
        stmt.execute();
        conf.confirmar();
    }

    @Override
    public void alterar(ModelFornecedor fornecedor) throws Exception {
        //Conexao c = new Conexao();
        ConnectionFactory conf = new ConnectionFactory();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        String sql = "UPDATE TBFORNECEDOR SET NOME=?, CNPJ=? WHERE CODIGO=?";
        stmt = con.prepareStatement(sql);
        stmt.setString(1, fornecedor.getNome());
        stmt.setString(2, fornecedor.getCnpj());
        stmt.setInt(3, fornecedor.getCodigo());
        stmt.execute();
        conf.confirmar();
    }

    @Override
    public void excluir(ModelFornecedor fornecedor) throws Exception {
        //Conexao c = new Conexao();
        ConnectionFactory conf = new ConnectionFactory();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        String sql = "DELETE FROM TBFORNECEDOR WHERE CODIGO=?";
        stmt = con.prepareStatement(sql);
        stmt.setInt(1, fornecedor.getCodigo());
        stmt.execute();
        conf.confirmar();
    }

    @Override
    public ArrayList<ModelFornecedor> listarTodos() throws Exception {
        //Conexao c = new Conexao();
        ConnectionFactory conf = new ConnectionFactory();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        String sql = "SELECT * FROM TBFORNECEDOR ORDER BY NOME";
        stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        ArrayList listaFornecedors = new ArrayList();
        while (rs.next()) {
            ModelFornecedor fornecedor = new ModelFornecedor();
            fornecedor.setCodigo(rs.getInt("CODIGO"));
            fornecedor.setNome(rs.getString("NOME"));
            fornecedor.setCnpj(rs.getString("CNPJ"));
            listaFornecedors.add(fornecedor);
        }

        return listaFornecedors;
    }

    @Override
    public ModelFornecedor recuperar(int codigo) throws Exception {
        //Conexao c = new Conexao();
        ConnectionFactory conf = new ConnectionFactory();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        String sql = "SELECT * FROM TBFORNECEDOR WHERE CODIGO=?";
        stmt = con.prepareStatement(sql);
        stmt.setInt(1, codigo);
        ResultSet rs = stmt.executeQuery();

        ModelFornecedor fornecedor = new ModelFornecedor();
        if (rs.next()) {
            fornecedor.setCodigo(rs.getInt("CODIGO"));
            fornecedor.setNome(rs.getString("NOME"));
            fornecedor.setCnpj(rs.getString("CNPJ"));
        }

        return fornecedor;
    }
}
