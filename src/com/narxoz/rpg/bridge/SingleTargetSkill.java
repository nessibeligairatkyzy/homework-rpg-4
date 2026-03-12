package com.narxoz.rpg.bridge;

import com.narxoz.rpg.composite.CombatNode;

public class SingleTargetSkill extends Skill {
    public SingleTargetSkill(String skillName, int basePower, EffectImplementor effect) {
        super(skillName, basePower, effect);
    }

    @Override
    public void cast(CombatNode target) {
        int damage = resolvedDamage();
        target.takeDamage(damage);
        System.out.println("[" + getEffectName() + "] " + getSkillName() +
                " hits " + target.getName() + " for " + damage + " damage.");
    }
}