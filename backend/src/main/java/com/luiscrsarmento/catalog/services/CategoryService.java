package com.luiscrsarmento.catalog.services;

import com.luiscrsarmento.catalog.dto.CategoryDTO;
import com.luiscrsarmento.catalog.repositories.CategoryRepository;
import com.luiscrsarmento.catalog.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll(){
        return repository.findAll().stream().map(CategoryDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id){
        return new CategoryDTO(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity not found")));
    }
}
