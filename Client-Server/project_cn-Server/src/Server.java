import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket server;

    public Server(int port) throws IOException {
        server = new ServerSocket(port);
        System.out.println("Server started!");
        System.out.println("~Waiting for a client~\n");

        while (true) {
            Socket clientSocket = server.accept();
            System.out.println("---Client with local IP address " + clientSocket.getLocalAddress() + " from port " + clientSocket.getPort() + " was accepted---");

            ClientHandlerThread client = new ClientHandlerThread(clientSocket);
            client.start();
        }
    }

    private static class ClientHandlerThread extends Thread {
        private Socket clientSocket;
        private BufferedReader input;
        private PrintWriter output;

        public ClientHandlerThread(Socket socket) {
            clientSocket = socket;
        }

        public void run() {
            try {
                input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                output = new PrintWriter(clientSocket.getOutputStream(), true);

                String message = input.readLine();
                while (message != null && !message.equals("exit")) {
                    System.out.println("Message received from the client from port " + clientSocket.getPort() + ". The message is: " + message);

                    String response = getResponse(message);
                    output.println("Message received by server. Server's response: " + response);

                    message = input.readLine();
                }

                System.out.println("---Client from port " + clientSocket.getPort() + " disconnected---\n");
                System.out.println("~Waiting for a client~\n");

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    input.close();
                    output.close();
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getResponse(String message) {
        String result = message;
        int p=-1, t=0, k=0;
        for (int i=0; i<message.length(); i++)
        {
            char c = message.charAt(i);
            if (c=='+')
            {
                t=1;
                p=i;
                k++;
            }
            else if (c=='-')
            {
                t=2;
                p=i;
                k++;
            }
            else if (c=='*')
            {
                t=3;
                p=i;
                k++;
            }
            else if (!Character.isDigit(c))
                k++;
        }

        if (p!=-1 && k==1 && p!=message.length()-1 && p!=0)
        {
            String left = message.substring(0, p);
            String right = message.substring(p+1, message.length());

            if (t==1)
               result = String.valueOf(Integer.valueOf(left) + Integer.valueOf(right));
            else if (t==2)
                result = String.valueOf(Integer.valueOf(left) - Integer.valueOf(right));
            else if (t==3)
                result = String.valueOf(Integer.valueOf(left) * Integer.valueOf(right));
        }
        else
        {
            char[] s = new char[message.length()];
            for (int i=0; i<message.length(); i++)
            {
                char c = message.charAt(i);
                if (Character.isLetter(c))
                {
                    if (Character.isUpperCase(c))
                        s[i]=Character.toLowerCase(c);
                    else
                        s[i]=Character.toUpperCase(c);
                }
                else
                    s[i]=c;
            }
            result = String.valueOf(s);
        }

        return result;
    }
}

