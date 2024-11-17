BEGIN;
CREATE TABLE unit_of_measure (
                                 id SERIAL PRIMARY KEY,
                                 name VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE ingredients (
                             id SERIAL PRIMARY KEY,
                             name VARCHAR(50) NOT NULL UNIQUE,
                             image_url VARCHAR(250),
                             description VARCHAR(200),
                             category VARCHAR(20) NOT NULL
);

CREATE TABLE possible_units (
                                ingredient_id INTEGER NOT NULL,
                                unit_of_measure_id INTEGER NOT NULL,
                                PRIMARY KEY (ingredient_id, unit_of_measure_id),
                                CONSTRAINT fk_ingredient FOREIGN KEY (ingredient_id) REFERENCES ingredients(id) ON DELETE CASCADE,
                                CONSTRAINT fk_ingredient_unit FOREIGN KEY (unit_of_measure_id) REFERENCES unit_of_measure(id) ON DELETE CASCADE
);

CREATE INDEX idx_ingredients_name ON ingredients(name);
CREATE INDEX idx_ingredients_category ON ingredients(category);

CREATE TABLE recipes (
                         id SERIAL PRIMARY KEY,
                         title VARCHAR(100) NOT NULL,
                         img_url VARCHAR(250),
                         description TEXT NOT NULL,
                         preparation_minutes INT NOT NULL,
                         ready_in_minutes INT NOT NULL,
                         cooking_minutes INT,
                         servings INT NOT NULL,
                         difficulty VARCHAR(20) NOT NULL,
                         estimated_cost INT NOT NULL,
                         likes INT DEFAULT 0,
                         active BOOLEAN NOT NULL DEFAULT FALSE,
                         published_at TIMESTAMP,
                         updated_at TIMESTAMP,
                         deleted_at TIMESTAMP
);

CREATE INDEX idx_recipe_title ON recipes (title);
CREATE INDEX idx_recipe_difficulty ON recipes (difficulty);
CREATE INDEX idx_recipe_published_at ON recipes (published_at);

CREATE TABLE ingredient_composition (
                                        recipe_id INTEGER NOT NULL,
                                        ingredient_id INTEGER NOT NULL,
                                        unit_of_measure_id INTEGER NOT NULL,
                                        amount INT NOT NULL,
                                        order_number INT NOT NULL,
                                        description VARCHAR(50),
                                        PRIMARY KEY (recipe_id, ingredient_id),
                                        FOREIGN KEY (recipe_id) REFERENCES recipes (id) ON DELETE CASCADE,
                                        FOREIGN KEY (ingredient_id) REFERENCES ingredients (id),
                                        FOREIGN KEY (unit_of_measure_id) REFERENCES unit_of_measure (id)
);

CREATE TABLE instructions (
                              recipe_id INTEGER NOT NULL,
                              instruction_number_id INT NOT NULL,
                              description VARCHAR(500) NOT NULL,
                              PRIMARY KEY (recipe_id, instruction_number_id),
                              FOREIGN KEY (recipe_id) REFERENCES recipes (id) ON DELETE CASCADE
);

INSERT INTO ingredients (name, image_url, description, category) VALUES
                                                                     ('Milk', 'https://example.com/images/milk.jpg', 'Dairy product obtained from cows.', 'DAIRY'),
                                                                     ('Eggs', 'https://example.com/images/eggs.jpg', 'Chicken eggs, commonly used in many recipes.', 'DAIRY'),
                                                                     ('Sugar', 'https://example.com/images/sugar.jpg', 'Sweetener used in a wide variety of recipes.', 'SWEETENER'),
                                                                     ('Flour', 'https://example.com/images/flour.jpg', 'Powdered wheat used for baking.', 'BAKING'),
                                                                     ('Butter', 'https://example.com/images/butter.jpg', 'Fat made from cream, used in cooking and baking.', 'OILS_AND_FATS'),
                                                                     ('Olive Oil', 'https://example.com/images/olive_oil.jpg', 'Oil extracted from olives, commonly used in cooking.', 'OILS_AND_FATS'),
                                                                     ('Chicken Breast', 'https://example.com/images/chicken_breast.jpg', 'Lean meat from the breast of a chicken.', 'POULTRY'),
                                                                     ('Beef Steak', 'https://example.com/images/beef_steak.jpg', 'Meat from the muscles of a cow, often grilled or fried.', 'MEAT'),
                                                                     ('Carrot', 'https://example.com/images/carrot.jpg', 'A root vegetable, often used in soups and salads.', 'VEGETABLES'),
                                                                     ('Potato', 'https://example.com/images/potato.jpg', 'A starchy tuber vegetable, used in many dishes.', 'VEGETABLES'),
                                                                     ('Tomato', 'https://example.com/images/tomato.jpg', 'A red, round fruit used in sauces and salads.', 'VEGETABLES'),
                                                                     ('Apple', 'https://example.com/images/apple.jpg', 'A sweet, crisp fruit, often eaten raw or used in desserts.', 'FRUITS'),
                                                                     ('Banana', 'https://example.com/images/banana.jpg', 'A tropical fruit, rich in potassium.', 'FRUITS'),
                                                                     ('Lettuce', 'https://example.com/images/lettuce.jpg', 'Leafy vegetable, often used in salads.', 'VEGETABLES'),
                                                                     ('Cucumber', 'https://example.com/images/cucumber.jpg', 'A fresh, crunchy vegetable, often used in salads.', 'VEGETABLES'),
                                                                     ('Salt', 'https://example.com/images/salt.jpg', 'A mineral used as a seasoning in cooking.', 'CONDIMENTS'),
                                                                     ('Black Pepper', 'https://example.com/images/black_pepper.jpg', 'Ground pepper, used as a seasoning.', 'HERBS_AND_SPICES'),
                                                                     ('Basil', 'https://example.com/images/basil.jpg', 'A fragrant herb often used in Mediterranean cooking.', 'HERBS_AND_SPICES'),
                                                                     ('Garlic', 'https://example.com/images/garlic.jpg', 'A strong-smelling bulb used to flavor food.', 'HERBS_AND_SPICES'),
                                                                     ('Rice', 'https://example.com/images/rice.jpg', 'A staple grain used in a variety of dishes.', 'GRAINS'),
                                                                     ('Pasta', 'https://example.com/images/pasta.jpg', 'Dough made from durum wheat and used in various dishes.', 'GRAINS'),
                                                                     ('Almonds', 'https://example.com/images/almonds.jpg', 'A type of nut, used in desserts and as a snack.', 'NUTS_AND_SEEDS'),
                                                                     ('Cashews', 'https://example.com/images/cashews.jpg', 'A popular nut, often roasted and salted.', 'NUTS_AND_SEEDS');
