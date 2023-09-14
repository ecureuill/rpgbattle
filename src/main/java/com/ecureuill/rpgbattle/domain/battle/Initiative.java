package com.ecureuill.rpgbattle.domain.battle;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Embeddable
public class Initiative {
  private Integer playerOneDiceValue;
  private Integer playerTwoDiceValue;
  @Transient
  private String status;
  @Transient
  private Player player;

  public Initiative() {
    this.playerOneDiceValue = 0;
    this.playerTwoDiceValue = 0;
    this.status = "";
    this.player = null;
  }
  public String getStatus(){
    if(playerOneDiceValue == 0 || playerTwoDiceValue == 0)
    {
      return "Not determined";
    }
    if(playerOneDiceValue > playerTwoDiceValue){
      return "player one has the initiative";
    }
    else if(playerOneDiceValue < playerTwoDiceValue){
      return "player two has the initiative";
    }
    else {
      return "Not determined";
    }
  }
}
