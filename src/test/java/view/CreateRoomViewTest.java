package view;

import interface_adapter.create_room.CreateRoomViewModel;
import interface_adapter.create_room.CreateRoomController;

import javax.swing.*;

public class CreateRoomViewTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Create Room View - Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CreateRoomViewModel viewModel = new CreateRoomViewModel();
        CreateRoomView view = new CreateRoomView(viewModel);

        frame.add(view);
        frame.setSize(800, 700);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
}