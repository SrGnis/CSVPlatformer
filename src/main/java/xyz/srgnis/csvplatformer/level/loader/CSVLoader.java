package xyz.srgnis.csvplatformer.level.loader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import xyz.srgnis.csvplatformer.CSVPlatformer;
import xyz.srgnis.csvplatformer.config.Config;
import xyz.srgnis.csvplatformer.level.Level;
import xyz.srgnis.csvplatformer.level.platform.SimplePlatform;

import java.io.FileReader;
import java.io.Reader;

public class CSVLoader implements LevelLoader {
    private final CSVPlatformer app;

    public CSVLoader(CSVPlatformer app) {
        this.app = app;
    }

    @Override
    public Level loadLevel(String source) {
        int lastRow = 0;
        int colNum = 0;
        Level level = null;
        try {
            Reader in = new FileReader(source);

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .build();

            Iterable<CSVRecord> records = csvFormat.parse(in);

            for (CSVRecord record : records) {
                colNum = record.size();
                if (level == null) {
                    level = new Level(record.size(), app);
                }

                int index = 0;
                for (String s : record) {
                    float height = Float.valueOf(s);

                    if (lastRow < record.getRecordNumber()) {
                        lastRow = (int) record.getRecordNumber();
                    }
                    if (height > 0) {
                        level.addPlatform(new SimplePlatform(
                                index,
                                (int) record.getRecordNumber() - 1,
                                height
                        ));
                    }
                    index++;
                }
            }
            level.createEndPlatform(colNum, lastRow);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (level == null) {
                level = new Level(Config.RAND_LEVEL_WIDTH, app);
            }
        }

        return level;
    }


}
