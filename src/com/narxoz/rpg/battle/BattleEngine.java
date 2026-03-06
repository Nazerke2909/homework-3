package com.narxoz.rpg.battle;

import java.util.List;
import java.util.Random;

public final class BattleEngine {
    private static BattleEngine instance;
    private Random random = new Random(1L);

    private BattleEngine() {}

    public static synchronized BattleEngine getInstance() {
        if (instance == null) {
            instance = new BattleEngine();
        }
        return instance;
    }

    public BattleEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }

    public EncounterResult runEncounter(List<Combatant> teamA, List<Combatant> teamB) {
        EncounterResult result = new EncounterResult();
        int rounds = 0;

        while (!teamA.isEmpty() && !teamB.isEmpty()) {
            rounds++;
            result.addLog("--- Round " + rounds + " ---");

            performAttacks(teamA, teamB, result);
            teamB.removeIf(c -> !c.isAlive());

            if (!teamB.isEmpty()) {
                performAttacks(teamB, teamA, result);
                teamA.removeIf(c -> !c.isAlive());
            }
        }

        result.setRounds(rounds);
        result.setWinner(teamA.isEmpty() ? "Team B (Enemies)" : "Team A (Heroes)");
        return result;
    }

    private void performAttacks(List<Combatant> attackers, List<Combatant> defenders, EncounterResult result) {
        for (Combatant attacker : attackers) {
            if (defenders.isEmpty()) break;
            Combatant target = defenders.get(0);
            target.takeDamage(attacker.getAttackPower());
            result.addLog(attacker.getName() + " attacks " + target.getName() + " for " + attacker.getAttackPower());
        }
    }
}