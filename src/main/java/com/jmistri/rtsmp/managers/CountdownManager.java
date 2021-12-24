package com.jmistri.rtsmp.managers;

import org.bukkit.entity.Player;

public class CountdownManager {
    private boolean countingDown = false;
    private int countdown = 10;
    private boolean disagree = false;
    private Player initiator;

    public boolean isCountingDown() {
        return countingDown;
    }

    public void initiateCountdown(Player player) {
        countdown = 10;
        initiator = player;
        countingDown = true;
        disagree = false;
    }

    public void cancelCountdown() {
        countingDown = false;
    }

    public int getCountdownValue() {
        return countdown;
    }

    public void countDownByOne() {
        countdown--;
    }

    public void playerDisagree() {
        disagree = true;
    }

    public boolean playerHasDisagreed() {
        return disagree;
    }

    public Player getInitiator() {
        return initiator;
    }
}

