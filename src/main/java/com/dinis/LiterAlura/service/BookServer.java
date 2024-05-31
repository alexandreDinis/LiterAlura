package com.dinis.LiterAlura.service;


import com.dinis.LiterAlura.model.Author;
import com.dinis.LiterAlura.model.Book;
import com.dinis.LiterAlura.model.BooksData;
import com.dinis.LiterAlura.repository.AuthorRepository;
import com.dinis.LiterAlura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServer {


    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;



    private final String URL = "https://gutendex.com/books?search=";
    private final ApiClient apiClient = new ApiClient();
    private final DataConverter dataConverter = new DataConverter();



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

            System.out.println(book.toString());
        }
    }
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Author> getAllAuthor(){
        return authorRepository.findAll();
    }

    public List<Author> getLivingAuthorsInYear(int year) {
        return authorRepository.findByBirthYearLessThanEqualAndDeathYearGreaterThanEqual(year, year);
    }

    public List<Book> getBooksByLanguage(String language) {
        return bookRepository.findByLanguagesContaining(language);
    }

    public DoubleSummaryStatistics getDownloadStatistics() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .mapToDouble(Book::getDownload)
                .summaryStatistics();
    }

    public List<Book> getTop10MostDownloadedBooks() {
        return bookRepository.findAll().stream()
                .sorted((b1, b2) -> b2.getDownload().compareTo(b1.getDownload()))
                .limit(10)
                .collect(Collectors.toList());
    }

    public void searchAthorByName(String name) {
        List<Author> authors = authorRepository.findByNameContainingIgnoreCase(name);

        if (authors.isEmpty()) {
            System.out.println("Autor não encontrado");
        } else {
            authors.forEach(author -> System.out.println("*******Autor******\n" +
                    "Autor: " + author.getName()+"\n"+
                    "Nasc.: " + author.getBirthYear() + "\n" +
                    "Morte: " + author.getDeathYear()));
        }
    }
}


