package tcp;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class baidexuat2server {
	private static final int PORT = 6789;

    private static final String[] WORDS = {
        "không",
        "một",
        "hai",
        "ba",
        "bốn",
        "năm",
        "sáu",
        "bảy",
        "tám",
        "chín"
    };
    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(PORT)) {

            System.out.println(
                "Digit TCP Server listening on port " + PORT
            );
            while (true) {
                try (Socket socket = server.accept()) {
                    BufferedReader in = new BufferedReader( new InputStreamReader(socket.getInputStream(),StandardCharsets.UTF_8));
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),StandardCharsets.UTF_8),true);
                    String data;
                    while ((data = in.readLine()) != null) {
                        if (data.equalsIgnoreCase("QUIT")) {
                            out.println("OK BYE");
                            break;
                        }
                        if (data.length() == 1 && data.charAt(0) >= '0' && data.charAt(0) <= '9') {
                            int digit = data.charAt(0) - '0';
                            out.println("OK " + WORDS[digit]);
                        } else {
                            out.println("ERR INVALID_DIGIT");
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println(
                "Server error: " + e.getMessage()
            );
        }
    }
}
