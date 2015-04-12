package org.tiqwab.ga.example;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;

@SuppressWarnings("serial")
public class MinimizingMakeChangeFitnessFunction extends FitnessFunction {

    private static final int MAX_ALLOWED_EVOLUTIONS = 50;
    
    private final int m_targetAmount;
    
    MinimizingMakeChangeFitnessFunction(int a_targetAmount) {
        if (a_targetAmount < 1 || a_targetAmount > 99) {
            throw new IllegalArgumentException("Change amount must be between 1 and 99.");
        }
        this.m_targetAmount = a_targetAmount;
    }
    
    @Override
    protected double evaluate(IChromosome a_subject) {
        int changeAmount = amountOfChange(a_subject);
        int totalCoints = getTotalNumberOfCoins(a_subject);
        int changeDifference = Math.abs(m_targetAmount - changeAmount);
        
        double fitness = (99 - changeDifference);
        
        if (changeAmount == m_targetAmount) {
            fitness += 100 - (10 * totalCoints);
        }
        
        return fitness;
    }
    
    public static int amountOfChange(IChromosome a_subject) {
        int numOfQuarters = getNumberOfCoinsAtGene(a_subject, 0);
        int numOfDimes = getNumberOfCoinsAtGene(a_subject, 1);
        int numOfNickels = getNumberOfCoinsAtGene(a_subject, 2);
        int numOfPennies = getNumberOfCoinsAtGene(a_subject, 3);
        
        return ((numOfQuarters * 25) + (numOfDimes * 10) + (numOfNickels * 5) + (numOfPennies * 1));
    }
    
    public static int getNumberOfCoinsAtGene(IChromosome a_subject, int a_position) {
        Integer numCoins = (Integer) a_subject.getGene(a_position).getAllele();
        return numCoins.intValue();
    }
    
    public static int getTotalNumberOfCoins(IChromosome a_subject) {
        int totalCoins = 0;
        
        int numberOfGenes = a_subject.size();
        for (int i = 0; i < numberOfGenes; i++) {
            totalCoins += getNumberOfCoinsAtGene(a_subject, i);
        }
        
        return totalCoins;
    }
    
    
    public static void main(String[] args) throws InvalidConfigurationException {
        Configuration conf = new DefaultConfiguration();
        
        int targetAmount = Integer.parseInt(args[0]);
        FitnessFunction myFunc = new MinimizingMakeChangeFitnessFunction(targetAmount);
        conf.setFitnessFunction(myFunc);
        
        Gene[] sampleGenes = new Gene[4];
        sampleGenes[0] = new IntegerGene(conf, 0, 3);
        sampleGenes[1] = new IntegerGene(conf, 0, 2);
        sampleGenes[2] = new IntegerGene(conf, 0, 1);
        sampleGenes[3] = new IntegerGene(conf, 0, 4);
        
        Chromosome sampleChromosome = new Chromosome(conf, sampleGenes);
        conf.setSampleChromosome(sampleChromosome);
        
        conf.setPopulationSize(500);
        
        Genotype population = Genotype.randomInitialGenotype(conf);
       
        for (int i = 0; i < MAX_ALLOWED_EVOLUTIONS; i++) {
            population.evolve();
        }
        IChromosome bestSolutionSoFar = population.getFittestChromosome();
        
        System.out.println("The best solution contained the following: ");
        System.out.println(
                MinimizingMakeChangeFitnessFunction.getNumberOfCoinsAtGene(
                    bestSolutionSoFar, 0 ) + " quarters." );

            System.out.println(
                MinimizingMakeChangeFitnessFunction.getNumberOfCoinsAtGene(
                    bestSolutionSoFar, 1 ) + " dimes." );

            System.out.println(
                MinimizingMakeChangeFitnessFunction.getNumberOfCoinsAtGene(
                    bestSolutionSoFar, 2 ) + " nickels." );

            System.out.println(
                MinimizingMakeChangeFitnessFunction.getNumberOfCoinsAtGene(
                    bestSolutionSoFar, 3 ) + " pennies." );

            System.out.println( "For a total of " +
                MinimizingMakeChangeFitnessFunction.amountOfChange(
                    bestSolutionSoFar ) + " cents in " +
                MinimizingMakeChangeFitnessFunction.getTotalNumberOfCoins(
                    bestSolutionSoFar ) + " coins." );
        
    }

}
