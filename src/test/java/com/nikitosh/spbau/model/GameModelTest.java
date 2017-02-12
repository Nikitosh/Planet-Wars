package com.nikitosh.spbau.model;

import com.nikitosh.spbau.*;
import com.nikitosh.spbau.strategies.*;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//CheckStyle:OFF: MagicNumber

public class GameModelTest {

    @Test
    public void testSampleUnionAction() {
        GameState state = generateState();
        Agent neutral = state.getNeutral();
        Agent agent = state.getAgent();
        Agent opponent = state.getOpponent();
        Strategy strategy = new EmptyStrategy();
        MoveAction action = new MoveAction(agent.getPlanets().get(0).getName(),
                agent.getPlanets().get(1).getName(), 3);

        Agent expectedAgent = agent.copy();
        expectedAgent.getPlanets().get(0).setSpaceshipsNumber(3);
        expectedAgent.getPlanets().get(1).setSpaceshipsNumber(10);

        Agent expectedOpponent = opponent.copy();
        expectedOpponent.getPlanets().get(0).setSpaceshipsNumber(10);
        expectedOpponent.getPlanets().get(1).setSpaceshipsNumber(2);

        GameState expectedState = new GameState(neutral, expectedAgent, expectedOpponent);
        assertEquals(expectedState, new GameModel(strategy).sample(state, action));
    }

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


    private GameState generateState() {
        Agent neutral = TestUtilities.generateAgent(GameState.NEUTRAL_NAME, Arrays.asList(2, 3));
        Agent agent = TestUtilities.generateAgent(GameState.AGENT_NAME, Arrays.asList(5, 6));
        Agent opponent = TestUtilities.generateAgent(GameState.OPPONENT_NAME, Arrays.asList(9, 1));
        return new GameState(neutral, agent, opponent);
    }

}

//CheckStyle:ON: MagicNumber
