//CLASSE RESPONSAVEL POR TRATAR A STRING 
package testeconexao;

import java.awt.BasicStroke;
import java.util.ArrayList;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.data.xy.XYSeries; 
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation; 
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeriesCollection;
/**
 *
 * @author Jaime
 */
public class TratarOutput {
    public String SaidaPing = null;
    public String SaidaTCPDW = null;
    public String SaidaTCPUP = null;
    public String SaidaUDPDW = null;
    public String SaidaUDPUP = null;

    ArrayList<Float> ValoreBandaTCPUP = new ArrayList<>();
    ArrayList<Float> ValoreBandaTCPDW = new ArrayList<>();
    ArrayList<Float> ValoreBandaUDPUP = new ArrayList<>();
    ArrayList<Float> ValoreBandaUDPDW = new ArrayList<>();
    ArrayList<Float> ValorePing = new ArrayList<>();


    public JFrame window;
    public JTabbedPane tabs;
    public XYSeries[][] graficos;
    public XYSeriesCollection datasets;
    public JFreeChart[] charts;
    
    boolean hasPing = false;
    float  BandaTCPUP, BandaTCPDW, BandaUDPUP, BandaUDPDW,TranferidosTCPUP, TranferidosTCPDW, 
            TranferidosUDPUP, TranferidosUDPDW, jitterUDPDW, jitterUDPUP, latenciaPing,perdasPing, perdasUDPUP, perdasUDPDW;
    int     IDTCPUP, IDTCPDW, IDUDPUP,IDUDPDW,
            IntervaloTCPUP,IntervaloTCPDW, IntervaloUDPUP,IntervaloUDPDW;

    int tamanho, tamanhoPing;
    public void TratarPing(){
        this.hasPing = false;
        String corteMedia="Perdidos";
        String teste="", teste2="";
        char aux=0;
        int indice;
        //indice = SaidaPing.indexOf(corteMedia);
        indice = SaidaPing.length();
        for (int i=2;i<6;i++){
            aux = SaidaPing.charAt(indice-i-1);
            teste =String.valueOf(aux)+teste;
        }
        if(teste.contains("=")){
            teste = teste.replace('=', ' ');
        }
        this.latenciaPing = Float.parseFloat(teste.trim());
        
        char aux2=0;
        int indice2, teste22, teste33;
        indice2 = SaidaPing.indexOf(corteMedia);
        teste22 = SaidaPing.length();
        for (int i=12;i<16;i++){
            aux = SaidaPing.charAt(indice2+i);
            teste2 =teste2+String.valueOf(aux);
        }
        if(teste2.contains("%")){
            teste2 = teste2.replace('%', ' ');
        }
        if(teste2.contains("(")){
            teste2 = teste2.replace('(', ' ');
        }
        this.perdasPing = Float.parseFloat(teste2.trim());
        System.out.println(this.perdasPing);
    }
    
