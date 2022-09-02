package com.example;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import org.springframework.web.reactive.config.ResourceHandlerRegistration;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.resource.VersionResourceResolver;

@Controller
public class GreetingController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 인터셉터를 쓰지 않고 response에 직접 헤더값을 지정할 수도 있다.
     */
    @GetMapping("/cache-control")
    public String cacheControl(final HttpServletResponse response) {
        final String cacheControl = CacheControl
                .noCache()
                .cachePrivate()
                .getHeaderValue();
        response.addHeader(HttpHeaders.CACHE_CONTROL, cacheControl);
        return "index";
    }

    @GetMapping("/etag")
    public String etag(final HttpServletResponse response) {
        return "index";
    }

    @GetMapping("/resource-versioning")
    public String resourceVersioning() {
//        resourceHandlerRegistry.addResourceHandler("/static/js/**")
//                .addResourceLocations("/static/js/")
//                .setCacheControl(CacheControl.maxAge(7, TimeUnit.DAYS))
//                .resourceChain(false)
//                .addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"));
        return "resource-versioning";
    }
}
