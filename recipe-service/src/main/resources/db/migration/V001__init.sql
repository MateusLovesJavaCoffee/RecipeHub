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


INSERT INTO unit_of_measure (name)
VALUES ('Gram'),
       ('Kilogram'),
       ('Milliliter'),
       ('Liter'),
       ('Cup'),
       ('Tablespoon'),
       ('Teaspoon'),
       ('Ounce'),
       ('Pound'),
       ('Inch'),
       ('Centimeter'),
       ('Meter'),
       ('Foot'),
       ('Liter (US)'),
       ('Fluid Ounce'),
       ('Quart'),
       ('Pint'),
       ('Pinch'),
       ('Dash'),
       ('Bunch');

INSERT INTO possible_units (ingredient_id, unit_of_measure_id)
VALUES
    ((SELECT id FROM ingredients WHERE name = 'Milk'), 4),  -- Liter
    ((SELECT id FROM ingredients WHERE name = 'Milk'), 3);  -- Milliliter

INSERT INTO possible_units (ingredient_id, unit_of_measure_id)
VALUES
    ((SELECT id FROM ingredients WHERE name = 'Eggs'), 10), -- Unit
    ((SELECT id FROM ingredients WHERE name = 'Eggs'), 1);  -- Gram

INSERT INTO possible_units (ingredient_id, unit_of_measure_id)
VALUES
    ((SELECT id FROM ingredients WHERE name = 'Sugar'), 1),  -- Gram
    ((SELECT id FROM ingredients WHERE name = 'Sugar'), 5);  -- Cup

INSERT INTO possible_units (ingredient_id, unit_of_measure_id)
VALUES
    ((SELECT id FROM ingredients WHERE name = 'Flour'), 1),  -- Gram
    ((SELECT id FROM ingredients WHERE name = 'Flour'), 5);  -- Cup

INSERT INTO possible_units (ingredient_id, unit_of_measure_id)
VALUES
    ((SELECT id FROM ingredients WHERE name = 'Butter'), 1),  -- Gram
    ((SELECT id FROM ingredients WHERE name = 'Butter'), 6);  -- Tablespoon

INSERT INTO possible_units (ingredient_id, unit_of_measure_id)
VALUES
    ((SELECT id FROM ingredients WHERE name = 'Olive Oil'), 3),  -- Milliliter
    ((SELECT id FROM ingredients WHERE name = 'Olive Oil'), 6);  -- Tablespoon

INSERT INTO possible_units (ingredient_id, unit_of_measure_id)
VALUES
    ((SELECT id FROM ingredients WHERE name = 'Chicken Breast'), 1),  -- Gram
    ((SELECT id FROM ingredients WHERE name = 'Chicken Breast'), 9);  -- Pound

INSERT INTO possible_units (ingredient_id, unit_of_measure_id)
VALUES
    ((SELECT id FROM ingredients WHERE name = 'Beef Steak'), 1),  -- Gram
    ((SELECT id FROM ingredients WHERE name = 'Beef Steak'), 8);  -- Ounce


INSERT INTO possible_units (ingredient_id, unit_of_measure_id)
VALUES
    ((SELECT id FROM ingredients WHERE name = 'Carrot'), 1),  -- Gram
    ((SELECT id FROM ingredients WHERE name = 'Carrot'), 10);  -- Unit

INSERT INTO possible_units (ingredient_id, unit_of_measure_id)
VALUES
    ((SELECT id FROM ingredients WHERE name = 'Potato'), 1),  -- Gram
    ((SELECT id FROM ingredients WHERE name = 'Potato'), 10);  -- Unit


INSERT INTO possible_units (ingredient_id, unit_of_measure_id)
VALUES
    ((SELECT id FROM ingredients WHERE name = 'Tomato'), 10),  -- Unit
    ((SELECT id FROM ingredients WHERE name = 'Tomato'), 1);  -- Gram

INSERT INTO possible_units (ingredient_id, unit_of_measure_id)
VALUES
    ((SELECT id FROM ingredients WHERE name = 'Apple'), 10),  -- Unit
    ((SELECT id FROM ingredients WHERE name = 'Apple'), 1);  -- Gram

