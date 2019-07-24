package br.com.Model;

/**
 * Classe contendo os dados do produto
 *
 * @author oweltonsantos
 */
public class ModelProduto {

    private int codigo;
    private String nome;
    private Double precoCompra;
    private Double precoVenda;
    private int quantidade;

    public ModelProduto() {
        this.codigo = 0;
        this.nome = "";
        this.precoCompra = 0.0;
        this.precoVenda = 0.0;
        this.quantidade = 0;
    }

    public ModelProduto(int codProduto) {
        this.codigo = codProduto;
        this.nome = "";
        this.precoCompra = 0.0;
        this.precoVenda = 0.0;
        this.quantidade = 0;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(Double precoCompra) {
        this.precoCompra = precoCompra;
    }

    public Double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(Double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return getNome();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ModelProduto) {
            ModelProduto p = (ModelProduto) o;
            if (p.getCodigo() == this.getCodigo()) {
                return true;
            }
        }
        return false;
    }
}
