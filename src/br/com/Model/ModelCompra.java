package br.com.Model;

import br.com.Util.Situacao;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Classe contendo os dados da compra
 *
 * @author Juliano
 */
public class ModelCompra {

    private int codigo;
    private ModelFornecedor fornecedor;
    private Date dataCompra;
    private Double valorTotal;
    private Situacao situacao;
    private List<ItemCompra> itens;
    private List<ItemCompra> itensRemover;

    public ModelCompra() {
        this.codigo = 0;
        this.fornecedor = new ModelFornecedor();
        this.dataCompra = new Date();
        this.valorTotal = 0.0;
        this.itens = new ArrayList<>();
        this.itensRemover = new ArrayList<>();
    }

    public ModelCompra(int codigo) {
        this.codigo = codigo;
        this.fornecedor = new ModelFornecedor();
        this.dataCompra = new Date();
        this.valorTotal = 0.0;
        this.itens = new ArrayList<>();
        this.itensRemover = new ArrayList<>();

    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public ModelFornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(ModelFornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public Double getValorTotal() {
        double total = 0;
        for (ItemCompra iv : itens) {
            total += (iv.getValorUnitario() * iv.getQuantidade());
        }
        return total;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public void setSituacao(int situacao) {
        if (situacao == Situacao.ABERTA.getId()) {
            setSituacao(Situacao.ABERTA);
        } else if (situacao == Situacao.FINALIZADA.getId()) {
            setSituacao(Situacao.FINALIZADA);
        } else if (situacao == Situacao.CANCELADA.getId()) {
            setSituacao(Situacao.CANCELADA);
        }
    }

    public List<ItemCompra> getItens() {
        return itens;
    }

    public List<ItemCompra> getItensRemover() {
        return itensRemover;
    }

    public void addItem(ItemCompra itemCompra) {
        itens.add(itemCompra);
    }

    public void removeItem(ItemCompra itemCompra) {
        itens.remove(itemCompra);
        if (itemCompra.getCodigo() != 0) {
            itensRemover.add(itemCompra);
        }
    }

    public int quantidadeItens() {
        return itens.size();
    }

    @Override
    public String toString() {
        return String.valueOf(this.codigo);
    }

}
