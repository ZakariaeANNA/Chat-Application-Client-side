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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import Interface.Home;

/**
 *
 * @author zakar
 */
public class Authentification extends Thread{
    Socket Client;
    String Username;
    String Password;
    String Confirm_Pass;
    String Auth;
    JFrame Login;

    public Authentification(JFrame Login,Socket Client, String Username, String Password,String Auth) {
        this.Login = Login;
        this.Client = Client;
        this.Username = Username;
        this.Password = Password;
        this.Auth = Auth;
        super.start();
    }

    public Authentification(JFrame Login,Socket Client, String Username, String Password, String Confirm_Pass,String Auth) {
        this.Login = Login;
        this.Client = Client;
        this.Username = Username;
        this.Password = Password;
        this.Confirm_Pass = Confirm_Pass;
        this.Auth = Auth;
        super.start();
    }
    
    public void Login(){
        PrintStream fluxEcriture = null;
        BufferedReader fluxLecture = null;
        try{
            fluxLecture = new BufferedReader(new InputStreamReader(Client.getInputStream()));
            fluxEcriture = new PrintStream(this.Client.getOutputStream());
            
        }catch(Exception e){
            
        }
        fluxEcriture.println(this.Username+","+this.Password+";Login");
        try{
            String line = fluxLecture.readLine();
            if(line.equals("true")){
                Home H = new Home(Username,Client);
                H.setVisible(true);
                Login.dispose();
            }else{
                JOptionPane.showMessageDialog(null, line, "Attention" , JOptionPane.INFORMATION_MESSAGE);
            }
        }catch(Exception e){
            
        }
    }
    
    public void Register(){
        if(this.Password.equals(this.Confirm_Pass)){
            PrintStream fluxEcriture = null;
            BufferedReader fluxLecture = null;
            try{
                fluxLecture = new BufferedReader(new InputStreamReader(Client.getInputStream()));
                fluxEcriture = new PrintStream(this.Client.getOutputStream());
            }catch(Exception e){
            }
            fluxEcriture.println(this.Username+","+this.Password+";Register");
            try{
                String line = fluxLecture.readLine();
                if(line.equals("true")){
                    Home H = new Home(Username,Client);
                    H.setVisible(true);
                    Login.dispose();
                }else{
                    JOptionPane.showMessageDialog(null, line, "Attention" , JOptionPane.INFORMATION_MESSAGE);
                }
            }catch(Exception e){

            }
        }else{
            JOptionPane.showMessageDialog(null, "Merci de remplir les champs d'une facon correcte ", "Attention" , JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public void run() {
        if(Auth.equals("Login")){
            this.Login();
        }else if(Auth.equals("Register")){
            this.Register();
        }
        this.stop();
    }
    
    
}
