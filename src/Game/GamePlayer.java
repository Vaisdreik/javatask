package Game;

public class GamePlayer {
    private char playerSign;
    private boolean humanPlayer = true;

    public GamePlayer(boolean isHumanPlayer, char playerSign) {
        this.humanPlayer = isHumanPlayer;
        this.playerSign = playerSign;
    }

    public boolean isHumanPlayer() { return humanPlayer;}

    public char getPlayerSign() {
        return playerSign;
    }
}
