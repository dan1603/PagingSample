package com.kalashnyk.denys.pagingsample.utils;

import com.kalashnyk.denys.pagingsample.repository.database.entities.UserEntity;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Constants {

    public static final String USERS_ARRAY_DATA_TAG = "records";
    public static final Type USER_ARRAY_LIST_CLASS_TYPE = (new ArrayList<UserEntity>()).getClass();
    public static final String API_BASE_URL = "https://dev.moroz.cc/";
    public static final String ORDER_TYPE = "id";

    public static final int LOADING_PAGE_SIZE = 10;

    public static final String DATA_BASE_NAME = "users.db";
    public static final int NUMBERS_OF_THREADS = 3;
}