    public int TratarPingString(){
        this.hasPing = true;
        this.ValorePing.clear();
        String referencia = "ms";
        String linha ="";
        String Valor ="";
        int retorno=0;
        char numero;
        int tamanhoLinha;
        String[] partes = this.SaidaPing.split("ms");
       // System.out.println("tamanho: "+this.SaidaUDPDW.length());
        for(int i=0;i<this.tamanhoPing;i++){
            linha = partes[i];
            tamanhoLinha = linha.length();
            //System.out.println("linha: "+linha);
            //System.out.println("tamanhoLinha: "+tamanhoLinha);
            for(int j=1;j<5;j++){
                numero = linha.charAt(tamanhoLinha-j);
                Valor = numero+Valor;
            }
            if(Valor.contains("=")){
                Valor = Valor.replace('=', ' ');
            }
            if(Valor.contains("o")){
                Valor = Valor.replace('o', ' ');
            }
            if(Valor.contains("p")){
                Valor = Valor.replace('p', ' ');
            }
            if(Valor.contains("<")){
                Valor = Valor.replace('<', ' ');
            }
            //System.out.println("String Valor: "+Valor);
                try {
                    this.ValorePing.add(Float.parseFloat(Valor.trim()));
                    retorno = 1;
                    
                } catch (NumberFormatException numberFormatException) {
                   retorno = 0;
                }
            Valor="";
        }
       //determina latencia
        String[] parteMedia = this.SaidaPing.split("ms");
        String linhaMedia;
        linhaMedia = parteMedia[this.tamanhoPing+2];
        tamanhoLinha = linhaMedia.length();
        for(int j=1;j<8;j++){
                numero = linhaMedia.charAt(tamanhoLinha-j);
                Valor = numero+Valor;
        }
        if(Valor.contains("=")){
                Valor = Valor.replace('=', ' ');
            }
            if(Valor.contains("i")){
                Valor = Valor.replace('i', ' ');
            }
            if(Valor.contains("d")){
                Valor = Valor.replace('d', ' ');
            }
            if(Valor.contains("<")){
                Valor = Valor.replace('<', ' ');
            }
            if(Valor.contains("a")){
                Valor = Valor.replace('a', ' ');
            }
        //System.out.println("perdas ping: "+Valor);
        this.latenciaPing = Float.parseFloat(Valor.trim());
        Valor="";
        
        //determina perdas
        String[] partePerdas = this.SaidaPing.split("%");
        for(int j=1;j<2;j++){
                numero = partePerdas[0].charAt(partePerdas[0].length()-j);
                Valor = numero+Valor;
        }

        System.out.println("perdas ping: "+Valor);
        this.perdasPing = Float.parseFloat(Valor.trim());

        return retorno;
    }
    public void TratarTCPDW(){
        this.ValoreBandaTCPDW.clear();
        String corte="Bytes";
        String teste="", teste2="";
        char aux=0, aux3=0;
        int indice;
        //determina a banda
        indice = SaidaTCPDW.length();
        for (int i=44;i<50;i++){
            aux = SaidaTCPDW.charAt(indice-i-3);
            teste =String.valueOf(aux)+teste;
        }
        if(teste.contains("M")){
            teste = teste.replace('M', ' ');
        }
        if(teste.contains("K")){
            teste = teste.replace('K', ' ');
        }
        this.BandaTCPDW = Float.parseFloat(teste.trim());
        //System.out.println("banda: "+this.BandaTCPDW);
        //determina os dados transferidos
        for (int i=57;i<62;i++){
            aux3 = SaidaTCPDW.charAt(indice-i-3);
            teste2 =String.valueOf(aux3)+teste2;
        }
        this.TranferidosTCPDW = Float.parseFloat(teste2.trim());
       // System.out.println("Transferidos: "+ this.TranferidosTCPDW);
        
        
       //pega cada valor 
       
        
            char aux2=0;
            int indice2, tamanho;
            String teste22="";
            indice2 = SaidaTCPDW.indexOf(corte);
            tamanho = SaidaTCPDW.length();
            for(int j=0; j< this.tamanho;j++){
            for (int i=7;i<12;i++){
                aux2 = SaidaTCPDW.charAt(indice2+i);
                teste22 =teste22+String.valueOf(aux2);
            }
            indice2+=70;
//            if(teste.contains("=")){
//                teste = teste.replace('=', ' ');
//            }
            if(teste22.contains("M")){
                teste22 = teste22.replace('M', ' ');
            }
            if(teste22.contains("K")){
                teste22 = teste22.replace('K', ' ');
        }
           // this.ValoreBandaTCPUP.add(Float.parseFloat(teste2.trim()));
            this.ValoreBandaTCPDW.add(Float.parseFloat(teste22.trim()));
            teste22="";
            //System.out.println("Valor: "+this.ValoreBandaTCPDW);
        }
        
            char aux33=0;
            int indice3;
            String teste223="", comparacao="[";
            indice3 = SaidaTCPDW.indexOf(comparacao);
            for (int i=1;i<4;i++){
                aux33 = SaidaTCPDW.charAt(indice3+i);
                teste223 =teste223+String.valueOf(aux33);
            }
            this.IDTCPDW = (Integer.parseInt(teste223.trim()));
            //System.out.println("ID: "+this.IDTCPDW );
        
       
       
        
        
    }

