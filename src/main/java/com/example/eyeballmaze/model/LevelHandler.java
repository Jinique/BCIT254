package com.example.eyeballmaze.model;

class LevelHandler {

    private final static String[][] levelOne =
            {
                    {"    ", "    ", "FR G", "    "}, // 00 10 20 30
                    {"CC  ", "FY  ", "DY  ", "CG  "}, // 01 11 21 31
                    {"FG  ", "SR  ", "SG  ", "DY  "}, // 02 12 22 32
                    {"FR  ", "FC  ", "SR  ", "FG  "}, // 03 13 23 33
                    {"SC  ", "DR  ", "FC  ", "DC  "}, // 04 14 24 34
                    {"    ", "DCU ", "    ", "    "}  // 05 15 25 35
            };

    private final static String[][] levelTwo =
            {
                    {"    ", "    ", "FR G", "    "}, // 00 10 20 30
                    {"CC  ", "FC  ", "DY  ", "CG  "}, // 01 11 21 31
                    {"FG  ", "FY  ", "SG  ", "DY  "}, // 02 12 22 32
                    {"FR  ", "SR  ", "SR  ", "FG  "}, // 03 13 23 33
                    {"SC  ", "DR  ", "FC  ", "DC  "}, // 04 14 24 34
                    {"    ", "DCU ", "    ", "    "}  // 05 15 25 35
            };

    private final static String[][] levelThree =
            {
                    {"    ", "    ", "FR G", "    "}, // 00 10 20 30
                    {"DC  ", "DC  ", "DC  ", "DC  "}, // 01 11 21 31
                    {"DC  ", "DC  ", "DC  ", "DC  "}, // 02 12 22 32
                    {"DC  ", "DC  ", "DC  ", "DC  "}, // 03 13 23 33
                    {"DC  ", "DC  ", "DC  ", "DC  "}, // 04 14 24 34
                    {"    ", "DCU ", "    ", "    "}  // 05 15 25 35
            };

    String[][] getLevel(String levelName){
        switch (levelName){
            case "levelOne":
                return levelOne;
            case "levelTwo":
                return levelTwo;
            case "levelThree":
                return levelThree;
            default:
                return levelOne;
        }
    }

}
