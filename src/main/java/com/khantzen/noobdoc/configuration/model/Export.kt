package com.khantzen.noobdoc.configuration.model

data class Export(
        val activated: Boolean,
        val outFolderPath: String,
        val theme: String
)