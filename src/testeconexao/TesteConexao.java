package testeconexao;

import com.itextpdf.text.Chunk;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import static java.lang.Math.ceil;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.Position;
import javax.swing.text.Segment;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import javax.swing.JOptionPane;
import static javax.swing.text.StyleConstants.FontFamily;

public class TesteConexao{
private static MainWindow TelaPrincipal;
private static ReportWindow TelaRelatorio;
private static TratarOutput Tratamento;

public String SaidaPing = null;
public String SaidaTCPDW = null;
public String SaidaTCPUP = null;
public String SaidaUDPDW = null;
public String SaidaUDPUP = null;




//construtor
TesteConexao(){
    TelaPrincipal = new MainWindow();
    TelaPrincipal.addIniciarPingListening(new ConnectListener());
    TelaPrincipal.addIniciarTCPDWListening(new ConnectListenerTCPDW());
    TelaPrincipal.addIniciarTCPUPListening(new ConnectListenerTCPUP());
    TelaPrincipal.addIniciarUDPDWListening(new ConnectListenerUDPDW());
    TelaPrincipal.addIniciarUDPUPListening(new ConnectListenerUDPUP());
    TelaPrincipal.addIniciarTODOSListening(new ConnectListenerTODOS());
    TelaPrincipal.addGerarRelatorioListening(new ConnectListenerGerarRelatorio());
    TelaPrincipal.addVerGraficosListening(new ConnectListenerVerGraficos());
    

    TelaPrincipal.pack();
    TelaPrincipal.setLocationRelativeTo(null);
    TelaPrincipal.setTitle("Teste de Bandwidth Iperf");
    TelaPrincipal.setVisible(true);
    
    TelaRelatorio = new ReportWindow();
    TelaRelatorio.addVerGraficosListening(new ConnectListenerVerGraficosREPORT());
    TelaRelatorio.addExportarListening(new ConnectListenerCriarREPORT());
    TelaRelatorio.pack();
    TelaRelatorio.setLocationRelativeTo(null);
    TelaRelatorio.setTitle("Gerar Relatório");
    TelaRelatorio.setDefaultCloseOperation(TelaRelatorio.DISPOSE_ON_CLOSE);
    
    Tratamento = new TratarOutput();
    //new Thread(new AtualizarTela()).start();
}
    
public String doCommandPing(List<String> command) throws IOException{
    int n=0;
    int k;
    String retorno=null;
    String impresao=null;
    k=100/Integer.parseInt(TelaPrincipal.getPingCount());
    ProcessBuilder pb = new ProcessBuilder(command);
    Process process = pb.start();

    BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
    BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

    // read the output from the command
    //System.out.println("Saida Ping:\n");
    //TelaPrincipal.addTextOutput("Teste de Ping:\n");
    while ((impresao = stdInput.readLine()) != null)
    {
      TelaPrincipal.addTextOutput(impresao);
      //TelaPrincipal.UpdatePing(n);
      retorno+=impresao;
      //n+=k;
    }

    // read any errors from the attempted command
    //System.out.println("Erro no Ping!");
    while ((impresao = stdError.readLine()) != null)
    {
      //System.out.println(SaidaPing);
    }
    return retorno;
  }
public String doCommandTCPDW(List<String> command) throws IOException{
    int n=0;
    int k;
    String retorno=null;
    String impresao=null;
    k=100/Integer.parseInt(TelaPrincipal.getPingCount());
    ProcessBuilder pb = new ProcessBuilder(command);
    Process process = pb.start();

    BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
    BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

    // read the output from the command
    //System.out.println("Saida Ping:\n");
    //TelaPrincipal.addTextOutput("Teste de Ping:\n");
    while ((impresao = stdInput.readLine()) != null)
    {
      TelaPrincipal.addTextOutput(impresao);
      //TelaPrincipal.BarraTCPDW.setValue(n);
      retorno+=impresao;
      //n+=k;
    }

    // read any errors from the attempted command
    //System.out.println("Erro no Ping!");
    while ((impresao = stdError.readLine()) != null)
    {
      //System.out.println(SaidaPing);
    }
    return retorno;
  } 
public String doCommandTCPUP(List<String> command) throws IOException{
    int n=0;
    int k;
    String retorno=null;
    String impresao=null;
    k=100/Integer.parseInt(TelaPrincipal.getPingCount());
    ProcessBuilder pb = new ProcessBuilder(command);
    Process process = pb.start();

    BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
    BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

    // read the output from the command
    //System.out.println("Saida Ping:\n");
    //TelaPrincipal.addTextOutput("Teste de Ping:\n");
    while ((impresao = stdInput.readLine()) != null)
    {
      TelaPrincipal.addTextOutput(impresao);
      //TelaPrincipal.BarraTCPUP.setValue(n);
      retorno+=impresao;
      //n+=k;
    }

    // read any errors from the attempted command
    //System.out.println("Erro no Ping!");
    while ((impresao = stdError.readLine()) != null)
    {
      //System.out.println(SaidaPing);
    }
    return retorno;
  } 
public String doCommandUDPDW(List<String> command) throws IOException{
    int n=0;
    int k;
    String retorno=null;
    String impresao=null;
    k=100/Integer.parseInt(TelaPrincipal.getPingCount());
    ProcessBuilder pb = new ProcessBuilder(command);
    Process process = pb.start();

    BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
    BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

    // read the output from the command
    //System.out.println("Saida Ping:\n");
    //TelaPrincipal.addTextOutput("Teste de Ping:\n");
    while ((impresao = stdInput.readLine()) != null)
    {
      TelaPrincipal.addTextOutput(impresao);
      //TelaPrincipal.BarraUDPDW.setValue(n);
      retorno+=impresao;
      //n+=k;
    }

    // read any errors from the attempted command
    //System.out.println("Erro no Ping!");
    while ((impresao = stdError.readLine()) != null)
    {
      //System.out.println(SaidaPing);
    }
    return retorno;
  } 
public String doCommandUDPUP(List<String> command) throws IOException{
    int n=0;
    int k;
    String retorno=null;
    String impresao=null;
    k=100/Integer.parseInt(TelaPrincipal.getPingCount());
    ProcessBuilder pb = new ProcessBuilder(command);
    Process process = pb.start();

    BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
    BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

    // read the output from the command
    //System.out.println("Saida Ping:\n");
    //TelaPrincipal.addTextOutput("Teste de Ping:\n");
    while ((impresao = stdInput.readLine()) != null)
    {
      TelaPrincipal.addTextOutput(impresao);
      //TelaPrincipal.BarraUDPUP.setValue(n);
      retorno+=impresao;
      //n+=k;
    }

    // read any errors from the attempted command
    //System.out.println("Erro no Ping!");
    while ((impresao = stdError.readLine()) != null)
    {
      //System.out.println(SaidaPing);
    }
    return retorno;
  } 
    
   
  public static void main(String args[]) {
        TesteConexao TesteIPERF = new TesteConexao();
    }
  
 
  class ConnectListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(new AtualizarTelaPing()).start();
            int tempo2 = Integer.parseInt(TelaPrincipal.PingCount.getValue().toString());
            new Thread(new AtualizaBarraPing(tempo2)).start();
        }
    }
  class ConnectListenerTCPDW implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(new AtualizarTelaTCPDW()).start();  
            int tempo2 = Integer.parseInt(TelaPrincipal.UDPQTD.getValue().toString());
            new Thread(new AtualizaBarraTCPDW(tempo2)).start();
        }
    }
  class ConnectListenerTCPUP implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(new AtualizarTelaTCPUP()).start();
            int tempo2 = Integer.parseInt(TelaPrincipal.UDPQTD.getValue().toString());
            new Thread(new AtualizaBarraTCPUP(tempo2)).start();
        }
    }
  class ConnectListenerUDPDW implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(new AtualizarTelaUDPDW()).start();
            int tempo2 = Integer.parseInt(TelaPrincipal.UDPQTD.getValue().toString());
            new Thread(new AtualizaBarraUDPDW(tempo2)).start();
        }
    }
  class ConnectListenerUDPUP implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(new AtualizarTelaUDPUP()).start();
            int tempo2 = Integer.parseInt(TelaPrincipal.UDPQTD.getValue().toString());
            new Thread(new AtualizaBarraUDPUP(tempo2)).start();
        }
    }
  class ConnectListenerTODOS implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(new ExecutaEspera()).start();
            int tempo1 = Integer.parseInt(TelaPrincipal.UDPQTD.getValue().toString());
            int tempo2 = Integer.parseInt(TelaPrincipal.PingCount.getValue().toString());
            int tempoFinal;
            tempoFinal = tempo1*4+tempo2;
            new Thread(new AtualizaProgresso(tempoFinal)).start();
            new Thread(new AtualizaTodasBarras()).start();
        }
    }
  class ConnectListenerGerarRelatorio implements ActionListener{
      @Override
        public void actionPerformed(ActionEvent e) {
                TelaRelatorio.setVisible(true);
        }
}
  class ConnectListenerVerGraficos implements ActionListener{
      @Override
        public void actionPerformed(ActionEvent e) {
            if(TelaPrincipal.ProgressoTotal.getValue()>98){
                Tratamento.show();
            }
            else TelaPrincipal.mostrarErro("Teste ainda não concluido!");
        }
  }
  
    class ConnectListenerVerGraficosREPORT implements ActionListener{
      @Override
        public void actionPerformed(ActionEvent e) {
            if(TelaPrincipal.ProgressoTotal.getValue()>98){
                Tratamento.show();
            }
            else TelaPrincipal.mostrarErro("Teste ainda não concluido!");
        }
  }
    
    class ConnectListenerCriarREPORT implements ActionListener{
      @Override
        public void actionPerformed(ActionEvent e) {
            CriarPDF();
        }
  }
 