    public void TratarUDPDW(){
        //this.ValoreBandaUDPDW.clear();
        String corte="Bytes";
        String teste="", teste2="", teste4="", teste5="";
        char aux=0, aux3=0;
        int indice;
        //----------------------------------------------------------------------
        //determina a PERDAS     ok
        indice = SaidaUDPDW.length();
        for (int i=36;i<40;i++){
            aux = SaidaUDPDW.charAt(indice-i-3);
            teste =String.valueOf(aux)+teste;
        }
        //tem que tratar o dado
        if(teste.contains("0%")){
            teste = "0";
        }
        if(teste.contains("%")){
            teste = teste.replace('%', ' ');
        }
//        if(teste.contains("(0")){
//            teste = "0";
//        }
        if(teste.contains("(")){
            teste = teste.replace('(', ' ');
        }
        if(teste.contains(")")){
            teste = teste.replace(')', ' ');
        }
        if(teste.contains("1%")) teste = "1";
        this.perdasUDPDW = Float.parseFloat(teste.trim());
        System.out.println("perdas: "+teste);
        //----------------------------------------------------------------------
        //determina o Jitter   ok
        indice = SaidaUDPDW.length();
        teste4="";
        for (int i=51;i<=59;i++){
            aux = SaidaUDPDW.charAt(indice-i-3);
            teste4 =String.valueOf(aux)+teste4;
        }
        //tem que tratar o dado
        if(teste4.contains("c")){
            teste4 = teste4.replace('c', ' ');
        }
        if(teste4.contains("m")){
            teste4 = teste4.replace('m', ' ');
        }
        if(teste4.contains("s")){
            teste4 = teste4.replace('s', ' ');
        }
        if(teste4.contains("e")){
            teste4 = teste4.replace('e', ' ');
        }
        if(teste4.contains("/")){
            teste4 = teste4.replace('/', ' ');
        }
        this.jitterUDPDW = Float.parseFloat(teste4.trim());
        System.out.println("Jitter "+teste4);
        //---------------------------------------------------------------------
        //determina os dados transferidos   ok
        
        for (int i=78;i<89;i++){
            aux3 = SaidaUDPDW.charAt(indice-i-3);
            teste2 =String.valueOf(aux3)+teste2;
        }
        //tem que tratar o dado
        if(teste2.contains("c")){
            teste2 = teste2.replace('c', ' ');
        }
        if(teste2.contains("t")){
            teste2 = teste2.replace('t', ' ');
        }
        if(teste2.contains("K")){
            teste2 = teste2.replace('K', ' ');
        }
        if(teste2.contains("s")){
            teste2 = teste2.replace('s', ' ');
        }
        if(teste2.contains("e")){
            teste2 = teste2.replace('e', ' ');
        }
        if(teste2.contains("M")){
            teste2 = teste2.replace('M', ' ');
        }
        if(teste2.contains("B")){
            teste2 = teste2.replace('B', ' ');
        }
        if(teste2.contains("y")){
            teste2 = teste2.replace('y', ' ');
        }
        if(teste2.contains("e")){
            teste2 = teste2.replace('e', ' ');
        }
        this.TranferidosUDPDW = Float.parseFloat(teste2.trim());
        //System.out.println("Transferidos: "+ this.TranferidosUDPDW);
        
       //---------------------------------------------------------------------- 
        // pega id
        
            char aux33=0;
            int indice3;
            String teste223="", comparacao="[";
            indice3 = SaidaUDPDW.indexOf(comparacao);
            for (int i=1;i<4;i++){
                aux33 = SaidaUDPDW.charAt(indice3+i);
                teste223 =teste223+String.valueOf(aux33);
            }
            this.IDUDPDW = (Integer.parseInt(teste223.trim()));
            //System.out.println("ID: "+this.IDUDPDW );
        
       //pega cada valor 
       
       
       
       
       //--------------------------------------------------
       
       
       
       
               //determina a banda
        indice = SaidaUDPDW.length();
        teste4 ="";
        for (int i=67;i<76;i++){
            aux = SaidaUDPDW.charAt(indice-i-3);
            teste4 =String.valueOf(aux)+teste4;
        }
        //tem que tratar o dado
        if(teste4.contains("b")){
            teste4 = teste4.replace('b', ' ');
        }
        if(teste4.contains("i")){
            teste4 = teste4.replace('i', ' ');
        }
        if(teste4.contains("t")){
            teste4 = teste4.replace('t', ' ');
        }
        if(teste4.contains("e")){
            teste4 = teste4.replace('e', ' ');
        }
        if(teste4.contains("s")){
            teste4 = teste4.replace('s', ' ');
        }
        if(teste4.contains("K")){
            teste4 = teste4.replace('K', ' ');
        }
        if(teste4.contains("M")){
            teste4 = teste4.replace('M', ' ');
        }
//        if(teste4.contains("K")){
//            teste4 = teste4.replace('K', ' ');
//        }
       this.BandaUDPDW = Float.parseFloat(teste4.trim());
        System.out.println("banda: "+teste4);
        
       
       
       //-----------------------------------
//       
//       
//        
//        
//        
//        
//        
//        
//       
//       
//       
//            char aux2=0;
//            int indice2, tamanho;
//            String teste22="";
//            indice2 = SaidaUDPDW.indexOf(corte);
//            tamanho = SaidaUDPDW.length();
//            int constante =75;
//            for(int j=0; j< this.tamanho;j++){
//                
//            for (int i=7;i<13;i++){
//                aux2 = SaidaUDPDW.charAt(indice2+i);
//                teste22 =teste22+String.valueOf(aux2);
//            }
//            indice2+=constante;
//            //constante=75;
//            System.out.println("sTRING: "+teste22);
//            if(teste22.contains("M")){
//                teste22 = teste22.replace('M', ' ');
//            }
//            if(teste22.contains("b")){
//                teste22 = teste22.replace('b', ' ');
//            }
//            if(teste22.contains("i")){
//                teste22 = teste22.replace('i', ' ');
//            }
//            if(teste22.contains("t")){
//                teste22 = teste22.replace('t', ' ');
//            }
//            if(teste22.contains("s")){
//                teste22 = teste22.replace('s', ' ');
//            }
//           if(teste22.contains("e")){
//            teste22 = teste22.replace('e', ' ');
//           }
//            
//                try {
//                    this.ValoreBandaUDPDW.add(Float.parseFloat(teste22.trim()));
//                    retorno = 1;
//                    teste22="";
//                    
//                } catch (NumberFormatException numberFormatException) {
//                    
//                   retorno = 0;
//                }
//                teste22="";
//            }
//            System.out.println("VALOR: "+ this.ValoreBandaUDPDW);
    }
    
