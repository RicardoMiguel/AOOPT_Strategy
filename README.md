# Strategy exercise - tic tac toe game

You have the code of a simple SWING TicTacToe game. It uses the Strategy design pattern to calculate the next computer move. The algorithm employed is very easy - it takes a random free field as the next computer move. Write 2 more strategies to make the game harder to the player:
* Defensive - looking for the player marks ("O") sequences and putting the computer mark ("X") at one end of the sequence
* Offensive - building its own sequence of winning computer marks ("X").

It should be possible to change the strategy in run-time.

If you are familiar with Java Swing Framework, add a start window that allows the player to choose:
* Size of the board;
* Length of the winning sequence.

```
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class NotFreeException extends RuntimeException {}
class Board {
    public static final int FREE=0, COMPUTER=1, PLAYER=2, BLOCKED=3;
    private int[][] board;
    int size, win;
    public Board(int s, int w) {
	size=s; win=w;
	board = new int[size][];
	for (int i=0; i< size;++i) board[i]=new int [size];
    }
    public int get (int i, int j) { return board[i][j]; }
    public void set (int i, int j, int m) throws NotFreeException
    {board[i][j]=m; }
    //return FREE (game not finish), COMPUTER (computer won),
    //	PLAYER (player won), BLOCKED (no more moves)
    public int checkWon() {
	int mark, number;
	for (int i=0; i<size; ++i) {
    	    for (int j=0; j<size; ++j) {
    		if (board[i][j]==FREE) continue;
    		mark=board[i][j];
    		//horizontal
    		number=1;
    		for (int k=1; k<win; ++k) {
    		    if (k+j>=size) break;
    		    if (board[i][j+k]==mark) ++ number;
    		    if (number==win) return mark;
    		}
    		//vertical
    		number=1;
    		for (int k=1; k<win; ++k) {
    		    if (k+i>=size) break;
    		    if (board[i+k][j]==mark) ++ number;
    		    if (number==win) return mark;
    		}
    		//right-down
    		number=1;
    		for (int k=1; k<win; ++k) {
    		    if (k+i>=size || k+j>=size) break;
    		    if (board[i+k][j+k]==mark) ++ number;
    		    if (number==win) return mark;
    		}
    		//left-down
    		number=1;
    		for (int k=1; k<win; ++k) {
    		    if (i+k>=size || j-k<0) break;
    		    if (board[i+k][j-k]==mark) ++ number;
    		    if (number==win) return mark;
    		}

    	    }
    	}
	for (int[] bb : board)
	    for (int b : bb) if (b==FREE)  return FREE;
	return BLOCKED;
    }
    public int getSize() { return size; }
}

class Move { public int row, col; public Move(int r, int c) { row=r; col=c; } }

abstract class Strategy {
    public abstract Move calculateNextMove(Board b) ;
}
class RandomStrategy extends Strategy {
    public Move calculateNextMove(Board b) {
	int size=b.getSize();
	Random r=new Random(System.currentTimeMillis());
	int[][] visited=new int[size][size];
	int square=size*size;
	while (square>0) {
	    int hit=r.nextInt(square);
	    for (int i=0; i<size; ++i)
		for (int j=0; j<size; ++j) {
		    if (b.get(i,j) != Board.FREE) continue;
		-- hit;
		if (hit<0) return new Move(i, j);
	    }
	    --square;
	}
	return null;
    }
}
class MyButton extends JButton {
    public int row, col;
    public MyButton(String text, int r, int c)
    { super(text); row=r; col=c; }
}

class Game extends JFrame {
    private Board board;
    private Strategy str=new RandomStrategy();
    MyButton buttons[][];
    private class MyActionListener implements ActionListener {
	private void check() {
	    int ckeck=board.checkWon();
	    switch (ckeck) {
	        case Board.COMPUTER:
    	    	    JOptionPane.showMessageDialog(null, "Computer won !");
		    System.exit(0);
		    break;
		case Board.PLAYER:
    		    JOptionPane.showMessageDialog(null, "You won !");
		    System.exit(0);
		    break;
		case Board.BLOCKED:
    		    JOptionPane.showMessageDialog(null, "Nobody won !");
		    System.exit(0);
		    break;
		}
	    }
	public void actionPerformed(ActionEvent evt) {
	    MyButton bt=(MyButton)(evt.getSource());
	    bt.setText(" O ");
	    board.set(bt.row, bt.col, Board.PLAYER);
	    bt.removeActionListener(this);
	    check();
	    Move m=str.calculateNextMove(board);
	    bt=buttons[m.row][m.col];
	    bt.setText(" X ");
	    bt.removeActionListener(this);
	    board.set(bt.row, bt.col, Board.COMPUTER);
	    check();
	}
    }
    public Game(Board b) {
	board=b;
	int s=board.getSize();
	buttons=new MyButton[s][s];
	ActionListener al=new MyActionListener();
	getContentPane().setLayout(new GridLayout(s,s));
	for (int i=0;i<s;++i)
	    for (int j=0;j<s;++j) {
		buttons[i][j]=new MyButton("   ", i, j);
		buttons[i][j].addActionListener(al);
		add(buttons[i][j]);
	    }
	pack();
    }
}
public class TicTacToe {
    public static void main (String[] args) {
	Board board=new Board(3,3);
	new Game(board).setVisible(true);

    }
}
```