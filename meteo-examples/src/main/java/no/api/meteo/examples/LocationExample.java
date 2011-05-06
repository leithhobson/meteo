/*
 * Copyright (c) 2011 A-pressen Digitale Medier
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

package no.api.meteo.examples;

import no.api.meteo.entity.MeteoData;
import no.api.meteo.client.DefaultMeteoClient;
import no.api.meteo.client.MeteoClient;
import no.api.meteo.client.MeteoClientException;
import no.api.meteo.entity.Coordinates;
import no.api.meteo.service.locationforecastlts.entity.Location;
import no.api.meteo.service.locationforecastlts.entity.LocationForecast;
import no.api.meteo.service.locationforecastlts.LocationforecastLTSService;
import no.api.meteo.service.locationforecastlts.entity.Model;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocationExample {

    public static final double LONGITUDE_OSLO = 10.7460923576733;

    public static final double LATITUDE_OSLO = 59.912726542422;

    public static final int ALTITUDE_OSLO = 14;

    private static final Logger log = LoggerFactory.getLogger(LocationExample.class);

    private MeteoClient meteoClient;

    public LocationExample() {
        BasicConfigurator.configure();
        meteoClient = new DefaultMeteoClient();
    }

    public MeteoData<LocationForecast> runExample() {
        LocationforecastLTSService ltsService = new LocationforecastLTSService(meteoClient);
        try {
            // Fetch the data from api.met.no

            return ltsService.fetchContent(new Coordinates(LONGITUDE_OSLO, LATITUDE_OSLO, ALTITUDE_OSLO));
        } catch (MeteoClientException e) {
            // Got client exception. No data available
            log.error("Caught exception : " + e.getMessage());
            return null;
        }
    }

    public void shutDown() {
        meteoClient.shutdown();
    }

    public static void main(String[] args) {
        LocationExample locationExample = new LocationExample();
        MeteoData<LocationForecast> data = locationExample.runExample();

        // Just to prove that we have data
        log.info(data.getResult().getMeta().getLicenseUrl().toString());
        for (Model m : data.getResult().getMeta().getModels()) {
            log.info("Model Name: " + m.getName());
        }
    }
}