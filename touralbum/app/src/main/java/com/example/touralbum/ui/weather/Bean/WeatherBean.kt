package com.example.touralbum.ui.weather.Bean

class WeatherBean {
    /**
     * code : 10000
     * charge : false
     * msg : 查询成功
     * result : {"HeWeather5":[{"aqi":{"city":{"aqi":"62","qlty":"良","pm25":"44","pm10":"60","no2":"12","so2":"13","co":"0.8","o3":"114"}},"basic":{"city":"新泰","cnty":"中国","id":"CN101120802","lat":"35.91038513","lon":"117.76609039","update":{"loc":"2020-02-10 18:24","utc":"2020-02-10 10:24"}},"daily_forecast":[{"astro":{"mr":"18:52","ms":"07:50","sr":"07:00","ss":"17:45"},"cond":{"code_d":"101","code_n":"101","txt_d":"多云","txt_n":"多云"},"date":"2020-02-10","hum":"73","pcpn":"0.0","pop":"0","pres":"988","tmp":{"max":"16","min":"1"},"uv":"3","vis":"25","wind":{"deg":"216","dir":"西南风","sc":"3-4","spd":"24"}},{"astro":{"mr":"20:05","ms":"08:29","sr":"06:59","ss":"17:46"},"cond":{"code_d":"101","code_n":"101","txt_d":"多云","txt_n":"多云"},"date":"2020-02-11","hum":"91","pcpn":"0.0","pop":"6","pres":"985","tmp":{"max":"15","min":"2"},"uv":"6","vis":"25","wind":{"deg":"181","dir":"南风","sc":"3-4","spd":"16"}},{"astro":{"mr":"21:16","ms":"09:06","sr":"06:58","ss":"17:47"},"cond":{"code_d":"100","code_n":"101","txt_d":"晴","txt_n":"多云"},"date":"2020-02-12","hum":"86","pcpn":"1.0","pop":"55","pres":"983","tmp":{"max":"16","min":"4"},"uv":"7","vis":"25","wind":{"deg":"170","dir":"南风","sc":"3-4","spd":"21"}},{"astro":{"mr":"22:26","ms":"09:40","sr":"06:57","ss":"17:48"},"cond":{"code_d":"101","code_n":"101","txt_d":"多云","txt_n":"多云"},"date":"2020-02-13","hum":"91","pcpn":"0.0","pop":"0","pres":"983","tmp":{"max":"18","min":"4"},"uv":"4","vis":"25","wind":{"deg":"177","dir":"南风","sc":"3-4","spd":"17"}},{"astro":{"mr":"23:35","ms":"10:14","sr":"06:56","ss":"17:49"},"cond":{"code_d":"104","code_n":"404","txt_d":"阴","txt_n":"雨夹雪"},"date":"2020-02-14","hum":"88","pcpn":"0.0","pop":"25","pres":"990","tmp":{"max":"13","min":"-4"},"uv":"3","vis":"12","wind":{"deg":"44","dir":"东北风","sc":"4-5","spd":"29"}},{"astro":{"mr":"00:00","ms":"10:49","sr":"06:55","ss":"17:50"},"cond":{"code_d":"404","code_n":"101","txt_d":"雨夹雪","txt_n":"多云"},"date":"2020-02-15","hum":"88","pcpn":"2.5","pop":"40","pres":"997","tmp":{"max":"0","min":"-10"},"uv":"1","vis":"22","wind":{"deg":"0","dir":"北风","sc":"4-5","spd":"30"}},{"astro":{"mr":"00:42","ms":"11:27","sr":"06:54","ss":"17:51"},"cond":{"code_d":"101","code_n":"100","txt_d":"多云","txt_n":"晴"},"date":"2020-02-16","hum":"65","pcpn":"0.0","pop":"0","pres":"997","tmp":{"max":"0","min":"-10"},"uv":"4","vis":"25","wind":{"deg":"354","dir":"北风","sc":"3-4","spd":"23"}}],"hourly_forecast":[{"cond":{"code":"100","txt":"晴"},"date":"2020-02-10 19:00","hum":"70","pop":"0","pres":"989","tmp":"9","wind":{"deg":"188","dir":"南风","sc":"1-2","spd":"1"}},{"cond":{"code":"101","txt":"多云"},"date":"2020-02-10 22:00","hum":"63","pop":"0","pres":"989","tmp":"6","wind":{"deg":"189","dir":"南风","sc":"3-4","spd":"18"}},{"cond":{"code":"101","txt":"多云"},"date":"2020-02-11 01:00","hum":"67","pop":"0","pres":"990","tmp":"4","wind":{"deg":"190","dir":"南风","sc":"3-4","spd":"14"}},{"cond":{"code":"101","txt":"多云"},"date":"2020-02-11 04:00","hum":"71","pop":"0","pres":"989","tmp":"2","wind":{"deg":"182","dir":"南风","sc":"1-2","spd":"4"}},{"cond":{"code":"101","txt":"多云"},"date":"2020-02-11 07:00","hum":"74","pop":"0","pres":"987","tmp":"2","wind":{"deg":"173","dir":"南风","sc":"3-4","spd":"23"}},{"cond":{"code":"101","txt":"多云"},"date":"2020-02-11 10:00","hum":"60","pop":"0","pres":"985","tmp":"7","wind":{"deg":"173","dir":"南风","sc":"3-4","spd":"17"}},{"cond":{"code":"101","txt":"多云"},"date":"2020-02-11 13:00","hum":"48","pop":"1","pres":"985","tmp":"12","wind":{"deg":"173","dir":"南风","sc":"3-4","spd":"18"}},{"cond":{"code":"101","txt":"多云"},"date":"2020-02-11 16:00","hum":"50","pop":"1","pres":"984","tmp":"12","wind":{"deg":"174","dir":"南风","sc":"3-4","spd":"20"}}],"now":{"cond":{"code":"100","txt":"晴"},"fl":"9","hum":"35","pcpn":"0.0","pres":"995","tmp":"12","vis":"13","wind":{"deg":"179","dir":"南风","sc":"2","spd":"9"}},"status":"ok","suggestion":{"air":{"brf":"良","txt":"气象条件有利于空气污染物稀释、扩散和清除，可在室外正常活动。"},"comf":{"brf":"较不舒适","txt":"白天天气晴好，但仍会使您感觉偏冷，不很舒适，请注意适时添加衣物，以防感冒。"},"cw":{"brf":"适宜","txt":"适宜洗车，未来持续两天无雨天气较好，适合擦洗汽车，蓝天白云、风和日丽将伴您的车子连日洁净。"},"drsg":{"brf":"较冷","txt":"建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。"},"flu":{"brf":"易发","txt":"昼夜温差很大，易发生感冒，请注意适当增减衣服，加强自我防护避免感冒。"},"sport":{"brf":"较适宜","txt":"天气较好，但考虑风力较强且气温较低，推荐您进行室内运动，若在户外运动请注意防风并适当增减衣物。"},"trav":{"brf":"适宜","txt":"天气较好，风稍大，但温度适宜，是个好天气哦。适宜旅游，您可以尽情地享受大自然的无限风光。"},"uv":{"brf":"弱","txt":"紫外线强度较弱，建议涂擦SPF在12-15之间、PA+的防晒护肤品。"}}}]}
     */
    var code: String? = null
    var isCharge = false
    var msg: String? = null
    var result: ResultBean? = null

