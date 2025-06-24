package br.com.wise.commerce.product.application.config.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class EnumValueValidatorTest {
    private EnumValueValidator validator;
    private ConstraintValidatorContext context;

    enum TestEnum {
        VALUE_ONE, VALUE_TWO
    }

    @BeforeEach
    void setUp() {
        validator = new EnumValueValidator();
        context = mock(ConstraintValidatorContext.class);
    }

    @Test
    void shouldAcceptValidValue() {
        EnumValue annotation = createAnnotation(false);
        validator.initialize(annotation);

        assertThat(validator.isValid("VALUE_ONE", context)).isTrue();
        assertThat(validator.isValid("VALUE_TWO", context)).isTrue();
    }

    @Test
    void shouldRejectInvalidValue() {
        EnumValue annotation = createAnnotation(false);
        validator.initialize(annotation);

        assertThat(validator.isValid("INVALID", context)).isFalse();
    }

    @Test
    void shouldIgnoreCaseIfConfigured() {
        EnumValue annotation = createAnnotation(true);
        validator.initialize(annotation);

        assertThat(validator.isValid("value_one", context)).isTrue();
        assertThat(validator.isValid("VaLuE_TwO", context)).isTrue();
    }

    @Test
    void shouldRejectIfCaseDoesNotMatch() {
        EnumValue annotation = createAnnotation(false);
        validator.initialize(annotation);

        assertThat(validator.isValid("value_one", context)).isFalse();
    }

    @Test
    void shouldAcceptNullValue() {
        EnumValue annotation = createAnnotation(false);
        validator.initialize(annotation);

        assertThat(validator.isValid(null, context)).isTrue();
    }

    // Mock annotation manually
    private EnumValue createAnnotation(boolean ignoreCase) {
        return new EnumValue() {
            @Override
            public Class<? extends java.lang.annotation.Annotation> annotationType() {
                return EnumValue.class;
            }

            @Override
            public String message() {
                return "Invalid enum";
            }

            @Override
            public Class<?>[] groups() {
                return new Class[0];
            }

            @Override
            public Class<? extends jakarta.validation.Payload>[] payload() {
                return new Class[0];
            }

            @Override
            public Class<? extends Enum<?>> enumClass() {
                return TestEnum.class;
            }

            @Override
            public boolean ignoreCase() {
                return ignoreCase;
            }
        };
    }
}