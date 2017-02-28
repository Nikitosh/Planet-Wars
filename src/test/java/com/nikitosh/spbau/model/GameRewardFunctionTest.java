package com.nikitosh.spbau.model;

//CheckStyle:OFF: MagicNumber

import burlap.mdp.core.state.*;
import com.nikitosh.spbau.*;
import com.nikitosh.spbau.strategies.*;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class GameRewardFunctionTest extends RulesBase {
    private static final double DELTA = 1e-6;

    @Test
    public void testReward() {
        GameState oldState = new GameState(TestUtilities.generateAgent(GameState.NEUTRAL_NAME, Arrays.asList(2, 3, 1)),
                TestUtilities.generateAgent(GameState.AGENT_NAME, Arrays.asList(3, 9, 8, 1),
                        Arrays.asList("Planet1", "Planet2", "Planet3", "Planet4")),
                TestUtilities.generateAgent(GameState.OPPONENT_NAME, Arrays.asList(1, 3)));
        State newState = new GameModel(new EmptyStrategy()).sample(oldState, MoveAction.WAITING_ACTION);
        assertEquals(4 + 10 + 9 + 2 - (2 + 4),
                new GameRewardFunction().reward(oldState, MoveAction.WAITING_ACTION, newState), DELTA);
    }

}

//CheckStyle:ON: MagicNumber
