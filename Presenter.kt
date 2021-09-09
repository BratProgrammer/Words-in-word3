package my.progekt.wordsinword2

class Presenter {

    var printedWordsLinkedList = arrayListOf<String>()

    fun allWords(inputWord: String, allRusWords: List<String>): ArrayList<String> {
        var word = true
        var numberOfCharacters = 0
        for (i in allRusWords) {
            for (secondI in i) {
                if (inputWord.indexOf(secondI) == -1) {
                    word = false
                }
            }
            if (word) {
                printedWordsLinkedList.add(i)
            }
            word = true
            numberOfCharacters += i.length
        }
        return printedWordsLinkedList
    }
}