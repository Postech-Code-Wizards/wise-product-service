package br.com.wise.commerce.product.application.controller.exceptions;

public class ProductException extends RuntimeException {
    public ProductException(String message) {
        super(message);
    }
}
