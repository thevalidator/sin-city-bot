/*
 * The Krechet Software
 */
package ru.thekrechetofficial.sincitybot.bot;

/**
 *
 * @author theValidator <the.validator@yandex.ru>
 */
public enum MESSAGE {

    GREETENG("Здравствуй, Странник! \n\nЯ помогу тебе "
            + "найти то, за чем ты сюда пришел... Всё необходимое ты узнаешь в разделе \"Помощь\"."
            + " \n\nИ помни... в город Грехов приходят с широко открытыми глазами... и не выходят "
            + "из него никогда."),
    NO_ANSWER("Странник, я бы пообщался с тобой, но мне "
            + "нельзя... Используй меню."),
    
    HELP("КРАТКИЙ ИНСТРУКТАЖ" +
            //"\n\n\t<i>Если лень читать, держи <a href=\"https://youtu.be/p8hcFTqg4w4\"><b>видеоинструкцию на YouTube</b></a></i>" +
            //"\n\n<b>-</b> Чтобы открыть меню используй команду \"/menu\"." +
            "\n\n- Для просмотра свежих объявлений выбери \"Поиск\" -> \"Разведка\". Затем нужно" +
            " выбрать пол и кол-во последних опубликованных объявлений. " +
            "\n\n- Для поиска всех объявлений, опубликованных от определенного контакта, используй \"Розыск\" в разделе меню " + 
            "\"Поиск\". " +
            "Контактные данные вводятся одним словом и могут содержать от 4 до 40 символов." +
            //"\n\t\t\t\t<b>1.1)</b> номер телефона вводится в 10 значном формате - пример: 9035557700" +
            //"\n\t\t\t\t<b>1.2)</b> адрес почты можно вводить до знака \"@\"." +
            //"\n\t\t\t\t<b>1.3)</b> имена telegram аккаунтов можно вводить без знака \"@\".</i>" +
            "\n\n- Выбери \"Кабинет\" для просмотра сведений по своему аккаунту."  +
            "\n\nБот пока еще работает в тестовом режиме! Могут возникать ошибки.\n\nЕсли бот не отвечает на " +
            "сообщения, не нужно ему писать миллион сообщений, он просто выключен или что то пошло не так." +
            "\n\nЗамечания и предложения направляйте на почту: sincitynightsbot@gmail.com" +
            //"\n\n-> <a href=\"https://vk.com/club204138420\"><b>Группа VK</b></a>" +
            //"\n\n-> <a href=\"https://youtu.be/p8hcFTqg4w4\"><b>Видеоинструкция на YouTube</b></a>" +
            //"\n\n-> <a href=\"https://qiwi.com/SINCITYNIGHTS\"><b>Донат по желанию разработчикам на шоколадки</b></a>
            "");

    String msg;

    private MESSAGE(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

}
