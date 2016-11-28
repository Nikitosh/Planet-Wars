package com.nikitosh.spbau.model;

import burlap.mdp.core.StateTransitionProb;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.model.statemodel.FullStateModel;
import com.nikitosh.spbau.strategies.AIAgent;

import java.util.List;

public class GameModel implements FullStateModel {
    private AIAgent opponentAgent;

    public GameModel(AIAgent opponentAgent) {
        this.opponentAgent = opponentAgent;
    }

    @Override
    public List<StateTransitionProb> stateTransitions(State state, Action action) {
        return FullStateModel.Helper.deterministicTransition(this, state, action);
    }

    @Override
    public State sample(State state, Action action) {
        GameState gameState = (GameState) state.copy();
        gameState.touchNeutral();
        processAction(gameState.touchAgent(), action, gameState);
        processAction(gameState.touchOpponent(), opponentAgent.getAction(gameState), gameState);
        return gameState;
    }

    private void processAction(Agent agent, Action action, GameState state) {
        MoveAction moveAction = (MoveAction) action;
        List<Planet> planets = agent.getPlanets();
        Planet source = moveAction.getSource();
        Planet destination = moveAction.getDestination();
        int spaceshipsNumber = moveAction.getSpaceshipsNumber();
        if (source.getSpaceshipsNumber() < spaceshipsNumber || planets.indexOf(source) == -1) {
            return;
        }
        source.increaseSpaceshipsNumber(-spaceshipsNumber);
        if (planets.indexOf(destination) != -1) {
            destination.increaseSpaceshipsNumber(spaceshipsNumber);
            return;
        }
        if (destination.getSpaceshipsNumber() > spaceshipsNumber) {
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
}
