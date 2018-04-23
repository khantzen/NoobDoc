package com.khantzen.noobdoc.manager.docWriter

import com.khantzen.noobdoc.configuration.model.Export
import com.khantzen.noobdoc.model.Documentation
import com.khantzen.noobdoc.model.Section

class DocTextWriter : IDocWriter {
    override fun writeSection(section: Section, doc: Documentation, exportRule: Export): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        val KEY = "text"
    }

    override fun writeDoc(doc: Documentation, export: Export): String {
        val finalString = StringBuilder()

        finalString.appendln("=============== ${doc.title} ===============")
        finalString.appendln()
        finalString.appendln(doc.description)

        for(section in doc.sectionList)
        {
            finalString.appendln()
            finalString.appendln("[${section.code}]-${section.title}")

            for (ruleGroup in section.ruleGroupList) {
                finalString.appendln("\t##${ruleGroup.name}")

                for (rule in ruleGroup.ruleList) {
                    finalString.appendln("\t\t[${rule.code}]-${rule.description}")
                }
                finalString.appendln()
            }
        }

        finalString.append("Generated with NoobDoc")
        return finalString.toString()
    }

}