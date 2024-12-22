package com.luiscrsarmento.catalog.services;

import com.luiscrsarmento.catalog.dto.CategoryDTO;
import com.luiscrsarmento.catalog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public List<CategoryDTO> findAll(){
        return repository.findAll().stream().map(CategoryDTO::new).toList();
    }
}
