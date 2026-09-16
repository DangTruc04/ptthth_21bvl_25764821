package tcp;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class baidexuat2client {
	public static void main(String[] args) {

        String host = "localhost";
        int port = 6789;

        try (Socket socket = new Socket(host, port);
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in,StandardCharsets.UTF_8));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(),StandardCharsets.UTF_8));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),StandardCharsets.UTF_8),true)) 
        {
            System.out.println("Connected. Enter digit 0-9 or QUIT");
            String data;
            while ((data = console.readLine()) != null) {
            	out.println(data);
                String response = in.readLine();
                if (response == null) {
                    break;
                }
                System.out.println("Server: " + response);
                if (data.equalsIgnoreCase("QUIT")) {
                    break;
                }
            }
        } catch (IOException e) {

            System.err.println(
                "Client error: " + e.getMessage()
            );
        }
    }
}
