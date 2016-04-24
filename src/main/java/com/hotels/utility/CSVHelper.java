package com.hotels.utility;

import com.hotels.model.HotelsModel;
import com.hotels.model.MembersModel;
import com.univocity.parsers.common.processor.RowListProcessor;
import com.univocity.parsers.common.processor.RowProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by narubordeesarnsuwan on 4/24/2016 AD.
 */
@Slf4j
public class CSVHelper {

    public List<String[]> parseCSV(String path) throws FileNotFoundException {
        CsvParserSettings settings = new CsvParserSettings();
        //You can configure the parser to automatically detect what line separator sequence is in the input
        settings.setLineSeparatorDetectionEnabled(true);

        // A RowListProcessor stores each parsed row in a List.
        RowListProcessor rowProcessor = new RowListProcessor();

        // You can configure the parser to use a RowProcessor to process the values of each parsed row.
        // You will find more RowProcessors in the 'com.univocity.parsers.common.processor' package, but you can also create your own.
        settings.setRowProcessor(rowProcessor);

        // Let's consider the first parsed row as the headers of each column in the file.
        settings.setHeaderExtractionEnabled(true);

        //the file used in the example uses '\n' as the line separator sequence.
        //the line separator sequence is defined here to ensure systems such as MacOS and Windows
        //are able to process this file correctly (MacOS uses '\r'; and Windows uses '\r\n').
        settings.getFormat().setLineSeparator("\r\n");

        // creates a CSV parser
        CsvParser parser = new CsvParser(settings);

        File file = new File(path);
        FileInputStream fis = null;
        fis = new FileInputStream(file);
        Reader reader = new InputStreamReader(fis, StandardCharsets.UTF_8);

        parser.parse(reader);
        return rowProcessor.getRows();
    }

}
