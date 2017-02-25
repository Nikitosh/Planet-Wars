package com.nikitosh.spbau.model;

import burlap.mdp.core.StateTransitionProb;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.model.statemodel.FullStateModel;
import com.nikitosh.spbau.strategies.Strategy;

import java.util.List;
import java.util.stream.*;

public class GameModel implements FullStateModel {
    private Strategy opponentStrategy;

    public GameModel(Strategy opponentAgent) {
        this.opponentStrategy = opponentAgent;
    }

    @Override
    public List<StateTransitionProb> stateTransitions(State state, Action action) {
        return opponentStrategy.getActions((GameState) state).stream()
                .map(actionProbability -> new StateTransitionProb(sample(state, action, actionProbability.getAction()),
                                actionProbability.getProbability())).collect(Collectors.toList());
    }

    @Override
    public State sample(State state, Action action) {
        return sample(state, action, opponentStrategy.getAction((GameState) state));
    }

    private State sample(State state, Action action, Action opponentAction) {
        GameState gameState = (GameState) state.copy();
        Agent newNeutral = gameState.touchNeutral();
        Agent newAgent = gameState.touchAgent();
        Agent newOpponent = gameState.touchOpponent();
        processActions(newAgent, newOpponent, action, opponentAction, gameState);
        onTick(newAgent);
        onTick(newOpponent);
        newNeutral.rebuildPlanets();
        newAgent.rebuildPlanets();
        newOpponent.rebuildPlanets();
        return gameState;
    }

    private void processActions(Agent agent, Agent opponent, Action action, Action opponentAction, GameState state) {
        MoveAction moveAction = (MoveAction) action.copy();
        MoveAction moveOpponentAction = (MoveAction) opponentAction.copy();
        if (!moveAction.isApplicable(state, agent)) {
            throw new RuntimeException("Agent action " + moveAction + " can't be applied to current state");
        }
        if (!moveOpponentAction.isApplicable(state, opponent)) {
            throw new RuntimeException("Opponent action " + moveOpponentAction + " can't be applied to current state");
        }
        if (!moveAction.isWaiting()) {
            state.getPlanet(moveAction.getSourceName()).increaseSpaceshipsNumber(-moveAction.getSpaceshipsNumber());
        }
        if (!moveOpponentAction.isWaiting()) {
            state.getPlanet(moveOpponentAction.getSourceName())
                    .increaseSpaceshipsNumber(-moveOpponentAction.getSpaceshipsNumber());
        }
        if (moveAction.getDestinationName().equals(moveOpponentAction.getDestinationName())) {
            int minimumSpaceshipsNumber = Math.min(moveAction.getSpaceshipsNumber(),
                    moveOpponentAction.getSpaceshipsNumber());
            moveAction.increaseSpaceshipsNumber(-minimumSpaceshipsNumber);
            moveOpponentAction.increaseSpaceshipsNumber(-minimumSpaceshipsNumber);
        }
        processAction(agent, moveAction, state);
        processAction(opponent, moveOpponentAction, state);
    }

    private void processAction(Agent agent, MoveAction action, GameState state) {
        if (action.isWaiting()) {
            return;
        }
        Planet destination = state.getPlanet(action.getDestinationName());
        int spaceshipsNumber = action.getSpaceshipsNumber();
        if (agent.getPlanets().contains(destination)) {
            destination.increaseSpaceshipsNumber(spaceshipsNumber);
            return;
        }
        if (destination.getSpaceshipsNumber() >= spaceshipsNumber) {
            destination.increaseSpaceshipsNumber(-spaceshipsNumber);
            return;
        }
        for (ObjectInstance objectInstance : state.objects()) {
            Agent anotherAgent = (Agent) objectInstance;
            if (anotherAgent.getPlanets().contains(destination)) {
                anotherAgent.removePlanet(destination);
                destination.setSpaceshipsNumber(spaceshipsNumber - destination.getSpaceshipsNumber());
                agent.addPlanet(destination);
                break;
            }
        }
    }

    private void onTick(Agent agent) {
        agent.getPlanets().stream().peek(Planet::onTick).forEach(Planet::normalize);
    }
}
