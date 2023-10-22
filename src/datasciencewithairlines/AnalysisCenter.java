package datasciencewithairlines;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Class that is used to load and parse the database
 */
public class AnalysisCenter {

    private List<Flight> completedFlights;
    private List<Flight> cancelledFlights;
    private Map<String, Long> arrFlightsInAirport;
    private Map<String, Long> depFlightsInAirport;
    private FormattedOutput formattedOutput = new FormattedOutput();

    /**
     * Method controls the process of analyzing the database
     *
     * @param dirName The absolute path to the folder into which the final file
     * with the analysis results is written
     * @param fileName The absolute name of the file received for analysis
     */
    public void analyzeFile(String dirName, File fileName) {
        loadFile(fileName);
        collectAirportsMaps();
        answerQuestion1();
        answerQuestion2();
        answerQuestion3();
        answerQuestion4();
        answerQuestion5and6();
        answerQuestion7();
        answerQuestion8();
        answerQuestion9();
        formattedOutput.writeAnswers(dirName);
    }

    /**
     * Method loads the database and builds objects consisting of flight data
     *
     * @param fileName The absolute name of the file received for analysis
     */
    void loadFile(File fileName) {
        completedFlights = new ArrayList<>();
        cancelledFlights = new ArrayList<>();
        try (FileReader fr = new FileReader(fileName)) {
            Scanner scan = new Scanner(fr);
            boolean firstLine = true;
            while (scan.hasNextLine()) {
                if (firstLine) {
                    scan.nextLine();
                    firstLine = false;
                } else {
                    String[] line = scan.nextLine().split(",");
                    Flight flight = new Flight.Builder()
                            .withDayofMonth(Verifier.checkDayOfMonth(Verifier.checkForInt(0, line, "others")))
                            .withDayOfWeek(Verifier.checkDayOfWeek(Verifier.checkForInt(1, line, "others")))
                            .withFlightDate(Verifier.checkForDate(2, line))
                            .withUniqueCarrier(Verifier.checkForString(3, line))
                            .withTailNum(Verifier.checkForString(4, line))
                            .withOriginAirportID(Verifier.checkForString(5, line))
                            .withOrigin(Verifier.checkForString(6, line))
                            .withOriginStateName(Verifier.checkForString(7, line))
                            .withDestAirportID(Verifier.checkForString(8, line))
                            .withDest(Verifier.checkForString(9, line))
                            .withDestStateName(Verifier.checkForString(10, line))
                            .withDepTime(Verifier.checkTime(Verifier.checkForInt(11, line, "others")))
                            .withDepDelay(Verifier.checkForInt(12, line, "delay"))
                            .withWheelsOff(Verifier.checkTime(Verifier.checkForInt(13, line, "others")))
                            .withWheelsOn(Verifier.checkTime(Verifier.checkForInt(14, line, "others")))
                            .withArrTime(Verifier.checkTime(Verifier.checkForInt(15, line, "others")))
                            .withArrDelay(Verifier.checkForInt(16, line, "delay"))
                            .withCancelled(Verifier.checkCancelled(Verifier.checkForInt(17, line, "others")))
                            .withCancellationCode(Verifier.checkForString(18, line))
                            .withDiverted(Verifier.checkCancelled(Verifier.checkForInt(19, line, "others")))
                            .withAirTime(Verifier.checkForInt(20, line, "others"))
                            .withDistance(Verifier.checkForInt(21, line, "others"))
                            .build();
                    if (flight.getCancelled() == 1) {
                        cancelledFlights.add(flight);
                    } else if (flight.getDiverted() != 1
                            && (flight.getDepTime() == 0 || flight.getArrTime() == 0)) {
                    } else {
                        completedFlights.add(flight);
                    }
                }
            }
            fr.close();
        } catch (IOException ex) {
            System.out.println("File upload failed.");;
        }
    }

