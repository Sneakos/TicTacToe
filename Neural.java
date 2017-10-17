
/**
 * Write a description of class Neural here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.ArrayList;

public class Neural
{
    FileReadWrite frw;
    int board[][];
    ArrayList<Integer> choices; //parallel
    ArrayList<String> lines;    //arrays

    public Neural()
    {
        frw = new FileReadWrite("History.txt", true);
        board = new int[3][3];
        choices = new ArrayList<Integer>();
        lines = new ArrayList<String>();
    }

    public int getMove(int board[][])
    {
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                this.board[i][j] = board[i][j];
        String gameBoard = toStr(board);
        String search = search(gameBoard);
        int choice = 0;
        if(!search.equals(""))
        {
            choice = getChoice(search, board);
            lines.add(search);
            choices.add(choice);
            return choice;
        } else {
            search = closest(gameBoard);
            addLine(gameBoard, search.split(" ")[1]);
            choice = getChoice(search, board);
            lines.add(search);
            choices.add(choice);
            return choice;
        }
    }

    public void addLine(String gameBoard, String regex)
    {
        frw.write(gameBoard + " " + regex);
    }

    public String search(String search)
    {
        return frw.findLine(search);
    }

    public String closest(String line)
    {
        return frw.findClosest(line);
    }

    public int getChoice(String line, int board[][])
    {
        int choice = 0;
        ArrayList<Integer> choices = new ArrayList<Integer>();
        String weights = line.split(" ")[1];
        String nodes[] = weights.split("[)]");
        int index = 0;
        while(board[index / 3][index % 3] != 0)
            index++;
        int largest = Integer.parseInt(nodes[index].split("[(]")[1]);
        choices.add(index);
        for(int i = index; i < 9; i++)
        {
            int weight = Integer.parseInt(nodes[i].split("[(]")[1]);
            if(weight == largest)
            {
                if(board[i / 3][i % 3] == 0)
                    choices.add(i);
            } else if(weight > largest) {
                if(board[i / 3][i % 3] == 0)
                {
                    choices.clear();
                    choices.add(i);
                    largest = weight;
                }
            }
        }
        choice = choices.get((int) Math.floor(Math.random() * choices.size()));
        return choice;
    }

    public void eval(boolean tie, int modifier)
    {
        for(int i = 0; i < lines.size(); i++)
        {
            try{
                frw.replace(lines.get(i), choices.get(i), i*modifier, tie);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Error");
            }
        }
        choices.clear();
        lines.clear();
    }

    public String toStr(int board[][])
    {
        String ret = "";
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
            {
                if(board[i][j] == 10)
                    ret += 2;
                else ret += board[i][j];
            }

        return ret;
    }

}
