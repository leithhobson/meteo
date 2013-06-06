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

package no.api.meteo.services.internal;

import no.api.meteo.MeteoException;
import no.api.meteo.entity.core.service.locationforecast.LocationForecast;
import no.api.meteo.entity.core.service.locationforecast.PeriodForecast;
import no.api.meteo.entity.core.service.locationforecast.PointForecast;
import no.api.meteo.service.locationforecast.LocationforcastLTSParser;
import no.api.meteo.test.MeteoTestException;
import no.api.meteo.test.MeteoTestUtils;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class MeteoForecastIndexerTest {

    private LocationForecast locationForecast;

    private MeteoForecastIndexer indexer;

    @Before
    public void loadResources() throws MeteoTestException, MeteoException {
        String resource = MeteoTestUtils.getTextResource("/META-INF/meteo/locationsforecastlts/test1.xml");
        LocationforcastLTSParser parser = new LocationforcastLTSParser();
        locationForecast = parser.parse(resource);
        indexer = new MeteoForecastIndexer(locationForecast.getForecasts());
    }

    @Test
    public void testGetMatchingPeriodForecasts() throws Exception {
        Assert.assertTrue(locationForecast.getForecasts().get(12) instanceof PointForecast);
        PointForecast pointForecast = (PointForecast) locationForecast.getForecasts().get(12);
        List<PeriodForecast> periodForecastList = indexer.getMatchingPeriodForecasts(new DateTime(pointForecast.getFromTime()));
        Assert.assertEquals(16, periodForecastList.size());

        DateTime nonTime = (new DateTime()).withYear(1960);
        pointForecast.setFromTime(nonTime.toDate());
        pointForecast.setToTime(nonTime.toDate());
        Assert.assertEquals(0, indexer.getMatchingPeriodForecasts(new DateTime(pointForecast.getFromTime())).size());
    }

    @Test
    public void testGetBestFitPeriodForecast1() throws Exception {
        PeriodForecast p = indexer.getBestFitPeriodForecast(new DateTime("2011-05-06T04:00:00Z"), new DateTime("2011-05-06T10:00:00Z"));
        Assert.assertNotNull(p);
        Assert.assertEquals(new Integer(3), p.getSymbol().getNumber());
        Assert.assertEquals("PARTLYCLOUD", p.getSymbol().getId());
        DateTime dt = new DateTime(p.getFromTime());
        Assert.assertEquals(6, dt.getHourOfDay());
        DateTime tt = new DateTime(p.getToTime());
        Assert.assertEquals(12, tt.getHourOfDay());
    }

    @Test
    public void testGetBestFitPeriodForecast2() throws Exception {
        PeriodForecast p = indexer.getBestFitPeriodForecast(new DateTime("2011-05-06T03:00:00Z"), new DateTime("2011-05-06T11:00:00Z"));
        Assert.assertNotNull(p);
        Assert.assertEquals(new Integer(3), p.getSymbol().getNumber());
        Assert.assertEquals("PARTLYCLOUD", p.getSymbol().getId());
        DateTime dt = new DateTime(p.getFromTime());
        Assert.assertEquals(5, dt.getHourOfDay());
        DateTime tt = new DateTime(p.getToTime());
        Assert.assertEquals(11, tt.getHourOfDay());
    }

    @Test
    public void testGetBestFitPeriodForecast3() throws Exception {
        PeriodForecast p = indexer.getBestFitPeriodForecast(new DateTime("2011-05-06T05:00:00Z"), new DateTime("2011-05-06T09:00:00Z"));
        Assert.assertNotNull(p);
        Assert.assertEquals(new Integer(3), p.getSymbol().getNumber());
        Assert.assertEquals("PARTLYCLOUD", p.getSymbol().getId());
        DateTime dt = new DateTime(p.getFromTime());
        Assert.assertEquals(6, dt.getHourOfDay());
        DateTime tt = new DateTime(p.getToTime());
        Assert.assertEquals(12, tt.getHourOfDay());

    }

    @Test
    public void testGetTightestFitPeriodForecast() throws Exception {
        Assert.assertNull(indexer.getTightestFitScoreForecast(null));
        Assert.assertNull(indexer.getTightestFitPeriodForecast(null));
        PointForecast pointForecast = (PointForecast) locationForecast.getForecasts().get(12);
        Assert.assertNotNull(pointForecast);
        ScoreForecast matchingScoreForecast =
                indexer.getTightestFitScoreForecast(new DateTime(pointForecast.getFromTime()));
        Assert.assertNotNull(matchingScoreForecast);
        Assert.assertEquals(1, matchingScoreForecast.getPointTightScore());

        PeriodForecast periodForecast = indexer.getTightestFitPeriodForecast(new DateTime(pointForecast.getFromTime()));
        Assert.assertEquals(periodForecast.getFromTime(), matchingScoreForecast.getPeriodForecast().getFromTime());
    }

    @Test
    public void testGetWidestFitPeriodForecast() throws Exception {
        Assert.assertNull(indexer.getWidestFitScoreForecast(null));
        Assert.assertNull(indexer.getWidestFitPeriodForecast(null));
        PointForecast pointForecast = (PointForecast) locationForecast.getForecasts().get(12);
        Assert.assertNotNull(pointForecast);
        ScoreForecast matchingScoreForecast = indexer.getWidestFitScoreForecast(new DateTime(pointForecast.getFromTime()));
        Assert.assertNotNull(matchingScoreForecast);
        PeriodForecast periodForecast = indexer.getWidestFitPeriodForecast(new DateTime(pointForecast.getFromTime()));
        Assert.assertNotNull(periodForecast);
        Assert.assertNotNull(periodForecast.getFromTime());
        Assert.assertNotNull(periodForecast.getToTime());
        DateTime ft = new DateTime(periodForecast.getFromTime());
        DateTime tt = new DateTime(periodForecast.getToTime());
        // TODO Add real tests here . Remember to convert into GMT to avoid tests from failing when summer time is over in Norway
    }

    @Test
    public void testPeriodIndexKey() throws Exception {
        HourIndexKey p1 = new HourIndexKey((new DateTime()).withYear(1970));
        HourIndexKey p2 = new HourIndexKey((new DateTime()).withYear(1980));
        Assert.assertFalse(p1.equals(null));
        Assert.assertFalse(p2.equals(null));
        Assert.assertTrue(p1.equals(p1));
        Assert.assertFalse(p1.equals(p2));

        p1 = new HourIndexKey((new DateTime()).withMonthOfYear(2));
        p2 = new HourIndexKey((new DateTime()).withMonthOfYear(3));
        Assert.assertFalse(p1.equals(null));
        Assert.assertFalse(p2.equals(null));
        Assert.assertTrue(p1.equals(p1));
        Assert.assertFalse(p1.equals(p2));

        p1 = new HourIndexKey((new DateTime()).withDayOfMonth(1));
        p2 = new HourIndexKey((new DateTime()).withDayOfMonth(2));
        Assert.assertFalse(p1.equals(null));
        Assert.assertFalse(p2.equals(null));
        Assert.assertTrue(p1.equals(p1));
        Assert.assertFalse(p1.equals(p2));

        p1 = new HourIndexKey((new DateTime()).withHourOfDay(3));
        p2 = new HourIndexKey((new DateTime()).withHourOfDay(5));
        Assert.assertFalse(p1.equals(null));
        Assert.assertFalse(p2.equals(null));
        Assert.assertTrue(p1.equals(p1));
        Assert.assertFalse(p1.equals(p2));
    }

    @Test
    public void testGetPointForecast() throws Exception {
        DateTime testTime = (new DateTime()).withYear(2011).withMonthOfYear(5).withDayOfMonth(6).withHourOfDay(16);
        PointForecast pointForecast = indexer.getPointForecast(testTime);
        Assert.assertNotNull(pointForecast);
        Assert.assertEquals(100.0, pointForecast.getHighClouds().getPercent(), 0.);

        testTime = (new DateTime()).withYear(2001).withMonthOfYear(5).withDayOfMonth(6).withHourOfDay(16);
        pointForecast = indexer.getPointForecast(testTime);
        Assert.assertNull(pointForecast);
    }
}