package mypokemons;

import mymoves.Rest;
import mymoves.Slam;
import mymoves.SwordsDance;
import ru.ifmo.se.pokemon.Type;

public class Weepinbell extends Bellsprout {
    public Weepinbell(String name, int level) {
        super(name, level);
        super.setStats(65, 90, 50, 85, 45, 55);
        super.setType(Type.GRASS, Type.POISON);

        Slam slam = new Slam(80, 75);
        Rest rest = new Rest(0, 0);
        SwordsDance swordsdance = new SwordsDance(0, 0);

        super.setMove(slam, rest, swordsdance);
    }
}