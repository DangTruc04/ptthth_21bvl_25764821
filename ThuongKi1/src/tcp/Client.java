package tcp;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Client {
    private static List<String> currentRoomList = new ArrayList<>();

    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 6789;

        try (Socket socket = new Socket(serverAddress, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            new Thread(() -> {
                try {
                    String response;
                    while ((response = in.readLine()) != null) {
                        if (response.startsWith("NHAP_TEN")) {
                            System.out.print("Nhap ten cua ban: ");
                        } else if (response.startsWith("DANH_SACH_PHONG:")) {
                            System.out.println("\n=== DANH SACH PHONG CHAT ===");
                            String roomsStr = response.substring(16);
                            
                            currentRoomList.clear();
                            if (!roomsStr.isEmpty()) {
                                currentRoomList.addAll(Arrays.asList(roomsStr.split(",")));
                            }

                            for (int i = 0; i < currentRoomList.size(); i++) {
                                System.out.println("  [" + (i + 1) + "] " + currentRoomList.get(i));
                            }
                            System.out.print("Chon so phong (hoac tu go ten phong moi): ");
                        } else if (response.startsWith("DA_VAO_PHONG:")) {
                            System.out.println("\n[Thanh cong] Da vao phong: " + response.substring(13));
                            System.out.println("-> Nhap tin nhan (Go /quit de doi phong, /exit de thoat):");
                        } else if (response.equals("DA_ROI_PHONG")) {
                            System.out.println("\n[Thong bao] Da roi phong. Dang quay lai danh sach phong...");
                        } else {
                            System.out.println(response);
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Mat ket noi tu server.");
                }
            }).start();

            while (true) {
                if (scanner.hasNextLine()) {
                    String input = scanner.nextLine().trim();

                    if (!currentRoomList.isEmpty() && isNumeric(input)) {
                        int index = Integer.parseInt(input) - 1;
                        if (index >= 0 && index < currentRoomList.size()) {
                            input = currentRoomList.get(index); 
                        }
                    }

                    out.println(input);
                    if (input.equalsIgnoreCase("/exit")) {
                        break;
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Khong the ket noi den server!");
        }
    }

    private static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) return false;
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}