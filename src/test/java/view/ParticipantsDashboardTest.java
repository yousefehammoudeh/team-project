package view;

import interface_adapter.joined_room.JoinedRoomViewModel;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class ParticipantsDashboardTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Participants Dashboard - Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JoinedRoomViewModel viewModel = new JoinedRoomViewModel();
        ParticipantsDashboardView view = new ParticipantsDashboardView(viewModel);

        view.setRoomId("ROOM12");

        List<String> participants = Arrays.asList("Diana", "Tamako", "Elaine", "He Sun", "Yousef");
        view.updateParticipants(participants);

        frame.add(view);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    };
}