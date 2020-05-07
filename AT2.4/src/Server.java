import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Server implements Hello {

    public Server() {
    }

    public String sayHello() {
        return "Hello, world!";
    }

    static String userPass = null;
    static String inpUser;
    static String inpPass;


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("RMI or Socket?");
        System.out.println("If you're using RMI be sure to run 'start rmiregistry'");

        String inLine = scanner.nextLine();
        if (inLine.equalsIgnoreCase("RMI")) {
            try {
                Server obj = new Server();
                Hello stub = (Hello) UnicastRemoteObject.exportObject(obj, 0);
                Registry registry = LocateRegistry.getRegistry();
                registry.bind("Hello", stub);
                System.err.println("Server ready");
            } catch (AlreadyBoundException | RemoteException var4) {
                System.err.println("Server exception: " + var4.toString());
            }
        } else if (inLine.equalsIgnoreCase("Socket")) {
            try (ServerSocket serverSocket = new ServerSocket(1234))//server is on this port
            {
                System.out.println("Server is listening on " + serverSocket.getLocalPort());
                try (Socket clientSocket = serverSocket.accept())// wait, listen and accept connection
                {
                    String clientHostName = clientSocket.getInetAddress().getHostName(); // client name
                    int clientPortNumber = clientSocket.getLocalPort(); // port used
                    System.out.println("Connected from " + clientHostName + " on #" + clientPortNumber);

                    BufferedReader inStream;
                    inStream = new BufferedReader((new InputStreamReader((clientSocket.getInputStream()))));

                    DataOutputStream outStream;
                    outStream = new DataOutputStream((clientSocket.getOutputStream()));

                    while (true) {
                        inLine = inStream.readLine(); // read a line from client
                        System.out.println("Received from client " + inLine);

                        //if the client sends "quit" then stop
                        if (inLine.equalsIgnoreCase("quit")) {
                            System.out.print("Client Disconnected");
                            break;
                        }

                        String outLine = "You said '" + inLine + "'.";
                        outStream.writeBytes(outLine); // send a message to client
                        outStream.write(13); // carriage return
                        outStream.write(10); // line feed
                        outStream.flush(); // flush the stream line
                    }
                    inStream.close();
                    outStream.close();
                }
            } catch (IOException e) {
                System.err.println("IOException occurred" + e);
            }
        } else {
            System.out.println("No valid option selected...");
        }
    }

}

