package com.khantzen.noobdoc.manager

import com.khantzen.noobdoc.model.Rule
import com.khantzen.noobdoc.model.RuleGroup
import com.khantzen.noobdoc.model.Section
import java.io.File
import java.util.regex.Pattern

//@noobDocSection("NoobDoc-02", "NoobDoc Tag Finder")
class NoobDocTagFinder {

    fun getDocumentationTitle(tag: String, language: String, documentedFile: File): String {
        //@noobDoc("NoobDoc title follow pattern %22[LanguageInlineCommentSymbol]@[yourDoctag]DocTitle(%22Your doc title%22)%22", "TD01", "01-Title and description")
        val regexpTitle = """${languageComments(language)}@${tag}DocTitle\("(?<title>.+)"\)"""

        val fileContent = String(documentedFile.readBytes())

        val titlePattern = Pattern.compile(regexpTitle)

        val titleMatcher = titlePattern.matcher(fileContent)

        if (titleMatcher.find())
            return titleMatcher.group("title")

        //@noobDoc("If no title is found in the project, default title will be "NoobDoc"", "TD02", "01-Title and description")
        return "NoobDoc"
    }

    fun getDocumentationDescription(tag: String, language: String, documentedFile: File): String? {
        //@noobDoc("NoobDoc description follow pattern %22[LanguageInlineCommentSymbol]@[yourDoctag]DocDescription(%22Your doc description%22)%22", "TD03", "01-Title and description")
        val regexpTitle = """${languageComments(language)}@${tag}DocDescription\("(?<description>.+)"\)"""

        val fileContent = String(documentedFile.readBytes())

        val descriptionPattern = Pattern.compile(regexpTitle)

        val descriptionMatcher = descriptionPattern.matcher(fileContent)

        if (descriptionMatcher.find())
            return descriptionMatcher.group("description")

        //@noobDoc("If no description is found in the project, none will be displayed", "TD04", "01-Title and description")
        return null
    }


    fun buildSectionWithRulesFromFile(
            tag: String,
            language: String,
            documentedFile: File
    ): Section? {
        val section = buildSection(
                tag,
                language,
                documentedFile
        )

        val ruleGroupList = buildRuleGroupList(tag, language, documentedFile)
        section.ruleGroupList.addAll(ruleGroupList)

        return if (section.ruleGroupList.size == 0) null else section
    }


    private fun buildSection(tag: String, language: String, documentedFile: File): Section {
        //@noobDoc("NoobDoc section follow pattern %22[LanguageInlineCommentSymbol]@[yourDoctag]DocSection(%22[Section Code]%22, %22[Section Name]%22)%22, example are available at rule[@TE01]", "SF00", "02-Section Finder")
        val regexpSection = """${languageComments(language)}@${tag}DocSection\((?<sectionParam>.+)\)"""

        val fileContent = String(documentedFile.readBytes())

        val sectionPattern = Pattern.compile(regexpSection)

        val sectionMatcher = sectionPattern.matcher(fileContent)

        //@noobDoc("If no section tag can be found, noob doc will use current file name (without its extension) as section name and N00B-[incremental number] as code", "SF01", "02-Section Finder")
        var sectionName = documentedFile.nameWithoutExtension
        var sectionCode = "N00B"

        if (sectionMatcher.find()) {
            val sectionParam = sectionMatcher.group("sectionParam")

            val regexSectionParameter = Pattern.compile("\"(?<SectionCode>.+?)\", \"(?<SectionName>.+?)\"")

            val sectionParamMatcher = regexSectionParameter.matcher(sectionParam)

            if (sectionParamMatcher.find()) {
                //@noobDoc("Section will be displayed in alphabetical order following [Section Code] param", "SF02", "02-Section Finder")
                sectionCode = sectionParamMatcher.group("SectionCode")
                sectionName = sectionParamMatcher.group("SectionName")
            }
        }

        return Section(sectionCode, sectionName, mutableListOf())
    }

