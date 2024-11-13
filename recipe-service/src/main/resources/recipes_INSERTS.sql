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