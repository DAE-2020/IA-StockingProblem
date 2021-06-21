package algorithms;

import java.util.Arrays;
import java.util.List;

public abstract class Individual<P extends Problem, I extends Individual> implements Comparable<I>{

    protected double fitness;
    protected P problem;

    public Individual(P problem) {
        this.problem = problem;
    }

    public Individual(Individual<P, I> original) {
        this.problem = original.problem;
        this.fitness = original.fitness;
    }

    public abstract double computeFitness();

    public abstract int getNumGenes();

    public abstract Object getGene(int index);

    public abstract void swapGenes(I other, int index);

    public abstract void invertGenes(int start, int end);

    public abstract void switchGenes(int firstIndex, int secondIndex);

    public double getFitness() {
        return fitness;
    }

    public abstract Object[] getGenome();

    public int genomeIndexOf(Object val) {
        return Arrays.asList(this.getGenome()).indexOf(val);
    }

    @Override
    public abstract I clone();
}
