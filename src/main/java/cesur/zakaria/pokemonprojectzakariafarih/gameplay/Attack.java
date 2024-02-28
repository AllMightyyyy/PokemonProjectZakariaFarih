package cesur.zakaria.pokemonprojectzakariafarih.gameplay;

public class Attack {
    private String name;
    private Type type;
    private Integer power; // Optional
    private Integer accuracy; // Optional
    private int maxUses;
    private int remainingUses;
    private State status; // Optional
    private Integer statusDuration; // Optional
    private String enhancement; // Optional
    private Integer enhancementDuration; // Optional

    public void useAttack(Pokemon opponentPokemon) {
        // Implementation needed
    }

    public void restoreUses() {
        // Implementation needed
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Integer getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }

    public int getMaxUses() {
        return maxUses;
    }

    public void setMaxUses(int maxUses) {
        this.maxUses = maxUses;
    }

    public int getRemainingUses() {
        return remainingUses;
    }

    public void setRemainingUses(int remainingUses) {
        this.remainingUses = remainingUses;
    }

    public State getStatus() {
        return status;
    }

    public void setStatus(State status) {
        this.status = status;
    }

    public Integer getStatusDuration() {
        return statusDuration;
    }

    public void setStatusDuration(Integer statusDuration) {
        this.statusDuration = statusDuration;
    }

    public String getEnhancement() {
        return enhancement;
    }

    public void setEnhancement(String enhancement) {
        this.enhancement = enhancement;
    }

    public Integer getEnhancementDuration() {
        return enhancementDuration;
    }

    public void setEnhancementDuration(Integer enhancementDuration) {
        this.enhancementDuration = enhancementDuration;
    }
}