    /**
     * Method generates maps with the values of the number of departing and
     * arriving flights for each airport
     */
    void collectAirportsMaps() {
        arrFlightsInAirport = new HashMap<>();
        depFlightsInAirport = new HashMap<>();
        if (completedFlights.size() > 0) {
            arrFlightsInAirport = completedFlights
                    .stream()
                    .filter(f -> !f.getOriginAirportID().equals("Not filled"))
                    .collect(Collectors.groupingBy(Flight::getOriginAirportID, Collectors.counting()));
            depFlightsInAirport = completedFlights
                    .stream()
                    .filter(f -> !f.getDestAirportID().equals("Not filled"))
                    .collect(Collectors.groupingBy(Flight::getDestAirportID, Collectors.counting()));
        }
    }

    /**
     * Method selects an entry with the maximum value from the map
     *
     * @param map The map with dataset <K, V>
     * @return The entry with dataset <K, V> with maximum value
     */
    <K, V> Entry<K, V> maxValueOfEntries(Map<K, V> map, Comparator<V> comp) {
        Entry<K, V> entry = Collections
                .max(map.<K, V>entrySet(), Map.Entry.<K, V>comparingByValue(comp));
        return entry;
    }

    /**
     * Method selects an entry with the minimum value from the map
     *
     * @param map The map with dataset <K, V>
     * @return The entry with dataset <K, V> with minimum value
     */
    <K, V> Entry<K, V> minValueOfEntries(Map<K, V> map, Comparator<V> comp) {
        Entry<K, V> entry = Collections
                .min(map.<K, V>entrySet(), Map.Entry.<K, V>comparingByValue(comp));
        return entry;
    }

    /**
     * Method finds the airline carrier with the highest percentage of canceled
     * flights
     */
    void answerQuestion1() {
        if (cancelledFlights.size() > 0) {
            Map<String, Long> ucCompletedFlights = completedFlights
                    .stream()
                    .filter(f -> f.getDiverted() != 1)
                    .filter(f -> !f.getUniqueCarrier().equals("Not filled"))
                    .collect(Collectors.groupingBy(Flight::getUniqueCarrier, Collectors.counting()));

            Map<String, Long> ucCancelledFlights = new HashMap<>();
            ucCancelledFlights = cancelledFlights
                    .stream()
                    .filter(f -> !f.getUniqueCarrier().equals("Not filled"))
                    .collect(Collectors.groupingBy(Flight::getUniqueCarrier, Collectors.counting()));

            Map<String, Double> ucShareOfCancelledFlights = new HashMap<>();

            ucCancelledFlights.entrySet().forEach(entry -> {
                String key = entry.getKey();
                long valueCancelled = entry.getValue();
                long valueCompleted = 0;
                if (ucCompletedFlights.size() > 0 && ucCompletedFlights.containsKey(key)) {
                    valueCompleted = ucCompletedFlights.get(key);
                }
                double percentageShare = (double) valueCancelled / (valueCompleted + valueCancelled) * 100;
                ucShareOfCancelledFlights.put(key, percentageShare);
            });

            if (ucShareOfCancelledFlights.size() > 0) {
                Entry<String, Double> maxEntryShareOfCancelledFlights
                        = maxValueOfEntries(ucShareOfCancelledFlights, Double::compare);

                String q1Answer = maxEntryShareOfCancelledFlights.getKey()
                        + "," + maxEntryShareOfCancelledFlights.getValue() + "%";
                formattedOutput.addAnswer(1, q1Answer);
            } else {
                formattedOutput.addAnswer(1, "The database does not contain flights "
                        + "with complete information necessary to answer this question");
            }
        } else {
            formattedOutput.addAnswer(1, "There is no information in the database "
                    + "about cancelled flights (or incomplete data are given on them)");
        }
    }

