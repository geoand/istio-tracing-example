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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:gytis@redhat.com">Gytis Trikleris</a>
 */
@RestController
public class HelloController {

    private final Tracer tracer;

    public HelloController(Tracer tracer) {
        this.tracer = tracer;
    }

    @RequestMapping
    public String hello() {
        try (ActiveSpan span = tracer.buildSpan("get-message")
                .startActive()) {
            Tags.COMPONENT.set(span, "hello-controller");
            return getMessage();
        }
    }

    private String getMessage() {
        return "Hello World!";
    }

}
