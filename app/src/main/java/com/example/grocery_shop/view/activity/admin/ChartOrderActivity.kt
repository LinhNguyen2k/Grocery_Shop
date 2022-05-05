package com.example.grocery_shop.view.activity.admin

import android.graphics.Color
import android.os.Handler
import androidx.activity.viewModels

import com.example.grocery_shop.base.BaseVMActivity
import com.example.grocery_shop.base.PriceHelper
import com.example.grocery_shop.databinding.ActivityChartOrderBinding
import com.example.grocery_shop.util.UserManager
import com.example.grocery_shop.viewmodel.AuthenticationViewModel
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.formatter.PercentFormatter

import com.github.mikephil.charting.utils.ColorTemplate


class ChartOrderActivity : BaseVMActivity<ActivityChartOrderBinding, AuthenticationViewModel>() {
    private val viewModels by viewModels<AuthenticationViewModel>()
    var result: Float = 0F
    override fun initView() {
        setupPieChart()
        callCategoryOrderYear()
    }

    override fun initListener() {
    }

    override fun initData() {
    }

    private fun setupPieChart() {
        binding.pieChart.isDrawHoleEnabled = true
        binding.pieChart.setUsePercentValues(true)
        binding.pieChart.animateXY(
            1000,
            1000
        ) // This 1000 is time that how much time piechart chreated
        binding.pieChart.setEntryLabelTextSize(12.0f)
        binding.pieChart.setEntryLabelColor(Color.BLACK)
        binding.pieChart.centerText = "Doanh Thu Theo Năm"
        binding.pieChart.setCenterTextSize(24.0f)
        binding.pieChart.isDrawHoleEnabled = true
        binding.pieChart.holeRadius = 35f
        binding.pieChart.description.isEnabled = false
        val l: Legend = binding.pieChart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.isEnabled = true
    }


    private fun callCategoryOrderYear() {
        val entries: ArrayList<PieEntry> = ArrayList()
        var result: Float = 0F
        loadingDialog.show(this, "")
        viewModels.getAllOrder(
            "Bearer " + UserManager.getToken(this),
            "2022",
            "1",
            onComplete = { data ->
                if (data.totalMoney > 0)
                    entries.add(
                        PieEntry(
                            data.totalMoney.toFloat(),
                            "Sản Phẩm Khuyến Mãi: ${PriceHelper.getPriceFormat(data.totalMoney)}đ"
                        )
                    )
                result += data.totalMoney.toFloat()
                loadingDialog.dismiss()

                viewModels.getAllOrder(
                    "Bearer " + UserManager.getToken(this),
                    "2022",
                    "2",
                    onComplete = { data2 ->
                        if (data2.totalMoney > 0)
                            entries.add(
                                PieEntry(
                                    data2.totalMoney.toFloat(), "Rau - Củ - Trái Cây: ${
                                        PriceHelper.getPriceFormat(
                                            data2.totalMoney
                                        )
                                    }đ"
                                )
                            )
                        result += data2.totalMoney.toFloat()

                        viewModels.getAllOrder(
                            "Bearer " + UserManager.getToken(this),
                            "2022",
                            "3",
                            onComplete = { data3 ->
                                if (data3.totalMoney > 0)
                                    entries.add(
                                        PieEntry(
                                            data3.totalMoney.toFloat(), "Thịt - Trứng - Hải Sản: ${
                                                PriceHelper.getPriceFormat(
                                                    data3.totalMoney
                                                )
                                            }đ"
                                        )
                                    )
                                result += data3.totalMoney.toFloat()

                                viewModels.getAllOrder(
                                    "Bearer " + UserManager.getToken(this),
                                    "2022",
                                    "4",
                                    onComplete = { data4 ->
                                        if (data4.totalMoney > 0)
                                            entries.add(
                                                PieEntry(
                                                    data4.totalMoney.toFloat(),
                                                    "Thực Phẩm Đông Lạnh: ${
                                                        PriceHelper.getPriceFormat(
                                                            data4.totalMoney
                                                        )
                                                    }đ"
                                                )
                                            )
                                        result += data4.totalMoney.toFloat()
                                        viewModels.getAllOrder(
                                            "Bearer " + UserManager.getToken(this),
                                            "2022",
                                            "5",
                                            onComplete = { data5 ->
                                                if (data5.totalMoney > 0)
                                                    entries.add(
                                                        PieEntry(
                                                            data5.totalMoney.toFloat(),
                                                            "Bánh Kẹo - Đồ Ăn Vặt: ${
                                                                PriceHelper.getPriceFormat(
                                                                    data5.totalMoney
                                                                )
                                                            }đ"
                                                        )
                                                    )
                                                result += data5.totalMoney.toFloat()
                                                val colors: ArrayList<Int> = ArrayList()
                                                for (color in ColorTemplate.MATERIAL_COLORS) {
                                                    colors.add(color)
                                                }
                                                for (color in ColorTemplate.VORDIPLOM_COLORS) {
                                                    colors.add(color)
                                                }
                                                val dataSet = PieDataSet(
                                                    entries,
                                                    "Tổng Doanh Thu Theo Năm: ${
                                                        PriceHelper.getPriceFormat(
                                                            result.toInt()
                                                        )
                                                    }đ"
                                                )
                                                dataSet.colors = colors
                                                val data = PieData(dataSet)
                                                data.setDrawValues(true)
                                                data.setValueFormatter(PercentFormatter(binding.pieChart))
                                                data.setValueTextSize(12f)
                                                data.setValueTextColor(Color.DKGRAY)
                                                binding.pieChart.data = data
                                                binding.pieChart.invalidate()
                                                binding.pieChart.animateY(
                                                    1400,
                                                    Easing.EaseInOutQuad
                                                )

                                            }, {
                                                loadingDialog.dismiss()
                                            })
                                    }, {
                                        loadingDialog.dismiss()
                                    })
                            }, {
                                loadingDialog.dismiss()
                            })
                    }, onErrors = {
                        loadingDialog.dismiss()
                    })
            }, onErrors = {
                loadingDialog.dismiss()
            })


    }
}