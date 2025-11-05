package mypokemons;

import mymoves.Rest;
import mymoves.Slam;
import mymoves.Swagger;
import mymoves.SwordsDance;
import ru.ifmo.se.pokemon.Type;

public final class Victreebell extends Weepinbell {
    public Victreebell(String name, int level) {
        super(name, level);
        super.setStats(80, 105, 65, 100, 70, 70);
        super.setType(Type.GRASS, Type.POISON);

        Swagger swagger = new Swagger(0, 90);
        Slam slam = new Slam(80, 75);
        Rest rest = new Rest(0, 0);
        SwordsDance swordsdance = new SwordsDance(0, 0);

        super.setMove(swagger, slam, rest, swordsdance);
    }
}