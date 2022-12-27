import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args){
    final ServerSocket serverSocket;
    final Socket clientSocket;
    final BufferedReader in;
    final PrintWriter out;
    final Scanner sc=new Scanner(System.in);

    //constructore of ServerSocket class take port number that the server will use to listen to client's request
     //the serversocket is used by the server to listen to connection requests from client
       //new InputStreamReader( clientSocket.getInputStream()) : creates a stream reader for the socket. However this stream reader only reads data as Bytes , therefore it must be passed to BufferedReader to be converted into characters.

       try{
           serverSocket =new ServerSocket(5000);
           clientSocket =serverSocket.accept();
           out =new PrintWriter(clientSocket.getOutputStream());
           in =new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
       //Sender thread : it contains the code the server will use to send messages to client
           Thread sender =new Thread(new Runnable() {
               String msg ;//that cointains data writer by the user
               @Override
               public void run() {
                   while(true){
                     msg =sc.nextLine();
                     out.println(msg); //write data stored in msg in clientSocket
                     out.flush(); //forces the sending of data
                   }
               }
           });
           sender.start();
           //Recieve thread : it contains the code the server will use to recieve messages from client
           Thread receive =new Thread(new Runnable() {
               String msg;
               @Override
               public void run() {
           //code inside a try/catch so we can print any error related to reading data or closing sockets and streams.
                  try{
                        msg =in.readLine(); //read data from client
                      while(msg!=null){
                          System.out.println("Client : "+msg);
                          msg =in.readLine();
                      }
                      System.out.println("Client disconnected");
                      out.close();
                      clientSocket.close();
                      serverSocket.close();
                  } catch (IOException e){
                      e.printStackTrace();
                  }
               }
           });
           receive.start();
       }catch (IOException e){
           e.printStackTrace();
       }
    }
}
