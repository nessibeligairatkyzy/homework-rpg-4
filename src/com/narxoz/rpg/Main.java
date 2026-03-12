package com.narxoz.rpg;

import com.narxoz.rpg.battle.RaidEngine;
import com.narxoz.rpg.battle.RaidResult;
import com.narxoz.rpg.bridge.*;
import com.narxoz.rpg.composite.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Homework 4 Demo: Bridge + Composite ===\n");

        // --- Composite: build leaves ---
        HeroUnit warrior = new HeroUnit("Arthas", 140, 30);
        HeroUnit mage    = new HeroUnit("Jaina", 90, 40);
        EnemyUnit goblin = new EnemyUnit("Goblin", 70, 20);
        EnemyUnit orc    = new EnemyUnit("Orc", 120, 25);
        EnemyUnit troll  = new EnemyUnit("Troll", 80, 18);

        // --- Composite: nested hierarchy ---
        PartyComposite heroes = new PartyComposite("Heroes");
        heroes.add(warrior);
        heroes.add(mage);

        PartyComposite frontline = new PartyComposite("Frontline");
        frontline.add(goblin);
        frontline.add(orc);

        PartyComposite backline = new PartyComposite("Backline");
        backline.add(troll);

        RaidGroup enemies = new RaidGroup("Enemy Raid");
        enemies.add(frontline);  // nested composite inside RaidGroup
        enemies.add(backline);

        System.out.println("--- Team Structures ---");
        heroes.printTree("");
        System.out.println();
        enemies.printTree("");

        // --- Bridge: same skill, different effects ---
        System.out.println("\n--- Bridge: Same Skill, Different Effects ---");
        Skill slashPhysical = new SingleTargetSkill("Slash", 20, new PhysicalEffect());
        Skill slashFire     = new SingleTargetSkill("Slash", 20, new FireEffect());
        Skill slashIce      = new SingleTargetSkill("Slash", 20, new IceEffect());
        System.out.println(slashPhysical.getSkillName() + " + " + slashPhysical.getEffectName()
                + " => " + slashPhysical.getSkillName());
        System.out.println(slashFire.getSkillName()     + " + " + slashFire.getEffectName()
                + " => " + slashFire.getSkillName());
        System.out.println(slashIce.getSkillName()      + " + " + slashIce.getEffectName()
                + " => " + slashIce.getSkillName());

        // --- Bridge: different skills, same effect ---
        System.out.println("\n--- Bridge: Different Skills, Same Effect ---");
        Skill singleFire = new SingleTargetSkill("Fireball", 25, new FireEffect());
        Skill areaFire   = new AreaSkill("Inferno", 15, new FireEffect());
        System.out.println("SingleTargetSkill + Fire: " + singleFire.getSkillName());
        System.out.println("AreaSkill         + Fire: " + areaFire.getSkillName());


        System.out.println("\n--- Raid Simulation ---");
        // Rebuild fresh units for fair fight
        HeroUnit w2 = new HeroUnit("Arthas", 140, 30);
        HeroUnit m2 = new HeroUnit("Jaina", 90, 40);
        EnemyUnit g2 = new EnemyUnit("Goblin", 70, 20);
        EnemyUnit o2 = new EnemyUnit("Orc", 120, 25);
        EnemyUnit t2 = new EnemyUnit("Troll", 80, 18);

        PartyComposite teamA = new PartyComposite("Heroes");
        teamA.add(w2); teamA.add(m2);

        PartyComposite fl2 = new PartyComposite("Frontline");
        fl2.add(g2); fl2.add(o2);
        RaidGroup teamB = new RaidGroup("Enemy Raid");
        teamB.add(fl2); teamB.add(t2);

        Skill heroSkill  = new SingleTargetSkill("Slash", 20, new FireEffect());
        Skill enemySkill = new AreaSkill("Storm", 15, new ShadowEffect());

        RaidEngine engine = new RaidEngine().setRandomSeed(42L);
        RaidResult result = engine.runRaid(teamA, teamB, heroSkill, enemySkill);

        System.out.println("\n--- Raid Result ---");
        System.out.println("Winner: " + result.getWinner());
        System.out.println("Rounds: " + result.getRounds());
        for (String line : result.getLog()) {
            System.out.println(line);
        }

        System.out.println("\n=== Demo Complete ===");
    }
}
