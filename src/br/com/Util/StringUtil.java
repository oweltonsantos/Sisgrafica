/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Util;

import java.text.Normalizer;

/**
 *
 * @author EJunior
 */
public class StringUtil {

    public static String removeAcentos(String str) {
        String strSemAcentos = Normalizer.normalize(str, Normalizer.Form.NFD);
        strSemAcentos = strSemAcentos.replaceAll("[^\\p{ASCII}]", "");
        return strSemAcentos;
    }
}
