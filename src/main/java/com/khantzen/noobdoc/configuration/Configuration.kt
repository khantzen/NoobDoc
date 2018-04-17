package com.khantzen.noobdoc.configuration

import com.khantzen.noobdoc.configuration.model.Export

data class Configuration constructor(var exportList: Map<String, Export>)
