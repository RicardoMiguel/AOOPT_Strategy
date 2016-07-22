package com;

/**
 * Created by Ricardo on 19/05/2016.
 */
public class DefensiveStrategy extends Strategy{
    @Override
    public Move calculateNextMove(Board b) {
        int size=b.getSize();
        for(int i = 0; i<size;i++) {
            for (int j=0; j<size; ++j) {
                if (b.get(i,j) == Board.FREE) {
                    return new Move(i, j);
                }
            }
        }
        return null;
    }
}
