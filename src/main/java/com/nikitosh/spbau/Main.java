package com.nikitosh.spbau;

import burlap.behavior.policy.*;
import burlap.behavior.singleagent.*;
import burlap.behavior.singleagent.learning.tdmethods.*;
import burlap.mdp.core.state.*;
import burlap.mdp.singleagent.common.*;
import burlap.mdp.singleagent.environment.*;
import burlap.mdp.singleagent.oo.*;
import burlap.statehashing.*;
import burlap.visualizer.*;
import com.nikitosh.spbau.model.*;
import com.nikitosh.spbau.strategies.*;
import com.nikitosh.spbau.visualization.*;

import java.io.*;
import java.util.*;

public final class Main {
    private static final String MAP_FILE_PATH = "src\\test\\resources\\map.txt";
    private static final double GAMMA = 0.99;
    private static final double LEARNING_RATE = 1;
    private static final int TRIALS_NUMBER = 5;
    private static final int TRIAL_LENGTH = 10000;
    private static final int CHART_WIDTH = 500;
    private static final int CHART_HEIGHT = 250;
    private static final int COLUMNS_NUMBER = 2;
    private static final int MAXIMUM_WINDOW_HEIGHT = 1000;
    private static final long FRAME_DELAY = 2000;

    private Main() {}

    public static void main(String[] args) throws FileNotFoundException {
        World world = World.readFromFile(new File(MAP_FILE_PATH));
        world.getActions().forEach(System.out::println);
        State initialState = world.getState();
        GameDomainGenerator domainGenerator = new GameDomainGenerator(world.getActions(),
                new RandomStrategy(world.getActions()));
        OOSADomain domain = domainGenerator.generateDomain();
        SimulatedEnvironment environment = new SimulatedEnvironment(domain, initialState);
        HashableStateFactory hashingFactory = new ReflectiveHashableStateFactory();

        /*

        Visualizer visualizer = new GameVisualizer(world.getEdges()).getVisualizer();
        VisualActionObserver observer = new VisualActionObserver(visualizer);
        observer.setRepaintOnActionInitiation(true);
        observer.setRepaintStateOnEnvironmentInteraction(false);
        observer.initGUI();
        observer.setFrameDelay(FRAME_DELAY);
        environment.addObservers(observer);
        QLearning agent = new QLearning(domain, GAMMA, hashingFactory, 0, LEARNING_RATE);
        agent.runLearningEpisode(environment);

        */

        /**
         * This part can be used to run many learning episodes on current model.
         */


        List<Episode> episodes = new ArrayList<>();
        QLearning agent = new QLearning(domain, GAMMA, hashingFactory, 0, LEARNING_RATE);
        for (int i = 0; i < TRIAL_LENGTH; i++) {
            System.out.println("Episode #" + i);
            Episode e = agent.runLearningEpisode(environment);
            //episodes.add(e);
            environment.resetEnvironment();
            System.out.println(e.rewardSequence.stream().mapToDouble(Double::doubleValue).sum());
            System.out.println(e.maxTimeStep());
        }
        agent.setLearningPolicy(new GreedyQPolicy(agent));
        /*
        for (int i = 0; i < 100; i++) {
            System.out.println("Episode ###" + i);
            Episode e = agent.runLearningEpisode(environment);
            //episodes.add(e);
            environment.resetEnvironment();
            System.out.println(e.rewardSequence.stream().mapToDouble(Double::doubleValue).sum());
            System.out.println(e.maxTimeStep());
        }
        */

        Visualizer visualizer = new GameVisualizer(world.getEdges()).getVisualizer();
        VisualActionObserver observer = new VisualActionObserver(visualizer);
        observer.setRepaintOnActionInitiation(true);
        observer.setRepaintStateOnEnvironmentInteraction(false);
        observer.initGUI();
        observer.setFrameDelay(FRAME_DELAY);
        environment.addObservers(observer);
        agent.runLearningEpisode(environment);

        /*
        agent.setLearningPolicy(new GreedyQPolicy(agent));
        for (int i = 0; i < 1000; i++) {
            System.out.println("Episode ###" + i);
            Episode e = agent.runLearningEpisode(environment);
            environment.resetEnvironment();
            System.out.println(e.rewardSequence.stream().mapToDouble(Double::doubleValue).sum());
            System.out.println(e.maxTimeStep());
        }
        */
        
        //new EpisodeSequenceVisualizer(visualizer, domain, episodes);

        /**
         * This part can be used for making learning curves on plot.
         */

        /*

        LearningAgentFactory qLearningFactory = new LearningAgentFactory() {
            @Override
            public String getAgentName() {
                return "Q-Learning";
            }

            @Override
            public LearningAgent generateAgent() {
                QLearning agent = new QLearning(domain, GAMMA, hashingFactory, 0, LEARNING_RATE);

                for (int i = 0; i < TRIAL_LENGTH; i++) {
                    agent.runLearningEpisode(environment);
                    environment.resetEnvironment();
                    if (i % 10 == 0) {
                        System.out.println(i);
                    }
                }
                agent.setLearningPolicy(new GreedyQPolicy(agent));

                //agent.setLearningPolicy(new EpsilonGreedy(agent, 0.1D));
                return agent;
            }
        };

        LearningAlgorithmExperimenter exp = new LearningAlgorithmExperimenter(environment, TRIALS_NUMBER, TRIAL_LENGTH,
                qLearningFactory);

        exp.setUpPlottingConfiguration(CHART_WIDTH, CHART_HEIGHT, COLUMNS_NUMBER, MAXIMUM_WINDOW_HEIGHT,
                TrialMode.MOST_RECENT_AND_AVERAGE,
                PerformanceMetric.CUMULATIVE_STEPS_PER_EPISODE,
                PerformanceMetric.CUMULATIVE_REWARD_PER_EPISODE);

        exp.startExperiment();
        exp.writeStepAndEpisodeDataToCSV("expData");

        */

        /**
         * This part can be used for testing BFS.
         */

        /*
        VisualExplorer explorer = new VisualExplorer(domain, environment, visualizer);
        explorer.initGUI();
        DeterministicPlanner planner = new BFS(domain, new TFGoalCondition(new GameTerminalFunction()), hashingFactory);
        Policy p = planner.planFromState(initialState);
        PolicyUtils.rollout(p, initialState, domain.getModel()).write("trash/" + "bfs");
        */
    }
}
