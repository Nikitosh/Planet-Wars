package com.nikitosh.spbau;

import burlap.behavior.singleagent.Episode;
import burlap.behavior.singleagent.auxiliary.EpisodeSequenceVisualizer;
import burlap.behavior.singleagent.learning.LearningAgent;
import burlap.behavior.singleagent.learning.tdmethods.QLearning;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.common.VisualActionObserver;
import burlap.mdp.singleagent.environment.SimulatedEnvironment;
import burlap.mdp.singleagent.oo.OOSADomain;
import burlap.statehashing.*;
import burlap.visualizer.Visualizer;
import com.nikitosh.spbau.model.*;
import com.nikitosh.spbau.visualization.GameVisualizer;

import java.io.*;
import java.util.*;

public final class Main {
    private Main() {}

    public static void main(String[] args) throws FileNotFoundException {
        World world = World.readFromFile(new File("src\\test\\resources\\map.txt"));
        State initialState = world.getState();
        GameDomainGenerator domainGenerator = new GameDomainGenerator(world.getActions());
        OOSADomain domain = domainGenerator.generateDomain();
        SimulatedEnvironment environment = new SimulatedEnvironment(domain, initialState);
        HashableStateFactory hashingFactory = new ReflectiveHashableStateFactory();

        Visualizer visualizer = new GameVisualizer(world.getActions()).getVisualizer();
        VisualActionObserver observer = new VisualActionObserver(visualizer);
        observer.initGUI();

        List<Episode> episodes = new ArrayList<>();

        LearningAgent agent = new QLearning(domain, 0.99, hashingFactory, 0, 1);
        for (int i = 0; i < 50; i++) {
            Episode e = agent.runLearningEpisode(environment);
            e.write("results/" + i);
            episodes.add(e);
            environment.resetEnvironment();
        }
        new EpisodeSequenceVisualizer(visualizer, domain, episodes);

        //VisualExplorer explorer = new VisualExplorer(domain, environment, visualizer);
        //explorer.initGUI();

        /*
        DeterministicPlanner planner = new BFS(domain, new TFGoalCondition(new GameTerminalFunction()), hashingFactory);
        Policy p = planner.planFromState(initialState);
        PolicyUtils.rollout(p, initialState, domain.getModel()).write("trash/" + "bfs");
         */
    }
}
