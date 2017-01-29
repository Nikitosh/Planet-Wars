package com.nikitosh.spbau;

import burlap.behavior.singleagent.*;
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
        observer.setRepaintOnActionInitiation(true);
        observer.setRepaintStateOnEnvironmentInteraction(false);
        observer.initGUI();
        observer.setFrameDelay(1000);
        environment.addObservers(observer);

        List<Episode> episodes = new ArrayList<>();

        QLearning agent = new QLearning(domain, 0.99, hashingFactory, 0, 1);

        for (int i = 0; i < 10000; i++) {
            Episode e = agent.runLearningEpisode(environment);
            e.write("results/" + i);
            episodes.add(e);
            environment.resetEnvironment();
            System.out.println(e.rewardSequence.stream().mapToDouble(Double::doubleValue).sum());
            System.out.println(e.maxTimeStep());
        }
        //new EpisodeSequenceVisualizer(visualizer, domain, episodes);

        /*
        VisualExplorer explorer = new VisualExplorer(domain, environment, visualizer);
        explorer.initGUI();
        DeterministicPlanner planner = new BFS(domain, new TFGoalCondition(new GameTerminalFunction()), hashingFactory);
        Policy p = planner.planFromState(initialState);
        PolicyUtils.rollout(p, initialState, domain.getModel()).write("trash/" + "bfs");
        */
    }
}
