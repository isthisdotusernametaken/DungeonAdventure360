package model;

import java.io.Serializable;

public interface SpecialSkill extends Serializable {

    AttackResult use(DungeonCharacter theTarget);
}
