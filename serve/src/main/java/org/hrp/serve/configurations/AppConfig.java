package org.hrp.serve.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.api.scripting.NashornScriptEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.script.ScriptEngineManager;

@Configuration
@ComponentScan("org.hrp.lib")
public class AppConfig {

    @Bean
    public NashornScriptEngine scriptEngine() {
        ScriptEngineManager manager = new ScriptEngineManager();
        return (NashornScriptEngine) manager.getEngineByName("JavaScript");
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
