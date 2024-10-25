package ku.cs.model;

public class MusicianRequirement {
    private int quantity;
    private MusicianRole musicianRole;

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
