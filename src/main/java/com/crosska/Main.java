package com.crosska;

import org.apache.commons.cli.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        Options options = new Options();
        options.addOption("d", "data", true, "Path to the .csv text data file (including filename and extension)");
        options.addOption("i", "input-file", true, "Path to the text file with search lines (including filename and extension)");
        options.addOption("o", "output-file", true, "Path to the JSON file with results data (including filename and extension)");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("data") && cmd.hasOption("input-file") && cmd.hasOption("output-file")) {
                startSearchProcess(cmd.getOptionValue("data"), cmd.getOptionValue("input-file"), cmd.getOptionValue("output-file"), startTime);
            } else {
                System.err.println("Ошибка. Необходимые параметры для запуска:\ndata (d) $путь\ninput-file (i) $путь\noutput-file (o) $путь");
            }
        } catch (ParseException e) {
            e.printStackTrace();
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("search.jar", options);
        }

    }

    private static void startSearchProcess(String dataPath, String inputPath, String outputPath, long startTime) {
        FileWorker fileWorker = new FileWorker(); // Обьект работы с файлами
        List<ResultElem> resArray = new ArrayList<>(); // Список обьектов для JSON файла

        LinkedList<String> requestList = fileWorker.readInputFile(inputPath); // Считываем данные о запросах в список

        long initTime = System.currentTimeMillis() - startTime;

        LinkedHashMap<String, Report> dataMap = fileWorker.readDataFile(dataPath); // Считываем данные об отчетах в мапу

        for (String currentRequest : requestList) { // Цикл по запросам в списке запросов
            long searchStartTime = System.currentTimeMillis();

            String[] parsedRequest = DataAnalyser.parseString(currentRequest); // Парсим текущий запрос по словам с обрезанием окончаний
            //System.out.println("Текущий запрос:" + Arrays.toString(parsedRequest));

            for (Map.Entry<String, Report> entry : dataMap.entrySet()) { // Обнуляем все точности отчетов
                entry.getValue().resetPrecision();
            }

            for (String seekWord : parsedRequest) { // Цикл по словам в разобранном запросе
                //System.out.println("Ищем \"" + seekWord + "\" в отчетах");

                for (Map.Entry<String, Report> entry : dataMap.entrySet()) { // Цикл по данным отчетов

                    for (int i = 0; i < entry.getValue().getParsedReportName().length; i++) { // Цикл по каждому слову в названии отчета
                        if (seekWord.equalsIgnoreCase(entry.getValue().getParsedReportName()[i])) { // Слово совпадает (опуская регистр)
                            //System.out.println(seekWord + " = " + entry.getValue().getParsedReportName()[i]);
                            entry.getValue().increasePrecision(); // Повышаем точность отчета
                        }
                    }

                }

            }

            ArrayList<String> GUIDList = new ArrayList<>();
            int max = 0;
            int reportCount = 0;
            for (Map.Entry<String, Report> entry : dataMap.entrySet()) { // Цикл по данным отчетам (Максимум 3 подходящих отчета)
                if (entry.getValue().getQueryPrecision() > max) { // Если точность выше нашей прошлой максимальной
                    GUIDList.clear();
                    GUIDList.add(entry.getKey());
                    reportCount = 1;
                    max = entry.getValue().getQueryPrecision();
                } else if (entry.getValue().getQueryPrecision() == max && entry.getValue().getQueryPrecision() != 0 && reportCount < 3) { // Если точность такая же
                    GUIDList.add(entry.getKey());
                    reportCount++;
                }
            }

            long searchTime = System.currentTimeMillis() - searchStartTime;

            resArray.add(new ResultElem(currentRequest, GUIDList.toArray(String[]::new), searchTime));
        }

        JSONObject jsonObject = new JSONObject(initTime, outputPath);
        for (ResultElem resultElem : resArray) {
            jsonObject.addNewResult(resultElem);
        }
        JSONWriter jsonWriter = new JSONWriter();
        jsonWriter.writeResult(jsonObject);
    }
}