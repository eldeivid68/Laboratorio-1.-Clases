import java.util.Random;

/*
 * Esta clase representa la Digievolucion de un Digimon.
 * Aqui guardo su nombre, el efecto que hace y la probabilidad
 * que tiene de activarse.
 */
public class Digievolucion {

    // Los diferentes efectos que puede tener una Digievolucion
    public enum EfectoDigievolucion {
        AUMENTA_ATAQUE,
        AUMENTA_DEFENSA,
        DANIO_DIRECTO
    }

    // Datos que necesito para cada Digievolucion
    private String nombre;
    private EfectoDigievolucion efecto;
    private int valor;
    private int probabilidadActivacion;
    private Random generador;

    // Creo la Digievolucion con todos sus datos
    public Digievolucion(String nombre, EfectoDigievolucion efecto,
                         int valor, int probabilidadActivacion) {

        this.nombre = nombre;
        this.efecto = efecto;
        this.valor = valor;
        this.probabilidadActivacion = probabilidadActivacion;
        this.generador = new Random();
    }

    /*
     * Este metodo intenta activar la Digievolucion.
     * Genero un numero al azar y dependiendo de la probabilidad
     * se activa o no la habilidad.
     */
    public boolean activar() {

        // Genero un numero entre 0 y 100
        int tiro = generador.nextInt(101);

        // Si cae dentro de la probabilidad entonces se activa
        return tiro <= probabilidadActivacion;
    }

    // Devuelve el nombre de la Digievolucion
    public String getNombre() {
        return nombre;
    }

    // Devuelve el tipo de efecto que tiene
    public EfectoDigievolucion getEfecto() {
        return efecto;
    }

    // Devuelve cuanto aumenta o disminuye la habilidad
    public int getValor() {
        return valor;
    }

    // Devuelve la probabilidad que tiene de activarse
    public int getProbabilidadActivacion() {
        return probabilidadActivacion;
    }
}