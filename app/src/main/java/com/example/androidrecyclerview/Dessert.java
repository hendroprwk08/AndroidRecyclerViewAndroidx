package com.example.androidrecyclerview;

public class Dessert {
    String strMeal, strMealThumb, idMeal;

    public Dessert(String idMeal, String strMeal, String strMealThumb) {
        this.strMeal = strMeal;
        this.strMealThumb = strMealThumb;
        this.idMeal = idMeal;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public String getIdMeal() {
        return idMeal;
    }
}
