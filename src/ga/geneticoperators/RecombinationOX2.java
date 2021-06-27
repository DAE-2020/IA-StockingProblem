package ga.geneticoperators;

import algorithms.IntVectorIndividual;
import algorithms.Problem;
import ga.GeneticAlgorithm;
import utils.Recombinations;

import java.util.Arrays;

public class RecombinationOX2<I extends IntVectorIndividual, P extends Problem<I>> extends Recombination<I, P> {

    private int[] child1, child2, segment1, segment2;

    private int cut1, cut2, cut3;

    public RecombinationOX2(double probability) {
        super(probability);
    }

    @Override
    public void recombine(I ind1, I ind2) {
        child1 = new int[ind1.getNumGenes()];
        child2 = new int[ind2.getNumGenes()];
        cut1 = GeneticAlgorithm.random.nextInt(ind1.getNumGenes());
        do {
            cut2 = GeneticAlgorithm.random.nextInt(ind1.getNumGenes());
        } while (cut1 == cut2);
        do {
            cut3 = GeneticAlgorithm.random.nextInt(ind1.getNumGenes());
        } while (cut1 == cut3 || cut2 == cut3);

        orderCuts();

        save_values(cut1, cut2, cut3, ind1, ind2);



        crossOver(child1, ind1);
        crossOver(child2, ind2);

        for (int i = 0; i < ind1.getNumGenes(); i++) {
            ind1.setGene(i, child1[i]);
            ind2.setGene(i, child2[i]);
        }

    }

    private void orderCuts(){
        do{
            if(cut1 > cut2){
                int aux = cut2;
                cut2 = cut1;
                cut1 = aux;
            }

            if(cut2 > cut3){
                int aux = cut3;
                cut3 = cut2;
                cut2 = aux;
            }
        } while(cut1 > cut3 || cut1 > cut2);
    }

    private boolean check_for_existence(int[] offspring, int indexOfElement) {
        for (int index = 0; index < offspring.length; index++) {
            if ((offspring[index] == offspring[indexOfElement]) &&
                    (indexOfElement != index)) {
                return true;
            }
        }
        return false;
    }

    private void save_values(int cutPoint1, int cutPoint2, int cutPoint3, I ind1, I ind2) {
        segment1 =  new int[3];
        segment1[0] = ind1.getGene(cutPoint1);
        segment1[1] = ind1.getGene(cutPoint2);
        segment1[2] = ind1.getGene(cutPoint3);

        segment2 =  new int[3];
        segment2[0] = ind2.getGene(cutPoint1);
        segment2[1] = ind2.getGene(cutPoint2);
        segment2[2] = ind2.getGene(cutPoint3);
    }

    private void insert_Segments(int[] offspring, int[] segment) {
        int[] offspring_copy = offspring;

        for (int index = 0; index < offspring.length; index++) {
            if(index == cut1){
                offspring_copy = replace_cut_with_validation(offspring, cut1, segment[0]);
            } else if(index == cut2){
                offspring_copy = replace_cut_with_validation(offspring, cut2, segment[1]);
            } else if(index == cut3){
                offspring_copy = replace_cut_with_validation(offspring, cut3, segment[2]);
            }
        }
        offspring = offspring_copy;
    }

    private int[] replace_cut_with_validation(int[] offspring, int cutPoint, int value) {
        for (int index = 0; index < offspring.length; index++) {
            if (offspring[index] == value && index != cutPoint) {
                offspring[index] = offspring[cutPoint];
                offspring[cutPoint] = value;
                break;
            }
        }
        return offspring;
    }

    // offspring2 gets segment 1, offspring1 gets segment2 //
    public void crossOver(int[] offspring, I ind) {
        for (int index = 0; index < offspring.length; index++) {
            offspring[index] = ind.getGene(index);
        }

        if (offspring == child1) {
            int[] segment = segment2;
            insert_Segments(offspring, segment);
        } else if (offspring == child2) {
            int[] segment = segment1;
            insert_Segments(offspring, segment);
        }
    }

    @Override
    public String toString(){
        return Recombinations.RECOMBINATION_OX2.getText();
    }    
}