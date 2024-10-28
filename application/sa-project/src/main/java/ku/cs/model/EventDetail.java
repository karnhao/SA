package ku.cs.model;

import java.util.List;

public class EventDetail extends Event {
    private List<MusicianRequirement> musicianRequirements;
    private List<StereoRequirement> stereoRequirements;
    private List<Musician> musicians;
    private List<Stereo> stereos;

    public List<Musician> getMusicians() {
        return musicians;
    }

    public void setMusicians(List<Musician> musicians) {
        this.musicians = musicians;
    }

    public List<Stereo> getStereos() {
        return stereos;
    }

    public void setStereos(List<Stereo> stereos) {
        this.stereos = stereos;
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
