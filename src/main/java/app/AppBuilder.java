package app;

import interface_adapter.ViewManagerModel;
import data_access.room.InMemoryRoomDataAccessObject;
import interface_adapter.vote.VotePresenter;
import interface_adapter.vote.VoteViewModel;
import interface_adapter.vote.VoteController;
import use_case.vote.VoteInteractor;
import view.ViewManager;
import view.VoteView;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collections;

/**
 * TODO: Compose application wiring.
 * - Instantiate ViewManagerModel and all ViewModels
 * - Build use case interactors with data-access and presenters
 * - Build controllers and views
 * - Register views to ViewManager and set initial active view
 */
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    public void build() {

    }
}
