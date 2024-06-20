package com.example.tictactoe.service;

import com.example.tictactoe.model.GameResult;
import com.example.tictactoe.repository.GameResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicTacToeAppService {

    @Autowired
    private GameResultRepository repository;

    public GameResult saveResult(GameResult result) {
        return repository.save(result);
    }

    public List<GameResult> getAllResults() {
        return repository.findAll();
    }
}
