/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Validacao;

/**
 *
 * @author oweltonsantos
 */
public class ValidacaoException extends Exception {

    private static final long serialVersionUID = 3387516993124229948L;

    // constr�i um objeto NumeroNegativoException com a mensagem passada por par�metro
    public ValidacaoException(String msg) {
        super(msg);
    }

    // contr�i um objeto NumeroNegativoException com mensagem e a causa dessa exce��o, utilizado para encadear exceptions
    public ValidacaoException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
