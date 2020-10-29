package edu.msu.brandta7.project1_9;

public class Player {

    /**
     * Name of the player
     */
    private String name;

    /**
     * Team number
     * 0: Player A
     * 1: Player B
     */
    private int team;

    /**
     * Whether or not the player has moved for their current turn
     */
    private boolean moved;

    /**
     * Constructor
     * @param name The name of the player
     * @param teamNum The player's team number
     */
    public Player(String name, int teamNum) {
        this.name = name;
        this.team = teamNum;
        moved = false;
    }

    public int getTeam() { return  team; }

    public String getName() { return name; }

    public void setMove(boolean status) {
        moved = status;
    }

    public boolean getMoved() { return moved; }
}
