package ku.cs.model;

import java.util.List;

public class EventDetail extends Event {
    private List<User> musicians;
    private List<MusicianRequirement> musicianRequirements;
    private List<StereoRequirement> stereoRequirements;

    public List<User> getMusicians() {
        return musicians;
    }

    public void setMusicians(List<User> musicians) {
        this.musicians = musicians;
    }

    public List<MusicianRequirement> getMusicianRequirements() {
        return musicianRequirements;
    }

    public List<StereoRequirement> getStereoRequirements() {
        return stereoRequirements;
    }

    public void setMusicianRequirements(List<MusicianRequirement> musicianRequirements) {
        this.musicianRequirements = musicianRequirements;
    }

    public void setStereoRequirements(List<StereoRequirement> stereoRequirements) {
        this.stereoRequirements = stereoRequirements;
    }
}
