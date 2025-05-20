package com.wallacy.projetoestagio.controller;

import com.wallacy.projetoestagio.dto.CategoryDTO;
import com.wallacy.projetoestagio.mapper.CategoryMapper;
import com.wallacy.projetoestagio.model.Category;
import com.wallacy.projetoestagio.model.User;
import com.wallacy.projetoestagio.repository.CategoryRepository;
import com.wallacy.projetoestagio.repository.UserRepository;
import com.wallacy.projetoestagio.util.TokenUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

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

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAll(JwtAuthenticationToken token) {
        Optional<User> user = TokenUtils.getUserFromToken(token);
        return user.map(value ->
                ResponseEntity.ok(
                        CategoryMapper.toDTOList(value.getCategories())
                )
        ).orElse(ResponseEntity.status(401).build());
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryDTO dto, JwtAuthenticationToken token) {
        Optional<User> user = TokenUtils.getUserFromToken(token);
        return user.map(u -> {
            Category saved = categoryRepository.save(CategoryMapper.toEntity(dto, u));
            return ResponseEntity.status(201).body(CategoryMapper.toDTO(saved));
        }).orElse(ResponseEntity.status(401).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, JwtAuthenticationToken token) {
        Optional<User> user = TokenUtils.getUserFromToken(token);
        if (user.isEmpty()) return ResponseEntity.status(401).build();

        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent() && category.get().getUser().equals(user.get())) {
            categoryRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(403).build();
    }
}
