/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import Interface.Home;
import javax.swing.JTable;
/**
 *
 * @author zakar
 */
public class ReadMsg extends Thread{
    
    Friends Friend;
    Socket Client;
    String Username;
    java.awt.List Chat;
    
    public ReadMsg(String Username,Socket Client,Friends Friend,java.awt.List Chat){
        this.Client = Client;
        this.Friend = Friend;
        this.Chat = Chat;
        this.Username = Username;
        super.start();
    }
    
    @Override
    public void run() {
        BufferedReader fluxLecture = null;
        try{
            fluxLecture = new BufferedReader(new InputStreamReader(Client.getInputStream()));
            while(true){
                try{
                    String U = null;
                    if(fluxLecture.ready()){
                        U = fluxLecture.readLine();
                    }
                    if (this.interrupted()) {  
                        break;
                    }
                    if(U!=null){
                        String[] str = U.split(";");
                        if(str[1].equals("Message")){
                            String[] Msg = str[0].split(",");           
                            if(Msg[0].equals(Friend.getFriend())){ 
                                Chat.add(Msg[1]+" : "+Msg[2]+"     <"+Msg[3]+">");
                                ReadMsgBD.readFromTextToCSV(Username,Msg[2],Friend.getFriend(),Msg[3]);
                            }else{
                                ReadMsgBD.readFromTextToCSV(Username,Msg[2],Msg[0],Msg[3]);
                            }
                        }
                    }
                }catch(Exception e){
                }
            }
        }catch(Exception e){
            
        }
    }
    
    public void close(){
        this.interrupt();
    }
}
