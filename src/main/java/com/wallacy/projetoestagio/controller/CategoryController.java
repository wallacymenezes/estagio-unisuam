package com.wallacy.projetoestagio.controller;

import com.wallacy.projetoestagio.dto.CategoryDTO;
import com.wallacy.projetoestagio.mapper.CategoryMapper;
import com.wallacy.projetoestagio.model.Category;
import com.wallacy.projetoestagio.model.User;
import com.wallacy.projetoestagio.repository.CategoryRepository;
import com.wallacy.projetoestagio.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryController(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    // Listar todas as categorias (sem autenticação) - retorna todas as categorias da base
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAll() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> dtos = CategoryMapper.toDTOList(categories);
        return ResponseEntity.ok(dtos);
    }

    // Criar nova categoria (sem usuário associado, você pode adaptar para passar usuário no body se quiser)
    @PostMapping
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryDTO dto) {
        Optional<User> userOpt = userRepository.findByUserId(dto.getUserId());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().build(); // ou lançar exceção com mensagem clara
        }
        Category category = CategoryMapper.toEntity(dto, userOpt.get());
        Category saved = categoryRepository.save(category);
        return ResponseEntity.status(201).body(CategoryMapper.toDTO(saved));
    }


    @PutMapping
    public ResponseEntity<CategoryDTO> update(@Valid @RequestBody CategoryDTO dto) {
        Optional<Category> categoryOpt = categoryRepository.findById(dto.getId());
        if (categoryOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<User> userOpt = userRepository.findByUserId(dto.getUserId());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Category category = categoryOpt.get();

        Category updated = CategoryMapper.updateEntityFromDTO(category, dto);

        updated.setUser(userOpt.get());

        categoryRepository.save(updated);

        return ResponseEntity.ok(CategoryMapper.toDTO(updated));
    }

    // Deletar categoria (sem verificação de usuário)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            categoryRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Endpoint para trazer categorias de um usuário pelo ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CategoryDTO>> getByUserId(@PathVariable UUID userId) {
        List<Category> categories = categoryRepository.findByUserUserId(userId);
        List<CategoryDTO> dtoList = CategoryMapper.toDTOList(categories);
        return ResponseEntity.ok(dtoList);
    }
}



