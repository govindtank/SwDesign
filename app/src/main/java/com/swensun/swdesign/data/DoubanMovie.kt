package com.swensun.swdesign.data

/**
 * Created by macmini on 2017/8/16.
 */

class DoubanMovie {

    /**
     * count : 2
     * start : 0
     * total : 250
     * subjects : [{"rating":{"max":10,"average":9.6,"stars":"50","min":0},"genres":["犯罪","剧情"],"title":"肖申克的救赎","casts":[{"alt":"https://movie.douban.com/celebrity/1054521/","avatars":{"small":"http://img7.doubanio.com/img/celebrity/small/17525.jpg","large":"http://img7.doubanio.com/img/celebrity/large/17525.jpg","medium":"http://img7.doubanio.com/img/celebrity/medium/17525.jpg"},"name":"蒂姆·罗宾斯","id":"1054521"},{"alt":"https://movie.douban.com/celebrity/1054534/","avatars":{"small":"http://img7.doubanio.com/img/celebrity/small/34642.jpg","large":"http://img7.doubanio.com/img/celebrity/large/34642.jpg","medium":"http://img7.doubanio.com/img/celebrity/medium/34642.jpg"},"name":"摩根·弗里曼","id":"1054534"},{"alt":"https://movie.douban.com/celebrity/1041179/","avatars":{"small":"http://img3.doubanio.com/img/celebrity/small/5837.jpg","large":"http://img3.doubanio.com/img/celebrity/large/5837.jpg","medium":"http://img3.doubanio.com/img/celebrity/medium/5837.jpg"},"name":"鲍勃·冈顿","id":"1041179"}],"collect_count":1103801,"original_title":"The Shawshank Redemption","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1047973/","avatars":{"small":"http://img7.doubanio.com/img/celebrity/small/230.jpg","large":"http://img7.doubanio.com/img/celebrity/large/230.jpg","medium":"http://img7.doubanio.com/img/celebrity/medium/230.jpg"},"name":"弗兰克·德拉邦特","id":"1047973"}],"year":"1994","images":{"small":"http://img7.doubanio.com/view/movie_poster_cover/ipst/public/p480747492.jpg","large":"http://img7.doubanio.com/view/movie_poster_cover/lpst/public/p480747492.jpg","medium":"http://img7.doubanio.com/view/movie_poster_cover/spst/public/p480747492.jpg"},"alt":"https://movie.douban.com/subject/1292052/","id":"1292052"},{"rating":{"max":10,"average":9.5,"stars":"50","min":0},"genres":["剧情","爱情","同性"],"title":"霸王别姬","casts":[{"alt":"https://movie.douban.com/celebrity/1003494/","avatars":{"small":"http://img3.doubanio.com/img/celebrity/small/67.jpg","large":"http://img3.doubanio.com/img/celebrity/large/67.jpg","medium":"http://img3.doubanio.com/img/celebrity/medium/67.jpg"},"name":"张国荣","id":"1003494"},{"alt":"https://movie.douban.com/celebrity/1050265/","avatars":{"small":"http://img7.doubanio.com/img/celebrity/small/46345.jpg","large":"http://img7.doubanio.com/img/celebrity/large/46345.jpg","medium":"http://img7.doubanio.com/img/celebrity/medium/46345.jpg"},"name":"张丰毅","id":"1050265"},{"alt":"https://movie.douban.com/celebrity/1035641/","avatars":{"small":"http://img3.doubanio.com/img/celebrity/small/1399268395.47.jpg","large":"http://img3.doubanio.com/img/celebrity/large/1399268395.47.jpg","medium":"http://img3.doubanio.com/img/celebrity/medium/1399268395.47.jpg"},"name":"巩俐","id":"1035641"}],"collect_count":786481,"original_title":"霸王别姬","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1023040/","avatars":{"small":"http://img7.doubanio.com/img/celebrity/small/750.jpg","large":"http://img7.doubanio.com/img/celebrity/large/750.jpg","medium":"http://img7.doubanio.com/img/celebrity/medium/750.jpg"},"name":"陈凯歌","id":"1023040"}],"year":"1993","images":{"small":"http://img7.doubanio.com/view/movie_poster_cover/ipst/public/p1910813120.jpg","large":"http://img7.doubanio.com/view/movie_poster_cover/lpst/public/p1910813120.jpg","medium":"http://img7.doubanio.com/view/movie_poster_cover/spst/public/p1910813120.jpg"},"alt":"https://movie.douban.com/subject/1291546/","id":"1291546"}]
     * title : 豆瓣电影Top250
     */

    var count: Int = 0
    var start: Int = 0
    var total: Int = 0
    var title = ""
    var subjects = arrayListOf<SubjectsBean>()

