package tcp;

import java.io.*;
import java.net.*;
import java.util.HashSet;
import java.util.Set;

public class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String clientName;
    private String currentRoom;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void sendMessage(String msg) {
        out.println(msg);
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("NHAP_TEN");
            clientName = in.readLine();
            if (clientName == null || clientName.trim().isEmpty()) return;
            System.out.println("Client [" + clientName + "] da vao he thong.");

            while (true) {
                out.println("DANH_SACH_PHONG:" + String.join(",", Server.chatRooms.keySet()));
                
                currentRoom = in.readLine(); 
                if (currentRoom == null) break;
                currentRoom = currentRoom.trim();

                synchronized (Server.chatRooms) {
                    Server.chatRooms.putIfAbsent(currentRoom, new HashSet<>());
                    Server.chatRooms.get(currentRoom).add(this);
                }

                out.println("DA_VAO_PHONG:" + currentRoom);
                broadcastToRoom("--> [" + clientName + "] da vao phong.");

                boolean inRoom = true;
                while (inRoom) {
                    String msg = in.readLine();
                    if (msg == null) {
                        inRoom = false;
                        break;
                    }
                    msg = msg.trim();

                    if (msg.equalsIgnoreCase("/quit")) {
                        broadcastToRoom("--> [" + clientName + "] da roi phong.");
                        synchronized (Server.chatRooms) {
                            Server.chatRooms.get(currentRoom).remove(this);
                        }
                        currentRoom = null;
                        out.println("DA_ROI_PHONG");
                        inRoom = false; 
                    } else if (msg.equalsIgnoreCase("/exit")) {
                        broadcastToRoom("--> [" + clientName + "] da ngat ket noi khoi server.");
                        return; 
                    } else {
                        broadcastToRoom(clientName + ": " + msg);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("[" + clientName + "] da mat ket noi dot ngot.");
        } finally {
            if (currentRoom != null) {
                synchronized (Server.chatRooms) {
                    Set<ClientHandler> clients = Server.chatRooms.get(currentRoom);
                    if (clients != null) {
                        clients.remove(this);
                    }
                }
                broadcastToRoom("--> [" + clientName + "] da roi phong (mat ket noi).");
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void broadcastToRoom(String message) {
        if (currentRoom == null) return;
        Set<ClientHandler> clientsInRoom = Server.chatRooms.get(currentRoom);
        if (clientsInRoom != null) {
            synchronized (clientsInRoom) {
                for (ClientHandler client : clientsInRoom) {
                    if (client != this) {
                        client.sendMessage(message);
                    }
                }
            }
        }
    }
}