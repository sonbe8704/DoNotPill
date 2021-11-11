package com.example.donotpill;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.IDNA;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class PreferenceManager {
    public static final String PREFERENCES_NAME = "rebuild_preference";

    private static final String DEFAULT_VALUE_STRING = "";

    private static final boolean DEFAULT_VALUE_BOOLEAN = false;

    private static final int DEFAULT_VALUE_INT = -1;

    private static final long DEFAULT_VALUE_LONG = -1L;

    private static final float DEFAULT_VALUE_FLOAT = -1F;



    private static SharedPreferences getPreferences(Context context) {

        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

    }

    public void setRooms(Context context, String uid, ArrayList<Room> rooms){

        SharedPreferences prefs = getPreferences(context);

        SharedPreferences.Editor editor = prefs.edit();

        JSONArray a = new JSONArray();
        Gson gson =new GsonBuilder().create();
        for (int i = 0; i < rooms.size(); i++) {
            String string = gson.toJson(rooms.get(i),Room.class);
            a.put(string);
        }
        if (!rooms.isEmpty()) {
            editor.putString(uid, a.toString());
        } else {
            editor.putString(uid, null);
        }
        editor.apply();

        editor.commit();

    }

    public ArrayList<Room> getRooms(Context context, String uid){
        SharedPreferences prefs = getPreferences(context);
        String json = prefs.getString(uid, null);

        SharedPreferences.Editor editor = prefs.edit();
        Gson gson =new GsonBuilder().create();
        ArrayList<Room> rooms = new ArrayList<>();

        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                for (int i = 0; i < a.length(); i++) {
                    Room info= gson.fromJson(a.get(i).toString(),Room.class);
                    rooms.add(info);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return rooms;
    }

    public void setRanking(Context context, String id_room, ArrayList<Other> others){
        SharedPreferences prefs = getPreferences(context);

        SharedPreferences.Editor editor = prefs.edit();

        JSONArray a = new JSONArray();
        Gson gson =new GsonBuilder().create();

        for (int i = 0; i < others.size(); i++) {
            String string = gson.toJson(others.get(i), Other.class);
            a.put(string);
        }
        if (!others.isEmpty()) {
            editor.putString(id_room, a.toString());
        } else {
            editor.putString(id_room, null);
        }

        editor.apply();

        editor.commit();
    }



    public ArrayList<Other> getRanking(Context context,String id_room){
        SharedPreferences prefs = getPreferences(context);
        String json = prefs.getString(id_room, null);

        ArrayList<Other> others= new ArrayList<>();
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson =new GsonBuilder().create();

        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                for (int i = 0; i < a.length(); i++) {
                    Other other= gson.fromJson( a.get(i).toString() , Other.class);
                    others.add(other);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return others;
    }
    /**

     * 키 값 삭제

     * @param context

     * @param key

     */

    public static void removeKey(Context context, String key) {

        SharedPreferences prefs = getPreferences(context);

        SharedPreferences.Editor edit = prefs.edit();

        edit.remove(key);

        edit.commit();

    }



    /**

     * 모든 저장 데이터 삭제

     * @param context

     */

    public static void clear(Context context) {

        SharedPreferences prefs = getPreferences(context);

        SharedPreferences.Editor edit = prefs.edit();

        edit.clear();

        edit.commit();

    }
}
