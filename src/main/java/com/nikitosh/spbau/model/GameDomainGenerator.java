package com.nikitosh.spbau.model;

import burlap.mdp.auxiliary.*;
import burlap.mdp.core.action.*;
import burlap.mdp.core.state.*;
import burlap.mdp.singleagent.model.*;
import burlap.mdp.singleagent.oo.*;
import com.nikitosh.spbau.strategies.*;

import java.util.*;

public class GameDomainGenerator implements DomainGenerator {
    private List<Action> actions;
    private Strategy strategy;

    public GameDomainGenerator(List<Action> actions, Strategy strategy) {
        this.actions = actions;
        this.strategy = strategy;
    }

    @Override
    public OOSADomain generateDomain() {
        OOSADomain domain = new OOSADomain();
        domain.addStateClass(Agent.CLASS_NAME, Agent.class)
                .addStateClass(Planet.CLASS_NAME, Planet.class);
        actions.forEach((action) -> domain.addActionType(new UniversalActionType(action) {
            public List<Action> allApplicableActions(State s) {
                GameState gameState = (GameState) s;
                MoveAction moveAction = (MoveAction) action;
                return moveAction.isApplicable(gameState, gameState.getAgent())
                        ? Collections.singletonList(action) : Collections.emptyList();
            }
        }));

        domain.setModel(new FactoredModel(new GameModel(strategy),
                new GameRewardFunction(),
                new GameTerminalFunction()));
        return domain;
    }
}
