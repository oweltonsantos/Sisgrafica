package br.com.Controller;

import br.com.Connection.ConnectionFactory;
import br.com.Model.ItemVenda;
import br.com.Model.Venda;
import br.com.Util.Situacao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Classe de acesso a dados da venda
 *
 * @author oweltonsantos
 */
public class ControllerVenda implements IDAO<Venda> {

    @Override
    public void inserir(Venda venda) throws Exception {
        //Conexao c = new Conexao();
        ConnectionFactory conf = new ConnectionFactory();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
                
        String sql = "INSERT INTO TBVENDA (CODIGOCLIENTE, DATAVENDA, VALORTOTAL, SITUACAO) VALUES (?, ?, ?, ?)";
        stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        //PreparedStatement ps = c.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, venda.getCliente().getCodigo());
        stmt.setDate(2, new Date(venda.getDataVenda().getTime()));
        stmt.setDouble(3, venda.getValorTotal());
        stmt.setInt(4, venda.getSituacao().getId());
        stmt.execute();

        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();
        int idVenda = rs.getInt(1);

        for (ItemVenda iv : venda.getItens()) {
            sql = "INSERT INTO TBITEMVENDA (CODIGOPRODUTO, CODIGOVENDA, QUANTIDADE, VALORUNITARIO) VALUES (?, ?, ?, ?)";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, iv.getProduto().getCodigo());
            stmt.setInt(2, idVenda);
            stmt.setInt(3, iv.getQuantidade());
            stmt.setDouble(4, iv.getValorUnitario());
            stmt.execute();
            
            if (venda.getSituacao() == Situacao.FINALIZADA) {
                
                ControllerProduto produtoDAO = new ControllerProduto();
                produtoDAO.saidaEstoque(conf, iv.getProduto().getCodigo(), iv.getQuantidade());
            }
        }
        conf.confirmar();
    }

    @Override
    public void alterar(Venda venda) throws Exception {
        //Conexao c = new Conexao();
        ConnectionFactory conf = new ConnectionFactory();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        String sql = "UPDATE TBVENDA SET CODIGOCLIENTE=?, DATAVENDA=?, VALORTOTAL=?, SITUACAO=? WHERE CODIGO=?";
        //PreparedStatement ps = c.getConexao().prepareStatement(sql);
        stmt = con.prepareStatement(sql);
        stmt.setInt(1, venda.getCliente().getCodigo());
        stmt.setDate(2, new Date(venda.getDataVenda().getTime()));
        stmt.setDouble(3, venda.getValorTotal());
        stmt.setInt(4, venda.getSituacao().getId());
        stmt.setInt(5, venda.getCodigo());
        stmt.execute();

        for (ItemVenda iv : venda.getItensRemover()) {
            sql = "DELETE FROM TBITEMVENDA WHERE CODIGO=?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, iv.getCodigo());
            stmt.execute();
        }

        for (ItemVenda iv : venda.getItens()) {
            if (iv.getCodigo() == 0) {
                sql = "INSERT INTO TBITEMVENDA (CODIGOPRODUTO, CODIGOVENDA, QUANTIDADE, VALORUNITARIO) VALUES (?, ?, ?, ?)";
                stmt = con.prepareStatement(sql);
                stmt.setInt(1, iv.getProduto().getCodigo());
                stmt.setInt(2, iv.getVenda().getCodigo());
                stmt.setInt(3, iv.getQuantidade());
                stmt.setDouble(4, iv.getValorUnitario());
                stmt.execute();
            } else {
                sql = "UPDATE TBITEMVENDA SET CODIGOPRODUTO=?, CODIGOVENDA=?, QUANTIDADE=?, VALORUNITARIO=? WHERE CODIGO=?";
                stmt = con.prepareStatement(sql);
                stmt.setInt(1, iv.getProduto().getCodigo());
                stmt.setInt(2, iv.getVenda().getCodigo());
                stmt.setInt(3, iv.getQuantidade());
                stmt.setDouble(4, iv.getValorUnitario());
                stmt.setInt(5, iv.getCodigo());
                stmt.execute();
            }

            if (venda.getSituacao() == Situacao.FINALIZADA) {
                ControllerProduto produtoDAO = new ControllerProduto();
                produtoDAO.saidaEstoque(conf, iv.getProduto().getCodigo(), iv.getQuantidade());
            }
        }

        conf.confirmar();
    }

    @Override
    public void excluir(Venda venda) throws Exception {
        //Conexao c = new Conexao();
        ConnectionFactory conf = new ConnectionFactory();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        String sql = "UPDATE TBVENDA SET CODIGOCLIENTE=?, DATAVENDA=?, VALORTOTAL=?, SITUACAO=? WHERE CODIGO=?";
        stmt = con.prepareStatement(sql);
        stmt.setInt(1, venda.getCliente().getCodigo());
        stmt.setDate(2, new Date(venda.getDataVenda().getTime()));
        stmt.setDouble(3, venda.getValorTotal());
        stmt.setInt(4, Situacao.CANCELADA.getId());
        stmt.setInt(5, venda.getCodigo());
        stmt.execute();
        conf.confirmar();
    }

    @Override
    public ArrayList<Venda> listarTodos() throws Exception {
        //Conexao c = new Conexao();
       // ConnectionFactory conf = new ConnectionFactory();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        ControllerCliente clienteDAO = new ControllerCliente();
        ControllerProduto produtoDAO = new ControllerProduto();

        String sql = "SELECT * FROM TBVENDA ORDER BY DATAVENDA DESC";
        stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        ArrayList listaVendas = new ArrayList();
        while (rs.next()) {
            Venda venda = new Venda();
            venda.setCodigo(rs.getInt("CODIGO"));
            venda.setCliente(clienteDAO.recuperar(rs.getInt("CODIGOCLIENTE")));
            venda.setDataVenda(rs.getDate("DATAVENDA"));
            venda.setSituacao(rs.getInt("SITUACAO"));

            String sqlItem = "SELECT * FROM TBITEMVENDA WHERE CODIGOVENDA=?";
           PreparedStatement psItem = con.prepareStatement(sqlItem);
            //PreparedStatement psItem = c.getConexao().prepareStatement(sqlItem);
            psItem.setInt(1, venda.getCodigo());
            ResultSet rsItem = psItem.executeQuery();

            while (rsItem.next()) {
                ItemVenda iv = new ItemVenda();
                iv.setCodigo(rsItem.getInt("CODIGO"));
                iv.setProduto(produtoDAO.recuperar(rsItem.getInt("CODIGOPRODUTO")));
                iv.setVenda(venda);
                iv.setQuantidade(rsItem.getInt("QUANTIDADE"));
                iv.setValorUnitario(rsItem.getDouble("VALORUNITARIO"));
                venda.addItem(iv);
            }

            listaVendas.add(venda);
        }

        return listaVendas;
    }

    @Override
    public Venda recuperar(int codigo) throws Exception {
        //Conexao c = new Conexao();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ControllerCliente clienteDAO = new ControllerCliente();
        ControllerProduto produtoDAO = new ControllerProduto();

        String sql = "SELECT * FROM TBVENDA ORDER BY DATAVENDA DESC";
        stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        Venda venda = new Venda();
        if (rs.next()) {
            venda.setCodigo(rs.getInt("CODIGO"));
            venda.setCliente(clienteDAO.recuperar(rs.getInt("CODIGOCLIENTE")));
            venda.setDataVenda(rs.getDate("DATAVENDA"));
            venda.setSituacao(rs.getInt("SITUACAO"));

            String sqlItem = "SELECT * FROM TBITEMVENDA WHERE CODIGOVENDA=?";
            PreparedStatement psItem = con.prepareStatement(sqlItem);
            //PreparedStatement psItem = c.getConexao().prepareStatement(sqlItem);
            psItem.setInt(1, venda.getCodigo());
            ResultSet rsItem = psItem.executeQuery();

            while (rsItem.next()) {
                ItemVenda iv = new ItemVenda();
                iv.setCodigo(rsItem.getInt("CODIGO"));
                iv.setProduto(produtoDAO.recuperar(rsItem.getInt("CODIGOPRODUTO")));
                iv.setVenda(venda);
                iv.setQuantidade(rsItem.getInt("QUANTIDADE"));
                iv.setValorUnitario(rsItem.getDouble("VALORUNITARIO"));
                venda.addItem(iv);
            }
        }

        return venda;
    }
}
