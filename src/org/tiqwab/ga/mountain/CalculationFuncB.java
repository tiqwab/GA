package org.tiqwab.ga.mountain;

public class CalculationFuncB implements ICalculationFunc {

    @Override
    public float calc(float x, float y) {
        return (float) (-(Math.pow(x+100, 2) + Math.pow(y-50, 2)) / 200 + 60);
    }

}
