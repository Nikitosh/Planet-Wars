package com.nikitosh.spbau.experiments;

import burlap.behavior.singleagent.auxiliary.performance.*;
import burlap.behavior.singleagent.learning.*;
import burlap.mdp.core.state.*;
import burlap.mdp.singleagent.environment.*;
import burlap.mdp.singleagent.oo.*;
import burlap.statehashing.*;
import com.nikitosh.spbau.algorithms.*;
import com.nikitosh.spbau.model.*;
import com.nikitosh.spbau.modifications.*;
import com.nikitosh.spbau.strategies.*;

import java.io.*;

public class PlotsDrawer {
    private static final String MAP_FILE_PATH = "src\\test\\resources\\maps\\map4.txt";
    private static final double GAMMA = 0.99;
    private static final double LEARNING_RATE = 0.3;
    private static final double EPSILON = 0.2;
    private static final int TRIALS_NUMBER = 5;
    private static final int TRIAL_LENGTH = 10000;
    private static final int CHART_WIDTH = 500;
    private static final int CHART_HEIGHT = 250;
    private static final int COLUMNS_NUMBER = 2;
    private static final int MAXIMUM_WINDOW_HEIGHT = 1000;

    public void run() throws FileNotFoundException {
        World world = World.readFromFile(new File(MAP_FILE_PATH));
        State initialState = world.getState();
        GameDomainGenerator domainGenerator = new GameDomainGenerator(world.getAgentActions(),
                new RandomStrategy(world.getAllActions()));
        OOSADomain domain = domainGenerator.generateDomain();
        SimulatedEnvironment environment = new SimulatedEnvironment(domain, initialState);
        HashableStateFactory hashingFactory = new ReflectiveHashableStateFactory();

        LearningAgentFactory qLearningFactory = new LearningAgentFactory() {
            @Override
            public String getAgentName() {
                return "Q-Learning";
            }

            @Override
            public LearningAgent generateAgent() {
                return new EpsilonGreedyDecayQLearning(domain, GAMMA, hashingFactory, 0, LEARNING_RATE,
                        TRIAL_LENGTH, EPSILON);
            }
        };

        LearningAlgorithmExperimenter exp = new UpgradedLearningAlgorithmExperimenter(environment, TRIALS_NUMBER,
                TRIAL_LENGTH, qLearningFactory);


        exp.setUpPlottingConfiguration(CHART_WIDTH, CHART_HEIGHT, COLUMNS_NUMBER, MAXIMUM_WINDOW_HEIGHT,
                TrialMode.MOST_RECENT_AND_AVERAGE,
                PerformanceMetric.AVERAGE_EPISODE_REWARD,
                PerformanceMetric.CUMULATIVE_REWARD_PER_EPISODE);

        exp.startExperiment();
        exp.writeStepAndEpisodeDataToCSV("expData");

    }
}
