package com;

import java.util.Random;

class RandomStrategy extends Strategy {
    public Move calculateNextMove(Board b) {
        int size=b.getSize();
        Random r=new Random(System.currentTimeMillis());
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
