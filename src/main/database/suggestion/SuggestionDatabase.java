package main.database.suggestion;
import main.database.Database;
import main.model.suggestion.Suggestion;
import java.util.List;
import java.util.Map;
import static main.utils.config.Location.RESOURCE_LOCATION;

public class SuggestionDatabase extends Database<Suggestion> {
    private static final String FILE_PATH = "/data/suggestions/suggestions.txt";

    SuggestionDatabase() {
        super();
        load();
    }

    public static SuggestionDatabase getInstance() {
        return new SuggestionDatabase();
    }

    @Override
    public String getFilePath() {
        return RESOURCE_LOCATION + FILE_PATH;
    }

    @Override
    public void setAll(List<Map<String, String>> listOfMappableObjects) {
        for (Map<String, String> map : listOfMappableObjects) {
            getAll().add(new Suggestion(map));
        }
    }
}