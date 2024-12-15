package com.crosska;

import org.apache.commons.cli.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {

        Options options = new Options();
        options.addOption("d", "data", true, "Path to the .csv text data file (including filename and extension)");
        options.addOption("i", "input-file", true, "Path to the text file with search lines (including filename and extension)");
        options.addOption("o", "output-file", true, "Path to the JSON file with results data (including filename and extension)");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("data") && cmd.hasOption("input-file") && cmd.hasOption("output-file")) {
                startSearchProcess(cmd.getOptionValue("data"), cmd.getOptionValue("input-file"), cmd.getOptionValue("output-file"));
            } else {
                System.err.println("Ошибка. Необходимые параметры для запуска:\ndata (d) $путь\ninput-file (i) $путь\noutput-file (o) $путь");
            }
        } catch (ParseException e) {
            e.printStackTrace();
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("search.jar", options);
        }

    }

    private static void startSearchProcess(String dataPath, String inputPath, String outputPath) {
        FileWorker fileWorker = new FileWorker();
        HashMap<String, String> dataMap = fileWorker.readFile(dataPath);

        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            System.out.println("\nGUID: " + entry.getKey() + "\nText: " + entry.getValue());
        }

    }

}