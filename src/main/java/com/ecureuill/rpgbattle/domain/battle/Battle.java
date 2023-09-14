package com.ecureuill.rpgbattle.domain.battle;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.context.event.EventListener;
import com.ecureuill.rpgbattle.application.exceptions.PlayerNotFoundException;
import com.ecureuill.rpgbattle.domain.battle.events.TurnEvent;
import com.ecureuill.rpgbattle.domain.battle.states.battlestate.BattleState;
import com.ecureuill.rpgbattle.domain.battle.states.battlestate.CreatedBattleState;
import com.ecureuill.rpgbattle.domain.battle.states.battlestate.EndBattleState;
import com.ecureuill.rpgbattle.domain.battle.states.battlestate.InitiativeBattleState;
import com.ecureuill.rpgbattle.domain.battle.states.battlestate.NotCreatedBattleState;
import com.ecureuill.rpgbattle.domain.battle.states.battlestate.TurnsBattleState;
import com.ecureuill.rpgbattle.domain.battle.strategies.turnstrategy.EventStrategyManager;
import com.ecureuill.rpgbattle.domain.battle.strategies.turnstrategy.TurnEventStrategy;
import com.ecureuill.rpgbattle.domain.character.Character;
import com.ecureuill.rpgbattle.domain.dice.Dice;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "battles")
@Data
@AllArgsConstructor
public class Battle {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private Stage stage;
  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "battle")
  private List<PlayerBattle> players;
  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<Turn> turns;  
  private UUID currentTurnId;
  private Integer turnsSequence = 0; 
  private Integer playerTurn;
  @CreationTimestamp
  private LocalDateTime startTime;
  private LocalDateTime lastTurnTime;
  private LocalDateTime endTime;
  @Transient
  private Dice dice;
  @Transient
  private EventStrategyManager eventStrategyManager;
  @Transient
  private BattleState state;
  @Embedded
  private Initiative initiative;

  public Optional<Turn> getCurrentTurn() {
    return turns.stream().filter(item -> item.getId().equals(currentTurnId)).findFirst();
  }

  public void setState(BattleState state) {
    this.state = state;
    if (state instanceof NotCreatedBattleState) {
      this.stage = Stage.NOT_CREATED;
    } else if (state instanceof CreatedBattleState) {
      this.stage = Stage.CHARACTER_SELECTION;
    } else if (state instanceof InitiativeBattleState) {
      this.stage = Stage.INITIATIVE;
    } else if (state instanceof TurnsBattleState) {
      this.stage = Stage.TURNS;
    } else if (state instanceof EndBattleState) {
      this.stage = Stage.END;
    }
  }

  @PostLoad
  void fillTransient() {
    if(stage != null){
      switch (stage) {
        case NOT_CREATED:
          state = new NotCreatedBattleState();
          break;
        case CHARACTER_SELECTION:
          state = new CreatedBattleState();
          break;
        case INITIATIVE:
          state = new InitiativeBattleState();
          break;
        case TURNS:
          state = new TurnsBattleState();
          break;
        case END:
          state = new EndBattleState();
          break;
        default:
          break;
      }
    }
  }

  public Battle() {
    this.stage = Stage.CHARACTER_SELECTION;
    this.startTime = LocalDateTime.now();
    this.turns = new ArrayList<>();
    this.currentTurnId = null;
    this.dice = new Dice();
    this.state = new NotCreatedBattleState();
    this.initiative = new Initiative();
    this.eventStrategyManager = new EventStrategyManager();
  }

  public void addCharacter(String username, Character character) {
    Player player = findPlayerByUsername(username);
    player.setCharacter(character);
    activeInitiative();
  }

  private Player findPlayerByUsername(String username) {
    return players.stream()
      .map(PlayerBattle::getPlayer)
      .filter(player -> player.getUsername().equals(username))
      .findFirst()
      .orElseThrow();
  }

  private void activeInitiative(){
    if(players.stream().map(PlayerBattle::getPlayer).anyMatch(player -> player.getCharacter() == null)) {
      return;
    }
    this.stage = Stage.INITIATIVE;
  } 

  public Player initiative(){
    final int INITIATIVE_DICE_FACES = 20;
    this.dice.setFaces(INITIATIVE_DICE_FACES);
    Integer playerOneDiceValue = this.dice.roll();
    Integer playerTwoDiceValue = this.dice.roll();

    if(playerOneDiceValue.equals(playerTwoDiceValue)) {
      return null;
    }
    
    this.stage = Stage.TURNS;

    if (playerOneDiceValue > playerTwoDiceValue) {
      return players.get(0).getPlayer();
    } else {
      return players.get(1).getPlayer();
    }
  }

  public void addTurn(Turn turn) {
    this.turns.add(turn);
    this.lastTurnTime = LocalDateTime.now();
  }
  
  @PrePersist
  protected void onCreate() {
    this.startTime = LocalDateTime.now();
  }

  @EventListener
  public void onTurnEvent(TurnEvent turnEvent) {
    TurnEventStrategy strategy = eventStrategyManager.getStrategy(turnEvent);
    if(strategy != null){
      strategy.handleEvent(turnEvent, this);
    }
  }
  
  public void updatePlayer(Player updatedPlayer) {
    for (int i = 0; i < players.size(); i++) {
      PlayerBattle playerBattle = players.get(i);
      if (playerBattle.getPlayer().getUsername().equals(updatedPlayer.getUsername())) {
        players.set(i, playerBattle.updatePlayer(updatedPlayer));
        break;
      }
    }
  }

  public Player findPlayerInBattle(String playerUsername) throws PlayerNotFoundException {
    return players.stream()
      .map(PlayerBattle::getPlayer)
      .filter(p -> p.getUsername().equals(playerUsername))
      .findFirst()
      .orElseThrow(() -> new PlayerNotFoundException(playerUsername));
  }
}

