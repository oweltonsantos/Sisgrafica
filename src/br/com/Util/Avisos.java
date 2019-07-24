/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Util;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author EJunior
 */
public class Avisos {
    public static void imprimeException(Component p,String e){
        JOptionPane.showMessageDialog(p, e,"Erro do Sistema",JOptionPane.ERROR_MESSAGE);
    }
    public static void imprimeAviso(Component p,String e){
        JOptionPane.showMessageDialog(p, e,"Aviso do Sistema",JOptionPane.INFORMATION_MESSAGE);
    }
    public static void imprimeConfirmacao(Component p, String e) {
        JOptionPane.showMessageDialog(p, e,"Aviso do Sistema",JOptionPane.PLAIN_MESSAGE);
        
    }
}
