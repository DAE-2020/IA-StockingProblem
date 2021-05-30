package ga.geneticoperators;

import algorithms.Individual;
import algorithms.IntVectorIndividual;
import algorithms.Problem;
import utils.Recombinations;

import java.util.ArrayList;
import java.util.List;

public class CompleteSubtourExangeCrossover<I extends IntVectorIndividual, P extends Problem<I>> extends Recombination<I, P> {

    public CompleteSubtourExangeCrossover(double probability) {
        super(probability);
    }

    @Override
    public void recombine(I ind1, I ind2) {
        List<List<Integer>> subtour1 = findCommonSubtours(ind1.getGenome(), ind2.getGenome());
        List<List<Integer>> subtour2 = findCommonSubtours(ind2.getGenome(), ind1.getGenome());
        Individual[] offsprings = generateOffsprings(ind1, ind2, subtour1, subtour2);
    }

    private List<List<Integer>> findCommonSubtours(int[] p1, int[] p2) {
        List<Integer> usedItems = new ArrayList<>();
        List<List<Integer>> subtours = new ArrayList<>();
        for (int i = 0; i < p1.length; i++) {
            for (int j = 0; j < p2.length; j++) {
                if (usedItems.contains(p1[i])) break;
                if(p1[i] == p2[j]){
                    int l = 0;
                    List<Integer> subtour = new ArrayList<>();
                    if (usedItems.contains(p1[i])) break;
                    do {
                        subtour.add(p1[i + l]);
                        l++;
                    } while (i + l < p1.length && j + l < p2.length && p1[i + l] == p2[j + l]);
                    if (subtour.size() > 1) {
                        subtours.add(subtour);
                        usedItems.addAll(subtour);
                        break;
                    }
                    if (usedItems.contains(p1[i])) break;
                    l = 0;
                    subtour.clear();
                    do {
                        subtour.add(p1[i - l]);
                        l++;
                    } while (i - l > -1 && j + l < p2.length && p1[i - l] == p2[j + l]);
                    if (subtour.size() > 1) {
                        subtours.add(subtour);
                        usedItems.addAll(subtour);
                        break;
                    }
                    if (usedItems.contains(p1[i])) break;
                    l = 0;
                    subtour.clear();
                    do {
                        subtour.add(p1[i + l]);
                        l++;
                    } while (i + l < p2.length - 1 && j - l > - 1 && p1[i + l] == p2[j - l]);
                    if (subtour.size() > 1) {
                        subtours.add(subtour);
                        usedItems.addAll(subtour);
                        break;
                    }
                    if (usedItems.contains(p1[i])) break;
                    l = 0;
                    subtour.clear();
                    do {
                        subtour.add(p1[i - l]);
                        l++;
                    } while (i - l > -1 && j - l > - 1 && p2[j - l] == p1[i - l]);
                    if (subtour.size() > 1) {
                        subtours.add(subtour);
                        usedItems.addAll(subtour);
                        break;
                    }
                }
            }
        }
        return subtours;
    }

    private Individual[] generateOffsprings(I p1, I p2, List<List<Integer>> subtours1, List<List<Integer>> subtours2) {
        int[][] offsprings = new int[(int) (Math.pow(2, subtours1.size())-1)][p1.getNumGenes()];
        killWorseOffsprings(new Individual[0]);
        return new Individual[0];
    }

    private void killWorseOffsprings(Individual[] individuals) {

    }


    @Override
    public String toString() {
        return Recombinations.CSEX.getText();
    }
}