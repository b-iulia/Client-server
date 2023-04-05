import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader standardInput;
    private BufferedReader dataInput;
    private PrintWriter dataOutput;

    public Client (String a, int p) throws IOException {
        System.out.println("Trying to connect to " + a + " " + p);
        socket = new Socket(a, p);
        System.out.println("Connected!\n");

        standardInput = new BufferedReader(new InputStreamReader(System.in));
        dataInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        dataOutput = new PrintWriter(socket.getOutputStream(), true);

        System.out.println("Please type here the message: ");
        String message = standardInput.readLine();
        while (!message.equals("exit"))
        {
            dataOutput.println(message);

            System.out.println(dataInput.readLine());

            System.out.println("\nPlease type here the message: ");
            message = standardInput.readLine();
        }

        dataInput.close();
        dataOutput.close();
        socket.close();
        System.out.println("Connection closed!");
    }
}
