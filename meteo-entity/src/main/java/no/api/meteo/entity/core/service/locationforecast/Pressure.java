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

public class Pressure extends UnitEntity {

    /**
     * Constructor with no initial values
     */
    public Pressure() {
        super();
    }

    public Pressure(String id, String unit, Double value) {
        setId(id);
        setUnit(unit);
        setValue(value);
    }
}