INSERT INTO possible_units (ingredient_id, unit_of_measure_id)
VALUES
    ((SELECT id FROM ingredients WHERE name = 'Banana'), 10),  -- Unit
    ((SELECT id FROM ingredients WHERE name = 'Banana'), 1);  -- Gram

INSERT INTO possible_units (ingredient_id, unit_of_measure_id)
VALUES
    ((SELECT id FROM ingredients WHERE name = 'Lettuce'), 1),  -- Gram
    ((SELECT id FROM ingredients WHERE name = 'Lettuce'), 10);  -- Unit

INSERT INTO possible_units (ingredient_id, unit_of_measure_id)
VALUES
    ((SELECT id FROM ingredients WHERE name = 'Cucumber'), 10),  -- Unit
    ((SELECT id FROM ingredients WHERE name = 'Cucumber'), 1);  -- Gram

INSERT INTO possible_units (ingredient_id, unit_of_measure_id)
VALUES
    ((SELECT id FROM ingredients WHERE name = 'Salt'), 1),  -- Gram
    ((SELECT id FROM ingredients WHERE name = 'Salt'), 7);  -- Teaspoon

INSERT INTO possible_units (ingredient_id, unit_of_measure_id)
VALUES
    ((SELECT id FROM ingredients WHERE name = 'Black Pepper'), 1),  -- Gram
    ((SELECT id FROM ingredients WHERE name = 'Black Pepper'), 7);  -- Teaspoon

INSERT INTO possible_units (ingredient_id, unit_of_measure_id)
VALUES
    ((SELECT id FROM ingredients WHERE name = 'Basil'), 1),  -- Gram
    ((SELECT id FROM ingredients WHERE name = 'Basil'), 6);  -- Tablespoon

INSERT INTO possible_units (ingredient_id, unit_of_measure_id)
VALUES
    ((SELECT id FROM ingredients WHERE name = 'Garlic'), 11),  -- Clove
    ((SELECT id FROM ingredients WHERE name = 'Garlic'), 1);  -- Gram

INSERT INTO possible_units (ingredient_id, unit_of_measure_id)
VALUES
    ((SELECT id FROM ingredients WHERE name = 'Rice'), 1),  -- Gram
    ((SELECT id FROM ingredients WHERE name = 'Rice'), 5);  -- Cup

INSERT INTO possible_units (ingredient_id, unit_of_measure_id)
VALUES
    ((SELECT id FROM ingredients WHERE name = 'Pasta'), 1),  -- Gram
    ((SELECT id FROM ingredients WHERE name = 'Pasta'), 5);  -- Cup

INSERT INTO possible_units (ingredient_id, unit_of_measure_id)
VALUES
    ((SELECT id FROM ingredients WHERE name = 'Almonds'), 1),  -- Gram
    ((SELECT id FROM ingredients WHERE name = 'Almonds'), 5);  -- Cup

INSERT INTO possible_units (ingredient_id, unit_of_measure_id)
VALUES
    ((SELECT id FROM ingredients WHERE name = 'Cashews'), 1),  -- Gram
    ((SELECT id FROM ingredients WHERE name = 'Cashews'), 5);  -- Cup
INSERT INTO recipes (title, img_url, description, preparation_minutes, ready_in_minutes, cooking_minutes, servings,
                     difficulty, estimated_cost, likes, active, published_at, updated_at)
