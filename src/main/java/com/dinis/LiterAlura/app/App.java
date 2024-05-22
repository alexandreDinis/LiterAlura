package com.dinis.LiterAlura.app;

import com.dinis.LiterAlura.messages.Message;
import com.dinis.LiterAlura.model.Book;
import com.dinis.LiterAlura.service.BookServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
                    break;
                case 4:
                    System.out.println(Message.OP4);
                    break;
                case 5:
                    System.out.println(Message.OP5);
                case 0:
                    System.out.println(Message.SAIR);
                    break;
                default:
                    System.out.println(Message.OP_ERROR);

            }
        }
    }
}
