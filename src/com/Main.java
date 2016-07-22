package com;

public class Main {

    public static void main (String[] args) {
        Board board=new Board(3,3);
        new Game(board).setVisible(true);

    }
}
