# Food Planner

### Description du projet:

#### Application de planification de menus hebdomadaires et d'achats

- Générer des menus en fonction des préférences alimentaires, de l'apport nutritionnel et des restrictions alimentaires.
- Créer une liste de courses automatisée basée sur les menus 

&rarr;  API nutritionnelles, stockage local, génération automatique de listes de courses

# UserData:
- Nom d'utilisateur
- Email
- Mot de passe
- Age
- Sexe : femme / homme
- Préférences alimentaires
- Restrictions
- Poids (kg)
- Taille (cm)


## APIs utilisées
### TheMealDB
Bunch of recipes with their description, picture, video to follow along, ingredients and their respective quantities
### Spoonacular API
Gives nutrition facts about a recipe (calories, fats, carbs and proteins)
### OpenFoodFacts
Gives nutritional facts about a product from it's barcode

## JSON retourné par notre serveur
``` 
{
    id: str,
    name: str,
    category: str,
    area: str,
    tags: [str],
    instructions: str,
    ingredients: [
        {
            ingredient: str,
            measure: str
        }
    ],
    calories: int,
    fats: int,
    carbs: int,
    proteins: int,
    photo: str,
    ytVideo: str
}
```
## Formule de calcul de BMR (basal metabolic rate)

*Mifflin-St Jeor Equation*:

    Hommes: BMR = 10W + 6.25H - 5A + 5
    Femmes: BMR = 10W + 6.25H - 5A - 161

**W**: poids en kg,   **H**: taille en cm,   **A**: âge


### Usage de la classe MealService

```
MealService mealService = new MealService(this);
//....
btnEvent.setOnClickListener(v -> {
    mealService.getMealById("53047", new RequestListener<MealModel>() {
        @Override
        public void onError(String message) {
            Log.e("Volley", "Error while requesting meal by ID");
        }
        @Override
        public void onResponse(MealModel meal) {
            try {
                Log.d("Volley", "Response: " + meal);
            } catch (Exception e) {
                Log.e("Volley", "Error while parsing response");
                throw new RuntimeException(e);
            }
        }
    });

    mealService.getMeals(new RequestListener<ArrayList<MealModel>>() {
        @Override
        public void onError(String message) {
            Log.e("Volley", "Error while requesting meals");
        }
        @Override
        public void onResponse(ArrayList<MealModel> response) {
            try {
                Log.d("Volley", "Response: " + response.get(0));
            } catch (Exception e) {
                Log.e("Volley", "Error while parsing response");
                throw new RuntimeException(e);
            }
        }
    });
});
```