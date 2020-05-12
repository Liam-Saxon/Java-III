import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    static Socket socket;
    static DataOutputStream outputStream;
    static FileInputStream inStream;

    public static void main(String[] args) {

        if (args.length == 2)
        {
            System.out.println("Incorrect arguments used");
            System.out.print("Usage: Main hostName port#");
            System.exit(1);
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter IP:");
        String host = scanner.nextLine(); //get host
        System.out.println("Enter Port:");
        int port = Integer.parseInt(scanner.nextLine()); //get port

        try
        {
            // create socket for connection
            socket = new Socket(host, port);
            System.out.println("Connection Successful!");

            File file = new File("F:\\Test.txt"); //enter a path name here like the example
            //get input/output streams
            inStream = new FileInputStream(file);
            outputStream = new DataOutputStream((socket.getOutputStream()));

            int count;
            byte[] buffer = new byte[8192]; // or 4096, or more
            while ((count = inStream.read(buffer)) > 0)
            {
                outputStream.write(buffer, 0, count);
            }
        }
        catch (UnknownHostException e)
        {
            System.out.println("Incorrect IP " + e);
            System.exit(1);
        }
        catch (IOException e)
        {
            System.out.println("Incorrect Port " + e);
            System.exit(1);
        }
        try
        {
            socket.close();
            inStream.close();
            outputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    public static void chat()
    {
        Scanner sc = new Scanner(System.in);

        while (true)
        {
            while (true)
            {
                try
                {
                    String lineInput = sc.nextLine();
                    if (lineInput.length() > 0)
                    {
                        outputStream.writeBytes(lineInput);
                        outputStream.write(13);
                        outputStream.write(10);
                        outputStream.flush();
                        if (lineInput.equalsIgnoreCase("quit"))
                        {

                            System.exit(0);
                        }

                        System.out.print("Server>>> ");

                        int inByte;

                        while ((inByte = inStream.read()) != 10) {
                            System.out.write(inByte);
                        }

                        System.out.println();
                    }
                }
                catch (IOException var3)
                {
                    System.out.println(var3);
                }
            }
        }
    }
}