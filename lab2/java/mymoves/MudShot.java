package mymoves;

import ru.ifmo.se.pokemon.*;

public class MudShot extends SpecialMove {
    public MudShot(double pow, double acc) {
        super(Type.GROUND, pow, acc);
    }

    @Override
    protected String describe() {
        return "бьет атакой MudShot";
    }

    @Override
    protected void applyOppDamage(Pokemon p, double dam) {
        super.applyOppDamage(p, dam);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        super.applyOppEffects(p);
        Effect e1 = new Effect().stat(Stat.SPEED, -1);
        p.addEffect(e1);
    }

}
