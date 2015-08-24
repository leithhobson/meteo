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

package no.api.meteo.entity.core.service.textforecast;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@ToString
@EqualsAndHashCode
public class Area {

    @JsonProperty
    private String type;

    @JsonProperty
    private String name;

    @JsonProperty
    private Integer id;

    @JsonProperty
    private Location location;

    @JsonCreator
    public Area(@JsonProperty("type") String type,
                @JsonProperty("name") String name,
                @JsonProperty("id") Integer id,
                @JsonProperty("areas") Location location) {
        this.type = type;
        this.name = name;
        this.id = id;
        this.location = location;
    }
}
