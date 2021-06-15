package br.com.alysonrodrigo.debitosassertiva.controller;

import br.com.alysonrodrigo.debitosassertiva.domain.Usuario;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Robo {

    private WebDriver driver;

    private boolean concluirColeta = false;

    private String arquivoEntrada;
    private String arquivoValidacao;
    private String arquivoDestino;

    public Robo(String url){
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--verbose");
        chromeOptions.addArguments("--allowed-ips");
        driver = new ChromeDriver(chromeOptions);
        driver.get(url);
    }

    private void dormir(int tempoSono) throws NumberFormatException, InterruptedException {

        Thread.sleep(Long.parseLong(String.valueOf(tempoSono * 1000)));
    }

    private void focoAba(int aba) {

        List<String> abas = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(abas.get(aba - 1));
    }

    private void fecharAba(int aba) {

        List<String> abas = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(abas.get(aba - 1));
        driver.close();
    }

    public void logarAdvogado() throws NumberFormatException, InterruptedException {

        Usuario advogado = new Usuario();

        WebElement element = driver.findElement(By.id("cliente"));
        element.sendKeys(advogado.getEmpresa());

        element = driver.findElement(By.id("usuario"));
        element.sendKeys(advogado.getLogin());

        element = driver.findElement(By.id("senha"));
        element.sendKeys(advogado.getSenha());

        element = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/form/span/span/button"));
        element.click();

        verificaModalOpen("/html/body/div[4]/div[2]/div");

        verificarTutorialOpen("/html/body/conpass/div/div[2]/div[1]");

        abrirConsultaCredito();

    }

    public void consultarDados(String cpf) throws InterruptedException {
        consultarDebitoProtesto(cpf);
    }

    public void encerrarConsulta(){

    }

    private void verificarTutorialOpen(String xpath) throws InterruptedException {

        WebElement tutorial = null;
        boolean achouTutorial = false;

        dormir(5);

        try{
            tutorial = driver.findElement(By.xpath(xpath));
            achouTutorial = true;
        }catch (Exception e){
            achouTutorial = false;
        }finally {
            if(achouTutorial){
                tutorial.click();
            }
        }
    }

    private void verificaModalOpen(String xpath) throws InterruptedException {

        WebElement bannerAnuncio = null;
        boolean achoBannerAnuncio = false;

        dormir(5);

        try {
            bannerAnuncio = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div"));
            achoBannerAnuncio = true;
        }catch (Exception e){
            achoBannerAnuncio = false;
        }finally {
            if(achoBannerAnuncio){
                driver.findElement(By.xpath("/html/body/div[4]/div[2]/div/div/div[2]/div/section[2]/button")).click();
            }

        }
    }

    private void abrirConsultaCredito() throws InterruptedException {
        WebElement menuCredMix = null;
        boolean achoMenuCredMix = false;

        try{
            menuCredMix = driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[3]/ul/li[3]/a"));
            achoMenuCredMix = true;
        }catch (Exception e){
            achoMenuCredMix = false;
        }finally {
            if(achoMenuCredMix){
                menuCredMix.click();

                WebElement menuCredMixPF = driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[3]/ul/li[3]/ul/li[1]/a"));
                menuCredMixPF.click();
            }

            dormir(5);
        }
    }

    private void consultarDebitoProtesto(String cpf) throws InterruptedException {
        WebElement documento;
        boolean achouDocumento = false;

        dormir(5);

        try{
            documento = driver.findElement(By.id("documento"));
            documento.sendKeys(cpf);
            achouDocumento = true;
        }catch (Exception e){
            achouDocumento = false;
        }finally {
                if(achouDocumento){

                    WebElement botaoConsulta = driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div/div/div/div[1]/div[2]/div/form/div[2]/div[2]/span[2]/span/button"));
                    botaoConsulta.click();

                    WebElement temProtesto = driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div/div/div/div[3]/div/div/div/div[4]/div[2]/div/div[2]/a/div/span[2]/div[1]/div"));

                    StringBuilder stringBuilder = new StringBuilder(cpf);
                    stringBuilder.append("|");
                    stringBuilder.append(temProtesto.getText());
                    escreverNoArquivoDebito(stringBuilder.toString());
                    escreverNoArquivoDebitoValidacao(cpf);

                    WebElement abaDados = driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div/div/div/div[3]/ul/li/a/div/span/a"));
                    abaDados.click();


                }

            dormir(5);
        }
    }

    public void escreverNoArquivoDebito(String linha) {
        try {
            File file = new File(this.getArquivoDestino());
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter conexao = new BufferedWriter(fw);
            conexao.write(linha);
            conexao.newLine();
            conexao.close();
            fw.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void escreverNoArquivoDebitoValidacao(String linha) {
        try {
            File file = new File(this.getArquivoValidacao());
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter conexao = new BufferedWriter(fw);
            conexao.write(linha);
            conexao.newLine();
            conexao.close();
            fw.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getArquivoEntrada() {
        return arquivoEntrada;
    }

    public void setArquivoEntrada(String arquivoEntrada) {
        this.arquivoEntrada = arquivoEntrada;
    }

    public String getArquivoValidacao() {
        return arquivoValidacao;
    }

    public void setArquivoValidacao(String arquivoValidacao) {
        this.arquivoValidacao = arquivoValidacao;
    }

    public String getArquivoDestino() {
        return arquivoDestino;
    }

    public void setArquivoDestino(String arquivoDestino) {
        this.arquivoDestino = arquivoDestino;
    }


}
