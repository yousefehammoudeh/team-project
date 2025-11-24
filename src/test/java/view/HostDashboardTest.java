package view;

import javax.swing.*;
import java.util.Arrays;
import java.util.Random;

public class HostDashboardTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Host Dashboard - Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        HostDashboardView view = new HostDashboardView();
        view.setRoomId(generateRoomId());
        view.updateParticipants(Arrays.asList("Diana", "Tamako", "Elaine", "He Sun", "Yousef"));

        frame.add(view);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static String generateRoomId() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder id = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            id.append(chars.charAt(random.nextInt(chars.length())));
        }
        return id.toString();
    }
}