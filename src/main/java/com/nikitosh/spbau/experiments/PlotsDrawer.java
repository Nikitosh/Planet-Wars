package com.nikitosh.spbau.experiments;

import burlap.behavior.singleagent.auxiliary.performance.*;
import burlap.behavior.singleagent.learning.*;
import burlap.behavior.singleagent.learning.tdmethods.*;
import burlap.mdp.core.state.*;
import burlap.mdp.singleagent.environment.*;
import burlap.mdp.singleagent.oo.*;
import burlap.statehashing.*;
import com.nikitosh.spbau.model.*;
import com.nikitosh.spbau.strategies.*;

import java.io.*;

public class PlotsDrawer {
    private static final String MAP_FILE_PATH = "src\\test\\resources\\maps\\map3.txt";
    private static final double GAMMA = 0.99;
    private static final double LEARNING_RATE = 0.3;
    private static final int TRIALS_NUMBER = 2;
    private static final int TRIAL_LENGTH = 2000;
    private static final int CHART_WIDTH = 500;
    private static final int CHART_HEIGHT = 250;
    private static final int COLUMNS_NUMBER = 2;
    private static final int MAXIMUM_WINDOW_HEIGHT = 1000;

    public void run() throws FileNotFoundException {
        World world = World.readFromFile(new File(MAP_FILE_PATH));
        State initialState = world.getState();
        GameDomainGenerator domainGenerator = new GameDomainGenerator(world.getActions(),
                new RandomStrategy(world.getActions()));
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
                QLearning agent = new QLearning(domain, GAMMA, hashingFactory, 0, LEARNING_RATE);
                /*
                agent.setLearningPolicy(new EpsilonGreedy(agent, 0.1) {
                    @Override
                    public Action action(State s) {
                        setEpsilon(Math.max(0, getEpsilon() - 0.000001));
                        return super.action(s);
                    }
                });
                */
                return agent;
            }
        };

        LearningAlgorithmExperimenter exp = new LearningAlgorithmExperimenter(environment, TRIALS_NUMBER, TRIAL_LENGTH,
                qLearningFactory);

        exp.setUpPlottingConfiguration(CHART_WIDTH, CHART_HEIGHT, COLUMNS_NUMBER, MAXIMUM_WINDOW_HEIGHT,
                TrialMode.MOST_RECENT_AND_AVERAGE,
                PerformanceMetric.AVERAGE_EPISODE_REWARD,
                PerformanceMetric.CUMULATIVE_REWARD_PER_EPISODE);

        exp.startExperiment();
        exp.writeStepAndEpisodeDataToCSV("expData");

    }
}
