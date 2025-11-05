package mypokemons;

import mymoves.CloseCombat;
import mymoves.RockPolish;
import mymoves.SwordsDance;
import mymoves.WorkUp;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public final class Cobalion extends Pokemon {
    public Cobalion(String name, int level) {
        super(name, level);
        super.setStats(91, 90, 129, 90, 72, 108);
        super.setType(Type.STEEL, Type.FIGHTING);

        CloseCombat closecombat = new CloseCombat(120, 100);
        SwordsDance swordsdance = new SwordsDance(0, 0);
        WorkUp workup = new WorkUp(0, 0);
        RockPolish rockpolish = new RockPolish(0, 0);

        super.setMove(closecombat, swordsdance, workup, rockpolish);
    }
}