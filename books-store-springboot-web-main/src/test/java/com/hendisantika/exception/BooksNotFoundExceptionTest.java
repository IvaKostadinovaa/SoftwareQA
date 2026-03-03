package com.hendisantika.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class BooksNotFoundExceptionTest {

    @Test
    void testExceptionMessage() {
        String msg = "Book not found!";
        BooksNotFoundException ex = assertThrows(BooksNotFoundException.class, () -> {
            throw new BooksNotFoundException(msg);
        });
        assertEquals(msg, ex.getMessage());
    }

    @Test
    void testSetterGetter() {
        BooksNotFoundException ex = new BooksNotFoundException("Initial message");
        ex.setMessage("Updated message");
        assertEquals("Updated message", ex.getMessage());
    }
}