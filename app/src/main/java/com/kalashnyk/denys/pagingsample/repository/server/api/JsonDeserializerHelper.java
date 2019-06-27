package com.kalashnyk.denys.pagingsample.repository.server.api;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.kalashnyk.denys.pagingsample.utils.Constants;
import com.kalashnyk.denys.pagingsample.repository.database.entities.UserEntity;

import java.lang.reflect.Type;
import java.util.ArrayList;

class JsonDeserializerHelper implements JsonDeserializer {

    private static String TAG = JsonDeserializerHelper.class.getSimpleName();

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ArrayList<UserEntity> users = null;
        try {
            JsonObject jsonObject = json.getAsJsonObject();
            JsonArray usersJsonArray = jsonObject.getAsJsonArray(Constants.USERS_ARRAY_DATA_TAG);
            users = new ArrayList<>(usersJsonArray.size());
            for (int i = 0; i < usersJsonArray.size(); i++) {
                UserEntity dematerialized = context.deserialize(usersJsonArray.get(i), UserEntity.class);
                users.add(dematerialized);
            }
        } catch (JsonParseException e) {
            Log.e(TAG, String.format("Could not deserialize UserEntity element: %s", json.toString()));
            Log.e(TAG, e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return users;
    }
}
