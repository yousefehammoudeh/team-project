package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Join room screen - participant enters room code and their name.
 * STUB: Basic panel exists but no UI implemented
 * TODO: Add text fields for room code and participant name
 * TODO: Add "Join Room" button
 * TODO: Wire up JoinRoomController to validate and join room
 * TODO: Navigate to ParticipantsDashboardView on successful join
 * TODO: Display error messages for invalid room codes
 * TODO: Integrate with JoinRoomViewModel for state management
 */
public class JoinRoomView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "JoinRoom";

    public String getViewName() {
        return viewName;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO: Call JoinRoomController to join room
        JOptionPane.showMessageDialog(this, "Join room functionality not yet implemented.");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // TODO: Update fields based on ViewModel changes
    }
}
