package ku.cs.model;

import java.util.List;

public class MusicianRequirement implements Requirement {
    private int quantity;
    private MusicianRole musicianRole;
    private List<Musician> musicians;
    private String status;
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

    public void setMusicians(List<Musician> musicians) {
        this.musicians = musicians;
    }

    public List<Musician> getMusicians() {
        return musicians;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.musicianRole.getName();
    }
}
