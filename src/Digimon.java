/*
 * Esta clase representa a cada Digimon.
 * Aqui guardo su nombre, tipo, ataque, defensa
 * y tambien la Digievolucion que puede utilizar.
 */
public class Digimon {

    // Los diferentes tipos que puede tener un Digimon
    public enum Tipo {
        FUEGO,
        AGUA,
        PLANTA,
        ELECTRICO
    }

    // Datos principales de cada Digimon
    private String nombre;
    private Tipo tipo;
    private int ataque;
    private int defensa;
    private Digievolucion digievolucion;

    // Creo el Digimon con todos sus datos y su Digievolucion
    public Digimon(String nombre, Tipo tipo, int ataque, int defensa,
                   String nombreDigievolucion, Digievolucion.EfectoDigievolucion efecto,
                   int valorEfecto, int probabilidadActivacion) {

        this.nombre = nombre;
        this.tipo = tipo;
        this.ataque = ataque;
        this.defensa = defensa;

        // Cada Digimon tiene su propia Digievolucion
        this.digievolucion = new Digievolucion(
                nombreDigievolucion,
                efecto,
                valorEfecto,
                probabilidadActivacion
        );
    }

    /*
     * Este metodo compara el tipo de los dos Digimon.
     * Dependiendo de los tipos puede dar ventaja,
     * desventaja o simplemente quedar neutral.
     */
    public int calcularEfectoTipo(Digimon rival) {

        // Guardo el tipo del rival para poder compararlo
        Tipo tipoRival = rival.getTipo();

        // Reviso si mi tipo tiene ventaja contra el rival
        boolean esEfectivo =
                (tipo == Tipo.FUEGO && tipoRival == Tipo.PLANTA) ||
                        (tipo == Tipo.PLANTA && tipoRival == Tipo.AGUA) ||
                        (tipo == Tipo.AGUA && tipoRival == Tipo.FUEGO) ||
                        (tipo == Tipo.ELECTRICO && tipoRival == Tipo.AGUA);

        // Ahora reviso si mi tipo tiene desventaja
        boolean esDebil =
                (tipo == Tipo.FUEGO && tipoRival == Tipo.AGUA) ||
                        (tipo == Tipo.PLANTA && tipoRival == Tipo.FUEGO) ||
                        (tipo == Tipo.AGUA && tipoRival == Tipo.PLANTA) ||
                        (tipo == Tipo.ELECTRICO && tipoRival == Tipo.ELECTRICO);

        // Si tiene ventaja le agrego 20 al ataque
        if (esEfectivo) {
            return 20;

            // Si tiene desventaja le quito 10
        } else if (esDebil) {
            return -10;

            // Si ninguno tiene ventaja queda neutral
        } else {
            return 0;
        }
    }

    // Devuelve el nombre del Digimon
    public String getNombre() {
        return nombre;
    }

    // Devuelve el tipo del Digimon
    public Tipo getTipo() {
        return tipo;
    }

    // Devuelve cuanto ataque tiene
    public int getAtaque() {
        return ataque;
    }

    // Devuelve cuanto defensa tiene
    public int getDefensa() {
        return defensa;
    }

    // Devuelve la Digievolucion que tiene el Digimon
    public Digievolucion getDigievolucion() {
        return digievolucion;
    }
}