package com.khantzen.noobdoc.manager.docWriter

import com.khantzen.noobdoc.configuration.model.Export
import com.khantzen.noobdoc.model.Documentation
import com.khantzen.noobdoc.model.Section

class DocMarkDownWriter : IDocWriter {
    override fun writeSection(section: Section, doc: Documentation, exportRule: Export): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        val KEY: String = "markdown"
    }

    override fun writeDoc(doc: Documentation, export: Export): String {
        val finalDoc = StringBuilder()

        finalDoc.appendln("# ${doc.title}")

        finalDoc.appendln()
        finalDoc.appendln(doc.description)

        for(section in doc.sectionList)
        {
            finalDoc.appendln()
            finalDoc.appendln("## [${section.code}]-${section.title}")

            for (ruleGroup in section.ruleGroupList) {
                finalDoc.appendln("### ${ruleGroup.name}")

                for (rule in ruleGroup.ruleList) {
                    finalDoc.appendln("- [${rule.code}]-${rule.description.replace("<", "\\<").replace(">", "\\>")}")
                }
                finalDoc.appendln()
            }
        }

        return finalDoc.toString()
    }

}