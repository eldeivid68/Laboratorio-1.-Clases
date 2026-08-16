import java.util.ArrayList;
import java.util.List;

/*
 * Esta clase se encarga de controlar la batalla.
 * Aqui se juegan las rondas, se calculan los ataques
 * y se revisa quien gana cada ronda y la batalla.
 */
public class Batalla {

    // Las dos decisiones que puede tomar un entrenador
    public enum Decision {
        ATACAR,
        HABILIDAD
    }

    /*
     * Guarda toda la informacion de una ronda.
     * Aqui queda que Digimon uso cada jugador,
     * sus ataques totales y quien gano.
     */
    public class Ronda {
        private int numero;
        private Digimon digimonJ1;
        private Digimon digimonJ2;
        private int ataqueTotalJ1;
        private int ataqueTotalJ2;
        private boolean habilidadActivadaJ1;
        private boolean habilidadActivadaJ2;
        private String resultado; // Puede ser J1, J2 o EMPATE

        public int getNumero() {
            return numero;
        }

        public Digimon getDigimonJ1() {
            return digimonJ1;
        }

        public Digimon getDigimonJ2() {
            return digimonJ2;
        }

        public int getAtaqueTotalJ1() {
            return ataqueTotalJ1;
        }

        public int getAtaqueTotalJ2() {
            return ataqueTotalJ2;
        }

        public boolean isHabilidadActivadaJ1() {
            return habilidadActivadaJ1;
        }

        public boolean isHabilidadActivadaJ2() {
            return habilidadActivadaJ2;
        }

        public String getResultado() {
            return resultado;
        }
    }

    // Datos que necesito para llevar el control de la batalla
    private Entrenador entrenador1;
    private Entrenador entrenador2;
    private List<Ronda> rondas;
    private static final int TOTAL_RONDAS = 4;

    // Recibo los dos entrenadores que van a pelear
    public Batalla(Entrenador entrenador1, Entrenador entrenador2) {
        this.entrenador1 = entrenador1;
        this.entrenador2 = entrenador2;
        this.rondas = new ArrayList<>();
    }

    /*
     * Este metodo se encarga de jugar una ronda completa.
     * Escoge los Digimon, calcula sus ataques, revisa las habilidades
     * y al final decide quien gano.
     */
    public Ronda jugarRonda(int indiceDigimonJ1, Decision decisionJ1,
                            int indiceDigimonJ2, Decision decisionJ2) {

        // Saco el Digimon que escogio cada entrenador
        Digimon d1 = entrenador1.elegirDigimon(indiceDigimonJ1);
        Digimon d2 = entrenador2.elegirDigimon(indiceDigimonJ2);

        // Creo una nueva ronda y guardo los Digimon que van a pelear
        Ronda ronda = new Ronda();
        ronda.numero = rondas.size() + 1;
        ronda.digimonJ1 = d1;
        ronda.digimonJ2 = d2;

        // Reviso si quedaron efectos de la ronda anterior
        int bonoJ1 = entrenador1.consumirBonoPendiente();
        int bonoJ2 = entrenador2.consumirBonoPendiente();

        // Calculo el ataque de los dos tomando en cuenta el tipo y los bonos
        int totalJ1 = d1.getAtaque() + d1.calcularEfectoTipo(d2) + bonoJ1;
        int totalJ2 = d2.getAtaque() + d2.calcularEfectoTipo(d1) + bonoJ2;

        // Aqui guardo el daño directo que puede recibir cada jugador
        int danioDirectoParaJ1 = 0;
        int danioDirectoParaJ2 = 0;

        // Reviso si el jugador 1 decidio usar su habilidad
        if (decisionJ1 == Decision.HABILIDAD) {

            ronda.habilidadActivadaJ1 = d1.getDigievolucion().activar();

            // Si tuvo suerte y se activo, aplico el efecto
            if (ronda.habilidadActivadaJ1) {

                Digievolucion h = d1.getDigievolucion();

                if (h.getEfecto() == Digievolucion.EfectoDigievolucion.DANIO_DIRECTO) {

                    // Le quito ataque al rival ahora y tambien dejo el efecto pendiente
                    danioDirectoParaJ2 = Math.abs(h.getValor());
                    entrenador2.agregarPenalizacionPendiente(danioDirectoParaJ2);

                } else {

                    // Si no hace daño directo, aumento el ataque
                    totalJ1 += h.getValor();
                    entrenador1.agregarBonoPendiente(h.getValor());
                }
            }
        }

        // Hago lo mismo pero ahora con el jugador 2
        if (decisionJ2 == Decision.HABILIDAD) {

            ronda.habilidadActivadaJ2 = d2.getDigievolucion().activar();

            if (ronda.habilidadActivadaJ2) {

                Digievolucion h = d2.getDigievolucion();

                if (h.getEfecto() == Digievolucion.EfectoDigievolucion.DANIO_DIRECTO) {

                    danioDirectoParaJ1 = Math.abs(h.getValor());
                    entrenador1.agregarPenalizacionPendiente(danioDirectoParaJ1);

                } else {

                    totalJ2 += h.getValor();
                    entrenador2.agregarBonoPendiente(h.getValor());
                }
            }
        }

        // Aplico el daño directo antes de comparar los resultados
        totalJ1 -= danioDirectoParaJ1;
        totalJ2 -= danioDirectoParaJ2;

        // Guardo los ataques finales de esta ronda
        ronda.ataqueTotalJ1 = totalJ1;
        ronda.ataqueTotalJ2 = totalJ2;

        // Comparo los ataques para saber quien gano
        if (totalJ1 > totalJ2) {

            ronda.resultado = "J1";
            entrenador1.sumarRondaGanada();

        } else if (totalJ2 > totalJ1) {

            ronda.resultado = "J2";
            entrenador2.sumarRondaGanada();

        } else {

            ronda.resultado = "EMPATE";
        }

        // Guardo la ronda en el historial
        rondas.add(ronda);

        return ronda;
    }

    // Reviso si todavia faltan rondas por jugar
    public boolean quedanRondasPorJugar() {
        return rondas.size() < TOTAL_RONDAS;
    }

    /*
     * Compara las rondas ganadas de los dos entrenadores
     * para saber quien gano toda la batalla.
     */
    public Entrenador determinarGanador() {

        if (entrenador1.getRondasGanadas() > entrenador2.getRondasGanadas()) {

            return entrenador1;

        } else if (entrenador2.getRondasGanadas() > entrenador1.getRondasGanadas()) {

            return entrenador2;

        } else {

            // Si los dos ganaron lo mismo entonces queda en empate
            return null;
        }
    }

    public Entrenador getEntrenador1() {
        return entrenador1;
    }

    public Entrenador getEntrenador2() {
        return entrenador2;
    }

    public List<Ronda> getRondas() {
        return rondas;
    }
}