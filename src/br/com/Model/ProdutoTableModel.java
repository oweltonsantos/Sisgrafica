package br.com.Model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Classe que define o modelo para tabela contendo dados do produto
 *
 * @author oweltonsantos
 */
public class ProdutoTableModel extends AbstractTableModel {

    private String colunas[] = {"Nome", "Quantidade"};
    private List<ModelProduto> dados;

    @Override
    public int getRowCount() {
        if(dados == null){
            return 0;
        }
        return dados.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int l, int c) {
        ModelProduto produto = dados.get(l);
        switch (c) {
            case 0:
                return produto.getNome();
            case 1:
                return produto.getQuantidade();
            default:
                throw new IndexOutOfBoundsException("Coluna inexistente!");
        }
    }

    @Override
    public String getColumnName(int c) {
        return colunas[c];
    }

    @Override
    public boolean isCellEditable(int l, int c) {
        return false;
    }

    public void setDados(List<ModelProduto> dados) {
        this.dados = dados;
        fireTableDataChanged();
    }

    public ModelProduto getRowValue(int l) {
        return dados.get(l);
    }
}
