package use_case.winner;

public interface WinnerOutputBoundary {
    void present(WinnerOutputData data);

    void presentFailure(String message);
}
