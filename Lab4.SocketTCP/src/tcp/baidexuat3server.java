package tcp;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class baidexuat3server {
	private static final int PORT = 6789;
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm:ss");
    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("DateTime TCP Server listening on port " + PORT);
            while (true) {
                try (Socket socket = server.accept()) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(),StandardCharsets.UTF_8));
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),StandardCharsets.UTF_8),true);
                    String command;
                    while ((command = in.readLine()) != null) {
                        if (command.equalsIgnoreCase("DATE")) {
                            out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy")));
                        } else if (command.equalsIgnoreCase("TIME")) {
                            out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss")));
                        } else if (command.equalsIgnoreCase("DATETIME")) 
                        {
                            out.println(LocalDateTime.now().format(FORMAT));
                        } else if (command.equalsIgnoreCase("QUIT")) 
                        {
                            out.println("OK BYE");
                            break;
                        } else {
                            out.println("ERR UNKNOWN_COMMAND");
                        }
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
}
