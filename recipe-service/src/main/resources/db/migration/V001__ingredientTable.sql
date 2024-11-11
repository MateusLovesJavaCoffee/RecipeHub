CREATE TABLE ingredient_units (
                                  id SERIAL PRIMARY KEY,
                                  unit_name VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE ingredients (
                             id SERIAL PRIMARY KEY,
                             name VARCHAR(50) NOT NULL UNIQUE,
                             image_url VARCHAR(250),
                             short_description VARCHAR(250),
                             category VARCHAR(50) NOT NULL
);

CREATE TABLE possible_units (
                                ingredient_id SERIAL NOT NULL,
                                ingredient_unit_id SERIAL NOT NULL,
                                PRIMARY KEY (ingredient_id, ingredient_unit_id),
                                CONSTRAINT fk_ingredient FOREIGN KEY (ingredient_id) REFERENCES ingredients(id) ON DELETE CASCADE,
                                CONSTRAINT fk_ingredient_unit FOREIGN KEY (ingredient_unit_id) REFERENCES ingredient_units(id) ON DELETE CASCADE
);

CREATE INDEX idx_ingredients_name ON ingredients(name);
CREATE INDEX idx_ingredients_category ON ingredients(category);
