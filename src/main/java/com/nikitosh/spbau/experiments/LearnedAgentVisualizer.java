package com.nikitosh.spbau.experiments;

import burlap.behavior.singleagent.*;
import burlap.behavior.singleagent.learning.tdmethods.*;
import burlap.mdp.core.state.*;
import burlap.mdp.singleagent.common.*;
import burlap.mdp.singleagent.environment.*;
import burlap.mdp.singleagent.oo.*;
import burlap.statehashing.*;
import burlap.visualizer.*;
import com.nikitosh.spbau.algorithms.*;
import com.nikitosh.spbau.model.*;
import com.nikitosh.spbau.strategies.*;
import com.nikitosh.spbau.visualization.*;

import java.io.*;

public class LearnedAgentVisualizer {
    private static final String MAP_FILE_PATH = "src\\test\\resources\\maps\\8\\map1.txt";
    private static final double GAMMA = 0.99;
    private static final double LEARNING_RATE = 0.3;
    private static final double EPSILON = 0.2;
    private static final int TRIAL_LENGTH = 100000;
    private static final long FRAME_DELAY = 2000;

    public void run() throws FileNotFoundException {
        World world = World.readFromFile(new File(MAP_FILE_PATH));
        BFSPlanner planner = new BFSPlanner(world);
        planner.planAllPaths();

        State initialState = world.getState();
        //GameDomainGenerator domainGenerator = new GameDomainGenerator(world.getAgentActions(),
        //          new GreedyAttackStrategy(planner, world.getAllActions()));
        GameDomainGenerator domainGenerator = new GameDomainGenerator(world.getAgentActions(),
                new RandomStrategy(world.getAllActions()));
        OOSADomain domain = domainGenerator.generateDomain();
        SimulatedEnvironment environment = new SimulatedEnvironment(domain, initialState);
        HashableStateFactory hashingFactory = new ReflectiveHashableStateFactory();

        QLearning agent = new EpsilonGreedyDecayQLearning(domain, GAMMA, hashingFactory, 0, LEARNING_RATE,
                TRIAL_LENGTH, EPSILON);
        for (int j = 0; j < 2; j++) {
            int wins = 0;
            int loses = 0;
            for (int i = 0; i < TRIAL_LENGTH; i++) {
                System.out.println("Episode #" + i);
                Episode e = agent.runLearningEpisode(environment);
                if (((GameState) environment.currentObservation()).isWinning()) {
                    wins++;
                } else {
                    loses++;
                }
                environment.resetEnvironment();
                System.out.println("Reward: " + e.rewardSequence.stream().mapToDouble(Double::doubleValue).sum());
                System.out.println("Number of turns: " + e.maxTimeStep());
            }
            System.out.println("Wins: " + wins);
            System.out.println("Loses: " + loses);
        }
        Visualizer visualizer = new GameVisualizer(world.getEdges()).getVisualizer();
        VisualActionObserver observer = new VisualActionObserver(visualizer);
        observer.setRepaintOnActionInitiation(true);
        observer.setRepaintStateOnEnvironmentInteraction(false);
        observer.initGUI();
        observer.setFrameDelay(FRAME_DELAY);
        environment.addObservers(observer);
        //agent.setLearningPolicy(new GreedyQPolicy(agent));
        Episode e = agent.runLearningEpisode(environment);
        System.out.println("Reward: " + e.rewardSequence.stream().mapToDouble(Double::doubleValue).sum());
        System.out.println("Number of turns: " + e.maxTimeStep());
    }
}
