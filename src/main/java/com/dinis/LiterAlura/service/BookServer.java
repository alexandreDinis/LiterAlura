package com.dinis.LiterAlura.service;


import com.dinis.LiterAlura.model.Author;
import com.dinis.LiterAlura.model.Book;
import com.dinis.LiterAlura.model.BooksData;
import com.dinis.LiterAlura.repository.AuthorRepository;
import com.dinis.LiterAlura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServer {


    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;



    private final String URL = "https://gutendex.com/books?search=";
    private ApiClient apiClient = new ApiClient();
    private DataConverter dataConverter = new DataConverter();



    public void searchBookWeb(String title) {
        var path = URL + title.replace(" ", "+");
        var json = apiClient.fetchDataFromApi(path);
        BooksData booksData = dataConverter.convertJsonToObject(json, BooksData.class);

        // Processar apenas o primeiro livro da lista
        if (!booksData.results().isEmpty()) {
            var apiBook = booksData.results().get(0);

            List<Author> authors = apiBook.authors().stream()
                    .map(apiAuthor -> {
                        List<Author> existingAuthors = authorRepository.findByName(apiAuthor.name());
                        if (existingAuthors.isEmpty()) {
                            Author author = new Author();
                            author.setName(apiAuthor.name());
                            author.setBirthYear(apiAuthor.birthYear());
                            author.setDeathYear(apiAuthor.deathYear());
                            return authorRepository.save(author);
                        } else {
                            return existingAuthors.get(0);
                        }
                    })
                    .distinct()
                    .collect(Collectors.toList());

            // Mapear autores para autores do banco de dados
            List<Author> bookAuthors = authors.stream()
                    .map(author -> authorRepository.findByName(author.getName()).get(0))
                    .collect(Collectors.toList());

            Book book = new Book();
            book.setTitle(apiBook.title());
            book.setAuthors(bookAuthors);
            book.setLanguages(apiBook.languages());
            book.setDownload(apiBook.download());

            bookRepository.save(book);

            // Exibir informações do livro salvo
            System.out.println("Title: " + book.getTitle());
            System.out.println("Authors: " + book.getAuthors().stream().map(Author::getName).collect(Collectors.joining(", ")));
            System.out.println("Languages: " + String.join(", ", book.getLanguages()));
            System.out.println("Download Count: " + book.getDownload());
            System.out.println();
        }
    }
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}


