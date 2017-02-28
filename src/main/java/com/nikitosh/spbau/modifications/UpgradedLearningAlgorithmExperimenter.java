package com.nikitosh.spbau.modifications;

import burlap.behavior.singleagent.auxiliary.performance.*;
import burlap.behavior.singleagent.learning.*;
import burlap.mdp.singleagent.environment.*;

public class UpgradedLearningAlgorithmExperimenter extends LearningAlgorithmExperimenter {
    public UpgradedLearningAlgorithmExperimenter(Environment testEnvironment, int nTrials, int trialLength,
                                                 LearningAgentFactory... agentFactories) {
        super(testEnvironment, nTrials, trialLength, agentFactories);
    }

    @Override
    public void setUpPlottingConfiguration(int chartWidth, int chartHeight, int columns, int maxWindowHeight,
                                           TrialMode trialMode, PerformanceMetric... metrics) {
        if (trialMode.averagesEnabled() && this.nTrials == 1) {
            trialMode = TrialMode.MOST_RECENT_TRIAL_ONLY;
        }

        this.displayPlots = true;
        this.plotter = new UpgradedPerfomancePlotter(this.agentFactories[0].getAgentName(),
                chartWidth, chartHeight, columns, maxWindowHeight, trialMode, metrics);
        this.plotter.setRefreshDelay(this.plotRefresh);
        this.plotter.setSignificanceForCI(this.plotCISignificance);
    }
}