//essa thread espera as outras terminarem


public class AtualizarTelaPing implements Runnable {

    private volatile boolean running = true;

    public void terminate() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            List<String> commands = new ArrayList<String>();
            commands.add("ping");
            commands.add(TelaPrincipal.getIP());
            commands.add("-n");
            commands.add(TelaPrincipal.getPingCount());
            TelaPrincipal.addTextOutput("\nTeste de Ping: ");
            try {
                SaidaPing = doCommandPing(commands);
            } catch (IOException ex) {
                TelaPrincipal.mostrarErro("Erro ao tentar realizar o Ping");
            }
            Tratamento.setSaidaPing(SaidaPing);
            Tratamento.setTamanhoPing((int) TelaPrincipal.PingCount.getValue());
            if(SaidaPing.contains("limite")){
                Tratamento.TratarPing();
            }
            else {
                int sucesso = Tratamento.TratarPingString();
            }
            TelaRelatorio.latenciaBOX.setText(String.valueOf(Tratamento.getLatencia()));
            TelaRelatorio.perdasBOX.setText(String.valueOf(Tratamento.getPerdas()));
            terminate();
        }

    }
}
public class AtualizarTelaTCPDW implements Runnable {

    private volatile boolean running = true;

    public void terminate() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            List<String> commands = new ArrayList<String>();
            //iperf3 -c 2001:12f0:700::48 -R -i 1 -f m -t 60 (DOWN)
            //iperf3 -c 200.137.0.48 -R -i 1 -f m -t 60 (DOWN)
            commands.add(TelaPrincipal.getCaminho()+"\\iperf3");
            commands.add("-c");
            commands.add(TelaPrincipal.getIP());
            commands.add("-R");
            commands.add("-i");
            commands.add("1");
            commands.add("-f");
            commands.add("m");
            commands.add("-t");
            commands.add(TelaPrincipal.UDPQTD.getValue().toString());
            TelaPrincipal.addTextOutput("\nTeste de TCP DOWNLOAD: \n");

