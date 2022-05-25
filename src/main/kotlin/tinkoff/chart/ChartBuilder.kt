package tinkoff.chart

import space.kscience.dataforge.meta.invoke
import space.kscience.plotly.Plotly
import space.kscience.plotly.bar
import space.kscience.plotly.toHTML
import tinkoff.model.LikesRecord
import tinkoff.model.Tweet
import java.time.format.DateTimeFormatter


class ChartBuilder {

    fun build(tweetId: String, records: List<LikesRecord>): String {
        if (records.isEmpty())
            throw Exception("Trying to build chart for empty data")
        if (!records.all { it.tweetId == tweetId })
            throw Exception("Trying to build chart for multiple tweets.")
        val title = "Likes chart for tweet with id = $tweetId"
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val dates = records.sortedBy { it.relevantAt }.map { it.relevantAt.format(formatter) }
        val likesCounts = records.map { it.likesCount }
        return buildPlot(title, dates, likesCounts)
    }

    private fun buildPlot(title: String, dates: List<String>, likesCounts: List<Int>): String =
        Plotly.plot {
            bar {
                x(*(dates.toTypedArray()))
                y(*(likesCounts.toTypedArray()))
                marker {
                    color("rgb(0, 191, 255)")
                }
                showlegend = false
            }

            layout {
                title {
                    text = title
                    font {
                        family = "Raleway, sans-serif"
                    }
                }
                xaxis {
                    tickangle = -45
                }
                yaxis {
                    zeroline = false
                    gridwidth = 2
                }
                bargap = 0.05
            }
        }.toHTML()

}