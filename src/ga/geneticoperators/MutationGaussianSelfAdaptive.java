package ga.geneticoperators;

import algorithms.IntVectorIndividual;
import algorithms.Problem;
import utils.Mutations;

public class MutationGaussianSelfAdaptive<I extends IntVectorIndividual, P extends Problem<I>> extends Mutation<I, P> {

    public MutationGaussianSelfAdaptive(double probability) {
        super(probability);
    }

    @Override
    public void mutate(I ind) {
        //TODO
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public String toString(){
        return Mutations.GAUSSIAN_SELF_ADAPTIVE.getText();
    }
}