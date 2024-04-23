package Socket2;
import java.io.*;
import java.net.*;

public class Server {

    private static final int PORT = 1234;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server đang chờ kết nối...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Có client mới kết nối!");

            // Tạo luồng để xử lý client riêng biệt
            new Thread(() -> {
                try (
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                ) {
                    int count = 1;
                    while (count <= 1000) {
                        out.println(count);
                        count++;

                        // Gửi dữ liệu mỗi giây
                        Thread.sleep(1000);
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
