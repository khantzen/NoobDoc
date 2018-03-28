package com.khantzen.noobdoc

import com.khantzen.noobdoc.configuration.model.User
import com.khantzen.noobdoc.manager.DocWriterFactory
import com.khantzen.noobdoc.manager.DocumentationBuilder
import com.khantzen.noobdoc.model.Documentation


//@noobDocTitle("NoobDoc Documentation")

//@noobDocDescription("NoobDoc is a kotlin program extracting your code business documentation directly from your inline comments following a specific format.")
fun main(args: Array<String>) {

    if (args.size < 3) {
        throw IllegalArgumentException(
                "This programm should be launch with at least 3 arguments \r\n" +
                        "java -jar noobDoc.jar tagPrefix language sourceRootPath (outPath)"
        )
    }

    val config = User(args[0], args[1], args[2])

    val docBuilder = DocumentationBuilder(config)
    val doc: Documentation = docBuilder.build()!!


    val docWriter = DocWriterFactory.buildWriter("markdown")

    val docText = docWriter.writeDoc(doc)

    val j = 1+1

    println(docText)
}



