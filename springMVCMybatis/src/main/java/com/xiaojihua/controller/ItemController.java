package com.xiaojihua.controller;

import com.xiaojihua.pojo.Items;
import com.xiaojihua.pojo.QueryVo;
import com.xiaojihua.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
/*@RequestMapping("/item") // 可以加到类上起到窄化请求的效果*/
public class ItemController {
    @Autowired
    private ItemService itemService;

    // method用来限制请求的方式
    @RequestMapping(value={"/itemList","/itemlist","/list"},method={RequestMethod.GET,RequestMethod.POST})
    public ModelAndView getItemList(QueryVo queryVo, String[] ids){// 这里可以是int[] springmvc将自动进行类型转换。也可以放到一个pojo中作为一个属性

        // 用于测试自定义异常
        // int i = 10/0;
        // 捕获异常，获取异常栈信息，可以把栈信息放到其他地方
        /*try{
            int i = 10/0;
        }catch (Exception e){
            StringWriter s = new StringWriter();
            PrintWriter pw = new PrintWriter(s);
            e.printStackTrace(pw);
            String s1 = s.toString();
            System.out.println(s1);
        }*/

        List<Items> itemsList = itemService.getItemsList();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("itemList",itemsList);
        modelAndView.setViewName("itemList");

        // 获取前台传入的数组类型参数（比如checkbox）
        System.out.println(ids);
        // 接收前台传过来的List，这种情况下需要用包装pojo来接收。在形参中无法接收List
        // 在queryVo中有List<Items> itemList，可以接收前台传过来的多个对象
        // 而在jsp中使用的是itemList[0].name,itemList[1].price...等属性进行传递。
        // jsp中使用的是input标签的表格，每行作为一个对象
        // 另外ids在queryVo中也有是int[]，springmvc可以同时将queryvo中的和形参
        // 列表中的ids进行赋值并且自动转换数据类型
        System.out.println(queryVo);

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
    public String updateItem(Items items, MultipartFile pictureFile) throws IOException {

        // 上传图片相关
        // 1、获取上传文件的完整路名称，包括扩展名
        String originalFilename = pictureFile.getOriginalFilename();
        // 2、创建一个随机数作为图片名称
        String fileName = UUID.randomUUID().toString();
        // 3、获取文件扩展名
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 4、保存文件
        pictureFile.transferTo(new File("I:\\image\\" + fileName + ext));
        // 5、保存到数据库中
        items.setPic(fileName + ext);


        itemService.update(items);
        // 目前还不牵扯到请求转发，先定位到一个成功页面
        /*return "success";*/


        // 重定向（原来的request和response作废）
        // 类似与response.sendRedirect()
        // return "redirect:list.action";


        // 请求转发（原来的request和response有效），浏览器地址栏不变，
        // 类似于request.getRequestDispatcher().forward(request,response)
        return "forward:list.action";
    }

    /**
     * springmvc 接收ajax提交的json字符串作为参数，进行自动对应
     * 需要导入响应的jar，然后配合使用requestBody
     * @param items
     * @return
     */
    @RequestMapping("/updateItemAjax")
    //作用 把即将返回的对象转成json字符串并且回写到浏览器
    @ResponseBody
    public Map updateItemAjax(@RequestBody Items items){//@RequestBody 强制要求传入的参数类型是json
        //		data :{"success":true|false,"message":"操作成功"|“操作失败”}
        Map<String,Object> map = new HashMap<>();
        try {
            itemService.update(items);
            map.put("success", true);
            map.put("message", "操作成功");
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "操作失败");
//			e.printStackTrace();
        }
        return map;
    }

    /**
     * springmvc 接收ajax提交的json字符串，里面包含多个对象
     * 后台通过List接收
     * @param items
     * @return
     */
    @RequestMapping("/requestBodyTest")
    public Map requestBodyTest(@RequestBody List<Items> items){

        return null;
    }


    /**
     * 使用restful风格进行查询
     * http://localhost:8980/ssm/itemEditRestful/2
     * 需要在web.xml中设置dispatcherServlet的映射地址为/
     * 搭配pathvarialbe注解使用
     * @return
     */
    @RequestMapping("/itemEditRestful/{id}")
    public String itemEditRestful(@PathVariable("id") int id, Model model){
        // 根据id获取item
        Items item = itemService.getItemById(id);
        // 向model中设置数据
        model.addAttribute("item",item);
        // 跳转jsp页面
        return "editItem";
    }
}
