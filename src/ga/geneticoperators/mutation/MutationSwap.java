package ga.geneticoperators.mutation;

import algorithms.IntVectorIndividual;
import algorithms.Problem;
import ga.GeneticAlgorithm;
import utils.Mutations;

public class MutationSwap<I extends IntVectorIndividual, P extends Problem<I>> extends Mutation<I, P> {

    public MutationSwap(double probability) {
        super(probability);
    }

    @Override
    public void mutate(I ind) {
        int firstIndex = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
        int secondIndex = GeneticAlgorithm.random.nextInt(ind.getNumGenes());

        while (secondIndex == firstIndex) {
            secondIndex = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
        }

        ind.switchGenes(firstIndex, secondIndex);
    }


    @Override
    public String toString(){
        return Mutations.SWAP.getText();
    }
}