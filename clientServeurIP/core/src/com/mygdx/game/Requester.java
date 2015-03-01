package com.mygdx.game;

import java.io.*;
import java.net.*;
public class Requester{
    Socket requestSocket;
   
    String message;
    boolean Onestconnecte = false;
    
    
    
    OutputStream outToServer;
    DataOutputStream Out ;
    InputStream inFromServer;
    DataInputStream In;
    
    Requester(){}
    void run()
    {
        try{
        	
        	Onestconnecte = true;
            //1. creating a socket to connect to the server
            requestSocket = new Socket("localhost", 2004);
            System.out.println("Connected to localhost in port 2004");
            
            outToServer = requestSocket.getOutputStream();
            Out = new DataOutputStream(outToServer);
            
            inFromServer = requestSocket.getInputStream();
            In = new DataInputStream(inFromServer);
            System.out.println("On est connecte" );
            sendMessage("Hi my server");
            
           
            //}while(!message.equals("bye"));
        }
        catch(UnknownHostException unknownHost){
        	Onestconnecte = false;
            System.err.println("You are trying to connect to an unknown host!");
        }
        catch(IOException ioException){
        	Onestconnecte = false;
            ioException.printStackTrace();
        }
      
    }
    public void closeconnexion() {
    	 //4: Closing connection
        try{
        	In.close();
            Out.close();            
            requestSocket.close();
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
    }
    public void sendMessage(String msg) throws IOException
    {   
    	System.out.println("Envoi mess " + msg);
    	Out.writeUTF(msg);
    	Out.flush();
    	
    }
    public String LireMessage() throws IOException   {
       
         //System.out.println("Lecture message" );
    	 String Unmessage="";
    	 if (In.available() > 0){
    		 Unmessage = In.readUTF();
    	 }
    	  //System.out.println("Fin Lecture message" );
          return Unmessage;
          
    }
   
}
