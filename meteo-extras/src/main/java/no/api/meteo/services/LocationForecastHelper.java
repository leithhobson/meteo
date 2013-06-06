/*
 * Copyright (c) 2011-2013 Amedia AS.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package no.api.meteo.services;

import no.api.meteo.MeteoException;
import no.api.meteo.entity.core.service.locationforecast.Forecast;
import no.api.meteo.entity.core.service.locationforecast.LocationForecast;
import no.api.meteo.entity.core.service.locationforecast.PeriodForecast;
import no.api.meteo.entity.core.service.locationforecast.PointForecast;
import no.api.meteo.entity.extras.MeteoExtrasForecast;
import no.api.meteo.entity.extras.MeteoExtrasForecastDay;
import no.api.meteo.entity.extras.MeteoExtrasLongTermForecast;
import no.api.meteo.services.internal.MeteoForecastIndexer;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class LocationForecastHelper {

    private LocationForecast locationForecast;

    private MeteoForecastIndexer indexer = null;

    private String title = null;

    public LocationForecastHelper(LocationForecast locationForecast, String title) {
        init(locationForecast);
        this.title = title;
    }

    public LocationForecastHelper(LocationForecast locationForecast) {
        init(locationForecast);
    }

    private void init(LocationForecast locationForecast) {
        this.locationForecast = locationForecast;
        if (this.locationForecast != null) {
            indexer = new MeteoForecastIndexer(locationForecast.getForecasts());
        }
    }

    public String getTitle() {
        return title;
    }

    /**
     * @deprecated Use LocationForecasHelper.getHourlyPointForecastsFromNow(hoursAhead) instead.
     */
    @Deprecated
    public List<MeteoExtrasForecast> getPointForecastsByHour(int hours) throws MeteoException {
        return findHourlyPointForecastsFromNow(hours);
    }

    public List<MeteoExtrasForecast> findHourlyPointForecastsFromNow(int hoursAhead)
            throws MeteoException {

        validateIndexer();
        if (!validData()) {
            return null;
        }

        List<MeteoExtrasForecast> pointExtrasForecasts = new ArrayList<MeteoExtrasForecast>();

        DateTime timeNow = new DateTime();
        for (int i = 0; i < hoursAhead; i++) {
            DateTime dataTime = timeNow.plusHours(i);
            PointForecast pointForecast = indexer.getPointForecast(dataTime);
            if (pointForecast != null) {
                PeriodForecast periodForecast =
                        indexer.getTightestFitPeriodForecast(new DateTime(pointForecast.getFromTime()));
                pointExtrasForecasts.add(new MeteoExtrasForecast(periodForecast, pointForecast));
            }
        }
        return pointExtrasForecasts;
    }

    /**
     * @deprecated Use #findBestForecastForPeriod(from, to) instead
     */
    @Deprecated
    public MeteoExtrasForecast getBestForecastForPeriod(DateTime from, DateTime to) {
        return findBestForecastForPeriod(from, to);
    }

    public MeteoExtrasForecast findBestForecastForPeriod(DateTime from, DateTime to) {
        PeriodForecast periodForecast = indexer.getBestFitPeriodForecast(from, to);
        if (periodForecast == null) {
            return null;
        }
        PointForecast pointForecast = indexer.getPointForecast(new DateTime(periodForecast.getFromTime()));
        if (periodForecast == null || pointForecast == null) {
            return null;
        }
        return new MeteoExtrasForecast(periodForecast, pointForecast);
    }

    public MeteoExtrasLongTermForecast createLongTermForecast() {
        List<MeteoExtrasForecastDay> forecastDays = new ArrayList<MeteoExtrasForecastDay>();
        DateTime dt = DateTime.now();
        addForecastForDay(dt, forecastDays);
        addForecastForDay(dt.plusDays(1), forecastDays);
        addForecastForDay(dt.plusDays(2), forecastDays);
        addForecastForDay(dt.plusDays(3), forecastDays);
        addForecastForDay(dt.plusDays(4), forecastDays);
        addForecastForDay(dt.plusDays(5), forecastDays);
        addForecastForDay(dt.plusDays(6), forecastDays);

        return new MeteoExtrasLongTermForecast(forecastDays);
    }

    private void addForecastForDay(DateTime dt, List<MeteoExtrasForecastDay> lst) {
        if (indexer.hasForecastsForDay(dt)) {
            MeteoExtrasForecastDay mefd = createForcastForDay(dt);
            if (mefd != null && mefd.getForecasts().size() > 0) {
                lst.add(mefd);

            }
        }
    }

    public MeteoExtrasForecastDay createForcastForDay(DateTime dt) {
        List<MeteoExtrasForecast> forecasts = new ArrayList<MeteoExtrasForecast>();
        addForecastToList(findBestForecastForPeriod(dt.withHourOfDay(0), dt.withHourOfDay(6)), forecasts);
        addForecastToList(findBestForecastForPeriod(dt.withHourOfDay(6), dt.withHourOfDay(12)), forecasts);
        addForecastToList(findBestForecastForPeriod(dt.withHourOfDay(12), dt.withHourOfDay(18)), forecasts);
        addForecastToList(findBestForecastForPeriod(dt.withHourOfDay(18), dt.plusDays(1).withHourOfDay(0)), forecasts);
        return new MeteoExtrasForecastDay(forecasts, dt.toDate());
    }

    private void addForecastToList(MeteoExtrasForecast mef, List<MeteoExtrasForecast> lst) {
        if (mef != null) {
            lst.add(mef);

        }
    }

    private boolean validData() {
        if (locationForecast == null) {
            return false;
        }
        return true;
    }

    private void validateIndexer() throws MeteoException {
        if (indexer == null) {
            throw new MeteoException("Indexer haven't been initialized. " +
                    "Something went wrong during fetching of the data");
        }
    }

    public MeteoExtrasForecast getNearestForecast() throws MeteoException {
        return findNearestForecast(new Date());
    }

    /**
     * @deprecated
     */
    @Deprecated
    public MeteoExtrasForecast getNearestForecast(Date date) throws MeteoException {
        return findNearestForecast(date);
    }

    public MeteoExtrasForecast findNearestForecast(Date date) throws MeteoException {
        if (date == null) {
            throw new MeteoException("Input date is null");
        }
        return findNearestForecast(new DateTime(date));
    }

    /**
     * @deprecated
     */
    @Deprecated
    public MeteoExtrasForecast getNearestForecast(DateTime date) throws MeteoException {
        return findNearestForecast(date);
    }

    public MeteoExtrasForecast findNearestForecast(DateTime date) throws MeteoException {
        validateIndexer();

        if (!validData()) {
            return null;
        }

        PointForecast chosenForecast = null;
        for (Forecast forecast : locationForecast.getForecasts()) {
            if (forecast instanceof PointForecast) {
                PointForecast pointForecast = (PointForecast) forecast;
                if (isDateMatch(date, new DateTime(pointForecast.getFromTime()))) {
                    chosenForecast = pointForecast;
                    break;
                } else if (chosenForecast == null) {
                    chosenForecast = pointForecast;
                } else if (isNearerDate(new DateTime(pointForecast.getFromTime()), date,
                        new DateTime(chosenForecast.getFromTime()))) {
                    chosenForecast = pointForecast;
                }
            }
        }
        return new MeteoExtrasForecast(indexer.getWidestFitPeriodForecast(new DateTime(chosenForecast.getFromTime())),
                chosenForecast);
    }

    private boolean isNearerDate(DateTime pointTime, DateTime dateTime, DateTime chosenTime) {
        if (Math.abs(pointTime.getMillis() - dateTime.getMillis()) <
                Math.abs(chosenTime.getMillis() - dateTime.getMillis())) {
            return true;
        }
        return false;
    }

    private boolean isDateMatch(DateTime requestedDate, DateTime actualDate) {
        if (requestedDate.getYear() == actualDate.getYear() &&
                requestedDate.getMonthOfYear() == actualDate.getMonthOfYear()
                && requestedDate.getDayOfMonth() == actualDate.getDayOfMonth() &&
                requestedDate.getHourOfDay() == actualDate.getHourOfDay()) {
            return true;
        }
        return false;
    }


}
