package stockingproblem;

import algorithms.IntVectorIndividual;
import ga.GeneticAlgorithm;

import java.util.ArrayList;

public class StockingProblemIndividual extends IntVectorIndividual<StockingProblem, StockingProblemIndividual> {
    //TODO this class might require the definition of additional methods and/or attributes

    public StockingProblemIndividual(StockingProblem problem, int size) {
        super(problem, size);
        ArrayList<Integer> aux = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            aux.add(i);
        }
        for (int i = 0; i < size; i++) {
            int rand = GeneticAlgorithm.random.nextInt(aux.size());
            genome[i] = aux.get(rand);
            aux.remove(rand);
        }
    }

    public StockingProblemIndividual(StockingProblemIndividual original) {
        super(original);
        //TODO
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public double computeFitness() {
        int worstCaseColumns = 0;

        for (Item item : this.problem.getItems()) {
            worstCaseColumns += item.getColumns();
        }

        int coordCount = this.problem.getMaterialHeight() * worstCaseColumns;
        int[][] matrix = new int[this.problem.getMaterialHeight()][worstCaseColumns];

        for (int i = 0; i < this.getGenome().length; i++) {
            for (Item item : this.problem.getItems()) {
                if (item.getId() == i) {
                    for (int j = 0; j < item.getLines(); j++) {
                        for (int k = 0; k < item.getColumns(); k++) {
                            if (checkValidPlacement(item, matrix, j, k)) {
                                matrix[j][k] = item.getMatrix()[j][k];
                                this.problem.setMaterialMaxWidth(j + k);
                            }
                        }
                    }
                }
            }
        }

        double fitness = coordCount;

        for (int i = 0; i < this.problem.getMaterialHeight(); i++) {
            for (int j = 0; j < this.problem.getMaterialMaxWidth(); j++) {
                if (matrix[i][j] == 0) {
                    fitness--;
                }
            }
        }

        return fitness;
    }

    private boolean checkValidPlacement(Item item, int[][] material, int lineIndex, int columnIndex) {
        int[][] itemMatrix = item.getMatrix();
        for (int i = 0; i < itemMatrix.length; i++) {
            for (int j = 0; j < itemMatrix[i].length; j++) {
                if (itemMatrix[i][j] != 0) {
                    if ((lineIndex + i) >= material.length
                            || (columnIndex + j) >= material[0].length
                            || material[lineIndex + i][columnIndex + j] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("fitness: ");
        sb.append(fitness);
        //TODO
        return sb.toString();
    }

    /**
     * @param i
     * @return 1 if this object is BETTER than i, -1 if it is WORST than I and
     * 0, otherwise.
     */
    @Override
    public int compareTo(StockingProblemIndividual i) {
        return (this.fitness == i.getFitness()) ? 0 : (this.fitness < i.getFitness()) ? 1 : -1;
    }

    @Override
    public StockingProblemIndividual clone() {
        return new StockingProblemIndividual(this);

    }
}