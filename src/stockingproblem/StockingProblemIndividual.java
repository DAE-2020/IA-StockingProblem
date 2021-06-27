package stockingproblem;

import algorithms.IntVectorIndividual;
import ga.GeneticAlgorithm;

import java.util.ArrayList;

public class StockingProblemIndividual extends IntVectorIndividual<StockingProblem, StockingProblemIndividual> {

    private int materialMaxWidth;

    public StockingProblemIndividual(StockingProblem problem, int size) {
        super(problem, size);

        this.materialMaxWidth = 0;
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

        this.materialMaxWidth = 0;
    }

    @Override
    public double computeFitness() {
        int worstCaseColumns = 0;

        for (Item item : this.problem.getItems()) {
            worstCaseColumns += item.getColumns();
        }

        int[][] matrix = new int[this.problem.getMaterialHeight()][worstCaseColumns];

        Integer[] genome = this.getGenome();

        for (Integer gene : genome) {
            for (Item item : this.problem.getItems()) {
                if (item.getId() == gene) {
                    search:
                    for (int j = 0; j < worstCaseColumns; j++) {
                        for (int k = 0; k < this.problem.getMaterialHeight(); k++) {
                            if (checkValidPlacement(item, matrix, k, j)) {
                                for (int l = 0; l < item.getLines(); l++) {
                                    for (int m = 0; m < item.getColumns(); m++) {
                                        matrix[k + l][j + m] = item.getMatrix()[l][m];
                                    }
                                }
                                if (j + item.getColumns() > this.materialMaxWidth)
                                    this.setMaterialMaxWidth(j + item.getColumns());
                                break search;
                            }
                        }
                    }
                }
            }
        }

        fitness = this.problem.getMaterialHeight() * this.materialMaxWidth;

        for (int i = 0; i < this.problem.getMaterialHeight(); i++) {
            for (int j = 0; j < this.materialMaxWidth; j++) {
                if (matrix[i][j] != 0) {
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

    public int getMaterialMaxWidth() {
        return materialMaxWidth;
    }

    public void setMaterialMaxWidth(int materialMaxWidth) {
        this.materialMaxWidth = materialMaxWidth;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        for (int gene :
                getGenome()) {
            sb.append(gene);
            sb.append(" ");
        }
        sb.append("]\nfitness: ");
        sb.append(fitness);
        sb.append("\n");
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