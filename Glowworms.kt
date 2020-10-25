package com.latynin.superrpg.events.ElvenForests

import com.latynin.superrpg.*

val glowwormsEvent = {
    val answers = mutableListOf<Answer>()

    answers.add(Answer("Осмотреть каменюку") {
        GameManager.events.push(glowwormsEventExemineStone())
    })

    answers.add(Answer("Пройти мимо") {
        GameManager.plusStep()
    })

    Event(
        message = "Оказавшись в лесу, вы натолкнулись на плотное скопление ярких светлячков, нависшее над заросшим крапивой валуном",
        answers = answers
    )
}

val glowwormsEventExemineStone = {
    val answers = mutableListOf<Answer>()

    answers.add(Answer("Попытаться сдвинуть преграду") {
        if (GameManager.person.power > 10) {
            GameManager.events.push(glowwormsEventDisplacedStone())
        } else {
            GameManager.events.push(glowwormsEventNotDisplacedStone())
        }
    })

    answers.add(Answer("Плюнуть на все и пойти дальше") {
        GameManager.plusStep()
    })

    Event(
        message = "Осмотревшись, вы заметили пустое пространство в овраге, закрытое треклятым валуном",
        answers = answers
    )
}

val glowwormsEventDisplacedStone = {
    val answers = mutableListOf<Answer>()

    answers.add(Answer("Войти и осмотреться") {
        GameManager.events.push(glowwormsEventInside())
    })

    answers.add(Answer("Уйти и не рисковать лишний раз ради любопытства") {
        GameManager.plusStep()
    })

    Event(
        message = "Приложив все свои усилия, вы чувствуете, как валун начинает поддаваться вам и менять свое положение в противоположное вам. " +
                "В конечном итоге вам удалось окончательно освободить проход, что был недоступен вам раннее",
        answers = answers
    )
}

val glowwormsEventNotDisplacedStone = {
    Event(
        message = "К сожалению, сколько бы вы не старались, валун не поддается вашим усилиям",
        answers = listOf(Answer("Оставить все и пойти дальше") {
            GameManager.plusStep()
        })
    )
}

val glowwormsEventInside = {
    val answers = mutableListOf<Answer>()

    answers.add(Answer("Попытаться найти в нем хоть что-то полезное") {
        GameManager.events.push(glowwormsEventSecretaire())
    })

    Event(
        message = "Внутри небольшого прохода оказалось довольно просторное помещение. " +
                "\"По всей видимости кто-то раньше жил здесь и судя по заросшей древесными корнями мебели это было довольно давно.\"" +
                "Не взирая на всю шаткость данного помещения и всего его содержимого, в углу, обставленном всяческой алхимической чепухой, притаился довольно хорошо сохранившийся" +
                "секретер.",
        answers = answers
    )
}

val glowwormsEventSecretaire = {
    val answers = mutableListOf<Answer>()

    answers.add(Answer("Взять") {
        GameManager.events.push(glowwormsEventFurniture())
    })

    answers.add(Answer("Оставить на месте") {
        GameManager.events.push(glowwormsEventFurniture())
    })

    Event(
        message = "Обшариф все что только можно, в одном из ящичков секретера вам удалось найти нож для вскрытия конвертов",
        answers = answers
    )
}


val glowwormsEventFurniture = {
    val answers = mutableListOf<Answer>()

    answers.add(Answer("Открыть столешницу") {
        //TODO
    })

    answers.add(Answer("Заглянуть в мешочек с травами") {
        //TODO
    })

    answers.add(Answer("Уйти") {
        GameManager.plusStep()
    })

    Event(
        message = "Помимо секретера, здесь есть и другие элементы интерьера, возможно хранящие, что-то полезное",
        answers = answers
    )
}