    class SubjectsBean {
        /**
         * rating : {"max":10,"average":9.6,"stars":"50","min":0}
         * genres : ["犯罪","剧情"]
         * title : 肖申克的救赎
         * casts : [{"alt":"https://movie.douban.com/celebrity/1054521/","avatars":{"small":"http://img7.doubanio.com/img/celebrity/small/17525.jpg","large":"http://img7.doubanio.com/img/celebrity/large/17525.jpg","medium":"http://img7.doubanio.com/img/celebrity/medium/17525.jpg"},"name":"蒂姆·罗宾斯","id":"1054521"},{"alt":"https://movie.douban.com/celebrity/1054534/","avatars":{"small":"http://img7.doubanio.com/img/celebrity/small/34642.jpg","large":"http://img7.doubanio.com/img/celebrity/large/34642.jpg","medium":"http://img7.doubanio.com/img/celebrity/medium/34642.jpg"},"name":"摩根·弗里曼","id":"1054534"},{"alt":"https://movie.douban.com/celebrity/1041179/","avatars":{"small":"http://img3.doubanio.com/img/celebrity/small/5837.jpg","large":"http://img3.doubanio.com/img/celebrity/large/5837.jpg","medium":"http://img3.doubanio.com/img/celebrity/medium/5837.jpg"},"name":"鲍勃·冈顿","id":"1041179"}]
         * collect_count : 1103801
         * original_title : The Shawshank Redemption
         * subtype : movie
         * directors : [{"alt":"https://movie.douban.com/celebrity/1047973/","avatars":{"small":"http://img7.doubanio.com/img/celebrity/small/230.jpg","large":"http://img7.doubanio.com/img/celebrity/large/230.jpg","medium":"http://img7.doubanio.com/img/celebrity/medium/230.jpg"},"name":"弗兰克·德拉邦特","id":"1047973"}]
         * year : 1994
         * images : {"small":"http://img7.doubanio.com/view/movie_poster_cover/ipst/public/p480747492.jpg","large":"http://img7.doubanio.com/view/movie_poster_cover/lpst/public/p480747492.jpg","medium":"http://img7.doubanio.com/view/movie_poster_cover/spst/public/p480747492.jpg"}
         * alt : https://movie.douban.com/subject/1292052/
         * id : 1292052
         */

        var rating: RatingBean = RatingBean()
        var title: String = ""
        var collect_count: Int = 0
        var original_title: String = ""
        var subtype: String = ""
        var year: String = ""
        var images: ImagesBean = ImagesBean()
        var alt: String = ""
        var id: String = ""
        var genres = arrayListOf<String>()
        var casts = arrayListOf<CastsBean>()
        var directors = arrayListOf<DirectorsBean>()

        class RatingBean {
            /**
             * max : 10
             * average : 9.6
             * stars : 50
             * min : 0
             */

            var max: Int = 0
            var average: Double = 0.0
            var stars: String = ""
            var min: Int = 0
        }

        class ImagesBean {
            /**
             * small : http://img7.doubanio.com/view/movie_poster_cover/ipst/public/p480747492.jpg
             * large : http://img7.doubanio.com/view/movie_poster_cover/lpst/public/p480747492.jpg
             * medium : http://img7.doubanio.com/view/movie_poster_cover/spst/public/p480747492.jpg
             */

            var small: String = ""
            var large: String = ""
            var medium: String = ""
        }

        class CastsBean {
            /**
             * alt : https://movie.douban.com/celebrity/1054521/
             * avatars : {"small":"http://img7.doubanio.com/img/celebrity/small/17525.jpg","large":"http://img7.doubanio.com/img/celebrity/large/17525.jpg","medium":"http://img7.doubanio.com/img/celebrity/medium/17525.jpg"}
             * name : 蒂姆·罗宾斯
             * id : 1054521
             */

            var alt: String = ""
            var avatars = AvatarsBean()
            var name: String = ""
            var id: String = ""

            class AvatarsBean {
                /**
                 * small : http://img7.doubanio.com/img/celebrity/small/17525.jpg
                 * large : http://img7.doubanio.com/img/celebrity/large/17525.jpg
                 * medium : http://img7.doubanio.com/img/celebrity/medium/17525.jpg
                 */

                var small: String = ""
                var large: String = ""
                var medium: String = ""
            }
        }

        class DirectorsBean {
            /**
             * alt : https://movie.douban.com/celebrity/1047973/
             * avatars : {"small":"http://img7.doubanio.com/img/celebrity/small/230.jpg","large":"http://img7.doubanio.com/img/celebrity/large/230.jpg","medium":"http://img7.doubanio.com/img/celebrity/medium/230.jpg"}
             * name : 弗兰克·德拉邦特
             * id : 1047973
             */

            var alt: String = ""
            var avatars = AvatarsBeanX()
            var name: String = ""
            var id: String = ""

            class AvatarsBeanX {
                /**
                 * small : http://img7.doubanio.com/img/celebrity/small/230.jpg
                 * large : http://img7.doubanio.com/img/celebrity/large/230.jpg
                 * medium : http://img7.doubanio.com/img/celebrity/medium/230.jpg
                 */

                var small: String = ""
                var large: String = ""
                var medium: String = ""
            }
        }
    }
}
