package com.fernando.book_service.controllers;


import com.fernando.book_service.models.Book;
import com.fernando.book_service.models.Cambio;
import com.fernando.book_service.proxies.CambioProxy;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(BookController.class);


    @Autowired
    private CambioProxy proxy;

    @GetMapping(path = "/{id}/{currency}")
    @Operation(summary = "Find specific book by your id")
    public Book findById(
            @PathVariable("id") long id,
            @PathVariable("currency") String currency
    ){
        log.info("Starting request process");

        var book = Book.builder()
                    .id(id)
                    .currency(currency)
                    .build();

        var cambio = proxy.getCambio(book.getPrice(), "USD", currency);

        book.setPrice(cambio.getConvertedValue());

        log.info("Ending request process");
        return book;
    }
}
