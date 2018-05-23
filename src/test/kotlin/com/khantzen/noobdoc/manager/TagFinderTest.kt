package com.khantzen.noobdoc.manager

import com.khantzen.noobdoc.model.Section
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.io.File

class TagFinderTest {

    lateinit var tagFinder: NoobDocTagFinder

    @Before
    fun setUp() {
        tagFinder = NoobDocTagFinder()
    }

    @Test
    fun getSectionFromJavaCodeWithOneRuleGroupTest() {
        val tag = "noob"
        val documentedFile = File("src/test/resources/tagFinderFile/noobDocTag.java")

        val section: Section = tagFinder.buildSectionWithRulesFromFile(tag, "java", documentedFile)!!

        assertThat(section).isNotNull
        assertThat(section.code).isEqualTo("S01")
        assertThat(section.title).isEqualTo("FuzzBizz")
        assertThat(section.ruleGroupList).isNotEmpty.isNotNull.hasSize(1)

        val ruleGroupList = section.ruleGroupList

        assertThat(ruleGroupList[0].name).isEqualTo("FuzzBizzRules")
        assertThat(ruleGroupList[0].ruleList).isNotNull.isNotEmpty.hasSize(4)

        assertThat(ruleGroupList[0].ruleList
                .stream().anyMatch({ it.description.contains("eur d'entrée est divisible par 5, on affiche Biz") }))
                .isTrue
    }

    @Test
    fun getSectionFromPythonCodeWithManyRuleGroupTest() {
        val tag = "monty"
        val documentedFile = File("src/test/resources/tagFinderFile/montyDocTag.py")

        val section: Section = tagFinder.buildSectionWithRulesFromFile(tag, "python", documentedFile)!!

        assertThat(section).isNotNull
        assertThat(section.ruleGroupList).hasSize(2)
    }

    @Test
    fun noDocTagTest() {
        val tag = "kot"
        val documentedFile = File("src/test/resources/tagFinderFile/NoDocTag.kt")

        val section: Section? = tagFinder.buildSectionWithRulesFromFile(tag, "python", documentedFile)

        assertThat(section).isNull()
    }

    @Test
    fun selfDocumentationKotlinTest() {
        val tag = "noob"
        val documentedFile = File("src/main/kotlin/com/khantzen/noobdoc/manager/NoobDocTagFinder.kt")

        val section: Section = tagFinder.buildSectionWithRulesFromFile(tag, "kotlin", documentedFile)!!
        println("Documentation")
        println("""${section.code}-${section.title}""")

        for (ruleGroup in section.ruleGroupList) {
            println("--${ruleGroup.name}")
            for (rule in ruleGroup.ruleList) println("----${rule.code}-${rule.description}")
        }
    }
}