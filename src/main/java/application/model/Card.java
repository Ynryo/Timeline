package application.model;

import application.pojo.CardPOJO;

public class Card {

    private String title;
    private String date;
    private String urlImage;

    public Card(String title, String date, int position, String urlImage) {
        super();
        this.title = title;
        this.date = date;
        this.urlImage = urlImage;
    }

    public Card(CardPOJO cardP) {
        super();
        this.title = cardP.name;
        this.date = cardP.date;
        this.urlImage = cardP.url;
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




    @Override
    public String toString() {
        return "Card [title=" + title + ", date=" + date + ", position= ]";
    }
    public void setNom(String nom) {
        this.title = nom;
    }


}