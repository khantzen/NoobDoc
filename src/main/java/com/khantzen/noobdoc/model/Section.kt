package com.khantzen.noobdoc.model

data class Section(
        val code: String,
        val title: String,
        var ruleGroupList: MutableList<RuleGroup>
)