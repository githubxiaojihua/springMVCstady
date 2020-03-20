package com.xiaojihua.controller;

import com.xiaojihua.pojo.Items;
import com.xiaojihua.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("/itemList")
    public ModelAndView getItemList(){
        List<Items> itemsList = itemService.getItemsList();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("itemList",itemsList);
        modelAndView.setViewName("itemList");
        return modelAndView;
    }

    /**
     * Controller参数绑定的方法之一：直接从request中获取参数
     * @param request
     * @return
     */
    @RequestMapping("/itemEdit")
    public ModelAndView itemEdit(HttpServletRequest request){

        // 从request中获取参数
        String strId = request.getParameter("id");
        Integer id = null;
        if(!StringUtils.isEmpty(strId)){
            id = new Integer(strId);
        }else{
            // 没有参数则返回空
            return null;
        }
        // 根据id获取item
        Items item = itemService.getItemById(id);
        // 创建modelandview
        ModelAndView modelAndView = new ModelAndView();
        // 向jsp传递数据
        modelAndView.addObject("item",item);
        // 跳转jsp页面
        modelAndView.setViewName("editItem");
        return modelAndView;
    }

    /**
     * 通过model可以向页面设置数据，有了model，可以不用返回modelandview
     * 直接返回一个String就可以了.
     * 不管是Model还是ModelAndView，其本质都是使用Request对象向jsp传递数据.
     * @param request
     * @return
     */
    @RequestMapping("/itemEdit2")
    public String itemEdit2(HttpServletRequest request,Model model){

        // 从request中获取参数
        String strId = request.getParameter("id");
        Integer id = null;
        if(!StringUtils.isEmpty(strId)){
            id = new Integer(strId);
        }else{
            // 没有参数则返回空
            return null;
        }
        // 根据id获取item
        Items item = itemService.getItemById(id);
        // 向model中设置数据
        model.addAttribute("item",item);
        // 跳转jsp页面
        return "editItem";
    }

    /**
     * Controller参数绑定的方法之二：绑定简单类型
     * 当请求的参数名称和处理器形参名称一致时会将请求参数与形参进行绑定
     * http://localhost:8080/ssm/itemEdit3.action?id=1
     * 请求中传递了参数id，springmvc将会自动进行绑定
     * 支持的数据类型包括：
     * Integer\int
     * String
     * Float\float
     * Double\double
     * Boolean\boolean
     * @return
     */
    @RequestMapping("/itemEdit3")
    public String itemEdit3(Integer id,Model model){
        // 根据id获取item
        Items item = itemService.getItemById(id);
        // 向model中设置数据
        model.addAttribute("item",item);
        // 跳转jsp页面
        return "editItem";
    }

    /**
     * Controller参数绑定的方法之三：使用@RequestParam来绑定简单数据类型
     * 一般用在前台传入的参数名称与后台接收的名称不一致
     * @return
     */
    @RequestMapping("/itemEdit4")
    public String itemEdit4(@RequestParam(value="item_id",required=true,defaultValue="1") int id, Model model){
        // 根据id获取item
        Items item = itemService.getItemById(id);
        // 向model中设置数据
        model.addAttribute("item",item);
        // 跳转jsp页面
        return "editItem";
    }

    /**
     * Controller参数绑定的方法之四：绑定pojo对象
     * 要求pojo对象中的属性名称与参数名称必须一致
     *
     * 如果属性中有日期类型，那么需要配置转换器或者增加注解@DateTimeFormat(pattern="yyyy-MM-dd")
     */
    @RequestMapping("/updateitem")
    public String updateItem(Items items){
        itemService.update(items);
        // 目前还不牵扯到请求转发，先定位到一个成功页面
        return "success";
    }
}
