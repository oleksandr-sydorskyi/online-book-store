package mate.academy.bookstore.service.category;

import java.util.List;
import mate.academy.bookstore.dto.category.CategoryDto;
import mate.academy.bookstore.dto.category.CreateCategoryRequestDto;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    List<CategoryDto> findAll(Pageable pageable);

    CategoryDto getById(Long id);

    CategoryDto save(CreateCategoryRequestDto createCategoryRequestDto);

    CategoryDto update(Long id, CreateCategoryRequestDto createCategoryRequestDto);

    void deleteById(Long id);
}
