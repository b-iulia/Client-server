import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Client app started");
        String address;
        int port;
        if (args.length==2)
        {
            address = args[0];
            port = Integer.valueOf(args[1]);
        }
        else
        {
            address="localhost";
            port = 8080;
        }

        Client c = new Client(address, port);
    }
}