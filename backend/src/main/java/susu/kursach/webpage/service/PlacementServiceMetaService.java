package susu.kursach.webpage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import susu.kursach.webpage.dto.Field;
import susu.kursach.webpage.service.placementservices.IPlacementService;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PlacementServiceMetaService {

    private final List<IPlacementService> services;

    public List<List<Field>> getAllServicesMeta() {
        var result = new LinkedList<List<Field>>();

        for (var service : services) {
            var tmpResult = new LinkedList<Field>();
            tmpResult.add(service.getServiceTypeName());
            tmpResult.addAll(service.getParamNames());
            result.add(tmpResult);
        }

        return result;
    }
}
