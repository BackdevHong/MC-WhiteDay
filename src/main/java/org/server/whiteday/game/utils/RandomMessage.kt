package org.server.whiteday.game.utils

object RandomMessage {
    private var messageList = arrayListOf<String>("어딜 그렇게 가? 나랑 놀자 ㅎ", "꼭꼭 숨어라~ 머리카락 보일라~", "내 곁으로 와줘..", "최후의 최후까지… 찾아가서 죽여줄게", "겁내지 마... 아무것도 아니야… 그저 한번 죽을뿐이지..")

    fun randomMessage(): String {
        val random = messageList.random()
        return random
    }
}