VALUES
    ('Chicken Salad', 'https://example.com/images/chicken_salad.jpg', 'A healthy and tasty chicken salad with fresh vegetables.', 15, 25, 10, 4, 'EASY', 10, 120, TRUE, NOW(), NOW()),
    ('Beef Steak with Mashed Potatoes', 'https://example.com/images/beef_steak_with_mashed_potatoes.jpg', 'Juicy beef steak served with creamy mashed potatoes.', 30, 45, 15, 2, 'INTERMEDIATE', 25, 350, TRUE, NOW(), NOW()),
    ('Vegetable Soup', 'https://example.com/images/vegetable_soup.jpg', 'A warm and hearty vegetable soup with carrots, potatoes, and tomatoes.', 20, 30, 10, 4, 'EASY', 5, 200, TRUE, NOW(), NOW()),
    ('Pasta Primavera', 'https://example.com/images/pasta_primavera.jpg', 'Delicious pasta with a mix of fresh vegetables in a light olive oil sauce.', 25, 35, 10, 3, 'EASY', 12, 180, TRUE, NOW(), NOW()),
    ('Garlic Butter Shrimp', 'https://example.com/images/garlic_butter_shrimp.jpg', 'Tender shrimp in a garlic butter sauce, served with rice.', 20, 30, 10, 2, 'INTERMEDIATE', 18, 250, TRUE, NOW(), NOW()),
    ('Classic Pancakes', 'https://example.com/images/classic_pancakes.jpg', 'Fluffy pancakes served with maple syrup and fresh berries.', 15, 20, 5, 2, 'EASY', 8, 500, TRUE, NOW(), NOW()),
    ('Chicken Alfredo', 'https://example.com/images/chicken_alfredo.jpg', 'Creamy Alfredo sauce with grilled chicken served over pasta.', 30, 40, 10, 4, 'INTERMEDIATE', 22, 180, TRUE, NOW(), NOW()),
    ('Tomato Basil Soup', 'https://example.com/images/tomato_basil_soup.jpg', 'A classic soup made with ripe tomatoes and fresh basil.', 20, 30, 10, 4, 'EASY', 6, 120, TRUE, NOW(), NOW()),
    ('Beef Burger with Fries', 'https://example.com/images/beef_burger_with_fries.jpg', 'Juicy beef burger with crispy fries on the side.', 15, 30, 10, 2, 'EASY', 12, 400, TRUE, NOW(), NOW()),
    ('Veggie Stir Fry', 'https://example.com/images/veggie_stir_fry.jpg', 'A colorful stir-fry with fresh vegetables, soy sauce, and rice.', 20, 30, 10, 3, 'EASY', 8, 150, TRUE, NOW(), NOW()),
    ('Lemon Herb Chicken', 'https://example.com/images/lemon_herb_chicken.jpg', 'Grilled chicken marinated with lemon and herbs.', 25, 35, 10, 4, 'INTERMEDIATE', 15, 180, TRUE, NOW(), NOW()),
    ('Cheese Omelette', 'https://example.com/images/cheese_omelette.jpg', 'Simple and quick cheese omelette, perfect for breakfast.', 10, 15, 5, 2, 'EASY', 5, 350, TRUE, NOW(), NOW()),
    ('Apple Crumble', 'https://example.com/images/apple_crumble.jpg', 'A delicious apple dessert topped with a crispy crumble.', 30, 45, 15, 4, 'EASY', 10, 220, TRUE, NOW(), NOW()),
    ('Chicken Parmesan', 'https://example.com/images/chicken_parmesan.jpg', 'Breaded chicken covered with marinara sauce and melted cheese.', 40, 55, 15, 4, 'INTERMEDIATE', 20, 300, TRUE, NOW(), NOW()),
    ('Spaghetti Bolognese', 'https://example.com/images/spaghetti_bolognese.jpg', 'Classic spaghetti with a rich and savory meat sauce.', 25, 40, 15, 4, 'INTERMEDIATE', 15, 500, TRUE, NOW(), NOW()),
    ('Vegetarian Pizza', 'https://example.com/images/vegetarian_pizza.jpg', 'A healthy pizza topped with tomatoes, onions, and peppers.', 25, 35, 10, 3, 'EASY', 12, 250, TRUE, NOW(), NOW()),
    ('Garlic Mashed Potatoes', 'https://example.com/images/garlic_mashed_potatoes.jpg', 'Creamy mashed potatoes with roasted garlic flavor.', 20, 30, 10, 4, 'EASY', 7, 150, TRUE, NOW(), NOW()),
    ('Steak Frites', 'https://example.com/images/steak_frites.jpg', 'Classic French dish of steak with crispy fries.', 25, 40, 15, 2, 'INTERMEDIATE', 18, 220, TRUE, NOW(), NOW()),
    ('Shrimp Scampi', 'https://example.com/images/shrimp_scampi.jpg', 'Shrimp cooked in garlic, butter, and lemon sauce, served with pasta.', 25, 35, 10, 3, 'INTERMEDIATE', 20, 320, TRUE, NOW(), NOW()),
    ('Chocolate Cake', 'https://example.com/images/chocolate_cake.jpg', 'A rich, moist chocolate cake topped with chocolate frosting.', 40, 50, 10, 8, 'HARD', 30, 150, TRUE, NOW(), NOW());

