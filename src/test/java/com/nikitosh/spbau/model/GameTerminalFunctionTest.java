package com.nikitosh.spbau.model;

import com.nikitosh.spbau.*;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.assertEquals;

//CheckStyle:OFF: MagicNumber

public class GameTerminalFunctionTest {

    @Test
    public void testIsTerminalFalse() {
        GameState state = new GameState(TestUtilities.generateAgent(GameState.NEUTRAL_NAME, Arrays.asList(2, 3, 1, 5)),
                TestUtilities.generateAgent(GameState.AGENT_NAME, Arrays.asList(3, 9, 8, 1)),
                TestUtilities.generateAgent(GameState.OPPONENT_NAME, Arrays.asList(1, 3)));
        assertEquals(false, (new GameTerminalFunction()).isTerminal(state));
    }

    @Test
    public void testIsTerminalTrue() {
        GameState state = new GameState(TestUtilities.generateAgent(GameState.NEUTRAL_NAME, Arrays.asList(2, 1)),
                TestUtilities.generateAgent(GameState.AGENT_NAME, Arrays.asList(3, 9, 8, 1)),
                TestUtilities.generateAgent(GameState.OPPONENT_NAME, Collections.emptyList()));
        assertEquals(true, (new GameTerminalFunction()).isTerminal(state));
    }
}

//CheckStyle:ON: MagicNumber
