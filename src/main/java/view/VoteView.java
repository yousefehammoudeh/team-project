package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * TODO: Voting view (ranked selection UI and winner display for host).
 */
public class VoteView extends JPanel implements ActionListener, PropertyChangeListener {
    // TODO: Wire submit and compute winner actions to VoteController

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO: Submit ballot or request winner
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // TODO: Update UI with voting progress and winner
    }
}
