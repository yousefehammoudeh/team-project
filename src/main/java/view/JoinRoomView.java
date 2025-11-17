package view;

import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Join room screen (enter code + name; show current room state after join).
 */
public class JoinRoomView extends JPanel implements ActionListener, PropertyChangeListener {
    private ViewManagerModel viewManagerModel;

    @Override
    public void actionPerformed(ActionEvent e) {
        // In the real app this would call JoinRoomController. For scaffolding, navigate
        // to participants dashboard.
        if (viewManagerModel != null) {
            viewManagerModel.setActiveViewName("ParticipantsDashboard");
        } else {
            JOptionPane.showMessageDialog(this, "Joined room (scaffold). Provide ViewManagerModel to navigate.");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // TODO: Update fields based on ViewModel changes
    }

    public void setViewManagerModel(ViewManagerModel vm) {
        this.viewManagerModel = vm;
    }
}
