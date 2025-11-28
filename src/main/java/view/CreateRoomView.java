package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.create_room.CreateRoomController;
import interface_adapter.create_room.CreateRoomState;
import interface_adapter.create_room.CreateRoomViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Simple view to create a room by entering a host name.
 */
public class CreateRoomView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "create room";

    private final CreateRoomViewModel viewModel;
    private final ViewManagerModel viewManagerModel;

    private final JTextField hostNameField = new JTextField(16);
    private final JButton createButton = new JButton("Create Room");
    private CreateRoomController controller;

    public CreateRoomView(CreateRoomViewModel vm, ViewManagerModel viewManagerModel) {
        this.viewModel = vm;
        this.viewManagerModel = viewManagerModel;
        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        final JLabel title = new JLabel("Create a Room", SwingConstants.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));

        final JPanel hostPanel = new JPanel();
        hostPanel.add(new JLabel("Host name:"));
        hostPanel.add(hostNameField);

        final JPanel buttons = new JPanel();
        buttons.add(createButton);

        add(title);
        add(Box.createRigidArea(new Dimension(0, 12)));
        add(hostPanel);
        add(Box.createRigidArea(new Dimension(0, 12)));
        add(buttons);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.execute(hostNameField.getText());
            }
        });

        // keep VM state updated on typing
        hostNameField.getDocument().addDocumentListener(new DocumentListener() {
            private void sync() {
                final CreateRoomState state = viewModel.getState();
                state.setHostName(hostNameField.getText());
                viewModel.setState(state);
            }

            public void insertUpdate(DocumentEvent e) { sync(); }
            public void removeUpdate(DocumentEvent e) { sync(); }
            public void changedUpdate(DocumentEvent e) { sync(); }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // unused
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final CreateRoomState state = (CreateRoomState) evt.getNewValue();
        hostNameField.setText(state.getHostName());
        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError());
        }
    }

    public void setController(CreateRoomController controller) {
        this.controller = controller;
    }

    public String getViewName() {
        return viewName;
    }
}
