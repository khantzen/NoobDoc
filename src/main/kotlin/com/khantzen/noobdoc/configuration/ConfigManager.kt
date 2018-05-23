package com.khantzen.noobdoc.configuration

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File
import java.io.FileInputStream

class ConfigManager {


companion object {
    fun getConfig(): Configuration {
        var configFile = File("config.yml")
        if (!configFile.exists())
            configFile = File(ConfigManager::class.java.classLoader.getResource("default-config.yml").file)

        val configFileStream = FileInputStream(configFile)

        val mapper = ObjectMapper(YAMLFactory()).registerKotlinModule()

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        return mapper.readValue(configFileStream, Configuration::class.java)
    }
}

}