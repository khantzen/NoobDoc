package com.khantzen.noobdoc

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.readValue
import com.khantzen.noobdoc.configuration.ConfigManager
import com.khantzen.noobdoc.configuration.Configuration
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

            val docText = docWriter.writeDoc(doc)

            // Remplacer par un FileWriter
            println(docText)
        }

    }





