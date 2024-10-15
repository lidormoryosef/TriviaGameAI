package com.example.demo.repo;

import com.example.demo.model.Attempt;
import com.example.demo.model.QuestionsResponse;
import com.example.demo.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AttemptsRepository extends CrudRepository<Attempt, String> {
    List<Attempt> findAllByUsernameAndTitle(String username, String title);
}
