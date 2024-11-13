CREATE TABLE recipes (
                         id INTEGER PRIMARY KEY AUTO_INCREMENT,
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

UPDATE recipes
SET difficulty = 'INTERMEDIATE'
where difficulty = 'MEDIUM';



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
