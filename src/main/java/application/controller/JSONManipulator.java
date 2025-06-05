package application.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONManipulator {
    private final String path;
    private Gson gson = new Gson();
    private Map<String, Map<String, Object>> data;

    public JSONManipulator(String path) {
        this.path = path;
        loadData();
    }

    public Map<String, Object> getDeckInfo(String deckId) {
        Map<String, Object> deckInfo = new HashMap<>();
        
        try (Reader reader = new FileReader(path)) {
            Type type = new TypeToken<Map<String, Map<String, Object>>>() {}.getType();
            Map<String, Map<String, Object>> data = gson.fromJson(reader, type);
            
            if (data == null || !data.containsKey(deckId)) {
                return null;
            }

            return data.get(deckId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private void loadData() {
        try (Reader reader = new FileReader(path)) {
            Type type = new TypeToken<Map<String, Map<String, Object>>>() {}.getType();
            data = gson.fromJson(reader, type);
            if (data == null) data = new HashMap<>();
        } catch (Exception e) {
            e.printStackTrace();
            data = new HashMap<>();
        }
    }

    private void saveData() {
        try (Writer writer = new FileWriter(path)) {
            gson.toJson(data, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String getDeckName(String deckId) {
        Map<String, Object> deckInfo = getDeckInfo(deckId);
        return deckInfo != null ? (String) deckInfo.get("name") : null;
    }
    
    public String getDeckDescription(String deckId) {
        Map<String, Object> deckInfo = getDeckInfo(deckId);
        return deckInfo != null ? (String) deckInfo.get("description") : null;
    }
    
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getCards(String deckId) {
        Map<String, Object> deckInfo = getDeckInfo(deckId);
        System.out.println(deckInfo+"\n"+ deckInfo.containsKey("cards"));
        if (deckInfo != null && deckInfo.containsKey("cards")) {
            System.out.println("rentre dans le if getCards");
            return (List<Map<String, Object>>) deckInfo.get("cards");
        }
        return new ArrayList<>();
    }
    
    public Map<String, Object> getCard(String deckId, int index) {
        List<Map<String, Object>> cards = getCards(deckId);
        if (index >= 0 && index < cards.size()) {
            return cards.get(index);
        }
        return null;
    }
    
    public String getCardTitle(String deckId, int index) {
        Map<String, Object> card = getCard(deckId, index);
        return card != null ? (String) card.get("title") : null;
    }
    
    public Integer getCardYear(String deckId, int index) {
        Map<String, Object> card = getCard(deckId, index);
        return card != null && card.containsKey("year") ? ((Number) card.get("year")).intValue() : null;
    }
    
    public String getCardImageURL(String deckId, int index) {
        Map<String, Object> card = getCard(deckId, index);
        return card != null ? (String) card.get("imageURL") : null;
    }
    
    public String getCardDescription(String deckId, int index) {
        Map<String, Object> card = getCard(deckId, index);
        return card != null ? (String) card.get("description") : null;
    }
    
    public void addCard(String deckId, String title, int year, String imageURL, String description) {
        try (Reader reader = new FileReader(path)) {
            Type type = new TypeToken<Map<String, Map<String, Object>>>() {}.getType();
            Map<String, Map<String, Object>> data = gson.fromJson(reader, type);
            
            if (data == null) {
                data = new HashMap<>();
            }
            
            if (!data.containsKey(deckId)) {
                data.put(deckId, new HashMap<>());
                data.get(deckId).put("name", deckId);
                data.get(deckId).put("description", "");
                data.get(deckId).put("cards", new ArrayList<>());
            }
            
            Map<String, Object> deckInfo = data.get(deckId);
            List<Map<String, Object>> cards = (List<Map<String, Object>>) deckInfo.get("cards");
            
            Map<String, Object> newCard = new HashMap<>();
            newCard.put("title", title);
            newCard.put("year", year);
            newCard.put("imageURL", imageURL);
            if (description != null && !description.isEmpty()) {
                newCard.put("description", description);
            }
            
            cards.add(newCard);
            
            try (Writer writer = new FileWriter(path)) {
                new GsonBuilder().setPrettyPrinting().create().toJson(data, writer);
            }
            saveData();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void createDeck(String deckId, String name, String description) {
        try (Reader reader = new FileReader(path)) {
            Type type = new TypeToken<Map<String, Map<String, Object>>>() {}.getType();
            Map<String, Map<String, Object>> data = gson.fromJson(reader, type);
            
            if (data == null) {
                data = new HashMap<>();
            }
            
            Map<String, Object> deckInfo = new HashMap<>();
            deckInfo.put("name", name);
            deckInfo.put("description", description);
            deckInfo.put("cards", new ArrayList<>());
            
            data.put(deckId, deckInfo);
            
            try (Writer writer = new FileWriter(path)) {
                new GsonBuilder().setPrettyPrinting().create().toJson(data, writer);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void updateDeckInfo(String deckId, String name, String description) {
        try (Reader reader = new FileReader(path)) {
            Type type = new TypeToken<Map<String, Map<String, Object>>>() {}.getType();
            Map<String, Map<String, Object>> data = gson.fromJson(reader, type);
            
            if (data == null || !data.containsKey(deckId)) {
                return;
            }
            
            Map<String, Object> deckInfo = data.get(deckId);
            deckInfo.put("name", name);
            deckInfo.put("description", description);
            
            try (Writer writer = new FileWriter(path)) {
                new GsonBuilder().setPrettyPrinting().create().toJson(data, writer);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void removeCard(String deckId, int index) {
        try (Reader reader = new FileReader(path)) {
            Type type = new TypeToken<Map<String, Map<String, Object>>>() {}.getType();
            Map<String, Map<String, Object>> data = gson.fromJson(reader, type);
            
            if (data == null || !data.containsKey(deckId)) {
                return;
            }
            
            Map<String, Object> deckInfo = data.get(deckId);
            List<Map<String, Object>> cards = (List<Map<String, Object>>) deckInfo.get("cards");
            
            if (index >= 0 && index < cards.size()) {
                cards.remove(index);
                
                try (Writer writer = new FileWriter(path)) {
                    new GsonBuilder().setPrettyPrinting().create().toJson(data, writer);
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void updateCard(String deckId, int index, String title, int year, String imageURL, String description) {
        try (Reader reader = new FileReader(path)) {
            Type type = new TypeToken<Map<String, Map<String, Object>>>() {}.getType();
            Map<String, Map<String, Object>> data = gson.fromJson(reader, type);
            
            if (data == null || !data.containsKey(deckId)) {
                return;
            }
            
            Map<String, Object> deckInfo = data.get(deckId);
            List<Map<String, Object>> cards = (List<Map<String, Object>>) deckInfo.get("cards");
            
            if (index >= 0 && index < cards.size()) {
                Map<String, Object> card = cards.get(index);
                card.put("title", title);
                card.put("year", year);
                card.put("imageURL", imageURL);
                
                if (description != null && !description.isEmpty()) {
                    card.put("description", description);
                } else {
                    card.remove("description");
                }
                
                try (Writer writer = new FileWriter(path)) {
                    new GsonBuilder().setPrettyPrinting().create().toJson(data, writer);
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public int getCardCount(String deckId) {
        List<Map<String, Object>> cards = getCards(deckId);
        return cards.size();
    }
    
    public List<String> getAllDeckIds() {
        try (Reader reader = new FileReader(path)) {
            Type type = new TypeToken<Map<String, Map<String, Object>>>() {}.getType();
            Map<String, Map<String, Object>> data = gson.fromJson(reader, type);
            
            if (data == null) {
                return new ArrayList<>();
            }
            
            return new ArrayList<>(data.keySet());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}