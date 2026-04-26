package com.fot.eventsystem.repository;

import com.fot.eventsystem.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Integer> {
}