    /**
     * Method for finding the most common reason for canceled flights
     */
    void answerQuestion2() {
        if (cancelledFlights.size() > 0) {
            Map<String, Long> cancellationCodes = cancelledFlights
                    .stream()
                    .filter(f -> !f.getCancellationCode().equals("Not filled"))
                    .collect(Collectors.groupingBy(Flight::getCancellationCode, Collectors.counting()));

            if (cancellationCodes.size() > 0) {
                formattedOutput.addAnswer(2, maxValueOfEntries(cancellationCodes, Long::compare).getKey());
            } else {
                formattedOutput.addAnswer(2, "The database does not contain flights "
                        + "with complete information necessary to answer this question");
            }
        } else {
            formattedOutput.addAnswer(2, "There is no information in the database "
                    + "about cancelled flights (or incomplete data are given on them)");
        }
    }

    /**
     * Method calculates which plane has flown the most miles
     */
    void answerQuestion3() {
        if (completedFlights.size() > 0) {
            Map<String, Long> sumDistancesOfTail = completedFlights
                    .stream()
                    .filter(f -> !f.getTailNum().equals("Not filled"))
                    .filter(f -> f.getDistance() > 0)
                    .collect(Collectors.groupingBy(Flight::getTailNum, Collectors.summingLong(Flight::getDistance)));

            if (sumDistancesOfTail.size() > 0) {
                formattedOutput.addAnswer(3, maxValueOfEntries(sumDistancesOfTail, Long::compare).getKey());
            } else {
                formattedOutput.addAnswer(3, "The database does not contain flights "
                        + "with complete information necessary to answer this question");
            }
        } else {
            formattedOutput.addAnswer(3, "There is no information on completed "
                    + "flights in the database (or incomplete data are given for them)");
        }
    }

    /**
     * Method calculates which airport is the busiest based on the number of
     * departing and arriving flights
     */
    void answerQuestion4() {
        if (completedFlights.size() > 0
                && (arrFlightsInAirport.size() > 0 || depFlightsInAirport.size() > 0)) {
            Map<String, Long> sumFlightsInAirport = new HashMap<>();
            arrFlightsInAirport.entrySet().forEach(entry -> {
                String key = entry.getKey();
                long value = entry.getValue();
                if (depFlightsInAirport.containsKey(key)) {
                    sumFlightsInAirport.put(key, value + depFlightsInAirport.get(key));
                } else {
                    sumFlightsInAirport.put(key, value);
                }
            });

            depFlightsInAirport.entrySet().forEach(entry -> {
                String key = entry.getKey();
                long value = entry.getValue();
                sumFlightsInAirport.putIfAbsent(key, value);
            });

            formattedOutput.addAnswer(4, maxValueOfEntries(sumFlightsInAirport, Long::compare).getKey());
        } else {
            formattedOutput.addAnswer(4, "There is no information on completed "
                    + "flights in the database (or incomplete data are given for them)");
        }
    }

    /**
     * Method calculates which of the airports are the largest sources and sinks
     * of flights
     */
    void answerQuestion5and6() {
        if (completedFlights.size() > 0
                && (arrFlightsInAirport.size() > 0 || depFlightsInAirport.size() > 0)) {
            Map<String, Long> diffArrAndDepFlights = new HashMap<>();
            arrFlightsInAirport.entrySet().forEach(entry -> {
                String key = entry.getKey();
                long value = entry.getValue();
                if (depFlightsInAirport.containsKey(key)) {
                    diffArrAndDepFlights.put(key, value - depFlightsInAirport.get(key));
                } else {
                    diffArrAndDepFlights.put(key, value);
                }
            });

            depFlightsInAirport.entrySet().forEach(entry -> {
                String key = entry.getKey();
                long value = -entry.getValue();
                diffArrAndDepFlights.putIfAbsent(key, value);
            });

            formattedOutput.addAnswer(5, maxValueOfEntries(diffArrAndDepFlights, Long::compare).getKey());
            formattedOutput.addAnswer(6, minValueOfEntries(diffArrAndDepFlights, Long::compare).getKey());
        } else {
            formattedOutput.addAnswer(5, "There is no information on completed "
                    + "flights in the database (or incomplete data are given for them)");
            formattedOutput.addAnswer(6, "There is no information on completed "
                    + "flights in the database (or incomplete data are given for them)");
        }
    }

