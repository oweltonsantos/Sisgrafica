package br.com.Model;

/**
 * Classe de relação entre a venda e o produto
 *
 * @author oweltonsantos
 */
public class ItemVenda {

    private int codigo;
    private Venda venda;
    private ModelProduto produto;
    private int quantidade;
    private Double valorUnitario;

    public ItemVenda() {
        this.codigo = 0;
        this.venda = new Venda();
        this.produto = new ModelProduto();
        this.quantidade = 0;
        this.valorUnitario = 0.0;
    }

    public ItemVenda(int codigo) {
        this.codigo = codigo;
        this.venda = new Venda();
        this.produto = new ModelProduto();
        this.quantidade = 0;
        this.valorUnitario = 0.0;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public ModelProduto getProduto() {
        return produto;
    }

    public void setProduto(ModelProduto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    @Override
    public String toString() {
        return getProduto().getNome();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ItemVenda) {
            ItemVenda iv = (ItemVenda) o;
            if (iv.getCodigo() == this.getCodigo()) {
                return true;
            }
        }
        return false;
    }
}
