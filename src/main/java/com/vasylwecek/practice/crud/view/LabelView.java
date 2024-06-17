package crud.view;

import crud.controller.LabelController;
import crud.model.Label;

import java.util.List;
import java.util.Scanner;

public class LabelView {
    private final LabelController labelController;
    private final Scanner scanner;

    public LabelView(LabelController labelController) {
        this.labelController = labelController;
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        while (true) {
            System.out.println("1. Создать Label");
            System.out.println("2. Получить Label по ID");
            System.out.println("3. Получить все Labels");
            System.out.println("4. Обновить Label");
            System.out.println("5. Удалить Label");
            System.out.println("6. Выход");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    createLabel();
                    break;
                case "2":
                    getLabelById();
                    break;
                case "3":
                    getAllLabels();
                    break;
                case "4":
                    updateLabel();
                    break;
                case "5":
                    deleteLabel();
                    break;
                case "6":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Неверный выбор");
            }
        }
    }

    private void createLabel() {
        try {
            System.out.print("Введите название: ");
            String name = scanner.nextLine();
            Label label = labelController.createLabel(name);
            System.out.println("Создан Label: " + label);
        } catch (Exception e) {
            System.out.println("Произошла ошибка при создании Label");
        }
    }

    private void getLabelById() {
        try {
            System.out.print("Введите ID Label: ");
            Long id = Long.parseLong(scanner.nextLine());
            Label label = labelController.getLabelById(id);
            if (label != null) {
                System.out.println("Label: " + label);
            } else {
                System.out.println("Label не найден");
            }
        } catch (Exception e) {
            System.out.println("Произошла ошибка при получении Label");
        }
    }

    private void getAllLabels() {
        try {
            List<Label> labels = labelController.getAllLabels();
            if (labels.isEmpty()) {
                System.out.println("Нет активных Labels");
            } else {
                labels.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Произошла ошибка при получении всех Labels");
        }
    }

    private void updateLabel() {
        try {
            System.out.print("Введите ID Label: ");
            Long id = Long.parseLong(scanner.nextLine());
            System.out.print("Введите новое название: ");
            String name = scanner.nextLine();
            Label label = labelController.updateLabel(id, name);
            if (label != null) {
                System.out.println("Label обновлен: " + label);
            } else {
                System.out.println("Label не найден");
            }
        } catch (Exception e) {
            System.out.println("Произошла ошибка при обновлении Label");
        }
    }

    private void deleteLabel() {
        try {
            System.out.print("Введите ID Label: ");
            Long id = Long.parseLong(scanner.nextLine());
            labelController.deleteLabel(id);
            System.out.println("Label помечен как DELETED");
        } catch (Exception e) {
            System.out.println("Произошла ошибка при удалении Label");
        }
    }
}
