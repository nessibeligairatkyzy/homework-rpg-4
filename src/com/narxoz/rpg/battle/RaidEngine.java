package com.narxoz.rpg.battle;

import com.narxoz.rpg.bridge.Skill;
import com.narxoz.rpg.composite.CombatNode;

import java.util.Random;

public class RaidEngine {
    private Random random = new Random(1L);

    public RaidEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }

    public RaidResult runRaid(CombatNode teamA, CombatNode teamB, Skill teamASkill, Skill teamBSkill) {
        RaidResult result = new RaidResult();

        if (teamA == null || teamB == null || teamASkill == null || teamBSkill == null) {
            result.setWinner("ERROR: null input");
            result.setRounds(0);
            return result;
        }

        if (!teamA.isAlive() || !teamB.isAlive()) {
            result.setWinner(!teamA.isAlive() ? teamB.getName() : teamA.getName());
            result.setRounds(0);
            return result;
        }

        int maxRounds = 100;
        int round = 0;

        while (teamA.isAlive() && teamB.isAlive() && round < maxRounds) {
            round++;
            result.addLine("--- Round " + round + " ---");


            if (teamA.isAlive()) {
                boolean crit = random.nextInt(100) < 10;
                int bonus = crit ? (int)(teamA.getAttackPower() * 0.5) : 0;
                teamASkill.cast(teamB);
                if (crit) {
                    teamB.takeDamage(bonus);
                    result.addLine("  CRITICAL HIT! +" + bonus + " extra damage on " + teamB.getName());
                }
                result.addLine("  " + teamA.getName() + " uses " + teamASkill.getSkillName() +
                        " [" + teamASkill.getEffectName() + "] on " + teamB.getName() +
                        " | TeamB HP=" + teamB.getHealth());
            }

            if (teamB.isAlive()) {
                boolean crit = random.nextInt(100) < 10;
                int bonus = crit ? (int)(teamB.getAttackPower() * 0.5) : 0;
                teamBSkill.cast(teamA);
                if (crit) {
                    teamA.takeDamage(bonus);
                    result.addLine("  CRITICAL HIT! +" + bonus + " extra damage on " + teamA.getName());
                }
                result.addLine("  " + teamB.getName() + " uses " + teamBSkill.getSkillName() +
                        " [" + teamBSkill.getEffectName() + "] on " + teamA.getName() +
                        " | TeamA HP=" + teamA.getHealth());
            }
        }

        result.setRounds(round);

        if (teamA.isAlive() && !teamB.isAlive()) {
            result.setWinner(teamA.getName());
        } else if (!teamA.isAlive() && teamB.isAlive()) {
            result.setWinner(teamB.getName());
        } else {
            result.setWinner("Draw");
        }

        return result;
    }
}