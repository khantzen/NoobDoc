package com.khantzen.noobdoc.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import java.io.File
import java.io.FileInputStream

class ConfigManager {
    companion object {
        fun loadConfig() {
            val mapper = ObjectMapper(YAMLFactory())

            var configFile = File("config.yml")

            if (!configFile.exists())
                configFile = FileInputStream("default-config.yml")

            val configFileStream = FileInputStream(configFile)
        }
    }
}