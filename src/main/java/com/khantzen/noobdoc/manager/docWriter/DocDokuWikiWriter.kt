package com.khantzen.noobdoc.manager.docWriter

import com.khantzen.noobdoc.configuration.model.Export
import com.khantzen.noobdoc.model.Documentation
import com.khantzen.noobdoc.model.Rule
import com.khantzen.noobdoc.model.RuleGroup
import com.khantzen.noobdoc.model.Section
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class DocDokuWikiWriter : IDocWriter {
    companion object {

        val KEY = "dokuWiki"
    }

    override fun writeDoc(doc: Documentation, export: Export): String {
        val finalString = StringBuilder()

        finalString.appendln("====== ${doc.title} ======")

        finalString.appendln()

        finalString.appendln(doc.description)

        for (section in doc.sectionList) {
            finalString.appendln()
            finalString.append(writeSection(section, doc, export))
        }

        finalString.append("Generated with NoobDoc")

        return finalString.toString()
    }

    override fun writeSection(section: Section, doc: Documentation, export: Export): String {
        val finalString = StringBuilder()
        var sectionTitle = section.title

        if (export.displaySectionCode)
            sectionTitle = "[${section.code}] $sectionTitle"

        finalString.appendln("===== $sectionTitle =====")

        for (ruleGroup in section.ruleGroupList) {
            finalString.appendln("==== ${ruleGroup.name} ====")

            for (rule in ruleGroup.ruleList) {
                var ruleDescription = rule.description

                if (export.displayRuleCode)
                    ruleDescription = "[${rule.code}]-$ruleDescription"

                ruleDescription = setIntraSectionLink(ruleDescription, doc.sectionList, export.namespace)
                ruleDescription = setInFileRuleLink(ruleDescription, section.ruleGroupList, export.namespace, section.title)

                finalString.appendln("$ruleDescription\\\\")
            }
            finalString.appendln()
        }

        if (!export.onePageDoc) {
            val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm")
            val calendar = Calendar.getInstance()
            val date = dateFormat.format(calendar.time)
            finalString.append(" --- // [[https://github.com/khantzen/NoobDoc|Generated with NoobDoc $date]]")
        }


        return finalString.toString()
    }

    private fun setIntraSectionLink(description: String, sectionList: List<Section>, dokuNameSpace: String): String {
        var finalDescription = description

        val sectionCodePattern = Pattern.compile("\\[@(?<sectionId>[^@].+?)]")
        val sectionCodeMatches = sectionCodePattern.matcher(description)

        while (sectionCodeMatches.find()) {
            val sectionCode = sectionCodeMatches.group("sectionId")
            val section = sectionList.firstOrNull { it.code == sectionCode } ?: continue

            val intraLinkAnchor =
                    section.title.toLowerCase().replace(" ", "_") +
                            "#" +
                            section.code.toLowerCase().replace(" ", "_")

            val intraLink = "([[$dokuNameSpace:$intraLinkAnchor|@${section.code}]])"

            finalDescription = sectionCodeMatches.replaceFirst(intraLink)
        }

        return finalDescription;
    }

    private fun setInFileRuleLink(description: String, ruleList: List<RuleGroup>, dokuNameSpace: String, currentSection: String): String {
        var finalDescription = description

        val ruleCodePattern = Pattern.compile("\\[@@(?<ruleGroup>.+?)]")
        val ruleCodeMatches = ruleCodePattern.matcher(description)

        while (ruleCodeMatches.find()) {
            val ruleGroupName = ruleCodeMatches.group("ruleGroup")
            val ruleGroup = ruleList.firstOrNull { it.name == ruleGroupName } ?: continue

            val intraLinkAnchor =
                    currentSection.toLowerCase().replace(" ", "_") +
                    "#" +
                    ruleGroup.name.toLowerCase().replace(" ", "_").replace("^\\d*-", "")

            val intraLink = "([[$dokuNameSpace:$intraLinkAnchor|@${ruleGroup.name}]])"

            finalDescription = ruleCodeMatches.replaceFirst(intraLink)
        }

        return finalDescription;
    }
}