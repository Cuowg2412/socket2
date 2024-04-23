package Socket2;
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer2 {

    private static final int PORT = 1234;
    private static final List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server đang chờ kết nối...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Có client mới kết nối!");

            // Tạo luồng để xử lý client riêng biệt
            new Thread(new ClientHandler(clientSocket)).start();
        }
    }

    private static class ClientHandler implements Runnable {

        private final Socket clientSocket;
        private String username;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                // Nhận username từ client
                username = in.readLine();

                // Gửi thông báo chào mừng đến client
                out.println("Chào mừng " + username + " đến với nhóm chat!");

                // Thêm client vào danh sách
                synchronized (clients) {
                    clients.add(this);
                }

                String line;
                while ((line = in.readLine()) != null) {
                    // Gửi tin nhắn đến tất cả các client khác
                    synchronized (clients) {
                        for (ClientHandler client : clients) {
                            if (client != this) {
                                client.out.println(username + ": " + line);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // Xóa client khỏi danh sách
                synchronized (clients) {
                    clients.remove(this);
                }

                try {
                    out.close();
                    in.close();
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
