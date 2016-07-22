package com;

/**
 * Created by Ricardo on 19/05/2016.
 */
public class StrategiesFactory {

    public Strategy getStrategy(StrategyMethod str){
        if(str == StrategyMethod.RANDOM){
            return new RandomStrategy();
        } else if(str == StrategyMethod.DEFENSIVE){
            return new DefensiveStrategy();
        } else if(str == StrategyMethod.OFFENSIVE){
            return new OffensiveStrategy();
        }
        return null;
    }
}
