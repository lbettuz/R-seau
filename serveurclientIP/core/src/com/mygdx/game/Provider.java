package com.mygdx.game;

import java.io.*;
import java.net.*;
public class Provider{
    ServerSocket providerSocket;
    Socket connection = null;
   
    String message;
    boolean connected = false;
    
    
    OutputStream outToServer;
    DataOutputStream Out ;
    InputStream inFromServer;
    DataInputStream In;
    
    Provider(){}
    public void run()
    {
        try{
            //1. creating a server socket
            providerSocket = new ServerSocket(2004, 10);
            //2. Wait for connection
            System.out.println("Waiting for connection");
            connection = providerSocket.accept();
            System.out.println("Connection received from " + connection.getInetAddress().getHostName());
            
            //3. get Input and Output streams
            outToServer = connection.getOutputStream();
            Out = new DataOutputStream(outToServer);
            
            inFromServer = connection.getInputStream();
            In = new DataInputStream(inFromServer);
            System.out.println("On est connecte" );
            sendMessage("Ok from server");
            //4. The two parts communicate via the input and output streams
            //do{
            //    try{
            //        message = (String)in.readObject();
            //        System.out.println("client>" + message);
            //        if (message.equals("bye"))
            //            sendMessage("bye");
            //    }
            //    catch(ClassNotFoundException classnot){
            //        System.err.println("Data received in unknown format");
            //    }
            //}while(!message.equals("bye"));
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
        
    }
    public String LireMessage() throws IOException   {
    	 // System.out.println("Lecture message" );
     	 String Unmessage="";
     	 if (In.available() > 0){
     		 Unmessage = In.readUTF();
     	 }
     	 // System.out.println("Fin Lecture message" );
           
    	 
          return Unmessage;
          
    }
    public void closeConnect() {
    	//4: Closing connection
        try{
        	In.close();
            Out.close(); 
            providerSocket.close();
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
    }
    public void sendMessage(String msg)
    {
    	
        try{
        	System.out.println("Envoi mess " + msg);
        	Out.writeUTF(msg);
        	Out.flush();
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
    }
    
}
