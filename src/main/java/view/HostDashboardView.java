package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * TODO: Host dashboard (shows room code, host token, controls for lock, compute winner, apply filters, etc.).
 */
public class HostDashboardView extends JPanel implements ActionListener, PropertyChangeListener {
    // TODO: Wire buttons and fields to controllers

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO: Dispatch actions to appropriate controllers (lock, compute winner, apply filters)
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // TODO: Update UI based on ViewModel changes
    }
}
