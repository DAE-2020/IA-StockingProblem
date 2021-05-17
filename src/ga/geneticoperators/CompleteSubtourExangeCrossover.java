package ga.geneticoperators;

import algorithms.IntVectorIndividual;
import algorithms.Problem;
import utils.Recombinations;

public class CompleteSubtourExangeCrossover<I extends IntVectorIndividual, P extends Problem<I>> extends Recombination<I, P> {

    public CompleteSubtourExangeCrossover(double probability) {
        super(probability);
    }

    @Override
    public void recombine(I ind1, I ind2) {
        //TODO
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public String toString() {
        return Recombinations.CSEX.getText();
    }
}