    private fun buildRuleGroupList(tag: String, language: String, documentedFile: File): MutableList<RuleGroup> {
        //@noobDoc("NoobDoc rules follow pattern [LanguageInlineCommentSymbol]@[yourDoctag]Doc(%22[Rule Description]%22%2C %22[Rule Code]%22%2C %22[Rule Group]%22), example are available at rule [@TE01]", "RF00", "03-Rule Finder")
        //@noobDoc("NoobDoc works only with inline comment, but if you have the feeling that your description is too long you can write the rest of it in another noobDocTag", "RF03", "03-Rule Finder")
        //@noobDoc("just give them the same rule code and group and noob doc will display them as one rule in their order of apparition adding the spacing by itself", "RF03", "03-Rule Finder")
        val regexpRule = """${languageComments(language)}@${tag}Doc\((?<ruleParam>.+)\)"""

        val fileContent = String(documentedFile.readBytes())

        val sectionPattern = Pattern.compile(regexpRule)

        val ruleMatcher = sectionPattern.matcher(fileContent)

        var ruleGroup: MutableList<RuleGroup> = mutableListOf()

        matcherLoop@ while (ruleMatcher.find()) {
            val ruleParam = ruleMatcher.group("ruleParam")
            val rule = buildSimpleRule(ruleParam) ?: continue@matcherLoop

            if (ruleGroup.size == 0 || ruleGroup.stream().noneMatch({ it.name == rule.ruleGroup }))
                ruleGroup.add(RuleGroup(rule.ruleGroup, mutableListOf()))


            val ruleList = ruleGroup.stream()
                    .filter({ it.name == rule.ruleGroup })
                    .findFirst().get().ruleList

            val existingRule = ruleList.stream().filter({it.code == rule.code}).findFirst().orElse(null)

            if (existingRule == null) { // Si la rule n'existe pas on la rajoute
                ruleList.add(rule)
                continue@matcherLoop
            }

            // Sinon on lui rajoute la description de la rule en cours
            existingRule.description = existingRule.description + " " + rule.description
        }

        ruleGroup = ruleGroup.sortedBy({ it.name }).toMutableList()
        ruleGroup.forEach({ it.ruleList = it.ruleList.sortedBy { it.code }.toMutableList() })

        return ruleGroup
    }

    private fun buildSimpleRule(ruleParam: String): Rule? {
        //@noobDoc("Noob doc param are found using regexp "(?<param>.+?)"((,\W)|$), sometime double quote and coma combination", "RF01", "03-Rule Finder")
        //@noobDoc("can fail, use instead their UTF-8 Ascii code %2522 and %252C", "RF01", "03-Rule Finder")
        //@noobDoc("but it's a rare problem that should happen only when you want to document NoobDoc using NoobDoc", "RF01", "03-Rule Finder")
        val ruleParamRegexp = Pattern.compile("\"(?<param>.+?)\"((,)|$)")

        val ruleParamMatcher = ruleParamRegexp.matcher(ruleParam)

        val param: MutableList<String> = mutableListOf()

        while (ruleParamMatcher.find()) {
            param.add(ruleParamMatcher
                    .group("param")
                    .replace("%22", "\"")
                    .replace("%2C", ",")
                    .replace("%25", "%")
            )
        }

        //@noobDoc("NoobDoc rules should contains at least 3 params according to [@RO1] (optional additional parameters could come with future release), if not rule is invalid and will not be displayed", "RF02", "03-Rule Finder")
        if (param.size < 3) {
            return null
        }

        return Rule(param[0], param[1], param[2])
    }


    private fun languageComments(language: String): String {

        return when (language) {
            "java", "php", "c-sharp", "kotlin" -> "//" //@noobDoc("java/php/C# with %22noob%22 tag will be //@noobDoc(%22rule description%22, %22rule code%22, %22rule set name%22) ", "TE01", "04-NoobDoc Tag exemple")
            "python" -> "#" //@noobDoc("python with "monty" tag will be #@montyDoc(%22rule description%22, %22rule code%22, %22Crule set name%22) ", "TE02", "04-NoobDoc Tag exemple")
            else -> throw UnsupportedOperationException("$language is not supported yet by NoobDoc.")
        //@noobDoc("If you want an exemple of a documentation generated with NoobDoc, you're actually reading one", "TE03", "04-NoobDoc Tag exemple")
        }
    }
}