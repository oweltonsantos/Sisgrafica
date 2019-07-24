package br.com.Controller;

import br.com.Connection.ConnectionFactory;
import br.com.Model.ModelCompra;
import br.com.Model.ItemCompra;
import br.com.Util.Situacao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Classe de acesso a dados da compra
 *
 * @author oweltonsantos
 */
public class ControllerCompra implements IDAO<ModelCompra> {

    @Override
    public void inserir(ModelCompra compra) throws Exception {
        //Conexao c = new Conexao();
        ConnectionFactory conf = new ConnectionFactory();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        String sql = "INSERT INTO TBCOMPRA (CODIGOFORNECEDOR, DATACOMPRA, VALORTOTAL, SITUACAO) VALUES (?, ?, ?, ?)";
        stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, compra.getFornecedor().getCodigo());
        stmt.setDate(2, new Date(compra.getDataCompra().getTime()));
        stmt.setDouble(3, compra.getValorTotal());
        stmt.setInt(4, compra.getSituacao().getId());
        stmt.execute();

        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();
        int idCompra = rs.getInt(1);

        for (ItemCompra iv : compra.getItens()) {
            sql = "INSERT INTO TBITEMCOMPRA (CODIGOPRODUTO, CODIGOCOMPRA, QUANTIDADE, VALORUNITARIO) VALUES (?, ?, ?, ?)";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, iv.getProduto().getCodigo());
            stmt.setInt(2, idCompra);
            stmt.setInt(3, iv.getQuantidade());
            stmt.setDouble(4, iv.getValorUnitario());
            stmt.execute();

