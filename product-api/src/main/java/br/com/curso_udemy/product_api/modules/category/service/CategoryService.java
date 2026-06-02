package br.com.curso_udemy.product_api.modules.category.service;

import br.com.curso_udemy.product_api.config.exception.SuccessResponse;
import br.com.curso_udemy.product_api.config.exception.ValidationException;
import br.com.curso_udemy.product_api.modules.category.dto.CategoryRequest;
import br.com.curso_udemy.product_api.modules.category.dto.CategoryResponse;
import br.com.curso_udemy.product_api.modules.category.model.Category;
import br.com.curso_udemy.product_api.modules.category.repository.CategoryRepository;
import br.com.curso_udemy.product_api.modules.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductService productService;

    public CategoryResponse findByIdResponse(Integer id) {
        return CategoryResponse.of(findById(id));
    }

    public List<CategoryResponse> findAll() {
        return categoryRepository
                .findAll()
                .stream()
                .map(CategoryResponse::of)
                .collect(Collectors.toList());
    }

    public List<CategoryResponse> findByDescription(String description) {
        if (isEmpty(description)){
            throw new ValidationException("The category description must be informed.");
        }
        return categoryRepository
                .findByDescriptionIgnoreCaseContaining(description)
                .stream()
                .map(CategoryResponse::of)
                .collect(Collectors.toList());
    }

    public Category findById(Integer id){
        ValidateInformedId(id);
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new ValidationException("There's no category for the given ID."));
    }

    public CategoryResponse save(CategoryRequest request){
        ValidateCategoryNameInformed(request);
        var category = categoryRepository.save(Category.of(request));
        return CategoryResponse.of(category);
    }

    private void ValidateCategoryNameInformed (CategoryRequest request){
        if(isEmpty(request.getDescription())) {
            throw new ValidationException("The category description was not informed.");
        }
    }

    public SuccessResponse delete(Integer id){
        ValidateInformedId(id);
        if(productService.existByCategoryId(id)){
            throw new ValidationException("You cannot delete this category because its already defined by a product.");
        }
        categoryRepository.deleteById(id);
        return SuccessResponse.create("The category was deleted.");
    }

    private void ValidateInformedId(Integer id){
        if (isEmpty(id)){
            throw new ValidationException("The category ID must be informed.");
        }
    }
}
