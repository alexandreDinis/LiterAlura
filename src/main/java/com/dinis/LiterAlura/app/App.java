package com.dinis.LiterAlura.app;

import com.dinis.LiterAlura.messages.Message;
import com.dinis.LiterAlura.model.Author;
import com.dinis.LiterAlura.model.Book;
import com.dinis.LiterAlura.service.BookServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Scanner;

@Component
public class App {


    private BookServer service;
    private Scanner input;

    @Autowired
    public App(BookServer service) {
        this.service = service;
        this.input  = new Scanner(System.in);
    }


    public void start() {

        var option = -1;

        while (option !=0) {

            System.out.println(Message.MENU);
            System.out.println(Message.OP);

            try {
                option = input.nextInt();
            }catch (Exception e){
                System.out.println(Message.OP_ERROR);
            }finally {
                input.nextLine();
            }


            switch (option) {

                case 1:
                    System.out.println(Message.OP1);
                    var title = input.nextLine();
                    service.searchBookWeb(title);
                    break;
                case 2:
                    System.out.println(Message.OP2);
                    List<Book> books = service.getAllBooks();
                    books.forEach(System.out::println);
                    break;
                case 3:
                    System.out.println(Message.OP3);
                    List<Author> authors = service.getAllAuthor();
                    authors.forEach(author -> System.out.println(Message.AUTHORS + author.getName()+"\n"
                           +"Nasc.: " + author.getBirthYear()+"\n"+ "Morte: "+author.getDeathYear()));
                    break;
                case 4:
                    System.out.println(Message.OP4);
                    var year = input.nextInt();
                    input.nextLine();
                    System.out.println("Autores vivos no ano " + year + ":");
                   List<Author> author = service.getLivingAuthorsInYear(year);
                   author.forEach(a ->  System.out.println(Message.AUTHORS + a.getName()+"\n"
                            +"Nasc.: " + a.getBirthYear()+"\n"+ "Morte: "+a.getDeathYear()));
                    break;
                case 5:
                    System.out.println(Message.OP5);
                    var language = input.nextLine();
                    System.out.println("Livros no idioma " + language + ":");
                    service.getBooksByLanguage(language).forEach(System.out::println);
                    break;
                case 6:
                    System.out.println(Message.OP6);
                    DoubleSummaryStatistics stats = service.getDownloadStatistics();
                    System.out.println(Message.OP6);
                    System.out.println("Soma: " + stats.getSum());
                    System.out.println("Média: " + stats.getAverage());
                    System.out.println("Mínimo: " + stats.getMin());
                    System.out.println("Máximo: " + stats.getMax());
                    System.out.println("Contagem: " + stats.getCount());
                    break;
                case 7:
                    List<Book> top10Books = service.getTop10MostDownloadedBooks();
                    System.out.println(Message.TOP10);
                    top10Books.forEach(System.out::println);
                    break;
                case 8:
                    System.out.println(Message.FIND_BY_AUTHOR);
                    var name = input.nextLine();
                    service.searchAthorByName(name);
                    break;
                case 0:
                    System.out.println(Message.Exit);
                default:
                    System.out.println(Message.OP_ERROR);

            }
        }
    }
}
