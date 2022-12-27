import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args){
         final Socket clientSocket; //socket udes by client to send and recibe data from server
         final BufferedReader in; //to read  data form socket
         final PrintWriter out;//to write data into socket
         final Scanner sc =new Scanner(System.in);
         try{
             clientSocket = new Socket("127.0.0.1",5000);
             out = new PrintWriter(clientSocket.getOutputStream());
             in =new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
         //Send thread
             Thread sender =new Thread(new Runnable() {
                 String msg;
                 @Override
                 public void run() {
                    while(true){
                        msg =sc.nextLine();
                        out.println(msg);
                        out.flush();
                    }
                 }
             });
             sender.start();
             Thread recevier =new Thread(new Runnable() {
                 String msg;
                 @Override
                 public void run() {
                     try {
                         msg =in.readLine();
                        while(msg!=null){
                            System.out.println("Server : "+msg);
                            msg =in.readLine();
                        }
                        System.out.println("Server out of service");
                        out.close();
                        clientSocket.close();
                     }catch (IOException e){
                         e.printStackTrace();
                     }
                 }
             });
             recevier.start();
         }catch(IOException e){
             e.printStackTrace();
         }

    }

}
