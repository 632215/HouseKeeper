package com.jinke.housekeeper.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 17-3-30.
 */

public class ScenePageInfo implements Serializable {


    private List<ObjBean> obj;

    public List<ObjBean> getObj() {
        return obj;
    }

    public void setObj(List<ObjBean> obj) {
        this.obj = obj;
    }

    public static class ObjBean implements Serializable {
        /**
         * id : 0
         * listObj : [{"id":"19","listObj":[{"id":"48","listObj":[],"text":"仪征溪上明堂项目"},{"id":"27","listObj":[],"text":"南通中南世纪城项目"},{"id":"25","listObj":[],"text":"南通廊桥水岸"},{"id":"26","listObj":[],"text":"南通苏通科技产业园项目"},{"id":"24","listObj":[],"text":"南通金融科技城项目"},{"id":"46","listObj":[],"text":"吴江廊桥水岸观邸项目"},{"id":"28","listObj":[],"text":"如皋世界城"},{"id":"29","listObj":[],"text":"如皋御龙湾项目"},{"id":"50","listObj":[],"text":"张家港廊桥天都"},{"id":"49","listObj":[],"text":"张家港廊桥美墅"},{"id":"47","listObj":[],"text":"扬中红星美凯龙项目"},{"id":"36","listObj":[],"text":"无锡(惠山)生命科技产业园项目"},{"id":"45","listObj":[],"text":"无锡万博商业广场"},{"id":"39","listObj":[],"text":"无锡东方水榭"},{"id":"41","listObj":[],"text":"无锡国际一花园项目"},{"id":"38","listObj":[],"text":"无锡城南世家"},{"id":"42","listObj":[],"text":"无锡恒电新能源"},{"id":"44","listObj":[],"text":"无锡米兰花园"},{"id":"40","listObj":[],"text":"无锡观庭"},{"id":"37","listObj":[],"text":"无锡财富商业广场"},{"id":"43","listObj":[],"text":"无锡金域香颂项目"},{"id":"20","listObj":[],"text":"江阴东方大院"},{"id":"21","listObj":[],"text":"江阴华府"},{"id":"23","listObj":[],"text":"江阴雅盛城市嘉园项目"},{"id":"22","listObj":[],"text":"江阴黄山华都项目"},{"id":"30","listObj":[],"text":"苏州东方水榭"},{"id":"33","listObj":[],"text":"苏州华谊艺术家村项目"},{"id":"35","listObj":[],"text":"苏州天籁花园"},{"id":"31","listObj":[],"text":"苏州枫景颐庭"},{"id":"32","listObj":[],"text":"苏州观天下花苑"},{"id":"34","listObj":[],"text":"苏州锦华苑项目"}],"text":"华东分公司"},{"id":"51","listObj":[{"id":"65","listObj":[],"text":"平塘中央大街"},{"id":"61","listObj":[],"text":"怀化PICC"},{"id":"63","listObj":[],"text":"怀化天星华府"},{"id":"62","listObj":[],"text":"怀化锦绣新城（顾问）"},{"id":"66","listObj":[],"text":"植物原著"},{"id":"64","listObj":[],"text":"浏阳山水洲"},{"id":"60","listObj":[],"text":"湖北丰业盛世"},{"id":"58","listObj":[],"text":"郴州五矿青园"},{"id":"57","listObj":[],"text":"郴州金科城"},{"id":"59","listObj":[],"text":"郴州钻石山"},{"id":"54","listObj":[],"text":"长沙上河国际（顾问）"},{"id":"56","listObj":[],"text":"长沙世界城"},{"id":"52","listObj":[],"text":"长沙东方大院"},{"id":"55","listObj":[],"text":"长沙时代中心"},{"id":"53","listObj":[],"text":"长沙金科亿达（科技新城）"}],"text":"华中分公司"},{"id":"5","listObj":[{"id":"8","listObj":[],"text":"北京天籁城"},{"id":"7","listObj":[],"text":"北京帕提欧"},{"id":"6","listObj":[],"text":"北京廊桥水岸"},{"id":"10","listObj":[],"text":"济南蓝海领航"},{"id":"9","listObj":[],"text":"济南金科城"},{"id":"13","listObj":[],"text":"绿博家苑"},{"id":"14","listObj":[],"text":"绿博新城"},{"id":"17","listObj":[],"text":"西耿河家园"},{"id":"18","listObj":[],"text":"郑州金科城"},{"id":"11","listObj":[],"text":"金科王府"},{"id":"12","listObj":[],"text":"锦州伟业东湖天玺顾问项目"},{"id":"15","listObj":[],"text":"青岛金科星辰"},{"id":"16","listObj":[],"text":"青岛阳光美镇"}],"text":"华北分公司"},{"id":"87","listObj":[{"id":"93","listObj":[],"text":"中亚机电城"},{"id":"88","listObj":[],"text":"华海新里城"},{"id":"92","listObj":[],"text":"新疆廊桥水乡"},{"id":"90","listObj":[],"text":"维拉庄园"},{"id":"89","listObj":[],"text":"蓝溪谷"},{"id":"91","listObj":[],"text":"香榭丽都"}],"text":"新疆分公司"},{"id":"1","listObj":[{"id":"207","listObj":[],"text":"发发发"},{"id":"2","listObj":[],"text":"测试天湖美镇"},{"id":"3","listObj":[],"text":"测试项目3"},{"id":"4","listObj":[],"text":"测试项目中华坊"}],"text":"测试分公司"},{"id":"94","listObj":[{"id":"123","listObj":[],"text":"万州观天下"},{"id":"122","listObj":[],"text":"万州财富广场项目"},{"id":"121","listObj":[],"text":"万盛中华养生城"},{"id":"100","listObj":[],"text":"丰都中国移动项目"},{"id":"99","listObj":[],"text":"丰都黄金海岸"},{"id":"126","listObj":[],"text":"云阳世界城"},{"id":"116","listObj":[],"text":"利川龙船天街项目"},{"id":"119","listObj":[],"text":"南川世界城"},{"id":"101","listObj":[],"text":"奉节环彬白帝天下顾问项目"},{"id":"124","listObj":[],"text":"巫山金科城"},{"id":"113","listObj":[],"text":"开县公园王府项目"},{"id":"114","listObj":[],"text":"开县开州城"},{"id":"115","listObj":[],"text":"开县维拉莊园"},{"id":"112","listObj":[],"text":"开县财富中心"},{"id":"97","listObj":[],"text":"恩施九尊上苑项目"},{"id":"98","listObj":[],"text":"恩施首府壹品项目"},{"id":"117","listObj":[],"text":"梁平顺盛依山郡项目"},{"id":"125","listObj":[],"text":"永江高速"},{"id":"106","listObj":[],"text":"涪陵世界走廊A区"},{"id":"110","listObj":[],"text":"涪陵中央公园城"},{"id":"107","listObj":[],"text":"涪陵天宸"},{"id":"108","listObj":[],"text":"涪陵天湖小镇"},{"id":"109","listObj":[],"text":"涪陵天籁城"},{"id":"105","listObj":[],"text":"涪陵廊桥水岸"},{"id":"103","listObj":[],"text":"涪陵红星国际广场项目"},{"id":"102","listObj":[],"text":"涪陵长江师范学院项目"},{"id":"104","listObj":[],"text":"涪陵黄金海岸"},{"id":"120","listObj":[],"text":"綦江电力局"},{"id":"118","listObj":[],"text":"邻水中元南城国际项目"},{"id":"111","listObj":[],"text":"金凤苑"},{"id":"95","listObj":[],"text":"长寿世界城"},{"id":"96","listObj":[],"text":"长寿阳光小镇"}],"text":"渝东分公司"},{"id":"71","listObj":[{"id":"85","listObj":[],"text":"内江中央公园城"},{"id":"82","listObj":[],"text":"内江公园王府"},{"id":"83","listObj":[],"text":"内江区域"},{"id":"84","listObj":[],"text":"内江时代中心"},{"id":"72","listObj":[],"text":"成都东方雅郡"},{"id":"78","listObj":[],"text":"成都双楠天都"},{"id":"79","listObj":[],"text":"成都天籁城"},{"id":"77","listObj":[],"text":"成都廊桥水乡"},{"id":"80","listObj":[],"text":"成都星耀天都"},{"id":"76","listObj":[],"text":"成都金沙城"},{"id":"74","listObj":[],"text":"成都金科一城"},{"id":"75","listObj":[],"text":"成都金科中心"},{"id":"73","listObj":[],"text":"成都金科天宸"},{"id":"81","listObj":[],"text":"芒市华江水岸星城"},{"id":"86","listObj":[],"text":"遂宁美湖湾"}],"text":"西南分公司"},{"id":"127","listObj":[{"id":"128","listObj":[],"text":"10年城东区"},{"id":"129","listObj":[],"text":"10年城西北区"},{"id":"130","listObj":[],"text":"VISAR国际"},{"id":"141","listObj":[],"text":"东方王府"},{"id":"195","listObj":[],"text":"中央华府"},{"id":"196","listObj":[],"text":"中央御院"},{"id":"159","listObj":[],"text":"乐山万达广场"},{"id":"193","listObj":[],"text":"云湖天都"},{"id":"171","listObj":[],"text":"人和政府项目"},{"id":"182","listObj":[],"text":"信达国际"},{"id":"181","listObj":[],"text":"信达滨江蓝庭"},{"id":"160","listObj":[],"text":"六盘水山语城"},{"id":"131","listObj":[],"text":"半岛逸景（公租房）"},{"id":"146","listObj":[],"text":"华美翡丽山"},{"id":"166","listObj":[],"text":"南岸区保时捷销售中心"},{"id":"143","listObj":[],"text":"合川世界城"},{"id":"144","listObj":[],"text":"合川天籁城"},{"id":"142","listObj":[],"text":"合川恒春凤凰城项目"},{"id":"145","listObj":[],"text":"和园"},{"id":"135","listObj":[],"text":"大宇里面项目"},{"id":"138","listObj":[],"text":"大足中国西南城"},{"id":"140","listObj":[],"text":"大足中央公园城"},{"id":"139","listObj":[],"text":"大足中央公园城"},{"id":"136","listObj":[],"text":"大足茂坤观棠晓月"},{"id":"137","listObj":[],"text":"大足茂坤观棠晓月"},{"id":"175","listObj":[],"text":"天湖美镇"},{"id":"174","listObj":[],"text":"太阳海岸"},{"id":"180","listObj":[],"text":"小城故事"},{"id":"158","listObj":[],"text":"廊桥美丽墅"},{"id":"148","listObj":[],"text":"建工金州苑"},{"id":"192","listObj":[],"text":"御康山庄"},{"id":"147","listObj":[],"text":"机电城"},{"id":"132","listObj":[],"text":"比华利豪园"},{"id":"187","listObj":[],"text":"永川中央公园城"},{"id":"186","listObj":[],"text":"永川中央公园城"},{"id":"188","listObj":[],"text":"永川中央金街"},{"id":"189","listObj":[],"text":"永川中央金街"},{"id":"183","listObj":[],"text":"永川公园王府"},{"id":"184","listObj":[],"text":"永川阳光小镇"},{"id":"185","listObj":[],"text":"永川阳光小镇"},{"id":"190","listObj":[],"text":"永江高速"},{"id":"149","listObj":[],"text":"江津世界城"},{"id":"150","listObj":[],"text":"江津中央公园城"},{"id":"157","listObj":[],"text":"津辉半山府邸"},{"id":"134","listObj":[],"text":"测试楼盘"},{"id":"163","listObj":[],"text":"湄潭德信国际广场"},{"id":"178","listObj":[],"text":"潼南加新新华花园"},{"id":"177","listObj":[],"text":"潼南加新新华花园"},{"id":"133","listObj":[],"text":"璧山中央公园城"},{"id":"169","listObj":[],"text":"綦江元方御景江湾项目"},{"id":"167","listObj":[],"text":"綦江法院"},{"id":"168","listObj":[],"text":"綦江红星国际广场"},{"id":"165","listObj":[],"text":"纳雍骏豪盛世国际"},{"id":"161","listObj":[],"text":"绿韵康城"},{"id":"164","listObj":[],"text":"美镇王榭"},{"id":"172","listObj":[],"text":"荣昌世界城"},{"id":"173","listObj":[],"text":"荣昌世界城"},{"id":"162","listObj":[],"text":"蚂蚁SOHO"},{"id":"179","listObj":[],"text":"西城大院"},{"id":"194","listObj":[],"text":"证监局项目"},{"id":"176","listObj":[],"text":"通用时代"},{"id":"206","listObj":[],"text":"遵义中央公园城"},{"id":"205","listObj":[],"text":"遵义湄潭黔北"},{"id":"202","listObj":[],"text":"重庆世界城"},{"id":"203","listObj":[],"text":"重庆天籁城"},{"id":"201","listObj":[],"text":"重庆廊桥天都"},{"id":"200","listObj":[],"text":"重庆廊桥水乡"},{"id":"199","listObj":[],"text":"重庆廊桥水岸"},{"id":"197","listObj":[],"text":"重庆金科城"},{"id":"198","listObj":[],"text":"重庆金科天宸"},{"id":"204","listObj":[],"text":"重庆阳光小镇"},{"id":"156","listObj":[],"text":"金砂水岸"},{"id":"154","listObj":[],"text":"金科天元道"},{"id":"153","listObj":[],"text":"金科时代中心"},{"id":"155","listObj":[],"text":"金科星辰"},{"id":"152","listObj":[],"text":"金科空港城"},{"id":"151","listObj":[],"text":"金科花园"},{"id":"170","listObj":[],"text":"青麓雅园"},{"id":"191","listObj":[],"text":"预师一团项目"}],"text":"重庆分公司"},{"id":"67","listObj":[{"id":"70","listObj":[],"text":"咸阳世界城"},{"id":"68","listObj":[],"text":"西安天籁城"},{"id":"69","listObj":[],"text":"西安紫薇公园时光顾问项目"}],"text":"陕西分公司"}]
         * text : 金科物业服务有限公司
         */

