package com.nikitosh.spbau.model;

import burlap.mdp.auxiliary.DomainGenerator;
import burlap.mdp.core.action.*;
import burlap.mdp.singleagent.model.FactoredModel;
import burlap.mdp.singleagent.oo.OOSADomain;
import com.nikitosh.spbau.strategies.*;

import java.util.List;

public class GameDomainGenerator implements DomainGenerator {
    private List<Action> actions;

    public GameDomainGenerator(List<Action> actions) {
        this.actions = actions;
    }

    @Override
    public OOSADomain generateDomain() {
        OOSADomain domain = new OOSADomain();
        domain.addStateClass(Agent.CLASS_NAME, Agent.class)
                .addStateClass(Planet.CLASS_NAME, Planet.class);
        actions.forEach((action) -> domain.addActionType(new UniversalActionType(action)));

        Strategy emptyStrategy = new EmptyStrategy();
        domain.setModel(new FactoredModel(new GameModel(emptyStrategy),
                new GameRewardFunction(),
                new GameTerminalFunction()));
        return domain;
    }
}
