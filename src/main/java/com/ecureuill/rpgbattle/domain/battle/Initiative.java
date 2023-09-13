package com.ecureuill.rpgbattle.domain.battle;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Initiative {
  private Integer playerOneDiceValue;
  private Integer playerTwoDiceValue;
  @Transient
  private String status;
  @Transient
  private Player player;

  public String getStatus(){
    if(playerOneDiceValue > playerTwoDiceValue){
      return "player one has the initiative";
    }
    else if(playerOneDiceValue < playerTwoDiceValue){
      return "player two has the initiative";
    }
    else {
      return "tie";
    }
  }
}
