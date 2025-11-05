package mymoves;

import ru.ifmo.se.pokemon.*;

public class Rest extends StatusMove {
    public Rest(double pow, double acc) {
        super(Type.PSYCHIC, pow, acc);
    }

    @Override
    protected String describe() {
        return "бьет атакой Rest";
    }


    @Override
    protected void applySelfEffects(Pokemon p) {
        super.applySelfEffects(p);
        p.restore();
        Effect.sleep(p);
        Effect e1 = new Effect().turns(2);
        p.addEffect(e1);
    }
}
