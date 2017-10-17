
/**
 * O
 * 
 * @author Alex Van Keulen
 * @date Dec 2016
 */
import java.util.ArrayList;

public class TicTacToe
{
    public TicTacToe(){}

    /**
     * Makes a move for the AI by populating a decision tree, and recursively checking the nodes for either A) Max if it is the computers turn, or B) Min if it is the users turn
     *
     * @param root Initially the original board
     * @param player Whoevers turn it is
     * @return Returns either the minimum or maximum nodes of a level, depending on whose turn it is
     */
    public int move(Node<Integer> root, int player)
    {
        player %= 2;
        populate(player * 9 + 1, root); //sends player param as either 1 or 10
        if(root.children.size() == 0) //board is full
            return 0;
        int score = 0;
        for(int i = 0; i < root.children.size(); i++)
        {
            score = check(root.children.get(i).data);
            root.children.get(i).score = score;
            if(score == 0)
            {
                root.children.get(i).score = move(root.children.get(i), player + 1);
            }
        }
        if(player == 0)
            return max(root).score;
        else
            return min(root).score;
    }

    /**
     * This returns the node that has the maximum value in a level
     *
     * @param root The parent of the level
     * @return The node with the maximum value
     */
    public Node<Integer> max(Node<Integer> root)
    {
        int max = -10;
        int j = 0;
        for(int i = 0; i < root.children.size(); i++)
        {
            if(root.children.get(i).score > max)
            {
                max = root.children.get(i).score;
                j = i;
            }
        }
        return root.children.get(j);
    }

    /**
     * This returns the node that has the minimum value in a level
     *
     * @param root The parent of the level
     * @return The node with the minimum value
     */
    public Node<Integer> min(Node<Integer> root)
    {
        int min = 10;
        int j = 0;
        for(int i = 0; i < root.children.size(); i++)
        {
            if(root.children.get(i).score < min)
            {
                min = root.children.get(i).score;
                j = i;
            }
        }
        return root.children.get(j);
    }

    /**
     * Gets the board space of the node that has the maximum value. If two or more nodes have the same max value, it randomly chooses between them
     *
     * @param root The parent of the level
     * @return The board space corresponding to the best move
     */
    public int endGame(Node<Integer> root)
    {
        int max = -10;
        ArrayList<Integer> j = new ArrayList<Integer>();
        for(int i = 0; i < root.children.size(); i++)
        {
            if(root.children.get(i).score >= max)
            {
                if(root.children.get(i).score > max)
                    j.clear();
                max = root.children.get(i).score;
                j.add(i);
            }
        }
        int i = (int) Math.floor(Math.random() * j.size());
        i = j.get(i);
        return getSpace(root.data, root.children.get(i).data);
    }

    /**
     *  Get's the space the node is pointing to by comparing the parent and child node, and seeing which index is different
     *
     * @param arr1 The parent nodes array
     * @param arr2 The child nodes array
     * @return The space the node is referring to
     */
    public int getSpace(int[][] arr1, int[][] arr2)
    {
        for(int i = 0; i < arr1.length; i++)
        {
            for(int j = 0; j < arr2.length; j++)
            {
                if(arr1[i][j] != arr2[i][j])
                    return (i*3 + j);
            }
        }
        return 0;
    }

    /**
     * Populates the root given with all possible moves
     *
     * @param player The player (1 or 10) whose turn it is
     * @param root The root to populate
     */
    public void populate(int player, Node<Integer> root)
    {
        int[][] board = root.data;
        int[][] tempBoard = new int[3][3];
        for(int i = 0; i < board.length; i++)
            tempBoard[i] = board[i].clone();
        for(int i = 0; i < board.length; i++)
        {
            for(int j = 0; j < board.length; j++)
            {
                if(board[i][j] == 0) //empty
                {
                    tempBoard[i][j] = player;
                    root.addNode(tempBoard);
                    tempBoard[i][j] = 0;
                }
            }
        }
    }

    /**
     * Checks to see who, if anyone, won
     *
     * @param board The game board
     * @return Returns 10 if computer wins, -10 if user wins, or 0 is no one wins
     */
    public int check(int[][] board)
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
                return 10; //computer wins
            }
            if(win == 30 || win2 == 30 || dWin == 30 || dWin2 == 30)
            {
                return -10; //user wins
            }
        }
        return 0; //no winner
    }
}
