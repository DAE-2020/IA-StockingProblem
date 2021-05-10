package ga.geneticoperators.mutation;

import algorithms.IntVectorIndividual;
import algorithms.Problem;
import utils.Mutations;

public class Mutation3<I extends IntVectorIndividual, P extends Problem<I>> extends Mutation<I, P> {

    public Mutation3(double probability) {
        super(probability);
    }

    @Override
    public void mutate(I ind) {
        //TODO
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public String toString(){
        return Mutations.MUTATION_3.getText();
    }
}