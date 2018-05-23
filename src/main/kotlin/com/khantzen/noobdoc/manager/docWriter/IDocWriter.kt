package com.khantzen.noobdoc.manager.docWriter

import com.khantzen.noobdoc.configuration.model.Export
import com.khantzen.noobdoc.model.Documentation
import com.khantzen.noobdoc.model.Section

interface IDocWriter {
    fun writeDoc(doc: Documentation, exportRule: Export) : String
    fun writeSection(section: Section, doc: Documentation, exportRule: Export) : String
}