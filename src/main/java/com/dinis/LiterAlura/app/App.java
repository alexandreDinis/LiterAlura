package com.dinis.LiterAlura.app;

import com.dinis.LiterAlura.service.BookServer;

import java.util.Scanner;

public class App {


    private BookServer service = new BookServer();

    private Scanner input = new Scanner(System.in);

    public void start() {

        System.out.println("Digite o titulo do livro que deseja buscar: ");
        var title = input.nextLine();
        service.searchBookWeb(title);
    }
}
