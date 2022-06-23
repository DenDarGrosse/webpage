package susu.kursach.webpage.service.placementservices;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import susu.kursach.webpage.dto.Field;

import java.util.List;
import java.util.Map;

@Service
public class HorizontalPlacementService implements IPlacementService {

    @Override
    @SneakyThrows
    public void createComposite(Map<String, String> data) {
        throw new Exception("Horizontal generation is not created yet!");
    }

    @Override
    public Field getServiceTypeName() {
        return new Field("HorizontalPlacement", "Горизонтальное расположение");
    }

    @Override
    public List<Field> getParamNames() {
        return List.of();
    }
}
