package com.latynin.superrpg.events.WizardsShop

import com.latynin.superrpg.*

val shopExplosionEvent = {
    val answers = mutableListOf<Answer>()

    answers.add(Answer("Развернуться и пойти куда подальше"){
        GameManager.plusStep()
    })

    answers.add(Answer("Подойти и утешить свое любопытство!"){
        GameManager.events.push(shopExplosionEventCome())
    })

    if (GameManager.person.specialization == Class.Thief) {
        answers.add(Answer("Попытаться обокрасть лавку, пока местные разгребают последствия взрыва"){
            if (GameManager.person.mind > 2) {
                GameManager.events.push(shopExplosionEventSuccessSteal())
            } else {
                GameManager.events.push(shopExplosionEventFailSteal())
            }
        });
    }

    Event(
        message = "Увидев силует лавки в дали, вы стали свидетелем странного происшествия. " +
                "Раздался громкий взрыв, а рядом с лавкой вы заметили очертания небольшого огненного гриба",
        answers = answers
    )
}

val shopExplosionEventCome = {
    val answers = mutableListOf<Answer>()

    answers.add(Answer("Попытаться узнать о произошедшем") {
        if (GameManager.person.specialization != Class.Mag) {
            GameManager.events.push(shopExplosionEventSuccessAsk())
        } else {
            GameManager.events.push(shopExplosionEventFailAsk())
        }
    })

    answers.add(Answer("Лучше не буду в это ввязываться"){
        GameManager.plusStep()
    })

    Event(
        message = "Подбежав к лавке, вы увидели сидящего на земле, бормочущего что-то себе под нос, волшебника",
        answers = answers
    )
}

val shopExplosionEventSuccessAsk = {
    val answers = mutableListOf<Answer>()

    answers.add(Answer("Была не была, давай свое зелье!"){
        if (GameManager.person.power > 5) {
            GameManager.events.push(shopExplosionEventSuccessDrink())
        } else {
            GameManager.events.push(shopExplosionEventFailDrink())
        }
    })

    answers.add(Answer("Спасибо за предложение, но жить я еще хочу..."){
        GameManager.plusStep()
    })

    Event(
        message = "\"Я так понимаю ты мало, что смыслишь в магии, так что думаю тебе можно рассказать.\" - снисходительно ответил чумазый волшебник " +
                "- \"Все свои опыты я провожу на улице, дабы обезопасить свое имущество, от подобных случаев. Недавно у меня появилась идея, " +
                "касательно нового зелья. Я понятия не имею, как оно повлияет на организм выпившего, но думаю, оно стоит того. Не хочешь стать первым, кто испытает его на себе?\"",
        answers = answers
    )
}

val shopExplosionEventFailAsk = {
    val answers = mutableListOf<Answer>()

    answers.add(Answer("Напасть на волшебника за его дерзость!") {
        GameManager.events.push(shopExplosionEventAtack())
    })

    answers.add(Answer("Поскорее уйти, пока он не разозлился еще сильнее") {
        GameManager.plusStep()
    })

    Event(
        message = "\"Иди к черту, ты думаешь я просто так возьму и расскажу тебе о своих опытах! Проваливай, пока я на тебе не начал экспериментировать!\"",
        answers = answers
    )
}

val shopExplosionEventSuccessDrink = {
    val answers = mutableListOf<Answer>()
    
    answers.add(Answer("Поблагодарить волшебника и продолжить путешествие"){
        GameManager.person.tags.add(Tag.Pacified)
        GameManager.person.health++
        GameManager.plusStep()
    })

    Event(
        message = "Осушив флакон со странным зельем, вы чувствуете небывалый прилив сил. Помимо бодрости, вы чувствуете спокойствие и умиротворение",
        answers = answers
    )
}

val shopExplosionEventFailDrink = {
    val answers = mutableListOf<Answer>()
    
    answers.add(Answer("Отойти по нужде") {
        GameManager.events.push(shopExplosionEventDropping())
    })

    answers.add(Answer("Сделать вид, что все в порядке и уйти поскорее") {
        if (GameManager.person.power >= 2) {
            GameManager.plusStep()
        } else {
            GameManager.person.health--
            GameManager.plusStep()
        }
    })

    Event(
        message = "С последней каплей зелья вы ощутили естественное желание любого существа",
        answers = answers
    )
}

val shopExplosionEventDropping = {
    Event(
        message = "К счастью все прошло гладко, ваша честь и гордость не задеты, посему можно отправляться дальше",
        answers = listOf(Answer("Отправиться дальше") {
            GameManager.plusStep()
        })
    )
}

val shopExplosionEventSuccessSteal = {
    val answers = mutableListOf<Answer>()
    
    answers.add(Answer("Попытаться узнать о произошедшем") {
        GameManager.person.coins += 15;
        if (GameManager.person.specialization != Class.Mag) {
            GameManager.events.push(shopExplosionEventSuccessAsk())
        } else {
            GameManager.events.push(shopExplosionEventFailAsk())
        }
    })

    answers.add(Answer("Лучше не буду в это ввязываться"){
        GameManager.person.coins += 15;
        GameManager.plusStep()
    })

    Event(
        message = "Незаметно обшарив каждый уголок в лавке, вам удалось насобирать несколько монет, одиноко лежавших в книге с детскими сказками",
        answers = answers
    )
}

val shopExplosionEventFailSteal = {
    val answers = mutableListOf<Answer>()

    answers.add(Answer("Попытаться сбежать"){
        GameManager.events.push(shopExplosionEventRunaway())
    })

    answers.add(Answer("Атаковать волшебника"){
        GameManager.events.push(shopExplosionEventAtack())
    })

    Event(
        message = "Увы, но вы были неосторожны в своих действиях и задели ${wizardShopItems.random()}, из-за чего вас заметил местный лавочник",
        answers = answers
    )
}

val shopExplosionEventRunaway = {
    if (GameManager.person.tags.contains(Tag.QuickLegs)) {
        Event(
            message = "К счастью вы сбежали от разъяренного лавочника",
            answers = listOf(Answer("Дальше в путь") {
                GameManager.plusStep()
            })
        )
    } else {
        Event(
            message = "Вам не удалось сбежать, в связи с чем отхватили от лавочника",
            answers = listOf(Answer("Отправиться дальше") {
                GameManager.person.health--
                GameManager.plusStep()
            })
        )
    }
   
}

val shopExplosionEventAtack = {
    if (GameManager.person.power > 6) {
        Event(
            message = "Вам удалось одолеть волшебника и забрать награбленное!",
            answers = listOf(Answer("Отправиться дальше") {
                GameManager.plusStep()
            })
        )
    } else {
        Event(
            message = "К сожалению вы слишком слабы, поэтому отхватили от лавочника",
            answers = listOf(Answer("Отправиться дальше") {
                GameManager.person.health--
                GameManager.plusStep()
            })
        )
    }
    
}