            try {
                SaidaTCPDW = doCommandTCPDW(commands);
            } catch (IOException ex) {
                TelaPrincipal.mostrarErro("Erro ao tentar realizar o teste de TCP DOWN");
            }
            Tratamento.setSaidaTCPDW(SaidaTCPDW);
            Tratamento.setTamanho(Integer.valueOf(Integer.valueOf(TelaPrincipal.UDPQTD.getValue().toString())));
            Tratamento.TratarTCPDW();
            TelaRelatorio.idTCPDW.setText(String.valueOf(Tratamento.getIDTCPDW()));
            TelaRelatorio.bandaTCPDW.setText(String.valueOf(Tratamento.getBandaTCPDW()));
            TelaRelatorio.intervaloTCPDW.setText(TelaPrincipal.UDPQTD.getValue().toString());
            TelaRelatorio.transferidosTCPDW.setText(String.valueOf(Tratamento.getTransferidosTCPDW()));
            //Tratamento.CriarGraficos();
            //Tratamento.show();
            terminate();
        }

    }
}
public class AtualizarTelaTCPUP implements Runnable {

    private volatile boolean running = true;

    public void terminate() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            List<String> commands = new ArrayList<String>();
            //iperf3 -c 2001:12f0:700::48 -i 1 -f m -t 60 (UP)
            //iperf3 -c 200.137.0.48 -i 1 -f m -t 60 (UP)
            commands.add(TelaPrincipal.getCaminho()+"\\iperf3");
            commands.add("-c");
            commands.add(TelaPrincipal.getIP());
            commands.add("-i");
            commands.add("1");
            commands.add("-f");
            commands.add("m");
            commands.add("-t");
            commands.add(TelaPrincipal.UDPQTD.getValue().toString());
            TelaPrincipal.addTextOutput("\nTeste de TCP UPLOAD: \n");

            try {
                SaidaTCPUP = doCommandTCPUP(commands);
            } catch (IOException ex) {
                TelaPrincipal.mostrarErro("Erro ao tentar realizar o teste de TCP UP");
            }
            Tratamento.setSaidaTCPUP(SaidaTCPUP);
            Tratamento.setTamanho(Integer.valueOf(Integer.valueOf(TelaPrincipal.UDPQTD.getValue().toString())));
            Tratamento.TratarTCPUP();
            TelaRelatorio.idTCPUP.setText(String.valueOf(Tratamento.getIDTCPUP()));
            TelaRelatorio.bandaTCPUP.setText(String.valueOf(Tratamento.getBandaTCPUP()));
            TelaRelatorio.intervaloTCPUP.setText(TelaPrincipal.UDPQTD.getValue().toString());
            TelaRelatorio.transferidosTCPUP.setText(String.valueOf(Tratamento.getTransferidosTCPUP()));
            
