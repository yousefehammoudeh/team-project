package use_case.create_room;

import javax.swing.*;
import java.awt.*;

import interface_adapter.ViewManagerModel;

import interface_adapter.create_room.CreateRoomController;
import interface_adapter.create_room.CreateRoomPresenter;
import interface_adapter.create_room.CreateRoomViewModel;
import interface_adapter.create_room.CreateRoomState;

import interface_adapter.created_room.CreatedRoomViewModel;
import interface_adapter.created_room.CreatedRoomState;

import view.CreateRoomView;
import view.CreatedRoomView;

/**
 * Simple demo to test CreateRoom -> CreatedRoom flow.
 * Run manually from the test folder.
 */
public class CreateRoomDemo {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("CreateRoom DEMO");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // --------------------------------------------------
            //  VIEW MANAGER
            // --------------------------------------------------
            ViewManagerModel viewManagerModel = new ViewManagerModel();
            viewManagerModel.setActiveViewName(ViewManagerModel.CREATE_ROOM_VIEW);

            // --------------------------------------------------
            //  VIEW MODELS
            // --------------------------------------------------
            CreateRoomViewModel createVM = new CreateRoomViewModel();
            createVM.setState(new CreateRoomState());

            CreatedRoomViewModel createdVM = new CreatedRoomViewModel();
            createdVM.setState(new CreatedRoomState());

            // --------------------------------------------------
            //  FAKE DAO
            // --------------------------------------------------
            CreateRoomUserDataAccessInterface dao = new InMemoryCreateRoomDAO();

            // --------------------------------------------------
            //  PRESENTER + INTERACTOR + CONTROLLER
            // --------------------------------------------------
            CreateRoomPresenter presenter =
                    new CreateRoomPresenter(createVM, createdVM, viewManagerModel);

            CreateRoomInputBoundary interactor =
                    new CreateRoomInteractor(dao, presenter);

            CreateRoomController controller =
                    new CreateRoomController(interactor);

            // --------------------------------------------------
            //  VIEWS
            // --------------------------------------------------
            CreateRoomView createRoomView = new CreateRoomView(createVM);
            createRoomView.setController(controller);

            CreatedRoomView createdRoomView = new CreatedRoomView(createdVM);
            createdRoomView.setViewManagerModel(viewManagerModel);

            // --------------------------------------------------
            //  CARD LAYOUT
            // --------------------------------------------------
            JPanel cards = new JPanel(new CardLayout());
            cards.add(createRoomView, ViewManagerModel.CREATE_ROOM_VIEW);
            cards.add(createdRoomView, ViewManagerModel.CREATED_ROOM_VIEW);

            viewManagerModel.addPropertyChangeListener(evt -> {
                if (evt.getNewValue() instanceof String newView) {
                    CardLayout layout = (CardLayout) cards.getLayout();
                    layout.show(cards, newView);
                }
            });

            frame.setContentPane(cards);
            frame.setSize(300, 250);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    // ----------------------------------------------------------------------
    //  Fake in-memory DAO (for demo only)
    // ----------------------------------------------------------------------
    static class InMemoryCreateRoomDAO implements CreateRoomUserDataAccessInterface {

        private String username;

        @Override
        public void createRoom(String roomCode) {
            System.out.println("TEST DEMO → room created: " + roomCode);
        }

        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public void setUsername(String username) {
            this.username = username;
        }
    }
}