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

package no.api.meteo.entity.core.service.locationforecast;

import net.sf.oval.constraint.NotNull;
import no.api.meteo.entity.core.Location;
import no.api.meteo.entity.core.RootEntity;

import java.util.ArrayList;
import java.util.List;

public class LocationForecast extends RootEntity {

    @NotNull
    private Location location;

    @NotNull
    private List<Forecast> forecasts = new ArrayList<>();

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Forecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<Forecast> forecasts) {
        if (forecasts != null) {
            this.forecasts = forecasts;
        }
    }
}
