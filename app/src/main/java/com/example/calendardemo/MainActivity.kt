package com.example.calendardemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.threetenabp.AndroidThreeTen
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import org.threeten.bp.format.DateTimeFormatter

class MainActivity : AppCompatActivity(), CalendarAdapter.OnItemListener {

    private lateinit var buttonPrevMonth: Button
    private lateinit var buttonNextMonth: Button
    private lateinit var monthYearText: TextView
    private lateinit var calendarRecyclerView: RecyclerView
    private lateinit var selectedDate: LocalDate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidThreeTen.init(this)

        initWidgets()
        selectedDate = LocalDate.now() // Requires API 26 onward
        setMonthView()

    }

    private fun initWidgets(){

        calendarRecyclerView = findViewById(R.id.calendarRecyclerView)
        monthYearText = findViewById(R.id.monthYearTV)
        buttonPrevMonth = findViewById(R.id.button_prev_month)
        buttonNextMonth = findViewById(R.id.button_next_month)

        buttonPrevMonth.setOnClickListener {
            selectedDate = selectedDate.minusMonths(1)
            setMonthView()
        }

        buttonNextMonth.setOnClickListener {
            selectedDate = selectedDate.plusMonths(1)
            setMonthView()

        }
    }

    private fun setMonthView() {
        monthYearText.text = monthYearFromDate(selectedDate)
        val daysInMonth : ArrayList<String> = daysInMonthArray(selectedDate) // this is the array of days

        val calendarAdapter = CalendarAdapter(daysInMonth, this, this)
        val layoutManager : RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)
        calendarRecyclerView.layoutManager = layoutManager
        calendarRecyclerView.adapter = calendarAdapter
    }

    private fun daysInMonthArray(date: LocalDate): ArrayList<String> {

        val daysInMonthArray : ArrayList<String> = ArrayList()
        val yearMonth : YearMonth = YearMonth.from(date)
        val daysInMonth : Int = yearMonth.lengthOfMonth()

        val firstDayOfMonth : LocalDate = selectedDate.withDayOfMonth(1)
        val dayOfWeek : Int = firstDayOfMonth.dayOfWeek.value

        for (i in 1..42){
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek){
                daysInMonthArray.add("")
            } else {
                daysInMonthArray.add((i - dayOfWeek).toString())
            }
        }

        return daysInMonthArray

    }

    private fun monthYearFromDate(date: LocalDate) : String {
        val formatter : DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date.format(formatter)
    }

    override fun onItemClick(position: Int, dayText: String?) {
        if (dayText!! != ""){
            val message = "Selected date $dayText ${monthYearFromDate(selectedDate)}"
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }


}