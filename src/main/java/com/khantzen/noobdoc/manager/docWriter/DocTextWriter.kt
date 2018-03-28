package com.khantzen.noobdoc.manager.docWriter

import com.khantzen.noobdoc.model.Documentation

class DocTextWriter : IDocWriter {
    companion object {
        val KEY = "text"
    }

    override fun writeDoc(doc: Documentation): String {
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