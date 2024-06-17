package crud.model;


import java.util.List;

public class Writer {
    private Long id;
    private String firstName;
    private String lastName;
    private List<crud.model.Post> posts;
    private crud.model.Status status;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<crud.model.Post> getPosts() {
        return posts;
    }

    public void setPosts(List<crud.model.Post> posts) {
        this.posts = posts;
    }

    public crud.model.Status getStatus() {
        return status;
    }

    public void setStatus(crud.model.Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Writer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", posts=" + posts +
                ", status=" + status +
                '}';
    }
}
