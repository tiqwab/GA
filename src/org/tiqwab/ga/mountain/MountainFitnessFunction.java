package org.tiqwab.ga.mountain;

import java.util.Arrays;

import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;

public class MountainFitnessFunction extends FitnessFunction {
    
    ICalculationFunc func;
    
    public static final int[] BIT_TABLE = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512};
    
    
    public MountainFitnessFunction(ICalculationFunc func) {
        this.func = func;
    }
    
    
    @Override
    protected double evaluate(IChromosome a_subject) {
        int[] xy = MountainFitnessFunction.convChromToXY(a_subject);
        float calculated = func.calc(xy[0], xy[1]);
        return (calculated > 0) ? calculated : 0;
        
        /*
        Gene[] genes = a_subject.getGenes();
        int[] intGenes = new int[genes.length];
        for (int i = 0; i < intGenes.length; i++) {
            intGenes[i] = (int) genes[i].getAllele();
        }
        
        int len = genes.length / 2;
        int[] arrayX = Arrays.copyOfRange(intGenes, 0, len);
        int[] arrayY = Arrays.copyOfRange(intGenes, len, genes.length);
        int x = MountainFitnessFunction.convBitToInt(arrayX);
        int y = MountainFitnessFunction.convBitToInt(arrayY);
        
        float calculated = func.calc(x, y);
        return (calculated > 0) ? calculated : 0;
        */
 /*       
        float x = 0;
        float y = 0;
        
        Gene[] genes = a_subject.getGenes();
        int len = genes.length / 2;
        
        for (int i = 0; i < len; i++) {
            x += ((int) genes[i].getAllele()) * BIT_TABLE[i];
            y += ((int) genes[i+len].getAllele()) * BIT_TABLE[i];
        }
        
        float calculated = func.calc(x,  y);
        return (calculated > 0) ? calculated : 0;
*/
    }
    
    
    public static int[] convChromToXY(IChromosome chrom) {
        Gene[] genes = chrom.getGenes();
        int[] intGenes = new int[genes.length];
        for (int i = 0; i < intGenes.length; i++) {
            intGenes[i] = (int) genes[i].getAllele();
        }
        
        int len = genes.length / 2;
        int[] arrayX = Arrays.copyOfRange(intGenes, 0, len);
        int[] arrayY = Arrays.copyOfRange(intGenes, len, genes.length);
        int x = MountainFitnessFunction.convBitToInt(arrayX);
        int y = MountainFitnessFunction.convBitToInt(arrayY);
        
        return new int[]{x, y};
    }
    
    
    public static int convBitToInt(int[] array) {
        int integer = 0;
        for (int i = 0; i < array.length; i++) {
            integer += array[i] * BIT_TABLE[i];
        }      
        if (integer > BIT_TABLE[array.length-1]) {
            integer = integer - BIT_TABLE[array.length];
        }
        return integer;
    }
    
    
    public static int convBitToInt(float[] floatArray) {
        int[] intArray = new int[floatArray.length];
        for (int i = 0; i < floatArray.length; i++) {
            intArray[i] = (int) floatArray[i];
        }
        return convBitToInt(intArray);
    }

}
