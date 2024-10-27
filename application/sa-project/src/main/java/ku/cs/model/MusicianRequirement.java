package ku.cs.model;

public class MusicianRequirement implements Requirement {
    private int quantity;
    private MusicianRole musicianRole;

    @Override
    public String getID() {
        return this.musicianRole.getId();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public MusicianRole getMusicianRole() {
        return musicianRole;
    }

    public void setMusicianRole(MusicianRole musicianRole) {
        this.musicianRole = musicianRole;
    }

    @Override
    public String toString() {
        return this.musicianRole.getName();
    }
}
