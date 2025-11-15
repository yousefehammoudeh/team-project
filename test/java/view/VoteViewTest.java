package view;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VoteViewTest {

    @Test
    public void testFivePostersPressAndRanking() throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(() -> {
            VoteView voteView = new VoteView();

            List<String> candidates = Arrays.asList("Movie-A", "Movie-B", "Movie-C", "Movie-D", "Movie-E");
            voteView.setCandidates(candidates);

            // Ensure five poster buttons were created
            List<JButton> posters = voteView.getPosterButtons();
            assertEquals(5, posters.size(), "There should be 5 poster buttons");

            // Press posters in a specific order: C, A, E, B, D
            JButton posterC = posters.get(2);
            JButton posterA = posters.get(0);
            JButton posterE = posters.get(4);
            JButton posterB = posters.get(1);
            JButton posterD = posters.get(3);

            posterC.doClick();
            posterA.doClick();
            posterE.doClick();
            posterB.doClick();
            posterD.doClick();

            // Validate internal rankings order
            List<String> expectedOrder = Arrays.asList("Movie-C", "Movie-A", "Movie-E", "Movie-B", "Movie-D");
            assertEquals(expectedOrder, voteView.getRankings(), "Ranking order should match the press order");

            // Validate rank labels text appear below each poster
            List<JLabel> rankLabels = voteView.getRankLabels();
            assertEquals("1", rankLabels.get(2).getText(), "Movie-C should show rank 1");
            assertEquals("2", rankLabels.get(0).getText(), "Movie-A should show rank 2");
            assertEquals("3", rankLabels.get(4).getText(), "Movie-E should show rank 3");
            assertEquals("4", rankLabels.get(1).getText(), "Movie-B should show rank 4");
            assertEquals("5", rankLabels.get(3).getText(), "Movie-D should show rank 5");
        });
    }
}
