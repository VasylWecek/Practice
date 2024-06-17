package crud.view;

import crud.controller.LabelController;
import crud.controller.PostController;
import crud.controller.WriterController;

import java.util.Scanner;

public class AppView {
    private final WriterView writerView;
    private final LabelView labelView;
    private final PostView postView;
    private final Scanner scanner;

    public AppView(WriterController writerController, LabelController labelController, PostController postController) {
        this.writerView = new WriterView(writerController);
        this.labelView = new LabelView(labelController);
        this.postView = new PostView(postController);
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("Главное меню:");
            System.out.println("1. Управление Writer");
            System.out.println("2. Управление Label");
            System.out.println("3. Управление Post");
            System.out.println("4. Выход");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    writerView.displayMenu();
                    break;
                case "2":
                    labelView.displayMenu();
                    break;
                case "3":
                    postView.displayMenu();
                    break;
                case "4":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Неверный выбор");
            }
        }
    }
}
