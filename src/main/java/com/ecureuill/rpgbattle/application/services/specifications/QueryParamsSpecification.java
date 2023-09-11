package com.ecureuill.rpgbattle.application.services.specifications;

import java.util.List;
import org.springframework.util.MultiValueMap;
import com.ecureuill.rpgbattle.domain.battle.Battle;

public interface QueryParamsSpecification {
  Boolean isSatisfiedBy(MultiValueMap<String, String> queryParams);
  List<Battle> getResults(MultiValueMap<String, String> queryParams);
}