    class ResultBean {
        var heWeather5: List<HeWeather5Bean>? = null

        class HeWeather5Bean {
            /**
             * aqi : {"city":{"aqi":"62","qlty":"良","pm25":"44","pm10":"60","no2":"12","so2":"13","co":"0.8","o3":"114"}}
             * basic : {"city":"新泰","cnty":"中国","id":"CN101120802","lat":"35.91038513","lon":"117.76609039","update":{"loc":"2020-02-10 18:24","utc":"2020-02-10 10:24"}}
             * daily_forecast : [{"astro":{"mr":"18:52","ms":"07:50","sr":"07:00","ss":"17:45"},"cond":{"code_d":"101","code_n":"101","txt_d":"多云","txt_n":"多云"},"date":"2020-02-10","hum":"73","pcpn":"0.0","pop":"0","pres":"988","tmp":{"max":"16","min":"1"},"uv":"3","vis":"25","wind":{"deg":"216","dir":"西南风","sc":"3-4","spd":"24"}},{"astro":{"mr":"20:05","ms":"08:29","sr":"06:59","ss":"17:46"},"cond":{"code_d":"101","code_n":"101","txt_d":"多云","txt_n":"多云"},"date":"2020-02-11","hum":"91","pcpn":"0.0","pop":"6","pres":"985","tmp":{"max":"15","min":"2"},"uv":"6","vis":"25","wind":{"deg":"181","dir":"南风","sc":"3-4","spd":"16"}},{"astro":{"mr":"21:16","ms":"09:06","sr":"06:58","ss":"17:47"},"cond":{"code_d":"100","code_n":"101","txt_d":"晴","txt_n":"多云"},"date":"2020-02-12","hum":"86","pcpn":"1.0","pop":"55","pres":"983","tmp":{"max":"16","min":"4"},"uv":"7","vis":"25","wind":{"deg":"170","dir":"南风","sc":"3-4","spd":"21"}},{"astro":{"mr":"22:26","ms":"09:40","sr":"06:57","ss":"17:48"},"cond":{"code_d":"101","code_n":"101","txt_d":"多云","txt_n":"多云"},"date":"2020-02-13","hum":"91","pcpn":"0.0","pop":"0","pres":"983","tmp":{"max":"18","min":"4"},"uv":"4","vis":"25","wind":{"deg":"177","dir":"南风","sc":"3-4","spd":"17"}},{"astro":{"mr":"23:35","ms":"10:14","sr":"06:56","ss":"17:49"},"cond":{"code_d":"104","code_n":"404","txt_d":"阴","txt_n":"雨夹雪"},"date":"2020-02-14","hum":"88","pcpn":"0.0","pop":"25","pres":"990","tmp":{"max":"13","min":"-4"},"uv":"3","vis":"12","wind":{"deg":"44","dir":"东北风","sc":"4-5","spd":"29"}},{"astro":{"mr":"00:00","ms":"10:49","sr":"06:55","ss":"17:50"},"cond":{"code_d":"404","code_n":"101","txt_d":"雨夹雪","txt_n":"多云"},"date":"2020-02-15","hum":"88","pcpn":"2.5","pop":"40","pres":"997","tmp":{"max":"0","min":"-10"},"uv":"1","vis":"22","wind":{"deg":"0","dir":"北风","sc":"4-5","spd":"30"}},{"astro":{"mr":"00:42","ms":"11:27","sr":"06:54","ss":"17:51"},"cond":{"code_d":"101","code_n":"100","txt_d":"多云","txt_n":"晴"},"date":"2020-02-16","hum":"65","pcpn":"0.0","pop":"0","pres":"997","tmp":{"max":"0","min":"-10"},"uv":"4","vis":"25","wind":{"deg":"354","dir":"北风","sc":"3-4","spd":"23"}}]
             * hourly_forecast : [{"cond":{"code":"100","txt":"晴"},"date":"2020-02-10 19:00","hum":"70","pop":"0","pres":"989","tmp":"9","wind":{"deg":"188","dir":"南风","sc":"1-2","spd":"1"}},{"cond":{"code":"101","txt":"多云"},"date":"2020-02-10 22:00","hum":"63","pop":"0","pres":"989","tmp":"6","wind":{"deg":"189","dir":"南风","sc":"3-4","spd":"18"}},{"cond":{"code":"101","txt":"多云"},"date":"2020-02-11 01:00","hum":"67","pop":"0","pres":"990","tmp":"4","wind":{"deg":"190","dir":"南风","sc":"3-4","spd":"14"}},{"cond":{"code":"101","txt":"多云"},"date":"2020-02-11 04:00","hum":"71","pop":"0","pres":"989","tmp":"2","wind":{"deg":"182","dir":"南风","sc":"1-2","spd":"4"}},{"cond":{"code":"101","txt":"多云"},"date":"2020-02-11 07:00","hum":"74","pop":"0","pres":"987","tmp":"2","wind":{"deg":"173","dir":"南风","sc":"3-4","spd":"23"}},{"cond":{"code":"101","txt":"多云"},"date":"2020-02-11 10:00","hum":"60","pop":"0","pres":"985","tmp":"7","wind":{"deg":"173","dir":"南风","sc":"3-4","spd":"17"}},{"cond":{"code":"101","txt":"多云"},"date":"2020-02-11 13:00","hum":"48","pop":"1","pres":"985","tmp":"12","wind":{"deg":"173","dir":"南风","sc":"3-4","spd":"18"}},{"cond":{"code":"101","txt":"多云"},"date":"2020-02-11 16:00","hum":"50","pop":"1","pres":"984","tmp":"12","wind":{"deg":"174","dir":"南风","sc":"3-4","spd":"20"}}]
             * now : {"cond":{"code":"100","txt":"晴"},"fl":"9","hum":"35","pcpn":"0.0","pres":"995","tmp":"12","vis":"13","wind":{"deg":"179","dir":"南风","sc":"2","spd":"9"}}
             * status : ok
             * suggestion : {"air":{"brf":"良","txt":"气象条件有利于空气污染物稀释、扩散和清除，可在室外正常活动。"},"comf":{"brf":"较不舒适","txt":"白天天气晴好，但仍会使您感觉偏冷，不很舒适，请注意适时添加衣物，以防感冒。"},"cw":{"brf":"适宜","txt":"适宜洗车，未来持续两天无雨天气较好，适合擦洗汽车，蓝天白云、风和日丽将伴您的车子连日洁净。"},"drsg":{"brf":"较冷","txt":"建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。"},"flu":{"brf":"易发","txt":"昼夜温差很大，易发生感冒，请注意适当增减衣服，加强自我防护避免感冒。"},"sport":{"brf":"较适宜","txt":"天气较好，但考虑风力较强且气温较低，推荐您进行室内运动，若在户外运动请注意防风并适当增减衣物。"},"trav":{"brf":"适宜","txt":"天气较好，风稍大，但温度适宜，是个好天气哦。适宜旅游，您可以尽情地享受大自然的无限风光。"},"uv":{"brf":"弱","txt":"紫外线强度较弱，建议涂擦SPF在12-15之间、PA+的防晒护肤品。"}}
             */
            var aqi: AqiBean? = null
            var basic: BasicBean? = null
            var now: NowBean? = null
            var status: String? = null
            var suggestion: SuggestionBean? = null
            var daily_forecast: List<DailyForecastBean>? = null
            var hourly_forecast: List<HourlyForecastBean>? = null

