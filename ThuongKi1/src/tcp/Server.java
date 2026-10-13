package tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Server {
    public static HashMap<String, Set<ClientHandler>> chatRooms = new HashMap<>();

    public static void main(String[] args) {
        int port = 6789;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server dang chay o cong " + port);

            chatRooms.put("Study", new HashSet<>());
            chatRooms.put("Game", new HashSet<>());
            chatRooms.put("TraiNghiem", new HashSet<>());

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Mot client moi da ket noi!");

                ClientHandler clientThread = new ClientHandler(socket);
                new Thread(clientThread).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}