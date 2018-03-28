package com.khantzen.noobdoc.manager.docWriter

import com.khantzen.noobdoc.model.Documentation

class DocMarkDownWriter : IDocWriter {

    companion object {
        val KEY: String = "markdown"
    }

    override fun writeDoc(doc: Documentation): String {
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