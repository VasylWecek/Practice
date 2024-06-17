package crud.repository;

import crud.model.Post;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import crud.model.Status;
import crud.repository.PostRepository;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GsonPostRepositoryImpl implements PostRepository {
    private static final String FILE_PATH = "posts.json";
    private final Gson gson;

    public GsonPostRepositoryImpl() {
        this.gson = new Gson();
    }

    @Override
    public Post getById(Long id) {
        List<Post> posts = load();
        return posts.stream()
                .filter(post -> post.getId().equals(id) && post.getStatus().equals(Status.ACTIVE))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = load();
        return posts.stream()
                .filter(post -> post.getStatus().equals(Status.ACTIVE))
                .collect(Collectors.toList());
    }

    @Override
    public Post save(Post post) {
        List<Post> posts = load();
        post.setId(generateId(posts));
        posts.add(post);
        saveToFile(posts);
        return post;
    }

    @Override
    public Post update(Post post) {
        List<Post> posts = load();
        Post existingPost = posts.stream()
                .filter(p -> p.getId().equals(post.getId()))
                .findFirst()
                .orElse(null);
        if (existingPost != null) {
            existingPost.setTitle(post.getTitle());
            existingPost.setContent(post.getContent());
            existingPost.setLabels(post.getLabels());
            existingPost.setStatus(post.getStatus());
            saveToFile(posts);
        }
        return existingPost;
    }

    @Override
    public void deleteById(Long id) {
        List<Post> posts = load();
        Post post = posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (post != null) {
            post.setStatus(Status.DELETED);
            saveToFile(posts);
        }
    }

    private List<Post> load() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<Post>>() {}.getType();
            List<Post> posts = gson.fromJson(reader, listType);
            return posts != null ? posts : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void saveToFile(List<Post> posts) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(posts, writer);
        } catch (Exception e) {
            System.out.println("Error saving Posts to file");
        }
    }

    private Long generateId(List<Post> posts) {
        return posts.size() > 0 ? posts.stream().mapToLong(Post::getId).max().getAsLong() + 1 : 1L;
    }
}
