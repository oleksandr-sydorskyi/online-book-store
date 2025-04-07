package mate.academy.bookstore.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateCategoryRequestDto {
    @NotBlank
    @Size(max = 255)
    private String name;
    @Size(max = 1000)
    private String description;
}
