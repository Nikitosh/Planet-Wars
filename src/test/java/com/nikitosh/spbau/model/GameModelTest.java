package com.nikitosh.spbau.model;

//CheckStyle:OFF: MagicNumber

import com.nikitosh.spbau.*;
import com.nikitosh.spbau.strategies.*;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameModelTest extends RulesBase {

    @Test
    public void testSampleUnionAction() {
        Agent neutral = TestUtilities.generateAgent(GameState.NEUTRAL_NAME, Arrays.asList(2, 3),
                Arrays.asList("Planet1", "Planet2"));
        Agent agent = TestUtilities.generateAgent(GameState.AGENT_NAME, Arrays.asList(5, 6),
                Arrays.asList("Planet3", "Planet4"));
        Agent opponent = TestUtilities.generateAgent(GameState.OPPONENT_NAME, Arrays.asList(9, 1),
                Arrays.asList("Planet5", "Planet6"));
        GameState state = new GameState(neutral, agent, opponent);
        Strategy strategy = new EmptyStrategy();
        MoveAction action = new MoveAction("Planet3", "Planet4", 3);

        Agent expectedAgent = TestUtilities.generateAgent(GameState.AGENT_NAME, Arrays.asList(3, 10),
                Arrays.asList("Planet3", "Planet4"));

        Agent expectedOpponent = TestUtilities.generateAgent(GameState.OPPONENT_NAME, Arrays.asList(10, 2),
                Arrays.asList("Planet5", "Planet6"));

        GameState expectedState = new GameState(neutral, expectedAgent, expectedOpponent);

        assertEquals(expectedState, new GameModel(strategy).sample(state, action));
    }


    @Test
    public void testSampleAttackAction() {
        Agent neutral = TestUtilities.generateAgent(GameState.NEUTRAL_NAME, Arrays.asList(2, 3),
                Arrays.asList("Planet1", "Planet2"));
        Agent agent = TestUtilities.generateAgent(GameState.AGENT_NAME, Arrays.asList(5, 6),
                Arrays.asList("Planet3", "Planet4"));
        Agent opponent = TestUtilities.generateAgent(GameState.OPPONENT_NAME, Arrays.asList(9, 1),
                Arrays.asList("Planet5", "Planet6"));
        GameState state = new GameState(neutral, agent, opponent);
        Strategy strategy = new EmptyStrategy();
        MoveAction action = new MoveAction("Planet3", "Planet5", 3);

        Agent expectedAgent = TestUtilities.generateAgent(GameState.AGENT_NAME, Arrays.asList(3, 7),
                Arrays.asList("Planet3", "Planet4"));

        Agent expectedOpponent = TestUtilities.generateAgent(GameState.OPPONENT_NAME, Arrays.asList(7, 2),
                Arrays.asList("Planet5", "Planet6"));

        GameState expectedState = new GameState(neutral, expectedAgent, expectedOpponent);
        assertEquals(expectedState, new GameModel(strategy).sample(state, action));
    }

    @Test
    public void testSampleCaptureAction() {
        Agent neutral = TestUtilities.generateAgent(GameState.NEUTRAL_NAME, Arrays.asList(2, 3),
                Arrays.asList("Planet1", "Planet2"));
        Agent agent = TestUtilities.generateAgent(GameState.AGENT_NAME, Arrays.asList(5, 6),
                Arrays.asList("Planet3", "Planet4"));
        Agent opponent = TestUtilities.generateAgent(GameState.OPPONENT_NAME, Arrays.asList(9, 1),
                Arrays.asList("Planet5", "Planet6"));
        GameState state = new GameState(neutral, agent, opponent);
        Strategy strategy = new EmptyStrategy();
        MoveAction action = new MoveAction("Planet3", "Planet6", 3);

        Agent expectedAgent = TestUtilities.generateAgent(GameState.AGENT_NAME, Arrays.asList(3, 7, 3),
                Arrays.asList("Planet3", "Planet4", "Planet6"));

        Agent expectedOpponent = TestUtilities.generateAgent(GameState.OPPONENT_NAME, Arrays.asList(10),
                Arrays.asList("Planet5"));

        GameState expectedState = new GameState(neutral, expectedAgent, expectedOpponent);
        assertEquals(expectedState, new GameModel(strategy).sample(state, action));
    }

    @Test
    public void testSampleSimultaneousAttack() {
        Agent neutral = TestUtilities.generateAgent(GameState.NEUTRAL_NAME, Arrays.asList(2, 3),
                Arrays.asList("Planet1", "Planet2"));
        Agent agent = TestUtilities.generateAgent(GameState.AGENT_NAME, Arrays.asList(5, 6),
                Arrays.asList("Planet3", "Planet4"));
        Agent opponent = TestUtilities.generateAgent(GameState.OPPONENT_NAME, Arrays.asList(9, 1),
                Arrays.asList("Planet5", "Planet6"));
        GameState state = new GameState(neutral, agent, opponent);
        Strategy strategy = mock(Strategy.class);
        when(strategy.getAction(state)).thenReturn(
                new MoveAction("Planet5", "Planet1", 8));
        MoveAction action = new MoveAction("Planet3", "Planet1", 3);

        Agent expectedAgent = TestUtilities.generateAgent(GameState.AGENT_NAME, Arrays.asList(3, 7),
                Arrays.asList("Planet3", "Planet4"));

        Agent expectedOpponent = TestUtilities.generateAgent(GameState.OPPONENT_NAME, Arrays.asList(2, 2, 4),
                Arrays.asList("Planet5", "Planet6", "Planet1"));

        Agent expectedNeutral = TestUtilities.generateAgent(GameState.OPPONENT_NAME, Arrays.asList(3),
                Arrays.asList("Planet2"));

        GameState expectedState = new GameState(expectedNeutral, expectedAgent, expectedOpponent);
        assertEquals(expectedState, new GameModel(strategy).sample(state, action));
    }
}

//CheckStyle:ON: MagicNumber
