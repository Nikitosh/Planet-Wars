package com.nikitosh.spbau.experiments;

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

public class LearnedAgentVisualizer {
    private static final String MAP_FILE_PATH = "src\\test\\resources\\maps\\map3.txt";
    private static final double GAMMA = 0.99;
    private static final double LEARNING_RATE = 0.3;
    private static final int TRIAL_LENGTH = 1000;
    private static final long FRAME_DELAY = 100;

    public void run() throws FileNotFoundException {
        World world = World.readFromFile(new File(MAP_FILE_PATH));
        State initialState = world.getState();
        GameDomainGenerator domainGenerator = new GameDomainGenerator(world.getActions(),
                new RandomStrategy(world.getActions()));
        OOSADomain domain = domainGenerator.generateDomain();
        SimulatedEnvironment environment = new SimulatedEnvironment(domain, initialState);
        HashableStateFactory hashingFactory = new ReflectiveHashableStateFactory();

        QLearning agent = new QLearning(domain, GAMMA, hashingFactory, 0, LEARNING_RATE);
        for (int i = 0; i < TRIAL_LENGTH; i++) {
            System.out.println("Episode #" + i);
            Episode e = agent.runLearningEpisode(environment);
            environment.resetEnvironment();
            System.out.println("Reward: " + e.rewardSequence.stream().mapToDouble(Double::doubleValue).sum());
            System.out.println("Number of turns: " + e.maxTimeStep());
        }

        Visualizer visualizer = new GameVisualizer(world.getEdges()).getVisualizer();
        VisualActionObserver observer = new VisualActionObserver(visualizer);
        observer.setRepaintOnActionInitiation(true);
        observer.setRepaintStateOnEnvironmentInteraction(false);
        observer.initGUI();
        observer.setFrameDelay(FRAME_DELAY);
        environment.addObservers(observer);
        agent.setLearningPolicy(new GreedyQPolicy(agent));
        Episode e = agent.runLearningEpisode(environment);
        System.out.println("Reward: " + e.rewardSequence.stream().mapToDouble(Double::doubleValue).sum());
        System.out.println("Number of turns: " + e.maxTimeStep());
    }
}
