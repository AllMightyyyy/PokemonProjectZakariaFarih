package cesur.zakaria.pokemonprojectzakariafarih.session;

public class Player {
    private int id;
    private String username;
    private int points;

    public Player(int id, String username, int points) {
        this.id = id;
        this.username = username;
        this.points = points;
    }

    // Getters and setters
    public int getId() { return id; }
    public String getUsername() { return username; }
    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
}