            //Tratamento.show();
            terminate();
        }

    }
}
public class AtualizarTelaUDPDW implements Runnable {

    private volatile boolean running = true;

    public void terminate() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            List<String> commands = new ArrayList<String>();
            //iperf3 -c 2001:12f0:700::48 -u -R -i 1 -f m -t 60 -b 5m (DOWN)
            //iperf3 -c 200.137.0.48 -u -R -i 1 -f m -t 60 -b 5m (DOWN)
            commands.add(TelaPrincipal.getCaminho()+"\\iperf3");
            commands.add("-c");
            commands.add(TelaPrincipal.getIP());
            commands.add("-u");
            commands.add("-R");
            commands.add("-i");
            commands.add("1");
            commands.add("-f");
            commands.add("m");
            commands.add("-t");
            commands.add(TelaPrincipal.UDPQTD.getValue().toString());
            commands.add("-b");
            commands.add(TelaPrincipal.UDPDW.getText()+"m");
            TelaPrincipal.addTextOutput("\nTeste de UDP DOWNLOAD: \n");

            try {
                SaidaUDPDW = doCommandUDPDW(commands);
            } catch (IOException ex) {
                TelaPrincipal.mostrarErro("Erro ao tentar realizar o teste de UDP DOWN");
            }
            Tratamento.setSaidaUDPDW(SaidaUDPDW);
            Tratamento.setTamanho(Integer.valueOf(Integer.valueOf(TelaPrincipal.UDPQTD.getValue().toString())));
            int sucesso = Tratamento.TratarUDPDWSting();
            if(sucesso==0){
                TelaPrincipal.mostrarErro("Erro na criação do gráfico UDP!\nFeche o programa e tente novamente");
            }
            //int k2 = Tratamento.TratarUDPDWSting();
            //Tratamento.TratarUDPDW();
            TelaRelatorio.intervaloUDPDW.setText(TelaPrincipal.UDPQTD.getValue().toString());
            TelaRelatorio.idUDPDW.setText(String.valueOf(Tratamento.getIDUDPDW()));
            TelaRelatorio.jitterUDPDW.setText(String.valueOf(Tratamento.getJitterUDPDW()));
            TelaRelatorio.perdasUDPDW.setText(String.valueOf(Tratamento.getPerdasUDPDW()));
            TelaRelatorio.transferidosUDPDW.setText(String.valueOf(Tratamento.getTransferidosUDPDW()));
            TelaRelatorio.bandaUDPDW.setText(String.valueOf(Tratamento.getBandaUDPDW()));
            terminate();
        }

    }
}
public class AtualizarTelaUDPUP implements Runnable {

    private volatile boolean running = true;

    public void terminate() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            List<String> commands = new ArrayList<String>();
//iperf3 -c 2001:12f0:700::48 -u -i 1 -f m -t 60 -b 2m (UP)
//iperf3 -c 200.137.0.48 -u -i 1 -f m -t 60 -b 2m (UP)
            commands.add(TelaPrincipal.getCaminho()+"\\iperf3");
            commands.add("-c");
            commands.add(TelaPrincipal.getIP());
            commands.add("-u");
            commands.add("-i");
            commands.add("1");
            commands.add("-f");
            commands.add("m");
            commands.add("-t");
            commands.add(TelaPrincipal.UDPQTD.getValue().toString());
            commands.add("-b");
            commands.add(TelaPrincipal.UDPUP.getText()+"m");
            TelaPrincipal.addTextOutput("\nTeste de UDP UPLOAD: \n");

            try {
                SaidaUDPUP = doCommandTCPDW(commands);
            } catch (IOException ex) {
                TelaPrincipal.mostrarErro("Erro ao tentar realizar o teste de UDP UP");
            }
            Tratamento.setSaidaUDPUP(SaidaUDPUP);
            Tratamento.setTamanho(Integer.valueOf(Integer.valueOf(TelaPrincipal.UDPQTD.getValue().toString())));
            //Tratamento.TratarUDPUP();
            int sucesso = Tratamento.TratarUDPUPSting();
            if(sucesso==0) TelaPrincipal.mostrarErro("Erro na criação do gráfico UDP!\nFeche o programa e tente novamente");
            TelaRelatorio.intervaloUDPUP.setText(TelaPrincipal.UDPQTD.getValue().toString());
            TelaRelatorio.idUDPUP.setText(String.valueOf(Tratamento.getIDUDPUP()));
            TelaRelatorio.jitterUDPUP.setText(String.valueOf(Tratamento.getJitterUDPUP()));
            TelaRelatorio.perdasUDPUP.setText(String.valueOf(Tratamento.getPerdasUDPUP()));
            TelaRelatorio.transferidosUDPUP.setText(String.valueOf(Tratamento.getTransferidosUDPUP()));
            TelaRelatorio.bandaUDPUP.setText(String.valueOf(Tratamento.getBandaUDPUP()));
            terminate();
        }

    }
}
public class AtualizaProgresso implements Runnable {

