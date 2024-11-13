package br.com.mateusulrich.recipeservice.ingredient.enums;

public enum IngredientCategory {

    DAIRY("Dairy"),
    FRUITS("Fruits"),
    VEGETABLES("Vegetables"),
    MEAT("Meat"),
    POULTRY("Poultry"),
    SEAFOOD("Seafood"),
    GRAINS("Grains"),
    LEGUMES("Legumes"),
    NUTS_AND_SEEDS("Nuts & Seeds"),
    HERBS_AND_SPICES("Herbs & Spices"),
    OILS_AND_FATS("Oils & Fats"),
    BEVERAGES("Beverages"),
    SWEETENER("Sweetener"),
    CONDIMENTS("Condiments"),
    BAKING("Baking"),
    SNACKS("Snacks"),
    DESSERTS("Desserts"),
    SAUCES("Sauces"),
    CANNED("Canned"),
    FROZEN("Frozen"),
    MISC("Miscellaneous");

    private final String category;

    IngredientCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return this.category;
    }
    public static IngredientCategory fromString(String category) {
        for (IngredientCategory c : IngredientCategory.values()) {
            if (c.getCategory().equalsIgnoreCase(category)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Unknown category: " + category);
    }
}
