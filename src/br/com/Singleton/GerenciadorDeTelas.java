/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Singleton;

import br.com.View.BuscaCliente;
import br.com.View.BuscaFornecedor;
import br.com.View.BuscaProduto;
import br.com.View.CadastroCliente;
import br.com.View.CadastroFornecedor;
import br.com.View.CadastroProduto;
import br.com.View.LancamentoCompra;
import br.com.View.LancamentoVenda;
import br.com.View.telaCadastroCliente;
import br.com.View.telaCadastroUsuario;
import br.com.View.telaEditarUsuario;
import br.com.View.telaPrincipal;
import br.com.View.telaUsuario;

/**
 *
 * @author oweltonsantos
 */
public class GerenciadorDeTelas {

    private static final GerenciadorDeTelas instanse = new GerenciadorDeTelas();

    public static GerenciadorDeTelas getInstanse() {
        return instanse;
    }

    private telaPrincipal telaPrincipal = null;
    private telaCadastroCliente telaCadastroCliente = null;
    private telaUsuario telaUsuario = null;
    private telaCadastroUsuario telaCadastroUsuario = null;
    private telaEditarUsuario telaEditarUsuario = null;
    private BuscaCliente buscaCliente = null;
    private BuscaFornecedor buscaFornecedor = null;
    private BuscaProduto buscaProduto = null;
    private CadastroCliente cadastroCliente = null;
    private CadastroFornecedor cadastroFornecedor = null;
    private CadastroProduto cadastroProduto = null;
    private LancamentoCompra lancamentoCompra = null;
    private LancamentoVenda lancamentoVenda = null;
    
     public CadastroCliente getcadastroCliente() {
        if(cadastroCliente == null){
            cadastroCliente = new CadastroCliente();
        }
        return cadastroCliente;
    }
//     public BuscaProduto getbuscaProduto() {
//        if(buscaProduto == null){
//            buscaProduto = new BuscaProduto();
//        }
//        return buscaProduto;
//    }
//     public BuscaFornecedor getbuscaFornecedor() {
//        if(buscaFornecedor == null){
//            buscaFornecedor = new BuscaFornecedor();
//        }
//        return buscaFornecedor;
//    }
//     public BuscaCliente getbuscaCliente() {
//        if(buscaCliente == null){
//            buscaCliente = new BuscaCliente();
//        }
//        return buscaCliente;
//    }
     public telaEditarUsuario getTelaEditarUsuario() {
        if(telaEditarUsuario == null){
            telaEditarUsuario = new telaEditarUsuario();
        }
        return telaEditarUsuario;
    }
     public telaCadastroCliente getTelaCadastroCliente() {
        if(telaCadastroCliente == null){
            telaCadastroCliente = new telaCadastroCliente();
        }
        return telaCadastroCliente;
    }
     public telaPrincipal getTelaPrincipal() {
        if(telaPrincipal == null){
            telaPrincipal = new telaPrincipal();
        }
        return telaPrincipal;
    }
     public telaUsuario getTelaUsuario() {
        if(telaUsuario == null){
            telaUsuario = new telaUsuario();
        }
        return telaUsuario;
    }
     public telaCadastroUsuario getTelaCadastroUsuario() {
        if(telaCadastroUsuario == null){
            telaCadastroUsuario = new telaCadastroUsuario();
        }
        return telaCadastroUsuario;
    }

}
