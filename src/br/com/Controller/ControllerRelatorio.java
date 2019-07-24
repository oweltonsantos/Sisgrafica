/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Controller;

import br.com.Connection.ConnectionFactory;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
        

/**
 *
 * @author santos
 */
public class ControllerRelatorio {
    
    public void getRelatorioProduto(){
    Connection con = ConnectionFactory.getConnection();
    
    String src = "Produtos.jasper";
    JasperPrint jasperPrint = null;
        try {
            jasperPrint = JasperFillManager.fillReport(src, null, con);
        } catch (JRException ex) {
            System.out.println("Erro: "+ex);
        }
        
        JasperViewer view = new JasperViewer(jasperPrint, false);
        
       view.setVisible(true);
    }
    
    
}
