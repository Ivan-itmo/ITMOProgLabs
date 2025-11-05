package mymoves;

import ru.ifmo.se.pokemon.*;

public class SwordsDance extends StatusMove {
    public SwordsDance(double pow, double acc) {
        super(Type.NORMAL, pow, acc);
    }

    @Override
    protected String describe() {
        return "бьет атакой SwordDance";
    }

    @Override
    protected void applySelfEffects(Pokemon p) {
        super.applySelfEffects(p);
        Effect e1 = new Effect().stat(Stat.ATTACK, 2);
        p.addEffect(e1);
    }
}
