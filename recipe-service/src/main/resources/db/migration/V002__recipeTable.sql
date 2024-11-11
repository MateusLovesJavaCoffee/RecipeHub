
CREATE TABLE recipes (
                         id BIGSERIAL PRIMARY KEY,
                         title VARCHAR(150) NOT NULL,
                         img_url VARCHAR(250),
                         description TEXT NOT NULL,
                         preparation_minutes INT NOT NULL,
                         ready_in_minutes INT NOT NULL,
                         cooking_minutes INT,
                         servings INT NOT NULL,
                         difficulty VARCHAR(20) NOT NULL,
                         estimated_cost INT NOT NULL,
                         likes INT DEFAULT 0,
                         active BOOLEAN NOT NULL,
                         published_at TIMESTAMP,
                         updated_at TIMESTAMP,
                         deleted_at TIMESTAMP
--                          CONSTRAINT chk_estimated_cost CHECK (estimated_cost >= 1 AND estimated_cost <= 999),
--                          CONSTRAINT chk_servings CHECK (servings >= 1 AND servings <= 99)
);

CREATE INDEX idx_recipe_title ON recipes (title);
CREATE INDEX idx_recipe_difficulty ON recipes (difficulty);
CREATE INDEX idx_recipe_published_at ON recipes (published_at);

CREATE TABLE recipe_ingredient_composition (
                                               recipe_id BIGINT NOT NULL,
                                               ingredient_id BIGINT NOT NULL,
                                               amount INT NOT NULL,
                                               order_number INT NOT NULL,
                                               description TEXT,
                                               unit_of_measure VARCHAR(255) NOT NULL,
                                               primary key (recipe_id, ingredient_id),
                                               FOREIGN KEY (recipe_id) REFERENCES recipes (id) ON DELETE CASCADE,
                                               FOREIGN KEY (ingredient_id) REFERENCES ingredients (id) ON DELETE CASCADE
);

CREATE TABLE recipe_step (
                             id BIGSERIAL PRIMARY KEY,
                             recipe_id BIGINT NOT NULL,
                             step_order INT NOT NULL,
                             step_description TEXT NOT NULL,
                             FOREIGN KEY (recipe_id) REFERENCES recipes (id) ON DELETE CASCADE
);

