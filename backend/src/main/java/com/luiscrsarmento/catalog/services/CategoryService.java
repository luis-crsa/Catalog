package com.luiscrsarmento.catalog.services;

import com.luiscrsarmento.catalog.dto.CategoryDTO;
import com.luiscrsarmento.catalog.entities.Category;
import com.luiscrsarmento.catalog.repositories.CategoryRepository;
import com.luiscrsarmento.catalog.services.exceptions.DatabaseException;
import com.luiscrsarmento.catalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAllPaged(PageRequest pageRequest){
        return repository.findAll(pageRequest).map(CategoryDTO::new);
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id){
        return new CategoryDTO(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id " + id + " not found")));
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dto){
        Category entity = new Category();
        entity.setName(dto.getName());
        return new CategoryDTO(repository.save(entity));
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto){
        try{
            Category entity = repository.getReferenceById(id);
            entity.setName(dto.getName());
            return new CategoryDTO(repository.save(entity));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id " + id + " not found");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id){
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Id " + id + " not found");
        }
        try{
            repository.deleteById(id);
        }catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }
}
