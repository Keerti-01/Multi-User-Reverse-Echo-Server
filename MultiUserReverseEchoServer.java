import java.net.*;
import java.io.*;
//server class
public class MultiUserReverseEchoServer extends Thread
{
        Socket stk;
        public MultiUserReverseEchoServer(Socket st)
        {
            stk=st;
        }
        //for multithreading
        public void run()
        {
            try {
                //input stream
                BufferedReader br = new BufferedReader(new InputStreamReader(stk.getInputStream()));
                //output stream
                PrintStream ps = new PrintStream(stk.getOutputStream());

                String msg;
                StringBuilder sb;
                do{
                    msg = br.readLine();

                    //reverse msg
                    sb = new StringBuilder(msg);
                    sb.reverse();
                    msg = sb.toString();

                    //send msg back to client
                    ps.println(msg);

                }while(! msg.equals("dne"));

                stk.close();

            }catch (Exception e){}
        }

        public static void main(String[] args) throws Exception
        {
            ServerSocket ss = new ServerSocket(2000);
            int count=1;
            Socket stk;
            MultiUserReverseEchoServer re;
            do{
                stk = ss.accept();
                System.out.println("Connection Established : "+count++);

                re = new MultiUserReverseEchoServer(stk);
                re.start();

            }while(true);
        }
}

//client class
class MultiUserReverseEchoClient
{
    public static void main(String[] args) throws Exception
    {
        Socket stk = new Socket("localhost", 2000);

        System.out.println("Connection Established!!!");

        //input stream
        BufferedReader br = new BufferedReader(new InputStreamReader(stk.getInputStream()));
        //output stream
        PrintStream ps = new PrintStream(stk.getOutputStream());
        //keyboard input
        BufferedReader keyb = new BufferedReader(new InputStreamReader(System.in));

        String msg;
        do {
            //read msg from keyboard
            msg = keyb.readLine();

            //send msg to server
            ps.println(msg);

            //reading msg from server
            msg = br.readLine();
            System.out.println("From server: " + msg);

        } while (!msg.equals("dne"));

        stk.close();
    }
}
