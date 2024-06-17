package crud.controller;

import crud.model.Post;
import crud.model.Status;
import crud.repository.PostRepository;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostController {
    private final PostRepository postRepository;
    private static final Logger LOGGER = Logger.getLogger(PostController.class.getName());

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post createPost(String title, String content) {
        try {
            Post post = new Post();
            post.setTitle(title);
            post.setContent(content);
            post.setStatus(Status.ACTIVE);
            return postRepository.save(post);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ошибка при создании поста: ", e);
            return null;
        }
    }

    public Post getPostById(Long id) {
        try {
            return postRepository.getById(id);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ошибка при получении сообщения по идентификатору: ", e);
            return null;
        }
    }

    public List<Post> getAllPosts() {
        try {
            return postRepository.getAll();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ошибка при получении всех сообщений: ", e);
            return null;
        }
    }

    public Post updatePost(Long id, String title, String content) {
        try {
            Post post = postRepository.getById(id);
            if (post != null) {
                post.setTitle(title);
                post.setContent(content);
                post.setStatus(Status.ACTIVE);
                return postRepository.update(post);
            } else {
                LOGGER.log(Level.WARNING, "Сообщение с идентификатором: " + id + " не найдено");
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ошибка при обновлении сообщения: ", e);
            return null;
        }
    }

    public void deletePost(Long id) {
        try {
            Post post = postRepository.getById(id);
            if (post != null) {
                postRepository.deleteById(id);
            } else {
                LOGGER.log(Level.WARNING, "Сообщение с идентификатором " + id + " не найдено");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ошибка при удалении сообщения: ", e);
        }
    }
}