    private volatile boolean running = true;
       private int to;
      public AtualizaProgresso(int to) {
          this.to = to;
      }
    public void terminate() {
        running = false;
    }

    @Override
    public void run() {
        int n=0;
        int k = (int) ceil((double)100/to);
        float k2 = (float)100/to;
        int dormir;
            if(k2<1){
                 dormir =(int) ceil((double)1000/k2);
            }
            else{
                dormir = 1000;
            }
        while (running) {
            TelaPrincipal.ProgressoTotal.setStringPainted(true);

            while (n <= 105){
                TelaPrincipal.ProgressoTotal.setValue(n);
                try {
                    Thread.sleep(dormir);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TesteConexao.class.getName()).log(Level.SEVERE, null, ex);
                }
                n+=k;
            }
            TelaPrincipal.mostrarAviso("Teste Finalizado com Sucesso!");
            if(TelaPrincipal.ProgressoTotal.getValue()>98){
                try {
                    Tratamento.CriarGraficos();
                } catch (IOException ex) {
                    Logger.getLogger(TesteConexao.class.getName()).log(Level.SEVERE, null, ex);
                    TelaPrincipal.mostrarErro("Erro na Criacao dos Graficos!");
                }
                Tratamento.show();
            }
            else TelaPrincipal.mostrarErro("Teste ainda não concluido!");
            terminate();
        }

    }
}
public class AtualizaBarraPing implements Runnable {

    private volatile boolean running = true;
       private int to, barra;
      public AtualizaBarraPing(int to) {
          this.to = to;
      }
    public void terminate() {
        running = false;
    }

    @Override
    public void run() {
        int n=0;
        int k = (int) ceil((double)100/to);
        float k2 = (float)100/to;
        int dormir;
            if(k2<1){
                 dormir =(int) ceil((double)1000/k2);
            }
            else{
                dormir = 1000;
            }
        while (running) {
            while (n <= 100){

                        TelaPrincipal.BarraPing.setValue(n);

                try {
                    Thread.sleep(dormir);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TesteConexao.class.getName()).log(Level.SEVERE, null, ex);
                }
                n+=k;
            }
            terminate();
        }

    }
}
public class AtualizaBarraTCPDW implements Runnable{

    private volatile boolean running = true;
       private int to;
      public AtualizaBarraTCPDW(int to) {
          this.to = to;
      }
    public void terminate() {
        running = false;
    }

    @Override
    public void run() {
        int n=0;
        int k = (int) ceil((double)100/to);
        float k2 = (float)100/to;
        int dormir;
            if(k2<1){
                 dormir =(int) ceil((double)1000/k2);
            }
            else{
                dormir = 1000;
            }
        while (running) {

            while (n <= 100){
                    TelaPrincipal.BarraTCPDW.setValue(n);
                try {
                    Thread.sleep(dormir);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TesteConexao.class.getName()).log(Level.SEVERE, null, ex);
                }
                n+=k;
            }
            terminate();
        }

    }
}
public class AtualizaBarraTCPUP implements Runnable{

    private volatile boolean running = true;
       private int to;
      public AtualizaBarraTCPUP(int to) {
          this.to = to;
      }
    public void terminate() {
        running = false;
    }

    @Override
    public void run() {
        int n=0;
        int k = (int) ceil((double)100/to);
        float k2 = (float)100/to;
        int dormir;
            if(k2<1){
                 dormir =(int) ceil((double)1000/k2);
            }
            else{
                dormir = 1000;
            }
        while (running) {


            while (n <= 100){
            TelaPrincipal.BarraTCPUP.setValue(n);
                try {
                    Thread.sleep(dormir);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TesteConexao.class.getName()).log(Level.SEVERE, null, ex);
                }
                n+=k;
            }
            terminate();
        }

    }
}
public class AtualizaBarraUDPDW implements Runnable{

    private volatile boolean running = true;
       private int to;
      public AtualizaBarraUDPDW(int to) {
          this.to = to;
      }
    public void terminate() {
        running = false;
    }

    @Override
    public void run() {
        int n=0;
        int k = (int) ceil((double)100/to);
        float k2 = (float)100/to;
        int dormir;
            if(k2<1){
                 dormir =(int) ceil((double)1000/k2);
            }
            else{
                dormir = 1000;
            }
        while (running) {


            while (n <= 100){
            TelaPrincipal.BarraUDPDW.setValue(n);
                try {
                    Thread.sleep(dormir);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TesteConexao.class.getName()).log(Level.SEVERE, null, ex);
                }
                n+=k;
            }
            terminate();
        }

    }
}
public class AtualizaBarraUDPUP implements Runnable{

