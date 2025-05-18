package com.example.budgetmanagerio.service;



import com.example.budgetmanagerio.dto.CategoryDto;
import com.example.budgetmanagerio.exception.ResourceAlreadyExistsException;
import com.example.budgetmanagerio.exception.ResourceNotFoundException;
import com.example.budgetmanagerio.mapper.CategoryMapper;
import com.example.budgetmanagerio.model.Category;
import com.example.budgetmanagerio.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.toDtoList(categories);
    }

    @Transactional(readOnly = true)
    public CategoryDto getCategoryById(Long id) {
        Category category = findCategoryOrThrow(id);
        return categoryMapper.toDto(Optional.ofNullable(category));
    }

    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {
        if (categoryRepository.existsByName(categoryDto.getName())) {
            throw new ResourceAlreadyExistsException("Category with name " + categoryDto.getName() + " already exists");
        }
        
        Category category = categoryMapper.toEntity(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDto(Optional.of(savedCategory));
    }

    @Transactional
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category existingCategory = findCategoryOrThrow(id);
        

        if (!existingCategory.getName().equals(categoryDto.getName()) &&
                categoryRepository.existsByName(categoryDto.getName())) {
            throw new ResourceAlreadyExistsException("Category with name " + categoryDto.getName() + " already exists");
        }
        
        categoryMapper.updateCategoryFromDto(categoryDto, existingCategory);
        Category updatedCategory = categoryRepository.save(existingCategory);
        return categoryMapper.toDto(Optional.of(updatedCategory));
    }

    @Transactional
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    protected Category findCategoryOrThrow(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }
}