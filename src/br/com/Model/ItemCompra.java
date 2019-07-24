package br.com.Model;

/**
 * Classe de relação entre a compra e o produto
 *
 * @author oweltonsantos
 */
public class ItemCompra {

    private int codigo;
    private ModelCompra compra;
    private ModelProduto produto;
    private int quantidade;
    private Double valorUnitario;

    public ItemCompra() {
        this.codigo = 0;
        this.compra = new ModelCompra();
        this.produto = new ModelProduto();
        this.quantidade = 0;
        this.valorUnitario = 0.0;
    }

    public ItemCompra(int codigo) {
        this.codigo = codigo;
        this.compra = new ModelCompra();
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

    public ModelCompra getCompra() {
        return compra;
    }

    public void setCompra(ModelCompra compra) {
        this.compra = compra;
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
        if (o instanceof ItemCompra) {
            ItemCompra iv = (ItemCompra) o;
            if (iv.getCodigo() == this.getCodigo()) {
                return true;
            }
        }
        return false;
    }
}
