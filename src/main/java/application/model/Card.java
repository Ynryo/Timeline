package application.model;

import application.pojo.CardPOJO;

public class Card {

    private String title;
    private String date;
    private String urlImage;
    private String description;

    public Card(String title, String date, String urlImage) {
        super();
        this.title = title;
        this.date = date;
        this.urlImage = urlImage;
    }

    public Card(String title, String date, String urlImage, String description) {
        super();
        this.title = title;
        this.date = date;
        this.urlImage = urlImage;
        this.description = description;
    }

    public Card(CardPOJO cardP) {
        super();
        this.title = cardP.title;
        this.date = cardP.year;
        this.urlImage = cardP.imageURL;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getUrlImage() {
        return urlImage;
    }
    public void setUrl(String url) {
        this.urlImage = url;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Card [title=" + title + ", year=" + date + ", position= ]";
    }
    public void setNom(String nom) {
        this.title = nom;
    }


}