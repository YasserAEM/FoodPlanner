package com.example.foodplanner;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    // Déclaration des vues
    private EditText etUsername, etEmail, etPassword, etAge, etWeight, etHeight;
    private RadioGroup rgGender;
    private Spinner spinnerPreference, spinnerAllergies;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etRegisterEmail);
        etPassword = findViewById(R.id.etRegisterPassword);
        etAge = findViewById(R.id.etAge);
        etWeight = findViewById(R.id.etWeight);
        etHeight = findViewById(R.id.etHeight);

        rgGender = findViewById(R.id.rgGender);

        spinnerPreference = findViewById(R.id.spinnerPreference);
        spinnerAllergies = findViewById(R.id.spinnerAllergies);

        btnRegister = findViewById(R.id.btnRegister);

        // 2. Configuration des Spinners
        setupSpinners();

        // 3. Gestion du clic sur le bouton S"inscrire
        btnRegister.setOnClickListener(v -> registerUser());
    }

    private void setupSpinners() {
        // --- Liste des Préférences Alimentaires  ---
        String[] preferences = {"Aucune", "Beef", "Breakfast", "Chicken", "Dessert", "Goat", "Lamb", "Miscellaneous", "Pasta",
        "Pork", "Seafood", "Side", "Starter", "Vegan", "Vegetarian"};

        ArrayAdapter<String> adapterPref = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, preferences);
        adapterPref.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPreference.setAdapter(adapterPref);

        // --- Liste des Allergies ---
        String[] allergies = {"Ackee", "All purpose flour", "All-purpose Seasoning", "Allspice", "Allspice Berries", "Almond Extract", "Almonds", "Anchovy Fillet", "Apple Cider Vinegar", "Apples", "Apricot", "Aubergine", "Avocado", "Baby Lettuce Leaves", "Baby New Potatoes", "Baby Pak Koi", "Baby Squid", "Bacon", "Bacon lardon", "Baguette", "Baked Beans", "Baking Powder", "Balsamic Vinegar", "Bamboo Shoot", "Banana", "Barbeque Sauce", "Basil", "Basil Leaves", "Basmati Rice", "Basmati rice", "Bay Leaf", "Bay Leaves", "Bay leaf", "Bean Sprouts", "Beef", "Beef Brisket", "Beef Cutlet", "Beef Fillet", "Beef Gravy", "Beef Shin", "Beef Stock", "Beef Stock Cubes", "Beef tomatoes", "Beetroot", "Bicarbonate Of Soda", "Birds-eye Chillies", "Biryani masala", "Black Beans", "Black Olives", "Black Pepper", "Black Pudding", "Black Treacle", "Black pepper", "Blackberries", "Blackcurrant Jam", "Blueberries", "Boiling Water", "Bok Choi", "Braeburn Apples", "Bramley Apples", "Bramley apples", "Brandy", "Bread", "Bread Flour", "Breadcrumbs", "Broad Beans", "Broccoli", "Brown Lentils", "Brown Rice", "Brown Rice Noodle", "Brown Sugar", "Brown sugar", "Buckwheat", "Bulgur Wheat", "Bun", "Butter", "Butter Beans", "Butternut Squash", "Cabbage", "Cabbage Leaves", "Cacao", "Can of chickpeas", "Candied Peel", "Canned tomatoes", "Cannellini Beans", "Canola Oil", "Capers", "Caraway Seed", "Cardamom", "Carrots", "Cashew Nuts", "Cashew nuts", "Caster Sugar", "Cayenne Pepper", "Cayenne pepper", "Celery", "Celery Salt", "Challots", "Cheddar Cheese", "Cheese", "Cheese Curds", "Cherry Tomatoes", "Chestnuts", "Chicken", "Chicken Bouillon Powder", "Chicken Breast", "Chicken Breasts", "Chicken Legs", "Chicken Liver", "Chicken Stock", "Chicken Stock Cube", "Chicken Thighs", "Chicken Wings", "Chicken drumsticks", "Chickpeas", "Chili Powder", "Chilli Bean Paste", "Chilli Flakes", "Chilli Powder", "Chilli powder", "Chimichurri sauce", "Chinese Cabbage", "Chinese Leaf", "Chinese Sesame Sauce", "Chinese broccoli", "Chinese five spice powder", "Chives", "Chocolate Chips", "Chopped Chive", "Chopped Parsley", "Chopped Tomatoes", "Chorizo", "Christmas Pudding", "Cider Vinegar", "Cilantro", "Cilantro Leaves", "Cinnamon", "Cinnamon Stick", "Cinnamon stick", "Clams", "Clear Honey", "Clotted Cream", "Cloves", "Coco Sugar", "Cocoa", "Cocoa Powder", "Coconut Flakes", "Coconut Milk", "Coconut cream", "Cod", "Colby Jack Cheese", "Cold Water", "Condensed Milk", "Cooked Beetroot", "Cooked Chestnut", "Cooking wine", "Coriander", "Coriander Leaves", "Coriander seeds", "Corn Flour", "Corn Tortillas", "Corned Beef", "Cornmeal", "Cornstarch", "Courgettes", "Couscous", "Cranberry", "Cream", "Cream Cheese", "Cream Of Tartar", "Creme Fraiche", "Crusty Bread", "Cucumber", "Cumin", "Cumin Seeds", "Cumin seeds", "Currants", "Curry Powder", "Custard", "Custard Powder", "Dark Chocolate", "Dark Rum", "Demerara Sugar", "Desiccated Coconut", "Diced Tomatoes", "Digestive Biscuits", "Dijon Mustard", "Dill", "Dill Pickles", "Doubanjiang", "Double Cream", "Dried Apricots", "Dried Fruit", "Dried Mint", "Dried Oregano", "Dried White Navy Beans", "Dried cranberries", "Dried white beans", "Dried white corn", "Dry White Wine", "Dry sherry", "Duck Legs", "Duck Sauce", "Dulce de leche", "Egg", "Egg Noodles", "Egg Plants", "Egg Roll Wrappers", "Egg Wash", "Egg White", "Egg Yolks", "Eggs", "Emmentaler Cheese", "English Mustard", "Extra Virgin Olive Oil", "Fast action yeast", "Feather blade beef", "Fennel", "Fennel Bulb", "Fennel Seeds", "Fermented Black Beans", "Feta", "Fettuccine", "Figs", "Fillet Of Steak", "Filo Pastry", "Fish Sauce", "Fish Stock", "Five Spice Powder", "Flaked Almonds", "Flat Rice Noodles", "Flour", "Floury Potatoes", "Free-range Egg, Beaten", "French Lentils", "Freshly Chopped Parsley", "Fromage Frais", "Frozen Mixed Berries", "Frozen Peas", "Frozen Seafood mix", "Fruit Mix", "Full fat yogurt", "Galangal", "Galangal Paste", "Garam Masala", "Garam masala", "Garlic", "Garlic Clove", "Garlic Granules", "Garlic Powder", "Garlic powder", "Gelatine Leafs", "German Sausages", "Ghee", "Ginger", "Ginger Cordial", "Ginger Paste", "Ginger garlic paste", "Ginger paste", "Glace Cherry", "Goats Cheese", "Gochujang", "Golden Caster Sugar", "Golden Syrup", "Goose Fat", "Gouda Cheese", "Graham Cracker Crumbs", "Grand Marnier", "Granulated Sugar", "Granulated sugar", "Grape Nut Cereal", "Greek Yogurt", "Greek yogurt", "Green Beans", "Green Chilli", "Green Olives", "Green Pepper", "Green chilli", "Ground Allspice", "Ground Almonds", "Ground Annatto", "Ground Beef", "Ground Cardomom", "Ground Cinnamon", "Ground Coriander", "Ground Cumin", "Ground Ginger", "Ground Oats", "Ground Pistachios", "Ground Pork", "Ground Sugar", "Gruyere", "Gruyere cheese", "Gruyère", "Haddock", "Ham", "Haricot Beans", "Harissa Spice", "Hazlenuts", "Heavy Cream", "Hispi (sweetheart) Cabbage", "Hoisin Sauce", "Honey", "Hot Chilli Powder", "Hotsauce", "Hummus", "Ice Cream", "Iceberg Lettuce", "Icing Sugar", "Italian Fennel Sausages", "Jalapeno", "Jam", "Jasmine Rice", "Jersey Royal Potatoes", "Jerusalem Artichokes", "Juniper Berries", "Kabanos Sausages", "Kabse Spice", "Kale", "Khus khus", "Kidney Beans", "Kielbasa", "King Prawns", "Kosher Salt", "Kosher salt", "Lamb", "Lamb Kidney", "Lamb Leg", "Lamb Loin Chops", "Lamb Mince", "Lamb Shoulder", "Lard", "Lasagne Sheets", "Lean Minced Steak", "Leek", "Lemon", "Lemon Juice", "Lemon Zest", "Lemongrass", "Lemongrass Stalks", "Lemons", "Lentils", "Lettuce", "Light Brown Soft Sugar", "Lime", "Lime Leaves", "Lime juice", "Linguine Pasta", "Macaroni", "Madras Paste", "Malt Vinegar", "Maple Syrup", "Marinated Tofu", "Marjoram", "Mars Bar", "Marzipan", "Mascarpone", "Massaman curry paste", "Mature Cheddar", "Mayonnaise", "Melted Butter", "Meringue Nests", "Milk", "Milk Chocolate", "Minced Beef", "Minced Pork", "Mincemeat", "Miniature Marshmallows", "Mint", "Mirin", "Mixed Beef Cuts", "Mixed Grain", "Mixed Peel", "Mixed Spice", "Mixed peppers", "Molasses", "Monkfish", "Monterey Jack Cheese", "Morcilla", "Mozzarella", "Mozzarella Balls", "Mung Bean Sprouts", "Muscovado Sugar", "Mushrooms", "Mussels", "Mustard", "Mustard Powder", "Naan Bread", "Napa Cabbage", "Natural Yoghurt", "New Potatoes", "Nutmeg", "Oats", "Oil", "Olive Oil", "Olive oil", "Onion", "Onion Salt", "Onions", "Orange", "Orange Blossom Water", "Orange Juice", "Orange Zest", "Oregano", "Oyster Mushrooms", "Oyster Sauce", "Paccheri Pasta", "Padron peppers", "Paella Rice", "Pak Choi", "Palm Sugar", "Paneer", "Paprika", "Parma ham", "Parmesan", "Parmesan Cheese", "Parmesan cheese", "Parmigiano-Reggiano", "Parsley", "Passata", "Passion Fruit Pulp", "Peaches", "Peanut Brittle", "Peanut Butter", "Peanut Cookies", "Peanut Oil", "Peanuts", "Pears", "Peas", "Pecan Nuts", "Pecorino", "Pepper", "Peppercorns", "Phyllo Dough", "Pickle Juice", "Pine Nuts", "Pineapple Chunks", "Pineapple Juice", "Pink Food Colouring", "Pinto Beans", "Pistachio", "Pistachio Paste", "Pita Bread", "Pitted Dates", "Plain Chocolate", "Plain Flour", "Plain chocolate", "Plain flour", "Plum Sauce", "Plum Tomatoes", "Plum tomatoes", "Polish Kabanos", "Pomegranate", "Pomegranate Molasses", "Poppy Seeds", "Pork", "Pork Chops", "Pork Shoulder", "Pork Tenderloin", "Pork belly slices", "Porridge oats", "Potato Starch", "Potato starch", "Potatoes", "Prawns", "Prunes", "Puff Pastry", "Pul Biber", "Pumpkin", "Quinoa", "Radish", "Rainbow Trout", "Raisins", "Rapeseed Oil", "Raspberries", "Raspberry Jam", "Raw King Prawns", "Raw Vegetables", "Raw tiger prawns", "Ready rolled shortcrust pastry", "Red Cabbage", "Red Chilli", "Red Chilli Flakes", "Red Chilli Powder", "Red Chilli powder", "Red Onions", "Red Pepper", "Red Pepper Flakes", "Red Pepper Paste", "Red Snapper", "Red Wine", "Red Wine Vinegar", "Redcurrants", "Rhubarb", "Rice", "Rice Flour Pancakes", "Rice Krispies", "Rice Noodles", "Rice Paper Sheets", "Rice Vinegar", "Rice wine", "Ricotta", "Roasted Peanut", "Roasted Vegetables", "Roasted pepper", "Rocket", "Romano Pepper", "Rose water", "Rosemary", "Rye", "Saffron", "Sage", "Sake", "Salmon", "Salt", "Salt Cod", "Salted Butter", "Saskatoon Berries", "Sauerkraut", "Sausages", "Savoy Cabbage", "Scallions", "Scotch Bonnet", "Sea Salt", "Seasoned Rice Vinegar", "Seasoning", "Self-raising Flour", "Semi-skimmed Milk", "Sesame Seed", "Sesame Seed Burger Buns", "Sesame Seed Oil", "Shallots", "Shaoxing Wine", "Shelled Hazelnuts", "Sherry", "Sherry vinegar", "Shiitake Mushrooms", "Shortcrust Pastry", "Shortening", "Shredded Mexican Cheese", "Shrimp", "Shrimp Stock", "Sichuan Pepper", "Sichuan pepper", "Silken Tofu", "Single Cream", "Sirloin steak", "Skirty Steak", "Small Potatoes", "Smoked Haddock", "Smoked Paprika", "Snow Peas", "Sour Cream", "Soured cream and chive dip", "Soy Sauce", "Soy sauce", "Soya Bean", "Spaghetti", "Spinach", "Spring Onions", "Squash", "Squid", "Star Anise", "Starch", "Stilton Cheese", "Stoned Dates", "Strawberries", "Strong White Flour", "Strong Wholemeal Flour", "Strong white bread flour", "Suet", "Sugar", "Sugar Snap Peas", "Sugar Syrup", "Sumac", "Sun-Dried Tomatoes", "Sunflower Oil", "Sushi Rice", "Sweet Potatoes", "Sweet Sherry", "Sweetcorn", "Szechuan Peppercorns", "Tabasco Sauce", "Tahini", "Tahini Paste", "Tamarind Paste", "Tamarind paste", "Tarragon Leaves", "Tempeh", "Thai Chilli Jam", "Thai Green Curry Paste", "Thai Red Curry Paste", "Thai fish sauce", "Thai green curry paste", "Thai red curry paste", "Thyme", "Tiger Prawns", "Tinned Tomatos", "Toast", "Tofu", "Tomato", "Tomato Ketchup", "Tomato Puree", "Tomato Purée", "Tomato Sauce", "Tomatoes", "Tortillas", "Trout", "Truffle Oil", "Tuna", "Turkey", "Turkey Mince", "Turkish Delight", "Turmeric", "Turmeric Powder", "Turmeric powder", "Udon Noodles", "Unflavoured Gelatin", "Unsalted Beef Stock", "Unsalted Butter", "Unsweetened Cocoa", "Unwaxed Lemon", "Unwaxed Lime", "Vanilla", "Vanilla Bean Paste", "Vanilla Extract", "Vegan White Wine Vinegar", "Vegetable Oil", "Vegetable Shortening", "Vegetable Stock", "Vegetable Stock Cube", "Vegetable oil", "Vermicelli Pasta", "Vermicelli Rice Noodles", "Vine Tomatoes", "Vinegar", "Walnuts", "Water", "Water Chestnut", "Whipping Cream", "White Cabbage", "White Chocolate Chips", "White Fish", "White Fish Fillets", "White Flour", "White Sauerkraut", "White Vinegar", "White Wine", "White Wine Vinegar", "White bread", "Whole Milk", "Wholegrain Bread", "Wild Garlic Leaves", "Wild Mushrooms", "Wonton Skin", "Wood Ear Mushrooms", "Worcestershire Sauce", "Yeast", "Yellow Pepper", "Yogurt", "allspice", "almond extract", "almond milk", "baby plum tomatoes", "baking powder", "basil", "bay leaf", "bay leaves", "black pepper", "bowtie pasta", "bread", "breadcrumbs", "butter", "cacao", "carrot", "caster sugar", "celeriac", "celery salt", "charlotte potatoes", "cherry tomatoes", "chestnut mushroom", "chicken breast", "chicken stock", "chicken thighs", "chili powder", "chilled butter", "chilli", "chilli powder", "chopped tomatoes", "cinnamon", "cinnamon stick", "coco sugar", "coconut milk", "cold water", "coriander", "curry powder", "dark soy sauce", "digestive biscuits", "double cream", "dried oregano", "egg", "eggs", "extra virgin olive oil", "farfalle", "fenugreek", "fish sauce", "flaked almonds", "flax eggs", "flour", "free-range egg, beaten", "free-range eggs, beaten", "fresh basil", "fresh thyme", "garam masala", "garlic", "garlic clove", "garlic powder", "ginger", "ginger cordial", "green beans", "green red lentils", "ground almonds", "ground cumin", "heavy cream", "honey", "horseradish", "hot beef stock", "italian seasoning", "jamón ibérico", "king prawns", "lamb loin chops", "lasagne sheets", "lean minced beef", "lemons", "lime", "makrut lime leaves", "manchego", "marjoram", "meringue nests", "milk", "mozzarella balls", "mushrooms", "mustard", "nutmeg", "olive oil", "onion", "onion salt", "onions", "oregano", "oyster sauce", "paprika", "parmesan", "parsley", "peanut oil", "penne rigate", "pepper", "plain flour", "raspberry jam", "red chilli flakes", "red onions", "red pepper flakes", "rice noodles", "rice stick noodles", "sage", "salt", "sausages", "shallots", "soy sauce", "soya milk", "spaghetti", "spinach", "strawberries", "sugar", "sultanas", "sunflower oil", "sweet chilli sauce", "sweet smoked paprika", "thyme", "tomato puree", "tomatoes", "tuna", "turmeric", "turnips", "vanilla", "vanilla pod", "vegan butter", "vegetable oil", "vegetable stock cube", "vinegar", "water", "white vinegar", "white wine", "whole wheat", "worcestershire sauce", "zucchini"
        };

        ArrayAdapter<String> adapterAllergy = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, allergies);
        adapterAllergy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAllergies.setAdapter(adapterAllergy);
    }

    private void registerUser() {
        // Récupération des valeurs
        String username = etUsername.getText().toString();
        String selectedPreference = spinnerPreference.getSelectedItem().toString();
        String selectedAllergy = spinnerAllergies.getSelectedItem().toString();

        // Récupérer le Sexe depuis les RadioButtons
        String gender = "";
        int selectedId = rgGender.getCheckedRadioButtonId();
        if (selectedId == R.id.rbMale) {
            gender = "Homme";
        } else if (selectedId == R.id.rbFemale) {
            gender = "Femme";
        }

        // TODO: Ajouter ici la validation (vérifier si les champs sont vides)
        // TODO: Envoyer ces données à votre base de données (MongoDB via API)

        // Test visuel
        String message = "Bienvenue " + username + " (" + gender + ") ! \nPref: " + selectedPreference;
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}