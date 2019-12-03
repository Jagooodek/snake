import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    Board board;
    ScorePanel scorePanel;

    public Main() throws InterruptedException {
        initUi();
    }

    private void initUi()  {
        board = new Board(this);
        scorePanel = new ScorePanel(this);
        add(board, BorderLayout.PAGE_START);
        add(scorePanel, BorderLayout.PAGE_END);

        setResizable(false);
        pack();

        setTitle("Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public ScorePanel getScorePanel(){
        return scorePanel;
    }

    public Board getBoard() {
        return board;
    }
    public static void main(String[] args) throws InterruptedException {
        Main main = new Main();
        main.setVisible(true);
    }
}