-- Inserção corrigida de ingredientes para as receitas

INSERT INTO ingredient_composition (recipe_id, ingredient_id, unit_of_measure_id, amount, order_number, description)
VALUES
-- Chicken Salad
(1, 6, 1, 200, 1, 'Chicken Breast, cooked and diced'),
(1, 9, 2, 50, 2, 'Lettuce, chopped'),
(1, 14, 3, 30, 3, 'Tomato, diced'),

-- Beef Steak with Mashed Potatoes
(2, 7, 1, 300, 1, 'Beef Steak, grilled'),
(2, 10, 2, 100, 2, 'Potato, mashed'),
(2, 12, 4, 5, 3, 'Black Pepper, ground'),

-- Vegetable Soup
(3, 9, 2, 100, 1, 'Carrot, diced'),
(3, 10, 2, 150, 2, 'Potato, diced'),
(3, 13, 5, 2, 3, 'Salt'),

-- Pasta Primavera
(4, 15, 6, 200, 1, 'Pasta, cooked'),
(4, 9, 2, 50, 2, 'Tomato, diced'),
(4, 12, 4, 5, 3, 'Olive Oil'),

-- Garlic Butter Shrimp
(5, 6, 1, 150, 1, 'Shrimp, peeled'),
(5, 12, 4, 10, 2, 'Butter'),
(5, 13, 5, 3, 3, 'Garlic, minced'),

-- Chicken Alfredo
(7, 6, 1, 200, 1, 'Chicken Breast'),
(7, 14, 3, 30, 2, 'Basil'),

-- Tomato Basil Soup
(8, 10, 2, 100, 1, 'Potato'),
(8, 9, 2, 50, 2, 'Carrot'),
(8, 6, 1, 200, 3, 'Chicken Breast'),

-- Beef Burger with Fries
(9, 12, 4, 150, 1, 'Beef Patty'),
(9, 7, 1, 200, 2, 'Bread Bun'),
(9, 16, 7, 150, 3, 'Fries'),

-- Veggie Stir Fry
(10, 9, 2, 100, 1, 'Carrot'),
(10, 10, 2, 150, 2, 'Broccoli'),
(10, 11, 1, 50, 3, 'Soy Sauce'),

-- Lemon Herb Chicken
(11, 6, 1, 250, 1, 'Chicken Breast'),
(11, 15, 6, 30, 2, 'Lemon'),
(11, 13, 5, 5, 3, 'Herbs'),

-- Cheese Omelette
(12, 6, 1, 100, 1, 'Eggs'),
(12, 17, 8, 50, 2, 'Cheese'),

-- Apple Crumble
(13, 18, 2, 200, 1, 'Apple'),
(13, 12, 4, 50, 2, 'Butter'),
(13, 13, 5, 5, 3, 'Cinnamon'),

-- Chicken Parmesan
(14, 6, 1, 250, 1, 'Chicken Breast'),
(14, 12, 4, 100, 2, 'Tomato Sauce'),
(14, 17, 8, 30, 3, 'Parmesan Cheese'),

-- Spaghetti Bolognese
(15, 6, 1, 200, 1, 'Ground Beef'),
(15, 9, 2, 50, 2, 'Tomato'),
(15, 16, 7, 100, 3, 'Spaghetti'),

-- Vegetarian Pizza
(16, 10, 2, 100, 1, 'Tomato'),
(16, 9, 2, 150, 2, 'Onion'),
(16, 12, 4, 10, 3, 'Olive Oil'),

-- Garlic Mashed Potatoes
(17, 10, 2, 200, 1, 'Potato'),
(17, 13, 5, 5, 2, 'Garlic'),
(17, 12, 4, 50, 3, 'Butter'),

