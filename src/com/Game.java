package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Game extends JFrame {
    private Board board;
    private Strategy str = new StrategiesFactory().getStrategy(StrategyMethod.RANDOM);
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
        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(s,s));
        getContentPane().add(grid, BorderLayout.CENTER);

        for (int i=0;i<s;++i)
            for (int j=0;j<s;++j) {
                buttons[i][j]=new MyButton("   ", i, j);
                buttons[i][j].addActionListener(al);
                grid.add(buttons[i][j]);
            }

        JComboBox strategies = new JComboBox(StrategyMethod.values());
        strategies.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                str = new StrategiesFactory().getStrategy((StrategyMethod)cb.getSelectedItem());
            }
        });
        getContentPane().add(strategies, BorderLayout.SOUTH);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
