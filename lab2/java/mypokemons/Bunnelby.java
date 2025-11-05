package mypokemons;

import mymoves.Facade;
import mymoves.MudShot;
import mymoves.Tackle;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Bunnelby extends Pokemon {
    public Bunnelby(String name, int level) {
        super(name, level);
        super.setStats(38, 36, 38, 32, 36, 57);
        super.setType(Type.NORMAL);

        MudShot mudshot = new MudShot(55, 95);
        Facade facade = new Facade(70, 100);
        Tackle tackle = new Tackle(35, 100);

        super.setMove(mudshot, facade, tackle);
    }
}
