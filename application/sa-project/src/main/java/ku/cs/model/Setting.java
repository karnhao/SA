package ku.cs.model;

public class Setting {
    private int theme;
    private float sfx;
    private float mus;

    public Setting() {
        theme = 0;
        sfx = 1;
        mus = 1;
    }

    public int getTheme() {
        return theme;
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }

    public float getSfx() {
        return sfx;
    }

    public void setSfx(float sfx) {
        this.sfx = sfx;
    }

    public float getMus() {
        return mus;
    }

    public void setMus(float mus) {
        this.mus = mus;
    }
}
