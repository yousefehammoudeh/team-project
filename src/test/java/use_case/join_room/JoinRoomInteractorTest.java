package use_case.join_room;

import java.awt.*;
import java.util.List;

import data_access.note_database.DataAccessException;
import data_access.room.InMemoryRoomDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.create_room.CreateRoomController;
import interface_adapter.create_room.CreateRoomPresenter;
import interface_adapter.create_room.CreateRoomState;
import interface_adapter.create_room.CreateRoomViewModel;
import interface_adapter.created_room.CreatedRoomState;
import interface_adapter.created_room.CreatedRoomViewModel;
import interface_adapter.join_room.JoinRoomController;
import interface_adapter.join_room.JoinRoomState;
import interface_adapter.join_room.JoinRoomViewModel;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import use_case.create_room.CreateRoomDemo;
import use_case.create_room.CreateRoomInputBoundary;
import use_case.create_room.CreateRoomInteractor;
import use_case.create_room.CreateRoomUserDataAccessInterface;
import view.JoinRoomView;

import javax.swing.*;


import static data_access.HTTPCode.CONFLICT_ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JoinRoomInteractorTest {

    @Test
    void successTest() {
        // create a room first
        InMemoryRoomDataAccessObject db = new InMemoryRoomDataAccessObject("Alice", new java.util.HashMap<>());
        try {
            db.createRoom("c4a760");
        } catch (data_access.note_database.DataAccessException e) {
            fail("Setup failed: " + e.getMessage());
        }

        JoinRoomInputData input = new JoinRoomInputData("Bob", "c4a760");

        JoinRoomOutputBoundary successPresenter = new JoinRoomOutputBoundary() {
            @Override
            public void prepareSuccessView(JoinRoomOutputData output) {
                assertEquals("c4a760", output.getRoomCode());
                assertEquals(List.of("Alice", "Bob"), output.getParticipants());
                assertEquals("Alice", output.getHostName());
            }

            @Override
            public void presentFailure(String error) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToWelcomeView() {}

        };

        JoinRoomInputBoundary interactor = new JoinRoomInteractor(db, successPresenter);
        interactor.execute(input);

    }

    @Test
    void noUsernameTest() {
        // create a room first
        InMemoryRoomDataAccessObject db = new InMemoryRoomDataAccessObject();

        JoinRoomInputData input = new JoinRoomInputData("", "c4a760");

        JoinRoomOutputBoundary successPresenter = new JoinRoomOutputBoundary() {
            @Override
            public void prepareSuccessView(JoinRoomOutputData output) {
                // should not reach this
                fail("Use case failure is unexpected.");
            }

            @Override
            public void presentFailure(String error) {
                assertEquals("Username cannot be empty", error);
            }

            @Override
            public void switchToWelcomeView() {}

        };

        JoinRoomInputBoundary interactor = new JoinRoomInteractor(db, successPresenter);
        interactor.execute(input);

    }

    @Test
    void emptyRoomTest() {
        // create a room first
        InMemoryRoomDataAccessObject db = new InMemoryRoomDataAccessObject();

        JoinRoomInputData input = new JoinRoomInputData("Alice", "");

        JoinRoomOutputBoundary successPresenter = new JoinRoomOutputBoundary() {
            @Override
            public void prepareSuccessView(JoinRoomOutputData output) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void presentFailure(String error) {
                assertEquals("Room code cannot be empty", error);
            }

            @Override
            public void switchToWelcomeView() {}

        };

        JoinRoomInputBoundary interactor = new JoinRoomInteractor(db, successPresenter);
        interactor.execute(input);

    }

    @Test
    void wrongRoomTest() {
        // create a room first (no rooms added to simulate wrong room)
        InMemoryRoomDataAccessObject db = new InMemoryRoomDataAccessObject();

        JoinRoomInputData input = new JoinRoomInputData("Bob", "d4a760");

        JoinRoomOutputBoundary successPresenter = new JoinRoomOutputBoundary() {
            @Override
            public void prepareSuccessView(JoinRoomOutputData output) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void presentFailure(String error) {
                assertEquals("Room doesn't exist.", error);
            }

            @Override
            public void switchToWelcomeView() {}

        };

        JoinRoomInputBoundary interactor = new JoinRoomInteractor(db, successPresenter);
        interactor.execute(input);

    }

    @Test
    void multipleParticipantsTest() {
        // create a room first with existing participant Alice
        InMemoryRoomDataAccessObject db = new InMemoryRoomDataAccessObject("Alice", new java.util.HashMap<>());
        try {
            db.createRoom("c4a760");
        } catch (data_access.note_database.DataAccessException e) {
            fail("Setup failed: " + e.getMessage());
        }

        JoinRoomInputData input = new JoinRoomInputData("Alice", "c4a760");

        JoinRoomOutputBoundary successPresenter = new JoinRoomOutputBoundary() {
            @Override
            public void prepareSuccessView(JoinRoomOutputData output) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void presentFailure(String error) {
                assertEquals("User already exists.", error);
            }

            @Override
            public void switchToWelcomeView() {}

        };

        JoinRoomInputBoundary interactor = new JoinRoomInteractor(db, successPresenter);
        interactor.execute(input);

    }

    @Test
    void testOtherDataException() {
        // A fake DAO that forces a non-NOT_FOUND exception (catch-else clause)
        JoinRoomUserDataAccessInterface fakeDao = new JoinRoomUserDataAccessInterface() {

            @Override
            public void setUsername(String username) {}

            @Override
            public boolean joinRoom(String roomcode) throws DataAccessException {
                // Force a CONFLICT ERROR (not NOT_FOUND)
                throw new DataAccessException("User or room already exists.", CONFLICT_ERROR);
            }

            @Override
            public List<String> getParticipantIDs() {
                return null;
            }

            @Override
            public String getHostId() {
                return null;
            }
        };

        JoinRoomInputData input = new JoinRoomInputData("Bob", "c4a760");

        JoinRoomOutputBoundary successPresenter = new JoinRoomOutputBoundary() {
            @Override
            public void prepareSuccessView(JoinRoomOutputData output) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void presentFailure(String error) {
                assertEquals("User or room already exists.", error);
            }

            @Override
            public void switchToWelcomeView() {}

        };

        JoinRoomInputBoundary interactor = new JoinRoomInteractor(fakeDao, successPresenter);
        interactor.execute(input);

    }


    @Test
    void testBackButtonCallsSwitchToWelcomeView() {
        // real view model
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        JoinRoomViewModel joinVM = new JoinRoomViewModel();
        joinVM.setState(new JoinRoomState());

        // fake dao
        JoinRoomUserDataAccessInterface dao = new JoinRoomUserDataAccessInterface() {
            @Override
            public void setUsername(String username) {}

            @Override
            public boolean joinRoom(String roomcode) throws DataAccessException {
                return true;
            }

            @Override
            public List<String> getParticipantIDs() {
                return null;
            }

            @Override
            public String getHostId() {
                return null;
            }
        };

        // mock presenter
        JoinRoomOutputBoundary presenter = new JoinRoomOutputBoundary() {
            @Override
            public void prepareSuccessView(JoinRoomOutputData output) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void presentFailure(String error) {
                assertEquals("User or room already exists.", error);
            }

            @Override
            public void switchToWelcomeView() {
                viewManagerModel.setActiveViewName(ViewManagerModel.WELCOME_VIEW);
            }
        };


        // real interactor + controller
        JoinRoomInteractor interactor = new JoinRoomInteractor(dao, presenter);
        JoinRoomController controller = new JoinRoomController(interactor);

        // view with controller injected
        JoinRoomView view = new JoinRoomView(joinVM);
        view.setJoinRoomController(controller);

        // retrieve the back button
        JButton backButton = (JButton) ((JPanel) view.getComponent(3)).getComponent(1);

        // simulate a click
        backButton.doClick();

        // verify presenter was called
        assertEquals("Welcome", viewManagerModel.getActiveViewName());
    }

}
