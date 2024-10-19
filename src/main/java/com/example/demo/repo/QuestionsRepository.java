package com.example.demo.repo;

import com.example.demo.model.QuestionsResponse;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionsRepository extends CrudRepository<QuestionsResponse, String> {
    QuestionsResponse findByUsernameAndTitle(String username,String title);

    void deleteAllByUsernameAndTitle(String username, String title);
}
