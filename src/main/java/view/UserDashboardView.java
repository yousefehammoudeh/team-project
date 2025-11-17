package view;

import interface_adapter.join_room.JoinRoomController;
import interface_adapter.joined_room.JoinedRoomState;
import interface_adapter.join_room.JoinRoomViewModel;
import interface_adapter.joined_room.JoinedRoomController;
import interface_adapter.joined_room.JoinedRoomViewModel;
import interface_adapter.joined_room.JoinedRoomViewModel;
import entity.Participant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class UserDashboardView extends JPanel implements ActionListener, PropertyChangeListener{
    private final String viewName = "joined room";

    private final JoinedRoomViewModel joinedRoomViewModel;
    private JoinedRoomController joinedRoomController = null;
    private final JPanel mainPanel;
    private final JPanel users;
    //private final JButton exit;

    public UserDashboardView(JoinedRoomViewModel joinedRoomViewModel) {
        this.joinedRoomViewModel = joinedRoomViewModel;
        joinedRoomViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(JoinedRoomViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);


        mainPanel = new JPanel();
        users = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        users.setLayout(new BoxLayout(users, BoxLayout.X_AXIS));
        mainPanel.add(title);
        mainPanel.add(users);

//        final JPanel buttons = new JPanel();
//        exit = new JButton(JoinedRoomViewModel.EXIT_BUTTON_LABEL);
//        buttons.add(exit);


//        exit.addActionListener(
//                new ActionListener() {
//                    public void actionPerformed(ActionEvent evt) {
//                        if (evt.getSource().equals(exit)) { //check that the event is exit button being clicked
//                            final JoinedRoomState currentState = joinedRoomViewModel.getState(); //get the state
//
//                            //pass the entered username and room code to the controller
//                            joinedRoomController.execute();
//                        }
//                    }
//                }
//        );
//
//        exit.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO: Trigger join action on controller

        //System.out.println("Click " + e.getActionCommand());

    }

    //if there is a change in the view model, these changes will be visible in the UI (view)
    //when a new participant joins the room, the view model will be updated
    //fire property in ViewModel prevents infinite loop
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // TODO: Update fields based on ViewModel changes
        final JoinedRoomState state = (JoinedRoomState) evt.getNewValue();

//        List<Participant> participants = state.getParticipants();
//        for (Participant p : participants) {
//            final JLabel newUser = new JLabel(p.getName());
//            users.add(newUser);
//        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setJoinedRoomController(JoinedRoomController controller) {
        this.joinedRoomController = controller;
    }
}
