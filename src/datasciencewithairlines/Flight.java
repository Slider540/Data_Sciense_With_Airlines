package datasciencewithairlines;

import java.time.LocalDate;

/**
 * Сlass that is used to describe the flight data model
 */
public class Flight {

    private int dayofMonth;
    private int dayOfWeek;
    private LocalDate flightDate;
    private String uniqueCarrier;
    private String tailNum;
    private String originAirportID;
    private String origin;
    private String originStateName;
    private String destAirportID;
    private String dest;
    private String destStateName;
    private int depTime;
    private int depDelay;
    private int wheelsOff;
    private int wheelsOn;
    private int arrTime;
    private int arrDelay;
    private int cancelled;
    private String cancellationCode;
    private int diverted;
    private int airTime;
    private int distance;

    @Override
    public String toString() {
        return "Flight{" + "dayofMonth=" + dayofMonth + ", dayOfWeek=" 
                + dayOfWeek + ", flightDate=" + flightDate + ", uniqueCarrier=" 
                + uniqueCarrier + ", tailNum=" + tailNum + ", originAirportID=" 
                + originAirportID + ", origin=" + origin + ", originStateName=" 
                + originStateName + ", destAirportID=" + destAirportID + ", dest=" 
                + dest + ", destStateName=" + destStateName + ", depTime=" + depTime 
                + ", depDelay=" + depDelay + ", wheelsOff=" + wheelsOff + ", wheelsOn=" 
                + wheelsOn + ", arrTime=" + arrTime + ", arrDelay=" + arrDelay 
                + ", cancelled=" + cancelled + ", cancellationCode=" + cancellationCode 
                + ", diverted=" + diverted + ", airTime=" + airTime + ", distance=" + distance + '}';
    }

    public int getDayofMonth() {
        return dayofMonth;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public LocalDate getFlightDate() {
        return flightDate;
    }

    public String getUniqueCarrier() {
        return uniqueCarrier;
    }

    public String getTailNum() {
        return tailNum;
    }

    public String getOriginAirportID() {
        return originAirportID;
    }

    public String getOrigin() {
        return origin;
    }

    public String getOriginStateName() {
        return originStateName;
    }

    public String getDestAirportID() {
        return destAirportID;
    }

    public String getDest() {
        return dest;
    }

    public String getDestStateName() {
        return destStateName;
    }

    public int getDepTime() {
        return depTime;
    }

    public int getDepDelay() {
        return depDelay;
    }

    public int getWheelsOff() {
        return wheelsOff;
    }

    public int getWheelsOn() {
        return wheelsOn;
    }

    public int getArrTime() {
        return arrTime;
    }

    public int getArrDelay() {
        return arrDelay;
    }

    public int getCancelled() {
        return cancelled;
    }

    public String getCancellationCode() {
        return cancellationCode;
    }

    public int getDiverted() {
        return diverted;
    }

    public int getAirTime() {
        return airTime;
    }

    public int getDistance() {
        return distance;
    }

    /**
     * Builder for flight objects
     */
    public static class Builder {

        private Flight newFlight;

        public Builder() {
            newFlight = new Flight();
        }

        public Builder withDayofMonth(int dayofMonth) {
            newFlight.dayofMonth = dayofMonth;
            return this;
        }

        public Builder withDayOfWeek(int dayOfWeek) {
            newFlight.dayOfWeek = dayOfWeek;
            return this;
        }

        public Builder withFlightDate(LocalDate flightDate) {
            newFlight.flightDate = flightDate;
            return this;
        }

        public Builder withUniqueCarrier(String uniqueCarrier) {
            newFlight.uniqueCarrier = uniqueCarrier;
            return this;
        }

        public Builder withTailNum(String tailNum) {
            newFlight.tailNum = tailNum;
            return this;
        }

        public Builder withOriginAirportID(String originAirportID) {
            newFlight.originAirportID = originAirportID;
            return this;
        }

        public Builder withOrigin(String origin) {
            newFlight.origin = origin;
            return this;
        }

        public Builder withOriginStateName(String originStateName) {
            newFlight.originStateName = originStateName;
            return this;
        }

        public Builder withDestAirportID(String destAirportID) {
            newFlight.destAirportID = destAirportID;
            return this;
        }

        public Builder withDest(String dest) {
            newFlight.dest = dest;
            return this;
        }

        public Builder withDestStateName(String destStateName) {
            newFlight.destStateName = destStateName;
            return this;
        }

        public Builder withDepTime(int depTime) {
            newFlight.depTime = depTime;
            return this;
        }

        public Builder withDepDelay(int depDelay) {
            newFlight.depDelay = depDelay;
            return this;
        }

        public Builder withWheelsOff(int wheelsOff) {
            newFlight.wheelsOff = wheelsOff;
            return this;
        }

        public Builder withWheelsOn(int wheelsOn) {
            newFlight.wheelsOn = wheelsOn;
            return this;
        }

        public Builder withArrTime(int arrTime) {
            newFlight.arrTime = arrTime;
            return this;
        }

        public Builder withArrDelay(int arrDelay) {
            newFlight.arrDelay = arrDelay;
            return this;
        }

        public Builder withCancelled(int cancelled) {
            newFlight.cancelled = cancelled;
            return this;
        }

        public Builder withCancellationCode(String cancellationCode) {
            newFlight.cancellationCode = cancellationCode;
            return this;
        }

        public Builder withDiverted(int diverted) {
            newFlight.diverted = diverted;
            return this;
        }

        public Builder withAirTime(int airTime) {
            newFlight.airTime = airTime;
            return this;
        }

        public Builder withDistance(int distance) {
            newFlight.distance = distance;
            return this;
        }

        public Flight build() {
            return newFlight;
        }
    }
}
