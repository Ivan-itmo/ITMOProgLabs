package mymoves;

import ru.ifmo.se.pokemon.*;

public class Facade extends PhysicalMove {
    public Facade(double pow, double acc) {
        super(Type.NORMAL, pow, acc);
    }

    @Override
    protected String describe() {
        return "бьет атакой Facade";
    }


    @Override
    protected void applyOppDamage(Pokemon p, double dam) {
        super.applyOppDamage(p, dam);
        Status s1 = Status.BURN;
        Status s2 = Status.POISON;
        Status s3 = Status.PARALYZE;
        if (p.getCondition() == s1 || p.getCondition() == s2 || p.getCondition() == s3) {
            super.applyOppDamage(p, dam);
        }
    }
}
