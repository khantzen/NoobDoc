package com.khantzen.noobdoc.manager

import com.khantzen.noobdoc.manager.docWriter.DocDokuWikiWriter
import com.khantzen.noobdoc.manager.docWriter.DocMarkDownWriter
import com.khantzen.noobdoc.manager.docWriter.DocTextWriter
import com.khantzen.noobdoc.manager.docWriter.IDocWriter

class DocWriterFactory {

    companion object {
        fun buildWriter(key: String): IDocWriter {
            return when (key) {
                DocTextWriter.KEY -> DocTextWriter()
                DocDokuWikiWriter.KEY -> DocDokuWikiWriter()
                DocMarkDownWriter.KEY -> DocMarkDownWriter()
                else ->
                    throw IllegalArgumentException(
                            "No doc writer available for type : $key, " +
                                    "available type are html / dokuWiki / markdown / text"
                    )
            }
        }
    }
}