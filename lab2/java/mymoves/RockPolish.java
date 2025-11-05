package mymoves;

import ru.ifmo.se.pokemon.*;

public class RockPolish extends StatusMove {
    public RockPolish(double pow, double acc) {
        super(Type.ROCK, pow, acc);
    }

    @Override
    protected String describe() {
        return "бьет атакой RockPolish";
    }

    @Override
    protected void applySelfEffects(Pokemon p) {
        super.applySelfEffects(p);
        Effect e1 = new Effect().stat(Stat.SPEED, 2);
        p.addEffect(e1);
    }
}
