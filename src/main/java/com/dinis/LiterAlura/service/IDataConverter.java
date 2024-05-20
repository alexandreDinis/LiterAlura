package com.dinis.LiterAlura.service;

import java.util.List;

public interface IDataConverter {

     <T> T convertJsonToObject(String json, Class<T> clazz);

    <T> List<T> convertJsonToList(String json, Class<T> clazz);
}
