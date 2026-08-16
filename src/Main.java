import java.util.Random;

/*
 * Clase principal del programa.
 * Aqui creo los Digimon, los entrenadores y comienzo la batalla.
 * Tambien muestro lo que pasa en cada ronda y quien gana al final.
 */
public class Main {

    public static void main(String[] args) {
        Random random = new Random();

        // Digimon que va usar el primer entrenador
        Digimon dracomon = new Digimon("Dracomon", Digimon.Tipo.FUEGO, 50, 30,
                "Baby Flame", Digievolucion.EfectoDigievolucion.AUMENTA_ATAQUE, 15, 40);

        Digimon floramon = new Digimon("Floramon", Digimon.Tipo.PLANTA, 45, 35,
                "Poison Ivy", Digievolucion.EfectoDigievolucion.AUMENTA_DEFENSA, 20, 35);

        Digimon betamon = new Digimon("Betamon", Digimon.Tipo.AGUA, 48, 32,
                "Blue Blaster", Digievolucion.EfectoDigievolucion.DANIO_DIRECTO, 10, 30);

        Digimon elecmon = new Digimon("Elecmon", Digimon.Tipo.ELECTRICO, 42, 38,
                "Boom Bubble", Digievolucion.EfectoDigievolucion.AUMENTA_ATAQUE, 12, 45);

        // Digimon que va usar el segundo entrenador
        Digimon meramon = new Digimon("Meramon", Digimon.Tipo.FUEGO, 46, 34,
                "Spiral Twister", Digievolucion.EfectoDigievolucion.AUMENTA_ATAQUE, 18, 30);

        Digimon thunderballmon = new Digimon("Thunderballmon", Digimon.Tipo.ELECTRICO, 44, 36,
                "Super Shocker", Digievolucion.EfectoDigievolucion.DANIO_DIRECTO, 10, 35);

        Digimon seadramon = new Digimon("Seadramon", Digimon.Tipo.AGUA, 47, 33,
                "Marching Fishes", Digievolucion.EfectoDigievolucion.AUMENTA_DEFENSA, 15, 40);

        Digimon mushroomon = new Digimon("Mushroomon", Digimon.Tipo.PLANTA, 49, 31,
                "Vee Headbutt", Digievolucion.EfectoDigievolucion.AUMENTA_ATAQUE, 10, 50);

        // Creo los entrenadores y les agrego sus 4 Digimon
        Entrenador tai = new Entrenador("Tai");
        tai.agregarDigimon(dracomon);
        tai.agregarDigimon(floramon);
        tai.agregarDigimon(betamon);
        tai.agregarDigimon(elecmon);

        Entrenador sora = new Entrenador("Sora");
        sora.agregarDigimon(meramon);
        sora.agregarDigimon(thunderballmon);
        sora.agregarDigimon(seadramon);
        sora.agregarDigimon(mushroomon);

        System.out.println("=========================================");
        System.out.println(" BATALLA DIGIMON: " + tai.getNombre() + " vs " + sora.getNombre());
        System.out.println("=========================================");

        // Creo la batalla con los dos entrenadores
        Batalla batalla = new Batalla(tai, sora);

        // Se juegan las 4 rondas
        for (int i = 1; i <= 4; i++) {

            // Se escoge un Digimon disponible de cada entrenador al azar
            int indiceJ1 = random.nextInt(tai.cantidadDisponibles());
            int indiceJ2 = random.nextInt(sora.cantidadDisponibles());

            // Cada entrenador decide al azar si atacar o intentar usar su habilidad
            Batalla.Decision decisionJ1 = random.nextBoolean()
                    ? Batalla.Decision.ATACAR
                    : Batalla.Decision.HABILIDAD;

            Batalla.Decision decisionJ2 = random.nextBoolean()
                    ? Batalla.Decision.ATACAR
                    : Batalla.Decision.HABILIDAD;

            // Juego la ronda y despues muestro lo que paso
            Batalla.Ronda ronda = batalla.jugarRonda(
                    indiceJ1, decisionJ1, indiceJ2, decisionJ2
            );

            mostrarRonda(ronda, tai, sora);
        }

        // Al final muestro cuantas rondas gano cada uno
        System.out.println("=========================================");
        System.out.println("Rondas ganadas por " + tai.getNombre() + ": " + tai.getRondasGanadas());
        System.out.println("Rondas ganadas por " + sora.getNombre() + ": " + sora.getRondasGanadas());

        // Reviso quien gano toda la batalla
        Entrenador ganador = batalla.determinarGanador();

        if (ganador == null) {
            System.out.println("Resultado final: EMPATE");
        } else {
            System.out.println("Ganador de la batalla: " + ganador.getNombre());
        }

        System.out.println("=========================================");
    }

    /*
     * Este metodo muestra toda la informacion de una ronda.
     */
    private static void mostrarRonda(Batalla.Ronda ronda, Entrenador j1, Entrenador j2) {

        System.out.println("--- Ronda " + ronda.getNumero() + " ---");

        System.out.println(j1.getNombre() + " usa " + ronda.getDigimonJ1().getNombre()
                + " | ataqueTotal = " + ronda.getAtaqueTotalJ1()
                + (ronda.isHabilidadActivadaJ1() ? " (habilidad activada)" : ""));

        System.out.println(j2.getNombre() + " usa " + ronda.getDigimonJ2().getNombre()
                + " | ataqueTotal = " + ronda.getAtaqueTotalJ2()
                + (ronda.isHabilidadActivadaJ2() ? " (habilidad activada)" : ""));

        // Dependiendo del resultado muestro quien gano
        String resultado = ronda.getResultado();

        if (resultado.equals("EMPATE")) {
            System.out.println("Resultado: EMPATE");
        } else if (resultado.equals("J1")) {
            System.out.println("Resultado: gana " + j1.getNombre());
        } else {
            System.out.println("Resultado: gana " + j2.getNombre());
        }

        System.out.println();
    }
}