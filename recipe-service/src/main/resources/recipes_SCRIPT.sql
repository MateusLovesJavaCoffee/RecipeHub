-- Inserindo uma nova receita
INSERT INTO recipes (title, img_url, description, preparation_minutes, ready_in_minutes, cooking_minutes, servings, difficulty, estimated_cost, active, published_at)
VALUES ('Spaghetti Carbonara', 'https://example.com/images/carbonara.jpg', 'A classic Italian pasta dish made with eggs, cheese, pancetta, and pepper.', 15, 30, 10, 4, 'Medium', 10, TRUE, NOW());
-- Inserindo ingredientes na tabela de composição
INSERT INTO ingredient_composition (recipe_id, ingredient_id, unit_of_measure_id, amount, order_number, description)
VALUES
    ((SELECT id FROM recipes WHERE title = 'Spaghetti Carbonara'), (SELECT id FROM ingredients WHERE name = 'Spaghetti'), (SELECT id FROM unit_of_measure WHERE name = 'Grams'), 400, 1, 'Spaghetti pasta'),
    ((SELECT id FROM recipes WHERE title = 'Spaghetti Carbonara'), (SELECT id FROM ingredients WHERE name = 'Eggs'), (SELECT id FROM unit_of_measure WHERE name = 'Pieces'), 4, 2, 'Eggs'),
    ((SELECT id FROM recipes WHERE title = 'Spaghetti Carbonara'), (SELECT id FROM ingredients WHERE name = 'Pancetta'), (SELECT id FROM unit_of_measure WHERE name = 'Grams'), 150, 3, 'Pancetta or bacon'),
    ((SELECT id FROM recipes WHERE title = 'Spaghetti Carbonara'), (SELECT id FROM ingredients WHERE name = 'Parmesan Cheese'), (SELECT id FROM unit_of_measure WHERE name = 'Grams'), 100, 4, 'Grated Parmesan cheese'),
    ((SELECT id FROM recipes WHERE title = 'Spaghetti Carbonara'), (SELECT id FROM ingredients WHERE name = 'Black Pepper'), (SELECT id FROM unit_of_measure WHERE name = 'Teaspoons'), 1, 5, 'Freshly ground black pepper');
-- Inserindo instruções passo a passo
INSERT INTO instructions (recipe_id, instruction_number_id, description)
VALUES
    ((SELECT id FROM recipes WHERE title = 'Spaghetti Carbonara'), 1, 'Cook the spaghetti according to the package instructions.'),
    ((SELECT id FROM recipes WHERE title = 'Spaghetti Carbonara'), 2, 'While the pasta is cooking, fry the pancetta in a pan until crispy.'),
    ((SELECT id FROM recipes WHERE title = 'Spaghetti Carbonara'), 3, 'Whisk together the eggs and grated Parmesan cheese in a bowl.'),
    ((SELECT id FROM recipes WHERE title = 'Spaghetti Carbonara'), 4, 'Once the pasta is cooked, reserve some pasta water and then drain the pasta.'),
    ((SELECT id FROM recipes WHERE title = 'Spaghetti Carbonara'), 5, 'Combine the hot pasta with the pancetta, then slowly mix in the egg and cheese mixture.'),
    ((SELECT id FROM recipes WHERE title = 'Spaghetti Carbonara'), 6, 'Season with black pepper and serve immediately.');
