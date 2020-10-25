package com.latynin.superrpg.events.BardsGuild

import com.latynin.superrpg.*

val songEvent = {
    val answers = mutableListOf<Answer>()
    answers.add(Answer("Подойти и узнать, что случилось"){
        GameManager.events.push(songEvent1())
    });
    answers.add(Answer("Пройти мимо, меня не волнуют его проблемы"){
        GameManager.plusStep()
    });

    Event(
        message = "Оказавшись внутри здания гильдии вы замечаете барда, поникшево над пустым пергаментом",
        answers = answers
    )
}

val songEvent1 = {
    val answers = mutableListOf<Answer>()
    answers.add(Answer("Помочь бедолаге") {
        if (GameManager.person.mind > 3) {
            GameManager.events.push(Event(
                message = "Спустя какое-то время вам удалось закончить с набросками новой баллады, после чего бард остался доволен вашим с ним знакомством и одарил вас небольшой наградой",
                answers = listOf(Answer("Поблагодарить его и попращаться") {
                    GameManager.person.coins += 35;
                    GameManager.plusStep()
                })
            ))
        } else {
            GameManager.events.push(Event(
                message = "Ему не понравились все ваши предложения касательно новых строчек баллады, так что он попросил вас уйти.",
                answers = listOf(Answer("Хорошо") {
                    GameManager.plusStep()
                })
            ))
        }
    });
    answers.add(Answer("Отказаться и уйти") {
        GameManager.plusStep()
    });

    Event(
        message = "По воле случая, вы встретили, потерявшего музу барда. Он просит вас помочь ему с новой балладой за небольшое вознаграждение",
        answers = answers
    )
}