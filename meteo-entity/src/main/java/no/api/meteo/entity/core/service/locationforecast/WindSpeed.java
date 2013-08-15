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

import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import no.api.meteo.entity.core.IdEntity;

public final class WindSpeed extends IdEntity {

    @NotNull
    private Integer beaufort;

    @NotNull
    private Double mps;

    @NotNull
    @NotEmpty
    private String name;

    /**
     * Constructor with no initial values
     */
    public WindSpeed() {
        super();
    }

    public WindSpeed(String id, Integer beaufort,
                     Double mps, String name) {
        setId(id);
        this.beaufort = beaufort;
        this.mps = mps;
        this.name = name;
    }

    public Integer getBeaufort() {
        return beaufort;
    }

    public void setBeaufort(Integer beaufort) {
        this.beaufort = beaufort;
    }

    public Double getMps() {
        return mps;
    }

    public void setMps(Double mps) {
        this.mps = mps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
