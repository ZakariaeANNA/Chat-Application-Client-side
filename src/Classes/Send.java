/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.PrintStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTable;

/**
 *
 * @author zakar
 */
public class Send extends Thread{
    String Controle;
    String Username;
    Friends F;
    String msg;
    Socket Client;
    String Inviter;
    String Choice;
    JTable Table;
    
    public Send(String Username, Friends F, String msg,Socket Client,String Controle) {
        this.Username = Username;
        this.F = F;
        this.msg = msg;
        this.Client = Client;
        this.Controle = Controle;
        super.start();
    }
    
    public Send(String Username, String inviter, String value, Socket Client,JTable Table,String Controle) {
        this.Username = Username;
        this.Inviter = inviter;
        this.Choice = value;
        this.Client = Client;
        this.Table = Table;
        this.Controle = Controle;
        super.start();
    }
    
    public Send(String Username, String Inviter, Socket Client,String Controle) {
        this.Username = Username;
        this.Inviter = Inviter;
        this.Client = Client;
        this.Controle = Controle;
        super.start();
    }
   
    public Send(String Username, Socket Client,String Controle) {
        this.Username = Username;
        this.Client = Client;
        this.Controle = Controle;
        super.start();
    }

    @Override
    public void run() {
        try{
            PrintStream fluxEcriture = new PrintStream(Client.getOutputStream());
            if(this.Controle.equals("Message")){
                String[] M = msg.split(","); 
                fluxEcriture.println(Username+"-"+F.getFriend()+"-"+M[0]+"-"+M[1]+";Message");
                ReadMsgBD.sendTextToCSV(Username,M[0],F.getFriend(),M[1]);
            }else if(this.Controle.equals("AddFriend")){
                fluxEcriture.println(this.Username+","+Inviter+","+Choice+";AddFriend");
            }else if(this.Controle.equals("Chercher")){
                fluxEcriture.println(this.Username+","+Inviter+";Chercher");
            }else if(this.Controle.equals("Close")){
                fluxEcriture.println(this.Username+";close");
                this.Client.close();
                System.exit(0);
            }else if(this.Controle.equals("Invitation")){
                fluxEcriture.println(this.Username+";Invitation");
            }
            
        }catch(Exception e){

        }
        this.interrupt();
    }
    
    
    
}
