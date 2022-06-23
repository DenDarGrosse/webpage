package susu.kursach.webpage.service.placementservices;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import susu.kursach.webpage.dto.Field;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ChaoticPlacementService implements IPlacementService {

    @SneakyThrows
    @Override
    public void createComposite(Map<String, String> data) {
        var params = new LinkedList<String>();

        params.add("python");
        params.add(1, "backend\\src\\main\\resources\\ChaoticPlacementComposite.py");

        params.addAll(getParamNames()
                .stream()
                .map(Field::getName)
                .map(data::get)
                .toList());

        var processBuilder = new ProcessBuilder(params);
        processBuilder.redirectErrorStream(true);

        var process = processBuilder.start();

        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
        var result = process.waitFor();
        log.debug(line);

        in.close();

        if (result != 0) {
            throw new Exception("Python script exception");
        }
    }

    @Override
    public Field getServiceTypeName() {
        return new Field("ChaoticPlacement", "Хаотичное расположение");
    }

    @Override
    public List<Field> getParamNames() {
        var result = new LinkedList<Field>();
        result.add(new Field("width", "ширина слоев"));
        result.add(new Field("height_1", "высота 1 слоя"));
        result.add(new Field("height_2", "высота 2 слоя"));
        result.add(new Field("height_3", "высота 3 слоя"));
        result.add(new Field("length_fiber", "длина короткого волокна"));
        result.add(new Field("depth", "толщина слоев"));
        result.add(new Field("diameter", "диаметр волокна"));
        result.add(new Field("min_length", "расстояние между волокнами"));
        result.add(new Field("volume", "площадь волокон"));
        result.add(new Field("num", "количество волокон"));
        result.add(new Field("mode", "тип упаковки волокон"));
        return result;
    }
}