    private volatile boolean running = true;
       private int to;
      public AtualizaBarraUDPUP(int to) {
          this.to = to;
      }
    public void terminate() {
        running = false;
    }

    @Override
    public void run() {
        int n=0;
        int k = (int) ceil((double)100/to);
        float k2 = (float)100/to;
        int dormir;
            if(k2<1){
                 dormir =(int) ceil((double)1000/k2);
            }
            else{
                dormir = 1000;
            }
        while (running) {

            while (n <= 100){
            TelaPrincipal.BarraUDPUP.setValue(n);
                try {
                    Thread.sleep(dormir);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TesteConexao.class.getName()).log(Level.SEVERE, null, ex);
                }
                n+=k;
            }
            terminate();
        }

    }
}
        
public String getSaidaPing(){
    return this.SaidaPing;
}
public String getSaidaTCPDW(){
    return this.SaidaTCPDW;
}
public String getSaidaTCPUP(){
    return this.SaidaTCPUP;
}
public String getSaidaUDPDW(){
    return this.SaidaUDPDW;
}
public String getSaidaUDPUP(){
    return this.SaidaUDPUP;
}

public class ExecutaEspera implements Runnable {

    private volatile boolean running = true;

    public void terminate() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
        int n = 5;
        // Build a fixed number of thread pool
        ExecutorService pool = Executors.newFixedThreadPool(n);
        // Wait until One finishes it's task.
            try {
                pool.submit(new AtualizarTelaPing()).get();
            } catch (InterruptedException ex) {
                Logger.getLogger(TesteConexao.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(TesteConexao.class.getName()).log(Level.SEVERE, null, ex);
            }
        
            try {
                pool.submit(new AtualizarTelaTCPDW()).get();
            } catch (InterruptedException ex) {
                Logger.getLogger(TesteConexao.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(TesteConexao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                pool.submit(new AtualizarTelaTCPUP()).get();
            } catch (InterruptedException ex) {
                Logger.getLogger(TesteConexao.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(TesteConexao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                pool.submit(new AtualizarTelaUDPDW()).get();
            } catch (InterruptedException ex) {
                Logger.getLogger(TesteConexao.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(TesteConexao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                pool.submit(new AtualizarTelaUDPUP()).get();
            } catch (InterruptedException ex) {
                Logger.getLogger(TesteConexao.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(TesteConexao.class.getName()).log(Level.SEVERE, null, ex);
            }
        // Wait until Two finishes it's task.
        pool.shutdown();
            terminate();
        }

    }
}



public class AtualizaTodasBarras implements Runnable {

    private volatile boolean running = true;

    public void terminate() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
        TelaPrincipal.BarraPing.setValue(0);
        TelaPrincipal.BarraTCPDW.setValue(0);
        TelaPrincipal.BarraTCPUP.setValue(0);
        TelaPrincipal.BarraUDPDW.setValue(0);
        TelaPrincipal.BarraUDPUP.setValue(0);
        int n = 5;
        // Build a fixed number of thread pool
        ExecutorService pool = Executors.newFixedThreadPool(n);
        int tempo2 = Integer.parseInt(TelaPrincipal.PingCount.getValue().toString());
        int tempo3 = Integer.parseInt(TelaPrincipal.UDPQTD.getValue().toString());
        // Wait until One finishes it's task.
            try {
                pool.submit(new AtualizaBarraPing(tempo2)).get();
            } catch (InterruptedException ex) {
                Logger.getLogger(TesteConexao.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(TesteConexao.class.getName()).log(Level.SEVERE, null, ex);
            }
        
            try {
                pool.submit(new AtualizaBarraTCPDW(tempo3)).get();
            } catch (InterruptedException ex) {
                Logger.getLogger(TesteConexao.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(TesteConexao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                pool.submit(new AtualizaBarraTCPUP(tempo3)).get();
            } catch (InterruptedException ex) {
                Logger.getLogger(TesteConexao.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(TesteConexao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                pool.submit(new AtualizaBarraUDPDW(tempo3)).get();
            } catch (InterruptedException ex) {
                Logger.getLogger(TesteConexao.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(TesteConexao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                pool.submit(new AtualizaBarraUDPUP(tempo3)).get();
            } catch (InterruptedException ex) {
                Logger.getLogger(TesteConexao.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(TesteConexao.class.getName()).log(Level.SEVERE, null, ex);
            }
        // Wait until Two finishes it's task.
        pool.shutdown();
            terminate();
        }

    }
}

/*----------------------------PARTE DOS PDF------------------*/

public static void CriarPDF(){
if(TelaPrincipal.ProgressoTotal.getValue()<98){
    TelaPrincipal.mostrarErro("Erro na criação do PDF!\nTeste não finalizado!");
}
else{
try {
    //criacao do documento
  Document documento = new Document();
  OutputStream os = null;
    //criacao do PDF
    os = new FileOutputStream(TelaRelatorio.nomeLocal.getText() + "_" 
            + TelaRelatorio.cidade.getSelectedItem().toString() + ".pdf");
    
  //Define as fontes
  Font fontValor = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
        Font fontCampo = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
        Font font2 = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
        Font fontTitle = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        Font fontParagraphOutput = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);  
        
  // adiciona imagem ao pdf


    PdfPTable tableTitulo = new PdfPTable(3);
    //tableImagem.addCell(Logotipo);

    
    Paragraph title = new Paragraph("\n" +
                "Centro de Operações @Jaime Dantas" + "\n" +
                 "Tesde de Banda " + "\n" + "\n" ,fontTitle);
    title.setAlignment(Paragraph.ALIGN_CENTER);
       
        //ADICIONA DADOS
    PdfPTable table = new PdfPTable(3);

    Paragraph titleDados = new Paragraph(
            "Dados da Instrituição" + "\n" ,font2);
    PdfPCell headerDados = new PdfPCell(titleDados);
    headerDados.setColspan(3);
    headerDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
    table.addCell(headerDados);
    

    Paragraph titleEscola = new Paragraph(
            "Instutuição: ", fontValor);
    titleEscola.add(TelaRelatorio.nomeLocal.getText().toUpperCase());
    PdfPCell headerNomeEscola = new PdfPCell(titleEscola);
    headerNomeEscola.setColspan(3);
    headerNomeEscola.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
    table.addCell(headerNomeEscola);
    
    Paragraph titleLocal = new Paragraph(
            "Cidade: ", fontValor);
    titleLocal.add(TelaRelatorio.cidade.getSelectedItem().toString().toUpperCase());
    PdfPCell headerCidade = new PdfPCell(titleLocal);
    headerCidade.setColspan(3);
    headerCidade.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
    table.addCell(headerCidade);
    
    Paragraph titleEstado = new Paragraph(
            "Estado: ", fontValor);
    titleEstado.add(TelaRelatorio.UF.getSelectedItem().toString().toUpperCase());
    PdfPCell headerUF = new PdfPCell(titleEstado);
    headerUF.setColspan(3);
    headerUF.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
    table.addCell(headerUF);
    
    Paragraph titleOLT = new Paragraph(
            "Servidor: ", fontValor);
    titleOLT.add(TelaRelatorio.oltConecata.getSelectedItem().toString().toUpperCase());
    PdfPCell headerolt = new PdfPCell(titleOLT);
    headerolt.setColspan(3);
    headerolt.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
    table.addCell(headerolt);
    
    Paragraph titleBolsista = new Paragraph(
            "Responsável: ", fontValor);
    titleBolsista.add(TelaRelatorio.bolsista.getText().toUpperCase());
    PdfPCell headeBolsista = new PdfPCell(titleBolsista);
    headeBolsista.setColspan(3);
    headeBolsista.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
    table.addCell(headeBolsista);
    
    Paragraph titleOBS = new Paragraph(
            "Observações: ", fontValor);
    titleOBS.add(TelaRelatorio.observacoes.getText());
    PdfPCell headeobs = new PdfPCell(titleOBS);
    headeobs.setColspan(3);
    headeobs.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
    table.addCell(headeobs);
    
    PdfPTable tableTCPheather = new PdfPTable(1);
    
    Paragraph titleTCP = new Paragraph(
            "Teste TCP" + "\n" ,font2);
    PdfPCell headerTCP = new PdfPCell(titleTCP);
    headerTCP.setColspan(3);
    headerTCP.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
    tableTCPheather.addCell(headerTCP);
    
    PdfPTable tableTCP = new PdfPTable(2);

    
    Paragraph nomeTCP = new Paragraph(
            "TCP DOWN", fontValor);
    nomeTCP.setAlignment(Paragraph.ALIGN_JUSTIFIED);
    tableTCP.addCell(nomeTCP);
    Paragraph nomeTCPUP = new Paragraph(
            "TCP UP", fontValor);
    nomeTCPUP.setAlignment(Paragraph.ALIGN_JUSTIFIED);
    tableTCP.addCell(nomeTCPUP);
    tableTCP.addCell("ID do Processo: "+Tratamento.getIDTCPDW());
    tableTCP.addCell("ID do Processo: "+Tratamento.getIDTCPUP());
    tableTCP.addCell("Intervalo: "+TelaPrincipal.UDPQTD.getValue()+" s");
    tableTCP.addCell("Intervalo: "+TelaPrincipal.UDPQTD.getValue()+" s");
    tableTCP.addCell("Transferidos: "+Tratamento.getTransferidosTCPDW()+" MB");
    tableTCP.addCell("Transferidos: "+Tratamento.getTransferidosTCPUP()+" MB");
    tableTCP.addCell("Banda: "+Tratamento.getBandaTCPDW()+" Mb/s");
    tableTCP.addCell("Banda: "+Tratamento.getBandaTCPUP()+" Mb/s");
    
    //------udp
    
    Paragraph titleUDP = new Paragraph(
            "Teste UDP" + "\n" ,font2);
    PdfPCell headerUDP = new PdfPCell(titleUDP);
    headerUDP.setColspan(3);
    headerUDP.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
    PdfPTable tableUDPh = new PdfPTable(1);
    tableUDPh.addCell(headerUDP);
    
    PdfPTable tableUDP = new PdfPTable(2);

    Paragraph nomeUDP = new Paragraph(
            "UDP DOWN", fontValor);
    nomeUDP.setAlignment(Paragraph.ALIGN_JUSTIFIED);
    tableUDP.addCell(nomeUDP);
    Paragraph nomeUDPUP = new Paragraph(
            "UDP UP", fontValor);
    nomeUDPUP.setAlignment(Paragraph.ALIGN_JUSTIFIED);
    tableUDP.addCell(nomeUDPUP);
    tableUDP.addCell("ID do Processo: "+Tratamento.getIDUDPDW());
    tableUDP.addCell("ID do Processo: "+Tratamento.getIDUDPUP());
    tableUDP.addCell("Intervalo: "+TelaPrincipal.UDPQTD.getValue()+" s");
    tableUDP.addCell("Intervalo: "+TelaPrincipal.UDPQTD.getValue()+" s");
    tableUDP.addCell("Transferidos: "+Tratamento.getTransferidosUDPDW()+" MB");
    tableUDP.addCell("Transferidos: "+Tratamento.getTransferidosUDPUP()+" MB");
    tableUDP.addCell("Banda: "+Tratamento.getBandaUDPDW()+" Mb/s");
    tableUDP.addCell("Banda: "+Tratamento.getBandaUDPUP()+" Mb/s");
    tableUDP.addCell("Jitter: "+Tratamento.getJitterUDPDW()+" ms");
    tableUDP.addCell("Jitter: "+Tratamento.getJitterUDPUP()+" ms");
    tableUDP.addCell("Perdas Datagrama: "+Tratamento.getPerdasUDPDW()+" %");
    tableUDP.addCell("Perdas Datagrama: "+Tratamento.getPerdasUDPUP()+" %");

    Paragraph latencia = new Paragraph(
            "Teste Ping" + "\n" ,font2);
    PdfPCell latenciaH = new PdfPCell(latencia);
    latenciaH.setColspan(3);
    latenciaH.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
    PdfPTable tablePingh = new PdfPTable(1);
    tablePingh.addCell(latenciaH);

    PdfPTable tablePing = new PdfPTable(2);

    tablePing.addCell("Latência: "+Tratamento.getLatencia()+" ms");
    tablePing.addCell("Perdas: "+Tratamento.getPerdas()+" %");

    
    Paragraph espaco = new Paragraph(
             "\n" ,font2);
    
    Calendar calendar = new GregorianCalendar();  
         Date trialTime = new Date();  
         calendar.setTime(trialTime);
         Paragraph DIA = new Paragraph("Data: " + calendar.get(Calendar.DATE) + "/" + (calendar.get(Calendar.MONTH)+1) + "/" + calendar.get(Calendar.YEAR));
         Paragraph HORA = new Paragraph("Hora: " + calendar.get(Calendar.HOUR_OF_DAY)+ ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND));
 
         
    Image GraficoTCP = Image.getInstance("../Graficos/GraficoTCP.png");
    GraficoTCP.scalePercent((float) 50);
    Image GraficoUDP = Image.getInstance("../Graficos/GraficoUDP.png");
    GraficoUDP.scalePercent((float) 50);
    Image GraficoPing = Image.getInstance("../Graficos/GraficoPing.png");
    GraficoPing.scalePercent((float) 50);
    
    PdfPTable tableSaida = new PdfPTable(1);

    tableSaida.addCell(TelaPrincipal.OutputArea.getText());
    
  PdfWriter.getInstance(documento, os);
  documento.open();
  documento.add(title);
  documento.add(table);
  documento.add(espaco);
  documento.add(tableTCPheather);
 // documento.add(espaco);
  documento.add(tableTCP);
  documento.add(espaco);
  documento.add(tableUDPh);
  documento.add(tableUDP);
  documento.add(espaco);
  documento.add(tablePingh);
  documento.add(tablePing);
  documento.add(espaco);
  documento.add(DIA);
  documento.add(HORA);
  documento.add(GraficoTCP);
  documento.add(espaco);
  documento.add(GraficoUDP);
  documento.add(espaco);
  documento.add(GraficoPing);
  documento.add(espaco);
  documento.add(espaco);
  documento.add(tableSaida);
  //documento.add(new Paragraph());
  //documento.add(tableDADOS);

  
  documento.close();

  TelaPrincipal.mostrarAviso("Relatório criado com sucesso!");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
      TelaPrincipal.mostrarErro("Erro na criação do PDF!");
    }
}
}


}