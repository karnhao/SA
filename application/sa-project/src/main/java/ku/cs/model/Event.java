package ku.cs.model;

import java.time.LocalDateTime;
import java.util.List;

public class Event {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String title;
    private String description;
    private String status;
    private User owner;
    private List<User> musicians;

    public Event() {}

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<User> getMusicians() {
        return musicians;
    }

    public void setMusicians(List<User> musicians) {
        this.musicians = musicians;
    }
}
