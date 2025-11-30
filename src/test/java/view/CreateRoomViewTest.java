package view;

import interface_adapter.create_room.CreateRoomViewModel;

import javax.swing.*;

public class CreateRoomViewTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Create Room View - Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CreateRoomViewModel viewModel = new CreateRoomViewModel();
        CreateRoomView view = new CreateRoomView(viewModel);

        frame.add(view);
        frame.setSize(300, 250);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}