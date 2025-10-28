package app;

import interface_adapter.ViewManagerModel;

/**
 * TODO: Compose application wiring.
 * - Instantiate ViewManagerModel and all ViewModels
 * - Build use case interactors with data-access and presenters
 * - Build controllers and views
 * - Register views to ViewManager and set initial active view
 */
public class AppBuilder {

    // TODO: Declare shared models (e.g., ViewManagerModel)
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();

    public AppBuilder() {
        // TODO: Initialize dependency graph for all 7 user stories
        // - Create Room (Host)
        // - Join Room (Participant)
        // - Search & Details
        // - Build & Lock Shortlist (Host)
        // - Vote & Winner
        // - Suggest Movie
        // - Content Filters (Host)
    }

    public void build() {
        // TODO: Construct and connect controllers, presenters, interactors, and views
        // TODO: Register views with a ViewManager and set initial view
    }
}

