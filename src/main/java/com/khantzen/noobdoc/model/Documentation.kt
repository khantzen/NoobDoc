package com.khantzen.noobdoc.model

data class Documentation(
        val title: String,
        val description: String?,
        val sectionList: MutableList<Section>
)