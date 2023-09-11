package com.ecureuill.rpgbattle.application.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecureuill.rpgbattle.application.dtos.BattleCreateRequest;
import com.ecureuill.rpgbattle.application.exceptions.InvalidBattleParametersException;
import com.ecureuill.rpgbattle.application.exceptions.PlayerNotFoundException;
import com.ecureuill.rpgbattle.domain.battle.Battle;
import com.ecureuill.rpgbattle.domain.user.User;
import com.ecureuill.rpgbattle.infrastructure.repositories.BattleRepository;
import com.ecureuill.rpgbattle.utils.DataFaker;
import com.ecureuill.rpgbattle.utils.DataFakerProvider;

@ExtendWith(MockitoExtension.class)
public class BattleServiceTest {
  @Mock
  private BattleRepository battleRepository;
  @Mock
  private UserService userService;
  @InjectMocks
  private BattleService battleService;
  
  @DisplayName("Should create a battle when valid data is provided")
  @Test
  void testCreateBattle() throws PlayerNotFoundException, InvalidBattleParametersException {
    DataFaker faker = DataFakerProvider.getInstace();
    BattleCreateRequest request = DataFakerProvider.getInstace().generateBattleCreateRequest();
    
    Mockito.when(userService.findByUsername(Mockito.anyString())).thenReturn(new User());
    Mockito.when(battleRepository.save(Mockito.any(Battle.class))).thenReturn(faker.generateBattle(true));
  
    var result = battleService.createBattle(request);

    Mockito.verify(userService, Mockito.times(2)).findByUsername(Mockito.anyString());
    Mockito.verify(battleRepository, Mockito.times(1)).save(Mockito.any(Battle.class));
    Assertions.assertNotNull(result);
  }

  @DisplayName("Should throw PlayerNotFoundException when invalid username is provided")
  @Test
  void testCreateBattle_PlayerNotFound() throws PlayerNotFoundException {
    BattleCreateRequest request = DataFakerProvider.getInstace().generateBattleCreateRequest();

    Mockito.when(userService.findByUsername(Mockito.anyString())).thenThrow(PlayerNotFoundException.class);

    Assertions.assertThrows(InvalidBattleParametersException.class, () -> battleService.createBattle(request));

    Mockito.verify(userService, Mockito.times(1)).findByUsername(Mockito.anyString());
    Mockito.verify(battleRepository, Mockito.times(0)).save(Mockito.any(Battle.class));
  
  }

  @DisplayName("Should throw InvalidBattleParametersException when playerOne and playerTwo are the same")
  @Test
  void testCreateBattle_InvalidBattleParameters() throws PlayerNotFoundException, InvalidBattleParametersException {
    BattleCreateRequest request = new BattleCreateRequest("username", "username");

    Assertions.assertThrows(InvalidBattleParametersException.class, () -> battleService.createBattle(request));

    Mockito.verify(userService, Mockito.times(0)).findByUsername(Mockito.anyString());
    Mockito.verify(battleRepository, Mockito.times(0)).save(Mockito.any(Battle.class));

  }
}
