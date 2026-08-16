import java.util.ArrayList;
import java.util.List;

/*
 * Esta clase representa a cada entrenador.
 * Aqui guardo su nombre, sus Digimon, los que todavia
 * puede utilizar y las rondas que ha ganado.
 */
public class Entrenador {

    // Datos que necesito guardar de cada entrenador
    private String nombre;
    private List<Digimon> equipo;
    private List<Digimon> disponibles;
    private int bonoPendiente;
    private int penalizacionPendiente;
    private int rondasGanadas;

    // Creo al entrenador y preparo sus listas y contadores
    public Entrenador(String nombre) {
        this.nombre = nombre;
        this.equipo = new ArrayList<>();
        this.disponibles = new ArrayList<>();
        this.bonoPendiente = 0;
        this.penalizacionPendiente = 0;
        this.rondasGanadas = 0;
    }

    /*
     * Este metodo agrega un Digimon al equipo.
     * Solo se pueden tener un maximo de 4.
     */
    public boolean agregarDigimon(Digimon digimon) {

        // Reviso que todavia haya espacio en el equipo
        if (equipo.size() >= 4) {
            return false;
        }

        // Lo agrego al equipo y tambien a los que puedo usar
        equipo.add(digimon);
        disponibles.add(digimon);

        return true;
    }

    /*
     * Escoge un Digimon de los que todavia estan disponibles.
     * Al escogerlo lo quito para que no pueda repetirse.
     */
    public Digimon elegirDigimon(int indice) {
        return disponibles.remove(indice);
    }

    // Reviso si todavia quedan Digimon que pueda usar
    public boolean tieneDigimonesDisponibles() {
        return !disponibles.isEmpty();
    }

    // Devuelve cuantos Digimon me quedan disponibles
    public int cantidadDisponibles() {
        return disponibles.size();
    }

    /*
     * Aqui aplico los efectos que quedaron de la ronda anterior.
     * Despues de usarlos los regreso a 0.
     */
    public int consumirBonoPendiente() {

        // Calculo el resultado entre el bono y la penalizacion
        int neto = bonoPendiente - penalizacionPendiente;

        // Los reinicio porque ya fueron utilizados
        bonoPendiente = 0;
        penalizacionPendiente = 0;

        return neto;
    }

    // Agrega un bono que se va usar en la siguiente ronda
    public void agregarBonoPendiente(int valor) {
        bonoPendiente += valor;
    }

    // Agrega una penalizacion para la siguiente ronda
    public void agregarPenalizacionPendiente(int valor) {
        penalizacionPendiente += valor;
    }

    // Sumo una ronda cuando este entrenador gana
    public void sumarRondaGanada() {
        rondasGanadas++;
    }

    // Devuelve el nombre del entrenador
    public String getNombre() {
        return nombre;
    }

    // Devuelve todos los Digimon que tiene en su equipo
    public List<Digimon> getEquipo() {
        return equipo;
    }

    // Devuelve cuantas rondas lleva ganadas
    public int getRondasGanadas() {
        return rondasGanadas;
    }
}