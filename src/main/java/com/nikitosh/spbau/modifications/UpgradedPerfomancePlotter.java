package com.nikitosh.spbau.modifications;

import burlap.behavior.singleagent.auxiliary.performance.*;

import java.util.*;

public class UpgradedPerfomancePlotter extends PerformancePlotter {

    public UpgradedPerfomancePlotter(String firstAgentName, int chartWidth, int chartHeight, int columns, int maxWindowHeight, TrialMode trialMode, PerformanceMetric... metrics) {
        super(firstAgentName, chartWidth, chartHeight, columns, maxWindowHeight, trialMode, metrics);
        curTrial = new UpgradedTrial();
    }

    @Override
    public synchronized void startNewTrial() {
        if(this.curTimeStep > 0) {
            this.needsClearing = true;
        }

        this.curTrial = new UpgradedTrial();
        this.lastTimeStepUpdate = 0;
        this.lastEpisode = 0;
        this.curTimeStep = 0;
        this.curEpisode = 0;
    }


    private class UpgradedTrial extends Trial {
        @Override
        public void setupForNewEpisode() {
            PerformancePlotter.accumulate(this.cumulativeEpisodeReward, this.curEpisodeReward);
            PerformancePlotter.accumulate(this.cumulativeStepEpisode, (double)this.curEpisodeSteps);
            double avgER = this.curEpisodeReward;
            this.averageEpisodeReward.add(Double.valueOf(avgER));
            this.stepEpisode.add(Double.valueOf((double)this.curEpisodeSteps));
            Collections.sort(this.curEpisodeRewards);
            double med = 0.0D;
            if(this.curEpisodeSteps > 0) {
                int n2 = this.curEpisodeSteps / 2;
                if(this.curEpisodeSteps % 2 == 0) {
                    double m = ((Double)this.curEpisodeRewards.get(n2)).doubleValue();
                    double m2 = ((Double)this.curEpisodeRewards.get(n2 - 1)).doubleValue();
                    med = (m + m2) / 2.0D;
                } else {
                    med = ((Double)this.curEpisodeRewards.get(n2)).doubleValue();
                }
            }

            this.medianEpisodeReward.add(Double.valueOf(med));
            this.totalSteps += this.curEpisodeSteps;
            ++this.totalEpisodes;
            this.curEpisodeReward = 0.0D;
            this.curEpisodeSteps = 0;
            this.curEpisodeRewards.clear();
        }
    }
}
