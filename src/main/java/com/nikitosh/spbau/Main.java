package com.nikitosh.spbau;

import com.nikitosh.spbau.experiments.*;

import java.io.*;

public final class Main {
    private Main() {}

    public static void main(String[] args) throws FileNotFoundException {
        new LearnedAgentVisualizer().run();
        new PlotsDrawer().run();
    }
}
