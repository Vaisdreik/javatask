package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBoard extends JFrame {
    static int dimension = 3;
    static int cellSize = 150;
    private char[][] gameField;
    private GameButton[] gameButtons;

    private Game game;

    static char nullSymbol = '\u0000';

    public GameBoard(Game game) {
        this.game = game;
        initField();
    }

    private void initField() {

        setBounds(cellSize * dimension, cellSize * dimension, 400, 300);
        setTitle("Крестики-нолики");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel  controlPanel  = new JPanel();
        JButton newGameButton = new JButton("Новая игра");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emptyField();
            }
        });

        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.add(newGameButton);
        controlPanel.setSize(cellSize * dimension, 150);

        JPanel gameFieldPanel = new JPanel();
        gameFieldPanel.setLayout(new GridLayout(dimension, dimension));
        gameFieldPanel.setSize(cellSize * dimension, cellSize * dimension);

        gameField = new char[dimension][dimension];
        gameButtons = new GameButton[dimension * dimension];

        for (int i = 0; i < (dimension * dimension); i++) {
            GameButton fieldButton = new GameButton(i, this);
            gameFieldPanel.add(fieldButton);
            gameButtons[i] = fieldButton;
        }

        getContentPane().add(controlPanel, BorderLayout.NORTH);
        getContentPane().add(gameFieldPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    void emptyField() {
        for (int i = 0; i < (dimension * dimension); i++) {
            gameButtons[i].setText("");

            int x = i / GameBoard.dimension;
            int y = i % GameBoard.dimension;

            gameField[x][y] = nullSymbol;
        }
    }

    Game getGame() {
        return game;
    }

    public GameButton getButton(int buttonIndex) {
        return gameButtons[buttonIndex];
    }

    boolean isTurnable(int x, int y) {
        boolean result = false;

        if (gameField[y][x] == nullSymbol)
            result = true;

        return result;
    }

    void updateGameField(int x, int y) {
        gameField[y][x] = game.getCurrentPlayer().getPlayerSign();
    }

    boolean checkWin() {
        boolean result = false;
        char playerSymbol = getGame().getCurrentPlayer().getPlayerSign();

        if (checkWiner(playerSymbol)){
            result = true;
        }
        return result;
    }

    private boolean checkWiner(char playerSymbol) {
        boolean result = false;
        boolean isLineBreak;  // флаг не заполненности линии рассматриваемым символом
        for (int i = 0; i < dimension; i++) {
            if (gameField[i][0] == playerSymbol) {
                isLineBreak = false;  // выставляем флаг при каждом внешнем цикле
                for (int j = 0; j < dimension; j++) {
                    if (gameField[i][j] != playerSymbol) {
                        // если в строке встречается другой символ, то нет смысла дальше просматривать эту строку, переходим на следующую
                        isLineBreak = true;
                        break;
                    }
                }
                if (!isLineBreak)
                    result = true;  // если линия не содержит других символов, кроме рассматриваемого, значит достигли условия победы
            }
            if (gameField[0][i] == playerSymbol) {
                // со столбцами аналогично, только индексы меняем, чтобы за один внешний цикл пройти сразу и строку и столбец
                isLineBreak = false;
                for (int j = 0; j < dimension; j++) {
                    if (gameField[j][i] != playerSymbol) {
                        isLineBreak = true;
                        break;
                    }
                }
                if (!isLineBreak) result = true;
            }
        }
        //проверяем диагональ \ на условие победы
        if (gameField[0][0] == playerSymbol) {
            isLineBreak = false;
            for (int i = 0; i < dimension; i++) {
                if (gameField[i][i] != playerSymbol) {
                    isLineBreak = true;
                    break;
                }
            }
            if (!isLineBreak) result = true;
        }
        // и обратную диагональ /
        if (gameField[0][dimension - 1] == playerSymbol) {
            isLineBreak = false;
            for (int i = 0; i < dimension; i++) {
                if (gameField[i][dimension - 1 - i] != playerSymbol) {
                    isLineBreak = true;
                    break;
                }
            }
            if (!isLineBreak) result = true;
        }

        return result;
    }

    boolean isFull() {
        boolean result = true;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (gameField[i][j] == nullSymbol)
                    result = false;
            }
        }
        return result;
    }


}
