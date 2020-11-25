package com.ezcoding.common.web.resolver.parameter;

import com.ezcoding.common.foundation.core.message.PageInfo;
import com.ezcoding.common.foundation.core.message.RequestAppHead;
import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.ezcoding.common.web.resolver.StandardPage;
import com.ezcoding.common.web.resolver.StandardParam;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.core.MethodParameter;

import java.util.Optional;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-25 20:08
 */
public class PageInfoRequestMessageResolver extends AbstractRequestMessageResolver {

    public PageInfoRequestMessageResolver() {
        super(PageInfo.class);
    }

    @Override
    public Object resolve(RequestMessage<JsonNode> requestMessage, StandardParam parameterAnnotation, MethodParameter methodParameter) throws Exception {
        PageInfo pageInfo = Optional
                .ofNullable(requestMessage)
                .map(RequestMessage::getAppHead)
                .map(RequestAppHead::getPageInfo)
                .orElseGet(PageInfo::new);
        StandardPage page = methodParameter.getParameterAnnotation(StandardPage.class);
        if (page != null) {
            this.fillDefaultValue(pageInfo, page.currentPage(), page.pageSize(), page.searchCount());
        }
        return pageInfo;
    }

    /**
     * 填充默认值
     *
     * @param pageInfo    待填充的分页信息
     * @param currentPage 默认的当前页
     * @param pageSize    默认的分页大小
     * @param searchCount 默认是否检索总页数
     */
    private void fillDefaultValue(PageInfo pageInfo, int currentPage, int pageSize, boolean searchCount) {
        if (pageInfo == null) {
            return;
        }
        if (pageInfo.getCurrentPage() == null) {
            pageInfo.setCurrentPage(currentPage);
        }
        if (pageInfo.getPageSize() == null) {
            pageInfo.setPageSize(pageSize);
        }
        if (pageInfo.getSearchCount() == null) {
            pageInfo.setSearchCount(searchCount);
        }
    }

}
