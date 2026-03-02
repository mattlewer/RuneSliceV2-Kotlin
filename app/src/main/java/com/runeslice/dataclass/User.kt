package com.runeslice.dataclass

data class User(
    var name: String,
    var skills: MutableList<Skill>,
    var boss: MutableList<Boss>,
    var clues: MutableList<ClueScroll>
)