    /**
     * Method calculates the number of flights of a particular airline that were
     * delayed by 60 minutes or more, either at departure or at arrival
     */
    void answerQuestion7() {
        if (completedFlights.size() > 0) {
            int sumDepDelay = (int) completedFlights
                    .stream()
                    .filter(f -> f.getDiverted() != 1)
                    .filter(f -> f.getDepDelay() != 1000000)
                    .filter(f -> f.getUniqueCarrier().equalsIgnoreCase("AA"))
                    .filter(f -> f.getDepDelay() >= 60)
                    .count();

            int sumArrDelay = (int) completedFlights
                    .stream()
                    .filter(f -> f.getDiverted() != 1)
                    .filter(f -> f.getArrDelay() != 1000000)
                    .filter(f -> f.getUniqueCarrier().equalsIgnoreCase("AA"))
                    .filter(f -> f.getDepDelay() < 60)
                    .filter(f -> f.getArrDelay() >= 60)
                    .count();

            formattedOutput.addAnswer(7, sumDepDelay + sumArrDelay);
        } else {
            formattedOutput.addAnswer(7, "There is no information on completed "
                    + "flights in the database (or incomplete data are given for them)");
        }
    }

    /**
     * Method for finding a flight with a maximum departure delay that has been
     * able to catch up to that delay and arrive on or before the scheduled time
     */
    void answerQuestion8() {
        if (completedFlights.size() > 0) {
            try {
                Flight maxLiquidatedDelay = completedFlights
                        .stream()
                        .filter(f -> !f.getTailNum().equals("Not filled"))
                        .filter(f -> f.getDayofMonth() != 0)
                        .filter(f -> f.getDiverted() != 1)
                        .filter(f -> f.getDepDelay() != 1000000)
                        .filter(f -> f.getArrDelay() <= 0)
                        .max((f1, f2) -> Integer.compare(f1.getDepDelay(), f2.getDepDelay()))
                        .get();

                formattedOutput.addAnswer(8, maxLiquidatedDelay.getDayofMonth() + ","
                        + maxLiquidatedDelay.getDepDelay() + ","
                        + maxLiquidatedDelay.getTailNum());
            } catch (Exception ex) {
                formattedOutput.addAnswer(8, "The database does not contain flights "
                        + "with complete information necessary to answer this question");
            }
        } else {
            formattedOutput.addAnswer(8, "There is no information on completed "
                    + "flights in the database (or incomplete data are given for them)");
        }
    }

    /**
     * Method calculates which of the states most often canceled flights due to
     * weather conditions
     */
    void answerQuestion9() {
        if (cancelledFlights.size() > 0) {
            Map<String, Long> osWeatherCancellationCode = cancelledFlights
                    .stream()
                    .filter(f -> !f.getOriginStateName().equals("Not filled"))
                    .filter(f -> !f.getCancellationCode().equals("Not filled"))
                    .filter(f -> f.getCancellationCode().equalsIgnoreCase("B"))
                    .collect(Collectors.groupingBy(Flight::getOriginStateName, Collectors.counting()));

            if (osWeatherCancellationCode.size() > 0) {
                formattedOutput.addAnswer(9, maxValueOfEntries(osWeatherCancellationCode, Long::compare).getKey());
            } else {
                formattedOutput.addAnswer(9, "There are no flights in the database "
                        + "with a reason for cancellation due to weather conditions (B) "
                        + "(or incomplete data are given on them)");
            }
        } else {
            formattedOutput.addAnswer(9, "There is no information in the database "
                    + "about cancelled flights (or incomplete data are given on them)");
        }
    }
}
