
import ru.ifmo.se.pokemon.Battle;

import mypokemons.*;

public class Main {
    public static void main(String[] args) {
        Battle b = new Battle();

        Bunnelby p1 = new Bunnelby("Бунелби", 1);
        Cobalion p2 = new Cobalion("Кобалеон", 1);
        Diggersby p3 = new Diggersby("Диггерсби", 21);
        Bellsprout p4 = new Bellsprout("Беллспраут", 1);
        Weepinbell p5 = new Weepinbell("Випенбил", 21);
        Victreebell p6 = new Victreebell("Виктрибил", 41);

        b.addAlly(p1);
        b.addAlly(p2);
        b.addAlly(p3);

        b.addFoe(p4);
        b.addFoe(p5);
        b.addFoe(p6);

        b.go();

    }
}