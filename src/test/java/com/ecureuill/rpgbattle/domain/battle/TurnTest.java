// package com.ecureuill.rpgbattle.domain.battle;

// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.Mockito;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.context.ApplicationEventPublisher;

// import com.ecureuill.rpgbattle.domain.battle.states.turnstate.AttackMoveState;
// import com.ecureuill.rpgbattle.domain.battle.states.turnstate.DefenseMoveState;
// import com.ecureuill.rpgbattle.domain.battle.states.turnstate.DemageMoveState;
// import com.ecureuill.rpgbattle.domain.battle.states.turnstate.EndTurnState;

// @ExtendWith(MockitoExtension.class)
// public class TurnTest {
//   @Mock
//   private Player attackPlayer;
//   @Mock
//   private Player defensePlayer;
//   @Mock(name = "attackMoveState")
//   private AttackMoveState attackMoveState;
//   @Mock(name = "defenseMoveState") 
//   private DefenseMoveState defenseMoveState;
//   @Mock(name = "demageMoveState")
//   private DemageMoveState demageMoveState;
//   @Mock(name = "endTurnState")
//   private EndTurnState endTurnState;
//   @Mock
//   private ApplicationEventPublisher eventPublisher;
//   @InjectMocks
//   private Turn turn;

//   @DisplayName("Should call the correct handle method of AttackMoveState")
//   @Test
//   public void testMovement_AttackState() {
//     turn.setState(attackMoveState);
//     turn.movement(attackPlayer, defensePlayer);
//     Mockito.verify(attackMoveState, Mockito.times(1)).handle(turn);
//   }

//   @DisplayName("Should call the correct handle method do DefenseMoveState")
//   @Test
//   public void testMovement_DefenseState() {
//     turn.setState(defenseMoveState);
//     turn.movement(attackPlayer, defensePlayer);
//     Mockito.verify(defenseMoveState, Mockito.times(1)).handle(turn);
//   }

//   @DisplayName("Should call the correct handle method do DemageMoveState")
//   @Test
//   public void testMovement_DemageMoveState() {
//     turn.setState(demageMoveState);
//     turn.movement(attackPlayer, defensePlayer);
//     Mockito.verify(demageMoveState, Mockito.times(1)).handle(turn, attackPlayer, defensePlayer);
//   }
// }
