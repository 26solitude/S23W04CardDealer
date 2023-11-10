package kr.ac.kumoh.s20161163.s23w04carddealer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kr.ac.kumoh.s20161163.s23w04carddealer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var main: ActivityMainBinding
    private lateinit var model: CardDealerViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main = ActivityMainBinding.inflate(layoutInflater)
        setContentView(main.root)

        model = ViewModelProvider(this)[CardDealerViewModel::class.java]
        model.cards.observe(this, Observer {
            val res = IntArray(5)
            for (i in it.indices) {
                res[i] = resources.getIdentifier(
                    getCardName(it[i]),
                    "drawable",
                    packageName
                )
            }
            main.imgCard1.setImageResource(res[0])
            main.imgCard2?.setImageResource(res[1])
            main.imgCard3?.setImageResource(res[2])
            main.imgCard4?.setImageResource(res[3])
            main.imgCard5?.setImageResource(res[4])

            // Check poker hand
            val pokerHand = checkPokerHand(it)

            main.textViewResult!!.text = "Poker Hand: $pokerHand"

        })


        main.btnShuffle.setOnClickListener{
            model.shuffle()
        }
    }

    fun checkPokerHand(cards: IntArray): String {
        val suits = mutableMapOf<Int, Int>()
        val numbers = mutableMapOf<Int, Int>()
        var hasJoker = false

        // Count the occurrences of each suit and number
        for (card in cards) {
            val suit = card / 13
            val number = card % 13

            if (number == 0) {
                hasJoker = true
            } else {
                suits[suit] = suits.getOrDefault(suit, 0) + 1
                numbers[number] = numbers.getOrDefault(number, 0) + 1
            }
        }

        // Check for a Royal Flush
        if (hasJoker) {
            // Handle Joker in a Royal Flush
        }

        // Check for a Straight Flush
        if (hasStraightFlush(suits, numbers)) {
            return "Straight Flush"
        }

        // Check for a Four of a Kind
        if (hasFourOfAKind(numbers.values)) {
            return "Four of a Kind"
        }

        // Check for a Full House
        if (hasFullHouse(numbers.values)) {
            return "Full House"
        }

        // Check for a Flush
        if (hasFlush(suits)) {
            return "Flush"
        }

        // Check for a Straight
        if (hasStraight(numbers.keys)) {
            return "Straight"
        }

        // Check for Three of a Kind
        if (hasThreeOfAKind(numbers.values)) {
            return "Three of a Kind"
        }

        // Check for Two Pairs
        if (hasTwoPairs(numbers.values)) {
            return "Two Pairs"
        }

        // Check for One Pair
        if (hasOnePair(numbers.values)) {
            return "One Pair"
        }

        // If none of the above, it's a High Card
        return "High Card"
    }

    private fun hasStraightFlush(suits: Map<Int, Int>, numbers: Map<Int, Int>): Boolean {
        // Implement logic for checking Straight Flush
        // Consider both natural and with Joker cases
        return false
    }

    private fun hasFourOfAKind(numberCounts: Collection<Int>): Boolean {
        return numberCounts.contains(4)
    }

    private fun hasFullHouse(numberCounts: Collection<Int>): Boolean {
        return numberCounts.contains(3) && numberCounts.contains(2)
    }

    private fun hasFlush(suits: Map<Int, Int>): Boolean {
        // Implement logic for checking Flush
        // Consider both natural and with Joker cases
        return false
    }

    private fun hasStraight(numbers: Set<Int>): Boolean {
        // Implement logic for checking Straight
        // Consider both natural and with Joker cases
        return false
    }

    private fun hasThreeOfAKind(numberCounts: Collection<Int>): Boolean {
        return numberCounts.contains(3)
    }

    private fun hasTwoPairs(numberCounts: Collection<Int>): Boolean {
        return numberCounts.count { it == 2 } == 2
    }

    private fun hasOnePair(numberCounts: Collection<Int>): Boolean {
        return numberCounts.contains(2)
    }

//    fun checkPokerHand(cards: IntArray): String {
//        val numberCountMap = mutableMapOf<Int, Int>()
//
//        // Count the occurrences of each card number
//        for (card in cards) {
//            val cardNumber = card % 13
//            numberCountMap[cardNumber] = numberCountMap.getOrDefault(cardNumber, 0) + 1
//        }
//
//        // Check for a Four of a Kind
//        for (entry in numberCountMap.entries) {
//            if (entry.value == 4) {
//                return "Four of a Kind"
//            }
//        }
//
//        // Check for a Full House
//        var hasThreeOfAKind = false
//        var hasPair = false
//        for (entry in numberCountMap.entries) {
//            when (entry.value) {
//                3 -> hasThreeOfAKind = true
//                2 -> hasPair = true
//            }
//        }
//        if (hasThreeOfAKind && hasPair) {
//            return "Full House"
//        }
//
//        // Check for a Three of a Kind
//        for (entry in numberCountMap.entries) {
//            if (entry.value == 3) {
//                return "Three of a Kind"
//            }
//        }
//
//        // Check for Two Pairs
//        var pairCount = 0
//        for (entry in numberCountMap.entries) {
//            if (entry.value == 2) {
//                pairCount++
//            }
//        }
//        if (pairCount == 2) {
//            return "Two Pairs"
//        }
//
//        // Check for a Pair
//        for (entry in numberCountMap.entries) {
//            if (entry.value == 2) {
//                return "One Pair"
//            }
//        }
//
//        // Check for a Straight
//        val sortedNumbers = numberCountMap.keys.sorted()
//        var isStraight = true
//        for (i in 1 until sortedNumbers.size) {
//            if (sortedNumbers[i] != sortedNumbers[i - 1] + 1) {
//                isStraight = false
//                break
//            }
//        }
//        if (isStraight) {
//            return "Straight"
//        }
//
//        // If none of the above, it's a High Card
//        return "High Card"
//    }



    private fun getCardName(c: Int) : String{
        val shape = when (c / 13){
            0 -> "spades"
            1 -> "diamonds"
            2 -> "hearts"
            3 -> "clubs"
            else -> "error"
        }

        val number = when (c % 13) {
            0 -> "ace"
            in 1..9 -> (c%13 + 1).toString()
            10 -> "jack"
            11 -> "queen"
            12 -> "king"
            else -> "error"
        }

        return if (number in arrayOf("jack", "queen", "king"))
            "c_${number}_of_${shape}2"
        else
            "c_${number}_of_${shape}"
    }
}