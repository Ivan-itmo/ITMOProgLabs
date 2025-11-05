package mymoves;

import ru.ifmo.se.pokemon.*;

public class BrutalSwing extends PhysicalMove {
    public BrutalSwing(double pow, double acc) {
        super(Type.DARK, pow, acc);
    }

    @Override
    protected String describe() {
        return "бьет атакой BrutalSwing";
    }

    @Override
    protected void applyOppDamage(Pokemon p, double dam) {
        super.applyOppDamage(p, dam);
    }

}
