package my.progekt.wordsinword2

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import my.progekt.wordsinword2.databinding.ActivityMainBinding
import java.io.IOException

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  private lateinit var animation: Animation
  private lateinit var presenter: Presenter
  private var lastWord = ""
  private lateinit var adapter: ResultListRVAdapter
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    presenter = Presenter()
    try {
      presenter.printedWordsLinkedList.addAll(savedInstanceState?.get("LIST") as Collection<String>)
    } catch (e: Exception) {
    }
    adapter = ResultListRVAdapter()
    adapter.setData(presenter.printedWordsLinkedList)
    animation = AnimationUtils.loadAnimation(this, R.anim.scaner)
    try {
      animation.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation) {}
        override fun onAnimationEnd(animation: Animation) {}
        override fun onAnimationRepeat(animation: Animation) {}
      })
    } catch (e: IOException) {
    }

    createList()

    binding.button.setOnClickListener {
      presenter.printedWordsLinkedList.clear()
      binding.bar.startAnimation(animation)
      when (lastWord) {
        binding.inputText.text.toString() -> {
        binding.bar.clearAnimation()
      }
      else -> {
        lastWord = binding.inputText.text.toString()
        MyThread().start()
      }
    }
  }
}

private fun createList() {
  val linearLayoutManager = LinearLayoutManager(applicationContext)
  linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
  binding.resultListRV.layoutManager = linearLayoutManager
  binding.resultListRV.adapter = adapter
}

inner class MyThread : Thread() {
  @SuppressLint("SetTextI18n")
  override fun run() {
    super.run()
    sleep(1000)
    binding.resultListRV.post {
      adapter.setData(
        presenter.allWords(
          binding.inputText.text.toString().trim(),
          assets.open("russian_words").bufferedReader().readLines()
        )
      )
    }
    presenter.allWords(
      binding.inputText.text.toString().trim(),
      assets.open("russian_words").bufferedReader().readLines()
    )
    binding.bar.post {
      binding.bar.clearAnimation()
    }
  }
}

override fun onSaveInstanceState(outState: Bundle) {
  super.onSaveInstanceState(outState)
  val savedList = arrayListOf<String>()
  savedList.addAll(presenter.printedWordsLinkedList)
  outState.putStringArrayList("LIST", savedList)
}
}
