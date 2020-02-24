package com.sample.android.smallcount

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.res.Resources
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)

    val viewModel = ViewModelProviders.of(this).get(VictoryViewModel::class.java)
    viewModel.viewState.observe(this, Observer { it ->
      it?.let { render(it) }
    })
    viewModel.repository = Repository(this)
    viewModel.initialize()

    fab.setOnClickListener {
      viewModel.incrementVictoryCount()
    }
    textVictoryTitle.setOnClickListener { showVictoryTitleDialog(viewModel) }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.action_reset -> {
        // TODO reset existing victory title and count
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  private fun render(uiModel: VictoryUiModel) {
    when (uiModel) {
      is VictoryUiModel.TitleUpdated -> {
        textVictoryTitle.text = uiModel.title
      }
      is VictoryUiModel.CountUpdated -> {
        textVictoryCount.text = uiModel.count.toString()
      }
    }
  }


  private fun showVictoryTitleDialog(viewModel: VictoryViewModel) {
    AlertDialog.Builder(this).apply {
      setTitle(getString(R.string.dialog_title))

      val input = EditText(this@MainActivity)
      input.setText(textVictoryTitle.text)
      val density = Resources.getSystem().displayMetrics.density
      val padding = Math.round(16 * density)

      val layout = FrameLayout(context)
      layout.setPadding(padding, 0, padding, 0)
      layout.addView(input)

      setView(layout)

      setPositiveButton(getString(R.string.dialog_ok)) { _, _ ->
        viewModel.setVictoryTitle(input.text.toString())
      }
      setNegativeButton(getString(R.string.dialog_cancel), null)
      create().show()
    }
  }
}
