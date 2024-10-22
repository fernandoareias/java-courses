package com.fernando.book_service.controllers;


import com.fernando.book_service.models.Book;
import com.fernando.book_service.models.Cambio;
import com.fernando.book_service.proxies.CambioProxy;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Tag(name = "Book endpoint")

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private CambioProxy proxy;

    @GetMapping(path = "/{id}/{currency}")
    @Operation(summary = "Find specific book by your id")
    public Book findById(
            @PathVariable("id") long id,
            @PathVariable("currency") String currency
    ){


        var book = Book.builder()
                    .id(id)
                    .currency(currency)
                    .build();

        var cambio = proxy.getCambio(book.getPrice(), "USD", currency);

        book.setPrice(cambio.getConvertedValue());


        return book;
    }
}
