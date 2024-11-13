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
