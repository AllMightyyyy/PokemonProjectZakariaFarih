-- SQL File for Pokémon Project

-- Part 1: User Authentication Queries
-- Checks user credentials during login
SELECT id, hashed_password FROM pokemonplayeruser WHERE username = ?;

-- Part 2: User Registration Queries
-- Inserts a new user into the database
INSERT INTO pokemonplayeruser (username, hashed_password, first_name, last_name, gender) VALUES (?, ?, ?, ?, ?);

-- Checks if a username already exists to avoid duplicates
SELECT id FROM pokemonplayeruser WHERE username = ?;

-- Part 3: Data Retrieval Queries
-- Retrieves all trainer data for a specific user
SELECT * FROM trainers WHERE user_id = ?;

-- Retrieves all Pokémon data for a specific trainer
SELECT * FROM pokemons WHERE trainer_id = ?;

-- Part 4: Data Manipulation Queries
-- Updates the points for a user
UPDATE pokemonplayeruser_data SET points = points + ? WHERE user_id = ?;

-- Updates the points for a user setting a new total
UPDATE pokemonplayeruser_data SET points = ? WHERE user_id = ?;

-- Part 5: Utility Queries
-- Retrieves the image path for a specific Pokémon
SELECT picture FROM pokedex WHERE identifier = ?;

-- Part 6: Creating Views
-- Creates a view that combines Pokémon and trainer data for easy retrieval
CREATE VIEW trainer_pokemon_view AS
SELECT tp.trainer_id, pd.name, pd.type, tp.attack, tp.defense, tp.hp
FROM pokedex pd
JOIN trainer_pokemons tp ON pd.pokemon_id = tp.pokemon_id;

-- Retrieve data from the view
SELECT * FROM trainer_pokemon_view WHERE trainer_id = ?;

-- End of SQL File
