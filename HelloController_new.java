// ... (contenu existant jusqu'à la méthode addCardToGrid)

    private void addCardToGrid(String title, String date, String description, String imageUrl) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("card-view.fxml"));
            Parent card = loader.load();
            
            // Obtenir le contrôleur de la carte
            CardController cardController = loader.getController();
            
            // Configurer les données de la carte
            cardController.setCardData(title, date, description, imageUrl);
            
            // Générer un ID unique pour la carte
            String cardId = "card_" + System.currentTimeMillis() + "_" + cardCount++;
            cardController.setCardId(cardId);
            
            // Configurer l'action de suppression
            cardController.setOnDeleteAction(() -> {
                gridPane.getChildren().remove(card);
                if (deckActuel != null) {
                    deckActuel.supprimerCarte(cardId);
                    jsonManager.sauvegarderDeck(deckActuel);
                }
            });
            
            // Configurer l'action de modification
            cardController.setOnEditAction(() -> {
                // Remplir les champs avec les données de la carte
                NameField.setText(cardController.getTitle());
                dateField.setText(cardController.getDate());
                // Mettre à jour l'ID de la carte en cours de modification
                currentEditingCardId = cardController.getCardId();
            });
            
            // Ajouter la carte à la grille
            int column = cardCount % 3;
            int row = cardCount / 3;
            
            // S'assurer que la grille a assez de rangées
            if (row >= gridPane.getRowCount()) {
                gridPane.addRow(row);
            }
            
            // Ajouter la carte à la grille
            gridPane.add(card, column, row);
            
            // Sauvegarder la carte dans le deck
            if (deckActuel != null) {
                Carte nouvelleCarte = new Carte(cardId, title, description, date, imageUrl);
                deckActuel.ajouterCarte(nouvelleCarte);
                jsonManager.sauvegarderDeck(deckActuel);
            }
            
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la carte : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ... (reste du code existant)
    
    // Méthode appelée lors du clic sur le bouton "Ajouter une carte"
    @FXML
    private void handleAddCard(ActionEvent event) {
        String title = NameField.getText().trim();
        String date = dateField.getText().trim();
        String description = descArea.getText().trim();
        String imageUrl = urlField.getText().trim();
        
        if (!title.isEmpty() && !date.isEmpty()) {
            if (currentEditingCardId != null) {
                // Mettre à jour la carte existante
                updateExistingCard(currentEditingCardId, title, date, description, imageUrl);
                currentEditingCardId = null; // Réinitialiser l'ID de la carte en cours d'édition
            } else {
                // Ajouter une nouvelle carte
                addCardToGrid(title, date, description, imageUrl);
            }
            
            // Réinitialiser les champs
            clearFields();
        }
    }
    
    // Méthode pour mettre à jour une carte existante
    private void updateExistingCard(String cardId, String title, String date, String description, String imageUrl) {
        // Parcourir les cartes dans la grille pour trouver celle à mettre à jour
        for (var node : gridPane.getChildren()) {
            // Vérifier si ce nœud est une carte
            if (node.getUserData() != null && node.getUserData() instanceof CardController) {
                CardController cardController = (CardController) node.getUserData();
                if (cardId.equals(cardController.getCardId())) {
                    // Mettre à jour les données de la carte
                    cardController.setCardData(title, date, description, imageUrl);
                    
                    // Mettre à jour la carte dans le deck
                    if (deckActuel != null) {
                        Carte carteModifiee = new Carte(cardId, title, description, date, imageUrl);
                        deckActuel.mettreAJourCarte(cardId, carteModifiee);
                        jsonManager.sauvegarderDeck(deckActuel);
                    }
                    
                    break;
                }
            }
        }
    }
    
    // ... (reste du code existant)
}
