package mymoves;

import ru.ifmo.se.pokemon.*;

public class WorkUp extends StatusMove {
    public WorkUp(double pow, double acc) {
        super(Type.NORMAL, pow, acc);
    }

    @Override
    protected String describe() {
        return "бьет атакой WorkUp";
    }

    @Override
    protected void applySelfEffects(Pokemon p) {
        super.applySelfEffects(p);
        Effect e1 = new Effect().stat(Stat.ATTACK, 1);
        p.addEffect(e1);
        Effect e2 = new Effect().stat(Stat.SPECIAL_ATTACK, 1);
        p.addEffect(e2);
    }
}
