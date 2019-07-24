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
public class ValidaCampos {

    /**
     * Esta fun��o testa o campo e verifica se possui apenas numeros caso possua
     * retorna true
     */
    public static boolean validaNumerico(String s) {
        boolean t = true;
        if (!s.matches("[0-9]+")) { // equivale a express�o regular [0-9]) {
            //  throw new ValidacaoException("Digite Apenas Numeros");
            t = false;
        }
        return t;
    }

    public static boolean validaFloat(String s) {
        boolean t = false;
        try {
            s = s.replaceAll(",", ".");
            Float.parseFloat(s);
        } catch (Exception e) {
            t = true;
        } finally {
            return t;
        }
    }
    
    public static String virgulaToPonto(String s) {
        try {
            s = s.replaceAll(",", ".");
            Float.parseFloat(s);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return s;
        }
    }

}