-- Steak Frites
(18, 7, 1, 250, 1, 'Beef Steak'),
(18, 16, 7, 150, 2, 'Fries'),

-- Shrimp Scampi
(19, 6, 1, 150, 1, 'Shrimp'),
(19, 13, 5, 5, 2, 'Garlic'),
(19, 12, 4, 10, 3, 'Butter'),

(20, 12, 4, 200, 1, 'Flour'),
(20, 13, 5, 30, 2, 'Cocoa Powder'),
(20, 6, 1, 100, 3, 'Eggs');

INSERT INTO instructions (recipe_id, instruction_number_id, description)
VALUES (1, 1, 'Cook the chicken breast in olive oil and dice it.'),
       (1, 2, 'Chop the lettuce and tomato, and mix with the chicken.'),

       (2, 1, 'Grill the beef steak to desired doneness.'),
       (2, 2, 'Mash the potatoes and season with salt and pepper.'),

       (3, 1, 'Dice the carrots and potatoes, and cook in water.'),
       (3, 2, 'Blend the vegetables into a creamy consistency.'),

       (4, 1, 'Boil pasta until al dente.'),
       (4, 2, 'Mix cooked pasta with diced tomatoes and olive oil.'),

       (5, 1, 'Sauté shrimp in butter and minced garlic.'),
       (5, 2, 'Serve shrimp over a bed of rice.'),

       (6, 1, 'Crack eggs and whisk with sugar.'),
       (6, 2, 'Pour the mixture onto a hot pan with butter and cook until golden brown.');


INSERT INTO instructions (recipe_id, instruction_number_id, description)
VALUES (11, 1, 'Grill the chicken breast until cooked through.'),
       (11, 2, 'Marinate the chicken with lemon juice and fresh basil before serving.');


INSERT INTO instructions (recipe_id, instruction_number_id, description)
VALUES (12, 1, 'Whisk eggs and pour into a hot pan with butter.'),
       (12, 2, 'Sprinkle cheese on top and fold the omelette once the eggs are set.');


INSERT INTO instructions (recipe_id, instruction_number_id, description)
VALUES (13, 1, 'Peel and slice the apples, then place them in a baking dish.'),
       (13, 2, 'Mix sugar and butter into a crumble topping and bake until golden brown.');


INSERT INTO instructions (recipe_id, instruction_number_id, description)
VALUES (14, 1, 'Bread the chicken breasts and fry them until golden brown.'),
       (14, 2, 'Top the fried chicken with tomato sauce and mozzarella cheese, then bake until cheese is melted.');


INSERT INTO instructions (recipe_id, instruction_number_id, description)
VALUES (15, 1, 'Cook the beef mince until browned.'),
       (15, 2, 'Add tomatoes and simmer to make the sauce. Serve over cooked spaghetti.');


INSERT INTO instructions (recipe_id, instruction_number_id, description)
VALUES (16, 1, 'Prepare the pizza dough and spread tomato sauce over it.'),
       (16, 2, 'Top with sliced tomatoes, lettuce, and drizzle with olive oil. Bake until crispy.');


INSERT INTO instructions (recipe_id, instruction_number_id, description)
VALUES (17, 1, 'Peel and boil the potatoes until tender.'),
       (17, 2, 'Mash the potatoes with garlic and butter until smooth.');


INSERT INTO instructions (recipe_id, instruction_number_id, description)
VALUES (18, 1, 'Grill the beef steak to your preferred doneness.'),
       (18, 2, 'Slice potatoes into fries and fry them until crispy. Serve with steak.');


INSERT INTO instructions (recipe_id, instruction_number_id, description)
VALUES (19, 1, 'Cook shrimp in olive oil and garlic until pink and tender.'),
       (19, 2, 'Serve the shrimp over pasta and drizzle with additional olive oil and garlic sauce.');

INSERT INTO instructions (recipe_id, instruction_number_id, description)
VALUES (20, 1, 'Preheat the oven and grease the baking pan. Mix flour, sugar, and cocoa powder.'),
       (20, 2,
        'Add eggs and butter to the mix, then bake until a toothpick comes out clean. Let cool before frosting.');
