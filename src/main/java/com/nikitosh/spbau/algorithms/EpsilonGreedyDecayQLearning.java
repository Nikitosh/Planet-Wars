package com.nikitosh.spbau.algorithms;

import burlap.behavior.policy.*;
import burlap.behavior.singleagent.*;
import burlap.behavior.singleagent.learning.tdmethods.*;
import burlap.mdp.singleagent.*;
import burlap.mdp.singleagent.environment.*;
import burlap.statehashing.*;

public class EpsilonGreedyDecayQLearning extends QLearning {
    private int episodesNumber;
    private double epsilon;

    public EpsilonGreedyDecayQLearning(SADomain domain, double gamma, HashableStateFactory hashingFactory, double qInit,
                                       double learningRate, int episodesNumber, double epsilon) {
        super(domain, gamma, hashingFactory, qInit, learningRate);
        this.episodesNumber = episodesNumber;
        this.epsilon = epsilon;
        learningPolicy = new EpsilonGreedy(this, epsilon);
    }

    @Override
    public Episode runLearningEpisode(Environment env) {
        EpsilonGreedy policy = (EpsilonGreedy) learningPolicy;
        policy.setEpsilon(Math.max(0, policy.getEpsilon() - epsilon / episodesNumber));
        return super.runLearningEpisode(env);
    }
}
