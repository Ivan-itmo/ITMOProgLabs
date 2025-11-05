package mymoves;

import ru.ifmo.se.pokemon.*;

public class CloseCombat extends PhysicalMove {
    public CloseCombat(double pow, double acc) {
        super(Type.FIGHTING, pow, acc);
    }

    @Override
    protected String describe() {
        return "бьет атакой CloseCombat";
    }

    @Override
    protected void applyOppDamage(Pokemon p, double dam) {
        super.applyOppDamage(p, dam);
    }

    @Override
    protected void applySelfEffects(Pokemon p) {
        super.applySelfEffects(p);
        Effect e1 = new Effect().stat(Stat.DEFENSE, -1);
        p.addEffect(e1);
        Effect e2 = new Effect().stat(Stat.SPECIAL_DEFENSE, -1);
        p.addEffect(e2);
    }
}