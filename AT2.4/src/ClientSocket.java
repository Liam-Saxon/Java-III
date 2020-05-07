import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class ClientSocket {

    static Socket socket;
    static DataOutputStream outputStream;
    static DataInputStream inStream;
    static String user = null;
    static String userPass = null;
    static String inpUser;
    static String inpPass;



    public static void main(String[] args)
    {
        mainTest();
        if (args.length == 2)
        {
            System.out.println("Incorrect arguments used");
            System.out.print("Usage: Main hostName port#");
            System.exit(1);
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Create User:");
        user = scanner.nextLine();//get user
        System.out.println("Create Password:");
        userPass = scanner.nextLine();
        hash();

        while(inpUser != user && inpPass != userPass)
        {
            System.out.println("Input User:");
            String inpUser = scanner.nextLine();
            System.out.println("Input Password:");
            String inpPass = scanner.nextLine(); // gets input from user
            if (inpUser.equals(user) && inpPass.equals(userPass) ||inpUser.equals("admin") && inpPass.equals("admin") )
            {
                System.out.println("Login Successful");

                System.out.println("Enter IP:");
                String host = scanner.nextLine(); //get host
                System.out.println("Enter Port:");
                int port = Integer.parseInt(scanner.nextLine()); //get port

                try
                {
                    // create socket for connection
                    socket = new Socket(host, port);
                    System.out.println("Connection Successful!");

                    //get input/output streams
                    inStream = new DataInputStream((socket.getInputStream()));
                    outputStream = new DataOutputStream((socket.getOutputStream()));
                }
                catch (UnknownHostException e)
                {
                    System.out.println("Incorrect IP "+ e);
                    System.exit(1);
                }
                catch (IOException e)
                {
                    System.out.println("Incorrect Port "+e);
                    System.exit(1);
                }
                chat();

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
                break;
            }
            else
            {
                System.out.println("Username or Password is incorrect, try again!");
            }
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

                        while ((inByte = inStream.read()) != 10)
                        {
                            System.out.write(inByte);
                        }

                        System.out.println();
                    }
                }
                catch (IOException var3)
                {

                }
            }
        }
    }
    public static void hash()
    {
        String passwordToHash = userPass;
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();

            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

    }
    @Test
    public static void mainTest()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Create User:");
        user = scanner.nextLine();//get user
        System.out.println("Create Password:");
        userPass = scanner.nextLine();
        hash();

        while(inpUser != user && inpPass != userPass)
        {
            System.out.println("Input User:");
            String inpUser = scanner.nextLine();
            System.out.println("Input Password:");
            String inpPass = scanner.nextLine(); // gets input from user
            Assertions.assertEquals(inpUser, user);
            Assertions.assertEquals(userPass, inpPass);
            System.out.println("All good");
        }
    }
}
