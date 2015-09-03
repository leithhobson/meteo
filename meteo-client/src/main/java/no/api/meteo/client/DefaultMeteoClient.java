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

package no.api.meteo.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SchemeRegistryFactory;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * The default Meteo Client using the Apache HTTPClient for fetching data from http://api.met.no
 */
@Slf4j
public class DefaultMeteoClient implements MeteoClient {

    public static final String CAUGHT_EXCEPTION_WHILE_FETCHING_CONTENT = "Caught exception while fetching content";

    private final DefaultHttpClient httpClient;

    /**
     * Client constructor using default timeout settings.
     */
    public DefaultMeteoClient() {
        httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager());
    }

    /**
     * Client constructor that takes connection time as input.
     *
     * @param timeout The connection timeout in seconds.
     */
    public DefaultMeteoClient(int timeout) {
        httpClient = new DefaultHttpClient(
                new ThreadSafeClientConnManager(SchemeRegistryFactory.createDefault(), timeout, TimeUnit.SECONDS));
    }

    @Override
    public void setProxy(String hostname, int port) {
        httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost(hostname, port));
    }

    @Override
    public void setTimeout(int timeout) {
        HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), timeout);
        HttpConnectionParams.setSoTimeout(httpClient.getParams(), timeout);
    }

    @Override
    public void shutdown() {
        httpClient.getConnectionManager().shutdown();
    }

    @Override
    public MeteoResponse fetchContent(URI uri) throws MeteoClientException {
        try {
            log.debug("Going to fetch: " + uri.toString());
            HttpGet httpget = new HttpGet(uri);

            HttpResponse response = httpClient.execute(httpget);
            if (response.getStatusLine().getStatusCode() != 200 &&
                    response.getStatusLine().getStatusCode() != 203) {
                throw new MeteoClientException(
                        "The request failed with error code " + response.getStatusLine().getStatusCode() + " : " +
                                response.getStatusLine().getReasonPhrase());
            }
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                return new MeteoResponse(EntityUtils.toString(entity),
                                         createMeteoResponseHeaders(response),
                                         response.getStatusLine().getStatusCode(),
                                         response.getStatusLine().getReasonPhrase());
            } else {
                throw new MeteoClientException("No content returned from request: " + uri.toString());
            }
        } catch (IOException e) {
            throw new MeteoClientException(CAUGHT_EXCEPTION_WHILE_FETCHING_CONTENT, e);
        } finally {
            httpClient.getConnectionManager().closeExpiredConnections();
        }
    }

    private List<MeteoResponseHeader> createMeteoResponseHeaders(HttpResponse response) {
        List<MeteoResponseHeader> headers = new ArrayList<>();
        for (Header header : response.getAllHeaders()) {
            log.debug("Adding header : " + header.toString());
            headers.add(new MeteoResponseHeader(header.getName(), header.getValue()));
        }
        return headers;
    }

}
