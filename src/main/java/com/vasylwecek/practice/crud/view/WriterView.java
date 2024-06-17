package crud.view;




import crud.controller.WriterController;
import crud.model.Writer;

import java.util.List;
import java.util.Scanner;

public class WriterView {
    private final WriterController writerController;
    private final Scanner scanner;

    public WriterView(WriterController writerController) {
        this.writerController = writerController;
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        while (true) {
            System.out.println("1. Создать Writer");
            System.out.println("2. Получить Writer по ID");
            System.out.println("3. Получить всех Writer");
            System.out.println("4. Обновить Writer");
            System.out.println("5. Удалить Writer");
            System.out.println("6. Выход");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    createWriter();
                    break;
                case "2":
                    getWriterById();
                    break;
                case "3":
                    getAllWriters();
                    break;
                case "4":
                    updateWriter();
                    break;
                case "5":
                    deleteWriter();
                    break;
                case "6":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Неверный выбор");
            }
        }
    }

    private void createWriter() {
        try {
            System.out.print("Введите имя: ");
            String firstName = scanner.nextLine();
            System.out.print("Введите фамилию: ");
            String lastName = scanner.nextLine();
            Writer writer = writerController.createWriter(firstName, lastName);
            System.out.println("Создан Writer: " + writer);
        } catch (Exception e) {
            System.out.println("Произошла ошибка при создании Writer");
        }
    }

    private void getWriterById() {
        try {
            System.out.print("Введите ID Writer: ");
            Long id = Long.parseLong(scanner.nextLine());
            Writer writer = writerController.getWriterById(id);
            if (writer != null) {
                System.out.println("Writer: " + writer);
            } else {
                System.out.println("Writer не найден");
            }
        } catch (Exception e) {
            System.out.println("Произошла ошибка при получении Writer");
        }
    }

    private void getAllWriters() {
        try {
            List<Writer> writers = writerController.getAllWriters();
            if (writers.isEmpty()) {
                System.out.println("Нет активных Writer");
            } else {
                writers.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Произошла ошибка при получении всех Writer");
        }
    }

    private void updateWriter() {
        try {
            System.out.print("Введите ID Writer: ");
            Long id = Long.parseLong(scanner.nextLine());
            System.out.print("Введите новое имя: ");
            String firstName = scanner.nextLine();
            System.out.print("Введите новую фамилию: ");
            String lastName = scanner.nextLine();
            Writer writer = writerController.updateWriter(id, firstName, lastName);
            if (writer != null) {
                System.out.println("Writer обновлен: " + writer);
            } else {
                System.out.println("Writer не найден");
            }
        } catch (Exception e) {
            System.out.println("Произошла ошибка при обновлении Writer");
        }
    }

    private void deleteWriter() {
        try {
            System.out.print("Введите ID Writer: ");
            Long id = Long.parseLong(scanner.nextLine());
            writerController.deleteWriter(id);
            System.out.println("Writer помечен как DELETED");
        } catch (Exception e) {
            System.out.println("Произошла ошибка при удалении Writer");
        }
    }
}