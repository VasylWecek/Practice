package crud.controller;

import crud.model.Writer;
import crud.model.Status;
import crud.repository.WriterRepository;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WriterController {
    private final WriterRepository writerRepository;
    private static final Logger LOGGER = Logger.getLogger(WriterController.class.getName());

    public WriterController(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    public Writer createWriter(String firstName, String lastName) {
        Writer writer = new Writer();
        try {
            writer.setFirstName(firstName);
            writer.setLastName(lastName);
            writer.setStatus(Status.ACTIVE);
            return writerRepository.save(writer);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ошибка при создании писателя: ", e);
            return null;
        }
    }

    public Writer getWriterById(Long id) {
        try {
            return writerRepository.getById(id);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ошибка при поиске писателя по идентификатору: ", e);
            return null;
        }
    }

    public List<Writer> getAllWriters() {
        try {
            return writerRepository.getAll();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ошибка при извлечении всех писателей: ", e);
            return null;
        }
    }

    public Writer updateWriter(Long id, String firstName, String lastName) {
        Writer writer = null;
        try {
            writer = writerRepository.getById(id);
            if (writer != null) {
                writer.setFirstName(firstName);
                writer.setLastName(lastName);
                writer = writerRepository.update(writer);
            } else {
                LOGGER.log(Level.WARNING, "Писатель с идентификатором " + id + " не найден");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ошибка обновления писателя: ", e);
        }
        return writer;
    }

    public void deleteWriter(Long id) {
        try {
            Writer writer = writerRepository.getById(id);
            if (writer != null) {
                writerRepository.deleteById(id);
            } else {
                LOGGER.log(Level.WARNING, "Писатель с идентификатором " + id + " не найден");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ошибка при удалении писателя: ", e);
        }
    }
}
