package com.luiscrsarmento.catalog.services;

import com.luiscrsarmento.catalog.dto.RoleDTO;
import com.luiscrsarmento.catalog.dto.UserDTO;
import com.luiscrsarmento.catalog.dto.UserInsertDTO;
import com.luiscrsarmento.catalog.dto.UserUpdateDTO;
import com.luiscrsarmento.catalog.entities.Role;
import com.luiscrsarmento.catalog.entities.User;
import com.luiscrsarmento.catalog.repositories.RoleRepository;
import com.luiscrsarmento.catalog.repositories.UserRepository;
import com.luiscrsarmento.catalog.services.exceptions.DatabaseException;
import com.luiscrsarmento.catalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(Pageable pageable){
        return repository.findAll(pageable).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id){
        User entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id " + id + " not found"));
        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO insert(UserInsertDTO dto){
        User entity = new User();
        copyDtoToEntity(dto, entity);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        return new UserDTO(repository.save(entity));
    }

    @Transactional
    public UserDTO update(Long id, UserUpdateDTO dto){
        try{
            User entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            return new UserDTO(repository.save(entity));
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

    private void copyDtoToEntity(UserDTO dto, User entity){
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());

        entity.getRoles().clear();
        for(RoleDTO roleDto : dto.getRoles()){
            Role role = roleRepository.getReferenceById(roleDto.getId());
            entity.getRoles().add(role);
        }
    }
}
