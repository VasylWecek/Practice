package crud.repository;

import crud.model.Label;

import java.util.List;

public interface LabelRepository extends crud.repository.GenericRepository<Label, Long> {
    Label getById(Long id);

    List<Label> getAll();

    Label save(Label label);

    Label update(Label label);

    void deleteById(Long id);
}
