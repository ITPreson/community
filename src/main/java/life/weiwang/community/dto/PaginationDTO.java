package life.weiwang.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 这是新构建的一个分页后的question发现数据传输对象
 * 在有问题列表的基础上又添加分页要用的属性
 */
@Data
public class PaginationDTO {
    //问题列表
    private List<QuestionDTO> questions;
    //显示上一页的按钮
    private boolean showPrevious;
    //显示直达第一页的按钮
    private boolean showFirstPage;
    //显示下一页的按钮
    private boolean showNext;
    //显示直达最后一页的按钮
    private boolean showEndPage;
    //显示当前页
    private Integer page;
    //显示的页码list
    private List<Integer> pages = new ArrayList<>();
    //总页数
    private Integer totalPage;

    public void setPagenaiton(Integer totalCount, Integer page, Integer size) {

        //分页数
        totalPage = 0;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;

        }
        if (page > totalPage) {
            page = totalPage;
        }

        this.page = page;

        //前端点击第几页会传回来一个页码，初始默认为1，把它添加进页码list
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                //后面的参数为0，表示从list的头部添加
                pages.add(0, page - i);
            }
            if (page + i <= totalPage) {
                pages.add(page + i);

            }
        }


        //是否展示上一页
        if (page != 1) {
            showPrevious = true;
        } else {
            showPrevious = false;
        }

        //是否展示下一页
        if (page == totalPage) {
            showNext = false;
        } else {
            showNext = true;
        }

        //是否展示直达第一页按钮
        if (pages.contains(1)) {
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }

        //是否展示最后一页
        if (pages.contains(totalPage)) {
            showEndPage = false;
        } else {
            showEndPage = true;
        }


    }
}
