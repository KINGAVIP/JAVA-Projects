import java.io.*;
import java.net.Socket;
import java.util.*;

class client{

    Socket socket;
    BufferedReader br;
    PrintWriter wr;
    public client()
    {
        try{
            socket=new Socket("127.0.0.1",7777);
            System.out.println("Connection Established");
            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            wr=new PrintWriter(socket.getOutputStream());

            startWriting();
            startReading();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

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
                System.out.println("Tata Bye Bye Khatam");
                // TODO: handle exception
            }
        };
        new Thread(r2).start();
    }
    public void startReading()
    {
        Runnable r1=()->{
            try {
            while(!socket.isClosed())
            {
             
                    String msg=br.readLine();
                    if(msg.equals("exit"))
                    {
                        System.out.println("Server terminated");
                        socket.close();
                        break;
                    }
                    System.out.println("Server:"+msg);
                }
            }
            catch (Exception e) {
                System.out.println("Tata Bye Bye Khatam");

                // TODO: handle exception
            }
        };
        new Thread(r1).start();
    }
    public static void main(String[] args) {
        System.out.println("working as client");
        new client();
    }
}