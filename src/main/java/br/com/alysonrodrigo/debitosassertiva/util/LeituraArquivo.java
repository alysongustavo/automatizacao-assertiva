package br.com.alysonrodrigo.debitosassertiva.util;

import br.com.alysonrodrigo.debitosassertiva.domain.Documento;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class LeituraArquivo {

    private List<Documento> documentoList = new ArrayList<>();

    private List<Documento> documentoListJaConsultado = new ArrayList<>();

    private String arquivoDocOrigem;
    private String arquivoDocDestinoValidacao;

    public LeituraArquivo(String arquivoDocOrigem, String arquivoDocDestinoValidacao) {

        this.arquivoDocOrigem = arquivoDocOrigem;
        this.arquivoDocDestinoValidacao = arquivoDocDestinoValidacao;

        try{
            lerArquivoDocOrigem();
            lerArquivoDocDestinoValidacao();

        }catch (IOException e){
            System.out.println("Ocorreu um erro: " + e.getMessage());
        }catch (URISyntaxException e){
            System.out.println("Ocorreu um erro: " + e.getMessage());
        }
    }

    public String getArquivoDocOrigem() {
        return arquivoDocOrigem;
    }

    public String getArquivoDocDestinoValidacao() {
        return arquivoDocDestinoValidacao;
    }

    public void lerArquivoDocOrigem() throws IOException, URISyntaxException {
        File file = new File(this.getArquivoDocOrigem());
        BufferedReader b = new BufferedReader(new FileReader(file));

        String readLine = "";

        while ((readLine = b.readLine()) != null){
                 this.addDocumento(new Documento(readLine));
        }
    }

    public void lerArquivoDocDestinoValidacao() throws IOException, URISyntaxException {
        File file = new File(this.getArquivoDocDestinoValidacao());
        BufferedReader b = new BufferedReader(new FileReader(file));

        String readLine = "";

        while ((readLine = b.readLine()) != null){
            this.addDocumentoJaConsultado(new Documento(readLine));
        }
    }

    private void addDocumento(Documento documento){
        this.documentoList.add(documento);
    }
    private void addDocumentoJaConsultado(Documento documento){
        this.documentoListJaConsultado.add(documento);
    }

    public boolean verificaDocJaConsultado(String documento){
        boolean docJaExiste = false;

      for(Documento doc: this.getDocumentoListJaConsultado())  {
           if(doc.getCpf().equals(documento)){
               docJaExiste = true;
               break;
           }else{
               docJaExiste = false;
           }
      }

      return docJaExiste;

    }

    public List<Documento> getDocumentoList() {
        return documentoList;
    }

    public List<Documento> getDocumentoListJaConsultado() {
        return documentoListJaConsultado;
    }

}