            if (compra.getSituacao() == Situacao.FINALIZADA) {
                ControllerProduto produtoDAO = new ControllerProduto();
                produtoDAO.entradaEstoque(conf, iv.getProduto().getCodigo(), iv.getQuantidade());
            }
        }
        conf.confirmar();
    }

    @Override
    public void alterar(ModelCompra compra) throws Exception {
        //Conexao c = new Conexao();
        ConnectionFactory conf = new ConnectionFactory();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        String sql = "UPDATE TBCOMPRA SET CODIGOFORNECEDOR=?, DATACOMPRA=?, VALORTOTAL=?, SITUACAO=? WHERE CODIGO=?";
        stmt = con.prepareStatement(sql);
        stmt.setInt(1, compra.getFornecedor().getCodigo());
        stmt.setDate(2, new Date(compra.getDataCompra().getTime()));
        stmt.setDouble(3, compra.getValorTotal());
        stmt.setInt(4, compra.getSituacao().getId());
        stmt.setInt(5, compra.getCodigo());
        stmt.execute();

        for (ItemCompra iv : compra.getItensRemover()) {
            sql = "DELETE FROM TBITEMCOMPRA WHERE CODIGO=?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, iv.getCodigo());
            stmt.execute();
        }

        for (ItemCompra iv : compra.getItens()) {
            if (iv.getCodigo() == 0) {
                sql = "INSERT INTO TBITEMCOMPRA (CODIGOPRODUTO, CODIGOCOMPRA, QUANTIDADE, VALORUNITARIO) VALUES (?, ?, ?, ?)";
                stmt = con.prepareStatement(sql);
                stmt.setInt(1, iv.getProduto().getCodigo());
                stmt.setInt(2, iv.getCompra().getCodigo());
                stmt.setInt(3, iv.getQuantidade());
                stmt.setDouble(4, iv.getValorUnitario());
                stmt.execute();
            } else {
                sql = "UPDATE TBITEMCOMPRA SET CODIGOPRODUTO=?, CODIGOCOMPRA=?, QUANTIDADE=?, VALORUNITARIO=? WHERE CODIGO=?";
                stmt = con.prepareStatement(sql);
                stmt.setInt(1, iv.getProduto().getCodigo());
                stmt.setInt(2, iv.getCompra().getCodigo());
                stmt.setInt(3, iv.getQuantidade());
                stmt.setDouble(4, iv.getValorUnitario());
                stmt.setInt(5, iv.getCodigo());
                stmt.execute();
            }

            if (compra.getSituacao() == Situacao.FINALIZADA) {
                ControllerProduto produtoDAO = new ControllerProduto();
                produtoDAO.entradaEstoque(conf, iv.getProduto().getCodigo(), iv.getQuantidade());
            }
        }

        conf.confirmar();
    }

    @Override
    public void excluir(ModelCompra compra) throws Exception {
        //Conexao c = new Conexao();
        ConnectionFactory conf = new ConnectionFactory();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        String sql = "UPDATE TBCOMPRA SET CODIGOFORNECEDOR=?, DATACOMPRA=?, VALORTOTAL=?, SITUACAO=? WHERE CODIGO=?";
        stmt = con.prepareStatement(sql);
        stmt.setInt(1, compra.getFornecedor().getCodigo());
        stmt.setDate(2, new Date(compra.getDataCompra().getTime()));
        stmt.setDouble(3, compra.getValorTotal());
        stmt.setInt(4, Situacao.CANCELADA.getId());
        stmt.setInt(5, compra.getCodigo());
        stmt.execute();
        conf.confirmar();
    }

    @Override
    public ArrayList<ModelCompra> listarTodos() throws Exception {
        //Conexao c = new Conexao();
        ConnectionFactory conf = new ConnectionFactory();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ControllerFornecedor fornecedorDAO = new ControllerFornecedor();
        ControllerProduto produtoDAO = new ControllerProduto();

        String sql = "SELECT * FROM TBCOMPRA ORDER BY DATACOMPRA DESC";
        stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        ArrayList listaCompras = new ArrayList();
        while (rs.next()) {
            ModelCompra compra = new ModelCompra();
            compra.setCodigo(rs.getInt("CODIGO"));
            compra.setFornecedor(fornecedorDAO.recuperar(rs.getInt("CODIGOFORNECEDOR")));
            compra.setDataCompra(rs.getDate("DATACOMPRA"));
            compra.setSituacao(rs.getInt("SITUACAO"));

            String sqlItem = "SELECT * FROM TBITEMCOMPRA WHERE CODIGOCOMPRA=?";
            //PreparedStatement psItem = c.getConexao().prepareStatement(sqlItem);
            PreparedStatement psItem = con.prepareStatement(sqlItem);
            psItem.setInt(1, compra.getCodigo());
            ResultSet rsItem = psItem.executeQuery();

            while (rsItem.next()) {
                ItemCompra iv = new ItemCompra();
                iv.setCodigo(rsItem.getInt("CODIGO"));
                iv.setProduto(produtoDAO.recuperar(rsItem.getInt("CODIGOPRODUTO")));
                iv.setCompra(compra);
                iv.setQuantidade(rsItem.getInt("QUANTIDADE"));
                iv.setValorUnitario(rsItem.getDouble("VALORUNITARIO"));
                compra.addItem(iv);
            }

            listaCompras.add(compra);
        }

        return listaCompras;
    }

    @Override
    public ModelCompra recuperar(int codigo) throws Exception {
        //Conexao c = new Conexao();
        ConnectionFactory conf = new ConnectionFactory();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ControllerFornecedor fornecedorDAO = new ControllerFornecedor();
        ControllerProduto produtoDAO = new ControllerProduto();

        String sql = "SELECT * FROM TBCOMPRA ORDER BY DATACOMPRA DESC";
        stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        ModelCompra compra = new ModelCompra();
        if (rs.next()) {
            compra.setCodigo(rs.getInt("CODIGO"));
            compra.setFornecedor(fornecedorDAO.recuperar(rs.getInt("CODIGOFORNECEDOR")));
            compra.setDataCompra(rs.getDate("DATACOMPRA"));
            compra.setSituacao(rs.getInt("SITUACAO"));

            String sqlItem = "SELECT * FROM TBITEMCOMPRA WHERE CODIGOCOMPRA=?";
            //PreparedStatement psItem = c.getConexao().prepareStatement(sqlItem);
            PreparedStatement psItem = con.prepareStatement(sqlItem);
            psItem.setInt(1, compra.getCodigo());
            ResultSet rsItem = psItem.executeQuery();

            while (rsItem.next()) {
                ItemCompra iv = new ItemCompra();
                iv.setCodigo(rsItem.getInt("CODIGO"));
                iv.setProduto(produtoDAO.recuperar(rsItem.getInt("CODIGOPRODUTO")));
                iv.setCompra(compra);
                iv.setQuantidade(rsItem.getInt("QUANTIDADE"));
                iv.setValorUnitario(rsItem.getDouble("VALORUNITARIO"));
                compra.addItem(iv);
            }
        }

        return compra;
    }
}
