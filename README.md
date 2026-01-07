# Food Planner

### Description du projet:

#### Application de planification de menus hebdomadaires et d'achats

- Générer des menus en fonction des préférences alimentaires, de l'apport nutritionnel et des restrictions alimentaires.
- Créer une liste de courses automatisée basée sur les menus 

&rarr;  API nutritionnelles, stockage local, génération automatique de listes de courses


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
    instructions: str,
    tags: [str],
    photo: str,
    ytVideo: str,
    ingredients: [
        {
            ingredient: str,
            measure: str
        }
    ],
    calories: int,
    fats: int,
    carbs: int,
    proteins: int
}
```
## Formule de calcul de BMR (basal metabolic rate)

*Mifflin-St Jeor Equation*:

    Hommes: BMR = 10W + 6.25H - 5A + 5
    Femmes: BMR = 10W + 6.25H - 5A - 161

**W**: poids en kg,   **H**: taille en cm,   **A**: âge