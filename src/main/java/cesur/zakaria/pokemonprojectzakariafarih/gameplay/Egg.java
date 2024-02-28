package cesur.zakaria.pokemonprojectzakariafarih.gameplay;


public class Egg {
        private Pokemon pokemonInside;

        public Egg(Pokemon pokemonInside) {
            this.pokemonInside = pokemonInside;
        }

        public Pokemon getPokemonInside() {
            return pokemonInside;
        }

        public void setPokemonInside(Pokemon pokemonInside) {
            this.pokemonInside = pokemonInside;
        }
}


