
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
