package org.hrp.serve.configurations;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.script.ScriptEngineManager;

@Configuration
public class AppConfig {

    @Bean
    public NashornScriptEngine scriptEngine() {
        ScriptEngineManager manager = new ScriptEngineManager();
        return (NashornScriptEngine) manager.getEngineByName("JavaScript");
    }
}
