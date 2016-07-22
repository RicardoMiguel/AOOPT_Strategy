package com;

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
