package crud.controller;

import crud.model.Label;
import crud.model.Status;
import crud.repository.LabelRepository;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LabelController {
    private final LabelRepository labelRepository;
    private static final Logger LOGGER = Logger.getLogger(LabelController.class.getName());

    public LabelController(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    public Label createLabel(String name) {
        try {
            Label label = new Label();
            label.setName(name);
            label.setStatus(Status.ACTIVE);
            return labelRepository.save(label);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ошибка при создании Label: ", e);
            return null;
        }
    }

    public Label getLabelById(Long id) {
        try {
            return labelRepository.getById(id);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ошибка при извлечении метки по идентификатору: ", e);
            return null;
        }
    }

    public List<Label> getAllLabels() {
        try {
            return labelRepository.getAll();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ошибка при извлечении всех меток: ", e);
            return null;
        }
    }

    public Label updateLabel(Long id, String name) {
        try {
            Label label = labelRepository.getById(id);
            if (label != null) {
                label.setName(name);
                label.setStatus(Status.ACTIVE);
                return labelRepository.update(label);
            } else {
                LOGGER.log(Level.WARNING, "Этикетка с идентификатором:  " + id + " не найден");
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ошибка обновления этикетки: ", e);
            return null;
        }
    }

    public void deleteLabel(Long id) {
        try {
            Label label = labelRepository.getById(id);
            if (label != null) {
                label.setStatus(Status.DELETED);
                labelRepository.update(label);
            } else {
                LOGGER.log(Level.WARNING, "Ярлык с идентификатором \" + id + \" не найден");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ошибка при удалении ярлыка", e);
        }
    }
}
