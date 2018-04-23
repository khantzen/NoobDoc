package com.khantzen.noobdoc

import com.khantzen.noobdoc.configuration.ConfigManager
import com.khantzen.noobdoc.configuration.model.User
import com.khantzen.noobdoc.manager.DocWriterFactory
import com.khantzen.noobdoc.manager.DocumentationBuilder
import com.khantzen.noobdoc.model.Documentation
import java.io.File
import java.nio.charset.Charset


//@noobDocTitle("NoobDoc Documentation")

//@noobDocDescription("NoobDoc is a kotlin program extracting your code business documentation directly from your inline comments following a specific format.")


fun main(args: Array<String>) {

    if (args.size < 3) {
        throw IllegalArgumentException(
                "This programm should be launch with at least 3 arguments \r\n" +
                        "java -jar noobDoc.jar tagPrefix language sourceRootPath (outPath)"
        )
    }

    val configUser = User(args[0], args[1], args[2])

    val config = ConfigManager.getConfig()


    val exportList = config.exportList.filter { it.value.activated }

    if (exportList.isEmpty()) {
        System.out.println("No export activated.");
        return
    }

    val docBuilder = DocumentationBuilder(configUser)
    val doc: Documentation = docBuilder.build()!!

    for (exportEntry in exportList) {
        val docWriterKey = exportEntry.key
        val export = exportEntry.value

        val docWriter = DocWriterFactory.buildWriter(docWriterKey)

        if (export.onePageDoc) {
            val docText = docWriter.writeDoc(doc, export)
            println(docText)
        } else {
            for (section in doc.sectionList) {
                val docText = docWriter.writeSection(section, doc, export)
                println(docText)
                val sectionDocFile = File(export.outFolderPath + section.title.toLowerCase().replace(" ", "_") + ".txt")
                sectionDocFile.writeText(docText, Charset.defaultCharset())
            }
        }
    }

}





