package application.model;

import application.pojo.CardPOJO;

public class Card {

    private String title;
    private int date;
    private int position;
    private String urlImage;

    public Card(String title, int date, int position, String urlImage) {
        super();
        this.title = title;
        this.date = date;
        this.position = position;
        this.urlImage = urlImage;
    }

    public Card(CardPOJO cardP, int position) {
        super();
        this.title = cardP.name;
        this.date = cardP.date;
        this.position = position;
        this.urlImage = cardP.url;
    }

    public String getTitle() {
        return title;
    }

    public int getDate() {
        return date;
    }
    public void setDate(int date) {
        this.date = date;
    }
    public String getUrlImage() {
        return urlImage;
    }
    public void setUrl(String url) {
        this.urlImage = url;
    }


    public int getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Card [title=" + title + ", date=" + date + ", position=" + position + "]";
    }
    public void setNom(String nom) {
        this.title = nom;
    }


}