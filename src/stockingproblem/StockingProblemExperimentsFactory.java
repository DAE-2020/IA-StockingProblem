package stockingproblem;

import experiments.*;
import algorithms.AlgorithmListener;
import ga.GeneticAlgorithm;
import ga.geneticoperators.*;
import ga.geneticoperators.mutation.Mutation;
import ga.geneticoperators.mutation.Mutation3;
import ga.geneticoperators.mutation.MutationInversion;
import ga.geneticoperators.mutation.MutationInsert;
import ga.selectionmethods.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import statistics.StatisticBestAverage;
import statistics.StatisticBestInRun;
import utils.Mutations;
import utils.Recombinations;

public class StockingProblemExperimentsFactory extends ExperimentsFactory {

    private int populationSize;
    private int maxGenerations;
    private SelectionMethod<StockingProblemIndividual, StockingProblem> selection;
    private Recombination<StockingProblemIndividual, StockingProblem> recombination;
    private Mutation<StockingProblemIndividual, StockingProblem> mutation;
    private StockingProblem problem;
    private Experiment<StockingProblemExperimentsFactory, StockingProblem> experiment;

    public StockingProblemExperimentsFactory(File configFile) throws IOException {
        super(configFile);
    }

    @Override
    public Experiment buildExperiment() throws IOException {
        numRuns = Integer.parseInt(getParameterValue("Runs"));
        populationSize = Integer.parseInt(getParameterValue("Population_size"));
        maxGenerations = Integer.parseInt(getParameterValue("Max_generations"));

        //SELECTION
        switch (getParameterValue("Selection")) {
            case "tournament" -> {
                int tournamentSize = Integer.parseInt(getParameterValue("Tournament_size"));
                selection = new Tournament<>(populationSize, tournamentSize);
            }
            case "roulette_wheel" -> selection = new RouletteWheel<>(populationSize);
        }

        //RECOMBINATION
        double recombinationProbability = Double.parseDouble(getParameterValue("Recombination_probability"));

        switch (Recombinations.getByText(getParameterValue("Recombination"))) {
            case PMX -> recombination = new RecombinationPartialMapped<>(recombinationProbability);
            case RECOMBINATION_2 -> recombination = new Recombination2<>(recombinationProbability);
            case RECOMBINATION_3 -> recombination = new Recombination3<>(recombinationProbability);
        }

        //MUTATION
        double mutationProbability = Double.parseDouble(getParameterValue("Mutation probability"));

        switch (Mutations.getByText(getParameterValue("Mutation"))) {
            case INSERT -> mutation = new MutationInsert<>(mutationProbability);
            case INVERSION -> mutation = new MutationInversion<>(mutationProbability);
            case MUTATION_3 -> mutation = new Mutation3<>(mutationProbability);
        }

        //PROBLEM
        problem = StockingProblem.buildWarehouse(new File(getParameterValue("Problem_file")));


        String experimentTextualRepresentation = buildExperimentTextualRepresentation();
        String experimentHeader = buildExperimentHeader();
        String experimentConfigurationValues = buildExperimentValues();

        experiment = new Experiment<>(
                this,
                numRuns,
                problem,
                experimentTextualRepresentation,
                experimentHeader,
                experimentConfigurationValues);

        statistics = new ArrayList<>();
        for (String statisticName : statisticsNames) {
            ExperimentListener statistic = buildStatistic(statisticName, experimentHeader);
            statistics.add(statistic);
            experiment.addExperimentListener(statistic);
        }

        return experiment;
    }

    @Override
    public GeneticAlgorithm generateGAInstance(int seed) {
        GeneticAlgorithm<StockingProblemIndividual, StockingProblem> ga;

        ga = new GeneticAlgorithm<>(
                populationSize,
                maxGenerations,
                selection,
                recombination,
                mutation,
                new Random(seed));

        for (ExperimentListener statistic : statistics) {
            ga.addListener((AlgorithmListener) statistic);
        }

        return ga;
    }

    private ExperimentListener buildStatistic(
            String statisticName,
            String experimentHeader) {
        if (statisticName.equals("BestIndividual")) {
            return new StatisticBestInRun(experimentHeader);
        }
        if (statisticName.equals("BestAverage")) {
            return new StatisticBestAverage(numRuns, experimentHeader);
        }
        return null;
    }

    private String buildExperimentTextualRepresentation() {
        return "Population size:" + populationSize + "\r\n" +
                "Max generations:" + maxGenerations + "\r\n" +
                "Selection:" + selection + "\r\n" +
                "Recombination:" + recombination + "\r\n" +
                "Recombination prob.: " + recombination.getProbability() + "\r\n" +
                "Mutation:" + mutation + "\r\n" +
                "Mutation prob.: " + mutation.getProbability();
    }

    private String buildExperimentHeader() {
        return "Population size:" + "\t" +
                "Max generations:" + "\t" +
                "Selection:" + "\t" +
                "Recombination:" + "\t" +
                "Recombination prob.:" + "\t" +
                "Mutation:" + "\t" +
                "Mutation prob.:" + "\t";
    }

    private String buildExperimentValues() {
        return populationSize + "\t" +
                maxGenerations + "\t" +
                selection + "\t" +
                recombination + "\t" +
                recombination.getProbability() + "\t" +
                mutation + "\t" +
                mutation.getProbability() + "\t";
    }
}
