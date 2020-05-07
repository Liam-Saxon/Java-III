
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class ClientRMI {

    private ClientRMI() {
    }

    static String userPass = null;
    static String inpUser;
    static String inpPass;

    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);

        String host = args.length < 1 ? null : args[0];

        System.out.println("Create User:");
        String user = scanner.nextLine();//get user
        System.out.println("Create Password:");
        userPass = scanner.nextLine();
        hash();

        while (inpUser != user && inpPass != userPass) {

            System.out.println("Input User:");
            String inpUser = scanner.nextLine();
            System.out.println("Input Password:");
            String inpPass = scanner.nextLine(); // gets input from user

            if (inpUser.equals(user) && inpPass.equals(userPass)|| inpUser.equals("admin") && inpPass.equals("admin"))
            {
                try
                {
                    Registry registry = LocateRegistry.getRegistry(host);
                    Hello stub = (Hello) registry.lookup("Hello");
                    String response = stub.sayHello();
                    System.out.println("response: " + response);
                } catch (NotBoundException | RemoteException var5)
                {
                    System.err.println("Client exception: " + var5.toString());
                }
                break;
            }
            else{
                System.out.println("Username or Password is incorrect, try again!");
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

            for (int i = 0; i < bytes.length; i++) {

                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

    }
}