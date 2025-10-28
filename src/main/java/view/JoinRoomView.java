package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * TODO: Join room screen (enter code + name; show current room state after join).
 */
public class JoinRoomView extends JPanel implements ActionListener, PropertyChangeListener {
    // TODO: Wire inputs and actions to JoinRoomController

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO: Trigger join action on controller
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // TODO: Update fields based on ViewModel changes
    }
}
