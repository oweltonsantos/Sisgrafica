package br.com.Controller;

import br.com.Connection.ConnectionFactory;
import br.com.Model.ModelProduto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Classe de acesso a dados do produto
 *
 * @author oweltonsantos
 */
public class ControllerProduto implements IDAO<ModelProduto> {

    @Override
    public void inserir(ModelProduto produto) throws Exception {
        //Conexao c = new Conexao();
        ConnectionFactory conf = new ConnectionFactory();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        String sql = "INSERT INTO TBPRODUTO (NOME, PRECOCOMPRA, PRECOVENDA, QUANTIDADEESTOQUE) VALUES (?, ?, ?, 0)";
        stmt = con.prepareStatement(sql);
        stmt.setString(1, produto.getNome());
        stmt.setDouble(2, produto.getPrecoCompra());
        stmt.setDouble(3, produto.getPrecoVenda());
        stmt.execute();
        conf.confirmar();
    }

    @Override
    public void alterar(ModelProduto produto) throws Exception {
        //Conexao c = new Conexao();
        ConnectionFactory conf = new ConnectionFactory();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        String sql = "UPDATE TBPRODUTO SET NOME=?, PRECOCOMPRA=?, PRECOVENDA=? WHERE CODIGO=?";
        stmt = con.prepareStatement(sql);
        stmt.setString(1, produto.getNome());
        stmt.setDouble(2, produto.getPrecoCompra());
        stmt.setDouble(3, produto.getPrecoVenda());
        stmt.setInt(4, produto.getCodigo());
        stmt.execute();
        conf.confirmar();
    }

    public void entradaEstoque(ConnectionFactory con, int codigo, int quantidade) throws Exception {
        Connection connetc = con.getConnection();
        PreparedStatement stmt = null;
        String sql = "UPDATE TBPRODUTO SET QUANTIDADEESTOQUE= QUANTIDADEESTOQUE  + ? WHERE CODIGO=?";
        stmt = connetc.prepareStatement(sql);
        stmt.setInt(1, quantidade);
        stmt.setInt(2, codigo);
        stmt.execute();
    }

    public void saidaEstoque(ConnectionFactory con, int codigo, int quantidade) throws Exception {
        Connection connetc = con.getConnection();
        PreparedStatement stmt = null;
        String sql = "UPDATE TBPRODUTO SET QUANTIDADEESTOQUE= QUANTIDADEESTOQUE  - ? WHERE CODIGO=?";
        stmt = connetc.prepareStatement(sql);
        stmt.setInt(1, quantidade);
        stmt.setInt(2, codigo);
        stmt.execute();
    }

    @Override
    public void excluir(ModelProduto produto) throws Exception {
        //Conexao c = new Conexao();
        ConnectionFactory conf = new ConnectionFactory();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        String sql = "DELETE FROM TBPRODUTO WHERE CODIGO=?";
        stmt = con.prepareStatement(sql);
        stmt.setInt(1, produto.getCodigo());
        stmt.execute();
        conf.confirmar();
    }

    @Override
    public ArrayList<ModelProduto> listarTodos() throws Exception {
        //Conexao c = new Conexao();
        //ConnectionFactory conf = new ConnectionFactory();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        String sql = "SELECT * FROM TBPRODUTO ORDER BY NOME";
        stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        ArrayList listaProdutos = new ArrayList();
        while (rs.next()) {
            ModelProduto produto = new ModelProduto();
            produto.setCodigo(rs.getInt("CODIGO"));
            produto.setNome(rs.getString("NOME"));
            produto.setPrecoCompra(rs.getDouble("PRECOCOMPRA"));
            produto.setPrecoVenda(rs.getDouble("PRECOVENDA"));
            produto.setQuantidade(rs.getInt("QUANTIDADEESTOQUE"));
            listaProdutos.add(produto);
        }

        return listaProdutos;
    }

    @Override
    public ModelProduto recuperar(int codigo) throws Exception {
        //Conexao c = new Conexao();
        ConnectionFactory conf = new ConnectionFactory();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        String sql = "SELECT * FROM TBPRODUTO WHERE CODIGO=?";
        stmt = con.prepareStatement(sql);
        stmt.setInt(1, codigo);
        ResultSet rs = stmt.executeQuery();

        ModelProduto produto = new ModelProduto();
        if (rs.next()) {
            produto.setCodigo(rs.getInt("CODIGO"));
            produto.setNome(rs.getString("NOME"));
            produto.setPrecoCompra(rs.getDouble("PRECOCOMPRA"));
            produto.setPrecoVenda(rs.getDouble("PRECOVENDA"));
            produto.setQuantidade(rs.getInt("QUANTIDADEESTOQUE"));
        }

        return produto;
    }
}
