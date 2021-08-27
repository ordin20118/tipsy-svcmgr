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
    }, r.prototype.initCharts = function(actStat) {
        var r = [];
        console.log(actStat);
        
        var labels =[];
        var data = [];
        
        for(var i in actStat){
        	labels.push(actStat[i].categ_name);
        	data.push(actStat[i].act_point)
        }
        
        console.log(labels);
        console.log(data);
        
      //회원별 활동 카테고리
        if (0 < c("#doughnut-chart").length) {
            r.push(this.respChart(c("#doughnut-chart"), "Doughnut", {
                labels: labels,
                datasets: [{
                    data: data,
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

        

        return r
    }, r.prototype.init = function(actStat) {
    	console.log("init : " + actStat);
        var a = this;
        Chart.defaults.global.defaultFontFamily = '-apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,Oxygen-Sans,Ubuntu,Cantarell,"Helvetica Neue",sans-serif', a.charts = this.initCharts(actStat), c(window).on("resize", function(r) {
            c.each(a.charts, function(r, a) {
                try {
                    a.destroy()
                } catch (r) {}
            }), a.charts = a.initCharts(actStat)
        })
    }, c.ChartJs = new r, c.ChartJs.Constructor = r
}(window.jQuery),
function(r) {
    "use strict";
    //window.jQuery.ChartJs.init()
}();