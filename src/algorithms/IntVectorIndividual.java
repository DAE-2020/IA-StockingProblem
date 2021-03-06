package algorithms;

import java.util.ArrayList;

public abstract class IntVectorIndividual<P extends Problem, I extends IntVectorIndividual> extends Individual<P, I> {
    //TODO this class might require the definition of additional methods and/or attributes

    protected Integer[] genome;

    public IntVectorIndividual(P problem, int size) {
        super(problem);
        genome = new Integer[size];
      }

    public IntVectorIndividual(IntVectorIndividual<P, I> original) {
        super(original);
        this.genome = new Integer[original.genome.length];
        System.arraycopy(original.genome, 0, genome, 0, genome.length);
    }

    @Override
    public int getNumGenes() {
        return genome.length;
    }

    public int getIndexof(int value){
        for (int i = 0; i < genome.length; i++) {
            if (genome[i] == value)
                return i;
        }
        return -1;
    }

    public ArrayList<Integer> subList(int cut1, int cut2) {
        ArrayList<Integer> values = new ArrayList<>();
        for (int i = cut1; i < cut2; i++) {
            values.add(genome[i]);
        }
        return values;
    }

    public Integer getGene(int index) {
        return genome[index];
    }

    public void setGene(int index, int newValue) {
        genome[index] = newValue;
    }

    @Override
    public void swapGenes(IntVectorIndividual other, int index) {
        int aux = genome[index];
        genome[index] = other.genome[index];
        other.genome[index] = aux;
    }

    @Override
    public void invertGenes(int start, int end) {
        int aux;
        if (end < start) {
            aux = start;
            start = end;
            end = aux;
        }
        int length = (end - start) + 1;
        int middle = Math.floorDiv(length, 2);
        for (int i = 0; i < middle; i++) {
            aux = genome[start + i];
            genome[start + i] = genome[end - i];
            genome[end - i] = aux;
        }
    }


    public void switchGenes(int firstIndex, int secondIndex) {
        int aux = genome[firstIndex];
        genome[firstIndex] = genome[secondIndex];
        genome[secondIndex] = aux;
    }

    public Integer[] getGenome() {
        return genome;
    }
}
