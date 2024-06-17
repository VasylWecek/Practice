package crud;

import crud.controller.LabelController;
import crud.controller.PostController;
import crud.controller.WriterController;
import crud.repository.GsonLabelRepositoryImpl;
import crud.repository.GsonPostRepositoryImpl;
import crud.repository.GsonWriterRepositoryImpl;
import crud.view.AppView;

public class Main {
    public static void main(String[] args) {
        GsonWriterRepositoryImpl writerRepository = new GsonWriterRepositoryImpl();
        GsonLabelRepositoryImpl labelRepository = new GsonLabelRepositoryImpl();
        GsonPostRepositoryImpl postRepository = new GsonPostRepositoryImpl();

        WriterController writerController = new WriterController(writerRepository);
        LabelController labelController = new LabelController(labelRepository);
        PostController postController = new PostController(postRepository);

        AppView appView = new AppView(writerController, labelController, postController);
        appView.start();
    }
}
