package com.nikitosh.spbau.strategies;

import burlap.mdp.core.action.*;
import com.nikitosh.spbau.algorithms.*;
import com.nikitosh.spbau.model.*;

import java.util.*;
import java.util.stream.*;

import static com.nikitosh.spbau.algorithms.BFSPlanner.INFINITY;

public class GreedyAttackStrategy implements Strategy {
    private Random random = new Random();
    private BFSPlanner planner;
    private List<Action> actions;

    public GreedyAttackStrategy(BFSPlanner planner, List<Action> actions) {
        this.planner = planner;
        this.actions = actions;
    }

    @Override
    public List<ActionProbability> getActions(GameState state) {
        List<Action> goodActions = new ArrayList<>();

        Agent opponent = state.getOpponent();
        Agent agent = state.getAgent();
        Agent neutral = state.getNeutral();
        int maximumSpaceshipsNumber = opponent.getPlanets().stream().map(Planet::getSpaceshipsNumber)
                .mapToInt(Integer::intValue).max().orElse(0);
        if (maximumSpaceshipsNumber == 1) {
            return Collections.singletonList(new ActionProbability(MoveAction.WAITING_ACTION, 1));
        }
        int minimumDistance = INFINITY;
        for (Planet planet : opponent.getPlanets()) {
            if (planet.getSpaceshipsNumber() >= 2 * maximumSpaceshipsNumber / 3) {
                for (Planet agentPlanet : Stream.concat(agent.getPlanets().stream(), neutral.getPlanets().stream())
                        .collect(Collectors.toSet())) {
                    int currentDistance = planner.getDistance(planet, agentPlanet);
                    if (currentDistance < minimumDistance) {
                        minimumDistance = currentDistance;
                        goodActions.clear();
                    }
                    if (currentDistance == minimumDistance) {
                        int currentSpaceshipsNumber = 0;
                        int spaceshipsNumber = planet.getSpaceshipsNumber();
                        String planetName = planner.getFirstPlanetInPath(planet, agentPlanet);
                        if (spaceshipsNumber >= state.getPlanet(planetName).getSpaceshipsNumber()) {
                            currentSpaceshipsNumber = spaceshipsNumber;
                        } else {
                            currentSpaceshipsNumber = spaceshipsNumber / 3 + random.nextInt((spaceshipsNumber + 2) / 3);
                        }
                        goodActions.add(new MoveAction(planet.getName(), planetName, currentSpaceshipsNumber));
                    }
                }
            }
        }
        return goodActions.stream().map(action -> new ActionProbability(action, 1. / goodActions.size()))
                .collect(Collectors.toList());
    }

    @Override
    public Action getAction(GameState state) {
            List<ActionProbability> possibleActions = getActions(state);
            return possibleActions.get(random.nextInt(possibleActions.size())).getAction();
        }
}
