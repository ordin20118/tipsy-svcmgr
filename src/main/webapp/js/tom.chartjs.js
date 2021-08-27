! function(c) {
    "use strict";
    var r = function() {
        this.$body = c("body"), this.charts = []
    };
    r.prototype.respChart = function(a, t, e, o) {
        var n = Chart.controllers.line.prototype.draw;
        Chart.controllers.line.prototype.draw = function() {
            n.apply(this, arguments);
            var r = this.chart.chart.ctx,
                a = r.stroke;
            r.stroke = function() {
                r.save(), r.shadowColor = "rgba(0,0,0,0.01)", r.shadowBlur = 20, r.shadowOffsetX = 0, r.shadowOffsetY = 5, a.apply(this, arguments), r.restore()
            }
        };
        var s = Chart.controllers.doughnut.prototype.draw;
        Chart.controllers.doughnut = Chart.controllers.doughnut.extend({
            draw: function() {
                s.apply(this, arguments);
                var r = this.chart.chart.ctx,
                    a = r.fill;
                r.fill = function() {
                    r.save(), r.shadowColor = "rgba(0,0,0,0.03)", r.shadowBlur = 4, r.shadowOffsetX = 0, r.shadowOffsetY = 3, a.apply(this, arguments), r.restore()
                }
            }
        });
        var l = Chart.controllers.bar.prototype.draw;
        Chart.controllers.bar = Chart.controllers.bar.extend({
            draw: function() {
                l.apply(this, arguments);
                var r = this.chart.chart.ctx,
                    a = r.fill;
                r.fill = function() {
                    r.save(), r.shadowColor = "rgba(0,0,0,0.01)", r.shadowBlur = 20, r.shadowOffsetX = 4, r.shadowOffsetY = 5, a.apply(this, arguments), r.restore()
                }
            }
        }), Chart.defaults.global.defaultFontColor = "#8391a2", Chart.defaults.scale.gridLines.color = "#8391a2";
        var i = a.get(0).getContext("2d"),
            d = c(a).parent();
        return function() {
            var r;
            switch (a.attr("width", c(d).width()), t) {
                case "Line":
                    r = new Chart(i, {
                        type: "line",
                        data: e,
                        options: o
                    });
                    break;
                case "Doughnut":
                    r = new Chart(i, {
                        type: "doughnut",
                        data: e,
                        options: o
                    });
                    break;
                case "Pie":
                    r = new Chart(i, {
                        type: "pie",
                        data: e,
                        options: o
                    });
                    break;
                case "Bar":
                    r = new Chart(i, {
                        type: "bar",
                        data: e,
                        options: o
                    });
                    break;
                case "horizontalBar":
                    r = new Chart(i, {
                        type: "horizontalBar",
                        data: e,
                        options: o
                    });
                    break;
                case "Radar":
                    r = new Chart(i, {
                        type: "radar",
                        data: e,
                        options: o
                    });
                    break;
                case "PolarArea":
                    r = new Chart(i, {
                        data: e,
                        type: "polarArea",
                        options: o
                    })
            }
            return r
        }()
    }, r.prototype.initCharts = function() {
        var r = [];
        
        //회원별 활동 카테고리
        if (0 < c("#donut-chart").length) {
            r.push(this.respChart(c("#donut-chart"), "Doughnut", {
                labels: ["주방가전", "영상가전", "생활가전", "계절가전"],
                datasets: [{
                    data: [300, 135, 154, 123],
                    backgroundColor: ["#36a2eb", "#4ac0c0", "#ff6383", "#ffcd56"],
                    borderColor: "transparent",
                    borderWidth: "20"
                }]
            }, {
                maintainAspectRatio: !1,
                cutoutPercentage: 40,
                legend: {
                    display: !1
                }
            }))
        }
        //최저가 추이
        if (0 < c("#line-chart").length) {
            r.push(this.respChart(c("#line-chart"), "Line", {
                labels: ["08", "09", "10", "11", "12"],
                datasets: [{
                    label: "Price",
                    fill: !0,
                    backgroundColor: "transparent",
                    borderColor: "#ff6699",
                    borderDash: [5, 5],
                    data: [2000, 1990, 2000, 2000, 1950]
                }]
            }, {
                maintainAspectRatio: !1,
                legend: {
                    display: !1
                },
                tooltips: {
                    intersect: !1
                },
                hover: {
                    intersect: !0
                },
                plugins: {
                    filler: {
                        propagate: !1
                    }
                },
                scales: {
                    xAxes: [{
                        reverse: !0,
                        gridLines: {
                            color: "rgba(0,0,0,0.05)"
                        }
                    }],
                    yAxes: [{
                        ticks: {
                            stepSize: 20
                        },
                        display: !0,
                        borderDash: [5, 5],
                        gridLines: {
                            color: "rgba(0,0,0,0)",
                            fontColor: "#fff"
                        }
                    }]
                }
            }))
        }

        return r
    }, r.prototype.init = function() {
        var a = this;
        Chart.defaults.global.defaultFontFamily = '-apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,Oxygen-Sans,Ubuntu,Cantarell,"Helvetica Neue",sans-serif', a.charts = this.initCharts(), c(window).on("resize", function(r) {
            c.each(a.charts, function(r, a) {
                try {
                    a.destroy()
                } catch (r) {}
            }), a.charts = a.initCharts()
        })
    }, c.ChartJs = new r, c.ChartJs.Constructor = r
}(window.jQuery),
function(r) {
    "use strict";
    window.jQuery.ChartJs.init()
}();