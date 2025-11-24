package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Join room screen (enter code + name; show current room state after join).
 */
public class JoinRoomView extends JPanel implements ActionListener, PropertyChangeListener {

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
