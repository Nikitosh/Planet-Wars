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
        processAction(agent, action, state);
        processAction(opponent, opponentAction, state);
    }

    private void processAction(Agent agent, Action action, GameState state) {
        MoveAction moveAction = (MoveAction) action;
        List<Planet> planets = agent.getPlanets();

        Planet source = state.getPlanet(moveAction.getSourceName());
        Planet destination = state.getPlanet(moveAction.getDestinationName());
        int spaceshipsNumber = moveAction.getSpaceshipsNumber();
        if (source.getSpaceshipsNumber() < spaceshipsNumber || planets.indexOf(source) == -1) {
            return;
        }
        source.increaseSpaceshipsNumber(-spaceshipsNumber);
        if (planets.indexOf(destination) != -1) {
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
            }
        }
    }

    private void onTick(Agent agent) {
        agent.getPlanets().stream().peek(Planet::onTick).forEach(Planet::normalize);
    }
}
