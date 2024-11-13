CREATE TABLE unit_of_measure (
                                 id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                 name VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE ingredients (
                             id INTEGER PRIMARY KEY AUTO_INCREMENT,
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
