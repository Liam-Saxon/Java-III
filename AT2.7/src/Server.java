import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server  {

    static String port;
    static int portInt = 0;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("What port will the server run on?");
        port = scanner.nextLine();
        try
        {
            portInt = Integer.parseInt(port);
        }
        catch (NumberFormatException e)
        {
            System.out.println("Error:" + e + " port has defaulted to 25565");
            portInt = 25565;
        }

        try (ServerSocket serverSocket = new ServerSocket(portInt))//server is on this port
        {
            System.out.println("Server is listening on " + serverSocket.getLocalPort());

            try (Socket clientSocket = serverSocket.accept())// wait, listen and accept connection
            {
                String clientHostName = clientSocket.getInetAddress().getHostName(); // client name
                int clientPortNumber = clientSocket.getLocalPort(); // port used
                System.out.println("Connected from " + clientHostName + " on #" + clientPortNumber);

                InputStream inStream;
                inStream = clientSocket.getInputStream();

                OutputStream outStream;
                outStream = new DataOutputStream((clientSocket.getOutputStream()));
                try
                {
                    outStream = new FileOutputStream("F:\\Test2.txt");
                }
                catch (FileNotFoundException ex)
                {
                    System.out.println("File not found. ");
                }
                int count;
                byte[] buffer = new byte[8192]; // or 4096, or more
                while ((count = inStream.read(buffer)) > 0)
                {
                    outStream.write(buffer, 0, count);
                }
            }
        } catch (IOException e) {
            System.err.println("IOException occurred" + e);
        }
    }

    @Test
    public void classTest()
    {
        Result result = JUnitCore.runClasses(Server.class);
        for (Failure failure: result.getFailures()){
            System.out.println((failure.toString()));
        }
    }

}