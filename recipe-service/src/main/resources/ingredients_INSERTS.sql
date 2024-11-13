
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


INSERT INTO possible_units (ingredient_id, unit_of_measure_id) VALUES

INSERT INTO possible_units (ingredient_id, unit_of_measure_id) VALUES
    ((SELECT id FROM ingredients WHERE name = 'Eggs'), (SELECT id FROM unit_of_measure WHERE name = 'Pieces'));


INSERT INTO possible_units (ingredient_id, unit_of_measure_id) VALUES
                                                                   ((SELECT id FROM ingredients WHERE name = 'Sugar'), (SELECT id FROM unit_of_measure WHERE name = 'Grams')),
                                                                   ((SELECT id FROM ingredients WHERE name = 'Sugar'), (SELECT id FROM unit_of_measure WHERE name = 'Teaspoons'));
INSERT INTO possible_units (ingredient_id, unit_of_measure_id) VALUES
                                                                   ((SELECT id FROM ingredients WHERE name = 'Flour'), (SELECT id FROM unit_of_measure WHERE name = 'Grams')),
                                                                   ((SELECT id FROM ingredients WHERE name = 'Flour'), (SELECT id FROM unit_of_measure WHERE name = 'Cups'));

INSERT INTO possible_units (ingredient_id, unit_of_measure_id) VALUES
                                                                   ((SELECT id FROM ingredients WHERE name = 'Butter'), (SELECT id FROM unit_of_measure WHERE name = 'Grams')),
                                                                   ((SELECT id FROM ingredients WHERE name = 'Butter'), (SELECT id FROM unit_of_measure WHERE name = 'Tablespoons'));

INSERT INTO possible_units (ingredient_id, unit_of_measure_id) VALUES
                                                                   ((SELECT id FROM ingredients WHERE name = 'Olive Oil'), (SELECT id FROM unit_of_measure WHERE name = 'Tablespoons')),
                                                                   ((SELECT id FROM ingredients WHERE name = 'Olive Oil'), (SELECT id FROM unit_of_measure WHERE name = 'Milliliters'));

INSERT INTO possible_units (ingredient_id, unit_of_measure_id) VALUES
                                                                   ((SELECT id FROM ingredients WHERE name = 'Chicken Breast'), (SELECT id FROM unit_of_measure WHERE name = 'Grams')),
                                                                   ((SELECT id FROM ingredients WHERE name = 'Chicken Breast'), (SELECT id FROM unit_of_measure WHERE name = 'Pieces'));

INSERT INTO possible_units (ingredient_id, unit_of_measure_id) VALUES
                                                                   ((SELECT id FROM ingredients WHERE name = 'Beef Steak'), (SELECT id FROM unit_of_measure WHERE name = 'Grams')),
                                                                   ((SELECT id FROM ingredients WHERE name = 'Beef Steak'), (SELECT id FROM unit_of_measure WHERE name = 'Pieces'));

INSERT INTO possible_units (ingredient_id, unit_of_measure_id) VALUES
    ((SELECT id FROM ingredients WHERE name = 'Carrot'), (SELECT id FROM unit_of_measure WHERE name = 'Pieces'));

INSERT INTO possible_units (ingredient_id, unit_of_measure_id) VALUES
    ((SELECT id FROM ingredients WHERE name = 'Potato'), (SELECT id FROM unit_of_measure WHERE name = 'Pieces'));

INSERT INTO possible_units (ingredient_id, unit_of_measure_id) VALUES
                                                                   ((SELECT id FROM ingredients WHERE name = 'Tomato'), (SELECT id FROM unit_of_measure WHERE name = 'Pieces')),
                                                                   ((SELECT id FROM ingredients WHERE name = 'Tomato'), (SELECT id FROM unit_of_measure WHERE name = 'Grams'));
INSERT INTO possible_units (ingredient_id, unit_of_measure_id) VALUES
    ((SELECT id FROM ingredients WHERE name = 'Apple'), (SELECT id FROM unit_of_measure WHERE name = 'Pieces'));

INSERT INTO possible_units (ingredient_id, unit_of_measure_id) VALUES
    ((SELECT id FROM ingredients WHERE name = 'Banana'), (SELECT id FROM unit_of_measure WHERE name = 'Pieces'));

INSERT INTO possible_units (ingredient_id, unit_of_measure_id) VALUES
    ((SELECT id FROM ingredients WHERE name = 'Lettuce'), (SELECT id FROM unit_of_measure WHERE name = 'Leaves'));

INSERT INTO possible_units (ingredient_id, unit_of_measure_id) VALUES
    ((SELECT id FROM ingredients WHERE name = 'Cucumber'), (SELECT id FROM unit_of_measure WHERE name = 'Pieces'));

INSERT INTO possible_units (ingredient_id, unit_of_measure_id) VALUES
                                                                   ((SELECT id FROM ingredients WHERE name = 'Salt'), (SELECT id FROM unit_of_measure WHERE name = 'Teaspoons')),
                                                                   ((SELECT id FROM ingredients WHERE name = 'Salt'), (SELECT id FROM unit_of_measure WHERE name = 'Pinches'));