            class AqiBean {
                /**
                 * city : {"aqi":"62","qlty":"良","pm25":"44","pm10":"60","no2":"12","so2":"13","co":"0.8","o3":"114"}
                 */
                var city: CityBean? = null

                class CityBean {
                    /**
                     * aqi : 62
                     * qlty : 良
                     * pm25 : 44
                     * pm10 : 60
                     * no2 : 12
                     * so2 : 13
                     * co : 0.8
                     * o3 : 114
                     */
                    var aqi: String? = null
                    var qlty: String? = null
                    var pm25: String? = null
                    var pm10: String? = null
                    var no2: String? = null
                    var so2: String? = null
                    var co: String? = null
                    var o3: String? = null
                }
            }

            class BasicBean {
                /**
                 * city : 新泰
                 * cnty : 中国
                 * id : CN101120802
                 * lat : 35.91038513
                 * lon : 117.76609039
                 * update : {"loc":"2020-02-10 18:24","utc":"2020-02-10 10:24"}
                 */
                var city: String? = null
                var cnty: String? = null
                var id: String? = null
                var lat: String? = null
                var lon: String? = null
                var update: UpdateBean? = null

                class UpdateBean {
                    /**
                     * loc : 2020-02-10 18:24
                     * utc : 2020-02-10 10:24
                     */
                    var loc: String? = null
                    var utc: String? = null
                }
            }

