package view;

import interface_adapter.create_room.CreateRoomViewModel;
import interface_adapter.create_room.CreateRoomController;
import interface_adapter.create_room.CreateRoomState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CreateRoomView extends JPanel implements ActionListener, PropertyChangeListener {

    private final CreateRoomViewModel createRoomViewModel;

    private CreateRoomController createRoomController;

    private final JTextField hostNameField = new JTextField(15);
    private final JButton createRoomButton = new JButton("Create Room");

    public CreateRoomView(CreateRoomViewModel createRoomViewModel) {
        this.createRoomViewModel = createRoomViewModel;
        this.createRoomViewModel.addPropertyChangeListener(this);

        // Layout
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Create Room");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nameLabel = new JLabel("Enter your name:");
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        hostNameField.setMaximumSize(new Dimension(200, 30));
        hostNameField.setAlignmentX(Component.CENTER_ALIGNMENT);

        createRoomButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        createRoomButton.addActionListener(this);

        this.add(title);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        this.add(nameLabel);
        this.add(hostNameField);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        this.add(createRoomButton);
    }

    public void setCreateRoomController(CreateRoomController controller) {
        this.createRoomController = controller;
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if (e.getSource().equals(createRoomButton)) {
            String hostName = hostNameField.getText();
            createRoomController.execute(hostName);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        CreateRoomState state = createRoomViewModel.getState();

        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}