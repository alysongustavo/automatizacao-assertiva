package br.com.alysonrodrigo.debitosassertiva.util;

import java.io.File;
import java.util.List;

public class VerificaArquivoUtil {

    public static boolean arquivosExistem(List<File> arquivos){

        if(arquivos == null){
            return false;
        }

        arquivos.removeIf(arquivo ->  !arquivo.exists());

        return (arquivos.size() > 0);
    }
}
