package org.tiqwab.ga.mountain;

public class CalculationFuncA implements ICalculationFunc {

    @Override
    public float calc(float x, float y) {
        double wk = (Math.sqrt(x*x + y*y) * Math.PI/180);
        return (float) (50 * (Math.cos(wk) + Math.cos(3*wk)));
    }

}
