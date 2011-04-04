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

import no.api.meteo.MeteoData;
import no.api.meteo.entity.LocationForecast;
import org.junit.Assert;
import org.junit.Test;

public class LocationExampleTest {

    @Test
    public void test_run_example() throws Exception {
        LocationExample locationExample = new LocationExample();
        MeteoData<LocationForecast> meteoData = locationExample.runExample();
        Assert.assertNotNull(meteoData);
        Assert.assertNotNull(meteoData.getRawResult());
        locationExample.shutDown();
    }
}
