package mymoves;

import ru.ifmo.se.pokemon.*;

public class Slam extends PhysicalMove {
    public Slam(double pow, double acc) {
        super(Type.NORMAL, pow, acc);
    }

    @Override
    protected String describe() {
        return "бьет атакой Slam";
    }

    @Override
    protected void applyOppDamage(Pokemon p, double dam) {
        super.applyOppDamage(p, dam);
    }

}