            class NowBean {
                /**
                 * cond : {"code":"100","txt":"晴"}
                 * fl : 9
                 * hum : 35
                 * pcpn : 0.0
                 * pres : 995
                 * tmp : 12
                 * vis : 13
                 * wind : {"deg":"179","dir":"南风","sc":"2","spd":"9"}
                 */
                var cond: CondBean? = null
                var fl: String? = null
                var hum: String? = null
                var pcpn: String? = null
                var pres: String? = null
                var tmp: String? = null
                var vis: String? = null
                var wind: WindBean? = null

                class CondBean {
                    /**
                     * code : 100
                     * txt : 晴
                     */
                    var code: String? = null
                    var txt: String? = null
                }

                class WindBean {
                    /**
                     * deg : 179
                     * dir : 南风
                     * sc : 2
                     * spd : 9
                     */
                    var deg: String? = null
                    var dir: String? = null
                    var sc: String? = null
                    var spd: String? = null
                }
            }

            class SuggestionBean {
                /**
                 * air : {"brf":"良","txt":"气象条件有利于空气污染物稀释、扩散和清除，可在室外正常活动。"}
                 * comf : {"brf":"较不舒适","txt":"白天天气晴好，但仍会使您感觉偏冷，不很舒适，请注意适时添加衣物，以防感冒。"}
                 * cw : {"brf":"适宜","txt":"适宜洗车，未来持续两天无雨天气较好，适合擦洗汽车，蓝天白云、风和日丽将伴您的车子连日洁净。"}
                 * drsg : {"brf":"较冷","txt":"建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。"}
                 * flu : {"brf":"易发","txt":"昼夜温差很大，易发生感冒，请注意适当增减衣服，加强自我防护避免感冒。"}
                 * sport : {"brf":"较适宜","txt":"天气较好，但考虑风力较强且气温较低，推荐您进行室内运动，若在户外运动请注意防风并适当增减衣物。"}
                 * trav : {"brf":"适宜","txt":"天气较好，风稍大，但温度适宜，是个好天气哦。适宜旅游，您可以尽情地享受大自然的无限风光。"}
                 * uv : {"brf":"弱","txt":"紫外线强度较弱，建议涂擦SPF在12-15之间、PA+的防晒护肤品。"}
                 */
                var air: AirBean? = null
                var comf: ComfBean? = null
                var cw: CwBean? = null
                var drsg: DrsgBean? = null
                var flu: FluBean? = null
                var sport: SportBean? = null
                var trav: TravBean? = null
                var uv: UvBean? = null

