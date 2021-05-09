package ga.geneticoperators.mutation;

import algorithms.IntVectorIndividual;
import algorithms.Problem;
import utils.Mutations;

public class MutationSwap<I extends IntVectorIndividual, P extends Problem<I>> extends Mutation<I, P> {

    public MutationSwap(double probability) {
        super(probability);
    }

    @Override
    public void mutate(I ind) {
        //TODO
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public String toString(){
        return Mutations.SWAP.getText();
    }
}