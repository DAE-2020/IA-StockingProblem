package ga.geneticoperators.mutation;

import algorithms.IntVectorIndividual;
import algorithms.Problem;
import ga.GeneticAlgorithm;
import utils.Mutations;

public class MutationInversion<I extends IntVectorIndividual, P extends Problem<I>> extends Mutation<I, P> {
    private int cut1;
    private int cut2 = -1;

    public MutationInversion(double probability) {
        super(probability);
    }

    @Override
    public void mutate(I ind) {
        computeCut(ind);
        ind.invertGenes(cut1, cut2);
    }

    private void computeCut(I ind) {
        cut1 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
        while (cut2 == -1 || cut2 == cut1) {
            cut2 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
        }
        if (cut1 > cut2) {
            int aux = cut1;
            cut1 = cut2;
            cut2 = aux;
        }
    }

    @Override
    public String toString(){
        return Mutations.INVERSION.getText();
    }
}