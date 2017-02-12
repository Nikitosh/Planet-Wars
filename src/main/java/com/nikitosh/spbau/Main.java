package com.nikitosh.spbau;

import burlap.behavior.singleagent.learning.tdmethods.*;
import burlap.mdp.core.state.*;
import burlap.mdp.singleagent.common.*;
import burlap.mdp.singleagent.environment.*;
import burlap.mdp.singleagent.oo.*;
import burlap.statehashing.*;
import burlap.visualizer.*;
import com.nikitosh.spbau.model.*;
import com.nikitosh.spbau.visualization.*;

import java.io.*;

public final class Main {
    private static final String MAP_FILE_PATH = "src\\test\\resources\\map.txt";
    private static final double GAMMA = 0.99;
    private static final double LEARNING_RATE = 1;
    private static final int TRIALS_NUMBER = 5;
    private static final int TRIAL_LENGTH = 2000;
    private static final int CHART_WIDTH = 500;
    private static final int CHART_HEIGHT = 250;
    private static final int COLUMNS_NUMBER = 2;
    private static final int MAXIMUM_WINDOW_HEIGHT = 1000;
    private static final long FRAME_DELAY = 1000;

    private Main() {}

    public static void main(String[] args) throws FileNotFoundException {
        World world = World.readFromFile(new File(MAP_FILE_PATH));
        State initialState = world.getState();
        GameDomainGenerator domainGenerator = new GameDomainGenerator(world.getActions());
        OOSADomain domain = domainGenerator.generateDomain();
        SimulatedEnvironment environment = new SimulatedEnvironment(domain, initialState);
        HashableStateFactory hashingFactory = new ReflectiveHashableStateFactory();


        Visualizer visualizer = new GameVisualizer(world.getEdges()).getVisualizer();
        VisualActionObserver observer = new VisualActionObserver(visualizer);
        observer.setRepaintOnActionInitiation(true);
        observer.setRepaintStateOnEnvironmentInteraction(false);
        observer.initGUI();
        observer.setFrameDelay(FRAME_DELAY);
        environment.addObservers(observer);
        QLearning agent = new QLearning(domain, GAMMA, hashingFactory, 0, LEARNING_RATE);
        agent.runLearningEpisode(environment);

        /**
         * This part can be used to run many learning episodes on current model.
         */

        /*
        List<Episode> episodes = new ArrayList<>();
        QLearning agent = new QLearning(domain, GAMMA, hashingFactory, 0, LEARNING_RATE);
        for (int i = 0; i < TRIAL_LENGTH; i++) {
            Episode e = agent.runLearningEpisode(environment);
            e.write("results/" + i);
            episodes.add(e);
            environment.resetEnvironment();
            //e.rewardSequence.stream().forEach(System.out::println);
            //System.out.println();
            //System.out.println(e.rewardSequence.stream().mapToDouble(Double::doubleValue).sum());
            //System.out.println(e.maxTimeStep());
        }
        new EpisodeSequenceVisualizer(visualizer, domain, episodes);
        */

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
                return new QLearning(domain, GAMMA, hashingFactory, 0, LEARNING_RATE);
            }
        };

        LearningAlgorithmExperimenter exp = new LearningAlgorithmExperimenter(environment, TRIALS_NUMBER, TRIAL_LENGTH,
                qLearningFactory);

        exp.setUpPlottingConfiguration(CHART_WIDTH, CHART_HEIGHT, COLUMNS_NUMBER, MAXIMUM_WINDOW_HEIGHT,
                TrialMode.MOST_RECENT_AND_AVERAGE,
                PerformanceMetric.CUMULATIVE_STEPS_PER_EPISODE,
                PerformanceMetric.AVERAGE_EPISODE_REWARD);

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
