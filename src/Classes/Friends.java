/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author zakar
 */
public class Friends {
    
    private String Username;
    private String Friend;

    public Friends(String Username){
        this.Username = Username;
    }

    public String getFriend() {
        return Friend;
    }

    public void setFriend(String Friend) {
        this.Friend = Friend;
    }
    
    public void ListFriends(JTable Table,Socket Client){
        PrintStream fluxEcriture = null;
        BufferedReader fluxLecture = null;
        try{
            fluxLecture = new BufferedReader(new InputStreamReader(Client.getInputStream()));
            fluxEcriture = new PrintStream(Client.getOutputStream());
            
        }catch(Exception e){
            
        }
        fluxEcriture.println(this.Username+";Friends");
        try{
            String line = fluxLecture.readLine();
            String[] str = line.split(";");
            if(str[1].equals("FriendsOfUser")){
                String[] Friends = str[0].split(",");
                DefaultTableModel model = (DefaultTableModel) Table.getModel();
                for(int i=0;i<Friends.length;i++){
                    String[] T = Friends[i].split("/");
                    model.addRow(new Object[]{T[0],T[1]});
                }
                Table.setModel(model);
            }
        }catch(Exception e){
            
        }
    }
}
