package io.github.fernando;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class App
{
    @GetMapping("/hello")
    public String Hello(){
        return "Ola mundo";
    }

    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }
}
