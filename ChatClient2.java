package Socket2;
import java.io.*;
import java.net.*;

public class ChatClient2 {

    private static final int PORT = 1234;

    public static void main(String[] args) throws IOException {
        Socket clientSocket = new Socket("localhost", PORT);
        System.out.println("Client đã kết nối đến server!");

        try (
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ) {
            // Nhập username
            System.out.print("Nhập username: ");
            String username = reader.readLine();

            // Gửi username đến server
            out.println(username);

            String line;
            while ((line = reader.readLine()) != null) {
                // Gửi tin nhắn đến server
                out.println(line);

                // Hiển thị tin nhắn nhận được từ server
                System.out.println(in.readLine());
            }
        }
    }
}
