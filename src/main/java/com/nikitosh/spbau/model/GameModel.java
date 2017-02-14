package com.nikitosh.spbau.model;

import burlap.mdp.core.StateTransitionProb;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.model.statemodel.FullStateModel;
import com.nikitosh.spbau.strategies.Strategy;

import java.util.List;

public class GameModel implements FullStateModel {
    private Strategy opponentStrategy;

    public GameModel(Strategy opponentAgent) {
        this.opponentStrategy = opponentAgent;
    }

    @Override
    public List<StateTransitionProb> stateTransitions(State state, Action action) {
        return FullStateModel.Helper.deterministicTransition(this, state, action);
    }

    @Override
    public State sample(State state, Action action) {
        GameState gameState = (GameState) state.copy();
        gameState.touchNeutral();
        Agent newAgent = gameState.touchAgent();
        Agent newOpponent = gameState.touchOpponent();
        Action opponentAction = opponentStrategy.getAction(gameState);
        processActions(newAgent, newOpponent, action, opponentAction, gameState);
        onTick(newAgent);
        onTick(newOpponent);
        return gameState;
    }

    private void processActions(Agent agent, Agent opponent, Action action, Action opponentAction, GameState state) {
        MoveAction moveAction = (MoveAction) action;
        MoveAction moveOpponentAction = (MoveAction) opponentAction;
        if (!state.isApplicable(agent, moveAction)) {
            throw new RuntimeException("Agent action " + moveAction + " can't be applied to current state");
        }
        if (!state.isApplicable(opponent, moveOpponentAction)) {
            throw new RuntimeException("Opponent action " + moveOpponentAction + " can't be applied to current state");
        }
        state.getPlanet(moveAction.getSourceName()).increaseSpaceshipsNumber(-moveAction.getSpaceshipsNumber());
        state.getPlanet(moveOpponentAction.getSourceName())
                .increaseSpaceshipsNumber(-moveOpponentAction.getSpaceshipsNumber());
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
        Planet destination = state.getPlanet(action.getDestinationName());
        int spaceshipsNumber = action.getSpaceshipsNumber();
        if (agent.getPlanets().indexOf(destination) != -1) {
            destination.increaseSpaceshipsNumber(spaceshipsNumber);
            return;
        }
        if (destination.getSpaceshipsNumber() >= spaceshipsNumber) {
            destination.increaseSpaceshipsNumber(-spaceshipsNumber);
            return;
        }
        for (ObjectInstance objectInstance : state.objects()) {
            Agent anotherAgent = (Agent) objectInstance;
            if (anotherAgent.getPlanets().indexOf(destination) != -1) {
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
