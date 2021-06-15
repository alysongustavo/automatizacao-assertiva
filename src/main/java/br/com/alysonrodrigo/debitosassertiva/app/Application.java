package br.com.alysonrodrigo.debitosassertiva.app;

import br.com.alysonrodrigo.debitosassertiva.controller.Robo;
import br.com.alysonrodrigo.debitosassertiva.domain.Documento;
import br.com.alysonrodrigo.debitosassertiva.util.LeituraArquivo;
import br.com.alysonrodrigo.debitosassertiva.util.UrlUtil;
import br.com.alysonrodrigo.debitosassertiva.util.VerificaArquivoUtil;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void main(String[] args) {

        String fileOrigem = "C:\\Users\\gdasi\\assertiva\\protestos\\lote01_entrada.txt";
        String fileValidacao = "C:\\Users\\gdasi\\assertiva\\protestos\\lote01_validacao.txt";
        String fileResultado = "C:\\Users\\gdasi\\assertiva\\protestos\\lote01_resultado.txt";

        List<File> arquivos = new ArrayList<>();
        arquivos.add(new File(fileOrigem));
        arquivos.add(new File(fileValidacao));
        arquivos.add(new File(fileResultado));

        if(VerificaArquivoUtil.arquivosExistem(arquivos)){
            // Carrega o arquivo de documentos
            LeituraArquivo leituraArquivo = new LeituraArquivo(fileOrigem, fileValidacao);
            List<Documento> documentos = leituraArquivo.getDocumentoList();

            documentos.removeIf(documento -> {
                return leituraArquivo.verificaDocJaConsultado(documento.getCpf());
            });

            Robo robo = new Robo(UrlUtil.URL);
            robo.setArquivoEntrada(fileOrigem);
            robo.setArquivoValidacao(fileValidacao);
            robo.setArquivoDestino(fileResultado);
            try{
                robo.logarAdvogado();

                documentos.forEach(documento -> {
                    try {
                        robo.consultarDados(documento.getCpf());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });

                robo.encerrarConsulta();
            }catch (NumberFormatException n){

            }catch (InterruptedException i){

            }


        }else{
            JOptionPane.showMessageDialog(null, "Arquivos Inexistentes, favor verificar!");
        }

    }
}
