package com.astmirzhan.cityflow

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.astmirzhan.cityflow.data.RouteHistoryRepository
import com.astmirzhan.cityflow.ui.RouteHistoryAdapter

class HistoryActivity : AppCompatActivity() {

    private lateinit var repository: RouteHistoryRepository
    private lateinit var list: RecyclerView
    private lateinit var emptyLabel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        title = getString(R.string.history_title)

        repository = RouteHistoryRepository(this)
        list = findViewById(R.id.historyList)
        emptyLabel = findViewById(R.id.historyEmpty)
        list.layoutManager = LinearLayoutManager(this)

        findViewById<Button>(R.id.clearHistoryButton).setOnClickListener {
            repository.clear()
            render()
        }
        render()
    }

    private fun render() {
        val items = repository.loadHistory()
        list.adapter = RouteHistoryAdapter(items)
        emptyLabel.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
        list.visibility = if (items.isEmpty()) View.GONE else View.VISIBLE
    }
}