    public int TratarUDPDWSting(){
        this.ValoreBandaUDPDW.clear();
        String referencia = "Mbits/sec";
        String linha ="";
        String Valor ="";
        int retorno=0;
        char numero;
        int tamanhoLinha;
        String[] partes = this.SaidaUDPDW.split("Mbits/sec");
       // System.out.println("tamanho: "+this.SaidaUDPDW.length());
        for(int i=0;i<this.tamanho;i++){
            linha = partes[i];
            tamanhoLinha = linha.length();
            //System.out.println("linha: "+linha);
            //System.out.println("tamanhoLinha: "+tamanhoLinha);
            for(int j=1;j<8;j++){
                numero = linha.charAt(tamanhoLinha-j);
                Valor = numero+Valor;
            }
            //System.out.println("String Valor DW: "+Valor);
                try {
                    this.ValoreBandaUDPDW.add(Float.parseFloat(Valor.trim()));
                    retorno = 1;
                    
                } catch (NumberFormatException numberFormatException) {
                    
                   retorno = 0;
                }
            Valor="";
        }
        //determina banda
        linha = partes[this.tamanho];
        tamanhoLinha = linha.length();
        for(int j=1;j<8;j++){
                numero = linha.charAt(tamanhoLinha-j);
                Valor = numero+Valor;
        }
        System.out.println("Banda dw: "+Valor);
        this.BandaUDPDW = Float.parseFloat(Valor.trim());
        Valor="";
        //determina transferidos
        String[] parteTransfer = linha.split("MBytes");
        tamanhoLinha = parteTransfer[0].length();
        for(int j=1;j<8;j++){
                numero = linha.charAt(tamanhoLinha-j);
                Valor = numero+Valor;
        }
        System.out.println("Transferidos dw: "+Valor);
        this.TranferidosUDPDW = Float.parseFloat(Valor.trim());
        Valor="";
        //determina jitter
        String[] parteJitter = this.SaidaUDPDW.split("ms");
        String linhaJitter;
        linhaJitter = parteJitter[this.tamanho+2];
        tamanhoLinha = linhaJitter.length();
        for(int j=1;j<9;j++){
                numero = linhaJitter.charAt(tamanhoLinha-j);
                Valor = numero+Valor;
        }
        System.out.println("Jitter dw: "+Valor);
        this.jitterUDPDW = Float.parseFloat(Valor.trim());
        Valor="";
        //determina perdas
        String[] partePerdas = this.SaidaUDPDW.split("%");
        String linhaPerdas;
        linhaPerdas = partePerdas[this.tamanho];
        tamanhoLinha = linhaPerdas.length();
        for(int j=1;j<4;j++){
                numero = linhaPerdas.charAt(tamanhoLinha-j);
                Valor = numero+Valor;
        }
        if(Valor.contains("(")){
            Valor = Valor.replace('(', ' ');
        }
        System.out.println("Perdas dw: "+Valor);
        this.perdasUDPDW = Float.parseFloat(Valor.trim());
        Valor="";
                //determina perdas
        String[] parteID = this.SaidaUDPDW.split("]");
        String linhaID;
        linhaID = parteID[this.tamanho];
        tamanhoLinha = linhaID.length();
        for(int j=1;j<4;j++){
                numero = linhaID.charAt(tamanhoLinha-j);
                Valor = numero+Valor;
        }
        if(Valor.contains("[")){
            Valor = Valor.replace('[', ' ');
        }
        System.out.println("UD dw: "+Valor);
        this.IDUDPDW = Integer.parseInt(Valor.trim());
        Valor="";
        return retorno;
    }
    
