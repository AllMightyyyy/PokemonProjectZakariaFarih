package cesur.zakaria.pokemonprojectzakariafarih.gameplay;

public class Move {
    private String nombre;
    private Tipo tipo;
    private Integer potencia; // Optional, hence Integer to allow null for non-attack moves
    private Integer precision; // Optional
    private int usosMaximos;
    private int usosRestantes;
    private State state; // Optional, for status moves
    private Integer duracionEstado; // Optional
    private Enhancement enhancement; // Optional, for enhancement moves
    private Integer duracionMejora; // Optional

    // Constructor
    public Move(String nombre, Tipo tipo, Integer potencia, Integer precision, int usosMaximos, State state, Integer duracionEstado, Enhancement enhancement, Integer duracionMejora) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.potencia = potencia;
        this.precision = precision;
        this.usosMaximos = usosMaximos;
        this.usosRestantes = usosMaximos; // Initially, usosRestantes equals usosMaximos
        this.state = state;
        this.duracionEstado = duracionEstado;
        this.enhancement = enhancement;
        this.duracionMejora = duracionMejora;
    }

    // Methods
    public void usarMovimiento(Pokemon pokemonRival) {
        // Implementation needed
    }

    public void restaurarUsos() {
        this.usosRestantes = this.usosMaximos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Integer getPotencia() {
        return potencia;
    }

    public void setPotencia(Integer potencia) {
        this.potencia = potencia;
    }

    public Integer getPrecision() {
        return precision;
    }

    public void setPrecision(Integer precision) {
        this.precision = precision;
    }

    public int getUsosMaximos() {
        return usosMaximos;
    }

    public void setUsosMaximos(int usosMaximos) {
        this.usosMaximos = usosMaximos;
    }

    public int getUsosRestantes() {
        return usosRestantes;
    }

    public void setUsosRestantes(int usosRestantes) {
        this.usosRestantes = usosRestantes;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Integer getDuracionEstado() {
        return duracionEstado;
    }

    public void setDuracionEstado(Integer duracionEstado) {
        this.duracionEstado = duracionEstado;
    }

    public Enhancement getEnhancement() {
        return enhancement;
    }

    public void setEnhancement(Enhancement enhancement) {
        this.enhancement = enhancement;
    }

    public Integer getDuracionMejora() {
        return duracionMejora;
    }

    public void setDuracionMejora(Integer duracionMejora) {
        this.duracionMejora = duracionMejora;
    }
}
