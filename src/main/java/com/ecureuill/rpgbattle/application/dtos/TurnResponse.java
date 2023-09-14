package com.ecureuill.rpgbattle.application.dtos;

import com.ecureuill.rpgbattle.domain.battle.Turn;

public record TurnResponse(
  Integer attackDiceValue,
  Integer defenseDiceValue,
  Integer damageDiceValue,
  Integer attack,
  Integer defense,
  Integer damage
) {
  public TurnResponse(Turn turn){
    this(turn.getAttackDiceValue(), turn.getDefenseDiceValue(), turn.getDamageDiceValue(), turn.getAttack(), turn.getDefense(), turn.getDamage());
  }
}