    public int TratarUDPUPSting(){
        this.ValoreBandaUDPUP.clear();
        String referencia = "Mbits/sec";
        String linha ="";
        String Valor ="";
        int retorno=0;
        char numero;
        int tamanhoLinha;
        String[] partes = this.SaidaUDPUP.split("Mbits/sec");
       // System.out.println("tamanho: "+this.SaidaUDPDW.length());
        for(int i=0;i<this.tamanho;i++){
            linha = partes[i];
            tamanhoLinha = linha.length();
            //System.out.println("linha: "+linha);
            //System.out.println("tamanhoLinha: "+tamanhoLinha);
            for(int j=1;j<8;j++){
                numero = linha.charAt(tamanhoLinha-j);
                Valor = numero+Valor;
            }
            //System.out.println("String Valor UP: "+Valor);
                try {
                    this.ValoreBandaUDPUP.add(Float.parseFloat(Valor.trim()));
                    retorno = 1;
                    
                } catch (NumberFormatException numberFormatException) {
                    
                   retorno = 0;
                }
            Valor="";
        }
        System.out.println("Vetor UP: "+this.ValoreBandaUDPUP);
        //determina banda
        linha = partes[this.tamanho];
        tamanhoLinha = linha.length();
        for(int j=1;j<8;j++){
                numero = linha.charAt(tamanhoLinha-j);
                Valor = numero+Valor;
        }
        System.out.println("Banda UP: "+Valor);
        this.BandaUDPUP = Float.parseFloat(Valor.trim());
        Valor="";
        //determina transferidos
        String[] parteTransfer = linha.split("MBytes");
        tamanhoLinha = parteTransfer[0].length();
        for(int j=1;j<8;j++){
                numero = linha.charAt(tamanhoLinha-j);
                Valor = numero+Valor;
        }
        System.out.println("Transferidos UP: "+Valor);
        this.TranferidosUDPUP = Float.parseFloat(Valor.trim());
        Valor="";
        //determina jitter
        String[] parteJitter = this.SaidaUDPUP.split("ms");
        String linhaJitter;
        linhaJitter = parteJitter[2];
        tamanhoLinha = linhaJitter.length();
        for(int j=1;j<9;j++){
                numero = linhaJitter.charAt(tamanhoLinha-j);
                Valor = numero+Valor;
        }
        System.out.println("Jitter UP: "+Valor);
        this.jitterUDPUP = Float.parseFloat(Valor.trim());
        Valor="";
        //determina perdas
        String[] partePerdas = this.SaidaUDPUP.split("%");
        String linhaPerdas;
        linhaPerdas = partePerdas[0];
        tamanhoLinha = linhaPerdas.length();
        for(int j=1;j<4;j++){
                numero = linhaPerdas.charAt(tamanhoLinha-j);
                Valor = numero+Valor;
        }
        if(Valor.contains("(")){
            Valor = Valor.replace('(', ' ');
        }
        System.out.println("Perdas UP: "+Valor);
        this.perdasUDPUP = Float.parseFloat(Valor.trim());
        Valor="";
                //determina perdas
        String[] parteID = this.SaidaUDPUP.split("]");
        String linhaID;
        linhaID = parteID[this.tamanho];
        tamanhoLinha = linhaID.length();
        for(int j=1;j<4;j++){
                numero = linhaID.charAt(tamanhoLinha-j);
                Valor = numero+Valor;
        }
        if(Valor.contains("[")){
            Valor = Valor.replace('[', ' ');
        }
        System.out.println("UD UP: "+Valor);
        this.IDUDPUP = Integer.parseInt(Valor.trim());
        Valor="";
        return retorno;
    }
    public void TratarUDPUP(){
        //this.ValoreBandaUDPUP.clear();
        String corte="Bytes";
        String teste="", teste2="", teste4="", teste5="", teste46="";
        char aux=0, aux3=0;
        int indice;
        //determina a PERDAS
        indice = SaidaUDPUP.length();
        for (int i=36;i<40;i++){
            aux = SaidaUDPUP.charAt(indice-i-3);
            teste =String.valueOf(aux)+teste;
        }
        //tem que tratar o dado
        if(teste.contains("%")){
            teste = teste.replace('%', ' ');
        }
        if(teste.contains("(0")){
            teste = "0";
        }
        if(teste.contains("(")){
            teste = teste.replace('(', ' ');
        }
        if(teste.contains(")")){
            teste = teste.replace(')', ' ');
        }
        this.perdasUDPUP = Float.parseFloat(teste.trim());
        //System.out.println("perdas "+this.perdasUDPUP);
        //---------------------------------------------------------------------
        //determina o Jitter
        indice = SaidaUDPUP.length();
        for (int i=50;i<61;i++){
            aux = SaidaUDPUP.charAt(indice-i-3);
            teste4 =String.valueOf(aux)+teste4;
        }
        //tem que tratar o dado
        if(teste4.contains("c")){
            teste4 = teste4.replace('c', ' ');
        }
        if(teste4.contains("i")){
            teste4 = teste4.replace('i', ' ');
        }
        if(teste4.contains("\\")){
            teste4 = teste4.replace('\\', ' ');
        }
        if(teste4.contains("m")){
            teste4 = teste4.replace('m', ' ');
        }
        if(teste4.contains("s")){
            teste4 = teste4.replace('s', ' ');
        }
        if(teste4.contains("e")){
            teste4 = teste4.replace('e', ' ');
        }
        this.jitterUDPUP = Float.parseFloat(teste4.trim());
        System.out.println("jitter " +teste4);
        //----------------------------------------------------------------------
        //determina os dados transferidos
        //determina os dados transferidos   ok
        
        for (int i=78;i<89;i++){
            aux3 = SaidaUDPUP.charAt(indice-i-3);
            teste2 =String.valueOf(aux3)+teste2;
        }
        //tem que tratar o dado
        if(teste2.contains("c")){
            teste2 = teste2.replace('c', ' ');
        }
        if(teste2.contains("t")){
            teste2 = teste2.replace('t', ' ');
        }
        if(teste2.contains("K")){
            teste2 = teste2.replace('K', ' ');
        }
        if(teste2.contains("s")){
            teste2 = teste2.replace('s', ' ');
        }
        if(teste2.contains("e")){
            teste2 = teste2.replace('e', ' ');
        }
        if(teste2.contains("M")){
            teste2 = teste2.replace('M', ' ');
        }
        if(teste2.contains("B")){
            teste2 = teste2.replace('B', ' ');
        }
        if(teste2.contains("y")){
            teste2 = teste2.replace('y', ' ');
        }
        if(teste2.contains("e")){
            teste2 = teste2.replace('e', ' ');
        }
        this.TranferidosUDPUP = Float.parseFloat(teste2.trim());
        //System.out.println("Transferidos: "+ this.TranferidosUDPDW);
        
        System.out.println("Transferidos: "+ this.TranferidosUDPUP);
        

        
            char aux33=0;
            int indice3;
            String teste223="", comparacao="[";
            indice3 = SaidaUDPUP.indexOf(comparacao);
            for (int i=1;i<4;i++){
                aux33 = SaidaUDPUP.charAt(indice3+i);
                teste223 =teste223+String.valueOf(aux33);
            }
            this.IDUDPUP = (Integer.parseInt(teste223.trim()));
            System.out.println("ID: "+this.IDUDPUP );
                      
            

//determina a banda
        indice = SaidaUDPUP.length();
        teste46 ="";
        for (int i=67;i<76;i++){
            aux = SaidaUDPUP.charAt(indice-i-3);
            teste46 =String.valueOf(aux)+teste46;
        }
        //tem que tratar o dado
        if(teste46.contains("b")){
            teste4 = teste4.replace('b', ' ');
        }
        if(teste46.contains("i")){
            teste46 = teste46.replace('i', ' ');
        }
        if(teste46.contains("t")){
            teste46 = teste46.replace('t', ' ');
        }
        if(teste46.contains("e")){
            teste46 = teste46.replace('e', ' ');
        }
        if(teste46.contains("s")){
            teste46 = teste46.replace('s', ' ');
        }
        if(teste46.contains("K")){
            teste46 = teste46.replace('K', ' ');
        }
        if(teste46.contains("M")){
            teste46 = teste46.replace('M', ' ');
        }    
        if(teste46.contains("b")){
            teste46 = teste46.replace('b', ' ');
        }
        
       
       this.BandaUDPUP = Float.parseFloat(teste46.trim());
        System.out.println("banda: "+teste46);
        
       //pega cada valor 
//       
//        
//            char aux2=0;
//            int indice2, tamanho;
//            String teste22="";
//            indice2 = SaidaUDPUP.indexOf(corte);
//            tamanho = SaidaUDPUP.length();
//            int constante =76;
//            for(int j=0; j< this.tamanho;j++){
//                
//            for (int i=7;i<13;i++){
//                aux2 = SaidaUDPUP.charAt(indice2+i);
//                teste22 =teste22+String.valueOf(aux2);
//            }
//            indice2+=constante;
//            constante=75;
//            if(teste22.contains("M")){
//                teste22 = teste22.replace('M', ' ');
//            }
//            if(teste22.contains("b")){
//                teste22 = teste22.replace('b', ' ');
//            }
//            if(teste22.contains("i")){
//                teste22 = teste22.replace('i', ' ');
//            }
//            if(teste22.contains("t")){
//                teste22 = teste22.replace('t', ' ');
//            }
//            if(teste22.contains("s")){
//                teste22 = teste22.replace('s', ' ');
//            }
//                try {
//                    this.ValoreBandaUDPUP.add(Float.parseFloat(teste22.trim()));
//                    retorno = 1;
//                    teste22="";
//                    
//                } catch (NumberFormatException numberFormatException) {
//                   retorno = 0;
//                }
//            }
//            System.out.println("VALOR: "+ this.ValoreBandaUDPUP);
//            return 1;
       //-----------------------------------
       
       
        
        
        
        
        
        
//       
//       
//       //PEGA CADA VALOR TB UPPPPP
//            char aux2=0;
//            int indice2, tamanho;
//            String teste22="";
//            indice2 = SaidaUDPUP.indexOf(corte);
//            tamanho = SaidaUDPUP.length();
//            int constante = 58;
//            for(int j=0; j< this.tamanho;j++){
//                
//            for (int i=7;i<13;i++){
//                aux2 = SaidaUDPUP.charAt(indice2+i);
//                teste22 =teste22+String.valueOf(aux2);
//            }
//            indice2+=constante;
//            //if (j==4) constante=59;
//            //System.out.println("sTRING: "+teste22);
//            if(teste22.contains("M")){
//                teste22 = teste22.replace('M', ' ');
//            }
//            if(teste22.contains("b")){
//                teste22 = teste22.replace('b', ' ');
//            }
//            if(teste22.contains("i")){
//                teste22 = teste22.replace('i', ' ');
//            }
//            if(teste22.contains("t")){
//                teste22 = teste22.replace('t', ' ');
//            }
//            if(teste22.contains("s")){
//                teste22 = teste22.replace('s', ' ');
//            }
//           if(teste22.contains("e")){
//            teste22 = teste22.replace('e', ' ');
//           }
//
//                try {
//                    this.ValoreBandaUDPUP.add(Float.parseFloat(teste22.trim()));
//                    retorno = 1;
//                    teste22="";
//                    
//                } catch (NumberFormatException numberFormatException) {
//                    
//                   retorno = 0;
//                }
//            }
//            System.out.println("VALOR: "+ this.ValoreBandaUDPUP);
//            return retorno;

    }
   
