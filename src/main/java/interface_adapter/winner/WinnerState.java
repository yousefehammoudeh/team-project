package interface_adapter.winner;

import javax.swing.ImageIcon;

public class WinnerState {
    private String title;
    private String details;
    private ImageIcon poster;

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }

    public ImageIcon getPoster() {
        return poster;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setPoster(ImageIcon poster) {
        this.poster = poster;
    }
}
