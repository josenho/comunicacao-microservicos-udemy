package br.com.curso_udemy.product_api.modules.product.service;

import br.com.curso_udemy.product_api.config.exception.ValidationException;
import br.com.curso_udemy.product_api.modules.product.dto.CategoryRequest;
import br.com.curso_udemy.product_api.modules.product.dto.CategoryResponse;
import br.com.curso_udemy.product_api.modules.product.model.Category;
import br.com.curso_udemy.product_api.modules.product.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

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
}
