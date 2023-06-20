package hjy.com.red_book_community;

import org.junit.Test;
import java.util.Date;

import hjy.com.red_book_community.community.bean.ArticleBean;
import hjy.com.red_book_community.community.bean.FontEndBean;
import hjy.com.red_book_community.utils.BeanUtils;

public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {
        ArticleBean articleBean = new ArticleBean(1, "abc", "sda", "/demo", "/tx", "yjh", 199L, new Date());
        FontEndBean fontEndBean = new FontEndBean();
        BeanUtils.copyProperties(articleBean, fontEndBean);
        System.out.println(fontEndBean.getTitle());
    }

    @Test
    public void demo1() {}

}