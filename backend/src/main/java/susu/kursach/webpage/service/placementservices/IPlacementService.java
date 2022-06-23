package susu.kursach.webpage.service.placementservices;

import susu.kursach.webpage.dto.Field;

import java.util.List;
import java.util.Map;

public interface IPlacementService {
    void createComposite(Map<String, String> data);

    Field getServiceTypeName();

    List<Field> getParamNames();
}
