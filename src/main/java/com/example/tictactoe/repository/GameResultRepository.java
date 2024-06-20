package com.example.tictactoe.repository;

import com.example.tictactoe.model.GameResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameResultRepository extends JpaRepository<GameResult, Long> {
}
