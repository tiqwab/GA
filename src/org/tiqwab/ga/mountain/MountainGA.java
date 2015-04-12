package org.tiqwab.ga.mountain;

import java.util.Arrays;
import java.util.List;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.DefaultFitnessEvaluator;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;

import processing.core.PApplet;

public class MountainGA {
    
    ICalculationFunc func;
    
    Genotype population;
    
    public static final int HALF_GENE = 8;
    
    int evolveCount = 0;
    int populationSize = 0;
    int evolveNum = 0;
    
    
    public MountainGA(ICalculationFunc func, GraphDrawer drawer, int a_populationSize, int a_evolveNum) throws InvalidConfigurationException {
        //this.func = func;
        //this.drawer = drawer;
        setup(func, a_populationSize, a_evolveNum);
    }
    
    
    public final void setup(ICalculationFunc func, int a_populationSize, int a_evolveNum) throws InvalidConfigurationException {
        this.func = func;
        
        Configuration conf = new DefaultConfiguration();
        Configuration.reset();
        conf.setFitnessEvaluator(new DefaultFitnessEvaluator());
        populationSize = a_populationSize;
        evolveNum = a_evolveNum;
        
        FitnessFunction fitnessFunction = new MountainFitnessFunction(func);
        conf.setFitnessFunction(fitnessFunction);
        
        Gene[] sampleGenes = new Gene[HALF_GENE * 2];
        for (int i = 0; i < sampleGenes.length; i++) {
            sampleGenes[i] = new IntegerGene(conf, 0, 1);
        }
        
        Chromosome sampleChromosome = new Chromosome(conf, sampleGenes);
        conf.setSampleChromosome(sampleChromosome);
        
        conf.setPopulationSize(a_populationSize);
        population = Genotype.randomInitialGenotype(conf);
    }
    
   /* 
    public void evolve(int a_populationSize, int a_evolveNum) throws InvalidConfigurationException {
        Configuration conf = new DefaultConfiguration();
        Configuration.reset();
        conf.setFitnessEvaluator(new DefaultFitnessEvaluator());
        populationSize = a_populationSize;
        evolveNum = a_evolveNum;
        
        FitnessFunction fitnessFunction = new MountainFitnessFunction(func);
        conf.setFitnessFunction(fitnessFunction);
        
        Gene[] sampleGenes = new Gene[16];
        for (int i = 0; i < sampleGenes.length; i++) {
            sampleGenes[i] = new IntegerGene(conf, 0, 1);
        }
        
        Chromosome sampleChromosome = new Chromosome(conf, sampleGenes);
        conf.setSampleChromosome(sampleChromosome);
        
        conf.setPopulationSize(a_populationSize);
        Genotype population = Genotype.randomInitialGenotype(conf);
        
        for (int i = 0; i < evolveNum; i++) {
            population.evolve();
        }
        
        IChromosome bestSolutionSoFar = population.getFittestChromosome();
        Arrays.stream(bestSolutionSoFar.getGenes()).forEach(s -> {System.out.println(String.format("%d,", s.getAllele()));});
    }
    */
    
    public void evolve() {
        if (evolveCount < evolveNum) {
            population.evolve();
            evolveCount++;
            System.out.println(String.format("Evolve: %d", evolveCount));
            IChromosome bestChrom = population.getFittestChromosome();
            int[] xy = MountainFitnessFunction.convChromToXY(bestChrom);
            System.out.println(String.format("Best(x, y) = (%d, %d)", xy[0], xy[1]));
        }       
    }
    
    
    public void display(PApplet app) {
        List<IChromosome> chromosomes = population.getPopulation().getChromosomes();
        for (IChromosome chrom : chromosomes) {
            int[] xy = MountainFitnessFunction.convChromToXY(chrom);
            float z = func.calc(xy[0], xy[1]);
            //drawer.displayGene(xy[0], xy[1], (int)z);
            app.fill(0);
            app.pushMatrix();
            app.translate(xy[0], xy[1], z);
            app.sphere(3);
            app.popMatrix();
        }
    }
    
    
    public void reset() {
        Configuration.reset();
        evolveCount = 0;
        populationSize = 0;
        evolveNum = 0;
    }

}
