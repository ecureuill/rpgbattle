package com.ecureuill.rpgbattle.infrastructure.repositories;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecureuill.rpgbattle.domain.battle.Battle;
import com.ecureuill.rpgbattle.domain.battle.Stage;

public interface BattleRepository extends JpaRepository<Battle, UUID> {

  @Query(nativeQuery = true, value = "SELECT b.* FROM battles b INNER JOIN battles_players bp ON b.id = bp.battle_id INNER JOIN players p ON bp.players_user_name = p.user_name WHERE stage = :stage AND (p.user_name = :player1 OR p.user_name = :player2) GROUP BY b.id, b.stage HAVING COUNT(DISTINCT p.user_name) = 2;")
  List<Battle> findAllByStageAndPlayerOneAndPlayerTwo(@Param("stage") int stage, @Param("player1") String player1, @Param("player2") String player2);

  @Query(nativeQuery = true, value = "SELECT b.*  FROM battles b INNER JOIN battles_players bp ON b.id = bp.battle_id INNER JOIN players p ON bp.players_user_name = p.user_name WHERE (p.user_name = :player1 OR p.user_name = :player2) GROUP BY b.id HAVING COUNT(DISTINCT p.user_name) = 2;")
  List<Battle> findAllByPlayerOneAndPlayerTwo(@Param("player1") String player1, @Param("player2") String player2);

  List<Battle> findAllByStage(Stage stage);

  @Query("SELECT b FROM Battle b INNER JOIN b.players p WHERE p.userName = :player")
  List<Battle> findAllByPlayerOne(@Param("player") String player);
  
  @Query("SELECT b FROM Battle b INNER JOIN b.players p WHERE p.userName = :player")
  List<Battle> findAllByPlayerTwo(@Param("player") String player);
}
