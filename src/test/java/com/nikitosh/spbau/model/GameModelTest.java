package com.nikitosh.spbau.model;

//CheckStyle:OFF: MagicNumber

import com.nikitosh.spbau.*;
import com.nikitosh.spbau.strategies.*;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.assertEquals;

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

    /*
    @Test
    public void testSampleAttackAction() {
        GameState state = generateState();
        Agent neutral = state.getNeutral();
        Agent agent = state.getAgent();
        Agent opponent = state.getOpponent();
        Strategy strategy = new EmptyStrategy();
        MoveAction action = new MoveAction(agent.getPlanets().get(0).getName(),
                opponent.getPlanets().get(0).getName(), 3);

        Agent expectedAgent = agent.copy();
        expectedAgent.getPlanets().get(0).setSpaceshipsNumber(3);
        expectedAgent.getPlanets().get(1).setSpaceshipsNumber(7);

        Agent expectedOpponent = opponent.copy();
        expectedOpponent.getPlanets().get(0).setSpaceshipsNumber(7);
        expectedOpponent.getPlanets().get(1).setSpaceshipsNumber(2);

        GameState expectedState = new GameState(neutral, expectedAgent, expectedOpponent);
        assertEquals(expectedState, new GameModel(strategy).sample(state, action));
    }

    @Test
    public void testSampleCaptureAction() {
        GameState state = generateState();
        Agent neutral = state.getNeutral();
        Agent agent = state.getAgent();
        Agent opponent = state.getOpponent();
        Strategy strategy = new EmptyStrategy();
        MoveAction action = new MoveAction(agent.getPlanets().get(0).getName(),
                opponent.getPlanets().get(1).getName(), 3);

        Agent expectedAgent = agent.copy();
        expectedAgent.getPlanets().get(0).setSpaceshipsNumber(3);
        expectedAgent.getPlanets().get(1).setSpaceshipsNumber(7);

        Agent expectedOpponent = opponent.copy();
        expectedOpponent.getPlanets().get(0).setSpaceshipsNumber(10);

        Planet capturedPlanet = expectedOpponent.getPlanets().get(1);
        expectedOpponent.removePlanet(capturedPlanet);
        capturedPlanet.setSpaceshipsNumber(3);
        expectedAgent.addPlanet(capturedPlanet);

        GameState expectedState = new GameState(neutral, expectedAgent, expectedOpponent);
        assertEquals(expectedState, new GameModel(strategy).sample(state, action));
    }

    @Test
    public void testSampleSimultaneousAttack() {
        GameState state = generateState();
        Agent neutral = state.getNeutral();
        Agent agent = state.getAgent();
        Agent opponent = state.getOpponent();
        Strategy strategy = mock(Strategy.class);
        when(strategy.getAction(state)).thenReturn(
                new MoveAction(opponent.getPlanets().get(0).getName(), neutral.getPlanets().get(0).getName(), 8));
        MoveAction action = new MoveAction(agent.getPlanets().get(0).getName(),
                neutral.getPlanets().get(0).getName(), 3);

        Agent expectedAgent = agent.copy();
        expectedAgent.getPlanets().get(0).setSpaceshipsNumber(3);
        expectedAgent.getPlanets().get(1).setSpaceshipsNumber(7);

        Agent expectedOpponent = opponent.copy();
        expectedOpponent.getPlanets().get(0).setSpaceshipsNumber(2);
        expectedOpponent.getPlanets().get(1).setSpaceshipsNumber(2);

        Agent expectedNeutral = neutral.copy();

        Planet capturedPlanet = expectedNeutral.getPlanets().get(0);
        expectedNeutral.removePlanet(capturedPlanet);
        capturedPlanet.setSpaceshipsNumber(4);
        expectedOpponent.addPlanet(capturedPlanet);

        GameState expectedState = new GameState(expectedNeutral, expectedAgent, expectedOpponent);
        assertEquals(expectedState, new GameModel(strategy).sample(state, action));
    }
    */
}

//CheckStyle:ON: MagicNumber
