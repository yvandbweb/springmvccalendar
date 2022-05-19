package com.example.webservfreedays.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.webservfreedays.entity.Translate;

@Repository
public interface TranslateRepository extends CrudRepository<Translate, Integer> {	

}
