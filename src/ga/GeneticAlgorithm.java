package ga;


import algorithms.*;
import ga.geneticoperators.mutation.Mutation;
import ga.geneticoperators.Recombination;
import ga.selectionmethods.SelectionMethod;

import java.util.Random;

public class GeneticAlgorithm<I extends Individual, P extends Problem<I>> extends Algorithm<I, P> {

    private final int populationSize;
    private Population<I, P> population;
    private final SelectionMethod<I, P> selection;
    private final Recombination<I, P> recombination;
    private final Mutation<I, P> mutation;

    public GeneticAlgorithm(
            int populationSize,
            int maxGenerations,
            SelectionMethod<I, P> selection,
            Recombination<I, P> recombination,
            Mutation<I, P> mutation,
            Random rand) {
        super(maxGenerations, rand);

        this.populationSize = populationSize;
        this.selection = selection;
        this.recombination = recombination;
        this.mutation = mutation;
    }

    public I run(P problem) {
        t = 0;
        population = new Population<>(populationSize, problem);
        globalBest = population.evaluate();
        //noinspection unchecked
        fireIterationEnded(new AlgorithmEvent(this));

        while (t < maxIterations && !stopped) {
            Population<I, P> populationAux = selection.run(population);
            recombination.run(populationAux);
            mutation.run(populationAux);
            population = populationAux;
            I bestInGen = population.evaluate();
            computeBestInRun(bestInGen);
            t++;
            //noinspection unchecked
            fireIterationEnded(new AlgorithmEvent(this));
        }
        //noinspection unchecked
        fireRunEnded(new AlgorithmEvent(this));
        return globalBest;
    }

    private void computeBestInRun(I bestInGen) {
        //noinspection unchecked
        if (bestInGen.compareTo(globalBest) > 0) {
            //noinspection unchecked
            globalBest = (I) bestInGen.clone();
        }
    }

    public double getAverageFitness() {
        return population.getAverageFitness();
    }


    @Override
    public String toString() {
        return "Population size:" + populationSize + "\n" +
                "Max generations:" + maxIterations + "\n" +
                "Selection:" + selection + "\n" +
                "Recombination:" + recombination + "\n" +
                "Mutation:" + mutation + "\n";
    }

}
