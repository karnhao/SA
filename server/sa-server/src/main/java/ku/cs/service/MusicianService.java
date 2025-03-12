package ku.cs.service;

import ku.cs.repository.MusicianRepository;

public class MusicianService {
    private MusicianRepository musicianRepository;

    public MusicianService(MusicianRepository musicianRepository) {
        this.musicianRepository = musicianRepository;
    }

    public String incrementMusicianPoint(String musicianId) {
        try {
            return musicianRepository.incrementPoint(musicianId);
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to increment musician point: " + e.getMessage();
        }
    }
}
