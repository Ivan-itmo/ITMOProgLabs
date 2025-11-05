package mypokemons;

import mymoves.BrutalSwing;
import mymoves.Facade;
import mymoves.MudShot;
import mymoves.Tackle;
import ru.ifmo.se.pokemon.Type;

public final class Diggersby extends Bunnelby {
    public Diggersby(String name, int level) {
        super(name, level);
        super.setStats(85, 56, 77, 50, 77, 78);
        super.setType(Type.NORMAL, Type.GROUND);

        MudShot mudshot = new MudShot(55, 95);
        Facade facade = new Facade(70, 100);
        Tackle tackle = new Tackle(35, 100);
        BrutalSwing brutalswing = new BrutalSwing(60, 100);

        super.setMove(mudshot, facade, tackle, brutalswing);
    }
}