        private String id;
        private String text;
        private List<ListObjBeanX> listObj;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public List<ListObjBeanX> getListObj() {
            return listObj;
        }

        public void setListObj(List<ListObjBeanX> listObj) {
            this.listObj = listObj;
        }

        public static class ListObjBeanX implements Serializable {
            /**
             * id : 19
             * listObj : [{"id":"48","listObj":[],"text":"仪征溪上明堂项目"},{"id":"27","listObj":[],"text":"南通中南世纪城项目"},{"id":"25","listObj":[],"text":"南通廊桥水岸"},{"id":"26","listObj":[],"text":"南通苏通科技产业园项目"},{"id":"24","listObj":[],"text":"南通金融科技城项目"},{"id":"46","listObj":[],"text":"吴江廊桥水岸观邸项目"},{"id":"28","listObj":[],"text":"如皋世界城"},{"id":"29","listObj":[],"text":"如皋御龙湾项目"},{"id":"50","listObj":[],"text":"张家港廊桥天都"},{"id":"49","listObj":[],"text":"张家港廊桥美墅"},{"id":"47","listObj":[],"text":"扬中红星美凯龙项目"},{"id":"36","listObj":[],"text":"无锡(惠山)生命科技产业园项目"},{"id":"45","listObj":[],"text":"无锡万博商业广场"},{"id":"39","listObj":[],"text":"无锡东方水榭"},{"id":"41","listObj":[],"text":"无锡国际一花园项目"},{"id":"38","listObj":[],"text":"无锡城南世家"},{"id":"42","listObj":[],"text":"无锡恒电新能源"},{"id":"44","listObj":[],"text":"无锡米兰花园"},{"id":"40","listObj":[],"text":"无锡观庭"},{"id":"37","listObj":[],"text":"无锡财富商业广场"},{"id":"43","listObj":[],"text":"无锡金域香颂项目"},{"id":"20","listObj":[],"text":"江阴东方大院"},{"id":"21","listObj":[],"text":"江阴华府"},{"id":"23","listObj":[],"text":"江阴雅盛城市嘉园项目"},{"id":"22","listObj":[],"text":"江阴黄山华都项目"},{"id":"30","listObj":[],"text":"苏州东方水榭"},{"id":"33","listObj":[],"text":"苏州华谊艺术家村项目"},{"id":"35","listObj":[],"text":"苏州天籁花园"},{"id":"31","listObj":[],"text":"苏州枫景颐庭"},{"id":"32","listObj":[],"text":"苏州观天下花苑"},{"id":"34","listObj":[],"text":"苏州锦华苑项目"}]
             * text : 华东分公司
             */

            private String id;
            private String text;
            private List<ListObjBean> listObj;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public List<ListObjBean> getListObj() {
                return listObj;
            }

            public void setListObj(List<ListObjBean> listObj) {
                this.listObj = listObj;
            }

            public static class ListObjBean implements Serializable {
                /**
                 * id : 48
                 * listObj : []
                 * text : 仪征溪上明堂项目
                 */

                private String id;
                private String text;
                private List<?> listObj;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public List<?> getListObj() {
                    return listObj;
                }

                public void setListObj(List<?> listObj) {
                    this.listObj = listObj;
                }
            }
        }
    }
}