    public void TratarTCPUP(){
        this.ValoreBandaTCPUP.clear();
        String corte="Bytes";
        String teste="", teste2="";
        char aux=0, aux3=0;
        int indice;
        //determina a banda
        indice = this.SaidaTCPUP.length();
        for (int i=44;i<50;i++){
            aux = SaidaTCPUP.charAt(indice-i-3);
            teste =String.valueOf(aux)+teste;
        }

        this.BandaTCPUP = Float.parseFloat(teste.trim());
        //System.out.println("banda: "+this.BandaTCPUP);
        //determina os dados transferidos
        for (int i=57;i<62;i++){
            aux3 = SaidaTCPUP.charAt(indice-i-3);
            teste2 =String.valueOf(aux3)+teste2;
        }
        if(teste2.contains("b")){
            teste2 = teste2.replace('b', ' ');
        }
        this.TranferidosTCPUP = Float.parseFloat(teste2.trim());
       // System.out.println("Transferidos: "+ this.TranferidosTCPUP);
        
        //IDIDIDID
                    
            char aux33=0;
            int indice3;
            String teste223="", comparacao="[";
            indice3 = SaidaTCPUP.indexOf(comparacao);
            for (int i=1;i<4;i++){
                aux33 = SaidaTCPUP.charAt(indice3+i);
                teste223 =teste223+String.valueOf(aux33);
            }
            this.IDTCPUP = (Integer.parseInt(teste223.trim()));
           // System.out.println("ID: "+this.IDTCPUP );
        
        
        
       //pega cada valor 
       
        
            char aux2=0;
            int indice2, tamanho;
            String teste22="";
            indice2 = SaidaTCPUP.indexOf(corte);
            tamanho = SaidaTCPUP.length();
            int contador =70;
            for(int j=0; j< this.tamanho;j++){
            for (int i=6;i<11;i++){
                aux2 = SaidaTCPUP.charAt(indice2+i);
                teste22 =teste22+String.valueOf(aux2);
            }
            indice2+=70;
            contador=69;
            if(teste22.contains("M")){
                teste22 = teste22.replace('M', ' ');
            }
            if(teste22.contains("K")){
                teste22 = teste22.replace('K', ' ');
            }
            if(teste22.contains("b")){
                teste22 = teste22.replace('b', ' ');
            }
           // this.ValoreBandaTCPUP.add(Float.parseFloat(teste2.trim()));
            this.ValoreBandaTCPUP.add(Float.parseFloat(teste22.trim()));
            //System.out.println("String "+teste22);
            teste22="";

        }

            
    }
    
    
    public String getSaidaPing(){
    return this.SaidaPing;
    }
    
    
    
