package use_case.join_room;

import java.util.List;
import data_access.room.InMemoryRoomDataAccessObject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JoinRoomInteractorTest {

    @Test
    void successTest() {
        // create a room first
        InMemoryRoomDataAccessObject db = new InMemoryRoomDataAccessObject();

        JoinRoomInputData input = new JoinRoomInputData("Bob", "c4a760");

        JoinRoomOutputBoundary successPresenter = new JoinRoomOutputBoundary() {
            @Override
            public void prepareSuccessView(JoinRoomOutputData output) {
                assertEquals("c4a760", output.getRoomCode());
                assertEquals(List.of("Alice", "Bob"), output.getParticipants());
                assertEquals("Bob", output.getCurrentUser());
            }

            @Override
            public void presentFailure(String error) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToCreateRoomView() {

            }
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
            public void switchToCreateRoomView() {

            }
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
            public void switchToCreateRoomView() {

            }
        };

        JoinRoomInputBoundary interactor = new JoinRoomInteractor(db, successPresenter);
        interactor.execute(input);

    }

    @Test
    void wrongRoomTest() {
        // create a room first
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
            public void switchToCreateRoomView() {

            }
        };

        JoinRoomInputBoundary interactor = new JoinRoomInteractor(db, successPresenter);
        interactor.execute(input);

    }

    @Test
    void multipleParticipantsTest() {
        // create a room first
        InMemoryRoomDataAccessObject db = new InMemoryRoomDataAccessObject();

        //db.addParticipant("Alice");

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
            public void switchToCreateRoomView() {

            }
        };

        JoinRoomInputBoundary interactor = new JoinRoomInteractor(db, successPresenter);
        interactor.execute(input);


    }


}


