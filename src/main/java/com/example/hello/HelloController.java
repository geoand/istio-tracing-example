/*
 * Copyright 2018 Red Hat, Inc, and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.hello;

import io.opentracing.ActiveSpan;
import io.opentracing.Tracer;
import io.opentracing.tag.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author <a href="mailto:gytis@redhat.com">Gytis Trikleris</a>
 */
@RestController
public class HelloController {

    private final Tracer tracer;

    private final RestTemplate restTemplate;

    @Autowired
    public HelloController(Tracer tracer, RestTemplate restTemplate) {
        this.tracer = tracer;
        this.restTemplate = restTemplate;
    }

    @RequestMapping
    public String hello() {
        String name;
        try (ActiveSpan span = tracer.buildSpan("get-name")
                .startActive()) {
            Tags.COMPONENT.set(span, "hello-controller");
            name  = restTemplate.getForObject("http://hello:8080/name", String.class);
        }

        return String.format("Hello, %s!", name);
    }

    @RequestMapping(path = "/name")
    public String name() {
        return "World";
    }

}
