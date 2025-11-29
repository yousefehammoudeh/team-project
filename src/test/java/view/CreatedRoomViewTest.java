package view;

import interface_adapter.created_room.CreatedRoomViewModel;
import interface_adapter.created_room.CreatedRoomState;

import javax.swing.*;

public class CreatedRoomViewTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Created Room View - Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CreatedRoomViewModel vm = new CreatedRoomViewModel();
        CreatedRoomView view = new CreatedRoomView(vm);

        CreatedRoomState state = vm.getState();
        state.setRoomCode("A1B2C3");
        vm.firePropertyChanged();

        frame.add(view);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}