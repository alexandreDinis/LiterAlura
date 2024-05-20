package com.dinis.LiterAlura.service;



public class BookServer {



    private final String URL = "https://gutendex.com/books?search=";
    private ApiClient apiClient = new ApiClient();
    private DataConverter dataConverter = new DataConverter();



    public void searchBookWeb(String title) {

        var path = URL + title.replace(" ","+" );
        var json = apiClient.fetchDataFromApi(path);

        System.out.println(json);


    }

}
