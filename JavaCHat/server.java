import java.util.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
class server{

    ServerSocket servers;
    Socket socket;
    //initialising a constructor
    BufferedReader br;
    PrintWriter wr;
    public server()
    {
        try{
            servers=new ServerSocket(7777);
            System.out.println("Server WAITING........");
            socket=servers.accept();

            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            wr=new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

    }
//dono kaam simuntaneously therefore multi-threading
    public void startReading()
    {
        Runnable r1=()->{
            System.out.println("Reader started");
            try{
            while(!socket.isClosed())
            {
                
                    String msg=br.readLine();
                    if(msg.equals("exit"))
                    {
                        System.out.println("CLient terminated:......");
                        socket.close();
                        break;
                    }
                    System.out.println("Client:"+msg);
                }
                
            }
            catch(IOException e)
                {
                    System.out.println("Tata Bye Bye Khatam");

                }
        };
        new Thread(r1).start();
    }
    public void startWriting()
    {
        Runnable r2=()->{
            try {
            while(!socket.isClosed())
            {
                
                    BufferedReader b1=new BufferedReader(new InputStreamReader(System.in));
                    String content=b1.readLine();
                    wr.println(content);
                    wr.flush();
                    if(content.equals("exit"))
                    {
                        socket.close();
                        break;
                    }

                }
            }
            catch (Exception e) {
                // TODO: handle exception
                System.out.println("Tata Bye Bye Khatam");
            }
        };
        new Thread(r2).start();
    }
    public static void main(String[] args) {
        System.out.println("Working project");
        new server();
    }
}
