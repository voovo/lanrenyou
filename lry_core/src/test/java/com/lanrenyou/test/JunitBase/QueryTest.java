package com.lanrenyou.test.JunitBase;


import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lanrenyou.travel.service.IIndexTravelService;


public class QueryTest extends Junit4Base{
	
//	@Autowired
//    private IIndexTravelService service;
	
	private Gson gson = new Gson();
	
	@Test
	public void testQueryDB(){
//		service.getIndexTravel(24);
		String content = "[{\"src\":\"http://img.lanrenyou.com/2014/08/04/100619081_s.jpg\" , \"info\" : \"位于加勒比海的大安的列斯群岛东部，包括波多黎各岛及别克斯、库莱布拉等小岛。Castillo San Cristobal是老城圣胡安的一座古堡，红色城墙衬着蓝天碧海。\"}{\"src\":\"http://img.lanrenyou.com/2014/08/04/101031895_s.jpg\" , \"info\" : \"五彩斑斓的各种房屋是圣胡安老城的特色，徜徉其中，仿佛走在油画里一样。联合国于1981年将圣胡安列入世界文化与自然遗产的名录。\"}{\"src\":\"http://img.lanrenyou.com/2014/08/04/101340081_s.jpg\" , \"info\" : \"童话般的街景让心情变得非常明媚，艳丽的色彩使得小城看起来充满生机。\"}{\"src\":\"http://img.lanrenyou.com/2014/08/04/101845891_s.jpg\" , \"info\" : \"雨后漂亮的青石砖，透着几分江南的秀美。据说这些石砖都是专程从西班牙运来的，承载着浓厚的历史痕迹。\"}{\"src\":\"http://img.lanrenyou.com/2014/08/04/103056735_s.jpg\" , \"info\" : \"从Castillo San Cristobal遥望另一侧的El Morro古堡，海水拍打着礁石，古老的建筑屹立在蓝天之下，岸边的草坪奔跑着玩耍的孩子，超脱童话般的存在。\"}{\"src\":\"http://img.lanrenyou.com/2014/08/04/103453342_s.jpg\" , \"info\" : \"红色斑驳的古城墙和城墙下的加勒比海。有的时候还能偶遇一只当地特有的大蜥蜴。\"}{\"src\":\"http://img.lanrenyou.com/2014/08/04/103714634_s.jpg\" , \"info\" : \"El Morro古堡，雨过天晴，碧海蓝天勾勒出的热带海滨风情。\"}{\"src\":\"http://img.lanrenyou.com/2014/08/04/103942081_s.jpg\" , \"info\" : \"老城最漂亮的取景地，没有之一。圆顶建筑是这片墓地的标志，曾经荣登过各大圣胡安旅行介绍杂志的封面。\"}{\"src\":\"http://img.lanrenyou.com/2014/08/04/104453180_s.jpg\" , \"info\" : \"从波多黎各本岛坐大约1h的船就到了库莱布拉岛。这里的Flamenco海滩被探索频道评选为全球第二美的海滩。\"}{\"src\":\"http://img.lanrenyou.com/2014/08/04/104623694_s.jpg\" , \"info\" : \"清澈纯净的海水，绵白细腻宛若白糖的沙滩，还有一碧如玺的天空。或是在白沙上沿着蓝天碧海漫步，或是撑起太阳伞躺在沙滩椅上看书，或是下海玩水游泳，都不失为人间极致的享受。\"}{\"src\":\"http://img.lanrenyou.com/2014/08/04/105003398_s.jpg\" , \"info\" : \"加勒比海晶莹剔透的水晶海浪，美得不像话。\"}{\"src\":\"http://img.lanrenyou.com/2014/08/04/105809795_s.jpg\" , \"info\" : \"波多黎各的El Yunque国家森林是美国唯一的一片热带雨林。雨林里有各种千奇百怪的热带植物和浆果，运气好的话还能邂逅野生动物。\"}{\"src\":\"http://img.lanrenyou.com/2014/08/04/110150774_s.jpg\" , \"info\" : \"圣胡安还有着世界最大最受欢迎的朗姆酒厂，每人可以领到两杯免费的朗姆酒，酒厂的tour也很值得参观，无论爱酒与否都能在这里找到乐趣。\"}{\"src\":\"http://img.lanrenyou.com/2014/08/04/110453083_s.jpg\" , \"info\" : \"到了波多黎各，怎么能不试一下特色菜Mofongo呢？这是烤牛肉Mofongo，肉质选嫩多汁，非常可口。\"}{\"src\":\"http://img.lanrenyou.com/2014/08/04/110916246_s.jpg\" , \"info\" : \"波多黎各的别克斯岛拥有世界闻名的荧光海。顶着漫天繁星，划着独木舟穿过红树林，散发着微微荧光的水滴从指尖滑落。\"}{\"src\":\"http://img.lanrenyou.com/2014/08/04/111158867_s.jpg\" , \"info\" : \"在波多黎各旅行，加勒比海的浮潜是必玩的项目。在清澈的海水里与海龟同行是一件非常美妙的事情。\"}]";
		List<Map<String, String>> list = gson.fromJson(content, new TypeToken<List<Map<String, String>>>(){}.getType());
	}
	// test clone commit
}