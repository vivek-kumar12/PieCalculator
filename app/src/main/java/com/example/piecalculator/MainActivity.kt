package com.example.piecalculator

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.piecalculator.databinding.ActivityMainBinding
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import java.util.Locale
import kotlin.properties.Delegates
import kotlin.random.Random



class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var graph1: ArrayList<BarEntry>
    private lateinit var graph2: ArrayList<BarEntry>
    private lateinit var graph3: ArrayList<PieEntry>
    private lateinit var graph4: ArrayList<BarEntry>
    private lateinit var graph5: ArrayList<BarEntry>
    private lateinit var dataSet: BarDataSet
    private lateinit var colorArray: ArrayList<Int>

    private var customRed by Delegates.notNull<Int>()
    private var customBlue by Delegates.notNull<Int>()
    private var customYellow by Delegates.notNull<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.scatterBarChart.description.isEnabled = false
        binding.scatterBarChart.legend.isEnabled = false
        val xAxis = binding.scatterBarChart.xAxis
        val yAxis = binding.scatterBarChart.axisRight
        val yAxisLeft= binding.scatterBarChart.axisLeft
        xAxis.isEnabled = false

        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                listOf(xAxis, yAxis, yAxisLeft).forEach { it.textColor = Color.WHITE }
                updateTextColorandChart()
            }
        }

        binding.scatterBarChart.legend.textColor = ContextCompat.getColor(this, R.color.white)

        customRed = ContextCompat.getColor(this, R.color.red)
        customBlue = ContextCompat.getColor(this, R.color.blue)
        customYellow = ContextCompat.getColor(this, R.color.yellow)

        colorArray = arrayListOf(customBlue, customRed, customYellow)

        dataSetChart1to3() // set data for chart 1,2 and 3
        dataSetChart4and5() // set data for chart 4 and 5
        chartSelection()

    }

    private fun chartSelection() {
        binding.scatterBarChart.visibility = View.VISIBLE
        binding.chipPercent1.isChecked =true
        showChart1()
        binding.chipPercent1.setOnClickListener {
            binding.pieChart.visibility = View.GONE
            binding.scatterBarChart.visibility = View.VISIBLE
            showChart1()
        }

        binding.chipPercent2.setOnClickListener {
            binding.scatterBarChart.visibility = View.VISIBLE
            binding.pieChart.visibility = View.GONE
            showChart2()
        }
        binding.chipDegree.setOnClickListener {
            binding.scatterBarChart.visibility = View.GONE
            binding.pieChart.visibility = View.VISIBLE
            showChart3()
        }
        binding.chipValue1.setOnClickListener {
            binding.scatterBarChart.visibility = View.VISIBLE
            binding.pieChart.visibility = View.GONE
            dataSetChart4and5()
            showChart4()
        }

        binding.chipValue2.setOnClickListener {
            binding.scatterBarChart.visibility = View.VISIBLE
            binding.pieChart.visibility = View.GONE
            dataSetChart4and5()
            showChart5()
        }

        binding.etxtInputValue.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is called as the text changes
            }

            override fun afterTextChanged(s: Editable?) {
                // This method is called after the text changes
                dataSetChart4and5()
                binding.pieChart.visibility = View.GONE
                when (Random.nextInt(1, 3)) {
                    1 -> {
                        binding.chipValue1.isChecked = true
                        showChart4()
                    }
                    2 -> {
                        binding.chipValue2.isChecked =true
                        showChart5()
                    }
                }
                binding.scatterBarChart.visibility = View.VISIBLE
            }
        })
    }


    private fun showChart1() {
        dataSet = BarDataSet(graph1, "")
        dataSet.setDrawValues(true)
        Toast.makeText(this, "100%", Toast.LENGTH_SHORT).show()
        colorArray = arrayListOf(customBlue, customRed, customYellow)
        dataSet.colors = colorArray
        dataSet.setDrawValues(true)
        binding.scatterBarChart.setDrawValueAboveBar(false)
            val barData = BarData(dataSet)

            dataSet.valueFormatter = PercentageValueFormatter()

            binding.scatterBarChart.data = barData
            updateTextColorandChart()
        }

        private fun showChart2() {
            dataSet = BarDataSet(graph2, "")
            Toast.makeText(this, "100% +", Toast.LENGTH_SHORT).show()
            colorArray = arrayListOf(customBlue, customRed, customYellow)
            dataSet.colors = colorArray

            // Create a BarData object and add the datasets
            val barData = BarData(dataSet)

            dataSet.valueFormatter = PercentageValueFormatter()

            binding.scatterBarChart.data = barData
            updateTextColorandChart()
        }

        private fun showChart3() {

            val pieDataSet = PieDataSet(graph3, "")
            Toast.makeText(this, "Pie Chart", Toast.LENGTH_SHORT).show()

            // Customize dataset appearance
            pieDataSet.colors = mutableListOf(customBlue, customRed)
            pieDataSet.valueTextSize = 14f

            // Create a PieData object and set the dataset
            val pieData = PieData(pieDataSet)

            pieDataSet.valueFormatter = PercentageValueFormatter()
            binding.pieChart.data = pieData
            binding.pieChart.description.isEnabled = false
            binding.pieChart.legend.isEnabled = false
            binding.pieChart.centerText = "Pie Chart"
            binding.pieChart.setCenterTextSize(20f)
            binding.pieChart.animateY(1000)
            binding.pieChart.notifyDataSetChanged()
        }

        private fun showChart4() {

            dataSet = BarDataSet(graph4, "")
            Toast.makeText(this, "Values", Toast.LENGTH_SHORT).show()
            colorArray = arrayListOf(customBlue, customRed, customYellow)
            dataSet.colors = colorArray

            val barData = BarData(dataSet)

            binding.scatterBarChart.data = barData
            updateTextColorandChart()
        }

        private fun showChart5() {
            dataSet = BarDataSet(graph5, "")
            Toast.makeText(this, "100% Values", Toast.LENGTH_SHORT).show()

            colorArray = arrayListOf(customBlue, customRed, customYellow)
            dataSet.colors = colorArray

            // Create a BarData object and add the datasets
            val barData = BarData(dataSet)

            // Customize the chart
            binding.scatterBarChart.data = barData
            updateTextColorandChart()
        }


        private fun dataSetChart1to3() {
            // Initialising Charts
            graph1 = ArrayList()
            graph2 = ArrayList()
            graph3 = ArrayList()
            graph4 = ArrayList()
            graph5 = ArrayList()

            // Value of Chart 1
            graph1.add(BarEntry(0f, floatArrayOf(38f, 24f, 38f)))
            graph1.add(BarEntry(2f, floatArrayOf(19f, 62f, 19f)))
            graph1.add(BarEntry(4f, floatArrayOf(31f, 38f, 31f)))
            graph1.add(BarEntry(6f, floatArrayOf(38f, 62f)))
            graph1.add(BarEntry(8f, floatArrayOf(62f, 38f)))

            // Value of Chart 2
            graph2.add(BarEntry(0f, floatArrayOf(38f, 24f, 38f)))
            graph2.add(BarEntry(2f, floatArrayOf(19f, 62f, 19f)))
            graph2.add(BarEntry(4f, floatArrayOf(31f, 38f, 31f)))
            graph2.add(BarEntry(6f, floatArrayOf(38f, 62f)))
            graph2.add(BarEntry(8f, floatArrayOf(62f, 38f)))
            graph2.add(BarEntry(10f, floatArrayOf(38f, 62f, 38f)))
            graph2.add(BarEntry(12f, floatArrayOf(62f, 38f, 62f)))


            // Value of Pie Chart
            graph3.add(PieEntry(62f, "222,49°"))
            graph3.add(PieEntry(38f, "137,51°"))

        }

        private fun dataSetChart4and5() {
            graph4.clear()
            graph5.clear()
            // Value of Chart 4 and 5
            val inputValue = binding.etxtInputValue.text?.toString()
            val floatValue = inputValue?.toFloatOrNull()

            var gValue1 = (0.6180339887 * 1).toFloat()
            var gValue2 = (0.3819660113 * 1).toFloat()
            var gValue3 = (0.2360679775 * 1).toFloat()
            var gValue4 = (0.1909830056 * 1).toFloat()
            var gValue5 = (0.3090169944 * 1).toFloat()

            if (floatValue != null) {
                gValue1 = (gValue1 * floatValue)

                gValue2 = (gValue2 * floatValue)

                gValue3 = (gValue3 * floatValue)

                gValue4 = (gValue4 * floatValue)

                gValue5 = (gValue5 * floatValue)

            }

            graph4.add(BarEntry(0f, floatArrayOf(gValue2, gValue3, gValue2)))
            graph4.add(BarEntry(2f, floatArrayOf(gValue4, gValue1, gValue4)))
            graph4.add(BarEntry(4f, floatArrayOf(gValue5, gValue2, gValue5)))
            graph4.add(BarEntry(6f, floatArrayOf(gValue2, gValue1)))
            graph4.add(BarEntry(8f, floatArrayOf(gValue1, gValue2)))
            graph4.add(BarEntry(10f, floatArrayOf(gValue2, gValue1, gValue2)))
            graph4.add(BarEntry(12f, floatArrayOf(gValue1, gValue2, gValue1)))


            graph5.add(BarEntry(0f, floatArrayOf(gValue2, gValue3, gValue2)))
            graph5.add(BarEntry(2f, floatArrayOf(gValue4, gValue1, gValue4)))
            graph5.add(BarEntry(4f, floatArrayOf(gValue5, gValue2, gValue5)))
            graph5.add(BarEntry(6f, floatArrayOf(gValue2, gValue1)))
            graph5.add(BarEntry(8f, floatArrayOf(gValue1, gValue2)))

        }

        inner class PercentageValueFormatter : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return String.format(Locale.getDefault(), "%.0f%%", value)
            }
        }

    private fun updateTextColorandChart(){
        binding.scatterBarChart.notifyDataSetChanged()
        binding.scatterBarChart.invalidate()
    }
}