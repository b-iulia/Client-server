import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        int port=8080;
        if (args.length==1)
            port = Integer.valueOf(args[0]);
        else
            port = 8080;

        Server s = new Server(port);
    }
}