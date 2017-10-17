
/**
 * A java representation of a tree, or more accurately, the nodes on the tree
 * 
 * @author Alex Van Keulen
 * @date Dec 2016
 */
import java.util.List;
import java.util.ArrayList;

public class Node<Integer> {
    int score;
    int[][] data;
    ArrayList<Node<Integer>> children;

    public Node() { 
        children = new ArrayList<Node<Integer>>();
        score = 0;
        data = new int[3][3];
    }

    /**
     * Adds a node to the root node
     *
     * @param data The game board
     */
    public void addNode(int[][] data)
    {
        Node<Integer> node = new Node<Integer>();
        node.score = 0;
        node.data = new int[3][3];
        for(int i = 0; i < data.length; i++)
            node.data[i] = data[i].clone();
        children.add(node);
    }
}

