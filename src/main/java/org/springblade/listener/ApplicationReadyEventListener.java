package org.springblade.listener;

import org.springblade.car.entity.Styles;
import org.springblade.car.service.IStylesService;
import org.springblade.common.cache.CacheNames;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.util.SimilarityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: bond
 * @Date: 2020/4/22
 * @Description:
 */
@Component
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {
	@Autowired
	private BladeRedis bladeRedis;
	@Autowired
	private IStylesService stylesService;

	@Override public void onApplicationEvent(ApplicationReadyEvent  applicationReadyEvent) {
		/*List<Styles>  list =stylesService.list();
		Map<Long,String> keyWordsMap= new HashMap<>();
		String str="";
		for (Styles style:list){
			//styles_name,styles_year,brand_name,series_name,group_name,configuration
			str=style.getStylesName()+style.getStylesYear()+style.getBrandName()+style.getSeriesName()+style.getGroupName()+style.getConfiguration();
			keyWordsMap.put(style.getId(), SimilarityUtils.ARFA2(str));
		}
		bladeRedis.set(CacheNames.STYLES_KEY,keyWordsMap);
		*/

	  }

}
