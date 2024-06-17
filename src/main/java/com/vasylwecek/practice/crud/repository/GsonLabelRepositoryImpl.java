package crud.repository;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import crud.model.Label;
import crud.model.Status;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GsonLabelRepositoryImpl implements LabelRepository {
    private static final String FILE_PATH = "labels.json";
    private final Gson gson;

    public GsonLabelRepositoryImpl() {
        this.gson = new Gson();
    }

    @Override
    public Label getById(Long id) {
        List<Label> labels = load();
        return labels.stream()
                .filter(label -> label.getId().equals(id) && label.getStatus() == Status.ACTIVE)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Label> getAll() {
        List<Label> labels = load();
        return labels.stream()
                .filter(label -> label.getStatus() == Status.ACTIVE)
                .collect(Collectors.toList());
    }

    @Override
    public Label save(Label label) {
        List<Label> labels = load();
        label.setId(generateId(labels));
        labels.add(label);
        saveToFile(labels);
        return label;
    }

    @Override
    public Label update(Label label) {
        List<Label> labels = load();
        Label existingLabel = labels.stream()
                .filter(l -> l.getId().equals(label.getId()))
                .findFirst()
                .orElse(null);
        if (existingLabel != null) {
            existingLabel.setName(label.getName());
            existingLabel.setStatus(label.getStatus());
            saveToFile(labels);
        }
        return existingLabel;
    }

    @Override
    public void deleteById(Long id) {
        List<Label> labels = load();
        Label label = labels.stream()
                .filter(l -> l.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (label != null) {
            label.setStatus(Status.DELETED);
            saveToFile(labels);
        }
    }

    private List<Label> load() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<Label>>() {}.getType();
            List<Label> labels = gson.fromJson(reader, listType);
            return labels != null ? labels : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void saveToFile(List<Label> labels) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(labels, writer);
        } catch (Exception e) {
            System.out.println("Ошибка при сохранении Labels в файл");
        }
    }

    private Long generateId(List<Label> labels) {
        return labels.size() > 0 ? labels.stream().mapToLong(Label::getId).max().getAsLong() + 1 : 1L;
    }
}
