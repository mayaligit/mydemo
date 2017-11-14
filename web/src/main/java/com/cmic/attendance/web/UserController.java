package com.cmic.attendance.web;/**
 * Created by pc on 2017/11/14.
 */

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @author 何家来
 * @create 2017-11-14 8:39
 **/
@Api(description = "统计表管理")
@RestController
@RequestMapping("/user")
public class UserController {


    @ApiOperation(value = "获取验证码", notes = "获取验证码", httpMethod = "GET")
    @RequestMapping(value="/getCheckCode", method = RequestMethod.GET)
    public void getCheckCode(HttpServletRequest request, HttpServletResponse response){

        //服务器通知浏览器不要缓存
        response.setHeader("pragma","no-cache");
        response.setHeader("cache-control","no-cache");
        response.setHeader("expires","0");

        //在内存中创建一张长80，宽30的图片，默认黑色
        //参数一：长
        //参数二：宽
        //参数三：颜色
        int width = 100;
        int height = 40;
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);


        //获取画笔
        Graphics g = image.getGraphics();
        //设置画笔颜色为灰色
        g.setColor(Color.GRAY);
        //填充图片
        g.fillRect(0,0, width,height);


        //产生4个随机验证码
        String checkCode = getCheckCode();
        //将验证码放入HttpSession中
        request.getSession().setAttribute("checkCodeServer",checkCode);

        //设置画笔颜色为黄色
//        g.setColor(Color.YELLOW);
        g.setColor(getRandomColor(150, 240));// 随机设置字体颜色
        //设置字体的小大
        g.setFont(new Font("黑体", Font.BOLD,30));
        Random random = new Random();
        //干扰线
                 for(int i=0;i<10;i++){
                            int x=random.nextInt(width);
                              int y=random.nextInt(height);
                          int x1=random.nextInt(width);
                             int y1=random.nextInt(height);
                          g.drawLine(x, y, x+x1, y+y1);
                         }
        //向图片上写入验证码
        g.drawString(checkCode,15,25);


        //将内存中的图片输出到浏览器
        //参数一：图片对象
        //参数二：图片的格式，如PNG,JPG,GIF
        //参数三：图片输出到哪里去
        try {
            ImageIO.write(image,"PNG",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * 产生4位随机字符串
     */
    private String getCheckCode() {
        String base = "0123456789ABCDEFGabcdefg";
        int size = base.length();
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i=1;i<=4;i++){
            //产生0到size-1的随机值
            int index = r.nextInt(size);
            //在base字符串中获取下标为index的字符
            char c = base.charAt(index);
            //将c放入到StringBuffer中去
            sb.append(c);
        }
        return sb.toString();
    }
    /**
     18      * 获取随机颜色值
     19      * @param minColor
     20      * @param maxColor
     21      * @return
     22      */
   private static Color getRandomColor(int minColor, int maxColor) {

    Random random = new Random();
          // 保存minColor最大不会超过255
            if (minColor > 255)
                    minColor = 255;
       // 保存minColor最大不会超过255
     if (maxColor > 255)
                      maxColor = 255;
              // 获得红色的随机颜色值
            int red = minColor + random.nextInt(maxColor - minColor);
             // 获得绿色的随机颜色值
          int green = minColor + random.nextInt(maxColor - minColor);
              // 获得蓝色的随机颜色值
              int blue = minColor + random.nextInt(maxColor - minColor);
              return new Color(red, green, blue);

           }
}