                class AirBean {
                    /**
                     * brf : 良
                     * txt : 气象条件有利于空气污染物稀释、扩散和清除，可在室外正常活动。
                     */
                    var brf: String? = null
                    var txt: String? = null
                }

                class ComfBean {
                    /**
                     * brf : 较不舒适
                     * txt : 白天天气晴好，但仍会使您感觉偏冷，不很舒适，请注意适时添加衣物，以防感冒。
                     */
                    var brf: String? = null
                    var txt: String? = null
                }

                class CwBean {
                    /**
                     * brf : 适宜
                     * txt : 适宜洗车，未来持续两天无雨天气较好，适合擦洗汽车，蓝天白云、风和日丽将伴您的车子连日洁净。
                     */
                    var brf: String? = null
                    var txt: String? = null
                }

                class DrsgBean {
                    /**
                     * brf : 较冷
                     * txt : 建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。
                     */
                    var brf: String? = null
                    var txt: String? = null
                }

                class FluBean {
                    /**
                     * brf : 易发
                     * txt : 昼夜温差很大，易发生感冒，请注意适当增减衣服，加强自我防护避免感冒。
                     */
                    var brf: String? = null
                    var txt: String? = null
                }

                class SportBean {
                    /**
                     * brf : 较适宜
                     * txt : 天气较好，但考虑风力较强且气温较低，推荐您进行室内运动，若在户外运动请注意防风并适当增减衣物。
                     */
                    var brf: String? = null
                    var txt: String? = null
                }

