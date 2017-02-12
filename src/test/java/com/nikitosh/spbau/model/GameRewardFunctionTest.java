package com.nikitosh.spbau.model;

import burlap.mdp.core.action.*;
import burlap.mdp.core.state.*;
import com.nikitosh.spbau.*;
import com.nikitosh.spbau.strategies.*;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.assertEquals;

//CheckStyle:OFF: MagicNumber

public class GameRewardFunctionTest {
    private static final double DELTA = 1e-6;

    @Test
    public void testReward() {
        GameState oldState = new GameState(TestUtilities.generateAgent(GameState.NEUTRAL_NAME, Arrays.asList(2, 3, 1)),
                TestUtilities.generateAgent(GameState.AGENT_NAME, Arrays.asList(3, 9, 8, 1)),
                TestUtilities.generateAgent(GameState.OPPONENT_NAME, Arrays.asList(1, 3)));
        Planet agentPlanet = oldState.getAgent().getPlanets().get(0);
        Action action = new MoveAction(agentPlanet.getName(), agentPlanet.getName(), 0);
        State newState = new GameModel(new EmptyStrategy()).sample(oldState, action);
        assertEquals(4 + 10 + 9 + 2 - (2 + 4), new GameRewardFunction().reward(oldState, action, newState), DELTA);
    }
}

//CheckStyle:ON: MagicNumber
