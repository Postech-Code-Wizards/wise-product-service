package br.com.wise.commerce.product.application.dtos.request;

import br.com.wise.commerce.product.application.configuration.validation.EnumValue;
import br.com.wise.commerce.product.domain.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductRequest(
        @NotBlank String name,
        @NotBlank String description,
        @NotBlank @EnumValue(enumClass = Category.class, ignoreCase = true) String category,
        @NotNull @Positive Double price
) {
}