                class TravBean {
                    /**
                     * brf : 适宜
                     * txt : 天气较好，风稍大，但温度适宜，是个好天气哦。适宜旅游，您可以尽情地享受大自然的无限风光。
                     */
                    var brf: String? = null
                    var txt: String? = null
                }

                class UvBean {
                    /**
                     * brf : 弱
                     * txt : 紫外线强度较弱，建议涂擦SPF在12-15之间、PA+的防晒护肤品。
                     */
                    var brf: String? = null
                    var txt: String? = null
                }
            }

            class DailyForecastBean {
                /**
                 * astro : {"mr":"18:52","ms":"07:50","sr":"07:00","ss":"17:45"}
                 * cond : {"code_d":"101","code_n":"101","txt_d":"多云","txt_n":"多云"}
                 * date : 2020-02-10
                 * hum : 73
                 * pcpn : 0.0
                 * pop : 0
                 * pres : 988
                 * tmp : {"max":"16","min":"1"}
                 * uv : 3
                 * vis : 25
                 * wind : {"deg":"216","dir":"西南风","sc":"3-4","spd":"24"}
                 */
                var astro: AstroBean? = null
                var cond: CondBeanX? = null
                var date: String? = null
                var hum: String? = null
                var pcpn: String? = null
                var pop: String? = null
                var pres: String? = null
                var tmp: TmpBean? = null
                var uv: String? = null
                var vis: String? = null
                var wind: WindBeanX? = null

                class AstroBean {
                    /**
                     * mr : 18:52
                     * ms : 07:50
                     * sr : 07:00
                     * ss : 17:45
                     */
                    var mr: String? = null
                    var ms: String? = null
                    var sr: String? = null
                    var ss: String? = null
                }

                class CondBeanX {
                    /**
                     * code_d : 101
                     * code_n : 101
                     * txt_d : 多云
                     * txt_n : 多云
                     */
                    var code_d: String? = null
                    var code_n: String? = null
                    var txt_d: String? = null
                    var txt_n: String? = null
                }

                class TmpBean {
                    /**
                     * max : 16
                     * min : 1
                     */
                    var max: String? = null
                    var min: String? = null
                }

                class WindBeanX {
                    /**
                     * deg : 216
                     * dir : 西南风
                     * sc : 3-4
                     * spd : 24
                     */
                    var deg: String? = null
                    var dir: String? = null
                    var sc: String? = null
                    var spd: String? = null
                }
            }

            class HourlyForecastBean {
                /**
                 * cond : {"code":"100","txt":"晴"}
                 * date : 2020-02-10 19:00
                 * hum : 70
                 * pop : 0
                 * pres : 989
                 * tmp : 9
                 * wind : {"deg":"188","dir":"南风","sc":"1-2","spd":"1"}
                 */
                var cond: CondBeanXX? = null
                var date: String? = null
                var hum: String? = null
                var pop: String? = null
                var pres: String? = null
                var tmp: String? = null
                var wind: WindBeanXX? = null

                class CondBeanXX {
                    /**
                     * code : 100
                     * txt : 晴
                     */
                    var code: String? = null
                    var txt: String? = null
                }

                class WindBeanXX {
                    /**
                     * deg : 188
                     * dir : 南风
                     * sc : 1-2
                     * spd : 1
                     */
                    var deg: String? = null
                    var dir: String? = null
                    var sc: String? = null
                    var spd: String? = null
                }
            }
        }
    }
}