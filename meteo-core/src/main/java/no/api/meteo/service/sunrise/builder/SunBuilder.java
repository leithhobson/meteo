/*
 * Copyright (c) 2011-2015 Amedia Utvikling AS.
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

package no.api.meteo.service.sunrise.builder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import no.api.meteo.entity.core.service.sunrise.ErrorType;
import no.api.meteo.entity.core.service.sunrise.Noon;
import no.api.meteo.entity.core.service.sunrise.Sun;
import no.api.meteo.entity.core.service.sunrise.TwilightType;
import no.api.meteo.util.EntityBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
public class SunBuilder implements EntityBuilder<Sun> {

    @Setter
    @Getter
    private Date rise;

    @Setter
    @Getter
    private Date set;

    @Setter
    @Getter
    private Boolean neverRise;

    @Setter
    @Getter
    private Boolean neverSet;

    @Setter
    @Getter
    private List<ErrorType> error;

    @Setter
    @Getter
    private Double daylength;

    @Setter
    @Getter
    private List<Noon> noon = new ArrayList<>();

    @Setter
    @Getter
    private List<TwilightType> twilight = new ArrayList<>();


    @Override
    public Sun build() {
        return new Sun(getRise(), getSet(), getNeverRise(), getNeverSet(), getError(), getDaylength(), getNoon(),
                       getTwilight());
    }
}