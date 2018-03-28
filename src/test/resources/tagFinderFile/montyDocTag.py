# coding=utf-8
#@montyDocSection("M01", "The bridge crossing")

#@montyDoc("Bridge keeper will ask 3 questions to candidate", "Q00", "AskQuestions")

#raw_input is not referencing here but we don't care because this script serve only in unit test as this comment that should not be
#displayed in the final rule list

name  = raw_input("What is your name?") #@montyDoc("First the bridge keeper will ask for candidate name", "Q01", "AskQuestions")
quest = raw_input("What is your quest?") #@montyDoc("Then the bridge keeper will ask for candidate quest", "Q02", "AskQuestions")
color = raw_input("What is your favorite color?") #@montyDoc("Then the bridge keeper will ask for candidate favorite color", "Q03", "AskQuestions")


#@montyDoc("According to the candidate answer, keeper will say "Ah, so your name is [candidate_name_answer], your quest is [candidate_quest_answer], and your favorite color is [candidate_favorite_color_answer]].", "A00", "Display Answer")
print  "Ah, so your name is %s, your quest is %s, and your favorite color is %s." % (name, quest, color)