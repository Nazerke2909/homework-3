package com.narxoz.rpg;

import com.narxoz.rpg.adapter.*;
import com.narxoz.rpg.battle.*;
import com.narxoz.rpg.enemy.*;
import com.narxoz.rpg.hero.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== RPG Battle Engine Demo ===\n");

        Warrior warrior = new Warrior("Luca");
        Mage mage = new Mage("Jane");
        Goblin goblin = new Goblin();

        List<Combatant> heroes = new ArrayList<>();
        heroes.add(new HeroCombatantAdapter(warrior));
        heroes.add(new HeroCombatantAdapter(mage));

        List<Combatant> enemies = new ArrayList<>();
        enemies.add(new EnemyCombatantAdapter(goblin));

        BattleEngine engineA = BattleEngine.getInstance();
        BattleEngine engineB = BattleEngine.getInstance();
        System.out.println("Singleton check: Is engineA the same as engineB? -> " + (engineA == engineB));
        System.out.println();

        System.out.println("Starting battle: Team Heroes vs Team Enemies...");
        engineA.setRandomSeed(42L);
        EncounterResult result = engineA.runEncounter(heroes, enemies);

        System.out.println("\n--- Battle Summary ---");
        System.out.println("Winner: " + result.getWinner());
        System.out.println("Total Rounds: " + result.getRounds());
        System.out.println("\nDetailed Log:");
        for (String line : result.getBattleLog()) {
            System.out.println("  " + line);
        }

        System.out.println("\n=== Demo Complete ===");
    }
}