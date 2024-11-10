package br.com.mateusulrich.recipeservice.ingredient.persistence;

public enum IngredientCategory {

    // Laticínios (leite, queijo, manteiga, etc.)
    DAIRY("Dairy"),

    // Frutas (maçã, banana, laranja, etc.)
    FRUITS("Fruits"),

    // Vegetais (cenoura, tomate, alface, etc.)
    VEGETABLES("Vegetables"),

    // Carnes (carne de boi, cordeiro, porco, etc.)
    MEAT("Meat"),

    // Aves (frango, peru, pato, etc.)
    POULTRY("Poultry"),

    // Frutos do mar (peixe, camarão, lula, etc.)
    SEAFOOD("Seafood"),

    // Grãos (arroz, quinoa, aveia, etc.)
    GRAINS("Grains"),

    // Leguminosas (feijão, lentilha, grão-de-bico, etc.)
    LEGUMES("Legumes"),

    // Nozes e sementes (amêndoas, castanha de caju, semente de chia, etc.)
    NUTS_AND_SEEDS("Nuts & Seeds"),

    // Ervas e especiarias (manjericão, pimenta-do-reino, cominho, etc.)
    HERBS_AND_SPICES("Herbs & Spices"),

    // Óleos e gorduras (azeite de oliva, óleo de coco, manteiga, etc.)
    OILS_AND_FATS("Oils & Fats"),

    // Bebidas (água, suco, chá, café, etc.)
    BEVERAGES("Beverages"),

    // Adoçantes (açúcar, mel, adoçante, xarope de bordo, etc.)
    SWEETENER("Sweetener"),

    // Condimentos (sal, pimenta, molho de soja, ketchup, etc.)
    CONDIMENTS("Condiments"),

    // Ingredientes para panificação (farinha, fermento, bicarbonato, etc.)
    BAKING("Baking"),

    // Lanches (batata frita, biscoitos, pipoca, etc.)
    SNACKS("Snacks"),

    // Sobremesas (chocolate, bolos, sorvetes, etc.)
    DESSERTS("Desserts"),

    // Molhos (molho de tomate, molho branco, molho barbecue, etc.)
    SAUCES("Sauces"),

    // Enlatados (feijão enlatado, atum em lata, legumes enlatados, etc.)
    CANNED("Canned"),

    // Congelados (vegetais congelados, frutas congeladas, carne congelada, etc.)
    FROZEN("Frozen"),

    // Diversos (categoria geral para ingredientes não categorizados especificamente)
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
