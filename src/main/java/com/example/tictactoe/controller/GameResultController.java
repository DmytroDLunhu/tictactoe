package com.example.tictactoe.controller;

import com.example.tictactoe.model.GameResult;
import com.example.tictactoe.repository.GameResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/results")
public class GameResultController {

    @Autowired
    private GameResultRepository repository;

    @PostMapping
    public GameResult saveResult(@RequestBody GameResult result) {
        return repository.save(result);
    }

    @GetMapping
    public List<GameResult> getAllResults() {
        return repository.findAll();
    }
}
