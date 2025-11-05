package mymoves;

import ru.ifmo.se.pokemon.*;

public class Tackle extends PhysicalMove {
    public Tackle(double pow, double acc) {
        super(Type.NORMAL, pow, acc);
    }

    @Override
    protected String describe() {
        return "бьет атакой Tackle";
    }

    @Override
    protected void applyOppDamage(Pokemon p, double dam) {
        super.applyOppDamage(p, dam);
    }

}
