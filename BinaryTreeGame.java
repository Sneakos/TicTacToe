
/**
 * Tic Tac Toe game with basic GUI and a working MinMax AI
 * 
 * @author Alex
 * @date 2016
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class BinaryTreeGame extends JFrame implements ActionListener
{
    private static final int WIDTH = 500, HEIGHT = 500;
    private JButton[] buttons = new JButton[9];
    private final Dimension gameSize = new Dimension(WIDTH, HEIGHT);
    private int count, choice;
    private int[][] board;
    private boolean user, computer;
    Object[] options = { "Play Again", "Switch who starts", "Exit" };
    Object[] options2 = { "Me", "Computer" };
    TicTacToe tGame;
    int start;
    int runs;

    /**
     * Creates the window, intializes instance variables, and present you with a dialog for who goes first
     *
     */
    public BinaryTreeGame()
    {
        createWindow();
        board = new int[3][3];
        user = computer = false;
        start = 1;
        int choice = JOptionPane.showOptionDialog(this, "Who is going first?", "Start", 
                JOptionPane.YES_NO_OPTION ,JOptionPane.QUESTION_MESSAGE, null, options2, options[0]);
        tGame = new TicTacToe();
        if(choice == 1)
        {
            aiTurn();
            start = 0;
        }
    }

    /**
     * Creates a window with 9 buttons, to represent the tic tac toe gameboard
     *
     */
    public void createWindow() 
    {
        setVisible(true);
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(gameSize);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new GridLayout(3, 3));
        addButtons();

        setPreferredSize(gameSize);
        setMinimumSize(gameSize);
        setMaximumSize(gameSize);

        pack();
    }

    /**
     * Adds buttons to the frame, each of which represents a spot on the tic tac toe board
     *
     */
    public void addButtons()
    {
        for(int i = 0; i < buttons.length; i++)
        {
            buttons[i] = new JButton();
            buttons[i].setFont(new Font(null, Font.PLAIN, 75));
            add(buttons[i]);
            buttons[i].addActionListener(this);
        }
    }

    /**
     * This changes the text of the button for the user, and alters the 2d array to reflect the change
     *
     * @param e A button being presesd
     */
    public void actionPerformed(ActionEvent e)
    {
        JButton button = (JButton) e.getSource();
        choice = Arrays.asList(buttons).indexOf(button);
        if(!button.getText().equals(""))
            return;

        button.setText("O");
        board[choice / 3][choice % 3] = 10;

        if(!user && !computer)
            if(checkWin())
                return;

        if(!full()){
            aiTurn();
        }

        if(!user && !computer)
            checkWin();
    }

    /**
     * This tests to see if the board is full, in order to prevent errors
     *
     * @return Whether or not the board is full
     */
    public boolean full()
    {
        for(int i = 0; i < board.length; i++)
        {
            for(int j = 0; j < board.length; j++)
            {
                if(board[i][j] == 0)
                {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Calls the main TicTacToe class to run the minmax algorithm and make a move for the AI. Interesting note - If the AI can win in multiple ways, it will troll you
     *
     */
    public void aiTurn()
    {
        setTitle("Thinking...");
        Node<Integer> node = new Node<Integer>();
        node.data = board;
        tGame.move(node, 0);
        choice = tGame.endGame(node);
        JButton button = buttons[choice];
        button.setText("X");
        board[choice / 3][choice % 3] = 1;
        setTitle("Tic Tac Toe");
        checkWin();
    }

    /**
     * Checks to see if either player has won
     *
     */
    public void check()
    {
        int win, win2, dWin, dWin2;

        dWin = board[0][0] + board[1][1] + board[2][2];
        dWin2 = board[0][2] + board[1][1] + board[2][0];

        for(int i = 0; i < 3; i ++)
        {
            win = win2 = 0;
            for(int j = 0; j < 3; j++)
            {
                win += board[i][j];
                win2 += board[j][i];
            }
            if(win == 3 || win2 == 3 || dWin == 3 || dWin2 == 3)
            {
                computer = true;
                return;
            }
            if(win == 30 || win2 == 30 || dWin == 30 || dWin2 == 30)
            {
                user = true;
                return;
            }
        }
    }

    /**
     * If someone has won, displays end game popup, asking to either restart, restart with opposing starter, or exit
     *
     */
    public boolean checkWin()
    {
        check();
        String winner1, winner2;
        winner1 = winner2 = "";
        boolean over = false;
        if(user)
        {
            winner1 = "You win!!!";
            winner2 = "You somehow managed to win";
            over = true;
        }
        else if(computer)
        {
            winner1 = "Computer wins";
            winner2 = "The computer won the game";
            over = true;
        }
        else if(full())
        {
            winner1 = "Cat game :(";
            winner2 = "The game was a draw";
            over = true;
        }
        if(over)
        {
            setTitle(winner1);
            int choice = JOptionPane.showOptionDialog(this, winner2, "Result", 
                    JOptionPane.YES_NO_CANCEL_OPTION ,JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if(choice == 0)
                resetGame();
            else if(choice == 1)
            {
                resetGame();
                start++;
                start %= 2;
                if(start == 0)
                    aiTurn();
            }
            else
            {
                this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            }
        }
        return over;
    }

    /**
     * Resets the game by setting the board array to 0, and setting all the button texts to empty strings
     *
     */
    public void resetGame()
    {
        user = computer = false;
        tGame = null;
        tGame = new TicTacToe();
        setTitle("Tic Tac Toe");
        for(int i = 0; i < buttons.length; i++)
        {
            buttons[i].setText("");
        }
        for(int i = 0; i < board.length; i++)
        {
            for(int j = 0; j < board.length; j++)
                board[i][j] = 0;
        }
    }
}
