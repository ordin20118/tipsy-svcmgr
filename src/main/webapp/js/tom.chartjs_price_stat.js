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
    }, r.prototype.initCharts = function(priceStat) {
        var r = [];
        
        //console.log(priceStat);
        
        let labels = [];
        let data   = [];
        
        let maxPrice = priceStat[0]['lowest_price'];
        let minPrice = priceStat[0]['lowest_price'];
        
        for(let i=0; i<priceStat.length; i++) {
        	let pStat = priceStat[i];
        	
        	labels.push(moment(pStat['stat_end']).format("YYYY-MM-DD"));
        	data.push(pStat['lowest_price']);
        	
        	if(maxPrice < pStat['lowest_price']) {
        		maxPrice = pStat['lowest_price'];
        	}
        	if(minPrice > pStat['lowest_price']) {
        		minPrice = pStat['lowest_price'];
        	}
        	
        }
        let gap = maxPrice - minPrice;
        let step =0;
        if(gap != 0) {
        	step = gap/3;
        }
        if(step > 1000000) {
        	step = Math.round(step/100000) * 100000;
        } else if(step > 100000) {
        	step = Math.round(step/10000) * 10000;
        } else if(step > 10000) {
        	step = Math.round(step/1000) * 1000;
        } else if(step > 1000) {
        	step = Math.round(step/100) * 100;
        } else if(step > 100) {
        	step = Math.round(step/10) * 10;
        }
        
        
        console.log(labels);
        console.log(data);
        console.log("max["+maxPrice+"] min["+minPrice+"]: gap["+gap+"] step["+step+"]");
        
        
        
        //최저가 추이
        if (0 < c("#line-chart").length) {
            r.push(this.respChart(c("#line-chart"), "Line", {
                labels: labels,
                datasets: [{
                    label: "Price",
                    fill: !0,
                    backgroundColor: "transparent",
                    borderColor: "#ff6699",
                    borderDash: [5, 5],
                    data: data
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
                tooltips: {
		            callbacks: {
		                label: function(tooltipItem, chart){
		                	let dslabel = chart['datasets'][0]['label'];
		                    return dslabel + ": " + numFormat01(tooltipItem['yLabel']);
		                }
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
                            stepSize: step,
                            callback: function (value) {
                            	return numFormat01(value);
                        	}
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
    }, r.prototype.init = function(priceStatList) {
    	//console.log("init : " + priceStatList);
        var a = this;
        Chart.defaults.global.defaultFontFamily = '-apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,Oxygen-Sans,Ubuntu,Cantarell,"Helvetica Neue",sans-serif', a.charts = this.initCharts(priceStatList), c(window).on("resize", function(r) {
            c.each(a.charts, function(r, a) {
                try {
                    a.destroy()
                } catch (r) {}
            }), a.charts = a.initCharts(priceStatList)
        })
    }, c.ChartJs = new r, c.ChartJs.Constructor = r
}(window.jQuery),
function(r) {
    "use strict";
    //window.jQuery.ChartJs.init()
}();