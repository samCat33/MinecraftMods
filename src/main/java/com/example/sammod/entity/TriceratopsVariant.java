package com.example.sammod.entity;

import com.example.sammod.entity.client.TriceratopsModel;

import java.util.Arrays;
import java.util.Comparator;

public enum TriceratopsVariant {
    GRAY(0),
    GREEN(1);

    //This stores the enum values into an array
    private static final TriceratopsVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(TriceratopsVariant::getId)).toArray(TriceratopsVariant[]::new);

    private final int id;

    TriceratopsVariant(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    //This allows us to access a certain enum value by using the array
    public static TriceratopsVariant byId(int id){
        return BY_ID[id % BY_ID.length];
    }
}
