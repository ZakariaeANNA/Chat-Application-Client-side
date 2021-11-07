/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import Interface.Invitation;

/**
 *
 * @author zakar
 */
public class ReadMsgInvitation extends Thread{
    String Username;
    JTable Table;
    Socket Client;
    
    public ReadMsgInvitation(String Username, JTable Table, Socket Client) {
        this.Username = Username;
        this.Table = Table;
        this.Client = Client;
        super.start();
    }

    @Override
    public void run() {
         BufferedReader fluxLecture = null;
         try{
            fluxLecture = new BufferedReader(new InputStreamReader(Client.getInputStream()));
         }catch(Exception e){
         }
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
                DefaultTableModel model = (DefaultTableModel) Table.getModel();
                String[] str = U.split(";");
                if(str[1].equals("Invit")){
                    String[] Invit = str[0].split(",");
                    if(Invit.length !=0 && !Invit[0].equals("")){
                        for(int i=0;i<Invit.length;i++){
                            model.addRow(new Object[]{Invit[i],"Accept","Decline"});
                        }
                    }
                    Table.setModel(model);
                }else if(str[1].equals("Chercher")){
                    JOptionPane.showMessageDialog(null, str[0], "Attention" , JOptionPane.INFORMATION_MESSAGE);
                }else if(str[1].equals("AddFriend")){
                    JOptionPane.showMessageDialog(null, str[0], "Attention" , JOptionPane.INFORMATION_MESSAGE);
                    model.removeRow(Table.getSelectedRow());
                }else if(str[1].equals("Message")){
                     String[] Msg = str[0].split(",");     
                     ReadMsgBD.readFromTextToCSV(Username,Msg[2],Msg[0],Msg[3]);
                }
               }
            }catch(Exception e){

            }
        }
         
    }
    
    public void close(){
        this.interrupt();
    }
    
}