    //SETS
    public void setSaidaTCPDW(String s){
         this.SaidaTCPDW = s;
    }
    public void setSaidaTCPUP(String s){
         this.SaidaTCPUP = s;
    }
    public void setSaidaUDPDW(String s){
         this.SaidaUDPDW = s;
    }
    public void setSaidaUDPUP(String s){
         this.SaidaUDPUP = s;
    }
    public void setSaidaPing(String s){
         this.SaidaPing = s;
    }
    
    //GETS
    public float getLatencia(){
        return this.latenciaPing;
    }
    public float getPerdas(){
        return this.perdasPing;
    }
    public int getIDTCPDW(){
        return this.IDTCPDW;
    }
    public int getIDTCPUP(){
        return this.IDTCPUP;
    }
    public float getBandaTCPUP(){
        return this.BandaTCPUP;
    }
    public float getIntervaloTCPUP(){
        return this.IntervaloTCPUP;
    }
    public float getTransferidosTCPUP(){
        return this.TranferidosTCPUP;
    }
    public float getTransferidosUDPDW(){
        return this.TranferidosUDPDW;
    }
    public float getTransferidosUDPUP(){
        return this.TranferidosUDPUP;
    }
    public float getBandaUDPDW(){
        return this.BandaUDPDW;
    }
    public float getBandaUDPUP(){
        return this.BandaUDPUP;
    }
    public float getJitterUDPDW(){
        return this.jitterUDPDW;
    }
    public float getJitterUDPUP(){
        return this.jitterUDPUP;
    }
    public float getPerdasUDPDW(){
        return this.perdasUDPDW;
    }
    public float getPerdasUDPUP(){
        return this.perdasUDPUP;
    }
    public int getIDUDPDW(){
        return this.IDUDPDW;
    }
    public int getIDUDPUP(){
        return this.IDUDPUP;
    }
    public float getBandaTCPDW(){
        return this.BandaTCPDW;
    }
    public float getIntervaloTCPDW(){
        return this.IntervaloTCPDW;
    }
    public float getTransferidosTCPDW(){
        return this.TranferidosTCPDW;
    }
    public void show(){
       window.setVisible(true);
    }
    //---------------------------------------GRAFICOS
    public void CriarGraficos() throws IOException{
        //quantidade de entradas e saidas
        int length = this.tamanho;
        //janela principal
        window = new JFrame("Gráficos Do Teste");
        //configurações da janela
        window.setSize(750,425);
        window.setLayout(new BorderLayout());
        
        WindowListener listener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent w) {
                window.setVisible(false);
            }
        };
        window.addWindowListener(listener);
        window.setLocationRelativeTo(null);
        //======================================
        tabs = new JTabbedPane();
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                 "Teste TCP", 
                   "Tempo(s)", 
                   "Bandwidth (Mbits)", 
                createDatasetTCP(),
                PlotOrientation.VERTICAL,
                true, true, false);
        ChartPanel graficoGerado = new ChartPanel(xylineChart);
        
        //JanelaPrincipal.PainelSaida.setLayout(null);
        //JanelaPrincipal.PainelSaida.add(graficoGerado);
        //graficoGerado.setBounds(JanelaPrincipal.PainelSaida.getVisibleRect());
        final XYPlot plot = xylineChart.getXYPlot();
              XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
             renderer.setSeriesPaint( 0 , Color.RED );
             renderer.setSeriesPaint( 1 , Color.GREEN );
             renderer.setSeriesStroke( 0 , new BasicStroke( 3.0f ) );
             renderer.setSeriesStroke( 1 , new BasicStroke( 3.0f ) );
        ValueAxis axis = plot.getDomainAxis();
        plot.setRenderer( renderer ); 
        axis.setAutoRange(true);
        axis.setFixedAutoRange(this.tamanho-1);
        window.getContentPane().add(tabs);
        ChartUtilities.saveChartAsPNG(new File("../Graficos/graficoTCP.png"), xylineChart, 1000, 500);
        
        //ImageIO.write((RenderedImage) graficoGerado, "PNG", new File("./Graficos/yourImageName.PNG"));
        //======================================
        JFreeChart xylineChartUDP = ChartFactory.createXYLineChart(
                 "Teste UDP", 
                   "Tempo(s)", 
                   "Bandwidth (Mbits)", 
                createDatasetUDP(),
                PlotOrientation.VERTICAL,
                true, true, false);
        ChartPanel graficoGeradoUDP = new ChartPanel(xylineChartUDP);
        //tabs.addTab("TCP", graficoGerado);
        //tabs.addTab("UDP", graficoGeradoUDP);
        //JanelaPrincipal.PainelSaida.setLayout(null);
        //JanelaPrincipal.PainelSaida.add(graficoGerado);
        //graficoGerado.setBounds(JanelaPrincipal.PainelSaida.getVisibleRect());
        final XYPlot plotUDP = xylineChartUDP.getXYPlot();
             XYLineAndShapeRenderer rendererUDP = new XYLineAndShapeRenderer( );
             rendererUDP.setSeriesPaint( 0 , Color.RED );
             rendererUDP.setSeriesPaint( 1 , Color.GREEN );
             rendererUDP.setSeriesStroke( 0 , new BasicStroke( 3.0f ) );
             rendererUDP.setSeriesStroke( 1 , new BasicStroke( 3.0f ) );
        ValueAxis axisUDP = plotUDP.getDomainAxis();
        plotUDP.setRenderer( rendererUDP ); 
        axisUDP.setAutoRange(true);
        axisUDP.setFixedAutoRange(this.tamanho-1);
        //window.getContentPane().add(tabs);
        ChartUtilities.saveChartAsPNG(new File("../Graficos/graficoUDP.png"), xylineChartUDP, 1000, 500);
        //========================================================
        if(hasPing){
            JFreeChart xylineCharPing = ChartFactory.createXYLineChart(
                 "Teste Ping", 
                   "Tempo(s)", 
                   "Tempo de Resposta (ms)", 
                createDatasetPing(),
                PlotOrientation.VERTICAL,
                true, true, false);
        ChartPanel graficoGeradoPing = new ChartPanel(xylineCharPing);
        tabs.addTab("TCP", graficoGerado);
        tabs.addTab("UDP", graficoGeradoUDP);
        tabs.addTab("Ping", graficoGeradoPing);
        //JanelaPrincipal.PainelSaida.setLayout(null);
        //JanelaPrincipal.PainelSaida.add(graficoGerado);
        //graficoGerado.setBounds(JanelaPrincipal.PainelSaida.getVisibleRect());
        final XYPlot plotPing = xylineCharPing.getXYPlot();
             XYLineAndShapeRenderer rendererPing = new XYLineAndShapeRenderer( );
             rendererPing.setSeriesPaint( 0 , Color.BLUE );
             rendererPing.setSeriesStroke( 0 , new BasicStroke( 3.0f ) );
        ValueAxis axisPing = plotPing.getDomainAxis();
        plotPing.setRenderer( rendererPing ); 
        axisPing.setAutoRange(true);
        axisPing.setFixedAutoRange(this.tamanhoPing-1);
        window.getContentPane().add(tabs);
        ChartUtilities.saveChartAsPNG(new File("../Graficos/graficoPing.png"), xylineCharPing, 1000, 300);
        }
    else{
        tabs.addTab("TCP", graficoGerado);
        tabs.addTab("UDP", graficoGeradoUDP);
        window.getContentPane().add(tabs);
        }

    }
    

    
    private XYDataset createDatasetTCP( )
   {
      final XYSeries TCPDOWN = new XYSeries( "Download" ); 
      for(int i=0; i<this.tamanho;i++){
        TCPDOWN.add( i+1 , this.ValoreBandaTCPDW.get(i) );  
      }
      final XYSeries TCPDOUP = new XYSeries( "Upload" ); 
      for(int i=0; i<this.tamanho;i++){
        TCPDOUP.add( i+1 , this.ValoreBandaTCPUP.get(i) );  
      }
      final XYSeriesCollection dataset = new XYSeriesCollection( );          
      dataset.addSeries( TCPDOWN );          
      dataset.addSeries( TCPDOUP ); 
      return dataset;
   }

    private XYDataset createDatasetUDP( )
   {
      final XYSeries UDPDOWN = new XYSeries( "Download" ); 
      for(int i=0; i<this.tamanho;i++){
        UDPDOWN.add( i+1 , this.ValoreBandaUDPDW.get(i) );  
      }
      final XYSeries UDPUP = new XYSeries( "Upload" ); 
      for(int i=0; i<this.tamanho;i++){
        UDPUP.add( i+1 , this.ValoreBandaUDPUP.get(i) );  
      }
      final XYSeriesCollection datasetUDP = new XYSeriesCollection( );          
      datasetUDP.addSeries( UDPDOWN );          
      datasetUDP.addSeries( UDPUP ); 
      return datasetUDP;
   }
    
     private XYDataset createDatasetPing( )
   {
      if(hasPing){
        final XYSeries PING = new XYSeries( "Ping" ); 
        for(int i=0; i<this.tamanhoPing;i++){
          PING.add( i+1 , this.ValorePing.get(i) );  
        }
        final XYSeriesCollection datasetPing = new XYSeriesCollection( );          
        datasetPing.addSeries( PING );    
        return datasetPing;
      }
      else return null;
   }
    
    //-------------------------------------------
    
    public void setTamanho(int tamanho){
        this.tamanho = tamanho;
    }
    public void setTamanhoPing(int tamanho){
        this.tamanhoPing = tamanho;
    }
}
