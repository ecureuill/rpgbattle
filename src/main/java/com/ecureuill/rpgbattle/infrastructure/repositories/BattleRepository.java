package com.ecureuill.rpgbattle.infrastructure.repositories;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.ecureuill.rpgbattle.domain.battle.Battle;
import com.ecureuill.rpgbattle.domain.battle.Stage;

public interface BattleRepository extends JpaRepository<Battle, UUID> {

  @Query(value = "SELECT b.* FROM battles b JOIN player_battle pb1 ON b.id = pb1.battle_id JOIN players p1 ON pb1.player_id = p1.username JOIN player_battle pb2 ON b.id = pb2.battle_id JOIN players p2 ON pb2.player_id = p2.username WHERE p1.username =:player1 AND p2.username =:player2 AND b.stage =:stage", nativeQuery = true)
  List<Battle> findAllByStageAndPlayerOneAndPlayerTwo(@Param("stage") int stage, @Param("player1") String player1, @Param("player2") String player2);


  @Query(value = "SELECT b.* FROM battles b JOIN player_battle pb1 ON b.id = pb1.battle_id JOIN players p1 ON pb1.player_id = p1.username JOIN player_battle pb2 ON b.id = pb2.battle_id JOIN players p2 ON pb2.player_id = p2.username WHERE p1.username =:player1 AND p2.username =:player2", nativeQuery = true)
  List<Battle> findAllByPlayerOneAndPlayerTwo(@Param("player1") String playerA, @Param("player2") String playerB);

  List<Battle> findAllByStage(Stage stage);

  @Query(value = "SELECT b.* FROM battles b JOIN  player_battle pb  ON b.id=pb.battle_id JOIN players p ON pb.player_id= p.username WHERE p.username= :player2", nativeQuery = true)
  List<Battle> findAllByPlayerOne(@Param("player") String player);
}
