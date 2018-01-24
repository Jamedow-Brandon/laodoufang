package com.jamedow.laodoufang.service;

import com.jamedow.laodoufang.plugin.es.EsClient;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.lucene.search.function.FieldValueFactorFunction;
import org.elasticsearch.common.lucene.search.function.FiltersFunctionScoreQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;

/**
 * Description
 * <p>
 * Created by Administrator on 2018/1/16.
 */
@Service
public class SearchService {
    private Logger logger = LoggerFactory.getLogger(EsClient.class);

    public List<Map<String, Object>> search(String index, String type, String content, String tags, String isOfficial, int from, int pageSize) {
        SearchResponse searchResponse = EsClient.search(index, type,
                buildSearchQuery(content, tags, isOfficial), from, pageSize);
        List<Map<String, Object>> list = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            Map<String, Object> source = hit.getSourceAsMap();
            list.add(source);
        }
        return list;
    }

    private FunctionScoreQueryBuilder buildSearchQuery(String content, String tags, String isOfficial) {
        FunctionScoreQueryBuilder query = null;

        BoolQueryBuilder bool = QueryBuilders.boolQuery();

        bool.must(termQuery("name", content).boost(0.5f));
        if (StringUtils.isNotBlank(tags)) {
            bool.must(termQuery("tags", tags).boost(0.5f));
        }
        if (isOfficial != null && "1".equals(isOfficial)) {
            ScoreFunctionBuilder scoreFunctionBuilder = ScoreFunctionBuilders.fieldValueFactorFunction("isOfficial").modifier(FieldValueFactorFunction.Modifier.NONE);
            query = QueryBuilders.functionScoreQuery(bool, scoreFunctionBuilder).scoreMode(FiltersFunctionScoreQuery.ScoreMode.SUM);
        } else {
            query = QueryBuilders.functionScoreQuery(bool).scoreMode(FiltersFunctionScoreQuery.ScoreMode.SUM);
        }
        return query;
    }

    public String insertRecipeToEs(String index, String type, String source) {
        String searchDocumentId = null;
        try {
//            XContentBuilder builder = jsonBuilder()
//                    .startObject()
//                    .field("id", recipe.getId())
//                    .field("name", recipe.getName())
//                    .field("intro", recipe.getIntro())
//                    .field("createTime", recipe.getCreateTime())
//                    .field("linkUrl", recipe.getLinkUrl())
//                    .field("imgUrl", recipe.getImgUrl())
//                    .field("tags", recipe.getTags())
//                    .field("voteUp", recipe.getVoteUp())
//                    .field("voteDown", recipe.getVoteDown())
//                    .field("isOfficial", recipe.getIsOfficial())
//                    .field("userId", recipe.getUserId())
//                    .field("trafficVolume", recipe.getTrafficVolume())
//                    .field("ingredient", recipe.getIngredient())
//                    .field("burdening", recipe.getBurdening())
//                    .endObject();

            JSONObject sourceObject = JSONObject.fromObject(source);
            searchDocumentId = sourceObject.getString("searchDocumentId");

            searchDocumentId = EsClient.createDocument(index, type, searchDocumentId, source);
        } catch (Exception e) {
            logger.error("新建文档失败{}", source, e.getMessage(), e);
        }
        return searchDocumentId;
    }

    public int delete(String index, String type, String searchDocumentId) {
        return EsClient.deleteDocumentById(index, type, searchDocumentId).status().getStatus();
    }
}
