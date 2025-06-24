package br.com.wise.commerce.product.application.dtos.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ProductRequestTest {
    private Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldPassValidationWithValidData() {
        var request = new ProductRequest("Name", "Desc", "ALIMENTOS", 10.0);

        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(request);

        assertThat(violations).isEmpty();
    }

    @Test
    void shouldFailValidationWithBlankName() {
        var request = new ProductRequest("", "Desc", "ALIMENTOS", 10.0);

        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(request);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("name"));
    }

    @Test
    void shouldFailValidationWithInvalidEnum() {
        var request = new ProductRequest("Name", "Desc", "INVALID_CATEGORY", 10.0);

        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(request);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("category"));
    }

    @Test
    void shouldFailValidationWithNegativePrice() {
        var request = new ProductRequest("Name", "Desc", "ALIMENTOS", -5.0);

        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(request);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("price"));
    }

}