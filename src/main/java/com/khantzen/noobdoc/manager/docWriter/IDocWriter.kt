package com.khantzen.noobdoc.manager.docWriter

import com.khantzen.noobdoc.model.Documentation

interface IDocWriter {
    fun writeDoc(doc: Documentation) : String
}