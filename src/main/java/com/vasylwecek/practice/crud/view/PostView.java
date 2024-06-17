package crud.view;



import crud.controller.PostController;
import crud.model.Post;

import java.util.List;
import java.util.Scanner;

public class PostView {
    private final PostController postController;
    private final Scanner scanner;

    public PostView(PostController postController) {
        this.postController = postController;
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        while (true) {
            System.out.println("1. Создать Post");
            System.out.println("2. Получить Post по ID");
            System.out.println("3. Получить все Posts");
            System.out.println("4. Обновить Post");
            System.out.println("5. Удалить Post");
            System.out.println("6. Выход");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    createPost();
                    break;
                case "2":
                    getPostById();
                    break;
                case "3":
                    getAllPosts();
                    break;
                case "4":
                    updatePost();
                    break;
                case "5":
                    deletePost();
                    break;
                case "6":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Неверный выбор");
            }
        }
    }

    private void createPost() {
        try {
            System.out.print("Введите заголовок: ");
            String title = scanner.nextLine();
            System.out.print("Введите содержание: ");
            String content = scanner.nextLine();
            Post post = postController.createPost(title, content);
            System.out.println("Создан Post: " + post);
        } catch (Exception e) {
            System.out.println("Произошла ошибка при создании Post");
        }
    }

    private void getPostById() {
        try {
            System.out.print("Введите ID Post: ");
            Long id = Long.parseLong(scanner.nextLine());
            Post post = postController.getPostById(id);
            if (post != null) {
                System.out.println("Post: " + post);
            } else {
                System.out.println("Post не найден");
            }
        } catch (Exception e) {
            System.out.println("Произошла ошибка при получении Post");
        }
    }

    private void getAllPosts() {
        try {
            List<Post> posts = postController.getAllPosts();
            if (posts.isEmpty()) {
                System.out.println("Нет активных Posts");
            } else {
                posts.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Произошла ошибка при получении всех Posts");
        }
    }

    private void updatePost() {
        try {
            System.out.print("Введите ID Post: ");
            Long id = Long.parseLong(scanner.nextLine());
            System.out.print("Введите новый заголовок: ");
            String title = scanner.nextLine();
            System.out.print("Введите новое содержание: ");
            String content = scanner.nextLine();
            Post post = postController.updatePost(id, title, content);
            if (post != null) {
                System.out.println("Post обновлен: " + post);
            } else {
                System.out.println("Post не найден");
            }
        } catch (Exception e) {
            System.out.println("Произошла ошибка при обновлении Post");
        }
    }

    private void deletePost() {
        try {
            System.out.print("Введите ID Post: ");
            Long id = Long.parseLong(scanner.nextLine());
            postController.deletePost(id);
            System.out.println("Post помечен как DELETED");
        } catch (Exception e) {
            System.out.println("Произошла ошибка при удалении Post");
        }
    }
}
