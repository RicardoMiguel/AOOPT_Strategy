package com;

/**
 * Created by Ricardo on 19/05/2016.
 */
public class OffensiveStrategy extends Strategy{

    @Override
    public Move calculateNextMove(Board b) {
        int size=b.getSize();
        for(int i = size-1; i>=0;i--) {
            for (int j=size-1; j>=0; --j) {
                if (b.get(i,j) == Board.FREE) {
                    return new Move(i, j);
                }
            }
        }
        return null;
    }
}
