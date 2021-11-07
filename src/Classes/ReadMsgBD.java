/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author zakar
 */
public class ReadMsgBD {
    
    public static void readFromDatabaseToCSV(String Username,Socket Client){
        String directoryPath = "Message/"+Username;
        File dossier = new File(directoryPath);
        if (!dossier.isDirectory()){
            boolean value = dossier.mkdirs();
        }
        PrintStream fluxEcriture = null;
        BufferedReader fluxLecture = null;
        try{
            fluxLecture = new BufferedReader(new InputStreamReader(Client.getInputStream()));
            fluxEcriture = new PrintStream(Client.getOutputStream());
        }catch(Exception e){
            
        }
        fluxEcriture.println(Username+";Message_DB");
        try{
            String line = fluxLecture.readLine();
            String[] str = line.split(";");
            if(str[1].equals("MessageDB")){
                String[] Messages = str[0].split("Sep");
                for(int i=0;i<Messages.length;i++){
                    String[] data = Messages[i].split(",");
                    try {
                        File conversation = new File(directoryPath+"/"+data[0]+"_"+data[1]+".csv");
                        if(!conversation.exists()){
                            conversation.createNewFile();
                        }
                        FileWriter pw = new FileWriter(directoryPath+"/"+data[0]+"_"+data[1]+".csv",true);
                        StringBuilder sb=new StringBuilder();
                        sb.append(data[1]);
                        sb.append(",");
                        sb.append(data[0]);
                        sb.append(",");
                        sb.append(data[2]);
                        sb.append(",");
                        sb.append(data[3]);
                        sb.append("\r\n");
                        pw.write(sb.toString());  
                        pw.close();
                     }catch(Exception e){

                     }
                }
            }
        }catch(Exception e){
            
        }
    }
    public static void readFromTextToCSV(String Username,String text,String Friend,String date) throws IOException{
        String directoryPath = "Message/"+Username;
        File dossier = new File(directoryPath);
        if (!dossier.isDirectory()){
            boolean value = dossier.mkdirs();
        }
        File conversation = new File(directoryPath+"/"+Username+"_"+Friend+".csv");
        if(!conversation.exists()){
            conversation.createNewFile();
        }
        try {
           FileWriter pw = new FileWriter(directoryPath+"/"+Username+"_"+Friend+".csv",true);
           StringBuilder sb=new StringBuilder();
           sb.append(Friend);
           sb.append(",");
           sb.append(Username);
           sb.append(",");
           sb.append(text);
           sb.append(",");
           sb.append(date);
           sb.append("\r\n");
           pw.write(sb.toString());
           pw.close();
        }catch(Exception e){

        }
    }
    
    public static void sendTextToCSV(String Username,String text,String Friend,String date) throws IOException{
        String directoryPath = "Message/"+Username;
        File dossier = new File(directoryPath);
        if (!dossier.isDirectory()){
            boolean value = dossier.mkdirs();
        }
        File conversation = new File(directoryPath+"/"+Username+"_"+Friend+".csv");
        if(!conversation.exists()){
            conversation.createNewFile();
        }
        try {
           FileWriter pw = new FileWriter(directoryPath+"/"+Username+"_"+Friend+".csv",true);
           StringBuilder sb=new StringBuilder();
           sb.append(Username);
           sb.append(",");
           sb.append(Friend);
           sb.append(",");
           sb.append(text);
           sb.append(",");
           sb.append(date);
           sb.append("\r\n");
           pw.write(sb.toString());
           pw.close();
        }catch(Exception e){

        }
    }
    
    public static ArrayList readFromCSVToText(String Username,String Friend){
            String directoryPath = "Message/"+Username;
            File dossier = new File(directoryPath);
            ArrayList MSG = new ArrayList();
            if(dossier.isDirectory()){
                String line = "";
                try {
                        File F = new File(directoryPath+"/"+Username+"_"+Friend+".csv");
                        if(F.exists()){
                            Scanner reader = new Scanner(F);
                            while(reader.hasNextLine()) {
                                line = reader.nextLine();
                                String[] L = line.split(",");
                                if(!(L[0].equals(Username))){
                                    MSG.add(L[0]+" : "+L[2]+"     <"+L[3]+">");
                                }else if((L[0].equals(Username))){
                                    MSG.add("Vous : "+L[2]+"     <"+L[3]+">");
                                }
                            }
                        }
                }catch(Exception e){

                }
            }
            return MSG;
    }
}
