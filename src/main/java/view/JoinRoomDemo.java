package view;

import javax.swing.*;
import interface_adapter.join_room.JoinRoomController;
import interface_adapter.join_room.JoinRoomViewModel;
import view.JoinRoomView;

public class JoinRoomDemo {
    public static void main(String[] args) {

        // 1. Create your real ViewModel
        JoinRoomViewModel viewModel = new JoinRoomViewModel();

        // 2. Create your real controller (inject your real interactor)
        // NOTE: Replace this with however you normally build your controller.
        JoinRoomController controller = buildRealController(viewModel);

        // 3. Create the view
        JoinRoomView joinRoomView = new JoinRoomView(viewModel);
        joinRoomView.setJoinRoomController(controller);

        // 4. Show it in a JFrame
        JFrame frame = new JFrame("Join Room Debug");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(joinRoomView);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JoinRoomController buildRealController(JoinRoomViewModel vm) {
        // TODO: replace this with your real initialization.
        // You likely have:
        //   JoinRoomInteractor interactor = new JoinRoomInteractor(...);
        //   return new JoinRoomController(interactor);

        throw new UnsupportedOperationException("Replace with your real controller wiring.");
    }
}