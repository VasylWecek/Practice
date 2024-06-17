package crud.repository;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import crud.model.Writer;
import crud.model.Status;
import crud.repository.WriterRepository;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GsonWriterRepositoryImpl implements WriterRepository {
    private static final String FILE_PATH = "writers.json";
    private final Gson gson;

    public GsonWriterRepositoryImpl() {
        this.gson = new Gson();
    }

    @Override
    public Writer getById(Long id) {
        List<Writer> writers = load();
        return writers.stream()
                .filter(writer -> writer.getId().equals(id) && writer.getStatus().equals(Status.ACTIVE))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Writer> getAll() {
        List<Writer> writers = load();
        return writers.stream()
                .filter(writer -> writer.getStatus().equals(Status.ACTIVE))
                .collect(Collectors.toList());
    }

    @Override
    public Writer save(Writer writer) {
        List<Writer> writers = load();
        writer.setId(generateId(writers));
        writers.add(writer);
        saveToFile(writers);
        return writer;
    }

    @Override
    public Writer update(Writer writer) {
        List<Writer> writers = load();
        Writer existingWriter = writers.stream()
                .filter(w -> w.getId().equals(writer.getId()))
                .findFirst()
                .orElse(null);
        if (existingWriter != null) {
            existingWriter.setFirstName(writer.getFirstName());
            existingWriter.setLastName(writer.getLastName());
            existingWriter.setPosts(writer.getPosts());
            existingWriter.setStatus(writer.getStatus());
            saveToFile(writers);
        }
        return existingWriter;
    }

    @Override
    public void deleteById(Long id) {
        List<Writer> writers = load();
        Writer writer = writers.stream()
                .filter(w -> w.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (writer != null) {
            writer.setStatus(Status.DELETED);
            saveToFile(writers);
        }
    }

    private List<Writer> load() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<Writer>>() {}.getType();
            List<Writer> writers = gson.fromJson(reader, listType);
            return writers != null ? writers : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void saveToFile(List<Writer> writers) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(writers, writer);
        } catch (Exception e) {
            System.out.println("Error saving Writers to file");
        }
    }

    private Long generateId(List<Writer> writers) {
        return writers.size() > 0 ? writers.stream().mapToLong(Writer::getId).max().getAsLong() + 1 : 1L;
    }
}
