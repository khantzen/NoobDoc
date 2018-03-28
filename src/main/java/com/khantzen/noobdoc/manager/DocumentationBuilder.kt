package com.khantzen.noobdoc.manager

import com.khantzen.noobdoc.configuration.model.User
import com.khantzen.noobdoc.constants.Language
import com.khantzen.noobdoc.model.Documentation
import com.khantzen.noobdoc.model.Section

class DocumentationBuilder(val config: User) {
    private val projectFileFetcher = ProjectFileFetcher()
    private val noobDocTagFinder = NoobDocTagFinder()

    fun build(): Documentation? {
        val currentLanguage: Language = Language.getById(config.language)

        val fileTree = projectFileFetcher.listFileTree(config.sourcePath, currentLanguage.extension)

        var sectionList: MutableList<Section> = arrayListOf()

        var title = "N00B"
        var description: String? = null

        for (file in fileTree) {
            if (title == "N00B") {
                val tmpTitle = noobDocTagFinder.getDocumentationTitle(config.tagPrefix, config.language, file)
                if (tmpTitle != null) {
                    title = tmpTitle
                }
            }
            if (description == null) {
                description = noobDocTagFinder.getDocumentationDescription(config.tagPrefix, config.language, file)
            }

            val section = noobDocTagFinder.buildSectionWithRulesFromFile(config.tagPrefix, config.language, file)
            if (section != null) sectionList.add(section)
        }

        sectionList = sectionList.sortedBy({ it.code }).toMutableList()

        return Documentation(title, description, sectionList)
    }

}