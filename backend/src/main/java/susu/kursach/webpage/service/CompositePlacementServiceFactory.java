package susu.kursach.webpage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import susu.kursach.webpage.dto.Field;
import susu.kursach.webpage.service.placementservices.IPlacementService;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CompositePlacementServiceFactory {
    private final List<IPlacementService> services;

    public IPlacementService getService(String type) {
        var serviceOpt = services
                .stream()
                .filter(p -> p.getServiceTypeName().getName().equals(type))
                .findFirst();

        if (serviceOpt.isEmpty()) {
            throw new InvalidParameterException("Wrong placement type. Available types: " + services
                    .stream()
                    .map(IPlacementService::getServiceTypeName)
                    .map(Field::getName)
                    .collect(Collectors.joining(", ")));
        }

        return serviceOpt.get();
    }
}
