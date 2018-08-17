package Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameActionListener implements ActionListener {
    private int row;
    private int cell;
    private GameButton button;

    public GameActionListener(int rowNum, int cellNum, GameButton gameButton) {
        this.row = rowNum;
        this.cell = cellNum;
        this.button = gameButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GameBoard board = button.getBoard();

        if (board.isTurnable(row, cell)) {
            updateByPlayersData(board);

            if (board.isFull()) {
                board.getGame().showMessage("Ничья");
                board.emptyField();
            } else {
                updateByAIData(board);
            }
        } else {
            board.getGame().showMessage("Некорректный ход!");
        }
    }

    private void updateByAIData(GameBoard board) {
        int    x, y;
        Random rnd = new Random();
        do {
            x = rnd.nextInt(GameBoard.dimension);
            y = rnd.nextInt(GameBoard.dimension);
        } while (!board.isTurnable(x, y));

        board.updateGameField(x,y);

        int cellIndex = GameBoard.dimension*x+y;
        board.getButton(cellIndex).setText(Character.toString(board.getGame().getCurrentPlayer().getPlayerSign()));

        if (board.checkWin()) {
            button.getBoard().getGame().showMessage("Компьютер выиграл!");
            board.emptyField();
        }
        else {
            board.getGame().passTurn();
        }
    }

    private void updateByPlayersData(GameBoard board) {
        board.updateGameField(row, cell);
        button.setText(Character.toString(board.getGame().getCurrentPlayer().getPlayerSign()));
        if (board.checkWin()) {
            button.getBoard().getGame().showMessage("Вы выиграли!");
            board.emptyField();
        } else {
            board.getGame().passTurn();
        }
    }
}
