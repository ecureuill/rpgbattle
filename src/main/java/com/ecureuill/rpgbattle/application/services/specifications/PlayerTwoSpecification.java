package com.ecureuill.rpgbattle.application.services.specifications;

import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import com.ecureuill.rpgbattle.domain.battle.Battle;
import com.ecureuill.rpgbattle.infrastructure.repositories.BattleRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PlayerTwoSpecification implements QueryParamsSpecification {

  private final BattleRepository battleRepository;

  @Override
  public Boolean isSatisfiedBy(MultiValueMap<String, String> queryParams) {
    return queryParams.size() == 1 && queryParams.containsKey("playerTwo");
  }

  @Override
  public List<Battle> getResults(MultiValueMap<String, String> queryParams) {
    String value = queryParams.getFirst("playerTwo");
    return battleRepository.findAllByPlayerTwo(value);
  }
  
}
