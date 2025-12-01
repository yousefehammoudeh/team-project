package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.join_room.JoinRoomController;
import interface_adapter.join_room.JoinRoomState;
import interface_adapter.join_room.JoinRoomViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class JoinRoomView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = ViewManagerModel.JOIN_ROOM_VIEW;

    private final JoinRoomViewModel joinRoomViewModel;
    private final JTextField usernameInputField = new JTextField(15);
    private final JTextField codeInputField = new JTextField(15);
    private JoinRoomController joinRoomController;

    private final JButton toJoin;
    private final JButton backButton;

    // instantiating a view model will also instantiate a new state, and set it to
    // the start state with empty strings
    public JoinRoomView(JoinRoomViewModel joinRoomViewModel) {
        this.joinRoomViewModel = joinRoomViewModel;
        joinRoomViewModel.addPropertyChangeListener(this);

        // Create the GUI for the join room view (title, username, room code, join and
        // create buttons)
        final JLabel title = new JLabel(JoinRoomViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel usernamePanel = new JPanel();
        usernamePanel.add(new JLabel(JoinRoomViewModel.USERNAME_LABEL));
        usernamePanel.add(usernameInputField);

        final JPanel roomcodePanel = new JPanel();
        roomcodePanel.add(new JLabel(JoinRoomViewModel.ROOM_CODE_LABEL));
        roomcodePanel.add(codeInputField);

        final JPanel buttons = new JPanel();
        toJoin = new JButton(JoinRoomViewModel.JOIN_BUTTON_LABEL);
        buttons.add(toJoin);

        backButton = new JButton(JoinRoomViewModel.BACK_BUTTON_LABEL);
        buttons.add(backButton);

        // listen to the join button
        // takes the updated state in the view model and calls the controller when the
        // user clicks the button
        toJoin.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        // check that the event is join button being clicked
                        if (evt.getSource().equals(toJoin)) {
                            // get the state
                            final JoinRoomState currentState = joinRoomViewModel.getState();

                            // pass the entered username and room code to the controller
                            // abstraction, calls the interactor inside the controller
                            joinRoomController.execute(
                                    currentState.getUsername(),
                                    currentState.getRoomcode());
                        }
                    }
                });

        // back button to go back to the welcome view
        backButton.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            joinRoomController.switchToWelcomeView();
                        }
                    }
                );

        // removed create button from Join view

        // call the listener functions
        // just wiring it up, it actually gets called when a user types something, this
        // will update the state
        addUsernameListener();
        addRoomcodeListener();

        // add all the panels into the frame
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(usernamePanel);
        this.add(roomcodePanel);
        this.add(buttons);
    }

    // this is where you will update the current state!!
    // if there is a change to username, then update the view model
    private void addUsernameListener() {
        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final JoinRoomState currentState = joinRoomViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                joinRoomViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });
    }

    private void addRoomcodeListener() {
        codeInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final JoinRoomState currentState = joinRoomViewModel.getState();
                currentState.setRoomcode(codeInputField.getText());
                joinRoomViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // System.out.println("Click " + e.getActionCommand());

    }

    // if there is a change in the view model, these changes will be visible in the
    // UI (view)
    // fire property in ViewModel prevents infinite loop
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        final JoinRoomState state = (JoinRoomState) evt.getNewValue();

        // set UI text
        usernameInputField.setText(state.getUsername());
        codeInputField.setText(state.getRoomcode());

        // presenter sends output if the room code is wrong
        if (state.getRoomcodeError() != null) {
            JOptionPane.showMessageDialog(this, state.getRoomcodeError());
        }
        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setJoinRoomController(JoinRoomController controller) {
        this.joinRoomController = controller;
    }
}
