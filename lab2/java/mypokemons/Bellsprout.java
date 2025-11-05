package mypokemons;

import mymoves.Rest;
import mymoves.SwordsDance;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Bellsprout extends Pokemon {
    public Bellsprout(String name, int level) {
        super(name, level);
        super.setStats(50, 75, 35, 70, 30, 40);
        super.setType(Type.GRASS, Type.POISON);

        Rest rest = new Rest(0, 0);
        SwordsDance swordsdance = new SwordsDance(0, 0);

        super.setMove(rest, swordsdance);
    }
}
