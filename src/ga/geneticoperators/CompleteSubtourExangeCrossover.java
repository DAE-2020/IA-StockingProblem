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
        if (subtour1.size() > 0 && subtour2.size() > 0) {
            List<I> offsprings = generateOffsprings(ind1, ind2, subtour1, subtour2);
            for (int i = 0; i < ind1.getNumGenes(); i++) {
                ind1.setGene(i, offsprings.get(0).getGene(i));
                ind2.setGene(i, offsprings.get(1).getGene(i));
            }
        }
    }

    private List<List<Integer>> findCommonSubtours(Integer[] p1, Integer[] p2) {
        List<Integer> usedItems = new ArrayList<>();
        List<List<Integer>> subtours = new ArrayList<>();
        for (int i = 0; i < p1.length; i++) {
            for (int j = 0; j < p2.length; j++) {
                if (usedItems.contains(p1[i])) break;
                if(p1[i].equals(p2[j])){
                    int l = 0;
                    List<Integer> subtour = new ArrayList<>();
                    if (usedItems.contains(p1[i])) break;
                    do {
                        subtour.add(p1[i + l]);
                        l++;
                    } while (i + l < p1.length && j + l < p2.length && p1[i + l].equals(p2[j + l]));
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
                    } while (i - l > -1 && j + l < p2.length && p1[i - l].equals(p2[j + l]));
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
                    } while (i + l < p2.length - 1 && j - l > - 1 && p1[i + l].equals(p2[j - l]));
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
                    } while (i - l > -1 && j - l > - 1 && p2[j - l].equals(p1[i - l]));
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

    private List<I> generateOffsprings(I p1, I p2, List<List<Integer>> subtours1, List<List<Integer>> subtours2) {
        List<I> offsprings = new ArrayList<>();
        exchangeSubtours(offsprings, p1, subtours1);
        exchangeSubtours(offsprings, p2, subtours2);
        return killWorseOffsprings(offsprings, 2);
    }

    private void exchangeSubtours(List<I> offsprings, I parent, List<List<Integer>> subtours) {
        int numOffsprings = (int) Math.pow(2, subtours.size()+1)-2;
        for (int offspring = 0; offspring < numOffsprings / 2; offspring++) {
            if (offspring != 0) {
                parent = offsprings.get((offsprings.size() - 1));
            }
            offsprings.add(parent);
            for (int subtour = 0; subtour < subtours.size(); subtour++) {
                if (offspring % Math.pow(2, subtour) == 0) {
                    int startIndex = offsprings.get(offspring)
                            .genomeIndexOf(subtours.get(subtour).get(0));
                    int endIndex = offsprings.get(offsprings.size() - 1)
                            .genomeIndexOf(subtours.get(subtour).get(subtours.get(subtour).size() - 1));
                    if (startIndex != -1 && endIndex != -1) {
                        offsprings.get(offsprings.size() - 1).invertGenes(startIndex, endIndex);
                    }
                }
            }
        }
    }

    private List<I> killWorseOffsprings(List<I> offsprings, int numSurvivors) {
        List<I> survivors = new ArrayList<>();
        for (int i = 0; i < numSurvivors; i++) {
            I best = offsprings.get(0);
            for (I individual : offsprings) {
                individual.computeFitness();
                if(individual.compareTo(best) > 0) {
                    best = individual;
                }
            }
            survivors.add(best);
            offsprings.remove(best);
        }
        return survivors;
    }

    @Override
    public String toString() {
        return Recombinations.CSEX.getText();
    }
}
