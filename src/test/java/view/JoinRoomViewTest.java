package view;

import interface_adapter.join_room.JoinRoomViewModel;

import javax.swing.*;
import java.util.Arrays;
import java.util.Random;

public class JoinRoomViewTest {
    public static void main(String args[]) {
        JFrame frame = new JFrame("Join Room View - Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JoinRoomViewModel viewModel = new JoinRoomViewModel();
        JoinRoomView view = new JoinRoomView(viewModel);

        frame.add(view);
        frame.setSize(800, 700);
        frame.pack();
        frame.setVisible(true);
    }
}
