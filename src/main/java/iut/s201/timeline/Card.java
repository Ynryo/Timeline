package iut.s201.timeline;

public class Card {
    private String nom;
    private String date;
    private String url;
    private String description;

    // Constructeur par défaut (nécessaire pour JSON)
    public Card() {}

    // Constructeur avec paramètres
    public Card(String nom, String date, String url, String description) {
        this.nom = nom;
        this.date = date;
        this.url = url;
        this.description = description;
    }

    // Getters et Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Card{" +
                "nom='" + nom + '\'' +
                ", date='" + date + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}