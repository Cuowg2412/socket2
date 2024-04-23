package Socket2;
import java.io.*;
import java.net.*;

public class Client {

    private static final int PORT = 1234;

    public static void main(String[] args) throws IOException {
        Socket clientSocket = new Socket("localhost", PORT);
        System.out.println("Client đã kết nối đến server!");

        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
